package com.fis.crm.repository;

import com.fis.crm.domain.EvaluateResultResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the EvaluateResultResponse entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EvaluateResultResponseRepository extends JpaRepository<EvaluateResultResponse, Long> {

    Page<EvaluateResultResponse> findAllByEvaluateResultId(Long evaluateResultId, Pageable pageable);
}
