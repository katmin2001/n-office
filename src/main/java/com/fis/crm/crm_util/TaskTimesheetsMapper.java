package com.fis.crm.crm_util;

import com.fis.crm.crm_entity.*;
import com.fis.crm.crm_entity.DTO.TaskDTO;
import com.fis.crm.crm_entity.DTO.TaskTimesheetsDTO;

public class TaskTimesheetsMapper {
    public static TaskTimesheetsDTO toDTO(CrmTaskTimesheets taskTimesheets) {
        TaskTimesheetsDTO dto = new TaskTimesheetsDTO();
        dto.setId(taskTimesheets.getId());
        dto.setDescription(taskTimesheets.getDescription());
        dto.setDatetimesheets(taskTimesheets.getDatetimesheets());
        dto.setDatecreated(taskTimesheets.getDatecreated());

        if (taskTimesheets.getProject() != null) {
            dto.setProjectid(taskTimesheets.getProject().getId());
            dto.setProjectname(taskTimesheets.getProject().getName());
        }

        if (taskTimesheets.getTask() != null) {
            dto.setTaskid(taskTimesheets.getTask().getTaskid());
            dto.setTaskname(taskTimesheets.getTask().getTaskname());
        }

        if (taskTimesheets.getUser() != null) {
            dto.setCreatorid(taskTimesheets.getUser().getUserid());
            dto.setCreator(taskTimesheets.getUser().getFullname());
        }

        return dto;
    }

    public static CrmTaskTimesheets toEntity(TaskTimesheetsDTO timesheetsDTO) {
        CrmTaskTimesheets timesheets = new CrmTaskTimesheets();
        timesheets.setId(timesheetsDTO.getId());
        if (timesheetsDTO.getTaskid() != null) {
            CrmTask task = new CrmTask();
            task.setTaskid(timesheetsDTO.getTaskid());
            task.setTaskname(timesheetsDTO.getTaskname());
            timesheets.setTask(task);
        }
        if (timesheetsDTO.getProjectid() != null) {
            CrmProject project = new CrmProject();
            project.setId(timesheetsDTO.getProjectid());
            project.setName(timesheetsDTO.getProjectname());
            timesheets.setProject(project);
        }

        timesheets.setDescription(timesheetsDTO.getDescription());
        timesheets.setDatecreated(timesheetsDTO.getDatecreated());
        timesheets.setDatetimesheets(timesheetsDTO.getDatetimesheets());

        if (timesheetsDTO.getCreatorid() != null) {
            CrmUser user = new CrmUser();
            user.setUserid(timesheetsDTO.getCreatorid());
            user.setUsername(timesheetsDTO.getCreator());
            timesheets.setUser(user);
        }
        return timesheets;
    }
}
