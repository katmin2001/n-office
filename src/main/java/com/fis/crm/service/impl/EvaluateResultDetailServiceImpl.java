package com.fis.crm.service.impl;

import com.fis.crm.commons.DataUtil;
import com.fis.crm.commons.DateUtil;
import com.fis.crm.domain.EvaluateResultDetail;
import com.fis.crm.repository.EvaluateResultDetailRepository;
import com.fis.crm.service.EvaluateResultDetailService;
import com.fis.crm.service.dto.CriteriaCallDTO;
import com.fis.crm.service.dto.EvaluateResultDetailDTO;
import com.fis.crm.service.mapper.EvaluateResultDetailMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link EvaluateResultDetail}.
 */
@Service
@Transactional
public class EvaluateResultDetailServiceImpl implements EvaluateResultDetailService {

    private final Logger log = LoggerFactory.getLogger(EvaluateResultDetailServiceImpl.class);

    private final EvaluateResultDetailRepository evaluateResultDetailRepository;

    private final EvaluateResultDetailMapper evaluateResultDetailMapper;

    public EvaluateResultDetailServiceImpl(EvaluateResultDetailRepository evaluateResultDetailRepository, EvaluateResultDetailMapper evaluateResultDetailMapper) {
        this.evaluateResultDetailRepository = evaluateResultDetailRepository;
        this.evaluateResultDetailMapper = evaluateResultDetailMapper;
    }

    @Override
    public EvaluateResultDetailDTO save(EvaluateResultDetailDTO evaluateResultDetailDTO) {
        log.debug("Request to save EvaluateResultDetai1 : {}", evaluateResultDetailDTO);
        EvaluateResultDetail evaluateResultDetail = evaluateResultDetailMapper.toEntity(evaluateResultDetailDTO);
        evaluateResultDetail = evaluateResultDetailRepository.save(evaluateResultDetail);
        return evaluateResultDetailMapper.toDto(evaluateResultDetail);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<EvaluateResultDetailDTO> findAll(Pageable pageable) {
        log.debug("Request to get all EvaluateResultDetai1s");
        return evaluateResultDetailRepository.findAll(pageable)
            .map(evaluateResultDetailMapper::toDto);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<EvaluateResultDetailDTO> findOne(Long id) {
        log.debug("Request to get EvaluateResultDetai1 : {}", id);
        return evaluateResultDetailRepository.findById(id)
            .map(evaluateResultDetailMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete EvaluateResultDetai1 : {}", id);
        evaluateResultDetailRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CriteriaCallDTO> getCriteriaDetailCall(EvaluateResultDetailDTO evaluateResultDetailDTO, Pageable pageable) {
        Page<Object[]> page = evaluateResultDetailRepository.getCriteriaDetailCall(
            evaluateResultDetailDTO.getCriteriaGroupId() == null  ? -1 : evaluateResultDetailDTO.getCriteriaGroupId(),
            DateUtil.dateToStringDateVN(evaluateResultDetailDTO.getStartDate()),
            DateUtil.dateToStringDateVN(evaluateResultDetailDTO.getEndDate()),
            pageable);
        List<Object[]> objectLst = page.getContent();
        List<CriteriaCallDTO> lstResults = new ArrayList<>();
        for (Object[] object : objectLst) {
            CriteriaCallDTO criteriaCallDTO = new CriteriaCallDTO();
            int index = -1;
            criteriaCallDTO.setCriteriaName(DataUtil.safeToString(object[++index]));
            criteriaCallDTO.setCriteriaPerCall(DataUtil.safeToString(object[++index]));
            criteriaCallDTO.setRate(DataUtil.safeToString(object[++index]));
            lstResults.add(criteriaCallDTO);
        }
        return new PageImpl<>(lstResults, pageable, page.getTotalElements());
    }

    @Override
    @Transactional(readOnly = true)
    public List<CriteriaCallDTO> getAllCriteriaDetailCall(EvaluateResultDetailDTO evaluateResultDetailDTO) {
        List<Object[]> objectLst = evaluateResultDetailRepository.getAllCriteriaDetailCall(
            evaluateResultDetailDTO.getCriteriaGroupId() == null ? -1 : evaluateResultDetailDTO.getCriteriaGroupId(),
            DateUtil.dateToStringDateVN(evaluateResultDetailDTO.getStartDate()),
            DateUtil.dateToStringDateVN(evaluateResultDetailDTO.getEndDate()));
        List<CriteriaCallDTO> lstResults = new ArrayList<>();
        for (Object[] object : objectLst) {
            CriteriaCallDTO criteriaCallDTO = new CriteriaCallDTO();
            int index = -1;
            criteriaCallDTO.setCriteriaName(DataUtil.safeToString(object[++index]));
            criteriaCallDTO.setCriteriaPerCall(DataUtil.safeToString(object[++index]));
            criteriaCallDTO.setRate(DataUtil.safeToString(object[++index]));
            lstResults.add(criteriaCallDTO);
        }
        return lstResults;
    }
}
