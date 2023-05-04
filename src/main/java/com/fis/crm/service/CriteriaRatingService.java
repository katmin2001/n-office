package com.fis.crm.service;

import com.fis.crm.service.dto.CriteriaRatingDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface CriteriaRatingService {

    CriteriaRatingDTO save(CriteriaRatingDTO criteriaRatingDTO);

    CriteriaRatingDTO update(CriteriaRatingDTO criteriaRatingDTO);

    Page<CriteriaRatingDTO> findAll(Pageable pageable);

    Optional<CriteriaRatingDTO> findOne(Long id);

    CriteriaRatingDTO delete(CriteriaRatingDTO criteriaRatingDTO);

    boolean checkValue(CriteriaRatingDTO criteriaRatingDTO);

    Integer checkValid(CriteriaRatingDTO criteriaRatingDTO);

    boolean checkName(String name);

    Optional<List<CriteriaRatingDTO>> findAllCriteriaRating();
}
