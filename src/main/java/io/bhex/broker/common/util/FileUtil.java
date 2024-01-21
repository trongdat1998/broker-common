/**********************************
 *@项目名称: broker-parent
 *@文件名称: io.bhex.broker.common.util
 *@Date 2018/7/8
 *@Author peiwei.ren@bhex.io 
 *@Copyright（C）: 2018 BlueHelix Inc.   All rights reserved.
 *注意：本内容仅限于内部传阅，禁止外泄以及用于其他的商业目的。
 ***************************************/
package io.bhex.broker.common.util;

import javax.annotation.Nullable;
import java.io.File;
import java.net.MalformedURLException;

public class FileUtil {

    public static String fileToUrl(File file) {
        try {
            return file.toURI().toURL().toString();
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }

    public static String getFileSuffix(@Nullable String fileName, String defaultSuffix) {
        if (fileName == null || fileName.isEmpty()) {
            return defaultSuffix;
        }
        int idx = fileName.lastIndexOf('.');
        return idx < 0 || idx >= fileName.length() - 1 ? defaultSuffix : fileName.substring(idx + 1);
    }

}
