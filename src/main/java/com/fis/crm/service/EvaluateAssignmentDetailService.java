package com.fis.crm.service;

import com.fis.crm.domain.EvaluateAssignmentDetail;
import com.fis.crm.service.dto.EvaluateAssignmentDetailDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link EvaluateAssignmentDetail}.
 */
public interface EvaluateAssignmentDetailService {

    /**
     * Save a evaluateAssignmentD.
     *
     * @param evaluateAssignmentDetailDTO the entity to save.
     * @return the persisted entity.
     */
    EvaluateAssignmentDetailDTO save(EvaluateAssignmentDetailDTO evaluateAssignmentDetailDTO);

    /**
     * Get all the evaluateAssignmentDS.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<EvaluateAssignmentDetailDTO> findAll(Pageable pageable);


    /**
     * Get the "id" evaluateAssignmentD.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<EvaluateAssignmentDetailDTO> findOne(Long id);

    /**
     * Delete the "id" evaluateAssignmentD.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    boolean updateTotalEvaluated(Long evaluateAssignmentDetailId);
}
