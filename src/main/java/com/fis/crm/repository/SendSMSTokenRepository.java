package com.fis.crm.repository;

import com.fis.crm.domain.SendSMSToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SendSMSTokenRepository extends JpaRepository<SendSMSToken, Long> {
}
