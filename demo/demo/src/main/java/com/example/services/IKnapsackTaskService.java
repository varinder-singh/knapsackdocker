package com.example.services;

import com.example.model.KnapsackProblem;
import com.example.model.KnapsackTaskDTO;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.io.IOException;
import java.util.List;

public interface IKnapsackTaskService {

    KnapsackTaskDTO createKnapsackTask(KnapsackProblem knapsackProblem) throws JsonProcessingException;

    KnapsackTaskDTO getKnapsackTaskByTaskId(String taskId) throws IOException;

    List<KnapsackTaskDTO> getAllKnapsackTasks() throws IOException;
}
