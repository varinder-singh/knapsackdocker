package com.example.repository;

import com.example.entity.Knapsack;
import com.example.model.KnapsackTaskDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface KnapsackRepository {
    /*
    We need a repository method which can fetch the knapsack object stored in our DB.
     */
    Knapsack getKnapsackByTaskId(String taskId);

    void createKnapsackTask(KnapsackTaskDTO knapsackDTO) throws JsonProcessingException;

    List<Knapsack> getAllKnapsackTasks();
}
