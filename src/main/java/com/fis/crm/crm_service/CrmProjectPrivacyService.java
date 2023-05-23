package com.fis.crm.crm_service;

import com.fis.crm.crm_entity.CrmProjectPrivacy;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Service
public interface CrmProjectPrivacyService {
    public List<CrmProjectPrivacy> getAllProjectPrivacies();
    public CrmProjectPrivacy getProjectPrivaciesById(Byte privacyId);
    public CrmProjectPrivacy updateProjectPrivacy(Byte privacyId,CrmProjectPrivacy projectPrivacyUpdate);
    public CrmProjectPrivacy deleteProjectPrivacy(Byte privacyId);

}
