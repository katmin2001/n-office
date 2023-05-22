package com.fis.crm.crm_repository;

import com.fis.crm.crm_entity.CrmCandidate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CandidateRepo extends JpaRepository<CrmCandidate, Long> {

    @Query(value = "select distinct c.candidateid, c.manageid, c.isid, c.fullname,c.phone,c.birthday,c.address,c.status,c.create_date\n" +
        "from crm_candidate c , crm_interview i\n" +
        "where \n" +
        "(:startDayCreate is null or TO_DATE(:startDayCreate,'dd/mm/yyyy') <= c.create_date) and (:endDayCreate is null or TO_DATE(:endDayCreate, 'dd/mm/yyyy')>=c.create_date)\n" +
        "and (:startDay is null or i.candidateid = c.candidateid) and (:startDay is null or TO_DATE(:startDay,'dd/mm/yyyy') <= i.interview_date) and (:endDay is null or TO_DATE(:endDay, 'dd/mm/yyyy')>=i.interview_date)\n" +
        "and (:ISID is null or c.isid = TO_NUMBER(:ISID))\n" +
        "and (:manageId is null or c.manageid = TO_NUMBER(:manageId)) ",nativeQuery = true)
    List<CrmCandidate> searchCandidate(@Param("startDayCreate") String startDayCreate,
                                       @Param("endDayCreate") String endDayCreate,
                                       @Param("startDay") String startDay,
                                       @Param("endDay") String endDay,
                                       @Param("ISID") String ISID,
                                       @Param("manageId") String manageId);


}

