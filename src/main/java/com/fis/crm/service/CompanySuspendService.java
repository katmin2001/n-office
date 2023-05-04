package com.fis.crm.service;

import com.fis.crm.service.dto.CompanySuspendDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.text.ParseException;

public interface CompanySuspendService {

    Page<CompanySuspendDTO> search(CompanySuspendDTO companySuspendDTO,
                                   Pageable pageable) throws ParseException;
}
