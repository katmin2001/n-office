package com.fis.crm.crm_service.impl;

import com.fis.crm.crm_entity.CrmStage;
import com.fis.crm.crm_entity.CrmStageRequest;
import com.fis.crm.crm_repository.CrmStageRepo;
import com.fis.crm.crm_repository.CrmStageRequestRepo;
import com.fis.crm.crm_service.CrmStageService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CrmStageServiceImpl implements CrmStageService {
    private final CrmStageRequestRepo stageRequestRepo;
    private final CrmStageRepo stageRepo;

    public CrmStageServiceImpl(CrmStageRequestRepo stageRequestRepo, CrmStageRepo stageRepo) {
        this.stageRequestRepo = stageRequestRepo;
        this.stageRepo = stageRepo;
    }

    @Override
    public CrmStageRequest createStage(CrmStageRequest stageRequest) {
        return stageRequestRepo.save(stageRequest);
    }

    @Override
    public List<CrmStage> getAllStages() {
        return stageRepo.findAll();
    }

    @Override
    public List<CrmStage> getStagesByProjectId(Long projectId) {
        List<CrmStage> res = new ArrayList<>();
        List<CrmStage> stages = stageRepo.findAll();
        for (CrmStage stage : stages) {
            if (stage.getProject().getId() == projectId) {
                res.add(stage);
            }
        }
        return res;
    }

    @Override
    public CrmStage getStageById(Long stageId) {
        return stageRepo.findById(stageId).orElse(null);
    }

    @Override
    public CrmStageRequest updateStageById(Long stageId, CrmStageRequest stageUpdate) {
        CrmStageRequest existingStage = stageRequestRepo.findById(stageId).orElse(null);
        if (existingStage != null) {
            if (stageUpdate.getName() != null) existingStage.setName(stageUpdate.getName());
            if (stageUpdate.getDescription() != null) existingStage.setDescription(stageUpdate.getDescription());
            if (stageUpdate.getStartDate() != null) existingStage.setStartDate(stageUpdate.getStartDate());
            if (stageUpdate.getEndDate() != null) existingStage.setEndDate(stageUpdate.getEndDate());
            if (stageUpdate.getFinishDate() != null) existingStage.setFinishDate(stageUpdate.getFinishDate());
            if (stageUpdate.getProjectId() != null) existingStage.setProjectId(stageUpdate.getProjectId());

        }
        return stageRequestRepo.save(existingStage);
    }
}
