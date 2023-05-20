package com.fis.crm.crm_service;

import com.fis.crm.crm_entity.CrmTask;
import com.fis.crm.crm_entity.DTO.TaskCreateDTO;
import com.fis.crm.crm_entity.DTO.TaskDTO;
import com.fis.crm.crm_entity.DTO.TaskUpdateDTO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public interface TaskService {

    public List<CrmTask> getAllTasks();

    public CrmTask getTaskById(Long id);

    public List<CrmTask> getTasksByProjectId(Long id);

    public List<CrmTask> getTaskByStageId(Long stageId);

    public List<CrmTask> getTasksByGivertaskId(Long userId);

    public List<CrmTask> getTasksByReceivertaskId(Long userId);

    public List<CrmTask> getTasksByStatus(Long status);

    public CrmTask createTask(Long projectId, TaskCreateDTO createDTO);

    public CrmTask updateTask(CrmTask task);

    public void deleteTask(Long id);

    public boolean checkStatus(TaskUpdateDTO updateDTO, CrmTask task);
}
