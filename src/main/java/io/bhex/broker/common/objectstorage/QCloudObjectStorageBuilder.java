/**********************************
 *@项目名称: broker-parent
 *@文件名称: io.bhex.broker.common.objectstorage
 *@Date 2018/7/6
 *@Author peiwei.ren@bhex.io 
 *@Copyright（C）: 2018 BlueHelix Inc.   All rights reserved.
 *注意：本内容仅限于内部传阅，禁止外泄以及用于其他的商业目的。
 ***************************************/
package io.bhex.broker.common.objectstorage;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;

import java.util.List;

public class QCloudObjectStorageBuilder {

    private List<String> urlPrefixList;
    private String bucket;
    private String appId;
    private String accessKey;
    private String secretKey;
    private String regionName;

    QCloudObjectStorageBuilder() {
    }

    public List<String> getUrlPrefixList() {
        return urlPrefixList;
    }

    public QCloudObjectStorageBuilder setUrlPrefixList(List<String> urlPrefixList) {
        if (urlPrefixList == null) {
            urlPrefixList = Lists.newArrayList();
        }
        // Preconditions.checkArgument(urlPrefixList != null && !urlPrefixList.isEmpty(), "urlPrefixList is empty");
        this.urlPrefixList = urlPrefixList;
        return this;
    }

    public QCloudObjectStorageBuilder addUrlPrefix(String urlPrefix) {
        Preconditions.checkArgument(urlPrefix != null && !urlPrefix.isEmpty(), "urlPrefix is empty");
        if (this.urlPrefixList == null) {
            this.urlPrefixList = Lists.newArrayList();
        }
        this.urlPrefixList.add(urlPrefix);
        return this;
    }

    public String getBucket() {
        return bucket;
    }

    public QCloudObjectStorageBuilder setBucket(String bucket) {
        Preconditions.checkArgument(bucket != null && !bucket.isEmpty(), "bucket is empty");
        this.bucket = bucket;
        return this;
    }

    public String getAppId() {
        return appId;
    }

    public QCloudObjectStorageBuilder setAppId(String appId) {
        Preconditions.checkArgument(appId != null && !appId.isEmpty(), "appId is empty");
        this.appId = appId;
        return this;
    }

    public String getAccessKey() {
        return accessKey;
    }

    public QCloudObjectStorageBuilder setAccessKey(String accessKey) {
        Preconditions.checkArgument(accessKey != null && !accessKey.isEmpty(), "accessKey is empty");
        this.accessKey = accessKey;
        return this;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public QCloudObjectStorageBuilder setSecretKey(String secretKey) {
        Preconditions.checkArgument(secretKey != null && !secretKey.isEmpty(), "secretKey is empty");
        this.secretKey = secretKey;
        return this;
    }

    public String getRegionName() {
        return regionName;
    }

    public QCloudObjectStorageBuilder setRegionName(String regionName) {
        Preconditions.checkArgument(regionName != null && !regionName.isEmpty(), "regionName is empty");
        this.regionName = regionName;
        return this;
    }

    public QCloudObjectStorage build() {
        return new QCloudObjectStorage(
                this.urlPrefixList,
                this.bucket,
                this.appId,
                this.accessKey,
                this.secretKey,
                this.regionName);
    }

}
