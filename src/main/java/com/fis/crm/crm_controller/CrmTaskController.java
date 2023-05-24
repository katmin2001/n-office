package com.fis.crm.crm_controller;

import com.fis.crm.crm_entity.CrmTask;
import com.fis.crm.crm_entity.DTO.TaskCreateDTO;
import com.fis.crm.crm_entity.DTO.TaskSearchDTO;
import com.fis.crm.crm_entity.DTO.TaskUpdateDTO;
import com.fis.crm.crm_entity.DTO.TaskDTO;
import com.fis.crm.crm_repository.CrmUserRepo;
import com.fis.crm.crm_service.CrmTaskHistoryService;
import com.fis.crm.crm_service.CrmTaskService;
import com.fis.crm.crm_service.CrmTaskStatusService;
import com.fis.crm.crm_service.impl.CrmTaskHistoryServiceImpl;
import com.fis.crm.crm_service.impl.CrmTaskServiceImpl;
import com.fis.crm.crm_service.impl.CrmTaskStatusServiceImpl;
import com.fis.crm.crm_util.TaskMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/task")
public class CrmTaskController {
    private final CrmTaskService taskService;
    private final CrmTaskStatusService statusService;
    private final CrmTaskHistoryService historyService;
    private final CrmUserRepo IUserRepo;
//    private TaskRepo taskRepo;

    public CrmTaskController(CrmTaskServiceImpl taskService, CrmTaskStatusServiceImpl statusService, CrmTaskHistoryServiceImpl historyService, CrmUserRepo iUserRepo) {
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
        CrmTask updatedTask = taskService.updateTask(taskId, taskUpdate);
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
