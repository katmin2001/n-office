package com.fis.crm.service;

import com.fis.crm.domain.EvaluateResultDetail;
import com.fis.crm.service.dto.CriteriaCallDTO;
import com.fis.crm.service.dto.EvaluateResultDetailDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link EvaluateResultDetail}.
 */
public interface EvaluateResultDetailService {

    /**
     * Save a evaluateResultDetai1.
     *
     * @param evaluateResultDetailDTO the entity to save.
     * @return the persisted entity.
     */
    EvaluateResultDetailDTO save(EvaluateResultDetailDTO evaluateResultDetailDTO);

    /**
     * Get all the evaluateResultDetai1s.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<EvaluateResultDetailDTO> findAll(Pageable pageable);


    /**
     * Get the "id" evaluateResultDetai1.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<EvaluateResultDetailDTO> findOne(Long id);

    /**
     * Delete the "id" evaluateResultDetai1.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    Page<CriteriaCallDTO> getCriteriaDetailCall(EvaluateResultDetailDTO evaluateResultDetailDTO, Pageable pageable);

    List<CriteriaCallDTO> getAllCriteriaDetailCall(EvaluateResultDetailDTO evaluateResultDetailDTO);
}
