package com.example.services.impl;

import com.example.entity.Knapsack;
import com.example.enums.TaskStatus;
import com.example.model.KnapsackProblem;
import com.example.model.KnapsackSolution;
import com.example.model.KnapsackTaskDTO;
import com.example.model.KnapsackTimestamps;
import com.example.repository.KnapsackRepository;
import com.example.repository.impl.KnapsackJpaRepoImpl;
import com.example.services.IKnapsackTaskService;
import com.example.utils.JSONMapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Service
public class KnapsackTaskService implements IKnapsackTaskService {

    public static Logger logger = LoggerFactory.getLogger(KnapsackTaskService.class);

    @Value(value="${knapsack.solver.url}")
    private String knapsackSolverServiceUrl;

    @Autowired
    private KnapsackRepository knapsackRepository;

    @Autowired
    private KnapsackJpaRepoImpl knapsackJpaRepo;

    @Override
    public KnapsackTaskDTO createKnapsackTask(KnapsackProblem knapsackProblem) throws JsonProcessingException {
        Knapsack knapsack = new Knapsack();
        KnapsackTaskDTO knapsackTaskDTO = new KnapsackTaskDTO();
        //Set the DTO Object
        knapsack.setStatus(TaskStatus.SUBMITTED.getTaskStatus());
        knapsackTaskDTO.setKnapsackProblem(knapsackProblem);
        //Set TimeStamps
        knapsackTaskDTO.setKnapsackTimestamps(this.initializeKnapsackTimestamps());
        // Set the persistent object
        knapsack.setTimestamps(JSONMapper.convertObjectToJson(knapsackTaskDTO.getKnapsackTimestamps()));
        knapsack.setProblem(JSONMapper.convertObjectToJson(knapsackTaskDTO.getKnapsackProblem()));
        knapsack.setSolution(JSONMapper.convertObjectToJson(knapsackTaskDTO.getKnapsackSolution()));

        knapsack.setProblem(JSONMapper.convertObjectToJson(knapsackTaskDTO.getKnapsackProblem()));
        String taskId = this.knapsackJpaRepo.saveKnapsackTask(knapsack);
        knapsackTaskDTO.setTaskId(taskId);
        if(taskId!=null){
            CloseableHttpClient httpClient = HttpClients.createDefault();
            HttpPost httpPost = new HttpPost(knapsackSolverServiceUrl);
            try {
                StringEntity payload = new StringEntity(JSONMapper.convertObjectToJson(knapsackTaskDTO));
                httpPost.setEntity(payload);
                httpPost.setHeader("Accept", "application/json");
                httpPost.setHeader("Content-type", "application/json");

                CloseableHttpResponse response = httpClient.execute(httpPost);
                if(response.getStatusLine().getStatusCode()!= HttpStatus.OK.value()){
                    logger.info("Error while publishing message to kafka Task Id {}",knapsackTaskDTO.getTaskId());
                }
            } catch (UnsupportedEncodingException e) {
                logger.error("Exception thrown whilst publishing message to Kafka Service {}",e.getMessage());
            } catch (ClientProtocolException e) {
                logger.error("Exception thrown whilst publishing message to Kafka Service {}",e.getMessage());
            } catch (IOException e) {
                logger.error("Exception thrown whilst publishing message to Kafka Service {}",e.getMessage());
            }
        }
        return knapsackTaskDTO;
    }

    @Override
    public KnapsackTaskDTO getKnapsackTaskByTaskId(String taskId) throws IOException {
        KnapsackTaskDTO knapsackTaskDTO = new KnapsackTaskDTO();
        Knapsack knapsack = knapsackRepository.getKnapsackByTaskId(taskId);

        knapsackTaskDTO.setTaskId(knapsack.getTaskId());
        knapsackTaskDTO.setStatus(knapsack.getStatus());
        knapsackTaskDTO.setKnapsackProblem((KnapsackProblem) JSONMapper.convertJsonToObject(knapsack.getProblem(),KnapsackProblem.class));
        knapsackTaskDTO.setKnapsackTimestamps((KnapsackTimestamps) JSONMapper.convertJsonToObject(knapsack.getTimestamps(), KnapsackTimestamps.class));
        knapsackTaskDTO.setKnapsackSolution((KnapsackSolution) JSONMapper.convertJsonToObject(knapsack.getSolution(), KnapsackSolution.class));
        return knapsackTaskDTO;

    }

    @Override
    public List<KnapsackTaskDTO> getAllKnapsackTasks() throws IOException {
        List<KnapsackTaskDTO> knapsackTaskDTOS = new ArrayList<KnapsackTaskDTO>();
        List<Knapsack> knapsacks =this.knapsackRepository.getAllKnapsackTasks();
        for(Knapsack knapsack : knapsacks){
            KnapsackTaskDTO knapsackTaskDTO = new KnapsackTaskDTO();
            knapsackTaskDTO.setTaskId(knapsack.getTaskId());
            knapsackTaskDTO.setStatus(knapsack.getStatus());
            knapsackTaskDTO.setKnapsackProblem((KnapsackProblem) JSONMapper.convertJsonToObject(knapsack.getProblem(),KnapsackProblem.class));
            knapsackTaskDTO.setKnapsackTimestamps((KnapsackTimestamps) JSONMapper.convertJsonToObject(knapsack.getTimestamps(), KnapsackTimestamps.class));
            knapsackTaskDTO.setKnapsackSolution((KnapsackSolution) JSONMapper.convertJsonToObject(knapsack.getSolution(), KnapsackSolution.class));
            // Add into the list
            knapsackTaskDTOS.add(knapsackTaskDTO);
        }
        return knapsackTaskDTOS;
    }

    private KnapsackTimestamps initializeKnapsackTimestamps(){
        KnapsackTimestamps knapsackTimestamps = new KnapsackTimestamps();
        knapsackTimestamps.setSubmitted(new Timestamp(System.currentTimeMillis()));
        knapsackTimestamps.setStarted(null);
        knapsackTimestamps.setCompleted(null);

        return knapsackTimestamps;
    }
}
