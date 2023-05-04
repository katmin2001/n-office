package com.fis.crm.service;

import com.fis.crm.service.dto.EvaluateResultResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Service Interface for managing {@link com.fis.crm.domain.EvaluateResultResponse}.
 */
public interface EvaluateResultResponseService {

    /**
     * Save a evaluateResultResponse.
     *
     * @param evaluateResultResponseDTO the entity to save.
     * @return the persisted entity.
     */
    EvaluateResultResponseDTO save(EvaluateResultResponseDTO evaluateResultResponseDTO);

    /**
     * Get all the evaluateResultResponses.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
//    Page<EvaluateResultResponseDTO> findAll(Pageable pageable);


    /**
     * Get the "id" evaluateResultResponse.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
//    Optional<EvaluateResultResponseDTO> findOne(Long id);

    /**
     * Delete the "id" evaluateResultResponse.
     *
     * @param id the id of the entity.
     * @return
     */
//    void delete(Long id);

    Page<EvaluateResultResponseDTO> getEvaluateResultResponseByEvaluateResult(Long evaluateResultId, Pageable pageable);
}
