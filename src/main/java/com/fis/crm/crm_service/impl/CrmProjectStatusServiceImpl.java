package com.fis.crm.crm_service.impl;

import com.fis.crm.crm_entity.CrmProjectStatus;
import com.fis.crm.crm_repository.CrmProjectStatusRepo;
import com.fis.crm.crm_service.CrmProjectStatusService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CrmProjectStatusServiceImpl implements CrmProjectStatusService {
    private final CrmProjectStatusRepo statusRepo;

    public CrmProjectStatusServiceImpl(CrmProjectStatusRepo statusRepo) {
        this.statusRepo = statusRepo;
    }

    @Override
    public List<CrmProjectStatus> getAllProjectStatus() {
        return statusRepo.findAll();
    }

    @Override
    public CrmProjectStatus getProjectStatusById(Byte statusId) {
        return statusRepo.findById(statusId).orElse(null);
    }

    @Override
    public CrmProjectStatus updateProjectStatus(Byte statusId, CrmProjectStatus projectStatusUpdate) {
        CrmProjectStatus existingStatus = statusRepo.findById(statusId).orElse(null);
        if (existingStatus != null) {
            if (projectStatusUpdate.getName() != null) existingStatus.setName(projectStatusUpdate.getName());
            return statusRepo.save(existingStatus);
        }
        return null;
    }

    @Override
    public CrmProjectStatus deleteProjectStatusById(Byte statusId) {
        CrmProjectStatus existingStatus = statusRepo.findById(statusId).orElse(null);
        if (existingStatus != null) {
            statusRepo.delete(existingStatus);
            return existingStatus;
        }
        return null;
    }
}
