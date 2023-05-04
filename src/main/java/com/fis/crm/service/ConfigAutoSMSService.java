package com.fis.crm.service;

import com.fis.crm.service.dto.ConfigAutoSMSDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ConfigAutoSMSService {

    Page<ConfigAutoSMSDTO> findAllByCreateDateDesc(Pageable pageable);

    ConfigAutoSMSDTO save(ConfigAutoSMSDTO configAutoSMSDTO);

    ConfigAutoSMSDTO delete(Long id);

}
