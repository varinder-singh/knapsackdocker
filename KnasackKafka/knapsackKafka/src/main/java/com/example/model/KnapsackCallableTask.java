package com.example.model;

import com.example.entity.Knapsack;
import com.example.utils.KnapsackSolver;
import javafx.util.Pair;

import java.util.concurrent.Callable;

public class KnapsackCallableTask implements Callable {

    private String taskId;

    private KnapsackProblem knapsackProblem;

    private Pair<String, Object> knapsackPair;

    public KnapsackCallableTask(String taskId, KnapsackProblem knapsackProblem){
        this.taskId = taskId;
        this.knapsackProblem=knapsackProblem;
        this.knapsackPair = new Pair<>(taskId,knapsackProblem);
    }


    /**
     * Computes a result, or throws an exception if unable to do so.
     *
     * @return computed result
     * @throws Exception if unable to compute a result
     */
    @Override
    public Object call() throws Exception {
        // Call the Util method of computing knapsack
        KnapsackSolution knapsackSolution = KnapsackSolver.solve(this.knapsackProblem);
        return new Pair<String, KnapsackSolution>(this.taskId, knapsackSolution);
    }
}
