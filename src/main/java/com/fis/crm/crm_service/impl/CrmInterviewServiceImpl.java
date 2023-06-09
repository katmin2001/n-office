package com.fis.crm.crm_service.impl;

import com.fis.crm.crm_entity.CrmInterview;
import com.fis.crm.crm_entity.CrmUser;
import com.fis.crm.crm_entity.DTO.*;
import com.fis.crm.crm_repository.CrmCandidateRepo;
import com.fis.crm.crm_repository.CrmUserRepo;
import com.fis.crm.crm_repository.CrmInterviewRepo;
import com.fis.crm.crm_repository.CrmInterviewStatusRepo;
import com.fis.crm.crm_service.CrmInterviewService;

import com.fis.crm.crm_util.DtoMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.sql.Date;
import java.util.*;



@Service
@Transactional
public class CrmInterviewServiceImpl implements CrmInterviewService{
    private final Logger log = LoggerFactory.getLogger(CrmInterviewServiceImpl.class);
    private final DtoMapper mapper = new DtoMapper();
    private final CrmCandidateRepo candidateRepo;
    private final CrmInterviewRepo interviewRepo;
    private final CrmInterviewStatusRepo interviewStatusRepo;
    private final CrmUserRepo userRepo;

    public CrmInterviewServiceImpl(CrmCandidateRepo candidateRepo, CrmInterviewRepo interviewRepo, CrmInterviewStatusRepo interviewStatusRepo, CrmUserRepo userRepo) {
        this.candidateRepo = candidateRepo;
        this.interviewRepo = interviewRepo;
        this.interviewStatusRepo = interviewStatusRepo;
        this.userRepo = userRepo;
    }

    @Override
    public List<InterviewDTO> getAllInterview() {
        List<CrmInterview> crmInterviews = interviewRepo.findAll();
        List<InterviewDTO> interviewDTOS = new ArrayList<>();
        for(CrmInterview interview: crmInterviews){
            Set<CrmUserDTO> crmUserDTOS = new HashSet<>();
            Set<CrmUser> crmUsers = interview.getUsers();
            for (CrmUser crmUser: crmUsers){

                CrmUserDTO crmUserDTO = mapper.userDtoMapper(crmUser);
                crmUserDTOS.add(crmUserDTO);
            }
            InterviewDTO interviewDTO = new InterviewDTO(
                interview.getInterviewid(),
                interview.getInterviewDate(),
                interview.getStatus(),
                interview.getCreateDate(),
                interview.getCandidate().getFullname(),
                interview.getInterviewStatus().getStatusName(),
                crmUserDTOS
            );
            interviewDTOS.add(interviewDTO);
        }
        return interviewDTOS;
    }

    @Override
    public InterviewDTO getInterviewById(Long interviewId) {
        CrmInterview interview = interviewRepo.findById(interviewId).orElse(null);
        if(interview == null){
            log.error("Không tồn tại buổi hẹn phỏng vấn!",new NullPointerException());
        }
        Set<CrmUserDTO> crmUserDTOS = new HashSet<>();
        Set<CrmUser> crmUsers = interview.getUsers();
        for (CrmUser crmUser: crmUsers){
            CrmUserDTO crmUserDTO = mapper.userDtoMapper(crmUser);
            crmUserDTOS.add(crmUserDTO);
        }
        InterviewDTO interviewDTO = new InterviewDTO(
            interview.getInterviewid(),
            interview.getInterviewDate(),
            interview.getStatus(),
            interview.getCreateDate(),
            interview.getCandidate().getFullname(),
            interview.getInterviewStatus().getStatusName(),
            crmUserDTOS
        );
        return interviewDTO;
    }

    @Override
    public Result addInterview(InterviewRequestDTO interviewRequestDTO) {
        CrmInterview interview = new CrmInterview();
        Set<CrmUser> users = new HashSet<>();
        long millis=System.currentTimeMillis();
        Date date=new Date(millis);
        interview.setCreateDate(date);
        interview.setStatus(true);
        if(interviewRequestDTO.getInterviewDate().compareTo(date) >= 0){
            interview.setInterviewDate(interviewRequestDTO.getInterviewDate());
        }
        else {
            log.error("Lỗi sai lệch thời gian phỏng vấn!",new NullPointerException());
            return new Result("FAIL_DATE","Sai lệch thời gian phỏng vấn","");
        }
        interview.setInterviewDate(interviewRequestDTO.getInterviewDate());
        interview.setCandidate(candidateRepo.findById(interviewRequestDTO.getCandidateId()).orElse(null));
        interview.setInterviewStatus(interviewStatusRepo.findById(Long.valueOf(2)).orElse(null));
        for(Long interviewerId : interviewRequestDTO.getUserId()){
            if(userRepo.findById(interviewerId).orElse(null) == null){
                log.error("Không tồn tại user!",new NullPointerException());
            }
            else{
                users.add(userRepo.findById(interviewerId).orElse(null));
            }
        }
        interview.setUsers(users);
        return new Result("SUCCESS","Thêm thành công!",interviewRepo.save(interview));
    }

    @Override
    public Result updateInterview(InterviewRequestDTO interviewRequestDTO, Long interviewId) {
        CrmInterview interview = interviewRepo.findById(interviewId).orElse(null);
        if(interview == null){
            log.error("Không tồn tại buổi hẹn phỏng vấn!",new NullPointerException());
        }
        long millis=System.currentTimeMillis();
        Date date=new Date(millis);
        interview.setCreateDate(date);
        if(interviewRequestDTO.getInterviewDate() != null){
            if(interviewRequestDTO.getInterviewDate().compareTo(date) >= 0){
                interview.setInterviewDate(interviewRequestDTO.getInterviewDate());
            }
            else {
                log.error("Lỗi sai lệch thời gian phỏng vấn!",new NullPointerException());
                return new Result("FAIL_DATE","Sai lệch thời gian phỏng vấn","");
            }
        }
        if(interviewRequestDTO.getStatus()!= null){
            interview.setStatus(interviewRequestDTO.getStatus());
        }
        if(interviewRequestDTO.getCandidateId() != null){
            interview.setCandidate(candidateRepo.findById(interviewRequestDTO.getCandidateId()).orElse(null));
        }
        return new Result("SUCCESS","Cập nhật thành công!",interviewRepo.save(interview));
    }

    @Override
    public Result updateInterviewer(InterviewRequestDTO interviewRequestDTO, Long interviewId) {
        CrmInterview interview = interviewRepo.findById(interviewId).orElse(null);
        if(interview == null){
            log.error("Không tồn tại buổi hẹn phỏng vấn!",new NullPointerException());
        }
        Set<CrmUser> users = new HashSet<>();
        for(Long interviewerId: interviewRequestDTO.getUserId()){
            if(userRepo.findById(interviewerId).orElse(null) == null){
                log.error("Không tồn tại user!",new NullPointerException());
            }
            else {
                users.add(userRepo.findById(interviewerId).orElse(null));
            }
        }
        interview.setUsers(users);
        return new Result("SUCCESS","Cập nhật thành công!",interviewRepo.save(interview));
    }

    @Override
    public Result deleteInterview(Long interviewId) {
        CrmInterview interview = interviewRepo.findById(interviewId).orElse(null);
        if(interview == null){
            log.error("Không tồn tại buổi hẹn phỏng vấn!",new NullPointerException());
        }
        interview.setStatus(false);
        return new Result("SUCCESS","Xoá thành công!",interviewRepo.save(interview));
    }
    @Override
    public Result updateStatusInterview(InterviewRequestDTO interviewRequestDTO, Long interviewId){
        CrmInterview interview = interviewRepo.findById(interviewId).orElse(null);
        if(interview == null){
            log.error("Không tồn tại buổi hẹn phỏng vấn!",new NullPointerException());
        }
        interview.setInterviewStatus(interviewStatusRepo.findById(interviewRequestDTO.getISID()).orElse(null));
        interview.getCandidate().setInterviewStatus(interviewStatusRepo.findById(interviewRequestDTO.getISID()).orElse(null));
        return new Result("SUCCESS","Cập nhật thành công!",interviewRepo.save(interview));
    }

    @Override
    public Result searchInterview(SearchInterviewDTO searchInterviewDTO) {
        List<CrmInterview> crmInterviews =  interviewRepo.searchInterview(searchInterviewDTO.getStartDay(),searchInterviewDTO.getEndDay(),searchInterviewDTO.getISID(),searchInterviewDTO.getInterviewer());
        List<InterviewDTO> interviewDTOS = new ArrayList<>();
        for(CrmInterview interview: crmInterviews){
            Set<CrmUserDTO> crmUserDTOS = new HashSet<>();
            Set<CrmUser> crmUsers = interview.getUsers();
            for (CrmUser crmUser: crmUsers){
                CrmUserDTO crmUserDTO = mapper.userDtoMapper(crmUser);
                crmUserDTOS.add(crmUserDTO);
            }
            InterviewDTO interviewDTO = new InterviewDTO(
                interview.getInterviewid(),
                interview.getInterviewDate(),
                interview.getStatus(),
                interview.getCreateDate(),
                interview.getCandidate().getFullname(),
                interview.getInterviewStatus().getStatusName(),
                crmUserDTOS
            );
            interviewDTOS.add(interviewDTO);
        }
        if (interviewDTOS.size() == 0){
            return new Result("NOT_FOUND", "Không tồn tại kết quả!","");
        }
        else {
            return new Result("OK", "Tìm kiếm thành công!", interviewDTOS);
        }
    }
}
