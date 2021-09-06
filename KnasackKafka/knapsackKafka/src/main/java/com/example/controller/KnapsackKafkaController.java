package com.example.controller;

import com.example.services.Producer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class KnapsackKafkaController {

    @Autowired
    Producer producer;

    @PostMapping(value = "/publish")
    public Object submitKnapsackTaskToKafka(@RequestBody String payload){
        this.producer.sendMessage(payload);
        return new ResponseEntity<String>("Successfully processed ", HttpStatus.OK);
    }

}
