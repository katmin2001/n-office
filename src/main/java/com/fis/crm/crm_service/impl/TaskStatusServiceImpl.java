package com.fis.crm.crm_service.impl;

import com.fis.crm.crm_entity.CrmTaskStatus;
import com.fis.crm.crm_repository.TaskStatusRepo;
import com.fis.crm.crm_service.TaskStatusService;
import org.springframework.stereotype.Service;

@Service
public class TaskStatusServiceImpl implements TaskStatusService {
    private final TaskStatusRepo statusRepo;

    public TaskStatusServiceImpl(TaskStatusRepo statusRepo) {
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
