/**********************************
 *@项目名称: broker-parent
 *@文件名称: io.bhex.broker.common.objectstorage
 *@Date 2018/7/18
 *@Author peiwei.ren@bhex.io 
 *@Copyright（C）: 2018 BlueHelix Inc.   All rights reserved.
 *注意：本内容仅限于内部传阅，禁止外泄以及用于其他的商业目的。
 ***************************************/
package io.bhex.broker.common.objectstorage;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.ClientConfiguration;
import com.amazonaws.Protocol;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.*;
import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import com.google.common.base.Throwables;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.google.common.io.ByteStreams;
import com.google.common.net.MediaType;
import com.qcloud.cos.exception.CosClientException;

import java.io.*;
import java.net.URL;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class AwsObjectStorage implements ObjectStorage {

    private final ImmutableList<String> urlPrefixList;
    private final String bucket;
    private final AmazonS3 amazonS3;

    public AwsObjectStorage(
            List<String> urlPrefixList,
            String bucket,
            String accessKey,
            String secretKey,
            String regionName) {
        // Preconditions.checkArgument(urlPrefixList != null && !urlPrefixList.isEmpty(), "urlPrefixList is empty");
        Preconditions.checkArgument(bucket != null && !bucket.isEmpty(), "bucket is empty");
        Preconditions.checkArgument(accessKey != null && !accessKey.isEmpty(), "accessKey is empty");
        Preconditions.checkArgument(secretKey != null && !secretKey.isEmpty(), "secretKey is empty");
        Preconditions.checkArgument(regionName != null && !regionName.isEmpty(), "regionName is empty");
        this.urlPrefixList = ImmutableList.copyOf(urlPrefixList);
        this.bucket = bucket;
        AWSCredentials credentials = new BasicAWSCredentials(accessKey, secretKey);
        ClientConfiguration clientConfig = new ClientConfiguration();
        clientConfig.setProtocol(Protocol.HTTP);
        this.amazonS3 = AmazonS3ClientBuilder.standard()
                .withClientConfiguration(clientConfig)
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                .withRegion(regionName)
                .build();
    }

    @Override
    public ImmutableList<String> urlPrefixList() {
        return this.urlPrefixList;
    }

    @Override
    public void uploadObject(String key, MediaType contentType, File file) throws ObjectStorageException {
        uploadObject(this.bucket, key, contentType, file, null);
    }

    @Override
    public void uploadObject(String bucketName, String key, MediaType contentType, File file) throws ObjectStorageException {
        uploadObject(bucketName, key, contentType, file, null);
    }

    @Override
    public void uploadObject(String key, MediaType contentType, File file, CannedAccessControlList accessControl) throws ObjectStorageException {
        uploadObject(this.bucket, key, contentType, file, accessControl);
    }

    @Override
    public void uploadObject(String bucketName, String key, MediaType contentType, File file, CannedAccessControlList accessControl) throws ObjectStorageException {
        Preconditions.checkArgument(bucketName != null && !bucketName.isEmpty(), "bucketName is empty");
        Preconditions.checkArgument(key != null && !key.isEmpty(), "key is empty");
        Preconditions.checkNotNull(contentType, "contentType is empty");
        Preconditions.checkNotNull(file, "file is empty");
        try {
            com.amazonaws.services.s3.model.ObjectMetadata objectMetadata = new com.amazonaws.services.s3.model.ObjectMetadata();
            objectMetadata.setContentType(contentType.toString().replace(" ", ""));
            objectMetadata.setContentLength(file.length());
            PutObjectRequest requst = new PutObjectRequest(bucketName, key, file);
            if (accessControl != null) {
                objectMetadata.setHeader("x-amz-acl", accessControl.toString());
                requst.withCannedAcl(com.amazonaws.services.s3.model.CannedAccessControlList.valueOf(accessControl.name()));
            }
            this.amazonS3.putObject(new PutObjectRequest(bucketName, key, file).withMetadata(objectMetadata));
        } catch (AmazonClientException e) {
            throw new ObjectStorageException(e);
        } catch (Throwable th) {
            Throwables.throwIfUnchecked(th);
            throw new ObjectStorageException(th);
        }
    }

    @Override
    public void uploadObject(String key, MediaType contentType, byte[] data) throws ObjectStorageException {
        uploadObject(this.bucket, key, contentType, data, null);
    }

    @Override
    public void uploadObject(String bucketName, String key, MediaType contentType, byte[] data) throws ObjectStorageException {
        uploadObject(bucketName, key, contentType, data, null);
    }

    @Override
    public void uploadObject(String key, MediaType contentType, byte[] data, CannedAccessControlList accessControl) throws ObjectStorageException {
        uploadObject(this.bucket, key, contentType, data);
    }

    @Override
    public void uploadObject(String bucketName, String key, MediaType contentType, byte[] data, CannedAccessControlList accessControl) throws ObjectStorageException {
        Preconditions.checkArgument(bucketName != null && !bucketName.isEmpty(), "bucketName is empty");
        Preconditions.checkArgument(key != null && !key.isEmpty(), "key is empty");
        Preconditions.checkNotNull(contentType, "contentType is empty");
        Preconditions.checkNotNull(data, "data is empty");
        try {
            com.amazonaws.services.s3.model.ObjectMetadata objectMetadata = new com.amazonaws.services.s3.model.ObjectMetadata();
            objectMetadata.setContentType(contentType.toString().replace(" ", ""));
            objectMetadata.setContentLength(data.length);
            if (accessControl != null) {
                objectMetadata.setHeader("x-amz-acl", accessControl.toString());
            }
            this.amazonS3.putObject(bucketName, key, new ByteArrayInputStream(data), objectMetadata);
        } catch (AmazonClientException e) {
            throw new ObjectStorageException(e);
        } catch (Throwable th) {
            Throwables.throwIfUnchecked(th);
            throw new ObjectStorageException(th);
        }
    }

    @Override
    public void uploadObject(String key, MediaType contentType, InputStream inputStream) throws ObjectStorageException {
        uploadObject(this.bucket, key, contentType, inputStream, null);
    }

    @Override
    public void uploadObject(String bucketName, String key, MediaType contentType, InputStream inputStream) throws ObjectStorageException {
        uploadObject(bucketName, key, contentType, inputStream, null);
    }

    @Override
    public void uploadObject(String key, MediaType contentType, InputStream inputStream, CannedAccessControlList accessControl) throws ObjectStorageException {
        uploadObject(this.bucket, key, contentType, inputStream, accessControl);
    }

    @Override
    public void uploadObjectWithCacheControl(String key, MediaType contentType, InputStream inputStream, CannedAccessControlList accessControl, String cacheControl) throws ObjectStorageException {
        uploadObject(this.bucket, key, contentType, inputStream, accessControl, cacheControl);
    }

    public void uploadObject(String bucketName, String key, MediaType contentType, InputStream inputStream, CannedAccessControlList accessControl, String cacheControl) throws ObjectStorageException {
        Preconditions.checkArgument(bucketName != null && !bucketName.isEmpty(), "bucketName is empty");
        Preconditions.checkArgument(key != null && !key.isEmpty(), "key is empty");
        Preconditions.checkNotNull(contentType, "contentType is empty");
        Preconditions.checkNotNull(inputStream, "inputStream is empty");
        try {
            com.amazonaws.services.s3.model.ObjectMetadata objectMetadata = new com.amazonaws.services.s3.model.ObjectMetadata();
            objectMetadata.setContentType(contentType.toString().replace(" ", ""));
            if (accessControl != null) {
                objectMetadata.setHeader("x-amz-acl", accessControl.toString());
            }
            if (cacheControl != null && !cacheControl.trim().equals("")) {
                objectMetadata.setCacheControl(cacheControl);
            }
            this.amazonS3.putObject(bucketName, key, inputStream, objectMetadata);
        } catch (AmazonClientException e) {
            throw new ObjectStorageException(e);
        } catch (Throwable th) {
            Throwables.throwIfUnchecked(th);
            throw new ObjectStorageException(th);
        }
    }


    @Override
    public void uploadObject(String bucketName, String key, MediaType contentType, InputStream inputStream, CannedAccessControlList accessControl) throws ObjectStorageException {
        uploadObject(this.bucket, key, contentType, inputStream, accessControl, "");
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
            return this.amazonS3.generatePresignedUrl(bucketName, key, expiration);
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
        Preconditions.checkNotNull(file, "file is empty");
        try {
            S3Object cosObject = this.amazonS3.getObject(bucketName, key);
            try (OutputStream outputStream = new FileOutputStream(file)) {
                ByteStreams.copy(cosObject.getObjectContent(), outputStream);
            }
        } catch (AmazonServiceException e) {
            if (e.getStatusCode() == 404) {
                throw new ObjectStorageNotFoundException("key not found : " + key, e);
            } else {
                throw new ObjectStorageException(e);
            }
        } catch (AmazonClientException e) {
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
        Preconditions.checkArgument(key != null && !key.isEmpty(), "key is empty");
        try {
            S3Object s3Object = this.amazonS3.getObject(bucketName, key);
            return ByteStreams.toByteArray(s3Object.getObjectContent());
        } catch (AmazonServiceException e) {
            if (e.getStatusCode() == 404) {
                throw new ObjectStorageNotFoundException("key not found : " + key, e);
            } else {
                throw new ObjectStorageException(e);
            }
        } catch (AmazonClientException e) {
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
        return this.amazonS3.doesObjectExist(bucketName, key);
    }

    @Override
    public Iterator<ObjectMetadata> listObjectMetadata(String keyPrefix) throws ObjectStorageException {
        Preconditions.checkNotNull(keyPrefix, "keyPrefix");
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
                    this.objectListing = amazonS3.listObjects(new ListObjectsRequest(bucket, keyPrefix, marker, "", 1000));
                    this.index = 0;
                } catch (AmazonClientException e) {
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
            return toObjectMetadata(key, this.amazonS3.getObjectMetadata(this.bucket, key));
        } catch (AmazonServiceException e) {
            if (e.getStatusCode() == 404) {
                throw new ObjectStorageNotFoundException("key not found : " + key, e);
            } else {
                throw new ObjectStorageException(e);
            }
        } catch (AmazonClientException e) {
            throw new ObjectStorageException(e);
        } catch (Throwable th) {
            Throwables.throwIfUnchecked(th);
            throw new ObjectStorageException(th);
        }
    }

    @Override
    public void shutdown() {
        this.amazonS3.shutdown();
    }

    private static ObjectMetadata toObjectMetadata(S3ObjectSummary summary) {
        return new ObjectMetadata(summary.getKey(), summary.getSize(), summary.getLastModified().getTime(), null);
    }

    private static ObjectMetadata toObjectMetadata(String key, com.amazonaws.services.s3.model.ObjectMetadata metadata) {
        return new ObjectMetadata(key, metadata.getContentLength(), metadata.getLastModified().getTime(), MediaType.parse(metadata.getContentType()));
    }

    public static AwsObjectStorageBuilder newBuilder() {
        return new AwsObjectStorageBuilder();
    }

    public static AwsObjectStorage buildFromProperties(AwsObjectStorageProperties properties) {
        if (properties.getUrlPrefixList() == null) {
            properties.setUrlPrefixList(Lists.newArrayList());
        }
        return new AwsObjectStorageBuilder()
                .setUrlPrefixList(properties.getUrlPrefixList())
                .setBucket(properties.getBucket())
                .setAccessKey(properties.getAccessKey())
                .setSecretKey(properties.getSecretKey())
                .setRegionName(properties.getRegionName())
                .build();
    }

}
