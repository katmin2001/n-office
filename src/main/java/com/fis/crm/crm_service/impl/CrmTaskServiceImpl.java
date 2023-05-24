package com.fis.crm.crm_service.impl;

import com.fis.crm.crm_entity.*;
import com.fis.crm.crm_entity.DTO.TaskCreateDTO;
import com.fis.crm.crm_entity.DTO.TaskDTO;
import com.fis.crm.crm_entity.DTO.TaskSearchDTO;
import com.fis.crm.crm_entity.DTO.TaskUpdateDTO;
import com.fis.crm.crm_repository.*;
//import com.fis.crm.crm_repository.impl.TaskRepoImpl;
import com.fis.crm.crm_service.CrmUserService;
import com.fis.crm.crm_service.CrmTaskHistoryService;
import com.fis.crm.crm_service.CrmTaskService;
import com.fis.crm.crm_service.CrmTaskStatusService;
import com.fis.crm.crm_util.TaskMapper;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class CrmTaskServiceImpl implements CrmTaskService {
    private final CrmTaskRepo taskRepo;
    private final CrmUserService userService;
    private final CrmTaskStatusService statusService;
    private final CrmUserRepo IUserRepo;

    private final CrmTaskHistoryService historyService;

    public CrmTaskServiceImpl(CrmTaskRepo taskRepo, CrmUserServiceImpl userService, CrmTaskStatusServiceImpl statusService, CrmUserRepo iUserRepo, CrmTaskHistoryServiceImpl historyService) {
        this.taskRepo = taskRepo;
        this.userService = userService;
        this.statusService = statusService;
        this.IUserRepo = iUserRepo;
        this.historyService = historyService;
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
        String projectname = taskSearchDTO.getProjectname();
        String taskProcess = taskSearchDTO.getTaskProcess();
        List<CrmTask> taskList = taskRepo.findTaskByKeyword(taskname, statusname, givertaskname, receivertaskname, projectname, taskProcess);
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

    public CrmTask updateTask(Long taskId, TaskUpdateDTO taskUpdate) {
        CrmTask existingTask = getTaskById(taskId);
        boolean check = true;
        CrmTaskHistory taskHistory = new CrmTaskHistory();
        if (check) {
            taskHistory.setTaskid(taskId);
            taskHistory.setStatusprev(existingTask.getStatus());
        }
        // Cập nhật thông tin của task từ request body
        if (taskUpdate.getTaskname() != null) {
            existingTask.setTaskname(taskUpdate.getTaskname());
        }
        if (taskUpdate.getStartdate() != null) {
            existingTask.setStartdate(taskUpdate.getStartdate());
        }
        if (taskUpdate.getEnddate() != null) {
            existingTask.setEnddate(taskUpdate.getEnddate());
        }
        if (statusService.getStatusCode(taskUpdate.getStatuscode()) != null) {
            existingTask.setStatus(statusService.getStatusCode(taskUpdate.getStatuscode()));
        }
        if (IUserRepo.findById(taskUpdate.getReceivertaskid()).orElse(null) != null) {
            existingTask.setReceivertask(IUserRepo.findById(taskUpdate.getReceivertaskid()).orElse(null));
        }

        if (check) {
            taskHistory.setStatuscurrent(existingTask.getStatus());
            taskHistory.setTimecreate(new Date());
            historyService.saveTaskHistory(taskHistory);
        }
        return taskRepo.save(existingTask);
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
