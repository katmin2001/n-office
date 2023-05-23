package com.fis.crm.crm_repository;

import com.fis.crm.crm_entity.CrmProjectMember;
import com.fis.crm.crm_entity.CrmProjectStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CrmProjectStatusRepo extends JpaRepository<CrmProjectStatus, Byte> {
}
