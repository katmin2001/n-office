package com.fis.crm.crm_service;

import com.fis.crm.crm_entity.CrmTaskStatus;
import org.springframework.stereotype.Service;

@Service
public interface TaskStatusService {

    public CrmTaskStatus getStatusCode(Long id);
}
