package com.fis.crm.crm_service.impl;

import com.fis.crm.crm_entity.CrmProjectPrivacy;
import com.fis.crm.crm_entity.CrmProjectStatus;
import com.fis.crm.crm_repository.CrmProjectPrivacyRepo;
import com.fis.crm.crm_service.CrmProjectPrivacyService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CrmProjectPrivacyServiceImpl implements CrmProjectPrivacyService {
    private final CrmProjectPrivacyRepo projectPrivacyRepo;

    public CrmProjectPrivacyServiceImpl(CrmProjectPrivacyRepo projectPrivacyRepo) {
        this.projectPrivacyRepo = projectPrivacyRepo;
    }

    @Override
    public List<CrmProjectPrivacy> getAllProjectPrivacies() {
        return projectPrivacyRepo.findAll();
    }

    @Override
    public CrmProjectPrivacy getProjectPrivaciesById(Byte privacyId) {
        return projectPrivacyRepo.findById(privacyId).orElse(null);
    }

    @Override
    public CrmProjectPrivacy updateProjectPrivacy(Byte privacyId, CrmProjectPrivacy projectPrivacyUpdate) {
        CrmProjectPrivacy existingPrivacy = projectPrivacyRepo.findById(privacyId).orElse(null);
        if (existingPrivacy != null) {
            if (projectPrivacyUpdate.getName() != null) existingPrivacy.setName(projectPrivacyUpdate.getName());
            return projectPrivacyRepo.save(existingPrivacy);
        }
        return null;
    }

    @Override
    public CrmProjectPrivacy deleteProjectPrivacy(Byte privacyId) {
        CrmProjectPrivacy existingPrivacy = projectPrivacyRepo.findById(privacyId).orElse(null);
        if (existingPrivacy != null) {
            projectPrivacyRepo.delete(existingPrivacy);
            return existingPrivacy;
        }
        return null;
    }
}
