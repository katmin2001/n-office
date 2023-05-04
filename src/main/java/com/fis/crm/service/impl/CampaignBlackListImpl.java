package com.fis.crm.service.impl;

import com.fis.crm.config.Constants;
import com.fis.crm.domain.CampaignBlacklist;
import com.fis.crm.domain.CampaignBlacklist_;
import com.fis.crm.domain.User;
import com.fis.crm.repository.CampaignBlackListRepository;
import com.fis.crm.service.ActionLogService;
import com.fis.crm.service.CampaignBlackListService;
import com.fis.crm.service.UserService;
import com.fis.crm.service.dto.ActionLogDTO;
import com.fis.crm.service.dto.CampaignBlackListDTO;
import com.fis.crm.service.dto.UserDTO;
import com.fis.crm.service.mapper.CampaignBlackListMapper;
import org.apache.commons.lang3.SerializationUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
public class CampaignBlackListImpl implements CampaignBlackListService {

    private final CampaignBlackListRepository campaignBlackListRepository;
    private final CampaignBlackListMapper campaignBlackListMapper;
    private final EntityManager entityManager;
    private final UserService userService;
    private final ActionLogService actionLogService;

    public CampaignBlackListImpl(CampaignBlackListRepository campaignBlackListRepository, CampaignBlackListMapper campaignBlackListMapper, EntityManager entityManager, UserService userService, ActionLogService actionLogService) {
        this.campaignBlackListRepository = campaignBlackListRepository;
        this.campaignBlackListMapper = campaignBlackListMapper;
        this.entityManager = entityManager;
        this.userService = userService;
        this.actionLogService = actionLogService;
    }

    @Override
    public Page<CampaignBlackListDTO> findCampaignBlackList(Long campaignId, String phoneNumber, Pageable pageable) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<CampaignBlacklist> criteria = cb.createQuery(CampaignBlacklist.class);
        Root<CampaignBlacklist> root = criteria.from(CampaignBlacklist.class);
        List<Predicate> predicates = new ArrayList<>();
        if (campaignId != null) {
            predicates.add(cb.equal(root.get(CampaignBlacklist_.CAMPAIGN_ID), campaignId));
        }
        if (phoneNumber != null) {
            predicates.add(cb.like(root.get(CampaignBlacklist_.PHONE_NUMBER), Constants.DEFAULT_CONTAINS_CHAR + phoneNumber + Constants.DEFAULT_CONTAINS_CHAR));
        }
        predicates.add(cb.equal(root.get(CampaignBlacklist_.STATUS), Constants.STATUS_ACTIVE));
        criteria.where(cb.and(predicates.toArray(new Predicate[0])));
        criteria.orderBy(cb.desc(root.get("createDate")));

        List<CampaignBlacklist> rs = entityManager.createQuery(criteria).setFirstResult((int) pageable.getOffset()).setMaxResults(pageable.getPageSize()).getResultList();
        rs.forEach(campaignBlacklist -> {
            Optional<UserDTO> userDTO = userService.findFirstById(campaignBlacklist.getCreateUser());
            if (userDTO.isPresent()){
                campaignBlacklist.setCreateUserName(userDTO.get().getFullName());
            }
        });
        CriteriaQuery<Long> countQuery = cb.createQuery(Long.class);
        Root<CampaignBlacklist> rootCount = countQuery.from(CampaignBlacklist.class);
        countQuery.select(cb.count(rootCount)).where(cb.and(predicates.toArray(new Predicate[predicates.size()])));

        Long count = entityManager.createQuery(countQuery).getSingleResult();
        List<CampaignBlackListDTO> rsDTOs = rs.stream().map(campaignBlackListMapper::toDto).collect(Collectors.toList());

        return new PageImpl<>(rsDTOs, pageable, count);
    }

    @Override
    public void save(CampaignBlackListDTO campaignBlackListDTO) throws Exception {
        Optional<User> user = userService.getUserWithAuthorities();
        CampaignBlacklist campaignBlacklist = campaignBlackListMapper.toEntity(campaignBlackListDTO);
        campaignBlacklist.setStatus(Constants.STATUS_ACTIVE);
        Instant currentDate = Instant.now();
        campaignBlacklist.setCreateDate(currentDate);
        campaignBlacklist.setCreateUser(user.get().getId());
        List<String> lstPhone = Arrays.asList(campaignBlackListDTO.getPhoneNumber().split(","));
        for (String phone :lstPhone){
            if (phone.equals(campaignBlackListRepository.phoneNumber(phone, campaignBlackListDTO.getCampaignId()))){
                throw new Exception("Thông tin đã tồn tại");
            }
        }
        List<String> collect = lstPhone.stream().distinct().collect(Collectors.toList());
        List<CampaignBlacklist> campaignBlacklists = collect.stream().map(phone -> {
            CampaignBlacklist campaign = SerializationUtils.clone(campaignBlacklist);
            campaign.setPhoneNumber(phone);
            return campaign;
        }).collect(Collectors.toList());
        Optional<User> userLog = userService.getUserWithAuthorities();
        actionLogService.saveActionLog(new ActionLogDTO(userLog.get().getId(), Constants.ACTION_LOG_TYPE.INSERT + "",
            null, String.format("Thêm mới danh sách đen gọi ra: [%s]",campaignBlackListDTO.getPhoneNumber()),
            new Date(), Constants.MENU_ID.OMS, Constants.MENU_ITEM_ID.campaign_blacklist, "CONFIG_MENU_ITEM"));
        campaignBlackListRepository.saveAll(campaignBlacklists);
    }

    @Override
    public CampaignBlacklist delete(Long id) {
        Optional<CampaignBlacklist> campaign = campaignBlackListRepository.findById(id);
        CampaignBlacklist campaignBlacklist = new CampaignBlacklist();
        if (campaign.isPresent()) {
            campaignBlacklist = campaign.get();
            campaignBlacklist.setStatus(Constants.STATUS_INACTIVE.toString());
        } else {
            throw new RuntimeException("Id not found");
        }
        campaignBlackListRepository.save(campaignBlacklist);
        return campaignBlacklist;
    }

    @Override
    public List<CampaignBlackListDTO> findAll() {
        return campaignBlackListRepository.findAll().stream().map(campaignBlackListMapper::toDto).collect(Collectors.toList());
    }

    @Override
    public void deleteAll(List<CampaignBlackListDTO> campaignBlackListDTOS) {
        List<CampaignBlacklist> campaignBlacklists = campaignBlackListMapper.toEntity(campaignBlackListDTOS);
        List<Long> listId = campaignBlacklists.stream().map(campaignBlacklist -> campaignBlacklist.getId()).collect(Collectors.toList());
        List<CampaignBlacklist> allId = campaignBlackListRepository.findAllById(listId);
        campaignBlackListRepository.deleteAll(allId);
        String phoneString = "";
        for (int i = 0; i < campaignBlackListDTOS.size(); i++){
            phoneString += campaignBlackListDTOS.get(i).getPhoneNumber() + ", ";
        }
        Optional<User> userLog = userService.getUserWithAuthorities();
        actionLogService.saveActionLog(new ActionLogDTO(userLog.get().getId(), Constants.ACTION_LOG_TYPE.DELETE + "",
            null, String.format("Xóa danh sách đen gọi ra: [%s]",phoneString),
            new Date(), Constants.MENU_ID.OMS, Constants.MENU_ITEM_ID.campaign_blacklist, "CONFIG_MENU_ITEM"));
    }
}
