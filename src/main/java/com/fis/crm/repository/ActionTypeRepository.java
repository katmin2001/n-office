package com.fis.crm.repository;

import com.fis.crm.domain.ActionType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ActionTypeRepository extends JpaRepository<ActionType, Long> {
}
