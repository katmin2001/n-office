package com.fis.crm.service.impl;

import com.fis.crm.commons.DbUtils;
import com.fis.crm.config.Constants;
import com.fis.crm.domain.CampaignSMSMarketing;
import com.fis.crm.domain.CampaignSMSResource;
import com.fis.crm.domain.CampaignSmsBatch;
import com.fis.crm.domain.User;
import com.fis.crm.repository.CampaignSMSMarketingRepository;
import com.fis.crm.repository.CampaignSMSResourceRepository;
import com.fis.crm.repository.CampaignSmsBatchRepository;
import com.fis.crm.service.ActionLogService;
import com.fis.crm.service.CampaignSmsBatchService;
import com.fis.crm.service.UserService;
import com.fis.crm.service.dto.ActionLogDTO;
import com.fis.crm.service.dto.CampaignSmsBatchDTO;
import com.fis.crm.service.dto.CampaignSmsBatchResDTO;
import com.fis.crm.service.mapper.CampaignSmsBatchMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link CampaignSmsBatch}.
 */
@Service
@Transactional
public class CampaignSmsBatchServiceImpl implements CampaignSmsBatchService {

    private final Logger log = LoggerFactory.getLogger(CampaignSmsBatchServiceImpl.class);

    private final CampaignSmsBatchRepository campaignSmsBatchRepository;
    private final CampaignSMSResourceRepository campaignSMSResourceRepository;
    private final CampaignSMSMarketingRepository campaignSMSMarketingRepository;

    private final CampaignSmsBatchMapper campaignSmsBatchMapper;
    private final UserService userService;
    private final EntityManager entityManager;
    private final ActionLogService actionLogService;

    public CampaignSmsBatchServiceImpl(CampaignSmsBatchRepository campaignSmsBatchRepository, CampaignSMSResourceRepository campaignSMSResourceRepository,
                                       CampaignSmsBatchMapper campaignSmsBatchMapper,
                                       UserService userService,
                                       EntityManager entityManager,
                                       CampaignSMSMarketingRepository campaignSMSMarketingRepository, ActionLogService actionLogService) {
        this.campaignSmsBatchRepository = campaignSmsBatchRepository;
        this.campaignSMSResourceRepository = campaignSMSResourceRepository;
        this.campaignSmsBatchMapper = campaignSmsBatchMapper;
        this.userService = userService;
        this.entityManager = entityManager;
        this.campaignSMSMarketingRepository = campaignSMSMarketingRepository;
        this.actionLogService = actionLogService;
    }

    @Override
    public CampaignSmsBatchDTO save(CampaignSmsBatchDTO campaignSmsBatchDTO) {
        log.debug("Request to save CampaignSmsBatch : {}", campaignSmsBatchDTO);
        CampaignSmsBatch campaignSmsBatch = campaignSmsBatchMapper.toEntity(campaignSmsBatchDTO);
        campaignSmsBatch = campaignSmsBatchRepository.save(campaignSmsBatch);
        return campaignSmsBatchMapper.toDto(campaignSmsBatch);
    }

    @Override
    public List<CampaignSmsBatchDTO> save2(CampaignSmsBatchResDTO campaignSmsBatchResDTO) {
        log.debug("Request to save CampaignSmsBatch : {}", campaignSmsBatchResDTO);

        Optional<User> user = userService.getUserWithAuthorities();
        List<CampaignSmsBatchDTO> list = new ArrayList<>();
        LocalDate date = LocalDate.now();
        Instant instant = date.atStartOfDay(ZoneId.of("Asia/Ho_Chi_Minh")).toInstant();
        boolean check = false;
        check = campaignSmsBatchResDTO.getListPhoneNumber().contains(",,");
        String[] listPhone = campaignSmsBatchResDTO.getListPhoneNumber().split(",");
        String isVNPhoneMobile = "(09|01|03|08[2|6|8|9])+([0-9]{8})\\b";
        for (String phoneNumber : listPhone) {
            if (phoneNumber != null && check == false && "".equals(phoneNumber) == false && phoneNumber.matches(isVNPhoneMobile)) {
                CampaignSmsBatchDTO campaignSmsBatchDTO = new CampaignSmsBatchDTO();
                CampaignSmsBatch campaignSmsBatch = campaignSmsBatchMapper.toEntity(campaignSmsBatchDTO);
                campaignSmsBatch = campaignSmsBatchRepository.save(campaignSmsBatch);
                Optional<CampaignSmsBatch> campaignSmsBatch1 = Optional.of(new CampaignSmsBatch());
                campaignSmsBatch1 = campaignSmsBatchRepository.findById(campaignSmsBatch.getId());

                campaignSmsBatch1.get().setBatchId(campaignSmsBatch1.get().getId());
                campaignSmsBatch1.get().setCampaignSmsMarketingId(-1L);
                campaignSmsBatch1.get().setPhoneNumber(phoneNumber);
                campaignSmsBatch1.get().setContent(campaignSmsBatchResDTO.getContent());
                campaignSmsBatch1.get().setCreateDatetime(instant);
                campaignSmsBatch1.get().setStatus("1");
                campaignSmsBatch1.get().setCreateUser(user.get().getId());

                campaignSmsBatch =campaignSmsBatch1.get();
                campaignSmsBatch = campaignSmsBatchRepository.save(campaignSmsBatch1.get());

                CampaignSmsBatchDTO campaignSmsBatchDTO1 = campaignSmsBatchMapper.toDto(campaignSmsBatch);
                campaignSmsBatchDTO1.setCheckList(1);
                list.add(campaignSmsBatchDTO1);
            } else {
                CampaignSmsBatchDTO campaignSmsBatchDTO = new CampaignSmsBatchDTO();
                campaignSmsBatchDTO.setCheckList(2);
                list.add(campaignSmsBatchDTO);
            }
        }
        Optional<User> userLog = userService.getUserWithAuthorities();
        actionLogService.saveActionLog(new ActionLogDTO(userLog.get().getId(), Constants.ACTION_LOG_TYPE.INSERT + "",
            null, String.format("Gửi SMS đơn lẻ: Số điện thoại [%s]", campaignSmsBatchResDTO.getListPhoneNumber()),
            new Date(), Constants.MENU_ID.SMS_MARKETING, Constants.MENU_ITEM_ID.send_sms_one, "CONFIG_MENU_ITEM"));
        return list;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CampaignSmsBatchDTO> findAll(Pageable pageable) {
        log.debug("Request to get all CampaignSmsBatches");
        return campaignSmsBatchRepository.findAll(pageable)
            .map(campaignSmsBatchMapper::toDto);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<CampaignSmsBatchDTO> findOne(Long id) {
        log.debug("Request to get CampaignSmsBatch : {}", id);
        return campaignSmsBatchRepository.findById(id)
            .map(campaignSmsBatchMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete CampaignSmsBatch : {}", id);
        campaignSmsBatchRepository.deleteById(id);
    }

    @Override
    public void saveListSmsBatch(List<CampaignSmsBatchDTO> campaignSmsBatchDTOS) {
        List<CampaignSmsBatch> campaignSMSBatches = campaignSmsBatchMapper.toEntity(campaignSmsBatchDTOS);

        Instant currentDate = Instant.now();
        Optional<User> user = userService.getUserWithAuthorities();
        Long sequence = DbUtils.getSequence(entityManager, "SMS_BATCH_ID_SEQ");
        String content ="";

        if(campaignSMSBatches.size()>0){
            Optional<CampaignSMSMarketing> csmOptional = campaignSMSMarketingRepository.findById(campaignSMSBatches.get(0).getCampaignSmsMarketingId());
            if(csmOptional.isPresent()){
                content = csmOptional.get().getContent();
            }
        }
        String finalContent = content;
        campaignSMSBatches.forEach(campaignEmailBatch -> {
            campaignEmailBatch.setCreateDatetime(currentDate);
            campaignEmailBatch.setSendDate(currentDate);
            campaignEmailBatch.setCreateUser(user.get().getId());
            campaignEmailBatch.setBatchId(sequence);
            campaignEmailBatch.setStatus("1");
            CampaignSMSResource smsResource=null;
            Optional<CampaignSMSResource> smsResourceOptional = campaignSMSResourceRepository.findById(campaignEmailBatch.getResourceId());
            if(smsResourceOptional.isPresent()){
                smsResource = smsResourceOptional.get();
                if(finalContent!=""){
                    campaignEmailBatch.setContent(getContent(finalContent, smsResource));
                }
            }
        });

        String chuoi = "";
        for (int i = 0; i < campaignSmsBatchDTOS.size(); i++){
            chuoi += campaignSmsBatchDTOS.get(i).getPhoneNumber() + ", ";
        }
        Optional<User> userLog = userService.getUserWithAuthorities();
        actionLogService.saveActionLog(new ActionLogDTO(userLog.get().getId(), Constants.ACTION_LOG_TYPE.INSERT + "",
            null, String.format("Gửi chiến dịch SMS marketing: Số điện thoại [%s]", chuoi),
            new Date(), Constants.MENU_ID.SMS_MARKETING, Constants.MENU_ITEM_ID.campaign_sms_resource, "CONFIG_MENU_ITEM"));
        campaignSmsBatchRepository.saveAll(campaignSMSBatches);
    }

    private String getContent(String marketingContent, CampaignSMSResource smsResource){
        String content = marketingContent.replace("@a0", replaceContent(smsResource.getC1()))
            .replace("@a1", replaceContent(smsResource.getC2()))
            .replace("@a2", replaceContent(smsResource.getC3()))
            .replace("@a3", replaceContent(smsResource.getC4()))
            .replace("@a4", replaceContent(smsResource.getC5()))
            .replace("@a5", replaceContent(smsResource.getC6()))
            .replace("@a6", replaceContent(smsResource.getC7()))
            .replace("@a7", replaceContent(smsResource.getC8()))
            .replace("@a8", replaceContent(smsResource.getC9()))
            .replace("@a9", replaceContent(smsResource.getC10()));
        return content;
    }

    private String replaceContent(String checkString){
        if(checkString==null) return "";
        else return checkString;
    }

    @Override
    public void update(List<CampaignSmsBatchDTO> campaignSmsBatchDTOS) {
            Optional<User> user = userService.getUserWithAuthorities();
            CampaignSMSResource smsResource = campaignSMSResourceRepository.getOne(campaignSmsBatchDTOS.get(0).getResourceId());
            for( CampaignSmsBatchDTO d:campaignSmsBatchDTOS)
            {
                smsResource = campaignSMSResourceRepository.getOne(d.getResourceId());
                smsResource.setSendUserId(user.get().getId());
                smsResource.setSendStatus("4");
                Date date = new Date();
                smsResource.setSendDate(new Date().toInstant());
                campaignSMSResourceRepository.save(smsResource);
            }

    }
}
