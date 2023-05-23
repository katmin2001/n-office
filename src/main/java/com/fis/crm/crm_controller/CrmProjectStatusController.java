package com.fis.crm.crm_controller;

import com.fis.crm.crm_entity.CrmProjectStatus;
import com.fis.crm.crm_service.CrmProjectStatusService;
import com.fis.crm.crm_service.impl.CrmProjectStatusServiceImpl;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/projectStatus")
public class CrmProjectStatusController {
    private final CrmProjectStatusService prjectStatusService;

    public CrmProjectStatusController(CrmProjectStatusServiceImpl prjectStatusService) {
        this.prjectStatusService = prjectStatusService;
    }

    @GetMapping("/")
    public List<CrmProjectStatus> getAllProjectStatus() {
        return prjectStatusService.getAllProjectStatus();
    }

    @GetMapping("/{statusId}")
    public CrmProjectStatus getProjectStatusById(@PathVariable Byte statusId) {
        return prjectStatusService.getProjectStatusById(statusId);
    }

    @PutMapping("/{statusId}")
    public CrmProjectStatus updateProjectStatus(@PathVariable Byte statusId, @RequestBody CrmProjectStatus projectStatusUpdate) {
        return prjectStatusService.updateProjectStatus(statusId, projectStatusUpdate);
    }

    @DeleteMapping("/{statusId}")
    public CrmProjectStatus deleteProjectStatusById(@PathVariable Byte statusId){
        return prjectStatusService.deleteProjectStatusById(statusId);
    }
}
