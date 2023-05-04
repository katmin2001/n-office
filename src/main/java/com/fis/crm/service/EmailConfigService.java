package com.fis.crm.service;

import com.fis.crm.service.dto.EmailConfigDTO;

import java.util.List;
import java.util.Optional;

public interface EmailConfigService {

    List<EmailConfigDTO> getAll();

    Optional<EmailConfigDTO> findById(Long id);

    void save(EmailConfigDTO emailConfigDTO) throws Exception;

    void update(Long id, EmailConfigDTO emailConfigDTO) throws Exception;
}
