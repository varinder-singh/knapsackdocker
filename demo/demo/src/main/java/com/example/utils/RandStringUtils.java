package com.example.utils;

import org.apache.commons.lang3.RandomStringUtils;

public class RandStringUtils {

    public static String generateRandomString(){
        return RandomStringUtils.randomAlphanumeric(8);
    }

}
