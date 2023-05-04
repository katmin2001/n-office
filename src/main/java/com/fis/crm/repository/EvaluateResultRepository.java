package com.fis.crm.repository;

import com.fis.crm.domain.EvaluateResult;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the EvaluateResult entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EvaluateResultRepository extends JpaRepository<EvaluateResult, Long> {

    Optional<EvaluateResult> findByIdAndEvaluaterIdAndEvaluateStatusAndStatus(Long id, Long evaluaterId, String evaluateStatus, String status);

    Optional<EvaluateResult> findByIdAndEvaluaterIdAndEvaluateStatusInAndStatus(Long id, Long evaluaterId, List<String> evaluateStatus, String status);

    @Query("select er from EvaluateResult er where er.id = :id and (er.evaluaterId = :evaluaterId or er.evaluatedId = :evaluatedId) and er.evaluateStatus in :evaluateStatus and er.status = :status")
    Optional<EvaluateResult> findByIdAndEvaluaterIdOrEvaluatedIdAndEvaluateStatusInAndStatus(@Param("id") Long id, @Param("evaluaterId") Long evaluaterId,
                                                                                             @Param("evaluatedId") Long evaluatedId,
                                                                                             @Param("evaluateStatus") List<String> evaluateStatus,
                                                                                             @Param("status") String status);

    @Query(value = "select er.ID," +
        "       er.CHANNEL_TYPE," +
        "       er.PHONE_NUMBER," +
        "       er.EVALUATER_ID," +
        "       (select FIRST_NAME from JHI_USER ju where ju.ID = er.EVALUATER_ID) as EVALUATER_NAME," +
        "       er.CREATE_DATETIME," +
        "       er.EVALUATED_ID," +
        "       (select FIRST_NAME from JHI_USER ju where ju.ID = er.EVALUATED_ID) as EVALUATED_NAME," +
        "       er.CONTENT," +
        "       er.SUGGEST," +
        "       er.TOTAL_SCORES," +
        "       er.TOTAL_SCORES_NEW," +
        "       er.CRITERIA_RATING_ID," +
        "       (select )cr.NAME                                                                                as CRITERIA_RATING_NAME," +
        "       er.ERROR1," +
        "       (select osv.NAME from OPTION_SET_VALUE osv where osv.CODE = er.ERROR1)                 as ERROR1_NAME," +
        "       er.ERROR2," +
        "       (select osv.NAME from OPTION_SET_VALUE osv where osv.CODE = er.ERROR2)                 as ERROR2_NAME, " +
        "       er.CRITERIA_RATING_NEW_ID " +
        "from EVALUATE_RESULT er" +
        "where 1=1" +
        "  and cr.status = '1'" +
        "  and (:CHANNEL_TYPE = '-1' or er.CHANNEL_TYPE = :CHANNEL_TYPE)" +
        "  and (:EVALUATER_ID = -1 or er.EVALUATER_ID = :EVALUATER_ID)" +
        "  and (:EVALUATED_ID = -1 or er.EVALUATED_ID = :EVALUATED_ID)" +
        "  and (:CRITERIA_RATING_ID = -1 or er.CRITERIA_RATING_ID = :CRITERIA_RATING_ID)" +
        "  and (:PHONE_NUMBER is null or lower(er.PHONE_NUMBER) like :PHONE_NUMBER)" +
        "  and (:START_DATE is null or er.CREATE_DATETIME >= TO_DATE(:START_DATE, 'dd/MM/yyyy'))" +
        "  and (:END_DATE is null or er.CREATE_DATETIME < TO_DATE(:END_DATE, 'dd/MM/yyyy') + 1) " +
        "ORDER BY CREATE_DATETIME desc",
        countQuery = "select count(*) " +
            "from EVALUATE_RESULT er  " +
            "where 1=1" +
            "  and (:CHANNEL_TYPE = '-1' or er.CHANNEL_TYPE = :CHANNEL_TYPE)" +
            "  and (:EVALUATER_ID = -1 or er.EVALUATER_ID = :EVALUATER_ID)" +
            "  and (:EVALUATED_ID = -1 or er.EVALUATED_ID = :EVALUATED_ID)" +
            "  and (:CRITERIA_RATING_ID = -1 or er.CRITERIA_RATING_ID = :CRITERIA_RATING_ID)" +
            "  and (:PHONE_NUMBER is null or lower(er.PHONE_NUMBER) like :PHONE_NUMBER)" +
            "  and (:START_DATE is null or er.CREATE_DATETIME >= TO_DATE(:START_DATE, 'dd/MM/yyyy'))" +
            "  and (:END_DATE is null or er.CREATE_DATETIME < TO_DATE(:END_DATE, 'dd/MM/yyyy') + 1)",
        nativeQuery = true)
    Page<Object[]> getEvaluateResult(@Param("CHANNEL_TYPE") String channelId,
                                             @Param("EVALUATER_ID") Long evaluaterId,
                                             @Param("EVALUATED_ID") Long evaluatedId,
                                             @Param("CRITERIA_RATING_ID") Long criteriaRatingId,
                                             @Param("PHONE_NUMBER") String phoneNumber,
                                             @Param("START_DATE") String startDate,
                                             @Param("END_DATE") String endDate, Pageable pageable);

    @Query(value = "select er.ID," +
        "       er.CHANNEL_TYPE," +
        "       er.PHONE_NUMBER," +
        "       er.EVALUATER_ID," +
        "       (select FIRST_NAME from JHI_USER ju where ju.ID = er.EVALUATER_ID) as EVALUATER_NAME," +
        "       er.CREATE_DATETIME," +
        "       er.EVALUATED_ID," +
        "       (select FIRST_NAME from JHI_USER ju where ju.ID = er.EVALUATED_ID) as EVALUATED_NAME," +
        "       er.CONTENT," +
        "       er.SUGGEST," +
        "       er.TOTAL_SCORES," +
        "       er.TOTAL_SCORES_NEW," +
        "       er.CRITERIA_RATING_ID," +
        "       cr.NAME                                                                                as CRITERIA_RATING_NAME," +
        "       er.ERROR1," +
        "       (select osv.NAME from OPTION_SET_VALUE osv where osv.CODE = er.ERROR1)                 as ERROR1_NAME," +
        "       er.ERROR2," +
        "       (select osv.NAME from OPTION_SET_VALUE osv where osv.CODE = er.ERROR2)                 as ERROR2_NAME " +
        "from EVALUATE_RESULT er, " +
        "     CRITERIA_RATING cr " +
        "where 1=1" +
        "  and cr.ID = er.CRITERIA_RATING_ID" +
        "  and cr.status = '1'" +
        "  and (:CHANNEL_TYPE = '-1' or er.CHANNEL_TYPE = :CHANNEL_TYPE)" +
        "  and (:EVALUATER_ID = -1 or er.EVALUATER_ID = :EVALUATER_ID)" +
        "  and (:EVALUATED_ID = -1 or er.EVALUATED_ID = :EVALUATED_ID)" +
        "  and (:CRITERIA_RATING_ID = -1 or er.CRITERIA_RATING_ID = :CRITERIA_RATING_ID)" +
        "  and (:PHONE_NUMBER is null or lower(er.PHONE_NUMBER) like :PHONE_NUMBER)" +
        "  and (:START_DATE is null or er.CREATE_DATETIME >= TO_DATE(:START_DATE, 'dd/MM/yyyy'))" +
        "  and (:END_DATE is null or er.CREATE_DATETIME < TO_DATE(:END_DATE, 'dd/MM/yyyy') + 1) " +
        "ORDER BY CREATE_DATETIME desc",
        nativeQuery = true)
    List<Object[]> getAllEvaluateResult(@Param("CHANNEL_TYPE") String channelId,
                                        @Param("EVALUATER_ID") Long evaluaterId,
                                        @Param("EVALUATED_ID") Long evaluatedId,
                                        @Param("CRITERIA_RATING_ID") Long criteriaRatingId,
                                        @Param("PHONE_NUMBER") String phoneNumber,
                                        @Param("START_DATE") String startDate,
                                        @Param("END_DATE") String endDate);



    @Query(value = "WITH tmp AS ( " +
        "    select count(*) as total from EVALUATE_RESULT where EVALUATE_STATUS in (1, 2) " +
        "  and STATUS = '1' " +
        "  and ( :CRITERIA_RATING_ID = -1 or nvl(CRITERIA_RATING_NEW_ID, CRITERIA_RATING_ID) = :CRITERIA_RATING_ID) " +
        "  and (:START_DATE is null or CALL_DATETIME >= TO_DATE(:START_DATE, 'dd/MM/yyyy')) " +
        "  and (:END_DATE is null or CALL_DATETIME < TO_DATE(:END_DATE, 'dd/MM/yyyy') + 1) " +
        ") " +
        "select nvl(CRITERIA_RATING_NEW_ID, CRITERIA_RATING_ID)                 as ID, " +
        "       (select NAME " +
        "        from CRITERIA_RATING cr " +
        "        where cr.ID = nvl(CRITERIA_RATING_NEW_ID, CRITERIA_RATING_ID)) as CRITERIA_RATING_NAME, " +
        "       count(*)                                                        as times, " +
        "       tmp.total                                                        as totalCalls, " +
        "       ROUND(count(*) * 100 / tmp.total, 2)                            as rate " +
        "from EVALUATE_RESULT, " +
        "     tmp " +
        "where EVALUATE_STATUS in (1, 2) " +
        "  and STATUS = '1' " +
        "  and ( :CRITERIA_RATING_ID = -1 or nvl(CRITERIA_RATING_NEW_ID, CRITERIA_RATING_ID) = :CRITERIA_RATING_ID) " +
        "  and (:START_DATE is null or CALL_DATETIME >= TO_DATE(:START_DATE, 'dd/MM/yyyy')) " +
        "  and (:END_DATE is null or CALL_DATETIME < TO_DATE(:END_DATE, 'dd/MM/yyyy') + 1) " +
        "group by nvl(CRITERIA_RATING_NEW_ID, CRITERIA_RATING_ID) , tmp.total order by nvl(CRITERIA_RATING_NEW_ID, CRITERIA_RATING_ID)",
        countQuery = "select count(*) from (select 1 " +
            "from EVALUATE_RESULT " +
            "where EVALUATE_STATUS in (1, 2) " +
            "  and STATUS = '1' " +
            "  and ( :CRITERIA_RATING_ID = -1 or nvl(CRITERIA_RATING_NEW_ID, CRITERIA_RATING_ID) = :CRITERIA_RATING_ID) " +
            "  and (:START_DATE is null or CALL_DATETIME >= TO_DATE(:START_DATE, 'dd/MM/yyyy')) " +
            "  and (:END_DATE is null or CALL_DATETIME < TO_DATE(:END_DATE, 'dd/MM/yyyy') + 1) " +
            "group by nvl(CRITERIA_RATING_NEW_ID, CRITERIA_RATING_ID));",
        nativeQuery = true)
    Page<Object[]> getRatingCall(@Param("CRITERIA_RATING_ID") Long criteriaRatingId,
                                     @Param("START_DATE") String startDate,
                                     @Param("END_DATE") String endDate, Pageable pageable);


    @Query(value = "WITH tmp AS ( " +
        "    select count(*) as total from EVALUATE_RESULT where EVALUATE_STATUS in (1, 2) " +
        "  and STATUS = '1' " +
        "  and ( :CRITERIA_RATING_ID = -1 or nvl(CRITERIA_RATING_NEW_ID, CRITERIA_RATING_ID) = :CRITERIA_RATING_ID) " +
        "  and (:START_DATE is null or CALL_DATETIME >= TO_DATE(:START_DATE, 'dd/MM/yyyy')) " +
        "  and (:END_DATE is null or CALL_DATETIME < TO_DATE(:END_DATE, 'dd/MM/yyyy') + 1) " +
        ") " +
        "select nvl(CRITERIA_RATING_NEW_ID, CRITERIA_RATING_ID)                 as ID, " +
        "       (select NAME " +
        "        from CRITERIA_RATING cr " +
        "        where cr.ID = nvl(CRITERIA_RATING_NEW_ID, CRITERIA_RATING_ID)) as CRITERIA_RATING_NAME, " +
        "       count(*)                                                        as times, " +
        "       tmp.total                                                        as totalCalls, " +
        "       ROUND(count(*) * 100 / tmp.total, 2)                            as rate " +
        "from EVALUATE_RESULT, " +
        "     tmp " +
        "where EVALUATE_STATUS in (1, 2) " +
        "  and STATUS = '1' " +
        "  and ( :CRITERIA_RATING_ID = -1 or nvl(CRITERIA_RATING_NEW_ID, CRITERIA_RATING_ID) = :CRITERIA_RATING_ID) " +
        "  and (:START_DATE is null or CALL_DATETIME >= TO_DATE(:START_DATE, 'dd/MM/yyyy')) " +
        "  and (:END_DATE is null or CALL_DATETIME < TO_DATE(:END_DATE, 'dd/MM/yyyy') + 1) " +
        "group by nvl(CRITERIA_RATING_NEW_ID, CRITERIA_RATING_ID) , tmp.total order by nvl(CRITERIA_RATING_NEW_ID, CRITERIA_RATING_ID)",
        nativeQuery = true)
    List<Object[]> getAllRatingCall(@Param("CRITERIA_RATING_ID") Long criteriaRatingId,
                                 @Param("START_DATE") String startDate,
                                 @Param("END_DATE") String endDate);
}
