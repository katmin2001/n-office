package com.fis.crm.crm_repository;

import com.fis.crm.crm_entity.CrmInterview;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InterviewRepo extends JpaRepository<CrmInterview,Long> {
    @Query(value = "select distinct i.interviewid, i.candidateid, i.isid,i.interview_date, i.status, i.create_date\n" +
        "from crm_interview i , crm_interview_detail d\n" +
        "where \n" +
        "(:startDay is null or TO_DATE(:startDay,'dd/mm/yyyy') <= i.interview_date) and (:endDay is null or TO_DATE(:endDay, 'dd/mm/yyyy')>=i.interview_date) \n" +
        "and (:ISID is null or i.isid = TO_NUMBER(:ISID)) \n" +
        "and (:interviewer is null or i.interviewid = d.interviewid and d.userid = TO_NUMBER(:interviewer))",nativeQuery = true)
    List<CrmInterview> searchInterview(@Param("startDay") String startDay,
                                       @Param("endDay") String endDay,
                                       @Param("ISID") String ISID,
                                       @Param("interviewer") String interviewer);
}
