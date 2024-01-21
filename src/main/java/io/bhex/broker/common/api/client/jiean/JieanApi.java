/**********************************
 *@项目名称: broker-common
 *@文件名称: io.bhex.broker.common.api.client
 *@Date 2018/10/30
 *@Author peiwei.ren@bhex.io 
 *@Copyright（C）: 2018 BlueHelix Inc.   All rights reserved.
 *注意：本内容仅限于内部传阅，禁止外泄以及用于其他的商业目的。
 ***************************************/
package io.bhex.broker.common.api.client.jiean;

import com.google.common.base.Charsets;
import com.google.common.base.Strings;
import com.google.common.hash.Hashing;
import com.google.gson.JsonObject;
import io.bhex.broker.common.api.client.okhttp.OkHttpPrometheusInterceptor;
import io.bhex.broker.common.exception.BrokerErrorCode;
import io.bhex.broker.common.exception.BrokerException;
import io.bhex.broker.common.util.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

@Slf4j
public class JieanApi {

    private static OkHttpClient okHttpClient
            = new OkHttpClient.Builder().addInterceptor(OkHttpPrometheusInterceptor.getInstance()).build();

    private static final String VERSION_ID = "01";
    private static final String CHARSET = Charsets.UTF_8.name();
    private static final String TRANSFER_TYPE = "STD_VERI";
    private static final String ID_CARD_VERIFY_PROD_ID = "CERT";

    private static final Integer CONN_TIMEOUT = 8000;
    private static final Integer READ_TIMEOUT = 8000;

    private JieanProperties jieanProperties;

    public JieanApi(JieanProperties jieanProperties) {
        this.jieanProperties = jieanProperties;
        OkHttpClient.Builder build = new OkHttpClient.Builder();
        if (jieanProperties.getConnTimeout() != null && jieanProperties.getConnTimeout() > 0) {
            build.connectTimeout(jieanProperties.getConnTimeout(), TimeUnit.MILLISECONDS);
        } else {
            build.connectTimeout(CONN_TIMEOUT, TimeUnit.MILLISECONDS);
        }
        if (jieanProperties.getReadTimeout() != null && jieanProperties.getReadTimeout() > 0) {
            build.readTimeout(jieanProperties.getReadTimeout(), TimeUnit.MILLISECONDS);
        } else {
            build.readTimeout(READ_TIMEOUT, TimeUnit.MILLISECONDS);
        }
        okHttpClient = build.addInterceptor(OkHttpPrometheusInterceptor.getInstance()).build();
    }

    public IDCardVerifyResponse idCardVerify(IDCardVerifyRequest request) {
        FormBody.Builder builder = new FormBody.Builder();
        StringBuilder macStrBuilder = new StringBuilder();
        builder.add("versionId", VERSION_ID);
        macStrBuilder.append(VERSION_ID);
        builder.add("chrSet", CHARSET);
        macStrBuilder.append(CHARSET);
        builder.add("custId", jieanProperties.getCustId());
        macStrBuilder.append(jieanProperties.getCustId());
        builder.add("ordId", request.getOrderId());
        macStrBuilder.append(request.getOrderId());
        builder.add("transType", TRANSFER_TYPE);
        macStrBuilder.append(TRANSFER_TYPE);
        if (!Strings.isNullOrEmpty(request.getBusiType())) {
            builder.add("busiType", request.getBusiType());
            macStrBuilder.append(request.getBusiType());
        }
        if (!Strings.isNullOrEmpty(request.getMerPriv())) {
            builder.add("merPriv", request.getMerPriv());
            macStrBuilder.append(request.getMerPriv());
        }
        if (!Strings.isNullOrEmpty(request.getRetUrl())) {
            builder.add("retUrl", request.getRetUrl());
            macStrBuilder.append(request.getRetUrl());
        }
        JsonObject jsonStr = new JsonObject();
        jsonStr.addProperty("CERT_ID", request.getIdCardId());
        jsonStr.addProperty("CERT_NAME", request.getIdCardName());
        jsonStr.addProperty("PROD_ID", ID_CARD_VERIFY_PROD_ID);
        builder.add("jsonStr", JsonUtil.defaultGson().toJson(jsonStr));
        macStrBuilder.append(JsonUtil.defaultGson().toJson(jsonStr));
        macStrBuilder.append(jieanProperties.getMacKey());
        builder.add("macStr", Hashing.md5().hashString(macStrBuilder, Charsets.UTF_8).toString().toUpperCase());

        Request okHttpRequest = new Request.Builder()
                .post(builder.build())
                .url(jieanProperties.getIdCardVerifyUrl())
                .build();
        IDCardVerifyResponse verifyResponse;
        try (Response response = okHttpClient.newCall(okHttpRequest).execute()) {
            if (response.isSuccessful()) {
                try {
                    JAXBContext context = JAXBContext.newInstance(IDCardVerifyResponse.class);
                    Unmarshaller unmarshaller = context.createUnmarshaller();
                    verifyResponse = (IDCardVerifyResponse) unmarshaller.unmarshal(response.body().byteStream());
                } catch (Exception e) {
                    log.error("idCardVerify parse response body failed", e);
                    throw new BrokerException(BrokerErrorCode.IDENTITY_AUTHENTICATION_FAILED, e);
                }
            } else {
                log.error("idCardVerify request not success, httpCode:{}" + response.code());
                throw new BrokerException(BrokerErrorCode.IDENTITY_AUTHENTICATION_FAILED);
            }
        } catch (IOException e) {
            log.error("idCardVerify request failed ");
            throw new BrokerException(BrokerErrorCode.IDENTITY_AUTHENTICATION_TIMEOUT, e);
        }
        return verifyResponse;
    }

    public BankCardVerifyResponse bankCardVerify(BankCardVerifyRequest request, BankCardVerifyType bankCardVerifyType) {
        FormBody.Builder builder = new FormBody.Builder();
        StringBuilder macStrBuilder = new StringBuilder();
        builder.add("versionId", VERSION_ID);
        macStrBuilder.append(VERSION_ID);
        builder.add("chrSet", CHARSET);
        macStrBuilder.append(CHARSET);
        builder.add("custId", jieanProperties.getCustId());
        macStrBuilder.append(jieanProperties.getCustId());
        builder.add("ordId", request.getOrderId());
        macStrBuilder.append(request.getOrderId());
        builder.add("transType", TRANSFER_TYPE);
        macStrBuilder.append(TRANSFER_TYPE);
        if (!Strings.isNullOrEmpty(request.getBusiType())) {
            builder.add("busiType", request.getBusiType());
            macStrBuilder.append(request.getBusiType());
        }
        if (!Strings.isNullOrEmpty(request.getMerPriv())) {
            builder.add("merPriv", request.getMerPriv());
            macStrBuilder.append(request.getMerPriv());
        }
        if (!Strings.isNullOrEmpty(request.getRetUrl())) {
            builder.add("retUrl", request.getRetUrl());
            macStrBuilder.append(request.getRetUrl());
        }
        JsonObject jsonStr = new JsonObject();
        jsonStr.addProperty("CARD_ID", request.getBankCardId());
        jsonStr.addProperty("CERT_NAME", request.getIdCardName());
        if (bankCardVerifyType == BankCardVerifyType.CARD_3) {
            jsonStr.addProperty("CERT_ID", request.getIdCardId());
        } else if (bankCardVerifyType == BankCardVerifyType.CARD_4) {
            jsonStr.addProperty("CERT_ID", request.getIdCardId());
            jsonStr.addProperty("MP", request.getMobile());
        }
        jsonStr.addProperty("PROD_ID", bankCardVerifyType.value());
        builder.add("jsonStr", JsonUtil.defaultGson().toJson(jsonStr));
        macStrBuilder.append(JsonUtil.defaultGson().toJson(jsonStr));
        macStrBuilder.append(jieanProperties.getMacKey());
        builder.add("macStr", Hashing.md5().hashString(macStrBuilder, Charsets.UTF_8).toString().toUpperCase());

        Request okHttpRequest = new Request.Builder()
                .post(builder.build())
                .url(jieanProperties.getBankCardVerifyUrl())
                .build();
        BankCardVerifyResponse verifyResponse;
        try (Response response = okHttpClient.newCall(okHttpRequest).execute()) {
            if (response.isSuccessful()) {
                try {
                    JAXBContext context = JAXBContext.newInstance(BankCardVerifyResponse.class);
                    Unmarshaller unmarshaller = context.createUnmarshaller();
                    verifyResponse = (BankCardVerifyResponse) unmarshaller.unmarshal(response.body().byteStream());
                } catch (Exception e) {
                    log.error("bankCardVerify parse response body failed", e);
                    throw new BrokerException(BrokerErrorCode.IDENTITY_AUTHENTICATION_FAILED, e);
                }
            } else {
                log.error("bankCardVerify request not success, httpCode:{}" + response.code());
                throw new BrokerException(BrokerErrorCode.IDENTITY_AUTHENTICATION_FAILED);
            }
        } catch (IOException e) {
            log.error("bankCardVerify request failed ");
            throw new BrokerException(BrokerErrorCode.IDENTITY_AUTHENTICATION_TIMEOUT, e);
        }
        return verifyResponse;
    }

    public static void main(String[] args) throws Exception {
        JieanProperties properties = new JieanProperties();
        properties.setCustId("6000003074");
        properties.setMacKey("308a00a23a2b6f1322b6936ae3bb1536");
        properties.setIdCardVerifyUrl("http://api.jieandata.cn/vpre/ccmn/verify");
        properties.setBankCardVerifyUrl("http://api.jieandata.cn/vpre/ccmn/verify");
        JieanApi jieanApi = new JieanApi(properties);
        IDCardVerifyRequest request = IDCardVerifyRequest.builder()
                .orderId(String.valueOf(System.currentTimeMillis()))
                .idCardId("1010101010101010")
                .idCardName("李海杰")
                .build();
        IDCardVerifyResponse response = jieanApi.idCardVerify(request);
        if (response != null) {
            System.out.println(JsonUtil.defaultGson().toJson(response));
        }

//        BankCardVerifyRequest bankCardVerifyRequest = BankCardVerifyRequest.builder()
//                .orderId(String.valueOf(System.currentTimeMillis()))
//                .bankCardId("xxxxx")
//                .idCardName("任培伟")
//                .idCardId("xxxxx")
//                .mobile("18600391314")
//                .build();
//        BankCardVerifyResponse bankCardVerifyResponse = jieanApi.bankCardVerify(bankCardVerifyRequest, BankCardVerifyType.CARD_4);
//        if (bankCardVerifyResponse != null) {
//            System.out.println(JsonUtil.defaultGson().toJson(bankCardVerifyResponse));
//        }
    }

}
