package com.fis.crm.crm_service;

import com.fis.crm.crm_entity.CrmCandidate;
import com.fis.crm.crm_entity.DTO.CandidateDTO;
import com.fis.crm.crm_entity.DTO.CandidateRequestDTO;
import com.fis.crm.crm_entity.DTO.SearchCandidateDTO;

import java.util.List;
import java.util.Optional;


public interface CandidateService {
    public List<CandidateDTO> getAllCandidate();
    public CandidateDTO getCandidateById(Long candidateId);
    public CrmCandidate addCandidate(CandidateRequestDTO candidate);
    public CrmCandidate updateCandidate(CandidateRequestDTO candidate, Long candidateId);
    public CrmCandidate deleteCandidate(Long candidateId);
    public List<CandidateDTO> searchCandidate(SearchCandidateDTO searchCandidateDTO);

}
