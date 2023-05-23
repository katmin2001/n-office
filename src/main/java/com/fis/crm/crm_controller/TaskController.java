package com.fis.crm.crm_controller;

import com.fis.crm.crm_entity.CrmTask;
import com.fis.crm.crm_entity.CrmTaskHistory;
import com.fis.crm.crm_entity.DTO.TaskCreateDTO;
import com.fis.crm.crm_entity.DTO.TaskUpdateDTO;
import com.fis.crm.crm_entity.DTO.TaskDTO;
import com.fis.crm.crm_service.CrmUserService;
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

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/task")
public class TaskController {
    private final TaskService taskService;
    private final TaskStatusService statusService;
    private final CrmUserService userService;
    private final TaskHistoryService historyService;
//    private TaskRepo taskRepo;

    public TaskController(TaskServiceImpl taskService, TaskStatusServiceImpl statusService, CrmUserServiceImpl userService, TaskHistoryServiceImpl historyService) {
        this.taskService = taskService;
//        this.taskRepo = taskRepo;
        this.statusService = statusService;
        this.userService = userService;
        this.historyService = historyService;
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
    public List<TaskDTO> searchTasks(@RequestBody Map<String, Object> requestBody) {
        String searchType = (String) requestBody.get("searchType");

        if (searchType == null) {
            throw new IllegalArgumentException("Missing searchType parameter");
        }

        switch (searchType) {
            case "stage":
                Integer stageId = (Integer) requestBody.get("stageId");
                return searchTasksByStage(stageId.longValue());
            case "giverTask":
                Integer giverTaskId = (Integer) requestBody.get("giverTaskId");
                return searchTasksByGiverTaskId(giverTaskId.longValue());
            case "receiverTask":
                Integer receiverTaskId = (Integer) requestBody.get("receiverTaskId");
                return searchTasksByReceiverTaskId(receiverTaskId.longValue());
            case "status":
                Integer statusCode = (Integer) requestBody.get("statusCode");
                return searchTasksByStatus(statusCode.longValue());
            default:
                throw new IllegalArgumentException("Invalid searchType parameter");
        }
    }


    private List<TaskDTO> searchTasksByStage(Long stageId) {
        List<CrmTask> crmTasks = taskService.getTaskByStageId(stageId);
        return mapToTaskDTOs(crmTasks);
    }

    private List<TaskDTO> searchTasksByGiverTaskId(Long giverTaskId) {
        List<CrmTask> crmTasks = taskService.getTasksByGivertaskId(giverTaskId);
        return mapToTaskDTOs(crmTasks);
    }

    private List<TaskDTO> searchTasksByReceiverTaskId(Long receiverTaskId) {
        List<CrmTask> crmTasks = taskService.getTasksByReceivertaskId(receiverTaskId);
        return mapToTaskDTOs(crmTasks);
    }

    private List<TaskDTO> searchTasksByStatus(Long statusCode) {
        List<CrmTask> crmTasks = taskService.getTasksByStatus(statusCode);
        return mapToTaskDTOs(crmTasks);
    }

    private List<TaskDTO> mapToTaskDTOs(List<CrmTask> crmTasks) {
        List<TaskDTO> taskDTOs = new ArrayList<>();
        for (CrmTask crmTask : crmTasks) {
            TaskDTO taskDTO = TaskMapper.toDTO(crmTask);
            taskDTOs.add(taskDTO);
        }
        return taskDTOs;
    }



    @GetMapping("/{id}")
    public TaskDTO getTaskById(@PathVariable("id") Long id) {
        return TaskMapper.toDTO(taskService.getTaskById(id));
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
    public ResponseEntity<?> updateTask(@PathVariable Long taskId, @RequestBody TaskUpdateDTO taskUpdate) {
        try {
            CrmTask existingTask = taskService.getTaskById(taskId);
            boolean check = taskService.checkStatus(taskUpdate, existingTask);
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
            if (userService.findByCrmUserId(taskUpdate.getReceivertaskid()) != null) {
                existingTask.setReceivertask(userService.findByCrmUserId(taskUpdate.getReceivertaskid());
            }
//            existingTask.setStageid(taskUpdate.getStageid());
            CrmTask updatedTask = taskService.updateTask(existingTask);
            if (check) {
                taskHistory.setStatuscurrent(existingTask.getStatus());
                taskHistory.setTimecreate(new Date());
                historyService.saveTaskHistory(taskHistory);
            }
            return ResponseEntity.ok(TaskMapper.toDTO(updatedTask));

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to update task");
        }
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
