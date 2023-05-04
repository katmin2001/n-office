package com.fis.crm.repository;

import com.fis.crm.domain.CriteriaRating;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CriteriaRatingRepository extends JpaRepository<CriteriaRating, Long> {

    @Query(value = "select * from criteria_rating where lower(name) like lower(:name) and status = '1'", nativeQuery = true)
    CriteriaRating findByName(@Param("name") String name);

    @Query(value = "select * from  criteria_rating where status = '1' order by FROM_SCORES asc", nativeQuery = true)
    List<CriteriaRating> findAll();

    @Query(value = "select * from criteria_rating where status = '1' order by FROM_SCORES desc", nativeQuery = true)
    Page<CriteriaRating> findAll(Pageable pageable);

    Optional<List<CriteriaRating>> findByStatusOrderByNameAsc(String status);

    @Query(value = "select *\n" +
        "from CRITERIA_RATING cr\n" +
        "where cr.STATUS = 1\n" +
        "  and (((cr.FROM_SCORES <= :fromScore) and (cr.TO_SCORES >= :fromScore)) or\n" +
        "       ((cr.FROM_SCORES > :toScore) and (cr.TO_SCORES < :toScore)))", nativeQuery = true)
    List<CriteriaRating> checkDuplicateValue(@Param("fromScore") Double fromScore, @Param("toScore") Double toScore);

}
