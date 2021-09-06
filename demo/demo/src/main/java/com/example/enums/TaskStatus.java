package com.example.enums;

public enum TaskStatus {
    SUBMITTED("Submitted"),
    STARTED("Started"),
    COMPLETED("Completed");

    private String taskStatus;
    private TaskStatus(String val){
        this.taskStatus = val;
    }

    public String getTaskStatus(){
        return this.taskStatus;
    }

}
