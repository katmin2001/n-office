package com.fis.crm.crm_service;

import com.fis.crm.crm_entity.DTO.CandidateDTO;
import com.fis.crm.crm_entity.DTO.CandidateRequestDTO;
import com.fis.crm.crm_entity.DTO.Result;
import com.fis.crm.crm_entity.DTO.SearchCandidateDTO;

import java.util.List;


public interface CrmCandidateService {
    public List<CandidateDTO> getAllCandidate();
    public CandidateDTO getCandidateById(Long candidateId);
    public Result addCandidate(CandidateRequestDTO candidate);
    public Result updateCandidate(CandidateRequestDTO candidate, Long candidateId);
    public Result deleteCandidate(Long candidateId);
    public Result searchCandidate(SearchCandidateDTO searchCandidateDTO);

}
