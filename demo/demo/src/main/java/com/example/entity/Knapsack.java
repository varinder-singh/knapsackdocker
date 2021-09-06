package com.example.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name="KNAPSACK")
public class Knapsack implements Serializable {

    @Id
    @GenericGenerator(name = "knapsackSeq", strategy = "com.example.generator.StringSequenceGenerator")
    @GeneratedValue(generator = "knapsackSeq")
    @Column(name="TASK_ID")
    private String taskId;

    @Column(name="TASK_STATUS")
    private String status;

    @Lob @Basic
    @Column(name="TASK_TIMESTAMPS")
    private String timestamps;

    @Lob @Basic
    @Column(name ="TASK_PROBLEM")
    private String problem;

    @Lob @Basic
    @Column(name ="TASK_SOLUTION")
    private String solution;

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTimestamps() {
        return timestamps;
    }

    public void setTimestamps(String timestamps) {
        this.timestamps = timestamps;
    }

    public String getProblem() {
        return problem;
    }

    public void setProblem(String problem) {
        this.problem = problem;
    }

    public String getSolution() {
        return solution;
    }

    public void setSolution(String solution) {
        this.solution = solution;
    }

}
