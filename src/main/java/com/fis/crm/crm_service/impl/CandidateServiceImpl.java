package com.fis.crm.crm_service.impl;

import com.fis.crm.crm_entity.CrmCandidate;
import com.fis.crm.crm_entity.DTO.*;
import com.fis.crm.crm_repository.CandidateRepo;
import com.fis.crm.crm_repository.IUserRepo;
import com.fis.crm.crm_repository.InterviewStatusRepo;
import com.fis.crm.crm_service.CandidateService;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

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
    public List<CandidateDTO> getAllCandidate() {
        List<CrmCandidate> crmCandidates = candidateRepo.findAll();
        List<CandidateDTO> candidateDTOS = new ArrayList<>();
        for(CrmCandidate candidate:crmCandidates){
            InterviewStatusDTO interviewStatusDTO = new InterviewStatusDTO(candidate.getInterviewStatus().getIsid(),candidate.getInterviewStatus().getStatusName(),candidate.getInterviewStatus().getDescription());
            Crm_UserDTO crmUserDTO = new Crm_UserDTO(candidate.getUser().getUsername(),
                candidate.getUser().getFullname(),
                candidate.getUser().getCreatedate(),
                candidate.getUser().getPhone(),
                candidate.getUser().getBirthday(),
                candidate.getUser().getAddress(),
                candidate.getUser().getStatus());
            CandidateDTO candidateDTO = new CandidateDTO(candidate.getCandidateid(),
                candidate.getFullname(),
                candidate.getPhone(),
                candidate.getBirthday(),
                candidate.getAddress(),
                candidate.getStatus(),
                candidate.getCreateDate(),
                interviewStatusDTO,
                crmUserDTO
                );
            candidateDTOS.add(candidateDTO);
        }
        return candidateDTOS;
    }

    @Override
    public CandidateDTO getCandidateById(Long candidateId) {
        CrmCandidate candidate = candidateRepo.findById(candidateId).orElse(null);
        InterviewStatusDTO interviewStatusDTO = new InterviewStatusDTO(candidate.getInterviewStatus().getIsid(),candidate.getInterviewStatus().getStatusName(),candidate.getInterviewStatus().getDescription());
        Crm_UserDTO crmUserDTO = new Crm_UserDTO(candidate.getUser().getUsername(),
            candidate.getUser().getFullname(),
            candidate.getUser().getCreatedate(),
            candidate.getUser().getPhone(),
            candidate.getUser().getBirthday(),
            candidate.getUser().getAddress(),
            candidate.getUser().getStatus());
        CandidateDTO candidateDTO = new CandidateDTO(candidate.getCandidateid(),
            candidate.getFullname(),
            candidate.getPhone(),
            candidate.getBirthday(),
            candidate.getAddress(),
            candidate.getStatus(),
            candidate.getCreateDate(),
            interviewStatusDTO,
            crmUserDTO
        );
        return candidateDTO;
    }

    @Override
    public CrmCandidate addCandidate(CandidateRequestDTO candidateDTO) {
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
    public CrmCandidate updateCandidate(CandidateRequestDTO candidateDTO, Long candidateId) {
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
    public List<CandidateDTO> searchCandidate(SearchCandidateDTO searchCandidateDTO) {
       List<CrmCandidate> crmCandidates =  candidateRepo.searchCandidate(searchCandidateDTO.getStartDayCreate(),searchCandidateDTO.getEndDayCreate(),searchCandidateDTO.getStartDay(), searchCandidateDTO.getEndDay(), searchCandidateDTO.getISID(), searchCandidateDTO.getManageId());
        List<CandidateDTO> candidateDTOS = new ArrayList<>();
        for(CrmCandidate candidate:crmCandidates){
            InterviewStatusDTO interviewStatusDTO = new InterviewStatusDTO(candidate.getInterviewStatus().getIsid(),candidate.getInterviewStatus().getStatusName(),candidate.getInterviewStatus().getDescription());
            Crm_UserDTO crmUserDTO = new Crm_UserDTO(candidate.getUser().getUsername(),
                candidate.getUser().getFullname(),
                candidate.getUser().getCreatedate(),
                candidate.getUser().getPhone(),
                candidate.getUser().getBirthday(),
                candidate.getUser().getAddress(),
                candidate.getUser().getStatus());
            CandidateDTO candidateDTO = new CandidateDTO(candidate.getCandidateid(),
                candidate.getFullname(),
                candidate.getPhone(),
                candidate.getBirthday(),
                candidate.getAddress(),
                candidate.getStatus(),
                candidate.getCreateDate(),
                interviewStatusDTO,
                crmUserDTO
            );
            candidateDTOS.add(candidateDTO);
        }
        return candidateDTOS;
    }

}
