package com.fis.crm.service;

import com.fis.crm.service.dto.ContractorsSuspendDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.text.ParseException;

public interface ContractorsSuspendService {
    Page<ContractorsSuspendDTO> search(ContractorsSuspendDTO contractorsSuspendDTO,
                                       Pageable pageable) throws ParseException;
}
