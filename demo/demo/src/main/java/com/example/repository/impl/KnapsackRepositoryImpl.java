package com.example.repository.impl;

import com.example.entity.Knapsack;
import com.example.enums.TaskStatus;
import com.example.model.KnapsackTaskDTO;
import com.example.repository.KnapsackRepository;
import com.example.utils.JSONMapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.List;

@Repository
public class KnapsackRepositoryImpl implements KnapsackRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional
    public Knapsack getKnapsackByTaskId(String taskId) {
        String jpql = "SELECT k FROM Knapsack k WHERE k.taskId=:taskId";
        Query knapsackTypedQuery = this.entityManager.createQuery(jpql, Knapsack.class);
        knapsackTypedQuery.setParameter("taskId", taskId);
        return (Knapsack) knapsackTypedQuery.getSingleResult();
    }

    @Override
    @Transactional
    public void createKnapsackTask(KnapsackTaskDTO knapsackDTO) throws JsonProcessingException {
        Knapsack knapsack = new Knapsack();
        knapsack.setStatus(TaskStatus.SUBMITTED.getTaskStatus());
        knapsack.setTimestamps(JSONMapper.convertObjectToJson(knapsackDTO.getKnapsackTimestamps()));
        knapsack.setProblem(JSONMapper.convertObjectToJson(knapsackDTO.getKnapsackProblem()));
        knapsack.setSolution(JSONMapper.convertObjectToJson(knapsackDTO.getKnapsackSolution()));
        this.entityManager.persist(knapsack);
        this.entityManager.flush();
        knapsackDTO.setTaskId(knapsack.getTaskId());
    }

    @Override
    @Transactional
    public List<Knapsack> getAllKnapsackTasks() {
        String jpql = "SELECT k FROM Knapsack k";
        TypedQuery<Knapsack> knapsackTypedQuery = this.entityManager.createQuery(jpql, Knapsack.class);
        return knapsackTypedQuery.getResultList();
    }
}
