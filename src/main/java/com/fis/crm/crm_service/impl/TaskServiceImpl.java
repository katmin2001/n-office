package com.fis.crm.crm_service.impl;

import com.fis.crm.crm_entity.*;
import com.fis.crm.crm_entity.DTO.TaskDTO;
import com.fis.crm.crm_repository.*;
//import com.fis.crm.crm_repository.impl.TaskRepoImpl;
import com.fis.crm.crm_service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class TaskServiceImpl implements TaskService {

    private final TaskRepo taskRepo;

    public TaskServiceImpl(TaskRepo taskRepo) {
        this.taskRepo = taskRepo;
    }

    @Override
    public List<CrmTask> getAllTasks() {
        return taskRepo.findAll();
    }

    @Override
    public CrmTask getTaskById(Long taskId) {
        return taskRepo.findById(taskId).get();
    }

    @Override
    public List<CrmTask> getTasksByProjectId(Long id) {
        return taskRepo.findTasksByProjectId(id);
    }

    @Override
    @Transactional
    public CrmTask createTask(Long projectId, TaskDTO taskRequest) {
        taskRequest.setProjecid(projectId);
        CrmTask task = new CrmTask();
        task.setTaskname(taskRequest.getTaskname());
        task.setProjectid(taskRequest.getProjecid());
        task.setGivertaskid(taskRequest.getGivertaskid());
        task.setReceivertaskid(taskRequest.getReceivertaskid());
        task.setStartdate(taskRequest.getStartdate());
        task.setEnddate(taskRequest.getEnddate());
        task.setStatuscode(taskRequest.getStatuscode());
        task.setStageid(taskRequest.getStageid());

        return taskRepo.save(task);
    }

    public CrmTask updateTask(CrmTask task) {
        return taskRepo.save(task);
    }

    public void deleteTask(Long id) {
        taskRepo.deleteById(id);
    }

    /**
     * @param stageId
     * @return
     */
    @Override
    public List<CrmTask> getTaskByStageId(Long stageId) {
        return taskRepo.findTasksByStageId(stageId);
    }

    /**
     * @param userId
     * @return
     */
    @Override
    public List<CrmTask> getTasksByGivertaskId(Long userId) {
        return taskRepo.findTasksByGivertaskId(userId);
    }

    /**
     * @param userId
     * @return
     */
    @Override
    public List<CrmTask> getTasksByReceivertaskId(Long userId) {
        return taskRepo.findTasksByReceivertaskId(userId);
    }

    /**
     * @param status
     * @return
     */
    @Override
    public List<CrmTask> getTasksByStatus(Long status) {
        return taskRepo.findTasksByStatus(status);
    }
}
