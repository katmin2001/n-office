package com.fis.crm.service.impl;

import com.fis.crm.commons.DataUtil;
import com.fis.crm.config.Constants;
import com.fis.crm.domain.CampaignEmailResource;
import com.fis.crm.repository.CampaignEmailResourceRepository;
import com.fis.crm.repository.CampaignResourceRepository;
import com.fis.crm.service.CampaignEmailResourceService;
import com.fis.crm.service.UserService;
import com.fis.crm.service.dto.CampaignEmailResourceDTO;
import com.fis.crm.service.dto.UserDTO;
import com.fis.crm.service.mapper.CampaignEmailResourceMapper;
import com.fis.crm.web.rest.errors.BadRequestAlertException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.*;

@Service
@Transactional
public class CampaignEmailResourceServiceImpl implements CampaignEmailResourceService {
    private final CampaignEmailResourceRepository campaignEmailResourceRepository;
    private final CampaignEmailResourceMapper campaignEmailResourceMapper;
    private final UserService userService;
    private final EntityManager entityManager;
    private final CampaignResourceRepository campaignResourceRepository;

    public CampaignEmailResourceServiceImpl(CampaignEmailResourceRepository campaignEmailResourceRepository, CampaignEmailResourceMapper campaignEmailResourceMapper, UserService userService, EntityManager entityManager, CampaignResourceRepository campaignResourceRepository) {
        this.campaignEmailResourceRepository = campaignEmailResourceRepository;
        this.campaignEmailResourceMapper = campaignEmailResourceMapper;
        this.userService = userService;
        this.entityManager = entityManager;
        this.campaignResourceRepository = campaignResourceRepository;
    }

    @Override
    public Page<CampaignEmailResourceDTO> search(Long campaignEmailMarketingId, String status, String fromDate,String toDate, Pageable pageable) {
        campaignEmailMarketingId = DataUtil.isNullOrEmpty(campaignEmailMarketingId) ? -1L : campaignEmailMarketingId;
        status = DataUtil.isNullOrEmpty(status) ? "-1" : status;
        fromDate = fromDate.equals("-1") ? "" : fromDate;
        toDate = toDate.equals("-1") ? "" : toDate;
        Page<Object[]> page = campaignEmailResourceRepository.search(campaignEmailMarketingId,status,fromDate,toDate,fromDate.equals("") ? 0L : 1L,toDate.equals("") ? 0L : 1L,pageable);
        List<Object[]> objectLst = page.getContent();
        List<CampaignEmailResourceDTO> campaignEmailResourceDTOS = new ArrayList<>();
        for (Object[] object : objectLst) {
            CampaignEmailResourceDTO campaignEmailResourceDTO = new CampaignEmailResourceDTO();
            int index = -1;
            campaignEmailResourceDTO.setId(DataUtil.safeToLong(object[++index]));
            campaignEmailResourceDTO.setCampaignEmailMarketingId(DataUtil.safeToLong(object[++index]));
            campaignEmailResourceDTO.setEmail(DataUtil.safeToString(object[++index]));
            campaignEmailResourceDTO.setC1(DataUtil.safeToString(object[++index]));
            campaignEmailResourceDTO.setC2(DataUtil.safeToString(object[++index]));
            campaignEmailResourceDTO.setC3(DataUtil.safeToString(object[++index]));
            campaignEmailResourceDTO.setC4(DataUtil.safeToString(object[++index]));
            campaignEmailResourceDTO.setC5(DataUtil.safeToString(object[++index]));
            campaignEmailResourceDTO.setC6(DataUtil.safeToString(object[++index]));
            campaignEmailResourceDTO.setC7(DataUtil.safeToString(object[++index]));
            campaignEmailResourceDTO.setC8(DataUtil.safeToString(object[++index]));
            campaignEmailResourceDTO.setC9(DataUtil.safeToString(object[++index]));
            campaignEmailResourceDTO.setC10(DataUtil.safeToString(object[++index]));
            campaignEmailResourceDTO.setSendStatus(DataUtil.safeToString(object[++index]));
            campaignEmailResourceDTO.setSendDate(DataUtil.safeToInstant(object[++index]));
            campaignEmailResourceDTO.setSendUserId(DataUtil.safeToLong(object[++index]));
            campaignEmailResourceDTO.setCreateDateTime(DataUtil.safeToInstant(object[++index]));

            Optional<UserDTO> userDTO = userService.findFirstById(campaignEmailResourceDTO.getSendUserId());
           if (userDTO.isPresent()) {
               campaignEmailResourceDTO.setSendUserName(userDTO.get().getFullName());
           }
            campaignEmailResourceDTOS.add(campaignEmailResourceDTO);
        }
        return new PageImpl<>(campaignEmailResourceDTOS, pageable, page.getTotalElements());
    }

    @Override
    public void changeStatus(Long id) {
        try {
            Optional<CampaignEmailResource> campaignEmailResourceOptional = campaignEmailResourceRepository.findById(id);

            if (campaignEmailResourceOptional.isPresent()) {
                campaignEmailResourceOptional.get().setSendStatus(Constants.STATUS_INACTIVE_STR);
                campaignEmailResourceRepository.save(campaignEmailResourceOptional.get());
            }
        } catch (Exception e) {
            throw new BadRequestAlertException("Xóa thất bại", "", "");
        }
    }
}
