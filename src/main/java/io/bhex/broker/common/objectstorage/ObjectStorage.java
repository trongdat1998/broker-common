/**********************************
 *@项目名称: broker-parent
 *@文件名称: io.bhex.broker.common
 *@Date 2018/7/6
 *@Author peiwei.ren@bhex.io 
 *@Copyright（C）: 2018 BlueHelix Inc.   All rights reserved.
 *注意：本内容仅限于内部传阅，禁止外泄以及用于其他的商业目的。
 ***************************************/
package io.bhex.broker.common.objectstorage;

import com.google.common.collect.ImmutableList;
import com.google.common.net.MediaType;

import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.util.Date;
import java.util.Iterator;

public interface ObjectStorage {

    /**
     * Gets a list of all url prefixes stored by the current object.
     * The main purpose：
     * 1.Public download object storage available prefix and key directly spliced into download address
     * 2.Determine whether the current url is the storage management for this object based on whether the prefix matches
     */
    ImmutableList<String> urlPrefixList();

    /**
     * upload file to object storage
     *
     * @param key         the key in object storage.
     * @param contentType data content type。reference{@link com.google.common.net.MediaType}
     * @param file        data
     * @throws ObjectStorageException
     */
    void uploadObject(String key, MediaType contentType, File file) throws ObjectStorageException;

    /**
     * upload file to object storage
     *
     * @param bucketName  the bucketName store object storage.
     * @param key         the key in object storage.
     * @param contentType data content type。reference{@link com.google.common.net.MediaType}
     * @param file        data
     * @throws ObjectStorageException
     */
    void uploadObject(String bucketName, String key, MediaType contentType, File file) throws ObjectStorageException;

    /**
     * upload file to object storage
     *
     * @param key           the key in object storage.
     * @param contentType   data content type。reference{@link com.google.common.net.MediaType}
     * @param file          data
     * @param accessControl object access
     * @throws ObjectStorageException
     */
    void uploadObject(String key, MediaType contentType, File file, CannedAccessControlList accessControl) throws ObjectStorageException;

    /**
     * upload file to object storage
     *
     * @param bucketName    the bucketName store object storage.
     * @param key           the key in object storage.
     * @param contentType   data content type。reference{@link com.google.common.net.MediaType}
     * @param file          data
     * @param accessControl object access
     * @throws ObjectStorageException
     */
    void uploadObject(String bucketName, String key, MediaType contentType, File file, CannedAccessControlList accessControl) throws ObjectStorageException;

    /**
     * upload data to object storage
     *
     * @param key         the key in object storage.
     * @param contentType data content type。reference{@link com.google.common.net.MediaType}
     * @param data        data
     * @throws ObjectStorageException
     */
    void uploadObject(String key, MediaType contentType, byte[] data) throws ObjectStorageException;

    /**
     * upload data to object storage
     *
     * @param bucketName  the bucketName store object storage.
     * @param key         the key in object storage.
     * @param contentType data content type。reference{@link com.google.common.net.MediaType}
     * @param data        data
     * @throws ObjectStorageException
     */
    void uploadObject(String bucketName, String key, MediaType contentType, byte[] data) throws ObjectStorageException;

    /**
     * upload data to object storage
     *
     * @param key           the key in object storage.
     * @param contentType   data content type。reference{@link com.google.common.net.MediaType}
     * @param data          data
     * @param accessControl accessControl
     * @throws ObjectStorageException
     */
    void uploadObject(String key, MediaType contentType, byte[] data, CannedAccessControlList accessControl) throws ObjectStorageException;

    /**
     * upload data to object storage
     *
     * @param bucketName    the bucketName store object storage.
     * @param key           the key in object storage.
     * @param contentType   data content type。reference{@link com.google.common.net.MediaType}
     * @param data          data
     * @param accessControl accessControl
     * @throws ObjectStorageException
     */
    void uploadObject(String bucketName, String key, MediaType contentType, byte[] data, CannedAccessControlList accessControl) throws ObjectStorageException;

    /**
     * upload data to object storage
     *
     * @param key         the key in object storage.
     * @param contentType data content type。reference{@link com.google.common.net.MediaType}
     * @param inputStream stream
     * @throws ObjectStorageException
     */
    void uploadObject(String key, MediaType contentType, InputStream inputStream) throws ObjectStorageException;

    /**
     * upload data to object storage
     *
     * @param bucketName  the bucketName store object storage.
     * @param key         the key in object storage.
     * @param contentType data content type。reference{@link com.google.common.net.MediaType}
     * @param inputStream stream
     * @throws ObjectStorageException
     */
    void uploadObject(String bucketName, String key, MediaType contentType, InputStream inputStream) throws ObjectStorageException;

    /**
     * upload data to object storage
     *
     * @param key           the key in object storage.
     * @param contentType   data content type。reference{@link com.google.common.net.MediaType}
     * @param inputStream   stream
     * @param accessControl object access
     * @throws ObjectStorageException
     */
    void uploadObject(String key, MediaType contentType, InputStream inputStream, CannedAccessControlList accessControl) throws ObjectStorageException;

    /**
     * upload data to object storage
     *
     * @param key           the key in object storage.
     * @param contentType   data content type。reference{@link com.google.common.net.MediaType}
     * @param inputStream   stream
     * @param accessControl object access
     * @throws ObjectStorageException
     */
    void uploadObjectWithCacheControl(String key, MediaType contentType, InputStream inputStream, CannedAccessControlList accessControl, String cacheControl) throws ObjectStorageException;

    /**
     * upload data to object storage
     *
     * @param bucketName    the bucketName store object storage.
     * @param key           the key in object storage.
     * @param contentType   data content type。reference{@link com.google.common.net.MediaType}
     * @param inputStream   stream
     * @param accessControl object access
     * @throws ObjectStorageException
     */
    void uploadObject(String bucketName, String key, MediaType contentType, InputStream inputStream, CannedAccessControlList accessControl) throws ObjectStorageException;

    /**
     * download object into the file (the original content of the file will be overwritten)
     *
     * @param key  the key in object storage.
     * @param file file to save object
     * @throws ObjectStorageException
     */
    void downloadObject(String key, File file) throws ObjectStorageException;

    /**
     * download object into the file (the original content of the file will be overwritten)
     *
     * @param bucketName the bucketName store object storage.
     * @param key        the key in object storage.
     * @param file       file to save object
     * @throws ObjectStorageException
     */
    void downloadObject(String bucketName, String key, File file) throws ObjectStorageException;

    /**
     * download object into byte array
     *
     * @param key the key in object storage.
     * @return byte array
     * @throws ObjectStorageException
     */
    byte[] downloadObject(String key) throws ObjectStorageException;

    /**
     * download object into byte array
     *
     * @param bucketName the bucketName store object storage.
     * @param key        the key in object storage.
     * @return byte array
     * @throws ObjectStorageException
     */
    byte[] downloadObject(String bucketName, String key) throws ObjectStorageException;

    boolean doesObjectExists(String key) throws ObjectStorageException;

    boolean doesObjectExists(String bucketName, String key) throws ObjectStorageException;

    /**
     * generate a url to access object
     *
     * @param key        the key in object storage.
     * @param expiration access expiration
     * @throws ObjectStorageException
     */
    URL generatePresignUrl(String key, Date expiration) throws ObjectStorageException;

    /**
     * generate a url to access object
     *
     * @param bucketName the bucketName store object storage.
     * @param key        the key in object storage.
     * @param expiration access expiration
     * @throws ObjectStorageException
     */
    URL generatePresignUrl(String bucketName, String key, Date expiration) throws ObjectStorageException;

    /**
     * Iterating over the object that sets the prefix key in the object storage
     *
     * @param keyPrefix prefix key
     * @return Object metadata iterator
     * @throws ObjectStorageException
     */
    Iterator<ObjectMetadata> listObjectMetadata(String keyPrefix) throws ObjectStorageException;

    /**
     * Get the metadata of the specified key. note：If the key does not exist in the object storage, an exception will be thrown! ! !
     *
     * @param key the key in object storage.
     * @return Object metadata
     * @throws ObjectStorageException
     */
    ObjectMetadata getObjectMetadata(String key) throws ObjectStorageException;

    /**
     * Close the current object storage instance
     */
    void shutdown();

}
