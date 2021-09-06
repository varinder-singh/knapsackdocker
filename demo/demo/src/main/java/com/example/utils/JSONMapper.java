package com.example.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;


import java.io.IOException;
import java.util.List;

public class JSONMapper {

    public static String convertObjectToJson(Object obj) throws JsonProcessingException {
        String json = null;
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        json = ow.writeValueAsString(obj);
        return json;
    }

    public static String convertObjectToJson(List<Object> objs) throws JsonProcessingException {
        String json = null;
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        json = ow.writeValueAsString(objs);
        return json;
    }

    public static Object convertJsonToObject(String json, Class type) throws IOException {
        Object obj =  new ObjectMapper().readValue(json, type);
        return obj;
    }
}
