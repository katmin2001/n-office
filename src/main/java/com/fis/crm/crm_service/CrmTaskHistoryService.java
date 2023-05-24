package com.fis.crm.crm_service;

import com.fis.crm.crm_entity.CrmTaskHistory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CrmTaskHistoryService {

    public CrmTaskHistory saveTaskHistory(CrmTaskHistory taskHistory);

    public List<CrmTaskHistory> getAllTaskHistory();

    public List<CrmTaskHistory> getTaskHistoryByTaskId(Long id);
}
