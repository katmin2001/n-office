package com.fis.crm.crm_service;

import com.fis.crm.crm_entity.CrmProject;
import com.fis.crm.crm_entity.DTO.CrmProjectDTO;
import com.fis.crm.crm_entity.DTO.CrmProjectRequestDTO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public interface CrmProjectService {
    public List<CrmProject> getAllProjects();

    public Optional<CrmProject> getProjectById(Long projectId);

    public List<CrmProjectDTO> getProjectsByManagerId(Long managerId);
    public List<CrmProjectDTO> getProjectsByCustomerId(Long customerId);
    public CrmProject getProjectsByCode(String projectCode);
    public CrmProject createProject(CrmProject newProject);
}
