package com.fis.crm.service.impl;

import com.fis.crm.service.ProcessTicketEntityService;
import com.fis.crm.domain.ProcessTicketEntity;
import com.fis.crm.repository.ProcessTicketEntityRepository;
import com.fis.crm.service.dto.ProcessTicketEntityDTO;
import com.fis.crm.service.mapper.ProcessTicketEntityMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link ProcessTicketEntity}.
 */
@Service
@Transactional
public class ProcessTicketEntityServiceImpl implements ProcessTicketEntityService {

    private final Logger log = LoggerFactory.getLogger(ProcessTicketEntityServiceImpl.class);

    private final ProcessTicketEntityRepository processTicketEntityRepository;

    private final ProcessTicketEntityMapper processTicketEntityMapper;

    public ProcessTicketEntityServiceImpl(ProcessTicketEntityRepository processTicketEntityRepository, ProcessTicketEntityMapper processTicketEntityMapper) {
        this.processTicketEntityRepository = processTicketEntityRepository;
        this.processTicketEntityMapper = processTicketEntityMapper;
    }

    @Override
    public ProcessTicketEntityDTO save(ProcessTicketEntityDTO processTicketEntityDTO) {
        log.debug("Request to save ProcessTicketEntity : {}", processTicketEntityDTO);
        ProcessTicketEntity processTicketEntity = processTicketEntityMapper.toEntity(processTicketEntityDTO);
        processTicketEntity = processTicketEntityRepository.save(processTicketEntity);
        return processTicketEntityMapper.toDto(processTicketEntity);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ProcessTicketEntityDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ProcessTicketEntities");
        return processTicketEntityRepository.findAll(pageable)
            .map(processTicketEntityMapper::toDto);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<ProcessTicketEntityDTO> findOne(Long id) {
        log.debug("Request to get ProcessTicketEntity : {}", id);
        return processTicketEntityRepository.findById(id)
            .map(processTicketEntityMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete ProcessTicketEntity : {}", id);
        processTicketEntityRepository.deleteById(id);
    }
}
