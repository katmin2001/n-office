package com.fis.crm.crm_service.impl;

import com.fis.crm.crm_entity.CrmProject;
import com.fis.crm.crm_repository.CrmProjectRepo;
import com.fis.crm.crm_service.CrmProjectService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CrmProjectServiceImpl implements CrmProjectService {
    final CrmProjectRepo projectRepo;

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
    public List<CrmProject> getProjectsByManagerId(Long managerId) {
        List<CrmProject> projects = projectRepo.findAll();
        List<CrmProject> projectsByManager = new ArrayList<>();
        for (CrmProject project : projects) {
            if (project.getManager().getUserid() == managerId) {
                projectsByManager.add(project);
            }
        }
        return projectsByManager;
    }

    @Override
    public List<CrmProject> getProjectsByCustomerId(Long customerId) {
        List<CrmProject> projects = projectRepo.findAll();
        List<CrmProject> projectsByCustomer = new ArrayList<>();
        for (CrmProject project : projects) {
            if (project.getCustomer().getId() == null) continue;
            if (project.getCustomer().getId() == customerId) {
                projectsByCustomer.add(project);
            }
        }
        return projectsByCustomer;
    }

    @Override
    public CrmProject getProjectsByCode(String projectCode) {
        return projectRepo.findCrmProjectByCode(projectCode);
    }
}
