package com.fis.crm.service.impl;

import com.fis.crm.commons.DataUtil;
import com.fis.crm.config.Constants;
import com.fis.crm.domain.CampaignResource;
import com.fis.crm.domain.CampaignResourceDetail;
import com.fis.crm.domain.User;
import com.fis.crm.repository.CampaignPerformInformationCustomRepository;
import com.fis.crm.repository.CampaignResourceDetailRepository;
import com.fis.crm.repository.CampaignResourceRepository;
import com.fis.crm.service.ActionLogService;
import com.fis.crm.service.CampaignResourceDetailService;
import com.fis.crm.service.UserService;
import com.fis.crm.service.dto.*;
import com.fis.crm.service.mapper.CampaignResourceDetailMapper;
import com.fis.crm.web.rest.errors.BusinessException;
import com.fis.crm.web.rest.errors.ErrorCodeResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link CampaignResourceDetail}.
 */
@Service
@Transactional
public class CampaignResourceDetailServiceImpl implements CampaignResourceDetailService {

    final
    CampaignPerformInformationCustomRepository campaignResourceDetailCustomRepository;

    private final Logger log = LoggerFactory.getLogger(CampaignResourceDetailServiceImpl.class);

    private final CampaignResourceDetailRepository campaignResourceDetailRepository;

    private final CampaignResourceDetailMapper campaignResourceDetailMapper;

    private final CampaignResourceRepository campaignResourceRepository;
    private final UserService userService;
    private final ActionLogService actionLogService;

    public CampaignResourceDetailServiceImpl(CampaignResourceDetailRepository campaignResourceDetailRepository, CampaignResourceDetailMapper campaignResourceDetailMapper, CampaignPerformInformationCustomRepository campaignResourceDetailCustomRepository,
                                             CampaignResourceRepository campaignResourceRepository, UserService userService, ActionLogService actionLogService) {
        this.campaignResourceDetailRepository = campaignResourceDetailRepository;
        this.campaignResourceDetailMapper = campaignResourceDetailMapper;
        this.campaignResourceDetailCustomRepository = campaignResourceDetailCustomRepository;
        this.campaignResourceRepository = campaignResourceRepository;
        this.userService = userService;
        this.actionLogService = actionLogService;
    }

    @Override
    public CampaignResourceDetailDTO save(CampaignResourceDetailDTO campaignResourceDetailDTO) {
        log.debug("Request to save CampaignResourceDetail : {}", campaignResourceDetailDTO);
        CampaignResourceDetail campaignResourceDetail = campaignResourceDetailMapper.toEntity(campaignResourceDetailDTO);
        campaignResourceDetail = campaignResourceDetailRepository.save(campaignResourceDetail);
        return campaignResourceDetailMapper.toDto(campaignResourceDetail);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CampaignResourceDetailDTO> findAll(Pageable pageable) {
        log.debug("Request to get all CampaignResourceDetails");
        return campaignResourceDetailRepository.findAll(pageable)
            .map(campaignResourceDetailMapper::toDto);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<CampaignResourceDetailDTO> findOne(Long id) {
        log.debug("Request to get CampaignResourceDetail : {}", id);
        return campaignResourceDetailRepository.findById(id)
            .map(campaignResourceDetailMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete CampaignResourceDetail : {}", id);
        campaignResourceDetailRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Long> getNumberCampaignResourceDetailsNotGroup(Long id) {
        return campaignResourceDetailRepository.countAllByCampaignResourceIdAndGroupIdIsNull(id);
    }

    @Override
    public boolean groupCampaignResourceDetails(GeneralCampaignDTO generalCampaignDTO) throws Exception {
        List<GroupUserDTO> lstGroupDto = generalCampaignDTO.getLstGroupDto();
        if (lstGroupDto != null && !lstGroupDto.isEmpty()) {
            int totalData = lstGroupDto.stream().mapToInt(item -> {
                if (item.getNumberData() == null || item.getNumberData().longValue() < 0)
                    return 0;
                return item.getNumberData().intValue();
            }).sum();
            if(totalData <= 0) {
                return true;
            }
            Page<CampaignResourceDetail> campaignResourceDetailPage = campaignResourceDetailRepository.findByCampaignResourceIdAndGroupIdIsNull(generalCampaignDTO.getCampaignResourceId(), PageRequest.of(0, totalData));
            if (campaignResourceDetailPage.getTotalElements() < totalData)
                throw new BusinessException(ErrorCodeResponse.CAMPAIGN_ASSIGN_OVERLOAD);
            List<CampaignResourceDetail> lstTmp = campaignResourceDetailPage.getContent();
            for (int i = 0; i < lstTmp.size(); ) {
                for (int j = 0; j < lstGroupDto.size(); j++) {
                    GroupUserDTO groupUserDTO = lstGroupDto.get(j);
                    Long numberData = groupUserDTO.getNumberData();
                    if (numberData == null || numberData.longValue() < 0) continue;
                    for (int k = 0; k < numberData; k++) {
                        lstTmp.get(i).setGroupId(groupUserDTO.getId());
                        i++;
                    }
                }
            }
            campaignResourceDetailRepository.saveAll(lstTmp);

            //update waiting_share for table campaign_resource
            CampaignResource resource = campaignResourceRepository.getOne(generalCampaignDTO.getCampaignResourceId());
            resource.setWaitingShare(resource.getWaitingShare() - totalData);
        }
        return true;
    }

    @Override
    public boolean groupNotDoneCampaignResourceDetails(GeneralCampaignDTO generalCampaignDTO) throws Exception {
        List<GroupUserDTO> lstGroupDto = generalCampaignDTO.getLstGroupDto();
        if (lstGroupDto != null && !lstGroupDto.isEmpty()) {
            int totalData = lstGroupDto.stream().mapToInt(item -> {
                if (item.getNumberData() == null || item.getNumberData().longValue() < 0)
                    return 0;
                return item.getNumberData().intValue();
            }).sum();
            if(totalData <= 0) {
                return true;
            }
            Page<CampaignResourceDetail> campaignResourceDetailPage = campaignResourceDetailRepository.findByCampaignResourceIdAndErrorStatus(generalCampaignDTO.getCampaignResourceId(), Constants.STATUS_ACTIVE, PageRequest.of(0, totalData));
            if (campaignResourceDetailPage.getTotalElements() < totalData)
                throw new BusinessException(ErrorCodeResponse.CAMPAIGN_ASSIGN_OVERLOAD);
            List<CampaignResourceDetail> lstTmp = campaignResourceDetailPage.getContent();
            for (int i = 0; i < lstTmp.size(); ) {
                for (int j = 0; j < lstGroupDto.size(); j++) {
                    GroupUserDTO groupUserDTO = lstGroupDto.get(j);
                    Long numberData = groupUserDTO.getNumberData();
                    if (numberData == null || numberData.longValue() < 0) continue;
                    for (int k = 0; k < numberData; k++) {
                        lstTmp.get(i).setGroupId(groupUserDTO.getId());
                        lstTmp.get(i).setErrorStatus("");
                        i++;
                    }
                }
            }
            campaignResourceDetailRepository.saveAll(lstTmp);

            //update waiting_share for table campaign_resource
            CampaignResource resource = campaignResourceRepository.getOne(generalCampaignDTO.getCampaignResourceId());
            resource.setCallNotDone(resource.getCallNotDone() - totalData);
        }
        return true;
    }

    @Override
    @Transactional(readOnly = true)
    public List<CampaignResourceGeneralDTO> getCampaignGroupDetails(Long campaignId) throws Exception {
        List<CampaignResourceGeneralDTO> result = new ArrayList<>();
        if(campaignId==null){
            campaignId=-1l;
        }
        List<Object[]> lstObject = campaignResourceDetailRepository.getCampaignGroupDetails(campaignId);

        for (Object[] object : lstObject) {
            CampaignResourceGeneralDTO campaignResourceGeneralDTO = new CampaignResourceGeneralDTO();
            int index = -1;
            campaignResourceGeneralDTO.setGroupId(DataUtil.safeToLong(object[++index]));
            campaignResourceGeneralDTO.setGroupName(DataUtil.safeToString(object[++index]));
            campaignResourceGeneralDTO.setReceived(DataUtil.safeToLong(object[++index]));
            campaignResourceGeneralDTO.setYetAssign(DataUtil.safeToLong(object[++index]));
            campaignResourceGeneralDTO.setAssigned(DataUtil.safeToLong(object[++index]));
            campaignResourceGeneralDTO.setCalled(DataUtil.safeToLong(object[++index]));
            campaignResourceGeneralDTO.setYetCall(DataUtil.safeToLong(object[++index]));
            campaignResourceGeneralDTO.setNotDoneCall(DataUtil.safeToLong(object[++index]));
            result.add(campaignResourceGeneralDTO);
        }
        return result;
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Long> getCampaignGroupDetailsNotUser(Long campaignId, Long groupId) {
        return campaignResourceDetailRepository.countAllByCampaignIdAndGroupIdAndAssignUserIdIsNull(campaignId, groupId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CampaignResourceGeneralDTO> getCampaignUserDetails(CampaignResourceDetailDTO campaignResourceGeneral) throws Exception {
        List<CampaignResourceGeneralDTO> result = new ArrayList<>();
        List<Object[]> lstObject = campaignResourceDetailRepository.getCampaignUserDetails(campaignResourceGeneral.getCampaignId(), campaignResourceGeneral.getGroupId(),
            !StringUtils.hasLength(campaignResourceGeneral.getSearch()) ? "-1" :
            DataUtil.makeLikeQuery(campaignResourceGeneral.getSearch()));

        for (Object[] object : lstObject) {
            CampaignResourceGeneralDTO campaignResourceGeneralDTO = new CampaignResourceGeneralDTO();
            int index = -1;
            campaignResourceGeneralDTO.setAssignUserId(DataUtil.safeToLong(object[++index]));
            campaignResourceGeneralDTO.setUserName(DataUtil.safeToString(object[++index]));
            campaignResourceGeneralDTO.setReceived(DataUtil.safeToLong(object[++index]));
            campaignResourceGeneralDTO.setCalled(DataUtil.safeToLong(object[++index]));
            campaignResourceGeneralDTO.setYetCall(DataUtil.safeToLong(object[++index]));
            campaignResourceGeneralDTO.setNotDoneCall(DataUtil.safeToLong(object[++index]));
            result.add(campaignResourceGeneralDTO);
        }
        return result;

    }

    @Override
    public boolean assignCampaignUser(GeneralCampaignDTO generalCampaignDTO) throws Exception {
        List<UserDTO> lstUserDto = generalCampaignDTO.getLstUserDto();
        if (lstUserDto != null && !lstUserDto.isEmpty()) {
            int totalData = lstUserDto.stream().mapToInt(item -> {
                if (item.getNumberData() == null || item.getNumberData().longValue() < 0)
                    return 0;
                return item.getNumberData().intValue();
            }).sum();
            if(totalData <= 0) {
                return true;
            }
            Page<CampaignResourceDetail> campaignResourceDetailPage = campaignResourceDetailRepository.findByCampaignIdAndGroupIdAndAssignUserIdIsNull(generalCampaignDTO.getCampaignId(),
                generalCampaignDTO.getGroupId(),
                PageRequest.of(0, totalData));
            if (campaignResourceDetailPage.getTotalElements() < totalData)
                throw new BusinessException(ErrorCodeResponse.CAMPAIGN_ASSIGN_OVERLOAD);
            List<CampaignResourceDetail> lstTmp = campaignResourceDetailPage.getContent();
            for (int i = 0; i < lstTmp.size(); ) {
                for (int j = 0; j < lstUserDto.size(); j++) {
                    UserDTO userDTO = lstUserDto.get(j);
                    Long numberData = userDTO.getNumberData();
                    if (numberData == null || numberData.longValue() < 0) continue;
                    for (int k = 0; k < numberData; k++) {
                        lstTmp.get(i).setAssignUserId(userDTO.getId());
                        i++;
                    }
                }
            }
            Optional<User> userLog = userService.getUserWithAuthorities();
            actionLogService.saveActionLog(new ActionLogDTO(userLog.get().getId(), Constants.ACTION_LOG_TYPE.UPDATE + "",
                null, "Phân công nguồn dữ liệu",
                new Date(), Constants.MENU_ID.OMS, Constants.MENU_ITEM_ID.campaign_group_user, "CONFIG_MENU_ITEM"));
            campaignResourceDetailRepository.saveAll(lstTmp);
        }
        return true;
    }

    @Override
    public boolean restoreCampaignGroup(GeneralCampaignDTO generalCampaignDTO) throws Exception {
        UserDTO userDTO = generalCampaignDTO.getUserDTO();
        if (userDTO != null && userDTO.getNumberData() != null && userDTO.getNumberData().longValue() > 0) {
            int totalData = userDTO.getNumberData().intValue();
            Page<CampaignResourceDetail> campaignResourceDetailPage = campaignResourceDetailRepository.findByCampaignIdAndGroupIdAndAssignUserIdAndCallStatus(generalCampaignDTO.getCampaignId(),
                generalCampaignDTO.getGroupId(),
                generalCampaignDTO.getUserId(),
                Constants.CALL_STATUS.YET_CALL,
                PageRequest.of(0, totalData));
            if (campaignResourceDetailPage.getTotalElements() < totalData)
                throw new BusinessException(ErrorCodeResponse.CAMPAIGN_ASSIGN_OVERLOAD);
            List<CampaignResourceDetail> lstTmp = campaignResourceDetailPage.getContent();
            for (CampaignResourceDetail campaignResourceDetail : lstTmp) {
                campaignResourceDetail.setAssignUserId(null);
            }
            Optional<User> userLog = userService.getUserWithAuthorities();
            actionLogService.saveActionLog(new ActionLogDTO(userLog.get().getId(), Constants.ACTION_LOG_TYPE.UPDATE + "",
                null, "Trả dữ liệu chưa gọi về nhóm",
                new Date(), Constants.MENU_ID.OMS, Constants.MENU_ITEM_ID.campaign_group_user, "CONFIG_MENU_ITEM"));
            campaignResourceDetailRepository.saveAll(lstTmp);
        }
        return true;
    }

    @Override
    public boolean restoreNotDoneCampaignGroup(GeneralCampaignDTO generalCampaignDTO) throws Exception {
        UserDTO userDTO = generalCampaignDTO.getUserDTO();
        if (userDTO != null && userDTO.getNumberData() != null && userDTO.getNumberData().longValue() > 0) {
            int totalData = userDTO.getNumberData().intValue();
            Page<CampaignResourceDetail> campaignResourceDetailPage = campaignResourceDetailRepository.findByCampaignIdAndGroupIdAndAssignUserIdAndErrorStatus(generalCampaignDTO.getCampaignId(),
                generalCampaignDTO.getGroupId(),
                generalCampaignDTO.getUserId(),
                Constants.STATUS_ACTIVE,
                PageRequest.of(0, totalData));
            if (campaignResourceDetailPage.getTotalElements() < totalData)
                throw new BusinessException(ErrorCodeResponse.CAMPAIGN_ASSIGN_OVERLOAD);
            List<CampaignResourceDetail> lstTmp = campaignResourceDetailPage.getContent();
            for (CampaignResourceDetail campaignResourceDetail : lstTmp) {
                campaignResourceDetailRepository.resetAssignUser(campaignResourceDetail.getId());
            }
            Optional<User> userLog = userService.getUserWithAuthorities();
            actionLogService.saveActionLog(new ActionLogDTO(userLog.get().getId(), Constants.ACTION_LOG_TYPE.UPDATE + "",
                null, "Trả dữ liệu chưa hoàn thành gọi ra về nhóm",
                new Date(), Constants.MENU_ID.OMS, Constants.MENU_ITEM_ID.campaign_group_user, "CONFIG_MENU_ITEM"));


        }
        return true;
    }

    @Override
    public boolean restoreCampaignResource(GeneralCampaignDTO generalCampaignDTO) throws Exception {
        GroupUserDTO groupUser = generalCampaignDTO.getGroupUserDTO();
        if (groupUser != null && groupUser.getNumberData() != null && groupUser.getNumberData().longValue() > 0) {
            int totalData = groupUser.getNumberData().intValue();
            Page<CampaignResourceDetail> campaignResourceDetailPage = campaignResourceDetailRepository.findByGroupIdAndCallStatus(generalCampaignDTO.getGroupId(),
                Constants.CALL_STATUS.YET_CALL,
                PageRequest.of(0, totalData));
            if (campaignResourceDetailPage.getTotalElements() < totalData)
                throw new BusinessException(ErrorCodeResponse.CAMPAIGN_ASSIGN_OVERLOAD);
            List<CampaignResourceDetail> lstCampaignResourceDetail = campaignResourceDetailPage.getContent();
            for (CampaignResourceDetail campaignResourceDetail : lstCampaignResourceDetail) {
                campaignResourceDetail.setAssignUserId(null);
                campaignResourceDetail.setGroupId(null);
            }
            Optional<User> userLog = userService.getUserWithAuthorities();
            actionLogService.saveActionLog(new ActionLogDTO(userLog.get().getId(), Constants.ACTION_LOG_TYPE.UPDATE + "",
                null, "Trả dữ liệu chưa gọi về nguồn chiến dịch",
                new Date(), Constants.MENU_ID.OMS, Constants.MENU_ITEM_ID.campaign_group_user, "CONFIG_MENU_ITEM"));
            campaignResourceDetailRepository.saveAll(lstCampaignResourceDetail);
        }
        return true;
    }

    @Override
    public boolean restoreNotDoneCampaignResource(GeneralCampaignDTO generalCampaignDTO) throws Exception {
        GroupUserDTO groupUser = generalCampaignDTO.getGroupUserDTO();
        if (groupUser != null && groupUser.getNumberData() != null && groupUser.getNumberData().longValue() > 0) {
            int totalData = groupUser.getNumberData().intValue();
            Page<CampaignResourceDetail> campaignResourceDetailPage = campaignResourceDetailRepository.findByGroupIdAndErrorStatus(generalCampaignDTO.getGroupId(),
                Constants.STATUS_ACTIVE,
                PageRequest.of(0, totalData));
            if (campaignResourceDetailPage.getTotalElements() < totalData)
                throw new BusinessException(ErrorCodeResponse.CAMPAIGN_ASSIGN_OVERLOAD);
            List<CampaignResourceDetail> lstCampaignResourceDetail = campaignResourceDetailPage.getContent();
            for (CampaignResourceDetail campaignResourceDetail : lstCampaignResourceDetail) {
                campaignResourceDetailRepository.resetAssignUserAndGroup(campaignResourceDetail.getId());
            }
            Optional<User> userLog = userService.getUserWithAuthorities();
            actionLogService.saveActionLog(new ActionLogDTO(userLog.get().getId(), Constants.ACTION_LOG_TYPE.UPDATE + "",
                null, "Trả dữ liệu chưa hoàn thành gọi ra về nguồn chiến dịch",
                new Date(), Constants.MENU_ID.OMS, Constants.MENU_ITEM_ID.campaign_group_user, "CONFIG_MENU_ITEM"));

        }
        return true;
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Long> getNoCampaignGroupYetCallForUser(Long campaignResourceId, Long groupId, Long assignUserId) throws Exception {
        return campaignResourceDetailRepository.countAllByCampaignResourceIdAndGroupIdAndAssignUserIdAndCallStatus(campaignResourceId, groupId, assignUserId, Constants.CALL_STATUS.YET_CALL);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Long> getNoCampaignYetCallForUser(Long groupId) throws Exception {
        return campaignResourceDetailRepository.countAllByGroupIdAndCallStatus(groupId, Constants.CALL_STATUS.YET_CALL);
    }

    @Override
    public CampaignResourceDetailDTO getCampaignResourceFromCID(String cid) {
        CampaignResourceDetail campaignResourceDetail = campaignResourceDetailRepository.findByCID(cid);
        return campaignResourceDetailMapper.toDto(campaignResourceDetail);
    }
}
