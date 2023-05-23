package com.fis.crm.crm_controller;

import com.fis.crm.crm_entity.CrmCandidate;
import com.fis.crm.crm_entity.DTO.CandidateDTO;
import com.fis.crm.crm_entity.DTO.CandidateRequestDTO;
import com.fis.crm.crm_entity.DTO.Result;
import com.fis.crm.crm_entity.DTO.SearchCandidateDTO;
import com.fis.crm.crm_service.CandidateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/candidate")
public class CandidateController {
    @Autowired
    private CandidateService candidateService;

    @GetMapping("/")
    public List<CandidateDTO> getAllCandidate(){
        return candidateService.getAllCandidate();
    }
    @GetMapping("/detail/{candidateId}")
    public CandidateDTO getCandidateById(@PathVariable("candidateId") Long candidateId){
        return candidateService.getCandidateById(candidateId);
    }
    @PostMapping("/add")
    public Result addCandidate(@RequestBody CandidateRequestDTO candidate){
        return candidateService.addCandidate(candidate);
    }
    @PostMapping("/edit/{candidateId}")
    public Result updateCandidate(@PathVariable("candidateId") Long candidateId,
                                        @RequestBody CandidateRequestDTO candidate){
        return candidateService.updateCandidate(candidate, candidateId);
    }
    @PostMapping("/delete/{candidateId}")
    public Result deleteCandidate(@PathVariable("candidateId") Long candidateId){
        return candidateService.deleteCandidate(candidateId);
    }
    @PostMapping("/search")
    public Result searchCandidate(@RequestBody SearchCandidateDTO searchCandidateDTO){
        return candidateService.searchCandidate(searchCandidateDTO);
    }
}
