package com.fis.crm.service;

import com.fis.crm.service.dto.EvaluateAssignmentDTO;
import com.fis.crm.service.dto.EvaluateAssignmentDataDTO;
import com.fis.crm.service.dto.EvaluateAssignmentSearchDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.fis.crm.domain.EvaluateAssignment}.
 */
public interface EvaluateAssignmentService {

    /**
     * Get all the evaluateAssignments.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<EvaluateAssignmentDTO> findAll(Pageable pageable);


    /**
     * Get the "id" evaluateAssignment.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<EvaluateAssignmentDTO> findOne(Long id);

    /**
     * Delete the "id" evaluateAssignment.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    Boolean createEvaluateAssignment(EvaluateAssignmentDTO evaluateAssignmentDTO) throws Exception;

    Boolean updateEvaluateAssignment(EvaluateAssignmentDTO evaluateAssignmentDTO) throws Exception;

    Page<EvaluateAssignmentDataDTO> getAllEvaluateAssignments(EvaluateAssignmentDTO evaluateAssignmentDTO, Pageable pageable) throws Exception;

    Page<EvaluateAssignmentSearchDTO> search(EvaluateAssignmentDTO evaluateAssignmentDTO, Pageable pageable) throws Exception;
}
