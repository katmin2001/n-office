package com.fis.crm.crm_service.impl;

import com.fis.crm.crm_entity.CrmProject;
import com.fis.crm.crm_entity.CrmProjectRequest;
import com.fis.crm.crm_entity.DTO.CrmProjectDTO;
import com.fis.crm.crm_repository.CrmProjectRepo;
import com.fis.crm.crm_repository.CrmProjectRequestRepo;
import com.fis.crm.crm_service.CrmProjectService;
import com.fis.crm.crm_util.DtoMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CrmProjectServiceImpl implements CrmProjectService {
    final CrmProjectRepo projectRepo;
    final CrmProjectRequestRepo projectRequestRepo;

    final DtoMapper dtoMapper = new DtoMapper();
    public CrmProjectServiceImpl(CrmProjectRepo projectRepo, CrmProjectRequestRepo projectRequestRepo) {
        this.projectRepo = projectRepo;
        this.projectRequestRepo = projectRequestRepo;
    }

    @Override
    public List<CrmProject> getAllProjects() {
        List<CrmProject> projects = projectRepo.findAll();
        return projects;
    }

    @Override
    public Optional<CrmProject> getProjectById(Long projectId) {
        return projectRepo.findById(projectId);
    }

    @Override
    public List<CrmProjectDTO> getProjectsByManagerId(Long managerId) {
        List<CrmProject> projects = projectRepo.findAll();
        List<CrmProjectDTO> projectsByManager = new ArrayList<>();
        for (CrmProject project : projects) {
            if (project.getManager().getUserid() == managerId) {
                projectsByManager.add(dtoMapper.projectDTOMapper(project));
            }
        }
        return projectsByManager;
    }

    @Override
    public List<CrmProjectDTO> getProjectsByCustomerId(Long customerId) {
        List<CrmProject> projects = projectRepo.findAll();
        List<CrmProjectDTO> projectsByCustomer = new ArrayList<>();
        for (CrmProject project : projects) {
            if (project.getCustomer().getId() == customerId) {
                projectsByCustomer.add(dtoMapper.projectDTOMapper(project));
            }
        }
        return projectsByCustomer;
    }

    @Override
    public CrmProject getProjectsByCode(String projectCode) {
        return projectRepo.findCrmProjectByCode(projectCode);
    }

    @Override
    public CrmProjectRequest createProject(CrmProjectRequest newProject) {
        CrmProjectRequest project = projectRequestRepo.save(newProject);
        return project;
    }

    @Override
    public CrmProjectRequest updateProjectById(Long projectId, CrmProjectRequest projectUpdate) {
        CrmProjectRequest existingProject = projectRequestRepo.findById(projectId).orElse(null);
        if (existingProject != null) {
            if (projectUpdate.getName() != null) existingProject.setName(projectUpdate.getName());
            if (projectUpdate.getCode() != null) existingProject.setCode(projectUpdate.getCode());
            if (projectUpdate.getCustomerId() != null) existingProject.setCustomerId(projectUpdate.getCustomerId());
            if (projectUpdate.getManagerId() != null) existingProject.setManagerId(projectUpdate.getManagerId());
            if (projectUpdate.getPrivacyId() != null) existingProject.setPrivacyId(projectUpdate.getPrivacyId());
            if (projectUpdate.getStatusId() != null) existingProject.setStatusId(projectUpdate.getStatusId());
            if (projectUpdate.getDescription() != null) existingProject.setDescription(projectUpdate.getDescription());
            if (projectUpdate.getStartDate() != null) existingProject.setStartDate(projectUpdate.getStartDate());
            if (projectUpdate.getEndDate() != null) existingProject.setEndDate(projectUpdate.getEndDate());
            if (projectUpdate.getFinishDate() != null) existingProject.setFinishDate(projectUpdate.getFinishDate());
            return projectRequestRepo.save(existingProject);
        }
        return null;
    }

    @Override
    public CrmProject searchProject(CrmProjectRequest projectRequest) {
        return null;
    }
}
