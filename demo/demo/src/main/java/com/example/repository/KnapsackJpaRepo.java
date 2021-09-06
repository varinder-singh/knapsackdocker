package com.example.repository;

import com.example.entity.Knapsack;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.orm.jpa.JpaTransactionManager;

@NoRepositoryBean
public interface KnapsackJpaRepo extends JpaRepository<Knapsack, Integer>, KnapsackJpaCustomMethodsRepo {

}
