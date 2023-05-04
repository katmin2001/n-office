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
import com.fis.crm.service.ActionLogService;
import com.fis.crm.service.CampaignTemplateService;
import com.fis.crm.service.UserService;
import com.fis.crm.service.dto.ActionLogDTO;
import com.fis.crm.service.dto.CampaignTemplateDTO;
import com.fis.crm.service.dto.CampaignTemplateListDTO;
import com.fis.crm.service.dto.OptionSetValueDTO;
import com.fis.crm.service.mapper.CampaignTemplateDetailMapper;
import com.fis.crm.service.mapper.CampaignTemplateMapper;
import org.apache.commons.lang3.SerializationUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link CampaignTemplate}.
 */
@Service
@Transactional
public class CampaignTemplateServiceImpl implements CampaignTemplateService {

    private final Logger log = LoggerFactory.getLogger(CampaignTemplateServiceImpl.class);

    private final CampaignTemplateRepository campaignTemplateRepository;

    private final CampaignTemplateDetailRepository campaignTemplateDetailRepository;

    private final CampaignTemplateOptionRepository campaignTemplateOptionRepository;

    private final UserService userService;

    private final CampaignTemplateMapper campaignTemplateMapper;

    private final CampaignTemplateDetailMapper campaignTemplateDetailMapper;

    private final ActionLogService actionLogService;

    public CampaignTemplateServiceImpl(CampaignTemplateRepository campaignTemplateRepository, CampaignTemplateDetailRepository campaignTemplateDetailRepository, CampaignTemplateOptionRepository campaignTemplateOptionRepository, UserService userService, CampaignTemplateMapper campaignTemplateMapper, CampaignTemplateDetailMapper campaignTemplateDetailMapper, ActionLogService actionLogService) {
        this.campaignTemplateRepository = campaignTemplateRepository;
        this.campaignTemplateDetailRepository = campaignTemplateDetailRepository;
        this.campaignTemplateOptionRepository = campaignTemplateOptionRepository;
        this.userService = userService;
        this.campaignTemplateMapper = campaignTemplateMapper;
        this.campaignTemplateDetailMapper = campaignTemplateDetailMapper;
        this.actionLogService = actionLogService;
    }

    @Override
    public CampaignTemplateDTO save(CampaignTemplateDTO campaignTemplateDTO) {
        log.debug("Request to save CampaignTemplate : {}", campaignTemplateDTO);
        Long userId = userService.getUserIdLogin();
        CampaignTemplateDTO dto = campaignTemplateRepository.insertTemplate(campaignTemplateDTO, userId);
        Optional<User> userLog = userService.getUserWithAuthorities();
        actionLogService.saveActionLog(new ActionLogDTO(userLog.get().getId(), Constants.ACTION_LOG_TYPE.INSERT + "",
            null, String.format("Thêm mới mẫu chiến dịch: [%s]",campaignTemplateDTO.getCampaignName()),
            new Date(), Constants.MENU_ID.OMS, Constants.MENU_ITEM_ID.callout_campaign, "CONFIG_MENU_ITEM"));
        return dto;
    }

    @Override
    @Transactional(readOnly = true)
    public List<CampaignTemplateDTO> findAll() {
        log.debug("Request to get all CampaignTemplates");
        return campaignTemplateRepository.findAllAndStatus();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<CampaignTemplateDTO> findOne(Long id) {
        log.debug("Request to get CampaignTemplate : {}", id);
        return campaignTemplateRepository.findById(id).map(campaignTemplateMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete CampaignTemplate : {}", id);
        CampaignTemplate campaignTemplate = campaignTemplateRepository.findById(id).isPresent() ? campaignTemplateRepository.findById(id).get() : new CampaignTemplate();
        campaignTemplate.setStatus(Constants.STATUS_INACTIVE.toString());
        Optional<CampaignTemplate> entity = campaignTemplateRepository.findById(id);
        Optional<User> userLog = userService.getUserWithAuthorities();
        actionLogService.saveActionLog(new ActionLogDTO(userLog.get().getId(), Constants.ACTION_LOG_TYPE.DELETE + "",
            null, String.format("Xóa mẫu chiến dịch: [%s]",entity.get().getCampaignName()),
            new Date(), Constants.MENU_ID.OMS, Constants.MENU_ITEM_ID.callout_campaign, "CONFIG_MENU_ITEM"));
        campaignTemplateRepository.save(campaignTemplate);
    }

    @Override
    public List<OptionSetValueDTO> getAllInfoCode() {
        return campaignTemplateRepository.getAllInfoCode();
    }

    @Override
    public Boolean checkUnusedTemplate(Long id) {
        return campaignTemplateRepository.checkUnusedTemplate(id);
    }

    @Override
    public CampaignTemplate checkExistedTemplate(String campaignName, Long id) {
        return campaignTemplateRepository.findFirstByCampaignNameAndIdNot(campaignName, id);
    }

    @Override
    public CampaignTemplateDTO update(CampaignTemplateDTO campaignTemplateDTO) {
        Long userId = userService.getUserIdLogin();
        CampaignTemplate campaignTemplate = campaignTemplateMapper.toEntity(campaignTemplateDTO);
        campaignTemplate.setCreateDatetime(new Date());
        campaignTemplate.setCreateUser(userId);
        Optional<User> userLog = userService.getUserWithAuthorities();
        actionLogService.saveActionLog(new ActionLogDTO(userLog.get().getId(), Constants.ACTION_LOG_TYPE.UPDATE + "",
            null, String.format("Cập nhật mẫu chiến dịch: [%s]",campaignTemplateDTO.getCampaignName()),
            new Date(), Constants.MENU_ID.OMS, Constants.MENU_ITEM_ID.callout_campaign, "CONFIG_MENU_ITEM"));
        return campaignTemplateMapper.toDto(campaignTemplateRepository.save(campaignTemplate));
    }

    @Override
    public CampaignTemplateDTO copy(CampaignTemplateDTO campaignTemplateDTO) {
        CampaignTemplate copyTemplate = campaignTemplateMapper.toEntity(campaignTemplateDTO);
        Long userId = userService.getUserIdLogin();
        copyTemplate.setId(null);
        copyTemplate.setCreateDatetime(new Date());
        copyTemplate.setCreateUser(userId);
        CampaignTemplate templateSaved = campaignTemplateRepository.save(copyTemplate);
        List<CampaignTemplateDetail> copyDetailTemplate = campaignTemplateDetailRepository.findAllByCampaignIdAndStatus(campaignTemplateDTO.getId(), Constants.STATUS_ACTIVE.toLowerCase());
        if (!DataUtil.isNullOrEmpty(copyDetailTemplate)) {
            copyDetailTemplate.forEach(obj -> {
                CampaignTemplateDetail campaignTemplateDetail = SerializationUtils.clone(obj);
                campaignTemplateDetail.setId(null);
                campaignTemplateDetail.setCreateUser(userId);
                campaignTemplateDetail.setCampaignId(templateSaved.getId());
                campaignTemplateDetail.setCreateDatetime(new Date().toInstant());
                campaignTemplateDetail.setCreateUser(userId);
                CampaignTemplateDetail savedDetail = campaignTemplateDetailRepository.save(campaignTemplateDetail);
                List<CampaignTemplateOption> copyOptions = campaignTemplateOptionRepository.findAllByCampaignTemplateDetailId(obj.getId());
                List<CampaignTemplateOption> beforeSaveOptions = new ArrayList<>();
                if (!DataUtil.isNullOrEmpty(copyOptions)) {
                    copyOptions.forEach(option -> {
                        CampaignTemplateOption campaignTemplateOption = SerializationUtils.clone(option);
                        campaignTemplateOption.setId(null);
                        campaignTemplateOption.setCampaignTemplateId(savedDetail.getId());
                        beforeSaveOptions.add(campaignTemplateOption);
                    });
                    campaignTemplateOptionRepository.saveAll(beforeSaveOptions);
                }
            });
        }
        Optional<CampaignTemplate> entity = campaignTemplateRepository.findById(campaignTemplateDTO.getId());
        Optional<User> userLog = userService.getUserWithAuthorities();
        actionLogService.saveActionLog(new ActionLogDTO(userLog.get().getId(), Constants.ACTION_LOG_TYPE.INSERT + "",
            null, String.format("Copy mẫu chiến dịch: [%s] từ [%s]",campaignTemplateDTO.getCampaignName(), entity.get().getCampaignName()),
            new Date(), Constants.MENU_ID.OMS, Constants.MENU_ITEM_ID.callout_campaign, "CONFIG_MENU_ITEM"));
        return campaignTemplateMapper.toDto(templateSaved);
    }

    @Override
    public List<CampaignTemplateListDTO> getListCampaignTemplateForCombobox() {
        List<Object[]> lst = new ArrayList<Object[]>();
        List<CampaignTemplateListDTO> lstResult = new ArrayList<>();
        try {

            lst = campaignTemplateRepository.getListCampaignTemplateForCombobox();
            for (Object[] obj1 : lst){
                CampaignTemplateListDTO campaignTemplateListDTO = new CampaignTemplateListDTO();

                campaignTemplateListDTO.setId(DataUtil.safeToLong(obj1[0]));
                campaignTemplateListDTO.setCampaignTemplateName(DataUtil.safeToString(obj1[1]));


                lstResult.add(campaignTemplateListDTO);
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return lstResult;
    }
}
