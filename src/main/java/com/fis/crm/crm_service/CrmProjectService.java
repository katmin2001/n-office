package com.fis.crm.crm_service;

import com.fis.crm.crm_entity.CrmProject;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public interface CrmProjectService {
    public List<CrmProject> getAllProjects();

    public Optional<CrmProject> getProjectById(Long projectId);

    public List<CrmProject> getProjectsByManagerId(Long managerId);
    public List<CrmProject> getProjectsByCustomerId(Long customerId);
    public CrmProject getProjectsByCode(String projectCode);
}
