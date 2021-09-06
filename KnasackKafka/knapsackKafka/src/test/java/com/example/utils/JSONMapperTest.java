package com.example.utils;

import com.example.model.KnapsackProblem;
import org.junit.Test;

import java.io.IOException;

public class JSONMapperTest {

    @Test
    public void testConvertJsonToObject(){
        String json = "{\"capacity\": 60, \"weights\": [10, 20, 33], \"values\": [10, 3, 30]}";
        try {
            KnapsackProblem knapsackProblem = (KnapsackProblem) JSONMapper.convertJsonToObject(json, KnapsackProblem.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
//        assertTrue("This will succeed.", true);
    }
}
