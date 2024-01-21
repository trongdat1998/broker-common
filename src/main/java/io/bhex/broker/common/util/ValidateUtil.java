/*
 ************************************
 * @项目名称: broker
 * @文件名称: ValidateUtil
 * @Date 2018/05/22
 * @Author will.zhao@bhex.io
 * @Copyright（C）: 2018 BlueHelix Inc.   All rights reserved.
 * 注意：本内容仅限于内部传阅，禁止外泄以及用于其他的商业目的。
 **************************************
 */
package io.bhex.broker.common.util;

import com.google.common.base.Strings;
import io.bhex.broker.common.exception.BrokerErrorCode;
import io.bhex.broker.common.exception.BrokerException;

/**
 * Validate Util
 *
 * @author will.zhao@bhex.io
 */
public class ValidateUtil {

    private static final String EMAIL_PATTERN = "^[_a-z0-9-]+(\\.[_a-z0-9-]+)*@[a-z0-9-]+(\\.[a-z0-9-]+)*(\\.[a-z]{2,})$";
//    private static final String EMAIL_PATTERN = "^[_a-z0-9-]+([.+][_a-z0-9-]+)*@[a-z0-9-]+(\\.[a-z0-9-]+)*(\\.[a-z]{2,})$";

    public static void validateMobile(String mobile) {
        if (Strings.isNullOrEmpty(mobile)) {
            throw new BrokerException(BrokerErrorCode.MOBILE_CANNOT_BE_NULL);
        }
    }

    public static void validateEmail(String email) {
        if (Strings.isNullOrEmpty(email)) {
            throw new BrokerException(BrokerErrorCode.EMAIL_CANNOT_BE_NULL);
        }
        if (!email.toLowerCase().matches(EMAIL_PATTERN)) {
            throw new BrokerException(BrokerErrorCode.EMAIL_ILLEGAL);
        }
    }

    @Deprecated
    public static void validateEmailLoose(String email) {
        if (Strings.isNullOrEmpty(email)) {
            throw new BrokerException(BrokerErrorCode.EMAIL_CANNOT_BE_NULL);
        }
        if (!email.contains("@") || email.startsWith("@") || email.endsWith("@")
                || email.startsWith(".") || email.endsWith(".")
                || email.contains(" ")) {
            throw new BrokerException(BrokerErrorCode.EMAIL_ILLEGAL);
        }
        String emailPrefix = email.substring(0, email.indexOf("@"));
        String emailSuffix = email.substring(email.indexOf("@") + 1);
        if (!emailSuffix.contains(".")
                || emailSuffix.startsWith(".") || emailSuffix.endsWith(".")) {
            throw new BrokerException(BrokerErrorCode.EMAIL_ILLEGAL);
        }
    }

    public static void main(String[] args) {
        System.out.println("System@gmail.com".toLowerCase().matches(EMAIL_PATTERN));
        System.out.println("system@gmail.com".matches(EMAIL_PATTERN));
        System.out.println("system admin@gmail.com".matches(EMAIL_PATTERN));
        System.out.println("gatti-galaxy-guard@t.vodafone.ne.jp".matches(EMAIL_PATTERN));
        System.out.println("9.4.1973-meisho_hitonyan_z@ezweb.ne.jp".matches(EMAIL_PATTERN));
        System.out.println("everybody-wants-to-be-tamai@hotmail.co.jp".matches(EMAIL_PATTERN));
        System.out.println("roseofflowersidex@yahoo.co.jp".matches(EMAIL_PATTERN));
        System.out.println("happy.h.r.k030309110204@gmail.com".matches(EMAIL_PATTERN));
    }

}


