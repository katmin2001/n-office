package com.fis.crm.crm_controller;

import com.fis.crm.crm_entity.CrmStage;
import com.fis.crm.crm_entity.CrmStageRequest;
import com.fis.crm.crm_repository.CrmStageRepo;
import com.fis.crm.crm_repository.CrmStageRequestRepo;
import com.fis.crm.crm_service.CrmStageService;
import com.fis.crm.crm_service.impl.CrmStageServiceImpl;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/stage")
public class CrmStageController {
    private final CrmStageService stageService;

    public CrmStageController(CrmStageServiceImpl stageService) {
        this.stageService = stageService;
    }

    @PostMapping("/")
    public CrmStageRequest createStage(@RequestBody CrmStageRequest stageRequest) {
        return stageService.createStage(stageRequest);
    }

    @GetMapping("/")
    public List<CrmStage> getAllStages() {
        return stageService.getAllStages();
    }

    @GetMapping("/project/{projectId}")
    public List<CrmStage> getStagesByProjectId(@PathVariable Long projectId) {
        return stageService.getStagesByProjectId(projectId);
    }

    @GetMapping("/{stageId}")
    public CrmStage getStageById(@PathVariable Long stageId) {
        return stageService.getStageById(stageId);
    }

    @PutMapping("/{stageId}")
    public CrmStageRequest updateStageById(@PathVariable Long stageId,@RequestBody CrmStageRequest stageUpdate) {
        return stageService.updateStageById(stageId, stageUpdate);
    }

}
