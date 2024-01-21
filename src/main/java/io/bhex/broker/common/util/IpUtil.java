/**********************************
 *@项目名称: broker-common
 *@文件名称: io.bhex.broker.common.util
 *@Date 2018/8/3
 *@Author peiwei.ren@bhex.io 
 *@Copyright（C）: 2018 BlueHelix Inc.   All rights reserved.
 *注意：本内容仅限于内部传阅，禁止外泄以及用于其他的商业目的。
 ***************************************/
package io.bhex.broker.common.util;

import com.google.common.base.Throwables;
import com.google.gson.JsonElement;
import com.google.gson.JsonSyntaxException;
import io.bhex.broker.common.exception.BrokerErrorCode;
import io.bhex.broker.common.exception.BrokerException;
import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.util.regex.Pattern;

@Slf4j
public class IpUtil {

    private static Pattern pattern = Pattern.compile("([1-9]|[1-9]\\d|1\\d{2}|2[0-4]\\d|25[0-5])(\\.(\\d|[1-9]\\d|1\\d{2}|2[0-4]\\d|25[0-5])){3}");

    private static final String QUERY_URL = "http://ip.taobao.com/service/getIpInfo.php?ip=";

    private static final OkHttpClient okHttpClient = new OkHttpClient.Builder().build();

    public static String getIpAddress(String ip) {
        Request request = new Request.Builder()
                .get()
                .url(QUERY_URL + ip)
                .build();
        try (Response response = okHttpClient.newCall(request).execute()) {
            if (response.isSuccessful()) {
                String dataStr = response.body().string();
                JsonElement json;
                try {
                    json = JsonUtil.defaultJsonParser().parse(dataStr);
                } catch (JsonSyntaxException e) {
                    throw new BrokerException(BrokerErrorCode.RECAPTCHA_INVALID, e);
                }
                Integer code = JsonUtil.getInt(json, ".code", -1);
                if (code != 0) {
                    log.error("QueryIPAddress request is failed, response body: " + dataStr);
                }
                //
                return "";
            } else {
                log.error("QueryIPAddress request is failed, invalid http code " + response.code());
            }
        } catch (IOException e) {
            log.error("QueryIPAddress request is failed, exception: " + Throwables.getStackTraceAsString(e));
        }
        return null;
    }

    public static boolean validIpV4(String ip) {
        return pattern.matcher(ip).matches();
    }

}
