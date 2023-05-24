package com.fis.crm.crm_controller;

import com.fis.crm.crm_entity.CrmTaskTimesheets;
import com.fis.crm.crm_entity.DTO.TaskTimesheetsCreateDTO;
import com.fis.crm.crm_entity.DTO.TaskTimesheetsDTO;
import com.fis.crm.crm_service.CrmTaskTimesheetsService;
import com.fis.crm.crm_util.TaskTimesheetsMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/task-timesheets")
public class CrmTaskTimesheetsController {
    private final CrmTaskTimesheetsService timesheetsService;

    public CrmTaskTimesheetsController(CrmTaskTimesheetsService taskTimesheetsService) {
        this.timesheetsService = taskTimesheetsService;
    }

    @GetMapping
    public List<TaskTimesheetsDTO> getAllTimesheets() {
        List<CrmTaskTimesheets> taskTimesheets = timesheetsService.getAllTimesheets();
        List<TaskTimesheetsDTO> taskTimesheetsDTOS = new ArrayList<>();

        for (CrmTaskTimesheets crmTaskTimesheets : taskTimesheets) {
            TaskTimesheetsDTO taskTimesheetsDTO = TaskTimesheetsMapper.toDTO(crmTaskTimesheets);
            taskTimesheetsDTOS.add(taskTimesheetsDTO);
        }

        return taskTimesheetsDTOS;
    }

    @PostMapping("/{taskId}")
    public TaskTimesheetsDTO createTimesheets(@PathVariable Long taskId, @RequestBody TaskTimesheetsCreateDTO timesheetsCreateDTO) {
        return TaskTimesheetsMapper.toDTO(timesheetsService.createTimesheets(taskId, timesheetsCreateDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTimesheets(@PathVariable Long id) {
        try {
            timesheetsService.deleteTimesheets(id);
            return ResponseEntity.ok().body("Successful delete!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to delete task");
        }
    }

    @PostMapping("/searchByProjectId")
    public List<TaskTimesheetsDTO> searchTaskByTaskId(@RequestBody Map<String, Long> requestBody) {
        Long projectid = requestBody.get("projectid");
        List<CrmTaskTimesheets> taskTimesheetsList = timesheetsService.getTimesheetsByProjectId(projectid);
        List<TaskTimesheetsDTO> timesheetsDTOList = new ArrayList<>();

        for (CrmTaskTimesheets crmTaskTimesheets : taskTimesheetsList) {
            TaskTimesheetsDTO taskTimesheetsDTO = TaskTimesheetsMapper.toDTO(crmTaskTimesheets);
            timesheetsDTOList.add(taskTimesheetsDTO);
        }
        return timesheetsDTOList;
    }

    @PostMapping("/searchByUserId")
    public List<TaskTimesheetsDTO> searchTimesheetsByUser(@RequestBody Map<String, Long> requestBody) {
        Long creatorid = requestBody.get("creatorid");
        List<CrmTaskTimesheets> taskTimesheetsList = timesheetsService.getTimesheetsByUserId(creatorid);
        List<TaskTimesheetsDTO> timesheetsDTOList = new ArrayList<>();

        for (CrmTaskTimesheets crmTaskTimesheets : taskTimesheetsList) {
            TaskTimesheetsDTO taskTimesheetsDTO = TaskTimesheetsMapper.toDTO(crmTaskTimesheets);
            timesheetsDTOList.add(taskTimesheetsDTO);
        }
        return timesheetsDTOList;
    }
}

