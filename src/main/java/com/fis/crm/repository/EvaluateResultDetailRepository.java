package com.fis.crm.repository;

import com.fis.crm.domain.EvaluateResultDetail;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data  repository for the EvaluateResultDetai1 entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EvaluateResultDetailRepository extends JpaRepository<EvaluateResultDetail, Long> {

    @Query(value = "WITH tmp AS (select count(*) as total from EVALUATE_RESULT where EVALUATE_STATUS in (1, 2) and STATUS = '1') " +
        "select cd.CONTENT, " +
        "       total_error || '/' || tmp.total, " +
        "       round(erd.total_error * 100 / tmp.total, 2) " +
        "from CRITERIA_GROUP cg, " +
        "     CRITERIA_DETAIL cd, " +
        "     (select erd.CRITERIA_DETAIL_ID, count(*) as total_error " +
        "      from EVALUATE_RESULT_DETAIL erd, EVALUATE_RESULT er " +
        "      where er.ID = erd.EVALUATE_RESULT_ID " +
        "        and er.EVALUATE_STATUS in (1, 2) " +
        "        and er.STATUS = '1' " +
        "        and (:START_DATE is null or nvl(er.CREATE_DATETIME_NEW, er.CREATE_DATETIME ) >= TO_DATE(:START_DATE, 'dd/MM/yyyy')) " +
        "        and (:END_DATE is null or nvl(er.CREATE_DATETIME_NEW, er.CREATE_DATETIME ) < TO_DATE(:END_DATE, 'dd/MM/yyyy') + 1) " +
        "      group by erd.CRITERIA_DETAIL_ID) erd, " +
        "    tmp " +
        "where erd.CRITERIA_DETAIL_ID = cd.ID " +
        "  and cd.CRITERIA_GROUP_ID = cg.ID " +
        "  and (:CRITERIA_GROUP_ID = -1 or cg.ID = :CRITERIA_GROUP_ID) ",
        countQuery = "select count(*) " +
            "     from CRITERIA_GROUP cg,   " +
            "          CRITERIA_DETAIL cd,   " +
            "          (select erd.CRITERIA_DETAIL_ID, count(*) as total_error   " +
            "           from EVALUATE_RESULT_DETAIL erd, EVALUATE_RESULT er   " +
            "           where er.ID = erd.EVALUATE_RESULT_ID   " +
            "             and er.EVALUATE_STATUS in (1, 2)   " +
            "             and er.STATUS = '1'   " +
            "             and (:START_DATE is null or nvl(er.CREATE_DATETIME_NEW, er.CREATE_DATETIME ) >= TO_DATE(:START_DATE, 'dd/MM/yyyy')) " +
            "             and (:END_DATE is null or nvl(er.CREATE_DATETIME_NEW, er.CREATE_DATETIME ) < TO_DATE(:END_DATE, 'dd/MM/yyyy') + 1) " +
            "           group by erd.CRITERIA_DETAIL_ID) erd " +
            "     where erd.CRITERIA_DETAIL_ID = cd.ID   " +
            "       and cd.CRITERIA_GROUP_ID = cg.ID   " +
            "       and (:CRITERIA_GROUP_ID = -1 or cg.ID = :CRITERIA_GROUP_ID) ",
        nativeQuery = true)
    Page<Object[]> getCriteriaDetailCall(@Param("CRITERIA_GROUP_ID") Long criteriaRatingId,
                                 @Param("START_DATE") String startDate,
                                 @Param("END_DATE") String endDate, Pageable pageable);

    @Query(value = "WITH tmp AS (select count(*) as total from EVALUATE_RESULT where EVALUATE_STATUS in (1, 2) and STATUS = '1') " +
        "select cd.CONTENT, " +
        "      total_error || '/' ||tmp.total, " +
        "       round(erd.total_error * 100 / tmp.total, 2) || '%' " +
        "from CRITERIA_GROUP cg, " +
        "     CRITERIA_DETAIL cd, " +
        "     (select erd.CRITERIA_DETAIL_ID, count(*) as total_error " +
        "      from EVALUATE_RESULT_DETAIL erd, EVALUATE_RESULT er " +
        "      where er.ID = erd.EVALUATE_RESULT_ID " +
        "        and er.EVALUATE_STATUS in (1, 2) " +
        "        and er.STATUS = '1' " +
        "        and (:START_DATE is null or nvl(er.CREATE_DATETIME_NEW, er.CREATE_DATETIME ) >= TO_DATE(:START_DATE, 'dd/MM/yyyy')) " +
        "        and (:END_DATE is null or nvl(er.CREATE_DATETIME_NEW, er.CREATE_DATETIME ) < TO_DATE(:END_DATE, 'dd/MM/yyyy') + 1) " +
        "      group by erd.CRITERIA_DETAIL_ID) erd, " +
        "    tmp " +
        "where erd.CRITERIA_DETAIL_ID = cd.ID " +
        "  and cd.CRITERIA_GROUP_ID = cg.ID " +
        "  and cg.ID = :CRITERIA_GROUP_ID",
        nativeQuery = true)
    List<Object[]> getAllCriteriaDetailCall(@Param("CRITERIA_GROUP_ID") Long criteriaRatingId,
                                            @Param("START_DATE") String startDate,
                                            @Param("END_DATE") String endDate);
}
