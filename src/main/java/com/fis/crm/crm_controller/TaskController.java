package com.fis.crm.crm_controller;

import com.fis.crm.crm_entity.CrmTask;
import com.fis.crm.crm_entity.DTO.TaskDTO;
import com.fis.crm.crm_repository.TaskRepo;
import com.fis.crm.crm_service.TaskService;
import com.fis.crm.crm_service.impl.TaskServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/task")
public class TaskController {
    private final TaskService taskService;

//    private TaskRepo taskRepo;

    public TaskController(TaskServiceImpl taskService) {
        this.taskService = taskService;
//        this.taskRepo = taskRepo;
    }

    @GetMapping("/project/{id}")
    public List<CrmTask> getAllTasksProject(@PathVariable("id") Long id){
        return taskService.getTasksByProjectId(id);
    }

    @GetMapping("/{id}")
    public CrmTask getTaskById(@PathVariable("id") Long id) {
        return taskService.getTaskById(id);
    }

    @GetMapping
    public List<CrmTask> getAllTasks() {
        return taskService.getAllTasks();
    }

    @PostMapping("/project/{id}")
    public CrmTask createTask(@PathVariable("id") Long id, @RequestBody TaskDTO newTask) {
        return taskService.createTask(id, newTask);
    }

    @PutMapping("/{taskId}")
    public ResponseEntity<?> updateTask(@PathVariable Long taskId, @RequestBody TaskDTO task) {
        try {
            CrmTask existingTask = taskService.getTaskById(taskId);
            if (existingTask == null) {
                return ResponseEntity.notFound().build();
            }

            // Cập nhật thông tin của task từ request body
            existingTask.setTaskname(task.getTaskname());
            existingTask.setStartdate(task.getStartdate());
            existingTask.setEnddate(task.getEnddate());
            existingTask.setStatuscode(task.getStatuscode());
            existingTask.setReceivertaskid(task.getReceivertaskid());
            existingTask.setStageid(task.getStageid());

            CrmTask updatedTask = taskService.updateTask(existingTask);
            return ResponseEntity.ok(updatedTask);
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

    @PostMapping("/searchByTaskId")
    public CrmTask searchTaskByTaskId(@RequestBody Map<String, Long> requestBody) {
        Long taskId = requestBody.get("taskId");
        return taskService.getTaskById(taskId);
    }

    @PostMapping("/searchByStage")
    public List<CrmTask> searchTaskByStage(@RequestBody Map<String, Long> requestBody) {
        Long stageId = requestBody.get("stageId");
        return taskService.getTaskByStageId(stageId);
    }

    @PostMapping("/searchByGiverTaskId")
    public List<CrmTask> searchTaskByGiverTaskId(@RequestBody Map<String, Long> requestBody) {
        Long giverTaskId = requestBody.get("giverTaskId");
        return taskService.getTasksByGivertaskId(giverTaskId);
    }

    @PostMapping("/searchByReceiverTaskId")
    public List<CrmTask> searchTaskByReceiverTaskId(@RequestBody Map<String, Long> requestBody) {
        Long receiverTaskId = requestBody.get("receiverTaskId");
        return taskService.getTasksByReceivertaskId(receiverTaskId);
    }

    @PostMapping("/searchByStatus")
    public List<CrmTask> searchTaskByStatus(@RequestBody Map<String, Long> requestBody) {
        Long statusCode = requestBody.get("statusCode");
        return taskService.getTasksByStatus(statusCode);
    }

}
