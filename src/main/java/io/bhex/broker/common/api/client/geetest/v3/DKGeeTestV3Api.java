package io.bhex.broker.common.api.client.geetest.v3;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import io.bhex.broker.common.api.client.geetest.GeeTestConfig;
import io.bhex.broker.common.api.client.geetest.GeeTestProperties;
import io.bhex.broker.common.api.client.geetest.v3.sdk.DigestmodEnum;
import io.bhex.broker.common.api.client.geetest.v3.sdk.GeetestLib;
import io.bhex.broker.common.api.client.geetest.v3.sdk.GeetestLibResult;
import io.bhex.broker.common.api.client.geetest.v3.sdk.HttpClientUtils;
import io.bhex.broker.common.exception.BrokerErrorCode;
import io.bhex.broker.common.exception.BrokerException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

/**
 * @author wangsc
 * @description
 * @date 2021-10-22 11:18
 */


@Slf4j
public class DKGeeTestV3Api {

    private final ImmutableMap<String, String> configMap;

    private final Map<String, Boolean> geeTestStatusMap = new HashMap<>();

    private final DigestmodEnum digestmodEnum = DigestmodEnum.MD5;

    private static final String CODE_SUFFIX = "|jordan";

    private static final Integer GEE_TEST_SUCCESS_STATUS = 1;

    private static final String RE_CAPTCHA_SUPPLIER = "geeV3";

    private static final String GT_STATUS_URL = "http://bypass.geetest.com/v1/bypass_status.php";

    public DKGeeTestV3Api(String reCaptchaSupplier, GeeTestProperties geeTestProperties) {
        List<GeeTestConfig> configList = geeTestProperties.getConfigs();
        Map<String, String> tmpConfigMap = Maps.newHashMap();
        for (GeeTestConfig geeTestConfig : configList) {
            tmpConfigMap.put(geeTestConfig.getId(), geeTestConfig.getPrivateKey());
        }
        configMap = ImmutableMap.copyOf(tmpConfigMap);
        if (RE_CAPTCHA_SUPPLIER.equalsIgnoreCase(reCaptchaSupplier)) {
            //启动定时任务,检查极验云状态,延时1s定时10s
            new Timer().schedule(new CheckGeetestStatusTask(), 1000, 10000);
        }
    }

    /**
     * 行为验证（第一步注册）
     * @return GeetestLibResult
     */
    public GeetestLibResult registerGeeV3(String geeId, String userId, String clientType) {
        String secret = configMap.get(geeId);
        if (secret == null) {
            throw new BrokerException(BrokerErrorCode.RECAPTCHA_INVALID);
        }
        GeetestLib gtLib = new GeetestLib(geeId, secret);
        GeetestLibResult result;
        // 检测存入redis中的极验云状态标识
        if (geeTestStatusMap.getOrDefault(geeId, false)) {
            Map<String, String> paramMap = new HashMap<>();
            paramMap.put("digestmod", digestmodEnum.getName());
            paramMap.put("user_id", userId);
            paramMap.put("client_type", "web");
            result = gtLib.register(digestmodEnum, paramMap);
        } else {
            result = gtLib.localInit();
        }
        return result;
    }

    /**
     * 行为验证（第二步校验）
     *
     * @param geeId           极验id
     * @param challenge       v3注册时返回的唯一编号
     * @param captchaResponse 前端请求返回的核心数据
     */
    public void validGeeTestCaptcha(String geeId, String challenge, String captchaResponse) {
        String secret = configMap.get(geeId);
        if (secret == null || StringUtils.isEmpty(challenge)) {
            throw new BrokerException(BrokerErrorCode.RECAPTCHA_INVALID);
        }
        GeetestLib gtLib = new GeetestLib(geeId, secret);
        String secCode = captchaResponse + CODE_SUFFIX;
        GeetestLibResult result;
        // 检测存入redis中的极验云状态标识
        if (geeTestStatusMap.getOrDefault(geeId, false)) {
            result = gtLib.successValidate(challenge, captchaResponse, secCode, null);
        } else {
            result = gtLib.failValidate(challenge, captchaResponse, secCode);
        }
        // 注意，不要更改返回的结构和值类型
        if (result.getStatus() != GEE_TEST_SUCCESS_STATUS) {
            log.warn("GeeTest v3 fail status, please check！geeId={},status={},msg={}", geeId, result.getStatus(), result.getMsg());
            throw new BrokerException(BrokerErrorCode.RECAPTCHA_INVALID);
        }
    }

    public class CheckGeetestStatusTask extends TimerTask {
        public void run() {
            configMap.keySet().forEach(geeId -> {
                String geeTestStatus = "";
                try {
                    Map<String, String> paramMap = new HashMap<>();
                    paramMap.put("gt", geeId);
                    String resBody = HttpClientUtils.doGet(GT_STATUS_URL, paramMap);
                    JSONObject jsonObject = new JSONObject(resBody);
                    geeTestStatus = jsonObject.getString("status");
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if ("success".equals(geeTestStatus)) {
                    geeTestStatusMap.put(geeId, true);
                } else {
                    geeTestStatusMap.put(geeId, false);
                    log.warn("Get geetest status fail!{},{}", geeId, geeTestStatus);
                }
            });
        }
    }

}
