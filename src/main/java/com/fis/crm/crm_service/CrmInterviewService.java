package com.fis.crm.crm_service;

import com.fis.crm.crm_entity.DTO.*;

import java.util.List;

public interface CrmInterviewService {
    public List<InterviewDTO> getAllInterview();
    public InterviewDTO getInterviewById(Long interviewId);
    public Result addInterview(InterviewRequestDTO interview);
    public Result updateInterview(InterviewRequestDTO interview, Long interviewId);
    public Result addInterviewDetail(InterviewRequestDTO interviewRequestDTO, Long interviewId);
    public Result deleteInterviewDetail(InterviewRequestDTO interviewRequestDTO, Long interviewId);
    public Result deleteInterview(Long interviewId);
    public Result updateStatusInterview(InterviewRequestDTO interviewRequestDTO, Long interviewId);
    public Result searchInterview(SearchInterviewDTO searchInterviewDTO);
}
