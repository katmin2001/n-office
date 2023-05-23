package com.fis.crm.crm_repository;

import com.fis.crm.crm_entity.CrmProjectPrivacy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CrmProjectPrivacyRepo extends JpaRepository<CrmProjectPrivacy, Byte> {
}
