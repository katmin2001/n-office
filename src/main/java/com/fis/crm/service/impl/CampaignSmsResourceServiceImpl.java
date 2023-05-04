package com.fis.crm.service.impl;

import com.fis.crm.commons.DataUtil;
import com.fis.crm.config.Constants;
import com.fis.crm.domain.CampaignEmailResource;
import com.fis.crm.domain.CampaignSMSResource;
import com.fis.crm.repository.CampaignSMSResourceRepository;
import com.fis.crm.service.CampaignSmsResourceService;
import com.fis.crm.service.UserService;
import com.fis.crm.service.dto.CampaignEmailResourceDTO;
import com.fis.crm.service.dto.CampaignSMSResourceDTO;
import com.fis.crm.service.dto.UserDTO;
import com.fis.crm.service.mapper.CampaignSMSResourceMapper;
import com.fis.crm.web.rest.errors.BadRequestAlertException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CampaignSmsResourceServiceImpl implements CampaignSmsResourceService {
    private final CampaignSMSResourceRepository campaignSMSResourceRepository;
    private final CampaignSMSResourceMapper campaignSMSResourceMapper;
    private final UserService userService;

    public CampaignSmsResourceServiceImpl(CampaignSMSResourceRepository campaignSMSResourceRepository, CampaignSMSResourceMapper campaignSMSResourceMapper, UserService userService) {
        this.campaignSMSResourceRepository = campaignSMSResourceRepository;
        this.campaignSMSResourceMapper = campaignSMSResourceMapper;
        this.userService = userService;
    }

    @Override
    public Page<CampaignSMSResourceDTO> search(Long campaignSmsMarketingId, String status, String fromDate,String toDate, Pageable pageable) {
        campaignSmsMarketingId = DataUtil.isNullOrEmpty(campaignSmsMarketingId) ? -1L : campaignSmsMarketingId;
        status = DataUtil.isNullOrEmpty(status) ? "-1" : status;
        fromDate = fromDate.equals("-1") ? "" : fromDate;
        toDate = toDate.equals("-1") ? "" : toDate;
        Page<Object[]> page = campaignSMSResourceRepository.search(campaignSmsMarketingId, status, fromDate, toDate, fromDate.equals("") ? 0L : 1L, toDate.equals("") ? 0L : 1L, pageable);
        List<Object[]> objectLst = page.getContent();
        List<CampaignSMSResourceDTO> campaignSMSResourceDTOS = new ArrayList<>();
        for (Object[] object : objectLst) {
            CampaignSMSResourceDTO campaignSMSResourceDTO = new CampaignSMSResourceDTO();
            int index = -1;
            campaignSMSResourceDTO.setId(DataUtil.safeToLong(object[++index]));
            campaignSMSResourceDTO.setCampaignSmsMarketingId(DataUtil.safeToLong(object[++index]));
            campaignSMSResourceDTO.setPhoneNumber(DataUtil.safeToString(object[++index]));
            campaignSMSResourceDTO.setC1(DataUtil.safeToString(object[++index]));
            campaignSMSResourceDTO.setC2(DataUtil.safeToString(object[++index]));
            campaignSMSResourceDTO.setC3(DataUtil.safeToString(object[++index]));
            campaignSMSResourceDTO.setC4(DataUtil.safeToString(object[++index]));
            campaignSMSResourceDTO.setC5(DataUtil.safeToString(object[++index]));
            campaignSMSResourceDTO.setC6(DataUtil.safeToString(object[++index]));
            campaignSMSResourceDTO.setC7(DataUtil.safeToString(object[++index]));
            campaignSMSResourceDTO.setC8(DataUtil.safeToString(object[++index]));
            campaignSMSResourceDTO.setC9(DataUtil.safeToString(object[++index]));
            campaignSMSResourceDTO.setC10(DataUtil.safeToString(object[++index]));
            campaignSMSResourceDTO.setSendStatus(DataUtil.safeToString(object[++index]));
            campaignSMSResourceDTO.setSendDate(DataUtil.safeToInstant(object[++index]));
            campaignSMSResourceDTO.setSendUserId(DataUtil.safeToLong(object[++index]));
            campaignSMSResourceDTO.setCreateDateTime(DataUtil.safeToInstant(object[++index]));

            Optional<UserDTO> userDTO = userService.findFirstById(campaignSMSResourceDTO.getSendUserId());
            if (userDTO.isPresent()) {
                campaignSMSResourceDTO.setSendUserName(userDTO.get().getFullName());
            }
            campaignSMSResourceDTOS.add(campaignSMSResourceDTO);
        }
        return new PageImpl<>(campaignSMSResourceDTOS, pageable, page.getTotalElements());
    }

    @Override
    public void changeStatus(Long id) {
        try {
            Optional<CampaignSMSResource> campaignSMSResourceOptional = campaignSMSResourceRepository.findById(id);

            if (campaignSMSResourceOptional.isPresent()) {
                campaignSMSResourceOptional.get().setSendStatus(Constants.STATUS_INACTIVE_STR);
                campaignSMSResourceRepository.save(campaignSMSResourceOptional.get());
            }
        } catch (Exception e) {
            throw new BadRequestAlertException("Xóa thất bại", "", "");
        }
    }
}
