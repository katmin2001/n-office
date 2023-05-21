package com.fis.crm.crm_controller;

import com.fis.crm.crm_entity.CrmProject;
import com.fis.crm.crm_entity.CrmProjectRequest;
import com.fis.crm.crm_entity.DTO.CrmProjectDTO;
import com.fis.crm.crm_service.CrmProjectService;
import com.fis.crm.crm_service.impl.CrmProjectServiceImpl;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/project")
public class CrmProjectController {

    private final CrmProjectService crmProjectService;

    public CrmProjectController(CrmProjectServiceImpl crmProjectService) {
        this.crmProjectService = crmProjectService;
    }

    @GetMapping("/")
    public List<CrmProject> getAllProjects() {
        List<CrmProject> projects = crmProjectService.getAllProjects();
        return projects;
    }

    @GetMapping("/{projectId}")
    public Optional<CrmProject> getProjectById(@PathVariable Long projectId) {
        return crmProjectService.getProjectById(projectId); // trả về null nếu không tìm thấy khách hàng với id tương ứng
    }

    @GetMapping("/code/{projectCode}")
    public CrmProject getProjectByCode(@PathVariable String projectCode) {
        return crmProjectService.getProjectsByCode(projectCode);
    }

    @GetMapping("/manager/{managerId}")
    public List<CrmProjectDTO> getProjectsByManagerId(@PathVariable Long managerId) {
        return crmProjectService.getProjectsByManagerId(managerId);
    }

    @GetMapping("/customer/{customerId}")
    public List<CrmProjectDTO> getProjectsByCustomerId(@PathVariable Long customerId) {
        return crmProjectService.getProjectsByCustomerId(customerId);
    }

    @PostMapping("/")
    public CrmProjectRequest createProject(@RequestBody CrmProjectRequest newProject) {
        return crmProjectService.createProject(newProject);
    }

    @PutMapping("/{projectId}")
    public CrmProjectRequest updateProject(@PathVariable Long projectId, @RequestBody CrmProjectRequest projectUpdate) {
        return crmProjectService.updateProjectById(projectId, projectUpdate);
    }
}
