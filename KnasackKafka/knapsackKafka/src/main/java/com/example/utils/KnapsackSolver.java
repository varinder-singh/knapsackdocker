package com.example.utils;

import com.example.model.KnapsackProblem;
import com.example.model.KnapsackSolution;

import java.util.ArrayList;
import java.util.List;

public class KnapsackSolver {

    public static KnapsackSolution solve(KnapsackProblem knapsackProblem){
        KnapsackSolution knapsackSolution = new KnapsackSolution();
        List<Integer> packedItems = new ArrayList<Integer>();
        int totalValue = 0;

        int capacity = knapsackProblem.getCapacity();
        List<Integer> weightsOriginal = knapsackProblem.getWeights();
        int [] weights = knapsackProblem.getWeights().stream().mapToInt(Integer::intValue).toArray();
        int [] values = knapsackProblem.getValues().stream().mapToInt(Integer::intValue).toArray();

        int N= weights.length+1;
        int W = capacity+1;
        int[][] dp = new int [N][W];

        for(int i=1; i<N; i++){
            for(int j=1; j<W; j++){
                if(weights[i-1]>j){
                    dp[i][j] = 0;
                }
                else{
                    dp[i][j] = Math.max(dp[i - 1][j], values[i - 1] + dp[i - 1][j - weights[i - 1]]);
                    if(dp[i][j]!=dp[i-1][j] && weights[i-1]==j){
                        int index = weightsOriginal.indexOf(weights[i-1]);
                        packedItems.add(index);
//                        weightsOriginal.remove(index);
                    }
                }

            }
        }
        knapsackSolution.setTotalValue(dp[N-1][W-1]);
        knapsackSolution.setPackedItems(packedItems);
        return knapsackSolution;
    }
}
