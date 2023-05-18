package com.fis.crm.crm_controller;

import com.fis.crm.crm_entity.CrmCandidate;
import com.fis.crm.crm_entity.DTO.CandidateDTO;
import com.fis.crm.crm_entity.DTO.SearchCandidateDTO;
import com.fis.crm.crm_service.CandidateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/candidate")
public class CandidateController {
    @Autowired
    private CandidateService candidateService;

    @GetMapping("/")
    public List<CrmCandidate> getAllCandidate(){
        return candidateService.getAllCandidate();
    }
    @GetMapping("/detail/{candidateId}")
    public Optional<CrmCandidate> getCandidateById(@PathVariable("candidateId") Long candidateId){
        return candidateService.getCandidateById(candidateId);
    }
    @PostMapping("/add")
    public CrmCandidate addCandidate(@RequestBody CandidateDTO candidate){
        return candidateService.addCandidate(candidate);
    }
    @PostMapping("/edit/{candidateId}")
    public CrmCandidate updateCandidate(@PathVariable("candidateId") Long candidateId,
                                        @RequestBody CandidateDTO candidate){
        return candidateService.updateCandidate(candidate, candidateId);
    }
    @PostMapping("/delete/{candidateId}")
    public CrmCandidate deleteCandidate(@PathVariable("candidateId") Long candidateId){
        return candidateService.deleteCandidate(candidateId);
    }
    @PostMapping("/search")
    public List<CrmCandidate> searchCandidate(@RequestBody SearchCandidateDTO searchCandidateDTO){
        return candidateService.searchCandidate(searchCandidateDTO);
    }
}
