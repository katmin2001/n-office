package com.fis.crm.service;

import com.fis.crm.service.dto.EmailConfigHistoryDTO;

import java.util.List;

public interface EmailConfigHistoryService {
    List<EmailConfigHistoryDTO> findAll();
}
