package com.fis.crm.repository;

import com.fis.crm.domain.EvaluateResultHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EvaluateResultHistoryRepository extends JpaRepository<EvaluateResultHistory, Long> {

    List<EvaluateResultHistory> findByEvaluateResultId(Long evaluateId);

}
