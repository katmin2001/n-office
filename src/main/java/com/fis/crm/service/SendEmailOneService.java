package com.fis.crm.service;

import com.fis.crm.service.dto.SendEmailOneDTO;

import java.util.List;

public interface SendEmailOneService {
    List<SendEmailOneDTO> findAll();

    void save(SendEmailOneDTO sendEmailOneDTO);
}
