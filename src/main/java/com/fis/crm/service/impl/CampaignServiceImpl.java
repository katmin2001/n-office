package com.fis.crm.service.impl;

import com.fis.crm.commons.DataUtil;
import com.fis.crm.config.Constants;
import com.fis.crm.domain.Campaign;
import com.fis.crm.domain.Campaign_;
import com.fis.crm.domain.User;
import com.fis.crm.repository.CampaignRepository;
import com.fis.crm.service.ActionLogService;
import com.fis.crm.service.CampaignService;
import com.fis.crm.service.UserService;
import com.fis.crm.service.dto.ActionLogDTO;
import com.fis.crm.service.dto.CampaignDTO;
import com.fis.crm.service.mapper.CampaignMapper;
import com.fis.crm.web.rest.errors.BadRequestAlertException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link Campaign}.
 */
@Service
@Transactional
public class CampaignServiceImpl implements CampaignService {

    @Autowired
    EntityManager entityManager;

    private final Logger log = LoggerFactory.getLogger(CampaignServiceImpl.class);

    private final CampaignRepository campaignRepository;

    private final CampaignMapper campaignMapper;

    private final UserService userService;

    private final ActionLogService actionLogService;

    public CampaignServiceImpl(CampaignRepository campaignRepository, CampaignMapper campaignMapper, UserService userService, ActionLogService actionLogService) {
        this.campaignRepository = campaignRepository;
        this.campaignMapper = campaignMapper;
        this.userService = userService;
        this.actionLogService = actionLogService;
    }

    @Override
    public CampaignDTO save(CampaignDTO campaignDTO) {
        log.debug("Request to save Campaign : {}", campaignDTO);
        if (campaignRepository.countByName(campaignDTO.getName()) != 0 && campaignDTO.getId() == null) {
            throw new BadRequestAlertException("Tên đã được sử dụng, vui lòng nhập thông tin khác", "", "");
        }
        Optional<User> user = userService.getUserWithAuthorities();
        campaignDTO.setUpdateUser(user.get().getId());
        Campaign campaign = campaignMapper.toEntity(campaignDTO);
        campaign.setListCallStatus(convertToListCallStatusString(campaignDTO.getListCallStatusArray()));
        campaign = campaignRepository.save(campaign);
        if (campaignDTO.getId() != null){
            actionLogService.saveActionLog(new ActionLogDTO(user.get().getId(), Constants.ACTION_LOG_TYPE.UPDATE + "",
                campaign.getId(), String.format("Cập nhật Chiến dịch: [%s]", campaign.getName()),
                new Date(), Constants.MENU_ID.OMS, Constants.MENU_ITEM_ID.campaign, "CONFIG_MENU_ITEM"));
        } else {
            actionLogService.saveActionLog(new ActionLogDTO(user.get().getId(), Constants.ACTION_LOG_TYPE.INSERT + "",
                campaign.getId(), String.format("Thêm mới Chiến dịch: [%s]", campaign.getName()),
                new Date(), Constants.MENU_ID.OMS, Constants.MENU_ITEM_ID.campaign, "CONFIG_MENU_ITEM"));
        }
        return campaignMapper.toDto(campaign);
    }

    private String convertToListCallStatusString(String[] array) {
        String listCallStatus = "";
        if (array != null && array.length > 0) {
            for (String e : array) {
                listCallStatus = listCallStatus.concat(e + ",");
            }
            listCallStatus = listCallStatus.substring(0, listCallStatus.length() - 1);
            return listCallStatus;
        }
        return null;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CampaignDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Campaigns");
        return campaignRepository.findAll(pageable)
            .map(campaignMapper::toDto);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<CampaignDTO> findOne(Long id) {
        log.debug("Request to get Campaign : {}", id);
        return campaignRepository.findById(id)
            .map(campaignMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Campaign : {}", id);
        campaignRepository.deleteById(id);
    }

    @Override
    public Page<CampaignDTO> searchCampaign(CampaignDTO campaignDTO, Pageable pageable) {
        Optional<User> user = userService.getUserWithAuthorities();
        Number number = campaignDTO.getCampaignScriptId();
        Number number2 = campaignDTO.getCampaignTemplateId();
        Date instant = campaignDTO.getStartDate();
        Date instant2 = campaignDTO.getEndDate();
        System.out.println("aaaaaaâ "+instant);
        System.out.println("aaaaaaâ2 "+instant2);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String startDate = null;
        String endDate = null;
        if(instant != null){
            startDate = format.format(instant);
        }
        if(instant2 != null){
            endDate = format.format(instant2);
        }

        return campaignRepository.search(
            number,
            number2,
            startDate,
            endDate,
            campaignDTO.getStatus(),
            DataUtil.makeLikeParam(campaignDTO.getName()),
            pageable)
            .map(CampaignDTO::new);
    }



    @Override
    public Page<CampaignDTO> findAllCampaign(CampaignDTO campaignDTO, Pageable pageable) {
        return campaignRepository.findAllCampaign(pageable).map(CampaignDTO::new);
    }

    @Override
    public Page<CampaignDTO> findAllCampaigns(CampaignDTO campaignDTO, Pageable pageable) {

        CampaignSpec campaignSpec = null;
        Page<Campaign> campaigns = campaignRepository.findAll(campaignSpec ,pageable);
        List<CampaignDTO> campaignDTOS = campaigns.getContent().stream()
            .map(campaign -> campaignMapper.toDto(campaign))
            .collect(Collectors.toList());
        Page<CampaignDTO> sonlhDtos1 = new PageImpl<>(campaignDTOS, campaigns.getPageable(), campaigns.getTotalElements());
        return sonlhDtos1;
    }

    static class CampaignSpec implements Specification<Campaign> {
        private final CampaignDTO dto;

        CampaignSpec(CampaignDTO dto) {
            this.dto = dto;
        }

        @Override
        public Predicate toPredicate(Root<Campaign> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
            List<Predicate> predicates = new ArrayList<>();
            if (dto != null) {

                Date startDate = dto.getStartDate();
                if (startDate != null) {
                    Predicate predicate1 = criteriaBuilder.greaterThan(root.get(Campaign_.START_DATE), startDate);
                    predicates.add(predicate1);
                }
                Date endDate = dto.getEndDate();
                if (endDate != null) {
                    Predicate predicate2 = criteriaBuilder.lessThan(root.get(Campaign_.END_DATE), endDate);
                    predicates.add(predicate2);
                }
                Long scriptId = dto.getCampaignScriptId();
                if (scriptId != null) {
                    Predicate predicate3 = criteriaBuilder.equal(root.get(Campaign_.CAMPAIGN_SCRIPT_ID), scriptId);
                    predicates.add(predicate3);
                }
                Long templateId = dto.getCampaignTemplateId();
                if (templateId != null) {
                    Predicate predicate4 = criteriaBuilder.equal(root.get(Campaign_.CAMPAIGN_TEMPLATE_ID), templateId);
                    predicates.add(predicate4);
                }
                String name = dto.getName();
                if (name != null) {
                    Predicate predicate5 = criteriaBuilder.equal(root.get(Campaign_.NAME), name);
                    predicates.add(predicate5);
                }
                String status = dto.getStatus();
                if (status != null) {
                    Predicate predicate6 = criteriaBuilder.equal(root.get(Campaign_.STATUS), status);
                    predicates.add(predicate6);
                }
            }
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        }
    }
}
