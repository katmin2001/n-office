package com.fis.crm.crm_controller;

import com.fis.crm.crm_entity.CrmInterview;
import com.fis.crm.crm_entity.DTO.InterviewDTO;
import com.fis.crm.crm_entity.DTO.InterviewRequestDTO;
import com.fis.crm.crm_entity.DTO.SearchInterviewDTO;
import com.fis.crm.crm_service.InterviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/interview")
public class InterviewController {
    @Autowired
    private InterviewService interviewService;
    @GetMapping("/")
    public List<InterviewDTO> getAllInterview(){
        return interviewService.getAllInterview();
    }
    @GetMapping("/detail/{interviewId}")
    public InterviewDTO getInterviewById(@PathVariable("interviewId") Long interviewId){
        return interviewService.getInterviewById(interviewId);
    }
    @PostMapping("/add")
    public CrmInterview addInterview(@RequestBody InterviewRequestDTO interviewRequestDTO){
        return interviewService.addInterview(interviewRequestDTO);
    }
    @PostMapping("/edit/{interviewId}")
    public CrmInterview updateInterview(@RequestBody InterviewRequestDTO interviewRequestDTO,
                                        @PathVariable("interviewId") Long interviewId){
        return interviewService.updateInterview(interviewRequestDTO,interviewId);
    }
    @PostMapping("/add-interviewer/{interviewId}")
    public CrmInterview addInterviewDetail(@RequestBody InterviewRequestDTO interviewRequestDTO,
                                        @PathVariable("interviewId") Long interviewId){
        return interviewService.addInterviewDetail(interviewRequestDTO,interviewId);
    }
    @PostMapping("/delete-interviewer/{interviewId}")
    public CrmInterview deleteInterviewDetail(@RequestBody InterviewRequestDTO interviewRequestDTO,
                                           @PathVariable("interviewId") Long interviewId){
        return interviewService.deleteInterviewDetail(interviewRequestDTO,interviewId);
    }
    @PostMapping("/delete/{interviewId}")
    public CrmInterview deleteInterview(@PathVariable("interviewId") Long interviewId){
        return interviewService.deleteInterview(interviewId);
    }
    @PostMapping("/status/{interviewId}")
    public CrmInterview updateInterviewStatus(@RequestBody InterviewRequestDTO interviewRequestDTO,
                                              @PathVariable("interviewId") Long interviewId){
        return interviewService.updateStatusInterview(interviewRequestDTO,interviewId);
    }
    @PostMapping("/search")
    public List<InterviewDTO> searchInterview(@RequestBody SearchInterviewDTO searchInterviewDTO){
        return interviewService.searchInterview(searchInterviewDTO);
    }
}
