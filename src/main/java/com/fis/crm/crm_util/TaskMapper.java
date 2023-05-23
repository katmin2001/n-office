package com.fis.crm.crm_util;

import com.fis.crm.crm_entity.CrmProject;
import com.fis.crm.crm_entity.CrmTask;
import com.fis.crm.crm_entity.CrmTaskStatus;
import com.fis.crm.crm_entity.CrmUser;
import com.fis.crm.crm_entity.DTO.TaskDTO;

public class TaskMapper {
    public static TaskDTO toDTO(CrmTask crmTask) {
        TaskDTO dto = new TaskDTO();
        dto.setTaskid(crmTask.getTaskid());
        dto.setTaskname(crmTask.getTaskname());

        if (crmTask.getProject() != null) {
            dto.setProjecid(crmTask.getProject().getId());
            dto.setProjectname(crmTask.getProject().getName());
        }

        dto.setStartdate(crmTask.getStartdate());
        dto.setEnddate(crmTask.getEnddate());

        if (crmTask.getStatus() != null) {
            dto.setStatuscode(crmTask.getStatus().getId());
            dto.setStatusname(crmTask.getStatus().getName());
        }

        if (crmTask.getGivertask() != null) {
            dto.setGivertaskid(crmTask.getGivertask().getUserid());
            dto.setGivertaskname(crmTask.getGivertask().getFullname());
        }

        if (crmTask.getReceivertask() != null) {
            dto.setReceivertaskid(crmTask.getReceivertask().getUserid());
            dto.setReceivertaskname(crmTask.getReceivertask().getFullname());
        }

        return dto;
    }

    public static CrmTask toEntity(TaskDTO dto) {
        CrmTask crmTask = new CrmTask();
        crmTask.setTaskid(dto.getTaskid());
        crmTask.setTaskname(dto.getTaskname());

        if (dto.getProjecid() != null) {
            CrmProject project = new CrmProject();
            project.setId(dto.getProjecid());
            project.setName(dto.getProjectname());
            crmTask.setProject(project);
        }

        crmTask.setStartdate(dto.getStartdate());
        crmTask.setEnddate(dto.getEnddate());

        if (dto.getStatuscode() != null) {
            CrmTaskStatus status = new CrmTaskStatus();
            status.setId(dto.getStatuscode());
            status.setName(dto.getStatusname());
            crmTask.setStatus(status);
        }

        if (dto.getReceivertaskid() != null) {
            CrmUser receivertask = new CrmUser();
            receivertask.setUserid(dto.getReceivertaskid());
            receivertask.setUsername(dto.getReceivertaskname());
            crmTask.setReceivertask(receivertask);
        }
        return crmTask;
    }

}
