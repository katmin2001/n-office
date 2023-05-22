package com.fis.crm.crm_controller;

import com.fis.crm.crm_entity.CrmTaskStatus;
import com.fis.crm.crm_repository.TaskStatusRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
@RestController
@RequestMapping("/task-status")
public class TaskStatusController {
    @Autowired
    private final TaskStatusRepo taskStatusRepo;

    public TaskStatusController(TaskStatusRepo taskStatusRepo) {
        this.taskStatusRepo = taskStatusRepo;
    }

    @GetMapping("")
    public List<CrmTaskStatus> getStatusTask() {
        List<CrmTaskStatus> crmTaskStatus = taskStatusRepo.findAll();
        return crmTaskStatus;
    }
}
