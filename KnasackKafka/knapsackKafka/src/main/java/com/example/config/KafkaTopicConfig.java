package com.example.config;

import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaAdmin;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaTopicConfig {

    @Value(value= "${kafka.bootstrapaddress}")
    private String bootstrapAdd;

    @Value(value = "#{new Integer('${kafka.partitions}')}")
    private Integer partitions;

    @Value(value = "#{new Integer('${kafka.replications}')}")
    private Integer replications;
    @Bean
    public KafkaAdmin kafkaAdmin(){
        Map<String, Object> configs = new HashMap<String, Object>();
        configs.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAdd);
        return new KafkaAdmin(configs);
    }

    @Bean
    public NewTopic knapsackTopic(){
        return new NewTopic("knapsack", partitions.intValue(), replications.shortValue());
    }
}
