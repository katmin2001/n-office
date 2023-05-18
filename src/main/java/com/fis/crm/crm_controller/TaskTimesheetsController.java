package com.fis.crm.crm_controller;

import com.fis.crm.crm_entity.CrmTask;
import com.fis.crm.crm_entity.CrmTaskTimesheets;
import com.fis.crm.crm_entity.DTO.TaskDTO;
import com.fis.crm.crm_service.TaskTimesheetsService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/task-timesheets")
public class TaskTimesheetsController {
    private final TaskTimesheetsService timesheetsService;

    public TaskTimesheetsController(TaskTimesheetsService taskTimesheetsService) {
        this.timesheetsService = taskTimesheetsService;
    }

    @GetMapping
    public List<CrmTaskTimesheets> getAllTimesheets() {
        return timesheetsService.getAllTimesheets();
    }

    @PostMapping("/{taskId}")
    public CrmTaskTimesheets createTimesheets(@PathVariable Long taskId, @RequestBody CrmTaskTimesheets newTimesheets) {
        return timesheetsService.createTimesheets(taskId, newTimesheets);
    }
}

