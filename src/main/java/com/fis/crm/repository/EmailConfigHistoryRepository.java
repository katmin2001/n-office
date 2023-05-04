package com.fis.crm.repository;

import com.fis.crm.domain.EmailConfigHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmailConfigHistoryRepository extends JpaRepository<EmailConfigHistory, Long> {
}
