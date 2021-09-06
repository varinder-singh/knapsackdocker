package com.example.model;

public class KnapsackTaskDTO {

    private String taskId;

    private String Status;

    private KnapsackTimestamps knapsackTimestamps;

    private KnapsackProblem knapsackProblem;

    private KnapsackSolution knapsackSolution;

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public KnapsackTimestamps getKnapsackTimestamps() {
        return knapsackTimestamps;
    }

    public void setKnapsackTimestamps(KnapsackTimestamps knapsackTimestamps) {
        this.knapsackTimestamps = knapsackTimestamps;
    }

    public KnapsackProblem getKnapsackProblem() {
        return knapsackProblem;
    }

    public void setKnapsackProblem(KnapsackProblem knapsackProblem) {
        this.knapsackProblem = knapsackProblem;
    }

    public KnapsackSolution getKnapsackSolution() {
        return knapsackSolution;
    }

    public void setKnapsackSolution(KnapsackSolution knapsackSolution) {
        this.knapsackSolution = knapsackSolution;
    }
}
