package com.fis.crm.service;

import com.fis.crm.service.dto.ConfigAutoEmailDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ConfigAutoEmailService {

    Page<ConfigAutoEmailDTO> findAllByCreateDateDesc(Pageable pageable);

    ConfigAutoEmailDTO save(ConfigAutoEmailDTO configAutoEmailDTO);

    ConfigAutoEmailDTO delete(Long id);

}
