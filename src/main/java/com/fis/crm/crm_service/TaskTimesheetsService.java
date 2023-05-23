package com.fis.crm.crm_service;

import com.fis.crm.crm_entity.CrmTaskTimesheets;
import com.fis.crm.crm_entity.DTO.TaskTimesheetsCreateDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface TaskTimesheetsService {

    public List<CrmTaskTimesheets> getAllTimesheets();

    public CrmTaskTimesheets createTimesheets(Long taskId, TaskTimesheetsCreateDTO timesheetsCreateDTO);

    public void deleteTimesheets(Long id);

    public List<CrmTaskTimesheets> getTimesheetsByProjectId(Long id);

    public List<CrmTaskTimesheets> getTimesheetsByUserId(Long id);
}
