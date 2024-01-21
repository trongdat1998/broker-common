/**********************************
 *@项目名称: broker-common
 *@文件名称: io.bhex.broker.common.api.client.alibaba
 *@Date 2019/1/14
 *@Author peiwei.ren@bhex.io 
 *@Copyright（C）: 2018 BlueHelix Inc.   All rights reserved.
 *注意：本内容仅限于内部传阅，禁止外泄以及用于其他的商业目的。
 ***************************************/
package io.bhex.broker.common.api.client.alibaba;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.afs.model.v20180112.AuthenticateSigRequest;
import com.aliyuncs.afs.model.v20180112.AuthenticateSigResponse;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import io.bhex.broker.common.exception.BrokerErrorCode;
import io.bhex.broker.common.exception.BrokerException;
import io.bhex.broker.common.util.JsonUtil;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AliSigCheckApi {

    private static final int SUCCESS_CODE = 100;

    private IAcsClient client;

    private AliSigCheckProperties aliSigCheckProperties;

    public AliSigCheckApi(AliSigCheckProperties aliSigCheckProperties) throws Exception {
        this.aliSigCheckProperties = aliSigCheckProperties;

        String regionId = "cn-hangzhou";
        String accessKeyId = aliSigCheckProperties.getAccesskeyId();
        String accessKeySecret = aliSigCheckProperties.getAccessKeySecret();

        // Create a new IClientProfile instance
        IClientProfile profile = DefaultProfile.getProfile(regionId, accessKeyId, accessKeySecret);
        client = new DefaultAcsClient(profile);

        DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", "afs", "afs.aliyuncs.com");
    }

    public void sigCheck(AliSigCheckRequest aliSigCheckRequest) {
        AuthenticateSigRequest request = new AuthenticateSigRequest();
        request.setSessionId(aliSigCheckRequest.getSessionId());
        request.setSig(aliSigCheckRequest.getSig());
        request.setToken(aliSigCheckRequest.getToken());
        request.setScene(aliSigCheckRequest.getScene());
        request.setAppKey(aliSigCheckRequest.getAppKey());
        request.setRemoteIp(aliSigCheckRequest.getRemoteIp());

        try {
            AuthenticateSigResponse response = client.getAcsResponse(request);
            if (response.getCode() != SUCCESS_CODE) {
                log.error("AliSigCheck valid request is failed, response: {}", JsonUtil.defaultGson().toJson(response));
                throw new BrokerException(BrokerErrorCode.RECAPTCHA_INVALID);
            }
        } catch (Exception e) {
            log.error("AliSigCheck valid request is failed, error: {}", e);
            throw new BrokerException(BrokerErrorCode.RECAPTCHA_INVALID);
        }
    }

}
