package com.fis.crm.crm_service;

import com.fis.crm.crm_entity.CrmInterview;
import com.fis.crm.crm_entity.DTO.InterviewDTO;

import java.util.List;
import java.util.Optional;

public interface InterviewService {
    public List<CrmInterview> getAllInterview();
    public Optional<CrmInterview> getInterviewById(Long interviewId);
    public CrmInterview addInterview(InterviewDTO interview);
    public CrmInterview updateInterview(InterviewDTO interview, Long interviewId);
    public CrmInterview deleteInterview(Long interviewId);
    public CrmInterview updateStatusInterview(InterviewDTO interviewDTO, Long interviewId);
//    public void search();
}
