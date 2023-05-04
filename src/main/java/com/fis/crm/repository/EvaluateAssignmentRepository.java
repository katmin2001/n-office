package com.fis.crm.repository;

import com.fis.crm.domain.EvaluateAssignment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the EvaluateAssignment entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EvaluateAssignmentRepository extends JpaRepository<EvaluateAssignment, Long> {

    @Query(value = "select id," +
        "       CHANNEL_ID," +
        "       EVALUATER_ID," +
        "       BUSSINESS_TYPE_ID," +
        "       TOTAL_USER_ID," +
        "       (select ju.FIRST_NAME from JHI_USER ju where ju.ID = EVALUATER_ID) as name," +
        "       LISTAGG(fullName, '; ') WITHIN GROUP (ORDER BY id) as fullName," +
        "       TOTAL_CALL, " +
        "       CREATE_DATETIME, " +
        "       START_DATE, " +
        "       END_DATE, " +
        "       CREATE_USER, " +
        "       (select ju.FIRST_NAME from JHI_USER ju where ju.ID = CREATE_USER) as createName " +
        "From " +
        "(select ea.ID, ea.CHANNEL_ID, ea.EVALUATER_ID, ea.BUSSINESS_TYPE_ID, ea.TOTAL_CALL, " +
        "       ea.TOTAL_USER_ID, " +
        "       us.FIRST_NAME as fullName, " +
        "       ea.CREATE_DATETIME, " +
        "       ea.START_DATE, " +
        "       ea.END_DATE, " +
        "       ea.CREATE_USER " +
        "    from EVALUATE_ASSIGNMENT ea, " +
        "         EVALUATE_ASSIGNMENT_DETAIL ead, " +
        "         JHI_USER us " +
        "    where ea.ID = ead.EVALUATE_ASSIGNMENT_ID and ead.USER_ID = us.ID " +
        "    and (:CHANNEL_ID = '-1' or ea.CHANNEL_ID = :CHANNEL_ID)" +
        "    and (:EVALUATER_ID = -1 or ea.EVALUATER_ID = :EVALUATER_ID)" +
        "    and (:BUSSINESS_TYPE_ID = -1 or ea.BUSSINESS_TYPE_ID = :BUSSINESS_TYPE_ID)" +
        "    and (:USER_ID = -1 or ead.USER_ID = :USER_ID)" +
        "    and (:START_DATE is null or ea.CREATE_DATETIME >= TO_DATE(:START_DATE, 'dd/MM/yyyy'))" +
        "    and (:END_DATE is null or ea.CREATE_DATETIME < TO_DATE(:END_DATE, 'dd/MM/yyyy') + 1)" +
        " ) " +
        "GROUP BY id , CHANNEL_ID, EVALUATER_ID, BUSSINESS_TYPE_ID, TOTAL_CALL, " +
        "         CREATE_DATETIME, START_DATE, END_DATE, CREATE_USER, TOTAL_USER_ID  " +
        "ORDER BY CREATE_DATETIME desc ",
        countQuery = "select count(*) from (select ea.id " +
            "     from EVALUATE_ASSIGNMENT ea, " +
            "          EVALUATE_ASSIGNMENT_DETAIL ead, " +
            "          JHI_USER us " +
            "     where ea.ID = ead.EVALUATE_ASSIGNMENT_ID" +
            "       and ead.USER_ID = us.ID" +
            "    and (:CHANNEL_ID = '-1' or ea.CHANNEL_ID = :CHANNEL_ID)" +
            "    and (:EVALUATER_ID = -1 or ea.EVALUATER_ID = :EVALUATER_ID)" +
            "    and (:BUSSINESS_TYPE_ID = -1 or ea.BUSSINESS_TYPE_ID = :BUSSINESS_TYPE_ID)" +
            "    and (:USER_ID = -1 or ead.USER_ID = :USER_ID)" +
            "    and (:START_DATE is null or ea.CREATE_DATETIME >= TO_DATE(:START_DATE, 'dd/MM/yyyy'))" +
            "    and (:END_DATE is null or ea.CREATE_DATETIME < TO_DATE(:END_DATE, 'dd/MM/yyyy')+1) " +
            "GROUP BY ea.id)"
        , nativeQuery = true)
    Page<Object[]> getAllEvaluateAssignments( @Param("CHANNEL_ID") String channelId,
                                              @Param("EVALUATER_ID") Long evaluaterId,
                                              @Param("BUSSINESS_TYPE_ID") Long businessTypeId,
                                              @Param("USER_ID") Long userId,
                                              @Param("START_DATE") String startDate,
                                              @Param("END_DATE") String endDate,
                                              Pageable pageable);


    @Query(value = "select id," +
        "       CHANNEL_ID," +
        "       EVALUATER_ID," +
        "       IDS," +
        "       TOTAL_USER_ID," +
        "       (select ju.FIRST_NAME from JHI_USER ju where ju.ID = EVALUATER_ID) as name," +
        "       (fullName) as fullName," +
        "       TOTAL_EVALUATED," +
        "       EVALUATE_STATUS, " +
        "       TOTAL_CALL, " +
        "       CREATE_DATETIME, " +
        "       START_DATE, " +
        "       END_DATE, " +
        "       CREATE_USER, " +
        "       (select ju.FIRST_NAME from JHI_USER ju where ju.ID = CREATE_USER) as createName " +
        "From " +
        "(select ea.ID, ea.CHANNEL_ID, ea.EVALUATER_ID, ea.TOTAL_CALL, " +
        "       ea.TOTAL_USER_ID, " +
        "       ead.TOTAL_EVALUATED, " +
        "       ead.ID as IDS, " +
        "       ea.EVALUATE_STATUS, " +
        "       us.FIRST_NAME as fullName, " +
        "       ea.CREATE_DATETIME, " +
        "       ea.START_DATE, " +
        "       ea.END_DATE, " +
        "       ea.CREATE_USER " +
        "    from EVALUATE_ASSIGNMENT ea, " +
        "         EVALUATE_ASSIGNMENT_DETAIL ead, " +
        "         JHI_USER us " +
        "    where ea.ID = ead.EVALUATE_ASSIGNMENT_ID and ead.USER_ID = us.ID " +
        "    and (:CHANNEL_ID = '-1' or ea.CHANNEL_ID = :CHANNEL_ID)" +
        "    and (:EVALUATER_ID = -1 or ea.EVALUATER_ID = :EVALUATER_ID)" +
        "    and (:USER_ID = -1 or ead.USER_ID = :USER_ID)" +
        "    and (ea.CREATE_USER = :CREATE_USER_ID)" +
        "    and (:EVALUATE_STATUS is null or ea.EVALUATE_STATUS = :EVALUATE_STATUS)" +
        "    and (:START_DATE is null or ea.CREATE_DATETIME >= TO_DATE(:START_DATE, 'dd/MM/yyyy'))" +
        "    and (:END_DATE is null or ea.CREATE_DATETIME < TO_DATE(:END_DATE, 'dd/MM/yyyy') + 1)" +
        " ) " +
        "GROUP BY id , CHANNEL_ID, EVALUATER_ID, TOTAL_CALL, " +
        "         CREATE_DATETIME, START_DATE, END_DATE, CREATE_USER, TOTAL_USER_ID, EVALUATE_STATUS, fullName, TOTAL_EVALUATED,IDS " +
        " ORDER BY CREATE_DATETIME desc ",
        countQuery = "select count(*) from (select ea.id " +
            "     from EVALUATE_ASSIGNMENT ea, " +
            "          EVALUATE_ASSIGNMENT_DETAIL ead, " +
            "          JHI_USER us " +
            "     where ea.ID = ead.EVALUATE_ASSIGNMENT_ID" +
            "       and ead.USER_ID = us.ID" +
            "    and (:CHANNEL_ID = '-1' or ea.CHANNEL_ID = :CHANNEL_ID)" +
            "    and (:EVALUATER_ID = -1 or ea.EVALUATER_ID = :EVALUATER_ID)" +
            "    and (:USER_ID = -1 or ead.USER_ID = :USER_ID)" +
            "    and (ea.CREATE_USER = :CREATE_USER_ID)" +
            "    and (:EVALUATE_STATUS is null or ea.EVALUATE_STATUS = :EVALUATE_STATUS)" +
            "    and (:START_DATE is null or ea.CREATE_DATETIME >= TO_DATE(:START_DATE, 'dd/MM/yyyy'))" +
            "    and (:END_DATE is null or ea.CREATE_DATETIME < TO_DATE(:END_DATE, 'dd/MM/yyyy')+1) " +
            "GROUP BY ea.id)"
        , nativeQuery = true)
    Page<Object[]> search( @Param("CHANNEL_ID") String channelId,
                           @Param("EVALUATER_ID") Long evaluaterId,
                           @Param("USER_ID") Long userId,
                           @Param("CREATE_USER_ID") Long createUserId,
                           @Param("EVALUATE_STATUS") String evaluateStatus,
                           @Param("START_DATE") String startDate,
                           @Param("END_DATE") String endDate, Pageable pageable);
}
