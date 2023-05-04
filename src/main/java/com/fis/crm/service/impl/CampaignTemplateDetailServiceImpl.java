package com.fis.crm.service.impl;

import com.fis.crm.commons.DataUtil;
import com.fis.crm.config.Constants;
import com.fis.crm.domain.CampaignTemplate;
import com.fis.crm.domain.CampaignTemplateDetail;
import com.fis.crm.domain.CampaignTemplateOption;
import com.fis.crm.domain.User;
import com.fis.crm.repository.CampaignTemplateDetailRepository;
import com.fis.crm.repository.CampaignTemplateOptionRepository;
import com.fis.crm.repository.CampaignTemplateRepository;
import com.fis.crm.repository.UserRepository;
import com.fis.crm.service.ActionLogService;
import com.fis.crm.service.CampaignTemplateDetailService;
import com.fis.crm.service.UserService;
import com.fis.crm.service.dto.ActionLogDTO;
import com.fis.crm.service.dto.CampaignTemplateDetailDTO;
import com.fis.crm.service.mapper.CampaignTemplateDetailMapper;
import com.fis.crm.web.rest.errors.BusinessException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link CampaignTemplateDetail}.
 */
@Service
@Transactional
public class CampaignTemplateDetailServiceImpl implements CampaignTemplateDetailService {

    private final Logger log = LoggerFactory.getLogger(CampaignTemplateDetailServiceImpl.class);

    private final CampaignTemplateDetailRepository campaignTemplateDetailRepository;

    private final CampaignTemplateOptionRepository campaignTemplateOptionRepository;

    private final CampaignTemplateDetailMapper campaignTemplateDetailMapper;

    private final CampaignTemplateRepository campaignTemplateRepository;

    private final UserService userService;

    private final ActionLogService actionLogService;

    public CampaignTemplateDetailServiceImpl(
        CampaignTemplateDetailRepository campaignTemplateDetailRepository,
        CampaignTemplateOptionRepository campaignTemplateOptionRepository, CampaignTemplateDetailMapper campaignTemplateDetailMapper,
        UserRepository userRepository, CampaignTemplateRepository campaignTemplateRepository, UserService userService, ActionLogService actionLogService) {
        this.campaignTemplateDetailRepository = campaignTemplateDetailRepository;
        this.campaignTemplateOptionRepository = campaignTemplateOptionRepository;
        this.campaignTemplateDetailMapper = campaignTemplateDetailMapper;
        this.campaignTemplateRepository = campaignTemplateRepository;
        this.userService = userService;
        this.actionLogService = actionLogService;
    }

    @Override
    public CampaignTemplateDetailDTO save(CampaignTemplateDetailDTO campaignTemplateDetailDTO) {
        log.debug("Request to save CampaignTemplateDetail2 : {}", campaignTemplateDetailDTO);
        CampaignTemplateDetail isExisted;
        if (!DataUtil.isNullOrEmpty(campaignTemplateDetailDTO.getId())) {
            isExisted = campaignTemplateDetailRepository.findFirstByCodeAndStatusAndCampaignIdAndIdNot(campaignTemplateDetailDTO.getCode(), Constants.STATUS_ACTIVE, campaignTemplateDetailDTO.getCampaignId(), campaignTemplateDetailDTO.getId());
        } else {
            isExisted = campaignTemplateDetailRepository.findFirstByCodeAndStatusAndCampaignId(campaignTemplateDetailDTO.getCode(), Constants.STATUS_ACTIVE, campaignTemplateDetailDTO.getCampaignId());
        }

        if (!DataUtil.isNullOrEmpty(isExisted)) {
            throw new BusinessException("400", "existedTemplateDetail");
        }
        Long userId = userService.getUserIdLogin();
        campaignTemplateDetailDTO.setCreateDatetime(new Date().toInstant());
        campaignTemplateDetailDTO.setCreateUser(userId);
        campaignTemplateDetailDTO.setStatus(Constants.STATUS_ACTIVE);
        CampaignTemplateDetail campaignTemplateDetail = campaignTemplateDetailMapper.toEntity(campaignTemplateDetailDTO);
        campaignTemplateDetail = campaignTemplateDetailRepository.save(campaignTemplateDetail);
        Long campaignTempDetailId = campaignTemplateDetail.getId();
        if (Constants.CBX_CODE.equalsIgnoreCase(campaignTemplateDetailDTO.getType())) {
            campaignTemplateOptionRepository.deleteAllByCampaignTemplateDetailId(campaignTempDetailId);
            List<CampaignTemplateOption> templateOptions = campaignTemplateDetailDTO.getOptionValues();
            templateOptions.forEach(campaignTemplateOption -> {
                campaignTemplateOption.setCampaignTemplateId(campaignTempDetailId);
                campaignTemplateOption.setStatus(Constants.STATUS_ACTIVE);
            });
            campaignTemplateOptionRepository.saveAll(templateOptions);
        }
        Optional<CampaignTemplate> entity = campaignTemplateRepository.findById(campaignTemplateDetailDTO.getCampaignId());
        Optional<User> userLog = userService.getUserWithAuthorities();
        actionLogService.saveActionLog(new ActionLogDTO(userLog.get().getId(), Constants.ACTION_LOG_TYPE.UPDATE + "",
            null, String.format("Cập nhật mẫu chiến dịch: [%s]",entity.get().getCampaignName()),
            new Date(), Constants.MENU_ID.OMS, Constants.MENU_ITEM_ID.callout_campaign, "CONFIG_MENU_ITEM"));
        return campaignTemplateDetailMapper.toDto(campaignTemplateDetail);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CampaignTemplateDetailDTO> findAll(Pageable pageable) {
        log.debug("Request to get all CampaignTemplateDetail2s");
        return campaignTemplateDetailRepository.findAll(pageable).map(campaignTemplateDetailMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<CampaignTemplateDetailDTO> findOne(Long id) {
        log.debug("Request to get CampaignTemplateDetail2 : {}", id);
        return campaignTemplateDetailRepository.findById(id).map(campaignTemplateDetailMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        CampaignTemplateDetail campaignTemplateDetail = campaignTemplateDetailRepository.findById(id).isPresent() ?
            campaignTemplateDetailRepository.findById(id).get() : new CampaignTemplateDetail();
        campaignTemplateDetail.setStatus(Constants.STATUS_INACTIVE.toString());
        Optional<CampaignTemplateDetail> campaignE = campaignTemplateDetailRepository.findById(id);
        Optional<CampaignTemplate> entity = campaignTemplateRepository.findById(campaignE.get().getCampaignId());
        Optional<User> userLog = userService.getUserWithAuthorities();
        actionLogService.saveActionLog(new ActionLogDTO(userLog.get().getId(), Constants.ACTION_LOG_TYPE.UPDATE + "",
            null, String.format("Cập nhật thông tin mẫu chiến dịch: [%s] - [%s]",entity.get().getCampaignName(), campaignE.get().getName()),
            new Date(), Constants.MENU_ID.OMS, Constants.MENU_ITEM_ID.callout_campaign, "CONFIG_MENU_ITEM"));
        campaignTemplateDetailRepository.save(campaignTemplateDetail);
    }

    @Override
    public List<CampaignTemplateDetailDTO> findAllByCampaignId(Long id) {
        return campaignTemplateDetailRepository.findAllByCampaignId(id);
    }
}
