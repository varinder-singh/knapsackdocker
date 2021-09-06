package com.example.services;

import com.example.entity.Knapsack;
import com.example.enums.TaskStatus;
import com.example.model.*;
import com.example.repository.impl.KnapsackJpaRepoImpl;
import com.example.utils.JSONMapper;
import org.apache.commons.lang3.tuple.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.Map;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicReference;

@Service
public class Consumer {

    /* Static members */
    public static Logger logger = LoggerFactory.getLogger(Consumer.class);

    private static final ThreadPoolExecutor service = new ThreadPoolExecutor(5,10,600, TimeUnit.SECONDS,new ArrayBlockingQueue<Runnable>(100));

    private static Map<String, KnapsackSolution> futureTaskMap = new ConcurrentHashMap<String, KnapsackSolution>();


    /* Instance members */
    @Autowired
    private KnapsackJpaRepoImpl knapsackJpaRepo;

    @KafkaListener(topics = "knapsack", groupId = "knapsack-group", containerFactory = "kafkaListenerContainerFactory")
    public void consume(String message, Acknowledgment acknowledgment) {

        logger.info("Consumed a message from Kafka : " + message);
        try {
            KnapsackTaskDTO knapsackTaskDTO = (KnapsackTaskDTO) JSONMapper.convertJsonToObject(message, KnapsackTaskDTO.class);
            Knapsack knapsack = this.knapsackJpaRepo.findKnapsackTaskByTaskId(knapsackTaskDTO.getTaskId());
            if (!knapsack.getStatus().equals(TaskStatus.COMPLETED.getTaskStatus())) {
                this.changeKnapsackState(knapsack);
            } else {
                acknowledgment.acknowledge();
                logger.debug("Knapsack Task with Id {} and status {} is in topic to get deleted based on topic purge policy", knapsack.getTaskId(), knapsack.getStatus());
            }
        } catch (IOException | ExecutionException | InterruptedException e) {
            logger.error("Error while consuming message with Message {}", message);
        }
    }

    private void changeKnapsackState(Knapsack knapsack) throws IOException, ExecutionException, InterruptedException {
        if(knapsack.getStatus().equals(TaskStatus.SUBMITTED.getTaskStatus())){
//            FutureTask<Pair<String, KnapsackSolution>> future = (FutureTask<Pair<String,KnapsackSolution>>) service.submit(new KnapsackCallableTask(knapsack.getTaskId(),(KnapsackProblem) JSONMapper.convertJsonToObject(knapsack.getProblem(), KnapsackProblem.class)));
//            futureTaskMap.put(future.get().getKey(), future.get().getValue()); //it was blocking
            knapsack.setStatus(TaskStatus.STARTED.getTaskStatus());
            AtomicReference<KnapsackTimestamps> knapsackTimestamps = new AtomicReference<>((KnapsackTimestamps) JSONMapper.convertJsonToObject(knapsack.getTimestamps(), KnapsackTimestamps.class));
            knapsackTimestamps.get().setStarted(new Timestamp(System.currentTimeMillis()));
            knapsack.setTimestamps(JSONMapper.convertObjectToJson(knapsackTimestamps));
            knapsackJpaRepo.saveKnapsackTask(knapsack);
            CompletableFuture.supplyAsync(new KnapsackSupplierTask(knapsack.getTaskId(),(KnapsackProblem) JSONMapper.convertJsonToObject(knapsack.getProblem(), KnapsackProblem.class)),service).thenApply((knapsackSolutionPair)->{
                Pair <String, KnapsackSolution> pair = (Pair<String, KnapsackSolution>) knapsackSolutionPair;
                if(!knapsack.getSolution().isEmpty()) {
                    knapsack.setStatus(TaskStatus.COMPLETED.getTaskStatus());
                    try {
                        knapsackTimestamps.set((KnapsackTimestamps) JSONMapper.convertJsonToObject(knapsack.getTimestamps(), KnapsackTimestamps.class));
                        knapsackTimestamps.get().setCompleted(new Timestamp(System.currentTimeMillis()));
                        knapsack.setTimestamps(JSONMapper.convertObjectToJson(knapsackTimestamps));
                        knapsack.setSolution(JSONMapper.convertObjectToJson(pair.getValue()));
                    } catch (IOException e) {
                        logger.error("Error occurred whilst completing the task with stack "+e);
                    }

                    knapsackJpaRepo.saveKnapsackTask(knapsack);

                }
                return "Completed task "+pair.getKey();
            }); //(Consumer::changeKnapsackStateToCompleted);

        }
//        else if(knapsack.getStatus().equals(TaskStatus.STARTED.getTaskStatus())){
//            if(!knapsack.getSolution().isEmpty()){
//                if(futureTaskMap.size()!=0){
//                    String taskId = knapsack.getTaskId();
//                    if(futureTaskMap.containsKey(taskId)){
//                        knapsack.setStatus(TaskStatus.COMPLETED.getTaskStatus());
//                        KnapsackTimestamps knapsackTimestamps = (KnapsackTimestamps) JSONMapper.convertJsonToObject(knapsack.getTimestamps(), KnapsackTimestamps.class);
//                        knapsackTimestamps.setCompleted(new Timestamp(System.currentTimeMillis()));
//                        knapsack.setTimestamps(JSONMapper.convertObjectToJson(knapsackTimestamps));
//                        knapsack.setSolution(JSONMapper.convertObjectToJson(futureTaskMap.get(taskId)));
//                        knapsackJpaRepo.saveKnapsackTask(knapsack);
//
//                        // remove the element from the list
//                        futureTaskMap.remove(taskId);
//                    }
//                }
//
//            }
//
//        }
    }

}
