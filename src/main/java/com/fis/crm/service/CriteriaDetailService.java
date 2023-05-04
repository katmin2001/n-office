package com.fis.crm.service;

import com.fis.crm.service.dto.CriteriaDetailDTO;
import com.fis.crm.service.dto.CriteriaScoresDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface CriteriaDetailService {

    CriteriaDetailDTO save(CriteriaDetailDTO criteriaDetailDTO);

    CriteriaDetailDTO update(CriteriaDetailDTO criteriaDetailDTO);

    Page<CriteriaDetailDTO> findAll(Pageable pageable);

    Optional<CriteriaDetailDTO> findOne(Long id);

    CriteriaDetailDTO findOneByName(String name);

    CriteriaDetailDTO delete(CriteriaDetailDTO criteriaDetailDTO);

    boolean checkValue(String content);

    CriteriaScoresDTO getScores(Long id);

}
