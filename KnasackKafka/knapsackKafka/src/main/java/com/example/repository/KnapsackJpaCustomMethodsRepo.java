package com.example.repository;

import com.example.entity.Knapsack;

public interface KnapsackJpaCustomMethodsRepo {
    public void saveKnapsackTask(Knapsack knapsack);
    public Knapsack findKnapsackTaskByTaskId(String taskId);
}
