package com.fis.crm.service.impl;

import com.fis.crm.domain.CampaignTemplateOption;
import com.fis.crm.repository.CampaignTemplateOptionRepository;
import com.fis.crm.service.CampaignTemplateOptionService;
import com.fis.crm.service.dto.CampaignTemplateOptionDTO;
import com.fis.crm.service.mapper.CampaignTemplateOptionMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link CampaignTemplateOption}.
 */
@Service
@Transactional
public class CampaignTemplateOptionServiceImpl implements CampaignTemplateOptionService {

  private final Logger log = LoggerFactory.getLogger(CampaignTemplateOptionServiceImpl.class);

  private final CampaignTemplateOptionRepository campaignTemplateOptionRepository;

  private final CampaignTemplateOptionMapper campaignTemplateOptionMapper;

  public CampaignTemplateOptionServiceImpl(
    CampaignTemplateOptionRepository campaignTemplateOptionRepository,
    CampaignTemplateOptionMapper campaignTemplateOptionMapper
  ) {
    this.campaignTemplateOptionRepository = campaignTemplateOptionRepository;
    this.campaignTemplateOptionMapper = campaignTemplateOptionMapper;
  }

  @Override
  public CampaignTemplateOptionDTO save(CampaignTemplateOptionDTO campaignTemplateOptionDTO) {
    log.debug("Request to save CampaignTemplateOption : {}", campaignTemplateOptionDTO);
    CampaignTemplateOption campaignTemplateOption = campaignTemplateOptionMapper.toEntity(campaignTemplateOptionDTO);
    campaignTemplateOption = campaignTemplateOptionRepository.save(campaignTemplateOption);
    return campaignTemplateOptionMapper.toDto(campaignTemplateOption);
  }

  @Override
  @Transactional(readOnly = true)
  public Page<CampaignTemplateOptionDTO> findAll(Pageable pageable) {
    log.debug("Request to get all CampaignTemplateOptions");
    return campaignTemplateOptionRepository.findAll(pageable).map(campaignTemplateOptionMapper::toDto);
  }

  @Override
  @Transactional(readOnly = true)
  public Optional<CampaignTemplateOptionDTO> findOne(Long id) {
    log.debug("Request to get CampaignTemplateOption : {}", id);
    return campaignTemplateOptionRepository.findById(id).map(campaignTemplateOptionMapper::toDto);
  }

  @Override
  public void delete(Long id) {
    log.debug("Request to delete CampaignTemplateOption : {}", id);
    campaignTemplateOptionRepository.deleteById(id);
  }
}
