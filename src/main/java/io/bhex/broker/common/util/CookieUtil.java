/*
 ************************************
 * @项目名称: broker-parent
 * @文件名称: CookieUtil
 * @Date 2018/05/27
 * @Author will.zhao@bhex.io
 * @Copyright（C）: 2018 BlueHelix Inc.   All rights reserved.
 * 注意：本内容仅限于内部传阅，禁止外泄以及用于其他的商业目的。
 **************************************
 */

package io.bhex.broker.common.util;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Cookie util
 *
 * @author will.zhao@bhex.io
 */
public class CookieUtil {

    private static final String PATH = "/";

    public static void create(HttpServletResponse response, String domain, Boolean httpOnly, String name, String value, Integer ages) {
        Cookie cookie = new Cookie(name, value);
        cookie.setSecure(false);
        cookie.setHttpOnly(httpOnly);
        // cookie.setDomain(domain);
        cookie.setDomain(domain.replaceAll("(^[.]+)", ""));
        cookie.setPath(PATH);
        cookie.setMaxAge(ages);
        response.addCookie(cookie);
    }

    public static void clear(HttpServletResponse response, String name) {
        Cookie cookie = new Cookie(name, null);
        cookie.setMaxAge(0);
        cookie.setPath(PATH);
        response.addCookie(cookie);
    }

    public static void clear(HttpServletResponse response, String domain, String name) {
        Cookie cookie = new Cookie(name, null);
        // cookie.setDomain(domain);
        cookie.setDomain(domain.replaceAll("(^[.]+)", ""));
        cookie.setPath(PATH);
        cookie.setMaxAge(0);
        response.addCookie(cookie);
    }

    public static String getValue(HttpServletRequest request, String name) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(name)) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }

}


