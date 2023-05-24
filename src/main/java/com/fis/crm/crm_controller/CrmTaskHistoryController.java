package com.fis.crm.crm_controller;

import com.fis.crm.crm_entity.CrmTaskHistory;
import com.fis.crm.crm_service.CrmTaskHistoryService;
import com.fis.crm.crm_service.impl.CrmTaskHistoryServiceImpl;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/task-history")
public class CrmTaskHistoryController {

    private final CrmTaskHistoryService historyService;

    public CrmTaskHistoryController(CrmTaskHistoryServiceImpl historyService) {
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
