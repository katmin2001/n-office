package com.fis.crm.service.impl;

import com.fis.crm.config.Constants;
import com.fis.crm.service.ApDomainService;
import com.fis.crm.domain.ApDomain;
import com.fis.crm.repository.ApDomainRepository;
import com.fis.crm.service.dto.ApDomainDTO;
import com.fis.crm.service.mapper.ApDomainMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link ApDomain}.
 */
@Service
@Transactional
public class ApDomainServiceImpl implements ApDomainService {

    private final Logger log = LoggerFactory.getLogger(ApDomainServiceImpl.class);

    private final ApDomainRepository apDomainRepository;

    private final ApDomainMapper apDomainMapper;

    public ApDomainServiceImpl(ApDomainRepository apDomainRepository, ApDomainMapper apDomainMapper) {
        this.apDomainRepository = apDomainRepository;
        this.apDomainMapper = apDomainMapper;
    }

    @Override
    public ApDomainDTO save(ApDomainDTO apDomainDTO) {
        log.debug("Request to save ApDomain : {}", apDomainDTO);
        ApDomain apDomain = apDomainMapper.toEntity(apDomainDTO);
        apDomain = apDomainRepository.save(apDomain);
        return apDomainMapper.toDto(apDomain);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ApDomainDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ApDomains");
        return apDomainRepository.findAll(pageable)
            .map(apDomainMapper::toDto);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<ApDomainDTO> findOne(Long id) {
        log.debug("Request to get ApDomain : {}", id);
        return apDomainRepository.findById(id)
            .map(apDomainMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete ApDomain : {}", id);
        apDomainRepository.deleteById(id);
    }

    public List<ApDomainDTO> findAllByType(String type) {
        return apDomainRepository.findAllByTypeAndStatus(type, Constants.STATUS_ACTIVE_STR).stream().map(apDomainMapper::toDto).collect(Collectors.toList());
    }
}
