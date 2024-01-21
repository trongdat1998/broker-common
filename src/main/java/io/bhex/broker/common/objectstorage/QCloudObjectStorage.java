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
import com.google.common.base.Strings;
import com.google.common.base.Throwables;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.google.common.io.ByteStreams;
import com.google.common.net.MediaType;
import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.exception.CosClientException;
import com.qcloud.cos.exception.CosServiceException;
import com.qcloud.cos.model.*;
import com.qcloud.cos.region.Region;

import java.io.*;
import java.net.URL;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class QCloudObjectStorage implements ObjectStorage {

    private final ImmutableList<String> urlPrefixList;
    private final String bucket;
    private final COSClient cosClient;

    public QCloudObjectStorage(List<String> urlPrefixList, String bucket, String appId, String accessKey,
                               String secretKey, String regionName) {
        // Preconditions.checkArgument(urlPrefixList != null &&
        // !urlPrefixList.isEmpty(), "urlPrefixList is empty");
        Preconditions.checkArgument(bucket != null && !bucket.isEmpty(), "bucket is empty");
        Preconditions.checkArgument(appId != null && !appId.isEmpty(), "appId is empty");
        Preconditions.checkArgument(accessKey != null && !accessKey.isEmpty(), "accessKey is empty");
        Preconditions.checkArgument(secretKey != null && !secretKey.isEmpty(), "secretKey is empty");
        Preconditions.checkArgument(regionName != null && !regionName.isEmpty(), "regionName is empty");
        this.urlPrefixList = ImmutableList.copyOf(urlPrefixList);
        this.bucket = bucket;
        COSCredentials cosCredentials = new BasicCOSCredentials(appId, accessKey, secretKey);
        ClientConfig clientConfig = new ClientConfig(new Region(regionName));
        this.cosClient = new COSClient(cosCredentials, clientConfig);
    }

    @Override
    public ImmutableList<String> urlPrefixList() {
        return this.urlPrefixList;
    }

    @Override
    public void uploadObject(String key, MediaType contentType, File file) throws ObjectStorageException {
        this.uploadObject(this.bucket, key, contentType, file, null);
    }

    @Override
    public void uploadObject(String bucketName, String key, MediaType contentType, File file) throws ObjectStorageException {
        this.uploadObject(bucketName, key, contentType, file, null);
    }

    @Override
    public void uploadObject(String key, MediaType contentType, File file, CannedAccessControlList accessControl) throws ObjectStorageException {
        this.uploadObject(this.bucket, key, contentType, file, accessControl);
    }

    @Override
    public void uploadObject(String bucketName, String key, MediaType contentType, File file, CannedAccessControlList accessControl) throws ObjectStorageException {
        Preconditions.checkArgument(bucketName != null && !bucketName.isEmpty(), "bucketName is empty");
        Preconditions.checkArgument(key != null && !key.isEmpty(), "key is empty");
        Preconditions.checkNotNull(contentType, "contentType is empty");
        Preconditions.checkNotNull(file, "file is null");
        try {
            com.qcloud.cos.model.ObjectMetadata metadata = new com.qcloud.cos.model.ObjectMetadata();
            metadata.setContentType(contentType.toString().replace(" ", "")); // 腾讯云带空格的contentType解析会有问题。把空格去掉
            metadata.setContentLength(file.length());
            PutObjectRequest request = new PutObjectRequest(bucketName, key, file);
            if (accessControl != null) {
                metadata.setHeader("x-amz-acl", accessControl.toString());
                request.withCannedAcl(com.qcloud.cos.model.CannedAccessControlList.valueOf(accessControl.name()));
            }
            this.cosClient.putObject(request.withMetadata(metadata));
        } catch (CosClientException e) {
            throw new ObjectStorageException(e);
        } catch (Throwable th) {
            Throwables.throwIfUnchecked(th);
            throw new ObjectStorageException(th);
        }
    }

    @Override
    public void uploadObject(String key, MediaType contentType, byte[] data) throws ObjectStorageException {
        this.uploadObject(this.bucket, key, contentType, data, null);
    }

    @Override
    public void uploadObject(String bucketName, String key, MediaType contentType, byte[] data) throws ObjectStorageException {
        this.uploadObject(bucketName, key, contentType, data, null);
    }

    @Override
    public void uploadObject(String key, MediaType contentType, byte[] data, CannedAccessControlList accessControl) throws ObjectStorageException {
        this.uploadObject(this.bucket, key, contentType, data, accessControl);
    }

    @Override
    public void uploadObject(String bucketName, String key, MediaType contentType, byte[] data, CannedAccessControlList accessControl) throws ObjectStorageException {
        Preconditions.checkArgument(bucketName != null && !bucketName.isEmpty(), "bucketName is empty");
        Preconditions.checkArgument(key != null && !key.isEmpty(), "key is empty");
        Preconditions.checkNotNull(contentType, "contentType is empty");
        Preconditions.checkNotNull(data, "data is empty");
        try {
            com.qcloud.cos.model.ObjectMetadata metadata = new com.qcloud.cos.model.ObjectMetadata();
            metadata.setContentType(contentType.toString().replace(" ", "")); // 腾讯云带空格的contentType解析会有问题。把空格去掉
            metadata.setContentLength(data.length);
            this.cosClient.putObject(bucketName, key, new ByteArrayInputStream(data), metadata);
        } catch (CosClientException e) {
            throw new ObjectStorageException(e);
        } catch (Throwable th) {
            Throwables.throwIfUnchecked(th);
            throw new ObjectStorageException(th);
        }
    }

    @Override
    public void uploadObject(String key, MediaType contentType, InputStream inputStream) throws ObjectStorageException {
        this.uploadObject(this.bucket, key, contentType, inputStream, null);
    }

    @Override
    public void uploadObject(String bucketName, String key, MediaType contentType, InputStream inputStream) throws ObjectStorageException {
        this.uploadObject(bucketName, key, contentType, inputStream, null);
    }

    @Override
    public void uploadObject(String key, MediaType contentType, InputStream inputStream, CannedAccessControlList accessControl) throws ObjectStorageException {
        this.uploadObject(this.bucket, key, contentType, inputStream, accessControl);
    }

    @Override
    public void uploadObjectWithCacheControl(String key, MediaType contentType, InputStream inputStream, CannedAccessControlList accessControl, String cacheControl) throws ObjectStorageException {
        this.uploadObject(this.bucket, key, contentType, inputStream, accessControl, cacheControl);
    }

    public void uploadObject(String bucketName, String key, MediaType contentType, InputStream inputStream, CannedAccessControlList accessControl, String cacheControl) throws ObjectStorageException {
        Preconditions.checkArgument(!Strings.isNullOrEmpty(bucketName), "bucketName is empty");
        Preconditions.checkArgument(key != null && !key.isEmpty(), "key is empty");
        Preconditions.checkNotNull(contentType, "contentType is empty");
        Preconditions.checkNotNull(inputStream, "inputStream is empty");
        try {
            com.qcloud.cos.model.ObjectMetadata metadata = new com.qcloud.cos.model.ObjectMetadata();
            metadata.setContentType(contentType.toString().replace(" ", "")); // 腾讯云带空格的contentType解析会有问题。把空格去掉
            if (accessControl != null) {
                metadata.setHeader("x-amz-acl", accessControl.toString());
            }
            if (cacheControl != null && !cacheControl.trim().equals("")) {
                metadata.setCacheControl(cacheControl);
            }
            this.cosClient.putObject(this.bucket, key, inputStream, metadata);
        } catch (CosClientException e) {
            throw new ObjectStorageException(e);
        } catch (Throwable th) {
            Throwables.throwIfUnchecked(th);
            throw new ObjectStorageException(th);
        }
    }

    @Override
    public void uploadObject(String bucketName, String key, MediaType contentType, InputStream inputStream, CannedAccessControlList accessControl) throws ObjectStorageException {
        this.uploadObject(this.bucket, key, contentType, inputStream, accessControl, "");
    }

    @Override
    public URL generatePresignUrl(String key, Date expiration) throws ObjectStorageException {
        return generatePresignUrl(this.bucket, key, expiration);
    }

    @Override
    public URL generatePresignUrl(String bucketName, String key, Date expiration) throws ObjectStorageException {
        Preconditions.checkArgument(!Strings.isNullOrEmpty(bucketName), "bucketName is empty");
        Preconditions.checkArgument(!Strings.isNullOrEmpty(key), "contentType is empty");
        Preconditions.checkArgument(expiration != null && expiration.after(new Date()), "expiration is invalid");
        try {
            return this.cosClient.generatePresignedUrl(bucketName, key, expiration);
        } catch (CosClientException e) {
            throw new ObjectStorageException(e);
        } catch (Throwable th) {
            Throwables.throwIfUnchecked(th);
            throw new ObjectStorageException(th);
        }
    }

    @Override
    public void downloadObject(String key, File file) throws ObjectStorageException {
        downloadObject(this.bucket, key, file);
    }

    @Override
    public void downloadObject(String bucketName, String key, File file) throws ObjectStorageException {
        Preconditions.checkArgument(!Strings.isNullOrEmpty(bucketName), "bucketName is empty");
        Preconditions.checkArgument(key != null && !key.isEmpty(), "key is empty");
        Preconditions.checkNotNull(file, "file is null");
        try {
            COSObject cosObject = this.cosClient.getObject(bucketName, key);
            try (OutputStream outputStream = new FileOutputStream(file)) {
                ByteStreams.copy(cosObject.getObjectContent(), outputStream);
            }
        } catch (CosServiceException e) {
            if (e.getStatusCode() == 404) {
                throw new ObjectStorageNotFoundException("key not found : " + key, e);
            } else {
                throw new ObjectStorageException(e);
            }
        } catch (CosClientException e) {
            throw new ObjectStorageException(e);
        } catch (Throwable th) {
            Throwables.throwIfUnchecked(th);
            throw new ObjectStorageException(th);
        }
    }

    @Override
    public byte[] downloadObject(String key) throws ObjectStorageException {
        return downloadObject(this.bucket, key);
    }

    @Override
    public byte[] downloadObject(String bucketName, String key) throws ObjectStorageException {
        Preconditions.checkArgument(!Strings.isNullOrEmpty(bucketName), "bucketName is empty");
        Preconditions.checkArgument(key != null && !key.isEmpty(), "key is empty");
        try {
            COSObject cosObject = this.cosClient.getObject(bucketName, key);
            return ByteStreams.toByteArray(cosObject.getObjectContent());
        } catch (CosServiceException e) {
            if (e.getStatusCode() == 404) {
                throw new ObjectStorageNotFoundException("key not found : " + key, e);
            } else {
                throw new ObjectStorageException(e);
            }
        } catch (CosClientException e) {
            throw new ObjectStorageException(e);
        } catch (Throwable th) {
            Throwables.throwIfUnchecked(th);
            throw new ObjectStorageException(th);
        }
    }

    @Override
    public boolean doesObjectExists(String key) throws ObjectStorageException {
        return doesObjectExists(this.bucket, key);
    }

    @Override
    public boolean doesObjectExists(String bucketName, String key) throws ObjectStorageException {
        return this.cosClient.doesObjectExist(bucketName, key);
    }

    @Override
    public Iterator<ObjectMetadata> listObjectMetadata(String keyPrefix) throws ObjectStorageException {
        Preconditions.checkNotNull(keyPrefix, "keyPrefix is empty");
        return new Iterator<ObjectMetadata>() {

            private ObjectListing objectListing = null;
            private int index = 0;

            @Override
            public boolean hasNext() {
                if (this.objectListing == null) {
                    this.doGetObjectListing();
                }
                return this.index < this.objectListing.getObjectSummaries().size();
            }

            @Override
            public ObjectMetadata next() {
                if (this.objectListing == null) {
                    this.doGetObjectListing();
                }
                if (this.index >= this.objectListing.getObjectSummaries().size()) {
                    throw new IndexOutOfBoundsException();
                }
                ObjectMetadata next = toObjectMetadata(this.objectListing.getObjectSummaries().get(this.index));
                this.index++;
                if (this.index >= this.objectListing.getObjectSummaries().size() && this.objectListing.isTruncated()) {
                    this.doGetObjectListing();
                }
                return next;
            }

            private void doGetObjectListing() {
                try {
                    String marker = this.objectListing == null ? "" : this.objectListing.getNextMarker();
                    this.objectListing = cosClient
                            .listObjects(new ListObjectsRequest(bucket, keyPrefix, marker, "", 1000));
                    this.index = 0;
                } catch (CosClientException e) {
                    throw new ObjectStorageException(e);
                } catch (Throwable th) {
                    Throwables.throwIfUnchecked(th);
                    throw new ObjectStorageException(th);
                }
            }
        };
    }

    @Override
    public ObjectMetadata getObjectMetadata(String key) throws ObjectStorageException {
        Preconditions.checkArgument(key != null && !key.isEmpty(), "key is empty");
        try {
            return toObjectMetadata(key, this.cosClient.getObjectMetadata(this.bucket, key));
        } catch (CosServiceException e) {
            if (e.getStatusCode() == 404) {
                throw new ObjectStorageNotFoundException("key not found : " + key, e);
            } else {
                throw new ObjectStorageException(e);
            }
        } catch (CosClientException e) {
            throw new ObjectStorageException(e);
        } catch (Throwable th) {
            Throwables.throwIfUnchecked(th);
            throw new ObjectStorageException(th);
        }
    }

    @Override
    public void shutdown() {
        this.cosClient.shutdown();
    }

    private static ObjectMetadata toObjectMetadata(COSObjectSummary summary) {
        return new ObjectMetadata(summary.getKey(), summary.getSize(), summary.getLastModified().getTime(), null);
    }

    private static ObjectMetadata toObjectMetadata(String key, com.qcloud.cos.model.ObjectMetadata metadata) {
        return new ObjectMetadata(key, metadata.getContentLength(), metadata.getLastModified().getTime(),
                MediaType.parse(metadata.getContentType()));
    }

    public static QCloudObjectStorageBuilder newBuilder() {
        return new QCloudObjectStorageBuilder();
    }

    public static QCloudObjectStorage buildFromProperties(QCloudObjectStorageProperties properties) {
        if (properties.getUrlPrefixList() == null) {
            properties.setUrlPrefixList(Lists.newArrayList());
        }
        return new QCloudObjectStorageBuilder().setUrlPrefixList(properties.getUrlPrefixList())
                .setBucket(properties.getBucket()).setAppId(properties.getAppId())
                .setAccessKey(properties.getAccessKey()).setSecretKey(properties.getSecretKey())
                .setRegionName(properties.getRegionName()).build();
    }

}
