/**********************************
 *@项目名称: broker-common
 *@文件名称: io.bhex.broker.common.api.client
 *@Date 2018/10/30
 *@Author peiwei.ren@bhex.io 
 *@Copyright（C）: 2018 BlueHelix Inc.   All rights reserved.
 *注意：本内容仅限于内部传阅，禁止外泄以及用于其他的商业目的。
 ***************************************/
package io.bhex.broker.common.api.client.geetest;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import io.bhex.broker.common.api.client.okhttp.OkHttpPrometheusInterceptor;
import io.bhex.broker.common.exception.BrokerErrorCode;
import io.bhex.broker.common.exception.BrokerException;
import lombok.extern.slf4j.Slf4j;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

@Slf4j
public class DkGeeTestApi {

    private static OkHttpClient okHttpClient
            = new OkHttpClient.Builder().addInterceptor(OkHttpPrometheusInterceptor.getInstance()).build();

    private static final Integer GEE_TEST_SUCCESS_STATUS = 1;

    private static final Integer GEE_TEST_CRASH_STATUS = 99;

    private static final Boolean DEFAULT_CRASH_CONTINUE = false;

    private static final Boolean DEFAULT_FILTER_HIGH_RISK_REQUEST = false;

    private static final Integer CONN_TIMEOUT = 8000;

    private static final Integer READ_TIMEOUT = 8000;

    private int highRiskLevel = 7;

    private final ImmutableMap<String, String> configMap;

    private final String geeTestUrl;

    private final boolean crashContinue;

    private final boolean filterHighRiskRequest;

    private final MediaType mediaType = MediaType.parse("text/x-markdown; charset=utf-8");

    public DkGeeTestApi(GeeTestProperties geeTestProperties) {
        List<GeeTestConfig> configList = geeTestProperties.getConfigs();
        Map<String, String> tmpConfigMap = Maps.newHashMap();
        for (GeeTestConfig geeTestConfig : configList) {
            tmpConfigMap.put(geeTestConfig.getId(), geeTestConfig.getPrivateKey());
        }
        configMap = ImmutableMap.copyOf(tmpConfigMap);
        geeTestUrl = geeTestProperties.getGeeTestUrl();
        if (geeTestProperties.getCrashContinue() == null) {
            crashContinue = DEFAULT_CRASH_CONTINUE;
        } else {
            crashContinue = geeTestProperties.getCrashContinue();
        }
        if (geeTestProperties.getHighRiskLevel() != null && geeTestProperties.getHighRiskLevel() > 0) {
            highRiskLevel = geeTestProperties.getHighRiskLevel();
        }
        if (geeTestProperties.getFilterHighRiskRequest() == null) {
            filterHighRiskRequest = DEFAULT_FILTER_HIGH_RISK_REQUEST;
        } else {
            filterHighRiskRequest = geeTestProperties.getFilterHighRiskRequest();
        }
        OkHttpClient.Builder build = new OkHttpClient.Builder();
        if (geeTestProperties.getConnTimeout() != null && geeTestProperties.getConnTimeout() > 0) {
            build.connectTimeout(geeTestProperties.getConnTimeout(), TimeUnit.MILLISECONDS);
        } else {
            build.connectTimeout(CONN_TIMEOUT, TimeUnit.MILLISECONDS);
        }
        if (geeTestProperties.getReadTimeout() != null && geeTestProperties.getReadTimeout() > 0) {
            build.readTimeout(geeTestProperties.getReadTimeout(), TimeUnit.MILLISECONDS);
        } else {
            build.readTimeout(READ_TIMEOUT, TimeUnit.MILLISECONDS);
        }
        okHttpClient = build.addInterceptor(OkHttpPrometheusInterceptor.getInstance()).build();
    }

    public DkGeeTestVerifyResponse geeTest(DkGeeTestVerifyRequest geeTestVerifyRequest) {
        Request request = getGeeTestRequest(geeTestVerifyRequest);
        try (Response response = okHttpClient.newCall(request).execute()) {
            if (response.isSuccessful() && response.body() != null) {
                String dataStr = response.body().string();
                try {
                    return new Gson().fromJson(dataStr, DkGeeTestVerifyResponse.class);
                } catch (Exception e) {
                    log.error("GeeTest parse response({}) error", dataStr, e);
                    throw new BrokerException(BrokerErrorCode.RECAPTCHA_INVALID, e);
                }
            } else {
                log.error("recaptcha valid request is failed, invalid http code " + response.code() + ", return true");
                return DkGeeTestVerifyResponse.builder()
                        .status(GEE_TEST_SUCCESS_STATUS)
                        .build();
            }
        } catch (IOException e) {
            log.error("GeeTest invoke timeout");
            throw new BrokerException(BrokerErrorCode.RECAPTCHA_INVALID);
        }
    }

    public void validGeeTestCaptcha(DkGeeTestVerifyRequest geeTestVerifyRequest) {
        Request request = getGeeTestRequest(geeTestVerifyRequest);
        try (Response response = okHttpClient.newCall(request).execute()) {
            if (response.isSuccessful() && response.body() != null) {
                String dataStr = response.body().string();
                DkGeeTestVerifyResponse dkGeeTestVerifyResponse;
                try {
                    dkGeeTestVerifyResponse = new Gson().fromJson(dataStr, DkGeeTestVerifyResponse.class);
                } catch (JsonSyntaxException e) {
                    log.error("GeeTest parse response({}) error", dataStr, e);
                    throw new BrokerException(BrokerErrorCode.RECAPTCHA_INVALID, e);
                }
                Integer status = dkGeeTestVerifyResponse.getStatus();
                if (Objects.equals(status, GEE_TEST_SUCCESS_STATUS)) {
                    int riskLevel = dkGeeTestVerifyResponse.getRiskLevel();
                    if (riskLevel > 0) {
                        log.warn("GeeTest valid is success and riskLevel is {}", riskLevel);
                        if (riskLevel >= highRiskLevel) {
                            log.warn("[ALERT] GeeTest valid response({}) a high level:{} with request:{}, user_id:{}, please check",
                                    dataStr, riskLevel, geeTestVerifyRequest.getId(), geeTestVerifyRequest.getUserId());
                            if (filterHighRiskRequest) {
                                throw new BrokerException(BrokerErrorCode.RECAPTCHA_INVALID);
                            }
                        }
                    }
                } else {
                    log.warn("GeeTest valid request is failed, response body: {}", dataStr);
                    if (status.equals(GEE_TEST_CRASH_STATUS)) {
                        log.error("GeeTest valid return Crash status, please check");
                        if (crashContinue) {
                            return;
                        }
                        throw new BrokerException(BrokerErrorCode.RECAPTCHA_INVALID);
                    }
                    throw new BrokerException(BrokerErrorCode.RECAPTCHA_INVALID);
                }
            } else {
                log.error("recaptcha valid request is failed, invalid http code " + response.code() + ", return true");
            }
        } catch (IOException e) {
            throw new BrokerException(BrokerErrorCode.RECAPTCHA_INVALID);
        }
    }

    private Request getGeeTestRequest(DkGeeTestVerifyRequest geeTestVerifyRequest) {
        String secret = configMap.get(geeTestVerifyRequest.getId());
        if (secret == null) {
            throw new BrokerException(BrokerErrorCode.RECAPTCHA_INVALID);
        }
        Algorithm algorithm = Algorithm.HMAC256(secret);
        JWTCreator.Builder builder = JWT.create()
                .withClaim("session_id", geeTestVerifyRequest.getSessionId())
                .withClaim("user_id", geeTestVerifyRequest.getUserId())
                .withClaim("scene", geeTestVerifyRequest.getScene());
        String sign = builder.sign(algorithm);
        return new Request.Builder()
                .url(geeTestUrl)
                .post(RequestBody.create(sign, mediaType))
                .build();
    }

    public static void main(String[] args) {
        GeeTestConfig testConfig = new GeeTestConfig();
        testConfig.setId("YYYYYYY");
        testConfig.setPrivateKey("XXXXXXXXXXX");

        GeeTestProperties geeTestProperties = new GeeTestProperties();
        geeTestProperties.setConfigs(Lists.newArrayList(testConfig));
        geeTestProperties.setGeeTestUrl("https://dkapi.geetest.com/deepknow/v2/verify");
        DkGeeTestApi api = new DkGeeTestApi(geeTestProperties);
        DkGeeTestVerifyRequest request = DkGeeTestVerifyRequest.builder()
                .id("YYYYY")
                .userId("110")
                .sessionId("5fe811cd-3bf3-463e-a849-dbe01c7b03a9")
                .scene(SceneEnum.BUY.getName())
                .build();
        api.validGeeTestCaptcha(request);
    }

}
