package com.fis.crm.crm_service;

import com.fis.crm.crm_entity.CrmTask;
import com.fis.crm.crm_entity.DTO.TaskCreateDTO;
import com.fis.crm.crm_entity.DTO.TaskDTO;
import com.fis.crm.crm_entity.DTO.TaskSearchDTO;
import com.fis.crm.crm_entity.DTO.TaskUpdateDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CrmTaskService {

    public List<CrmTask> getAllTasks();

    public CrmTask getTaskById(Long id);

    public List<CrmTask> getTasksByProjectId(Long id);

    public List<TaskDTO> searchTasks(TaskSearchDTO taskSearchDTO);

    public CrmTask createTask(Long projectId, TaskCreateDTO createDTO);

    public CrmTask updateTask(Long taskid, TaskUpdateDTO task);

    public void deleteTask(Long id);

    public boolean checkStatus(TaskUpdateDTO updateDTO, CrmTask task);
}
