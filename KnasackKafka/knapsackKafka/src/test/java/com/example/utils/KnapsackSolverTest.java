package com.example.utils;

import com.example.entity.Knapsack;
import com.example.model.KnapsackProblem;
import com.example.model.KnapsackSolution;
import org.assertj.core.util.Lists;
import org.junit.Before;
import org.junit.Test;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;

public class KnapsackSolverTest {

    private KnapsackProblem knapsackProblem;

    @Before
    public void setUp(){
        this.knapsackProblem = new KnapsackProblem();
        this.knapsackProblem.setCapacity(60);


        List<Integer> weights = new ArrayList<Integer>();
        weights.add(10);
        weights.add(20);
        weights.add(33);

        List<Integer> values = new ArrayList<Integer>();
        values.add(10);
        values.add(3);
        values.add(30);

        this.knapsackProblem.setValues(values);
        this.knapsackProblem.setWeights(weights);
    }
    @Test
    public void testSolve(){
        List<Integer> packedItemsActual = Arrays.asList(0,2);
        KnapsackSolution knapsackSolution = KnapsackSolver.solve(this.knapsackProblem);
        assertEquals(knapsackSolution.getTotalValue(),40);
        assertEquals(packedItemsActual,knapsackSolution.getPackedItems());

    }
}
