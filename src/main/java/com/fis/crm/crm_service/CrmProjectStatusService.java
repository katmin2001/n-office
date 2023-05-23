package com.fis.crm.crm_service;

import com.fis.crm.crm_entity.CrmProjectStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Service
public interface CrmProjectStatusService {
    public List<CrmProjectStatus> getAllProjectStatus();
    public CrmProjectStatus getProjectStatusById(Byte statusId);
    public CrmProjectStatus updateProjectStatus(Byte statusId, CrmProjectStatus projectStatusUpdate);
    public CrmProjectStatus deleteProjectStatusById(Byte statusId);

}
