package com.fis.crm.service;

import com.fis.crm.service.dto.EvaluateResultDTO;
import com.fis.crm.service.dto.EvaluateResultRatingDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.text.ParseException;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.fis.crm.domain.EvaluateResult}.
 */
public interface EvaluateResultService {

    /**
     * Get the "id" evaluateResult.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<EvaluateResultDTO> findOne(Long id, String type);

    /**
     * Delete the "id" evaluateResult.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    Boolean createEvaluateResult(EvaluateResultDTO evaluateResultDTO) throws Exception;

    Optional<EvaluateResultDTO> getTicketForEvaluate(Long id);

    Page<EvaluateResultDTO> getEvaluateResults(EvaluateResultDTO evaluateResultDTO, Pageable pageable) throws ParseException;

    List<EvaluateResultDTO> getAllEvaluateResults(EvaluateResultDTO evaluateResultDTO) throws ParseException;

    Boolean reEvaluateResult(EvaluateResultDTO evaluateResultDTO) throws Exception;

    Page<EvaluateResultRatingDTO> getRatingCall(EvaluateResultDTO evaluateResultDTO, Pageable pageable);

    List<EvaluateResultRatingDTO> getAllRatingCall(EvaluateResultDTO evaluateResultDTO) throws ParseException;
}
