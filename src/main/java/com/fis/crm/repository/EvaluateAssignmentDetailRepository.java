package com.fis.crm.repository;

import com.fis.crm.domain.EvaluateAssignmentDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the EvaluateAssignmentD entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EvaluateAssignmentDetailRepository extends JpaRepository<EvaluateAssignmentDetail, Long> {

    Optional<List<EvaluateAssignmentDetail>> findByEvaluateAssignmentId(Long evaluateAssignmentId);

    @Modifying
    @Query(value = "update EVALUATE_ASSIGNMENT_DETAIL a set a.TOTAL_EVALUATED = nvl(a.TOTAL_EVALUATED, 0) + 1 where a.id = to_number(:id)", nativeQuery = true)
    int updateTotalEvaluated(@Param("id") Long id);
}
