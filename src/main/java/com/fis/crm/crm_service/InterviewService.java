package com.fis.crm.crm_service;

import com.fis.crm.crm_entity.CrmInterview;
import com.fis.crm.crm_entity.DTO.InterviewDTO;
import com.fis.crm.crm_entity.DTO.SearchInterviewDTO;

import java.util.List;
import java.util.Optional;

public interface InterviewService {
    public List<CrmInterview> getAllInterview();
    public Optional<CrmInterview> getInterviewById(Long interviewId);
    public CrmInterview addInterview(InterviewDTO interview);
    public CrmInterview updateInterview(InterviewDTO interview, Long interviewId);
    public CrmInterview addInterviewDetail(InterviewDTO interviewDTO, Long interviewId);
    public CrmInterview deleteInterviewDetail(InterviewDTO interviewDTO, Long interviewId);
    public CrmInterview deleteInterview(Long interviewId);
    public CrmInterview updateStatusInterview(InterviewDTO interviewDTO, Long interviewId);
    public List<CrmInterview> searchInterview(SearchInterviewDTO searchInterviewDTO);
}
