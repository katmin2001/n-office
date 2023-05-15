package com.fis.crm.crm_repository;

import com.fis.crm.crm_entity.CrmUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IUserRepo extends JpaRepository<CrmUser, Long> {
}
