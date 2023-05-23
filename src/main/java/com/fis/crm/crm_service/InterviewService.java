package com.fis.crm.crm_service;

import com.fis.crm.crm_entity.CrmInterview;
import com.fis.crm.crm_entity.DTO.InterviewDTO;
import com.fis.crm.crm_entity.DTO.InterviewRequestDTO;
import com.fis.crm.crm_entity.DTO.InterviewStatusDTO;
import com.fis.crm.crm_entity.DTO.SearchInterviewDTO;

import java.util.List;
import java.util.Optional;

public interface InterviewService {
    public List<InterviewDTO> getAllInterview();
    public InterviewDTO getInterviewById(Long interviewId);
    public CrmInterview addInterview(InterviewRequestDTO interview);
    public CrmInterview updateInterview(InterviewRequestDTO interview, Long interviewId);
    public CrmInterview addInterviewDetail(InterviewRequestDTO interviewRequestDTO, Long interviewId);
    public CrmInterview deleteInterviewDetail(InterviewRequestDTO interviewRequestDTO, Long interviewId);
    public CrmInterview deleteInterview(Long interviewId);
    public CrmInterview updateStatusInterview(InterviewRequestDTO interviewRequestDTO, Long interviewId);
    public List<InterviewDTO> searchInterview(SearchInterviewDTO searchInterviewDTO);
}
