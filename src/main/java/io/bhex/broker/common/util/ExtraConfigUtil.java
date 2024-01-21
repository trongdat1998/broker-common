package io.bhex.broker.common.util;

import com.google.common.collect.Maps;
import com.google.gson.reflect.TypeToken;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;

public class ExtraConfigUtil {

    private Map<String, String> configMap = Maps.newHashMap();

    private ExtraConfigUtil(String configMapStr) {
        if (!StringUtils.isEmpty(configMapStr)) {
            configMap = JsonUtil.defaultGson().fromJson(configMapStr,
                    new TypeToken<HashMap<String, String>>() {}.getType());
        }
    }
    private ExtraConfigUtil(Map<String, String> configMap) {
        if (!StringUtils.isEmpty(configMap)) {
            this.configMap = Maps.newHashMap(configMap);
        }
    }

    public static ExtraConfigUtil newInstance(Map<String, String> configMap) {
        return new ExtraConfigUtil(configMap);
    }
    public static ExtraConfigUtil newInstance(String configMapStr) {
        return new ExtraConfigUtil(configMapStr);
    }

    public String getConfigValue(String tagKey) {
        return configMap.getOrDefault(tagKey, "");
    }
    public Map<String, String> map() {
        return configMap;
    }
    public ExtraConfigUtil putAll(Map<String, String> newConfigMap) {
        configMap.putAll(newConfigMap);
        return this;
    }
    public ExtraConfigUtil put(String tagKey, String value) {
        configMap.put(tagKey, value);
        return this;
    }
    public String jsonStr() {
        return JsonUtil.defaultGson().toJson(configMap);
    }



    public static void main(String[] args) {
        ExtraConfigUtil jsonUtil = ExtraConfigUtil.newInstance("{\"a\":\"1\"}");
        jsonUtil.put("b", "2").put("a", "3");
        System.out.println(jsonUtil.getConfigValue("b"));
    }


}
