package com.fis.crm.crm_repository;

import com.fis.crm.crm_entity.CrmProject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CrmProjectRepo extends JpaRepository<CrmProject, Long> {
    @Query (value = "select p from CrmProject p where p.code = :code")
    public CrmProject findCrmProjectByCode(@Param("code") String code);
}
