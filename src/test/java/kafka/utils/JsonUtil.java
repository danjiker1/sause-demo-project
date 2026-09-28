package kafka.utils;

import tools.jackson.databind.ObjectMapper;

public class JsonUtil {
    public static ObjectMapper mapper;
    public static String toJson(Object object){
        return mapper.writeValueAsString(object);
    }
    public static <T> T fromJson(String json, Class <T> clazz){
        return mapper.readValue(json,clazz);
    }
}
