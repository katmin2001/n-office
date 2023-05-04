package com.fis.crm.service.impl;

import com.fis.crm.commons.ExcelUtils;
import com.fis.crm.config.Constants;
import com.fis.crm.domain.User;
import com.fis.crm.repository.CampaignPerformInformationCustomRepository;
import com.fis.crm.security.SecurityUtils;
import com.fis.crm.service.ActionLogService;
import com.fis.crm.service.CampaignPerformInformationService;
import com.fis.crm.service.UserService;
import com.fis.crm.service.dto.ActionLogDTO;
import com.fis.crm.service.dto.CampaignPerformDTO;
import com.fis.crm.service.dto.CampaignResourceTemplateDTO;
import com.fis.crm.service.dto.ListExcelDynamicGroupDTO;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class CampaignPerformInformationServiceImpl implements CampaignPerformInformationService {

    final
    CampaignPerformInformationCustomRepository campaignPerformInformationCustomRepository;

    final
    ExcelUtils excelUtils;
    private final UserService userService;
    private final ActionLogService actionLogService;

    public CampaignPerformInformationServiceImpl(CampaignPerformInformationCustomRepository campaignPerformInformationCustomRepository, ExcelUtils excelUtils, UserService userService, ActionLogService actionLogService) {
        this.campaignPerformInformationCustomRepository = campaignPerformInformationCustomRepository;
        this.excelUtils = excelUtils;
        this.userService = userService;
        this.actionLogService = actionLogService;
    }

    @Override
    public List<CampaignPerformDTO> getDataInformation(String call_time_from, String call_time_to, Long campaign_id, Long campaign_resource_id, Long assign_user_id) {
        User user = userService.findOneByLogin(SecurityUtils.getCurrentUserLogin().get()).get();
        //Neu la role TDV thi chi load minh TDV
        if(user.getType().equals("1"))
        {
            assign_user_id= user.getId();
        }
        return campaignPerformInformationCustomRepository.getDataInformation(call_time_from,
            call_time_to,
            campaign_id,
            campaign_resource_id,
            assign_user_id);
    }

    @Override
    public List<CampaignPerformDTO> getCallStatus(Long campaign_id) {
        return campaignPerformInformationCustomRepository.getCallStatus(campaign_id, "Status");
    }

    @Override
    public List<CampaignPerformDTO> getSurveyStatus(Long campaign_id) {
        return campaignPerformInformationCustomRepository.getCallStatus(campaign_id, "SalesStatus");
    }

    @Override
    public List<List<CampaignResourceTemplateDTO>> getCallData(String call_time_from,
                                                               String call_time_to,
                                                               Long campaign_id,
                                                               Long campaign_resource_id,
                                                               Long assign_user_id,
                                                               String type,
                                                               String status,
                                                               String saleStatus) {
        return campaignPerformInformationCustomRepository.getCallData(
            call_time_from,
            call_time_to,
            campaign_id,
            campaign_resource_id,
            assign_user_id,
            type,
            status,
            saleStatus);
    }

    @Override
    public ByteArrayInputStream exportExcel(CampaignPerformDTO campaignPerformDTO) {
        try {
            ListExcelDynamicGroupDTO listExcelDynamicGroupDTO = campaignPerformInformationCustomRepository.exportExcel(campaignPerformDTO.getCall_time_from(),
                campaignPerformDTO.getCall_time_to(),
                campaignPerformDTO.getCampaign_id(),
                campaignPerformDTO.getCampaign_resource_id(),
                campaignPerformDTO.getAssign_user_id(),
                campaignPerformDTO.getType(),
                campaignPerformDTO.getStatus(),
                campaignPerformDTO.getSaleStatus());
            Optional<User> userLog = userService.getUserWithAuthorities();
            actionLogService.saveActionLog(new ActionLogDTO(userLog.get().getId(), Constants.ACTION_LOG_TYPE.EXPORT + "",
                null, "Xuất excel: Dữ liệu cá nhân",
                new Date(), Constants.MENU_ID.OMS, Constants.MENU_ITEM_ID.campaign_perform_information, "CONFIG_MENU_ITEM"));
            return excelUtils.buildExcelDynamic(listExcelDynamicGroupDTO, "hihi");
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
