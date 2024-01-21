/*
 ************************************
 * @项目名称: broker
 * @文件名称: CryptoUtil
 * @Date 2018/05/22
 * @Author will.zhao@bhex.io
 * @Copyright（C）: 2018 BlueHelix Inc.   All rights reserved.
 * 注意：本内容仅限于内部传阅，禁止外泄以及用于其他的商业目的。
 **************************************
 */
package io.bhex.broker.common.util;

import com.google.common.base.Charsets;
import com.google.common.hash.Hashing;
import org.apache.commons.lang3.RandomStringUtils;

/**
 * Crypto Util
 *
 * @author will.zhao@bhex.io
 */
public class CryptoUtil {

    private static final String SNOW_DEFAULT = "BestRegards2「BTC」By「BHEX.IO」©2018.";

    public static String getRandomCode(int length){
        return RandomStringUtils.randomAlphanumeric(length);
    }

    public static String encryptPassword(String password, String snow) {
        return sha256(sha256(password.toUpperCase()) + SNOW_DEFAULT + snow);
    }

    private static String sha256(String value) {
        return sha256(value, false);
    }

    private static String sha256(String str, boolean upper) {
        String enc = Hashing.sha256().hashString(str, Charsets.UTF_8).toString();
        return upper ? enc.toUpperCase() : enc;
    }
}


