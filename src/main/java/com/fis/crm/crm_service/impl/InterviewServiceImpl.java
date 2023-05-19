package com.fis.crm.crm_service.impl;

import com.fis.crm.crm_entity.CrmInterview;
import com.fis.crm.crm_entity.CrmUser;
import com.fis.crm.crm_entity.DTO.InterviewDTO;
import com.fis.crm.crm_repository.CandidateRepo;
import com.fis.crm.crm_repository.IUserRepo;
import com.fis.crm.crm_repository.InterviewRepo;
import com.fis.crm.crm_repository.InterviewStatusRepo;
import com.fis.crm.crm_service.InterviewService;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.sql.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
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
    public List<CrmInterview> getAllInterview() {
        return interviewRepo.findAll();
    }

    @Override
    public Optional<CrmInterview> getInterviewById(Long interviewId) {
        Optional<CrmInterview> interview = interviewRepo.findById(interviewId);
        return interview;
    }

    @Override
    public CrmInterview addInterview(InterviewDTO interviewDTO) {
        CrmInterview interview = new CrmInterview();
        Set<CrmUser> users = new HashSet<>();
        long millis=System.currentTimeMillis();
        Date date=new Date(millis);
        interview.setCreateDate(date);
        interview.setStatus(true);
        interview.setInterviewDate(interviewDTO.getInterviewDate());
        interview.setCandidate(candidateRepo.findById(interviewDTO.getCandidateId()).orElse(null));
        interview.setInterviewStatus(candidateRepo.findById(interviewDTO.getCandidateId()).orElse(null).getInterviewStatus());
        for(Long userid: interviewDTO.getUserId()){
            users.add(userRepo.findById(userid).orElse(null));
        }
        interview.setUsers(users);
        return interviewRepo.save(interview);
    }

    @Override
    public CrmInterview updateInterview(InterviewDTO interviewDTO, Long interviewId) {
        CrmInterview interview = interviewRepo.findById(interviewId).orElse(null);
        if(interview == null){
            return null;
        }
        Set<CrmUser> users = new HashSet<>();
        if(interviewDTO.getInterviewDate() != null){
            interview.setInterviewDate(interviewDTO.getInterviewDate());
        }
        if(interviewDTO.getStatus()!= null){
            interview.setStatus(interviewDTO.getStatus());
        }
        if(interviewDTO.getCandidateId() != null){
            interview.setCandidate(candidateRepo.findById(interviewDTO.getCandidateId()).orElse(null));
        }
        for(Long userid: interviewDTO.getUserId()){
            users.add(userRepo.findById(userid).orElse(null));
        }
        if(users.isEmpty() == false){
            interview.setUsers(users);
        }
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
    public CrmInterview updateStatusInterview(InterviewDTO interviewDTO, Long interviewId){
        CrmInterview interview = interviewRepo.findById(interviewId).orElse(null);
        if(interview == null){
            return null;
        }
        interview.setInterviewStatus(interviewStatusRepo.findById(interviewDTO.getISID()).orElse(null));
        interview.getCandidate().setInterviewStatus(interviewStatusRepo.findById(interviewDTO.getISID()).orElse(null));
        return interviewRepo.save(interview);
    }
}
