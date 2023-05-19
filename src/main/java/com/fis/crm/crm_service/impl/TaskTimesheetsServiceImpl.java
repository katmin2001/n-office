package com.fis.crm.crm_service.impl;

import com.fis.crm.crm_entity.CrmTaskTimesheets;
import com.fis.crm.crm_repository.TaskRepo;
import com.fis.crm.crm_repository.TaskTimesheetsRepo;
import com.fis.crm.crm_service.TaskTimesheetsService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskTimesheetsServiceImpl implements TaskTimesheetsService {

    private final TaskTimesheetsRepo timesheetsRepo;

    private final TaskRepo taskRepo;

    public TaskTimesheetsServiceImpl(TaskTimesheetsRepo timesheetsRepo, TaskRepo taskRepo) {
        this.timesheetsRepo = timesheetsRepo;
        this.taskRepo = taskRepo;
    }

    /**
     * @return
     */
    @Override
    public List<CrmTaskTimesheets> getAllTimesheets() {
        return timesheetsRepo.findAll();
    }

    /**
     * @param taskTimesheets
     * @return
     */
    @Override
    public CrmTaskTimesheets createTimesheets(Long taskId, CrmTaskTimesheets taskTimesheets) {
        taskTimesheets.setTaskid(taskId);
//        taskTimesheets.setProjectid(taskRepo.findById(taskId).get().getProjectid());

        CrmTaskTimesheets timesheets = new CrmTaskTimesheets();
//        timesheets.setProjectid(taskTimesheets.getProjectid());
//        timesheets.setTaskid(taskTimesheets.getTaskid());
//        timesheets.setDatetimesheets(taskTimesheets.getDatetimesheets());
//        timesheets.setDescription(taskTimesheets.getDescription());

        return timesheetsRepo.save(timesheets);
    }
}
