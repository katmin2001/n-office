package com.fis.crm.crm_service.impl;

import com.fis.crm.crm_entity.CrmProject;
import com.fis.crm.crm_entity.DTO.CrmProjectDTO;
import com.fis.crm.crm_entity.DTO.CrmProjectRequestDTO;
import com.fis.crm.crm_repository.CrmProjectRepo;
import com.fis.crm.crm_service.CrmProjectService;
import com.fis.crm.crm_util.CrmProjectMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CrmProjectServiceImpl implements CrmProjectService {
    final CrmProjectRepo projectRepo;
    final CrmProjectMapper projectMapper = new CrmProjectMapper();

    public CrmProjectServiceImpl(CrmProjectRepo projectRepo) {
        this.projectRepo = projectRepo;
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
                projectsByManager.add(projectMapper.toDTO(project));
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
                projectsByCustomer.add(projectMapper.toDTO(project));
            }
        }
        return projectsByCustomer;
    }

    @Override
    public CrmProject getProjectsByCode(String projectCode) {
        return projectRepo.findCrmProjectByCode(projectCode);
    }

    @Override
    public CrmProject createProject(CrmProject newProject) {
        return projectRepo.save(newProject);
    }
}
