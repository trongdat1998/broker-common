/**********************************
 *@项目名称: broker-parent
 *@文件名称: io.bhex.broker.common.objectstorage
 *@Date 2018/7/6
 *@Author peiwei.ren@bhex.io 
 *@Copyright（C）: 2018 BlueHelix Inc.   All rights reserved.
 *注意：本内容仅限于内部传阅，禁止外泄以及用于其他的商业目的。
 ***************************************/
package io.bhex.broker.common.objectstorage;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.google.common.hash.Hashing;
import com.google.common.io.BaseEncoding;
import com.google.common.io.Files;
import com.google.common.net.MediaType;

import javax.annotation.CheckForNull;
import java.io.File;
import java.io.IOException;
import java.util.Map;

public class ObjectStorageUtil {

    private ObjectStorageUtil() {
    }

    private static final ImmutableMap<String, MediaType> FILE_SUFFIX_TO_CONTENT_TYPE_MAP
            = ImmutableMap.copyOf(constructFileSuffixToContentTypeMap());

    @CheckForNull
    public static MediaType getFileContentType(String fileSuffix) {
        return FILE_SUFFIX_TO_CONTENT_TYPE_MAP.get(fileSuffix.toLowerCase());
    }

    public static MediaType getFileContentType(String fileSuffix, MediaType defaultContentType) {
        MediaType contentType = getFileContentType(fileSuffix);
        return contentType == null ? defaultContentType : contentType;
    }

    public static MediaType getFileContentTypeOrOctetStream(String fileSuffix) {
        return getFileContentType(fileSuffix, MediaType.OCTET_STREAM);
    }

    private static final BaseEncoding BASE64URL_NOPADDING_ENCODING = BaseEncoding.base64Url().omitPadding();

    public static String sha256FileName(File file, String fileSuffix) throws IOException {
        return BASE64URL_NOPADDING_ENCODING.encode(Files.asByteSource(file).hash(Hashing.sha256()).asBytes()) + "." + fileSuffix.toLowerCase();
    }

    public static String sha256FileName(byte[] data, String fileSuffix) throws IOException {
        return BASE64URL_NOPADDING_ENCODING.encode(Hashing.sha256().hashBytes(data).asBytes()) + "." + fileSuffix.toLowerCase();
    }

    private static Map<String, MediaType> constructFileSuffixToContentTypeMap() {
        Map<String, MediaType> map = Maps.newHashMap();
        // nginx mime conf
        map.put("html", MediaType.parse("text/html"));
        map.put("htm", MediaType.parse("text/html"));
        map.put("shtml", MediaType.parse("text/html"));
        map.put("css", MediaType.parse("text/css"));
        map.put("xml", MediaType.parse("text/xml"));
        map.put("gif", MediaType.GIF);
        map.put("jpeg", MediaType.JPEG);
        map.put("jpg", MediaType.JPEG);
        map.put("js", MediaType.parse("application/javascript"));
        map.put("atom", MediaType.parse("application/atom+xml"));
        map.put("rss", MediaType.parse("application/rss+xml"));
        map.put("mml", MediaType.parse("text/mathml"));
        map.put("txt", MediaType.parse("text/plain"));
        map.put("jad", MediaType.parse("text/vnd.sun.j2me.app-descriptor"));
        map.put("wml", MediaType.parse("text/vnd.wap.wml"));
        map.put("htc", MediaType.parse("text/x-component"));
        map.put("png", MediaType.PNG);
        map.put("tif", MediaType.TIFF);
        map.put("tiff", MediaType.TIFF);
        map.put("wbmp", MediaType.parse("image/vnd.wap.wbmp"));
        map.put("ico", MediaType.parse("image/x-icon"));
        map.put("jng", MediaType.parse("image/x-jng"));
        map.put("bmp", MediaType.parse("image/x-ms-bmp"));
        map.put("svg", MediaType.parse("image/svg+xml"));
        map.put("svgz", MediaType.parse("image/svg+xml"));
        map.put("webp", MediaType.WEBP);
        map.put("woff", MediaType.WOFF);
        map.put("jar", MediaType.parse("application/java-archive"));
        map.put("war", MediaType.parse("application/java-archive"));
        map.put("ear", MediaType.parse("application/java-archive"));
        map.put("json", MediaType.parse("application/json"));
        map.put("hqx", MediaType.parse("application/mac-binhex40"));
        map.put("doc", MediaType.MICROSOFT_WORD);
        map.put("pdf", MediaType.PDF);
        map.put("ps", MediaType.POSTSCRIPT);
        map.put("eps", MediaType.POSTSCRIPT);
        map.put("ai", MediaType.POSTSCRIPT);
        map.put("rtf", MediaType.parse("application/rtf"));
        map.put("m3u8", MediaType.parse("application/vnd.apple.mpegurl"));
        map.put("xls", MediaType.MICROSOFT_EXCEL);
        map.put("eot", MediaType.EOT);
        map.put("ppt", MediaType.MICROSOFT_POWERPOINT);
        map.put("wmlc", MediaType.parse("application/vnd.wap.wmlc"));
        map.put("kml", MediaType.KML);
        map.put("kmz", MediaType.KMZ);
        map.put("7z", MediaType.parse("application/x-7z-compressed"));
        map.put("cco", MediaType.parse("application/x-cocoa"));
        map.put("jardiff", MediaType.parse("application/x-java-archive-diff"));
        map.put("jnlp", MediaType.parse("application/x-java-jnlp-file"));
        map.put("run", MediaType.parse("application/x-makeself"));
        map.put("pl", MediaType.parse("application/x-perl"));
        map.put("pm", MediaType.parse("application/x-perl"));
        map.put("prc", MediaType.parse("application/x-pilot"));
        map.put("pdb", MediaType.parse("application/x-pilot"));
        map.put("rar", MediaType.parse("application/x-rar-compressed"));
        map.put("rpm", MediaType.parse("application/x-redhat-package-manager"));
        map.put("sea", MediaType.parse("application/x-sea"));
        map.put("swf", MediaType.SHOCKWAVE_FLASH);
        map.put("sit", MediaType.parse("application/x-stuffit"));
        map.put("tcl", MediaType.parse("application/x-tcl"));
        map.put("tk", MediaType.parse("application/x-tcl"));
        map.put("der", MediaType.parse("application/x-x509-ca-cert"));
        map.put("pem", MediaType.parse("application/x-x509-ca-cert"));
        map.put("crt", MediaType.parse("application/x-x509-ca-cert"));
        map.put("xpi", MediaType.parse("application/x-xpinstall"));
        map.put("xhtml", MediaType.parse("application/xhtml+xml"));
        map.put("xspf", MediaType.parse("application/xspf+xml"));
        map.put("zip", MediaType.ZIP);
        map.put("bin", MediaType.OCTET_STREAM);
        map.put("exe", MediaType.OCTET_STREAM);
        map.put("dll", MediaType.OCTET_STREAM);
        map.put("deb", MediaType.OCTET_STREAM);
        map.put("dmg", MediaType.OCTET_STREAM);
        map.put("iso", MediaType.OCTET_STREAM);
        map.put("img", MediaType.OCTET_STREAM);
        map.put("msi", MediaType.OCTET_STREAM);
        map.put("msp", MediaType.OCTET_STREAM);
        map.put("msm", MediaType.OCTET_STREAM);
        map.put("docx", MediaType.OOXML_DOCUMENT);
        map.put("xlsx", MediaType.OOXML_SHEET);
        map.put("pptx", MediaType.OOXML_PRESENTATION);
        map.put("mid", MediaType.parse("audio/midi"));
        map.put("midi", MediaType.parse("audio/midi"));
        map.put("kar", MediaType.parse("audio/midi"));
        map.put("mp3", MediaType.MPEG_AUDIO);
        map.put("ogg", MediaType.OGG_AUDIO);
        map.put("m4a", MediaType.parse("audio/x-m4a"));
        map.put("ra", MediaType.parse("audio/x-realaudio"));
        map.put("3gpp", MediaType.THREE_GPP_VIDEO);
        map.put("3gp", MediaType.THREE_GPP_VIDEO);
        map.put("ts", MediaType.parse("video/mp2t"));
        map.put("mp4", MediaType.MP4_VIDEO);
        map.put("mpeg", MediaType.MPEG_VIDEO);
        map.put("mpg", MediaType.MPEG_VIDEO);
        map.put("mov", MediaType.QUICKTIME);
        map.put("webm", MediaType.WEBM_VIDEO);
        map.put("flv", MediaType.FLV_VIDEO);
        map.put("m4v", MediaType.parse("video/x-m4v"));
        map.put("mng", MediaType.parse("video/x-mng"));
        map.put("asx", MediaType.parse("video/x-ms-asf"));
        map.put("asf", MediaType.parse("video/x-ms-asf"));
        map.put("wmv", MediaType.WMV);
        map.put("avi", MediaType.parse("video/x-msvideo"));

        // other
        map.put("apk", MediaType.parse("application/vnd.android.package-archive"));
        map.put("ipa", MediaType.OCTET_STREAM);
        map.put("plist", MediaType.parse("text/plain"));
        return map;
    }

}
