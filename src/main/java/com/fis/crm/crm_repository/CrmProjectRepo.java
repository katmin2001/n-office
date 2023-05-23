package com.fis.crm.crm_repository;

import com.fis.crm.crm_entity.CrmProject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface CrmProjectRepo extends JpaRepository<CrmProject, Long> {
    @Query(value = "select p from CrmProject p where p.code = :code")
    public CrmProject findCrmProjectByCode(@Param("code") String code);

//    List<CrmProject> searchProjects(@Param("id") Long id,
//                                    @Param("name") String name,
//                                    @Param("code") String code,
//                                    @Param("customerId") Long customerId,
//                                    @Param("managerId") Long managerId,
//                                    @Param("privacyId") Long privacyId,
//                                    @Param("statusId") Long statusId,
//                                    @Param("description") String description,
//                                    @Param("startDate") Date startDate,
//                                    @Param("endDate") Date endDate,
//                                    @Param("finishDate") Date finishDate);
}
