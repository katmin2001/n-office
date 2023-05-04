package com.fis.crm.service.impl;

import com.fis.crm.commons.DbUtils;
import com.fis.crm.commons.Translator;
import com.fis.crm.config.Constants;
import com.fis.crm.domain.CampaignEmailBatch;
import com.fis.crm.domain.CampaignEmailMarketing;
import com.fis.crm.domain.CampaignEmailResource;
import com.fis.crm.domain.User;
import com.fis.crm.repository.CampaignEmailBatchRepository;
import com.fis.crm.repository.CampaignEmailMarketingRepository;
import com.fis.crm.repository.CampaignEmailResourceRepository;
import com.fis.crm.service.ActionLogService;
import com.fis.crm.service.CampaignEmailBatchService;
import com.fis.crm.service.UserService;
import com.fis.crm.service.dto.ActionLogDTO;
import com.fis.crm.service.dto.CampaignEmailBatchDTO;
import com.fis.crm.service.mapper.CampaignEmailBatchMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CampaignEmailBatchServiceImpl implements CampaignEmailBatchService {

    private final Logger log = LoggerFactory.getLogger(CampaignEmailBatchServiceImpl.class);

    private final CampaignEmailBatchRepository campaignEmailBatchRepository;
    private final CampaignEmailBatchMapper campaignEmailBatchMapper;
    private final UserService userService;
    private final EntityManager entityManager;
    private final CampaignEmailMarketingRepository campaignEmailMarketingRepository;
    private final CampaignEmailResourceRepository campaignEmailResourceRepository;
    private final ActionLogService actionLogService;

    public CampaignEmailBatchServiceImpl(CampaignEmailBatchRepository campaignEmailBatchRepository, CampaignEmailBatchMapper campaignEmailBatchMapper,
                                         UserService userService,
                                         CampaignEmailMarketingRepository campaignEmailMarketingRepository,
                                         CampaignEmailResourceRepository campaignEmailResourceRepository,
                                         EntityManager entityManager, ActionLogService actionLogService) {
        this.campaignEmailBatchRepository = campaignEmailBatchRepository;
        this.campaignEmailBatchMapper = campaignEmailBatchMapper;
        this.userService = userService;
        this.campaignEmailMarketingRepository = campaignEmailMarketingRepository;
        this.campaignEmailResourceRepository = campaignEmailResourceRepository;
        this.entityManager = entityManager;
        this.actionLogService = actionLogService;
    }

    @Override
    public void saveCampaignEmailBatch(List<CampaignEmailBatchDTO> campaignEmailBatchDTOS) throws Exception {
        List<CampaignEmailBatch> campaignEmailBatches = campaignEmailBatchMapper.toEntity(campaignEmailBatchDTOS);
        Long campaignEmailMarketingId = campaignEmailBatchDTOS.get(0).getCampaignEmailMarketingId();
        CampaignEmailMarketing emailMarketing = campaignEmailMarketingRepository.getOne(campaignEmailMarketingId);
//        CampaignEmailResource emailResource = campaignEmailResourceRepository.getOne(campaignEmailBatchDTOS.get(0).getCampaignEmailResourceId());
        Instant currentDate = Instant.now();
        Optional<User> user = userService.getUserWithAuthorities();
        Long sequence = DbUtils.getSequence(entityManager, "BATCH_ID_SEQ");
        campaignEmailBatches.forEach(campaignEmailBatch -> {
            campaignEmailBatch.setStatus("1");
            campaignEmailBatch.setCreateDate(currentDate);
            campaignEmailBatch.setSendDate(currentDate);
            campaignEmailBatch.setCreateUser(user.get().getId());
            campaignEmailBatch.setBatchId(sequence);
            campaignEmailBatch.setTitle(emailMarketing.getTitle());
            Optional<CampaignEmailResource> emailResourceOptional = campaignEmailResourceRepository.findById(campaignEmailBatch.getCampaignEmailResourceId());
            if (!emailResourceOptional.isPresent()) {
                log.error("Not found campaign email resource id = " + campaignEmailBatch.getCampaignEmailResourceId());
                throw new RuntimeException(Translator.toLocale("campaign-email-resource-not-found") + " " + campaignEmailBatch.getCampaignEmailResourceId());
            }
            CampaignEmailResource emailResource = emailResourceOptional.get();

            //set content
            String content = emailMarketing.getContent();
            content = content.replace("@a0", replaceContent(emailResource.getC1()))
                .replace("@a1", replaceContent(emailResource.getC2()))
                .replace("@a2", replaceContent(emailResource.getC3()))
                .replace("@a3", replaceContent(emailResource.getC4()))
                .replace("@a4", replaceContent(emailResource.getC5()))
                .replace("@a5", replaceContent(emailResource.getC6()))
                .replace("@a6", replaceContent(emailResource.getC7()))
                .replace("@a7", replaceContent(emailResource.getC8()))
                .replace("@a8", replaceContent(emailResource.getC9()))
                .replace("@a9", replaceContent(emailResource.getC10()));
            campaignEmailBatch.setContent(content);
        });
        String chuoi = "";
        for (int i = 0; i < campaignEmailBatchDTOS.size(); i++) {
            chuoi += campaignEmailBatchDTOS.get(i).getEmail() + ", ";
        }
        Optional<User> userLog = userService.getUserWithAuthorities();
        actionLogService.saveActionLog(new ActionLogDTO(userLog.get().getId(), Constants.ACTION_LOG_TYPE.INSERT + "",
            null, String.format("Gửi mail: [%s] ", chuoi),
            new Date(), Constants.MENU_ID.EMAIL_MARKETING, Constants.MENU_ITEM_ID.campaign_email_resource, "CONFIG_MENU_ITEM"));
        campaignEmailBatchRepository.saveAll(campaignEmailBatches);
    }


    private String replaceContent(String checkString) {
        if (checkString == null) return "";
        else return checkString;
    }

    @Override
    public void saveEmailOne(CampaignEmailBatchDTO campaignEmailBatchDTO) {
        Long batchId = DbUtils.getSequence(entityManager, "CAMPAIGN_EMAIL_BATCH_SEQ");
        Optional<User> user = userService.getUserWithAuthorities();
        Instant currentDate = Instant.now();
        CampaignEmailBatch campaignEmailBatch = campaignEmailBatchMapper.toEntity(campaignEmailBatchDTO);
        if (user.isPresent()) {
            campaignEmailBatch.setCreateUser(user.get().getId());
        }
        campaignEmailBatch.setCampaignEmailMarketingId(1L);
        campaignEmailBatch.setCreateDate(currentDate);
        campaignEmailBatch.setStatus(Constants.STATUS_ACTIVE);
        campaignEmailBatchRepository.save(campaignEmailBatch);
        campaignEmailBatch.setBatchId(campaignEmailBatch.getId());
//        String chuoi = "";
//        for (int i =0; i< campaignEmailBatchDTO.size(); i++){
//            chuoi += campaignEmailBatchDTO.get(i).getEmail() + ", ";
//        }
        String chuoi = campaignEmailBatchDTO.getEmail();
        Optional<User> userLog = userService.getUserWithAuthorities();
        actionLogService.saveActionLog(new ActionLogDTO(userLog.get().getId(), Constants.ACTION_LOG_TYPE.INSERT + "",
            null, String.format("Gửi mail đơn lẻ: [%s] ", chuoi),
            new Date(), Constants.MENU_ID.EMAIL_MARKETING, Constants.MENU_ITEM_ID.send_email_one, "CONFIG_MENU_ITEM"));
    }

    @Override
    public void update(List<CampaignEmailBatchDTO> campaignEmailBatchDTOS) {
        Optional<User> user = userService.getUserWithAuthorities();
        CampaignEmailResource emailResource = campaignEmailResourceRepository.getOne(campaignEmailBatchDTOS.get(0).getCampaignEmailResourceId());
        for (CampaignEmailBatchDTO d : campaignEmailBatchDTOS) {
            emailResource = campaignEmailResourceRepository.getOne(d.getCampaignEmailResourceId());
            emailResource.setSendUserId(user.get().getId());
            emailResource.setSendStatus("4");
            campaignEmailResourceRepository.save(emailResource);
        }
    }

}
