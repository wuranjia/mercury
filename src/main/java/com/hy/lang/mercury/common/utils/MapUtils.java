package com.hy.lang.mercury.common.utils;

import java.util.HashMap;
import java.util.Map;

public class MapUtils {
    public static Map buildKeyValueMap(Object... conditions) {
        Map map = new HashMap();
        for (int i = 0; i < conditions.length; i += 2) {
            map.put(conditions[i], conditions[i + 1]);
        }
        return map;
    }
}
