package com.fis.crm.repository;

import com.fis.crm.domain.CallInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CallInfoRepository extends JpaRepository<CallInfo, Long> {
}
