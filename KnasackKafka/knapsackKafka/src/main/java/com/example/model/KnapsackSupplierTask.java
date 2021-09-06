package com.example.model;

import com.example.utils.KnapsackSolver;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

import java.util.function.Supplier;

public class KnapsackSupplierTask implements Supplier {

    private String taskId;

    private KnapsackProblem knapsackProblem;

    public KnapsackSupplierTask(String taskId, KnapsackProblem knapsackProblem){
        this.taskId = taskId;
        this.knapsackProblem=knapsackProblem;
    }
    /**
     * Gets a result.
     *
     * @return a result
     */
    @Override
    public Object get() {
        // Call the Util method of computing knapsack
        KnapsackSolution knapsackSolution = KnapsackSolver.solve(this.knapsackProblem);
        return new ImmutablePair<>(this.taskId, knapsackSolution);
    }
}
