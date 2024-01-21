/**********************************
 *@项目名称: broker-common
 *@文件名称: io.bhex.broker.common.api.client
 *@Date 2018/10/30
 *@Author peiwei.ren@bhex.io 
 *@Copyright（C）: 2018 BlueHelix Inc.   All rights reserved.
 *注意：本内容仅限于内部传阅，禁止外泄以及用于其他的商业目的。
 ***************************************/
package io.bhex.broker.common.api.client.recaptcha;

import com.google.gson.JsonElement;
import com.google.gson.JsonSyntaxException;
import io.bhex.broker.common.exception.BrokerErrorCode;
import io.bhex.broker.common.exception.BrokerException;
import io.bhex.broker.common.util.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;

@Slf4j
public class GoogleRecaptchaApi {

    private static final OkHttpClient okHttpClient = new OkHttpClient.Builder().build();

    private GoogleRecaptchaProperties googleRecaptchaProperties;

    public GoogleRecaptchaApi(GoogleRecaptchaProperties googleRecaptchaProperties) {
        this.googleRecaptchaProperties = googleRecaptchaProperties;
    }

    public void validGoogleReCaptchaResponse(GoogleRecaptchaRequest recaptchaRequest) {
        Request request = new Request.Builder()
                .post(new FormBody.Builder()
                        .add("secret", googleRecaptchaProperties.getSecurityKey())
                        .add("response", recaptchaRequest.getRecaptchaResponse())
                        .add("remoteip", recaptchaRequest.getRemoteIp())
                        .build())
                .url(googleRecaptchaProperties.getRecaptchaUrl())
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
                Boolean validResult = JsonUtil.getBoolean(json, ".success", false);
                if (!validResult) {
                    log.error("recaptcha valid request is failed, response body: " + dataStr);
                    throw new BrokerException(BrokerErrorCode.RECAPTCHA_INVALID);
                }
            } else {
                log.error("recaptcha valid request is failed, invalid http code " + response.code());
                throw new BrokerException(BrokerErrorCode.RECAPTCHA_INVALID);
            }
        } catch (IOException e) {
            throw new BrokerException(BrokerErrorCode.RECAPTCHA_INVALID);
        }
    }

    public static void main(String[] args) {
        GoogleRecaptchaProperties googleRecaptchaProperties = new GoogleRecaptchaProperties();
        googleRecaptchaProperties.setSecurityKey("6LcgnmQUAAAAANrvC_D5TNC-1NOBxXrQS2zfrMy2");
        googleRecaptchaProperties.setRecaptchaUrl("https://recaptcha.net/recaptcha/api/siteverify");
        GoogleRecaptchaApi googleRecaptchaApi = new GoogleRecaptchaApi(googleRecaptchaProperties);
        googleRecaptchaApi.validGoogleReCaptchaResponse(GoogleRecaptchaRequest.builder()
                .recaptchaResponse("2314234")
                .remoteIp("127.0.0.1")
                .build());
    }

}
