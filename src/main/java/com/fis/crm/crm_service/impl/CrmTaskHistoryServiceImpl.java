package com.fis.crm.crm_service.impl;

import com.fis.crm.crm_entity.CrmTaskHistory;
import com.fis.crm.crm_repository.CrmTaskHistoryRepo;
import com.fis.crm.crm_service.CrmTaskHistoryService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CrmTaskHistoryServiceImpl implements CrmTaskHistoryService {

    private final CrmTaskHistoryRepo historyRepo;

    public CrmTaskHistoryServiceImpl(CrmTaskHistoryRepo historyRepo) {
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
