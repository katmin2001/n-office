package com.fis.crm.crm_service;

import com.fis.crm.crm_entity.CrmCandidate;
import com.fis.crm.crm_entity.DTO.CandidateDTO;
import com.fis.crm.crm_entity.DTO.SearchCandidateDTO;

import java.util.List;
import java.util.Optional;
import java.util.Set;


public interface CandidateService {
    public List<CrmCandidate> getAllCandidate();
    public Optional<CrmCandidate> getCandidateById(Long candidateId);
    public CrmCandidate addCandidate(CandidateDTO candidate);
    public CrmCandidate updateCandidate(CandidateDTO candidate, Long candidateId);
    public CrmCandidate deleteCandidate(Long candidateId);
    public List<CrmCandidate> searchCandidate(SearchCandidateDTO searchCandidateDTO);

}
