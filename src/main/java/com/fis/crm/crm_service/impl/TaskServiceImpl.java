package com.fis.crm.crm_service.impl;

import com.fis.crm.crm_entity.*;
import com.fis.crm.crm_entity.DTO.TaskCreateDTO;
import com.fis.crm.crm_entity.DTO.TaskDTO;
import com.fis.crm.crm_entity.DTO.TaskSearchDTO;
import com.fis.crm.crm_entity.DTO.TaskUpdateDTO;
import com.fis.crm.crm_repository.*;
//import com.fis.crm.crm_repository.impl.TaskRepoImpl;
import com.fis.crm.crm_service.IUserService;
import com.fis.crm.crm_service.TaskService;
import com.fis.crm.crm_service.TaskStatusService;
import com.fis.crm.crm_util.TaskMapper;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TaskServiceImpl implements TaskService {
    private final TaskRepo taskRepo;
    private final IUserService userService;
    private final TaskStatusService statusService;
    private final IUserRepo IUserRepo;

    public TaskServiceImpl(TaskRepo taskRepo, CrmUserServiceImpl userService, TaskStatusServiceImpl statusService, IUserRepo iUserRepo) {
        this.taskRepo = taskRepo;
        this.userService = userService;
        this.statusService = statusService;
        this.IUserRepo = iUserRepo;
    }

    @Override
    public List<CrmTask> getAllTasks() {
        return taskRepo.findAll();
    }

    @Override
    public CrmTask getTaskById(Long taskId) {
        CrmTask task = taskRepo.findByTaskId(taskId);
        return task;
    }

    @Override
    public List<CrmTask> getTasksByProjectId(Long id) {
        return taskRepo.findTasksByProjectId(id);
    }

    /**
     * @param taskSearchDTO
     * @return
     */
    @Override
    public List<TaskDTO> searchTasks(TaskSearchDTO taskSearchDTO) {
        String taskname = taskSearchDTO.getTaskname();
        String statusname = taskSearchDTO.getStatusname();
        String givertaskname = taskSearchDTO.getGivertaskname();
        String receivertaskname = taskSearchDTO.getReceivertaskname();
        String stagename = taskSearchDTO.getStagename();
        String projectname = taskSearchDTO.getProjectname();
        List<CrmTask> taskList = taskRepo.findTaskByKeyword(taskname, statusname, givertaskname, receivertaskname, stagename, projectname);
        List<TaskDTO> taskDTOList = new ArrayList<>();

        for (CrmTask crmTask : taskList) {
            TaskDTO task = TaskMapper.toDTO(crmTask);
            taskDTOList.add(task);
        }
        return taskDTOList;
    }

    @Override
    @Transactional
    public CrmTask createTask(Long projectId, TaskCreateDTO createDTO) {
        createDTO.setProjecid(projectId);
        CrmTask task = new CrmTask();
        task.setTaskname(createDTO.getTaskname());
        task.setGivertask(IUserRepo.findById(createDTO.getReceivertaskid()).orElseThrow(NullPointerException::new));
        task.setReceivertask(IUserRepo.findById(createDTO.getGivertaskid()).orElseThrow(NullPointerException::new));
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
     * @param updateDTO
     * @param task
     * @return
     */
    @Override
    public boolean checkStatus(TaskUpdateDTO updateDTO, CrmTask task) {
        boolean check = false;
        if (updateDTO.getStatuscode() != task.getStatus().getId()) {
            check = true;
        }
        return check;
    }

}
