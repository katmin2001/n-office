package com.fis.crm.crm_service.impl;

import com.fis.crm.crm_entity.*;
import com.fis.crm.crm_entity.DTO.TaskCreateDTO;
import com.fis.crm.crm_repository.*;
//import com.fis.crm.crm_repository.impl.TaskRepoImpl;
import com.fis.crm.crm_service.IUserService;
import com.fis.crm.crm_service.TaskService;
import com.fis.crm.crm_service.TaskStatusService;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class TaskServiceImpl implements TaskService {
    private final TaskRepo taskRepo;
    private final IUserService userService;
    private final TaskStatusService statusService;

    public TaskServiceImpl(TaskRepo taskRepo, CrmIUserServiceImpl userService, TaskStatusServiceImpl statusService) {
        this.taskRepo = taskRepo;
        this.userService = userService;
        this.statusService = statusService;
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
    public CrmTask createTask(Long projectId, TaskCreateDTO createDTO) {
        createDTO.setProjecid(projectId);
        CrmTask task = new CrmTask();
        task.setTaskname(createDTO.getTaskname());
        task.setGivertask(userService.getUserById(createDTO.getGivertaskid()));
        task.setReceivertask(userService.getUserById(createDTO.getReceivertaskid()));
        task.setStartdate(createDTO.getStartdate());
        task.setEnddate(createDTO.getEnddate());
        task.setStatus(statusService.getStatusCode(createDTO.getStatuscode()));
//        task.setStageid(createDTO.getStageid());

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
