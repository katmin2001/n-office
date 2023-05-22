package com.fis.crm.crm_service.impl;

import com.fis.crm.crm_entity.CrmTaskHistory;
import com.fis.crm.crm_repository.TaskHistoryRepo;
import com.fis.crm.crm_service.TaskHistoryService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskHistoryServiceImpl implements TaskHistoryService {

    private final TaskHistoryRepo historyRepo;

    public TaskHistoryServiceImpl(TaskHistoryRepo historyRepo) {
        this.historyRepo = historyRepo;
    }

    /**
     * @param taskHistory
     */
    @Override
    public CrmTaskHistory saveTaskHistory(CrmTaskHistory taskHistory) {
        return historyRepo.save(taskHistory);
    }

    /**
     * @return
     */
    @Override
    public List<CrmTaskHistory> getAllTaskHistory() {
        return historyRepo.findAll();
    }

    /**
     * @return
     */
    @Override
    public List<CrmTaskHistory> getTaskHistoryByTaskId(Long taskid) {
        return historyRepo.findTaskHistoryByTaskId(taskid);
    }
}
