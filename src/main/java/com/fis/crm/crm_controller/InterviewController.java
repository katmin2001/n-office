package com.fis.crm.crm_controller;

import com.fis.crm.crm_entity.CrmInterview;
import com.fis.crm.crm_entity.DTO.InterviewDTO;
import com.fis.crm.crm_entity.DTO.SearchInterviewDTO;
import com.fis.crm.crm_service.InterviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/interview")
public class InterviewController {
    @Autowired
    private InterviewService interviewService;
    @GetMapping("/")
    public List<CrmInterview> getAllInterview(){
        return interviewService.getAllInterview();
    }
    @GetMapping("/detail/{interviewId}")
    public Optional<CrmInterview> getInterviewById(@PathVariable("interviewId") Long interviewId){
        return interviewService.getInterviewById(interviewId);
    }
    @PostMapping("/add")
    public CrmInterview addInterview(@RequestBody InterviewDTO interviewDTO){
        return interviewService.addInterview(interviewDTO);
    }
    @PostMapping("/edit/{interviewId}")
    public CrmInterview updateInterview(@RequestBody InterviewDTO interviewDTO,
                                        @PathVariable("interviewId") Long interviewId){
        return interviewService.updateInterview(interviewDTO ,interviewId);
    }
    @PostMapping("/add-interviewer/{interviewId}")
    public CrmInterview addInterviewDetail(@RequestBody InterviewDTO interviewDTO,
                                        @PathVariable("interviewId") Long interviewId){
        return interviewService.addInterviewDetail(interviewDTO ,interviewId);
    }
    @PostMapping("/delete-interviewer/{interviewId}")
    public CrmInterview deleteInterviewDetail(@RequestBody InterviewDTO interviewDTO,
                                           @PathVariable("interviewId") Long interviewId){
        return interviewService.deleteInterviewDetail(interviewDTO ,interviewId);
    }
    @PostMapping("/delete/{interviewId}")
    public CrmInterview deleteInterview(@PathVariable("interviewId") Long interviewId){
        return interviewService.deleteInterview(interviewId);
    }
    @PostMapping("/status/{interviewId}")
    public CrmInterview updateInterviewStatus(@RequestBody InterviewDTO interviewDTO,
                                              @PathVariable("interviewId") Long interviewId){
        return interviewService.updateStatusInterview(interviewDTO,interviewId);
    }
    @PostMapping("/search")
    public List<CrmInterview> searchInterview(@RequestBody SearchInterviewDTO searchInterviewDTO){
        return interviewService.searchInterview(searchInterviewDTO);
    }
}
