package io.bhex.broker.common.objectstorage;

/**********************************
 *@项目名称: broker-common
 *@文件名称: io.bhex.broker.common.objectstorage
 *@Date 2018/9/25
 *@Author peiwei.ren@bhex.io 
 *@Copyright（C）: 2018 BlueHelix Inc.   All rights reserved.
 *注意：本内容仅限于内部传阅，禁止外泄以及用于其他的商业目的。
 ***************************************/
public enum CannedAccessControlList {
    /**
     * <p>
     * This is the default access control policy for any new buckets or objects.
     * </p>
     */
    Private("private"),

    /**
     * <p>
     * If this policy is used on an object, it can be read from a browser without
     * authentication.
     * </p>
     */
    PublicRead("public-read"),

    /**
     * <p>
     * This access policy is not recommended for general use.
     * </p>
     */
    PublicReadWrite("public-read-write");

    /**
     * x-amz-acl header value representing the canned acl
     */
    private final String cannedAclHeader;

    CannedAccessControlList(String cannedAclHeader) {
        this.cannedAclHeader = cannedAclHeader;
    }

    @Override
    public String toString() {
        return this.cannedAclHeader;
    }
}
