/**********************************
 *@项目名称: broker-parent
 *@文件名称: io.bhex.broker.common.util
 *@Date 2018/6/26
 *@Author peiwei.ren@bhex.io 
 *@Copyright（C）: 2018 BlueHelix Inc.   All rights reserved.
 *注意：本内容仅限于内部传阅，禁止外泄以及用于其他的商业目的。
 ***************************************/
package io.bhex.broker.common.util;

public class QuoteUtil {

    public static String getRateKey(String tokenId) {
        return String.format("QUOTE:FX_RATE:%s", tokenId.toUpperCase());
    }

    public static String getKlineKey(String symbolId, String resolution) {
        return String.format("QUOTE:%s:%s", symbolId.toUpperCase(), resolution);
    }

    public static String getDepthKey(Long exchangeId,String symbolId) {
        return String.format("QUOTE:%s:%s:DEPTH", exchangeId,symbolId.toUpperCase());
    }

    public static String getPartialDepthKey(Long exchangeId,String symbolId, int limit) {
        return String.format("QUOTE:%s:%s:PARTIAL_DEPTH:%s", exchangeId,symbolId.toUpperCase(), limit);
    }

    public static String getTradeKey(String exchangeId,String symbolId) {
        return String.format("QUOTE:%s:%s:TICKETS", exchangeId,symbolId.toUpperCase());
    }

    public static String getRealTimeKey(Long exchangeId, String symbolId) {
        return String.format("QUOTE:%d:%s:REALTIME", exchangeId, symbolId.toUpperCase());
    }

}
