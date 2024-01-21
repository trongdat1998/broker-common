/**********************************
 *@项目名称: broker-parent
 *@文件名称: io.bhex.broker.common.objectstorage
 *@Date 2018/7/6
 *@Author peiwei.ren@bhex.io 
 *@Copyright（C）: 2018 BlueHelix Inc.   All rights reserved.
 *注意：本内容仅限于内部传阅，禁止外泄以及用于其他的商业目的。
 ***************************************/
package io.bhex.broker.common.objectstorage;

import com.google.common.collect.Lists;

import java.util.List;

public class QCloudObjectStorageProperties {

    private List<String> urlPrefixList = Lists.newArrayList();
    private String bucket;
    private String appId;
    private String accessKey;
    private String secretKey;
    private String regionName;

    public List<String> getUrlPrefixList() {
        return urlPrefixList;
    }

    public void setUrlPrefixList(List<String> urlPrefixList) {
        this.urlPrefixList = urlPrefixList;
    }

    public String getBucket() {
        return bucket;
    }

    public void setBucket(String bucket) {
        this.bucket = bucket;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getAccessKey() {
        return accessKey;
    }

    public void setAccessKey(String accessKey) {
        this.accessKey = accessKey;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    public String getRegionName() {
        return regionName;
    }

    public void setRegionName(String regionName) {
        this.regionName = regionName;
    }

    @Override
    public String toString() {
        return "QCloudObjectStorageProperties{" +
                "urlPrefixList=" + urlPrefixList +
                ", bucket='" + bucket + '\'' +
                ", appId='" + appId + '\'' +
                ", accessKey='" + accessKey + '\'' +
                ", secretKey='" + secretKey + '\'' +
                ", regionName='" + regionName + '\'' +
                '}';
    }

}
