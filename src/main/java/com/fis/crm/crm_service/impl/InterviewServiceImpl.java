package com.fis.crm.crm_service.impl;

import com.fis.crm.crm_entity.CrmInterview;
import com.fis.crm.crm_entity.CrmUser;
import com.fis.crm.crm_entity.DTO.*;
import com.fis.crm.crm_repository.CandidateRepo;
import com.fis.crm.crm_repository.IUserRepo;
import com.fis.crm.crm_repository.InterviewRepo;
import com.fis.crm.crm_repository.InterviewStatusRepo;
import com.fis.crm.crm_service.InterviewService;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.sql.Date;
import java.util.*;

@Service
@Transactional
public class InterviewServiceImpl implements InterviewService{
    private final CandidateRepo candidateRepo;
    private final InterviewRepo interviewRepo;
    private final InterviewStatusRepo interviewStatusRepo;
    private final IUserRepo userRepo;

    public InterviewServiceImpl(CandidateRepo candidateRepo, InterviewRepo interviewRepo, InterviewStatusRepo interviewStatusRepo, IUserRepo userRepo) {
        this.candidateRepo = candidateRepo;
        this.interviewRepo = interviewRepo;
        this.interviewStatusRepo = interviewStatusRepo;
        this.userRepo = userRepo;
    }

    @Override
    public List<InterviewDTO> getAllInterview() {
//        List<CrmInterview> crmInterviews = interviewRepo.findAll();
//        List<InterviewDTO> interviewDTOS = new ArrayList<>();
//        for(CrmInterview interview: crmInterviews){
//            Set<Crm_UserDTO> crmUserDTOS = new HashSet<>();
//            Set<CrmUser> crmUsers = interview.getUsers();
//            for (CrmUser crmUser: crmUsers){
//                Crm_UserDTO crmUserDTO = new Crm_UserDTO(
//                    crmUser.getUserid(),
//                    crmUser.getUsername(),
//                    crmUser.getFullname(),
//                    crmUser.getCreatedate(),
//                    crmUser.getPhone(),
//                    crmUser.getBirthday(),
//                    crmUser.getAddress(),
//                    crmUser.getStatus()
//                );
//                crmUserDTOS.add(crmUserDTO);
//            }
//            InterviewDTO interviewDTO = new InterviewDTO(
//                interview.getInterviewid(),
//                interview.getInterviewDate(),
//                interview.getStatus(),
//                interview.getCreateDate(),
//                interview.getCandidate().getFullname(),
//                interview.getInterviewStatus().getStatusName(),
//                crmUserDTOS
//            );
//            interviewDTOS.add(interviewDTO);
//        }
//        return interviewDTOS;
        return null;
    }

    @Override
    public InterviewDTO getInterviewById(Long interviewId) {
        CrmInterview interview = interviewRepo.findById(interviewId).orElse(null);
        Set<Crm_UserDTO> crmUserDTOS = new HashSet<>();
        Set<CrmUser> crmUsers = interview.getUsers();
        for (CrmUser crmUser: crmUsers){
            Crm_UserDTO crmUserDTO = new Crm_UserDTO(
                crmUser.getUserid(),
                crmUser.getUsername(),
                crmUser.getFullname(),
                crmUser.getCreatedate(),
                crmUser.getPhone(),
                crmUser.getBirthday(),
                crmUser.getAddress(),
                crmUser.getStatus()
            );
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
    public CrmInterview addInterview(InterviewRequestDTO interviewRequestDTO) {
        CrmInterview interview = new CrmInterview();
        Set<CrmUser> users = new HashSet<>();
        long millis=System.currentTimeMillis();
        Date date=new Date(millis);
        interview.setCreateDate(date);
        interview.setStatus(true);
        interview.setInterviewDate(interviewRequestDTO.getInterviewDate());
        interview.setCandidate(candidateRepo.findById(interviewRequestDTO.getCandidateId()).orElse(null));
        interview.setInterviewStatus(interviewStatusRepo.findById(Long.valueOf(2)).orElse(null));
        if(userRepo.findById(interviewRequestDTO.getUserId()).orElse(null) == null){
            return null;
        }
        else {
            users.add(userRepo.findById(interviewRequestDTO.getUserId()).orElse(null));
            interview.setUsers(users);
        }
        return interviewRepo.save(interview);
    }

    @Override
    public CrmInterview updateInterview(InterviewRequestDTO interviewRequestDTO, Long interviewId) {
        CrmInterview interview = interviewRepo.findById(interviewId).orElse(null);
        if(interview == null){
            return null;
        }
        if(interviewRequestDTO.getInterviewDate() != null){
            interview.setInterviewDate(interviewRequestDTO.getInterviewDate());
        }
        if(interviewRequestDTO.getStatus()!= null){
            interview.setStatus(interviewRequestDTO.getStatus());
        }
        if(interviewRequestDTO.getCandidateId() != null){
            interview.setCandidate(candidateRepo.findById(interviewRequestDTO.getCandidateId()).orElse(null));
        }
        return interviewRepo.save(interview);
    }

    @Override
    public CrmInterview addInterviewDetail(InterviewRequestDTO interviewRequestDTO, Long interviewId) {
        CrmInterview interview = interviewRepo.findById(interviewId).orElse(null);
        if(interview == null){
            return null;
        }
        boolean check = true;
        Set<CrmUser> users = interview.getUsers();
        for(CrmUser user: users){
            if(user.getUserid() == interviewRequestDTO.getUserId()){
                check = false;
            }
        }
        if(check == true){
            users.add(userRepo.findById(interviewRequestDTO.getUserId()).orElse(null));
        }
        interview.setUsers(users);
        return interviewRepo.save(interview);
    }

    @Override
    public CrmInterview deleteInterviewDetail(InterviewRequestDTO interviewRequestDTO, Long interviewId) {
        CrmInterview interview = interviewRepo.findById(interviewId).orElse(null);
        if(interview == null){
            return null;
        }
        Set<CrmUser> users = interview.getUsers();
        for(CrmUser user: users){
            if(user.getUserid() == interviewRequestDTO.getUserId()){
                users.remove(userRepo.findById(interviewRequestDTO.getUserId()).orElse(null));
                break;
            }
        }
        interview.setUsers(users);
        return interviewRepo.save(interview);
    }

    @Override
    public CrmInterview deleteInterview(Long interviewId) {
        CrmInterview interview = interviewRepo.findById(interviewId).orElse(null);
        if(interview == null){
            return null;
        }
        interview.setStatus(false);
        return interviewRepo.save(interview);
    }
    @Override
    public CrmInterview updateStatusInterview(InterviewRequestDTO interviewRequestDTO, Long interviewId){
        CrmInterview interview = interviewRepo.findById(interviewId).orElse(null);
        if(interview == null){
            return null;
        }
        interview.setInterviewStatus(interviewStatusRepo.findById(interviewRequestDTO.getISID()).orElse(null));
        interview.getCandidate().setInterviewStatus(interviewStatusRepo.findById(interviewRequestDTO.getISID()).orElse(null));
        return interviewRepo.save(interview);
    }

    @Override
    public List<InterviewDTO> searchInterview(SearchInterviewDTO searchInterviewDTO) {
        List<CrmInterview> crmInterviews =  interviewRepo.searchInterview(searchInterviewDTO.getStartDay(),searchInterviewDTO.getEndDay(),searchInterviewDTO.getISID(),searchInterviewDTO.getInterviewer());
        List<InterviewDTO> interviewDTOS = new ArrayList<>();
        for(CrmInterview interview: crmInterviews){
            Set<Crm_UserDTO> crmUserDTOS = new HashSet<>();
            Set<CrmUser> crmUsers = interview.getUsers();
            for (CrmUser crmUser: crmUsers){
                Crm_UserDTO crmUserDTO = new Crm_UserDTO(
                    crmUser.getUserid(),
                    crmUser.getUsername(),
                    crmUser.getFullname(),
                    crmUser.getCreatedate(),
                    crmUser.getPhone(),
                    crmUser.getBirthday(),
                    crmUser.getAddress(),
                    crmUser.getStatus()
                );
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
}
