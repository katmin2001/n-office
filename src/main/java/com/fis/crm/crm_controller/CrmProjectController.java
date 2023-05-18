package com.fis.crm.crm_controller;

import com.fis.crm.crm_entity.CrmCustomer;
import com.fis.crm.crm_entity.CrmProject;
import com.fis.crm.crm_service.CrmProjectService;
import com.fis.crm.crm_service.impl.CrmProjectServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
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
    public List<CrmProject> getProjectsByManagerId(@PathVariable Long managerId) {
        return crmProjectService.getProjectsByManagerId(managerId);
    }
}
