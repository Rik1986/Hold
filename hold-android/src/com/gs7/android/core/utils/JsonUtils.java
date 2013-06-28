package com.gs7.android.core.utils;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig;

/**
 * @author Administrator
 * 
 */
public class JsonUtils {

    public static ObjectMapper objectMapper = new ObjectMapper();

    static {

        objectMapper.configure(SerializationConfig.Feature.WRITE_DATES_AS_TIMESTAMPS, false);
        objectMapper.getDeserializationConfig().setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS"));
        objectMapper.getSerializationConfig().setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS"));
        objectMapper.configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
        objectMapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_CONTROL_CHARS, true);
        // ALLOW_BACKSLASH_ESCAPING_ANY_CHARACTER
        // objectMapper.configure(SerializationConfig.Feature.WRITE_NULL_MAP_VALUES,
        // false); //配置不写value为null的key
    }

    public static String writerAsString(String key, Object value) throws JsonGenerationException, JsonMappingException,
            IOException {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put(key, value);
        return objectMapper.writeValueAsString(map);
    }

}
