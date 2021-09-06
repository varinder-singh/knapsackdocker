package com.example.repository.impl;

import com.example.entity.Knapsack;
import com.example.repository.KnapsackJpaCustomMethodsRepo;
import com.example.repository.KnapsackJpaRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;


@Repository
@Transactional
public class KnapsackJpaRepoImpl implements KnapsackJpaCustomMethodsRepo {

    @Autowired
    private EntityManager entityManager;

    @Override
    public String saveKnapsackTask(Knapsack knapsack){
        this.entityManager.persist(knapsack);
        this.entityManager.flush();
        return knapsack.getTaskId();
    }

}
