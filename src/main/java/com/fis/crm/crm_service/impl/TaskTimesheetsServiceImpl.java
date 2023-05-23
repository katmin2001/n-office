package com.fis.crm.crm_service.impl;

import com.fis.crm.crm_entity.CrmTaskTimesheets;
import com.fis.crm.crm_entity.DTO.TaskTimesheetsCreateDTO;
import com.fis.crm.crm_repository.CrmProjectRepo;
import com.fis.crm.crm_repository.TaskTimesheetsRepo;
import com.fis.crm.crm_service.CrmUserService;
import com.fis.crm.crm_service.TaskService;
import com.fis.crm.crm_service.TaskTimesheetsService;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class TaskTimesheetsServiceImpl implements TaskTimesheetsService {

    private final TaskService taskService;

    private final CrmProjectRepo projectRepo;

    private final CrmUserService userService;

    private final TaskTimesheetsRepo timesheetsRepo;

    public TaskTimesheetsServiceImpl(TaskServiceImpl taskService, CrmProjectRepo projectRepo, CrmUserServiceImpl userService, TaskTimesheetsRepo timesheetsRepo) {
        this.taskService = taskService;
        this.projectRepo = projectRepo;
        this.userService = userService;
        this.timesheetsRepo = timesheetsRepo;
    }

    /**
     * @return
     */
    @Override
    public List<CrmTaskTimesheets> getAllTimesheets() {
        return timesheetsRepo.findAll();
    }

    @Override
    public CrmTaskTimesheets createTimesheets(Long taskId, TaskTimesheetsCreateDTO timesheetsCreateDTO) {
        timesheetsCreateDTO.setTaskid(taskId);
        timesheetsCreateDTO.setProjectid(taskService.getTaskById(taskId).getProject().getId());
        CrmTaskTimesheets timesheets = new CrmTaskTimesheets();

        timesheets.setProject(projectRepo.findById(timesheetsCreateDTO.getProjectid()).get());
        timesheets.setTask(taskService.getTaskById(timesheetsCreateDTO.getTaskid()));
        timesheets.setDatetimesheets(timesheetsCreateDTO.getDatetimesheets());
        timesheets.setDescription(timesheetsCreateDTO.getDescription());
        timesheets.setUser(userService.findByCrmUserId(timesheetsCreateDTO.getCreatorid()));
        timesheets.setDatecreated(new Date());
        return timesheetsRepo.save(timesheets);
    }

    /**
     * @param id
     */
    @Override
    public void deleteTimesheets(Long id) {
        timesheetsRepo.deleteById(id);
    }

    /**
     * @param id
     * @return
     */
    @Override
    public List<CrmTaskTimesheets> getTimesheetsByProjectId(Long id) {
        return timesheetsRepo.findTimesheetsByProjectId(id);
    }

    /**
     * @param id
     * @return
     */
    @Override
    public List<CrmTaskTimesheets> getTimesheetsByUserId(Long id) {
        return timesheetsRepo.findTimesheetsByUserId(id);
    }
}
