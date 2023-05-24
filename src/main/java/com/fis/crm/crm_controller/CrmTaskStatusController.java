package com.fis.crm.crm_controller;

import com.fis.crm.crm_entity.CrmTaskStatus;
import com.fis.crm.crm_repository.CrmTaskStatusRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
@RestController
@RequestMapping("/task-status")
public class CrmTaskStatusController {
    @Autowired
    private final CrmTaskStatusRepo taskStatusRepo;

    public CrmTaskStatusController(CrmTaskStatusRepo taskStatusRepo) {
        this.taskStatusRepo = taskStatusRepo;
    }

    @GetMapping("")
    public List<CrmTaskStatus> getStatusTask() {
        List<CrmTaskStatus> crmTaskStatus = taskStatusRepo.findAll();
        return crmTaskStatus;
    }
}
