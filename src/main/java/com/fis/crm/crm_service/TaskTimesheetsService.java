package com.fis.crm.crm_service;

import com.fis.crm.crm_entity.CrmTaskTimesheets;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface TaskTimesheetsService {

    public List<CrmTaskTimesheets> getAllTimesheets();

    public CrmTaskTimesheets createTimesheets(Long taskId,CrmTaskTimesheets taskTimesheets);
}
