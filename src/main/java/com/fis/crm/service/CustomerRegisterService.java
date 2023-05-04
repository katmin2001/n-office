package com.fis.crm.service;

import com.fis.crm.service.dto.CustomerRegisterDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CustomerRegisterService {

    Page<CustomerRegisterDTO> search(CustomerRegisterDTO customerRegisterDTO,
                                     Pageable pageable);
}
