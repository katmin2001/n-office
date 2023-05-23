package com.fis.crm.crm_controller;

import com.fis.crm.crm_entity.CrmProjectPrivacy;
import com.fis.crm.crm_service.CrmProjectPrivacyService;
import com.fis.crm.crm_service.impl.CrmProjectPrivacyServiceImpl;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/projectPrivacy")
public class CrmProjectPrivacyController {
    private final CrmProjectPrivacyService projectPrivacyService;

    public CrmProjectPrivacyController(CrmProjectPrivacyServiceImpl projectPrivacyService) {
        this.projectPrivacyService = projectPrivacyService;
    }

    @GetMapping("/")
    public List<CrmProjectPrivacy> getAllProjectPrivacies() {
        return projectPrivacyService.getAllProjectPrivacies();
    }

    @GetMapping("/{privacyId}")
    public CrmProjectPrivacy getProjectPrivaciesById(@PathVariable Byte privacyId) {
        return projectPrivacyService.getProjectPrivaciesById(privacyId);
    }

    @PutMapping("/{privacyId}")
    public CrmProjectPrivacy updateProjectPrivacy(@PathVariable Byte privacyId, @RequestBody CrmProjectPrivacy projectPrivacyUpdate) {
        return projectPrivacyService.updateProjectPrivacy(privacyId, projectPrivacyUpdate);
    }

    @DeleteMapping("/{privacyId}")
    public CrmProjectPrivacy deleteProjectPrivacy(@PathVariable Byte privacyId){
        return projectPrivacyService.deleteProjectPrivacy(privacyId);
    }
}
