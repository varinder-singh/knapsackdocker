package com.example.controller;

import com.example.model.KnapsackProblem;
import com.example.services.IKnapsackTaskService;
import com.example.utils.JSONMapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.json.*;
import java.io.IOException;

@Controller
public class KnapsackController {

    public static String PROBLEM_KEY = "problem";
    @Autowired
    IKnapsackTaskService knapsackTaskService;

    @PostMapping(value = "/knapsack", produces = MediaType.APPLICATION_JSON_VALUE)
    public Object createKnapsackTask(@RequestBody String payload){
        ResponseEntity response = null;
        try {
            KnapsackProblem knapsackProblem = (KnapsackProblem) JSONMapper.convertJsonToObject(new JSONObject(payload).getJSONObject(PROBLEM_KEY).toString(), KnapsackProblem.class);
            response =  new ResponseEntity<String>(JSONMapper.convertObjectToJson(knapsackTaskService.createKnapsackTask(knapsackProblem)), HttpStatus.OK);
        }
        catch (JsonProcessingException e) {
            return new ResponseEntity<String>("Thrown exception "+e, HttpStatus.INTERNAL_SERVER_ERROR);
        }catch (IOException e){
            return new ResponseEntity<String>("Thrown exception "+e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return response;
    }

    @GetMapping(value = "/knapsack/{taskId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Object getKnapsackTaskByTaskId(@PathVariable("taskId") String taskId){
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String json = null;
        try {
            return new ResponseEntity<String>(JSONMapper.convertObjectToJson(knapsackTaskService.getKnapsackTaskByTaskId(taskId)), HttpStatus.OK);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return new ResponseEntity<String>("Exception in parsing object to JSON : "+e, HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (IOException e) {
            e.printStackTrace();
            return new ResponseEntity<String>("Exception in parsing object to JSON : " + e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/knapsack/tasks/", produces = MediaType.APPLICATION_JSON_VALUE)
    public Object getAllKnapsackTasks(){
        try {
            return new ResponseEntity<String>(JSONMapper.convertObjectToJson(knapsackTaskService.getAllKnapsackTasks()), HttpStatus.OK);
        } catch (IOException e) {
            e.printStackTrace();
            return new ResponseEntity<String>("Exception in parsing object to JSON : " + e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
