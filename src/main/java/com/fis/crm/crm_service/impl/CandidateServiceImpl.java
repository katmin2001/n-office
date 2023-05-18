package com.fis.crm.crm_service.impl;

import com.fis.crm.crm_entity.CrmCandidate;
import com.fis.crm.crm_entity.CrmUser;
import com.fis.crm.crm_entity.DTO.CandidateDTO;
import com.fis.crm.crm_entity.DTO.SearchCandidateDTO;
import com.fis.crm.crm_repository.CandidateRepo;
import com.fis.crm.crm_repository.IUserRepo;
import com.fis.crm.crm_repository.InterviewStatusRepo;
import com.fis.crm.crm_service.CandidateService;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.sql.Date;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@Transactional
public class CandidateServiceImpl implements CandidateService {
    private final CandidateRepo candidateRepo;
    private final IUserRepo userRepo;
    private final InterviewStatusRepo interviewStatusRepo;
    public CandidateServiceImpl(CandidateRepo candidateRepo, IUserRepo userRepo, InterviewStatusRepo interviewStatusRepo) {
        this.candidateRepo = candidateRepo;
        this.userRepo = userRepo;
        this.interviewStatusRepo = interviewStatusRepo;
    }


    @Override
    public List<CrmCandidate> getAllCandidate() {
        return candidateRepo.findAll();
    }

    @Override
    public Optional<CrmCandidate> getCandidateById(Long candidateId) {
        return candidateRepo.findById(candidateId);
    }

    @Override
    public CrmCandidate addCandidate(CandidateDTO candidateDTO) {
        candidateDTO.setISID(Long.valueOf(1));
        CrmCandidate candidate = new CrmCandidate();
        candidate.setAddress(candidateDTO.getAddress());
        candidate.setFullname(candidateDTO.getFullname());
        candidate.setPhone(candidateDTO.getPhone());
        candidate.setBirthday(candidateDTO.getBirthday());

        long millis=System.currentTimeMillis();
        Date date=new Date(millis);
        candidate.setCreateDate(date);

        candidate.setStatus(true);
        candidate.setUser(userRepo.findById(candidateDTO.getManageId()).orElse(null));
        candidate.setInterviewStatus(interviewStatusRepo.findById(candidateDTO.getISID()).orElse(null));
        return candidateRepo.save(candidate);
    }

    @Override
    public CrmCandidate updateCandidate(CandidateDTO candidateDTO, Long candidateId) {
        CrmCandidate candidate = candidateRepo.findById(candidateId).orElse(null);
        if(candidate == null){
            return null;
        }
        if(candidateDTO.getFullname() != null){
            candidate.setFullname(candidateDTO.getFullname());
        }
        if(candidateDTO.getAddress() != null){
            candidate.setAddress(candidateDTO.getAddress());
        }
        if(candidateDTO.getPhone() != null){
            candidate.setPhone(candidateDTO.getPhone());
        }
        if(candidateDTO.getBirthday() != null){
            candidate.setBirthday(candidateDTO.getBirthday());
        }
        if(candidateDTO.getStatus() != null){
            candidate.setStatus(candidateDTO.getStatus());
        }
        if(candidateDTO.getManageId() != null){
            candidate.setUser(userRepo.findById(candidateDTO.getManageId()).orElse(null));
        }
        return candidateRepo.save(candidate);
    }

    @Override
    public CrmCandidate deleteCandidate(Long candidateId) {
        CrmCandidate candidate = candidateRepo.findById(candidateId).orElse(null);
        if(candidate == null){
            return null;
        }
        candidate.setStatus(false);
        return candidateRepo.save(candidate);
    }

    @Override
    public List<CrmCandidate> searchCandidate(SearchCandidateDTO searchCandidateDTO) {
       return candidateRepo.searchCandidate(searchCandidateDTO.getStartDayCreate(),searchCandidateDTO.getEndDayCreate(),searchCandidateDTO.getStartDay(), searchCandidateDTO.getEndDay(), searchCandidateDTO.getISID(), searchCandidateDTO.getManageId());
    }

}
