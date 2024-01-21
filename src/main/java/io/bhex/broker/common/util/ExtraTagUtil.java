package io.bhex.broker.common.util;

import com.google.common.collect.Maps;
import com.google.gson.reflect.TypeToken;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;

public class ExtraTagUtil {

    private Map<String, Integer> tagMap = Maps.newHashMap();

    private ExtraTagUtil(String tagMapStr) {
        if (!StringUtils.isEmpty(tagMapStr)) {
            tagMap = JsonUtil.defaultGson().fromJson(tagMapStr,
                    new TypeToken<HashMap<String, Integer>>() {}.getType());
        }
    }
    private ExtraTagUtil(Map<String, Integer> tagMap) {
        if (!StringUtils.isEmpty(tagMap)) {
            this.tagMap = Maps.newHashMap(tagMap);
        }
    }

    public static ExtraTagUtil newInstance(Map<String, Integer> tagMap) {
        return new ExtraTagUtil(tagMap);
    }
    public static ExtraTagUtil newInstance(String tagMapStr) {
        return new ExtraTagUtil(tagMapStr);
    }

    public Integer getTagValue(String tagKey) {
        return tagMap.getOrDefault(tagKey, 0);
    }
    public Map<String, Integer> map() {
        return tagMap;
    }
    public ExtraTagUtil putAll(Map<String, Integer> newTagMap) {
        tagMap.putAll(newTagMap);
        return this;
    }
    public ExtraTagUtil put(String tagKey, Integer value) {
        tagMap.put(tagKey, value);
        return this;
    }
    public String jsonStr() {
        return JsonUtil.defaultGson().toJson(tagMap);
    }



    public static void main(String[] args) {
        ExtraTagUtil jsonUtil = ExtraTagUtil.newInstance("{\"a\":1}");
        jsonUtil.put("b", 2).put("a", 3);
        System.out.println(jsonUtil.jsonStr());
    }


}
