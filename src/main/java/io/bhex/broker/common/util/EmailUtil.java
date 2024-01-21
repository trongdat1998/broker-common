package io.bhex.broker.common.util;

@Deprecated
public class EmailUtil {

    public static String emailAlias(String email) {
        if (email.toLowerCase().endsWith("@gmail.com")) {
            String emailName = email.substring(0, email.indexOf("@")).replaceAll("\\.", "");
            if (emailName.contains("+")) {
                emailName = emailName.substring(0, emailName.indexOf("+"));
            }
            return emailName.replaceAll("\\.", "") + "@gmail.com";
        }
        return email;
    }

}
