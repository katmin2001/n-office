package com.fis.crm.repository;

import com.fis.crm.domain.CriteriaDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CriteriaDetailRepository extends JpaRepository<CriteriaDetail, Long> {

    List<CriteriaDetail> findByCriteriaIdAndStatus(Long criteriaId, String status);

    List<CriteriaDetail> findByCriteriaId(Long criteriaId);


    @Query(value = "select * from  criteria_detail where lower(content) = lower(:content)  and status = '1'", nativeQuery = true)
    CriteriaDetail findByContent(@Param("content") String content);

    Optional<List<CriteriaDetail>> findByCriteriaIdInAndStatus(List<Long> criteriaId, String status);

    @Query(value = "select  scores from  criteria where id = :id", nativeQuery = true)
    List<Object[]> getScores(@Param("id") Long id);

}
