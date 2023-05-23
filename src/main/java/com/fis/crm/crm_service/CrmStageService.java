package com.fis.crm.crm_service;

import com.fis.crm.crm_entity.CrmStage;
import com.fis.crm.crm_entity.CrmStageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public interface CrmStageService {
    public CrmStageRequest createStage(CrmStageRequest stageRequest);
    public List<CrmStage> getAllStages();
    public List<CrmStage> getStagesByProjectId(Long projectId);
    public CrmStage getStageById(Long stageId);
    public CrmStageRequest updateStageById(Long stageId, CrmStageRequest stageRequest);
//    deleteStageById(long stageId);
}
