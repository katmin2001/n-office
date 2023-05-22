package com.fis.crm.crm_controller;

import com.fis.crm.crm_entity.CrmTaskHistory;
import com.fis.crm.crm_service.TaskHistoryService;
import com.fis.crm.crm_service.impl.TaskHistoryServiceImpl;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/task-history")
public class TaskHistoryController {

    private final TaskHistoryService historyService;

    public TaskHistoryController(TaskHistoryServiceImpl historyService) {
        this.historyService = historyService;
    }

    @GetMapping
    public List<CrmTaskHistory> getAllTaskHistory() {
        return historyService.getAllTaskHistory();
    }

    @GetMapping("/{taskid}")
    public List<CrmTaskHistory> getTaskHistoryByTaskId(@PathVariable Long taskid) {
        return historyService.getTaskHistoryByTaskId(taskid);
    }
}
