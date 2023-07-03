package com.sy.camunda.handler;

import java.util.Map;

public abstract class AbstractTaskExecutionHandler {

    protected Long longValue(Map<String, Object> map, String key) {
        if (!map.containsKey(key)) return null;
        return Long.valueOf(map.get(key).toString());
    }

    protected Integer intValue(Map<String, Object> map, String key) {
        if (!map.containsKey(key)) return null;
        return Integer.valueOf(map.get(key).toString());
    }


    protected String stringValue(Map<String, Object> map, String key) {
        if (!map.containsKey(key)) return null;
        return map.get(key).toString();
    }

    protected Boolean boolValue(Map<String, Object> map, String key) {
        if (!map.containsKey(key)) return null;
        return Boolean.valueOf(map.get(key).toString());
    }


}
