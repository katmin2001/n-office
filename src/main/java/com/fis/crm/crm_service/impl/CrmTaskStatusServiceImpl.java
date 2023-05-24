package com.fis.crm.crm_service.impl;

import com.fis.crm.crm_entity.CrmTaskStatus;
import com.fis.crm.crm_repository.CrmTaskStatusRepo;
import com.fis.crm.crm_service.CrmTaskStatusService;
import org.springframework.stereotype.Service;

@Service
public class CrmTaskStatusServiceImpl implements CrmTaskStatusService {
    private final CrmTaskStatusRepo statusRepo;

    public CrmTaskStatusServiceImpl(CrmTaskStatusRepo statusRepo) {
        this.statusRepo = statusRepo;
    }

    /**
     * @param id
     * @return
     */
    @Override
    public CrmTaskStatus getStatusCode(Long id) {
        return statusRepo.findStatus(id);
    }
}
