package com.fis.crm.service.impl;

import com.fis.crm.config.Constants;
import com.fis.crm.service.BussinessTypeService;
import com.fis.crm.domain.BussinessType;
import com.fis.crm.repository.BussinessTypeRepository;
import com.fis.crm.service.dto.BussinessTypeDTO;
import com.fis.crm.service.mapper.BussinessTypeMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link BussinessType}.
 */
@Service
@Transactional
public class BussinessTypeServiceImpl implements BussinessTypeService {

    private final Logger log = LoggerFactory.getLogger(BussinessTypeServiceImpl.class);

    private final BussinessTypeRepository bussinessTypeRepository;

    private final BussinessTypeMapper bussinessTypeMapper;

    public BussinessTypeServiceImpl(BussinessTypeRepository bussinessTypeRepository, BussinessTypeMapper bussinessTypeMapper) {
        this.bussinessTypeRepository = bussinessTypeRepository;
        this.bussinessTypeMapper = bussinessTypeMapper;
    }

    @Override
    public BussinessTypeDTO save(BussinessTypeDTO bussinessTypeDTO) {
        log.debug("Request to save BussinessType : {}", bussinessTypeDTO);
        BussinessType bussinessType = bussinessTypeMapper.toEntity(bussinessTypeDTO);
        bussinessType = bussinessTypeRepository.save(bussinessType);
        return bussinessTypeMapper.toDto(bussinessType);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<BussinessTypeDTO> findAll(Pageable pageable) {
        log.debug("Request to get all BussinessTypes");
        return bussinessTypeRepository.findAllByStatus(Constants.STATUS.ACTIVE, pageable)
            .map(bussinessTypeMapper::toDto);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<BussinessTypeDTO> findOne(Long id) {
        log.debug("Request to get BussinessType : {}", id);
        return bussinessTypeRepository.findById(id)
            .map(bussinessTypeMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete BussinessType : {}", id);
        bussinessTypeRepository.deleteById(id);
    }
}
