package io.bhex.broker.common.api.client.tencent;

import com.alibaba.fastjson.JSONObject;
import com.google.api.client.json.Json;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import io.bhex.broker.common.api.client.okhttp.OkHttpPrometheusInterceptor;
import io.bhex.broker.common.exception.BrokerErrorCode;
import io.bhex.broker.common.exception.BrokerException;
import io.bhex.broker.common.util.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * @author JinYuYuan
 * @description
 * @date 2020-10-16 10:18
 */
@Slf4j
public class TencentApi {
    private static OkHttpClient okHttpClient
            = new OkHttpClient.Builder().addInterceptor(OkHttpPrometheusInterceptor.getInstance()).build();

    private static final Integer CONN_TIMEOUT = 8000;
    private static final Integer READ_TIMEOUT = 8000;

    private TencentProperties tencentProperties;

    public TencentApi(TencentProperties tencentProperties) {
        this.tencentProperties = tencentProperties;

        OkHttpClient.Builder build = new OkHttpClient.Builder();
        build.connectTimeout(CONN_TIMEOUT, TimeUnit.MILLISECONDS);
        build.readTimeout(READ_TIMEOUT, TimeUnit.MILLISECONDS);
        okHttpClient = build.addInterceptor(OkHttpPrometheusInterceptor.getInstance()).build();

    }

    public  String calcAuthorization(String source, String secretId, String secretKey, String datetime)
            throws NoSuchAlgorithmException, UnsupportedEncodingException, InvalidKeyException {
        String signStr = "x-date: " + datetime + "\n" + "x-source: " + source;
        Mac mac = Mac.getInstance("HmacSHA1");
        Key sKey = new SecretKeySpec(secretKey.getBytes("UTF-8"), mac.getAlgorithm());
        mac.init(sKey);
        byte[] hash = mac.doFinal(signStr.getBytes("UTF-8"));
        String sig = Base64.getEncoder().encodeToString(hash);

        String auth = "hmac id=\"" + secretId + "\", algorithm=\"hmac-sha1\", headers=\"x-date x-source\", signature=\"" + sig + "\"";
        return auth;
    }
    public static String urlencode(Map<?, ?> map) throws UnsupportedEncodingException {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<?, ?> entry : map.entrySet()) {
            if (sb.length() > 0) {
                sb.append("&");
            }
            sb.append(String.format("%s=%s",
                    URLEncoder.encode(entry.getKey().toString(), "UTF-8"),
                    URLEncoder.encode(entry.getValue().toString(), "UTF-8")
            ));
        }
        return sb.toString();
    }
    public TencentIDCardVerifyResponse idCardVerify(TencentIDCardVerifyRequest request){
        Calendar cd = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss 'GMT'", Locale.US);
        sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
        String datetime = sdf.format(cd.getTime());
        // 签名
        String auth = null;
        try {
            auth = calcAuthorization(tencentProperties.getSource(), tencentProperties.getSecretId(), tencentProperties.getSecretKey(), datetime);
        }catch (Exception e){
            log.error("idCardVerify calcAuthorization failed", e);
            throw new BrokerException(BrokerErrorCode.IDENTITY_AUTHENTICATION_FAILED, e);
        }
        // 请求方法
        Map<String,String> params = new HashMap<>();
        params.put("certcode",request.getCertcode());
        params.put("name",request.getName());
        String payload = null;
        try{
            payload = urlencode(params);
        }catch (Exception e){
            log.error("idCardVerify urlencode failed", e);
            throw new BrokerException(BrokerErrorCode.IDENTITY_AUTHENTICATION_FAILED, e);
        }

        try{
            Request okhttpRequest = new Request.Builder().
                    addHeader("X-Source", tencentProperties.getSource()).
                    addHeader("X-Date", datetime).
                    addHeader("Authorization",auth).
                    get().
                    url(tencentProperties.getIdCardVerifyUrl()+"?"+payload).
                    build();
            Call call = okHttpClient.newCall(okhttpRequest);
            Response response = call.execute();
            String result = response.body().string();
            JsonObject json;
            try {
                json =JsonUtil.defaultGson().fromJson(result,JsonElement.class).getAsJsonObject();
            } catch (JsonSyntaxException e) {
                log.error("tencent parse response({}) to json error", result, e);
                throw new BrokerException(BrokerErrorCode.IDENTITY_AUTHENTICATION_FAILED, e);
            }
            Integer code = json.get("code").getAsInt();
            String message = json.get("message").getAsString();
            JsonObject data = json.get("data").getAsJsonObject();
            TencentIDCardVerifyResponse.Builder verifyResponse = TencentIDCardVerifyResponse.builder();
            verifyResponse.code(code);
            verifyResponse.message(message);
            if(code == 0){
                verifyResponse.result(data.get("result").getAsInt());
                verifyResponse.remark(data.get("remark").getAsString());
            }else{
                verifyResponse.result(-1);
                verifyResponse.remark(message);
            }
            return verifyResponse.build();
        } catch (IOException e) {
            log.error("tencent idCardVerify request failed ");
            throw new BrokerException(BrokerErrorCode.IDENTITY_AUTHENTICATION_TIMEOUT, e);
        }


    }
}
