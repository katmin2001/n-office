package com.fis.crm.service.impl;

import com.fis.crm.commons.Translator;
import com.fis.crm.service.EvaluateAssignmentDetailService;
import com.fis.crm.domain.EvaluateAssignmentDetail;
import com.fis.crm.repository.EvaluateAssignmentDetailRepository;
import com.fis.crm.service.dto.EvaluateAssignmentDetailDTO;
import com.fis.crm.service.mapper.EvaluateAssignmentDetailMapper;
import com.fis.crm.web.rest.errors.BusinessException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link EvaluateAssignmentDetail}.
 */
@Service
@Transactional
public class EvaluateAssignmentDetailServiceImpl implements EvaluateAssignmentDetailService {

    private final Logger log = LoggerFactory.getLogger(EvaluateAssignmentDetailServiceImpl.class);

    private final EvaluateAssignmentDetailRepository evaluateAssignmentDetailRepository;

    private final EvaluateAssignmentDetailMapper evaluateAssignmentDetailMapper;

    public EvaluateAssignmentDetailServiceImpl(EvaluateAssignmentDetailRepository evaluateAssignmentDetailRepository, EvaluateAssignmentDetailMapper evaluateAssignmentDetailMapper) {
        this.evaluateAssignmentDetailRepository = evaluateAssignmentDetailRepository;
        this.evaluateAssignmentDetailMapper = evaluateAssignmentDetailMapper;
    }

    @Override
    public EvaluateAssignmentDetailDTO save(EvaluateAssignmentDetailDTO evaluateAssignmentDetailDTO) {
        log.debug("Request to save EvaluateAssignmentD : {}", evaluateAssignmentDetailDTO);
        EvaluateAssignmentDetail evaluateAssignmentDetail = evaluateAssignmentDetailMapper.toEntity(evaluateAssignmentDetailDTO);
        evaluateAssignmentDetail = evaluateAssignmentDetailRepository.save(evaluateAssignmentDetail);
        return evaluateAssignmentDetailMapper.toDto(evaluateAssignmentDetail);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<EvaluateAssignmentDetailDTO> findAll(Pageable pageable) {
        log.debug("Request to get all EvaluateAssignmentDS");
        return evaluateAssignmentDetailRepository.findAll(pageable)
            .map(evaluateAssignmentDetailMapper::toDto);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<EvaluateAssignmentDetailDTO> findOne(Long id) {
        log.debug("Request to get EvaluateAssignmentD : {}", id);
        return evaluateAssignmentDetailRepository.findById(id)
            .map(evaluateAssignmentDetailMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete EvaluateAssignmentD : {}", id);
        evaluateAssignmentDetailRepository.deleteById(id);
    }

    @Override
    public boolean updateTotalEvaluated(Long evaluateAssignmentDetailId) {
        if(evaluateAssignmentDetailRepository.updateTotalEvaluated(evaluateAssignmentDetailId) != 1)
            log.info("update updateTotalEvaluated false");
        return true;
    }
}
