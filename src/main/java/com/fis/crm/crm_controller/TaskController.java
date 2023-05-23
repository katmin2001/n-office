package com.fis.crm.crm_controller;

import com.fis.crm.crm_entity.CrmTask;
import com.fis.crm.crm_entity.CrmTaskHistory;
import com.fis.crm.crm_entity.DTO.TaskCreateDTO;
import com.fis.crm.crm_entity.DTO.TaskSearchDTO;
import com.fis.crm.crm_entity.DTO.TaskUpdateDTO;
import com.fis.crm.crm_entity.DTO.TaskDTO;
import com.fis.crm.crm_repository.IUserRepo;
import com.fis.crm.crm_service.TaskHistoryService;
import com.fis.crm.crm_service.TaskService;
import com.fis.crm.crm_service.TaskStatusService;
import com.fis.crm.crm_service.impl.CrmUserServiceImpl;
import com.fis.crm.crm_service.impl.TaskHistoryServiceImpl;
import com.fis.crm.crm_service.impl.TaskServiceImpl;
import com.fis.crm.crm_service.impl.TaskStatusServiceImpl;
import com.fis.crm.crm_util.TaskMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/task")
public class TaskController {
    private final TaskService taskService;
    private final TaskStatusService statusService;
    private final TaskHistoryService historyService;
    private final IUserRepo IUserRepo;
//    private TaskRepo taskRepo;

    public TaskController(TaskServiceImpl taskService, TaskStatusServiceImpl statusService, TaskHistoryServiceImpl historyService, IUserRepo iUserRepo) {
        this.taskService = taskService;
        this.statusService = statusService;
        this.historyService = historyService;
        this.IUserRepo = iUserRepo;
    }

    @GetMapping("/project/{id}")
    public List<TaskDTO> getAllTasksProject(@PathVariable("id") Long id){
        List<CrmTask> crmTasks = taskService.getTasksByProjectId(id);
        List<TaskDTO> taskDTOs = new ArrayList<>();

        for (CrmTask crmTask : crmTasks) {
            TaskDTO taskDTO = TaskMapper.toDTO(crmTask);
            taskDTOs.add(taskDTO);
        }
        return taskDTOs;
    }

    @PostMapping("/searchByTaskId")
    public CrmTask searchTaskByTaskId(@RequestBody Map<String, Long> requestBody) {
        Long taskId = requestBody.get("taskId");
        return taskService.getTaskById(taskId);
    }

    @PostMapping("/search")
    public List<TaskDTO> searchTasks(@RequestBody TaskSearchDTO taskSearchDTO) {
        return taskService.searchTasks(taskSearchDTO);
    }

//    @PostMapping("/search")
//    public List<TaskDTO> searchTasksByName(@RequestBody Map<String, String> requestBody) {
//        String taskname = requestBody.get("taskname");
//        List<CrmTask> taskList = taskService.searchTasksByName(taskname);
//        List<TaskDTO> taskDTOList = new ArrayList<>();
//
//        for (CrmTask crmTask : taskList) {
//            TaskDTO task = TaskMapper.toDTO(crmTask);
//            taskDTOList.add(task);
//        }
//        return taskDTOList;
//    }

    @GetMapping("/{taskid}")
    public TaskDTO getTaskById(@PathVariable Long taskid) {
        return TaskMapper.toDTO(taskService.getTaskById(taskid));
    }

    @GetMapping
    public List<TaskDTO> getAllTasks() {
        List<CrmTask> crmTasks = taskService.getAllTasks();
        List<TaskDTO> taskDTOs = new ArrayList<>();

        for (CrmTask crmTask : crmTasks) {
            TaskDTO taskDTO = TaskMapper.toDTO(crmTask);
            taskDTOs.add(taskDTO);
        }
        return taskDTOs;
    }

    @PostMapping("/project/{id}")
    public TaskDTO createTask(@PathVariable("id") Long id, @RequestBody TaskCreateDTO createDTO) {
        return TaskMapper.toDTO(taskService.createTask(id, createDTO));
    }

    @PutMapping("/{taskId}")
    public CrmTask updateTask(@PathVariable Long taskId, @RequestBody TaskUpdateDTO taskUpdate) {
        CrmTask existingTask = taskService.getTaskById(taskId);
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
//            existingTask.setStageid(taskUpdate.getStageid());
        CrmTask updatedTask = taskService.updateTask(existingTask);
        if (check) {
            taskHistory.setStatuscurrent(existingTask.getStatus());
            taskHistory.setTimecreate(new Date());
            historyService.saveTaskHistory(taskHistory);
        }
        return updatedTask;
    }

    @DeleteMapping("/{taskId}")
    public ResponseEntity<?> deleteTask(@PathVariable Long taskId) {
        try {
            taskService.deleteTask(taskId);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to delete task");
        }
    }

}
