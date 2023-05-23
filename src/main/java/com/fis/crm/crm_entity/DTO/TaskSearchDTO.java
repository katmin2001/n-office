package com.fis.crm.crm_entity.DTO;

import java.util.Date;

public class TaskSearchDTO {
    private String taskname;
    private String statusname;
    private String givertaskname;
    private String receivertaskname;
    private String projectname;
    private String taskProcess;

    public String getTaskname() {
        return taskname;
    }

    public void setTaskname(String taskname) {
        this.taskname = taskname;
    }

    public String getStatusname() {
        return statusname;
    }

    public void setStatusname(String statusname) {
        this.statusname = statusname;
    }

    public String getGivertaskname() {
        return givertaskname;
    }

    public void setGivertaskname(String givertaskname) {
        this.givertaskname = givertaskname;
    }

    public String getReceivertaskname() {
        return receivertaskname;
    }

    public void setReceivertaskname(String receivertaskname) {
        this.receivertaskname = receivertaskname;
    }

    public String getProjectname() {
        return projectname;
    }

    public void setProjectname(String projectname) {
        this.projectname = projectname;
    }

    public String getTaskProcess() {
        return taskProcess;
    }

    public void setTaskProcess(String taskProcess) {
        this.taskProcess = taskProcess;
    }
}
