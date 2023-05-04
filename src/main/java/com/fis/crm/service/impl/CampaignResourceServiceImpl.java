package com.fis.crm.service.impl;

import com.fis.crm.commons.DataUtil;
import com.fis.crm.commons.ExcelUtils;
import com.fis.crm.commons.ExportUtils;
import com.fis.crm.commons.Translator;
import com.fis.crm.config.Constants;
import com.fis.crm.domain.*;
import com.fis.crm.repository.*;
import com.fis.crm.security.SecurityUtils;
import com.fis.crm.service.ActionLogService;
import com.fis.crm.service.CampaignResourceService;
import com.fis.crm.service.UserService;
import com.fis.crm.service.dto.*;
import com.fis.crm.service.mapper.CampaignResourceDetailMapper;
import com.fis.crm.service.mapper.CampaignResourceErrorMapper;
import com.fis.crm.service.mapper.CampaignResourceMapper;
import com.fis.crm.web.rest.errors.BadRequestAlertException;
import org.apache.commons.io.FilenameUtils;
import org.apache.poi.ss.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.sql.DataSource;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@Transactional
public class CampaignResourceServiceImpl implements CampaignResourceService {


    private final CampaignResourceDetailRepository campaignResourceDetailRepository;

    @Autowired
    private RecordCallResultCustomRepository recordCallResultCustomRepository;

    private final CampaignResourceRepository campaignResourceRepository;
    private final CampaignResourceMapper campaignResourceMapper;
    private final CampaignRepository campaignRepository;
    private final CampaignResourceErrorRepository campaignResourceErrorRepository;
    private final CampaignResourceDetailMapper campaignResourceDetailMapper;
    private final CampaignResourceErrorMapper campaignResourceErrorMapper;
    private final CampaignResourceTemplateRepository campaignResourceTemplateRepository;
    private final CampaignTemplateOptionRepository campaignTemplateOptionRepository;
    private final CampaignBlackListRepository campaignBlackListRepository;
    private final ExportUtils exportUtils;
    private final ExcelUtils excelUtils;
    private final DataSource dataSource;
    private final CampaignResourceTemplateErrRepository campaignResourceTemplateErrRepository;
    private final UserService userService;
    private final ActionLogService actionLogService;

    private final CampaignResourceDetailCustomRepository campaignResourceDetailCustomRepository;

    public CampaignResourceServiceImpl(CampaignResourceDetailRepository campaignResourceDetailRepository,
                                       CampaignResourceRepository campaignResourceRepository,
                                       CampaignResourceMapper campaignResourceMapper,
                                       CampaignRepository campaignRepository,
                                       CampaignResourceErrorRepository campaignResourceErrorRepository,
                                       CampaignResourceDetailMapper campaignResourceDetailMapper,
                                       CampaignResourceErrorMapper campaignResourceErrorMapper,
                                       CampaignResourceTemplateRepository campaignResourceTemplateRepository,
                                       CampaignBlackListRepository campaignBlackListRepository,
                                       ExportUtils exportUtils,
                                       ExcelUtils excelUtils,
                                       DataSource dataSource,
                                       CampaignTemplateOptionRepository campaignTemplateOptionRepository,
                                       CampaignResourceDetailCustomRepository campaignResourceDetailCustomRepository,
                                       CampaignResourceTemplateErrRepository campaignResourceTemplateErrRepository, UserService userService, ActionLogService actionLogService) {
        this.campaignResourceDetailRepository = campaignResourceDetailRepository;
        this.campaignResourceRepository = campaignResourceRepository;
        this.campaignResourceMapper = campaignResourceMapper;
        this.campaignRepository = campaignRepository;
        this.campaignResourceErrorRepository = campaignResourceErrorRepository;
        this.campaignResourceDetailMapper = campaignResourceDetailMapper;
        this.campaignResourceErrorMapper = campaignResourceErrorMapper;
        this.campaignResourceTemplateRepository = campaignResourceTemplateRepository;
        this.campaignBlackListRepository = campaignBlackListRepository;
        this.exportUtils = exportUtils;
        this.excelUtils = excelUtils;
        this.dataSource = dataSource;
        this.campaignTemplateOptionRepository = campaignTemplateOptionRepository;
        this.campaignResourceTemplateErrRepository = campaignResourceTemplateErrRepository;
        this.campaignResourceDetailCustomRepository = campaignResourceDetailCustomRepository;
        this.userService = userService;
        this.actionLogService = actionLogService;
    }

    @Override
    public List<CampaignResourceDTO> findAllByStatusAndCampaignId(Long campaign_id) {
        try {
            List<CampaignResource> campaignResources = campaignResourceRepository
                .findAllByStatusAndCampaignIdOrderByResourceNameAsc(Constants.STATUS_ACTIVE, campaign_id);
            return campaignResourceMapper.toDto(campaignResources);
        } catch (Exception e) {
            throw new BadRequestAlertException("error", "", "");
        }

    }

    @Override
    public List<CampaignResourceDTO> findAll() {
        try {
            List<CampaignResource> campaignResources = campaignResourceRepository
                .findAllByStatusOrderByResourceNameAsc(Constants.STATUS_ACTIVE);
            return campaignResourceMapper.toDto(campaignResources);
        } catch (Exception e) {
            throw new BadRequestAlertException("error", "", "");
        }

    }

    @Override
    public Page<CampaignResourceDTO> search(Long campaignId, String fromDate, String toDate, Pageable pageable) {
        Page<Object[]> page = campaignResourceRepository.search(campaignId,
            fromDate.equals("") ? 0L : 1L, fromDate, toDate.equals("") ? 0L : 1L, toDate, pageable);
        List<Object[]> objectLst = page.getContent();
        List<CampaignResourceDTO> campaignResourceDTOList = new ArrayList<>();
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        for (Object[] object : objectLst) {
            CampaignResourceDTO campaignResourceDTO = new CampaignResourceDTO();
            int index = -1;
            campaignResourceDTO.setId(DataUtil.safeToLong(object[++index]));
            campaignResourceDTO.setCampaignId(DataUtil.safeToLong(object[++index]));
            campaignResourceDTO.setResourceName(DataUtil.safeToString(object[++index]));
            campaignResourceDTO.setTotalNotDistinct(DataUtil.safeToLong(object[++index]));
            campaignResourceDTO.setDistinctData(DataUtil.safeToString(object[++index]));
            campaignResourceDTO.setTotal(DataUtil.safeToLong(object[++index]));
            campaignResourceDTO.setCalled(DataUtil.safeToLong(object[++index]));
            campaignResourceDTO.setWaitingCall(DataUtil.safeToLong(object[++index]));
            campaignResourceDTO.setWaitingShare(DataUtil.safeToLong(object[++index]));
            campaignResourceDTO.setCreateDateTime(DataUtil.safeToInstant(object[++index]));
            campaignResourceDTO.setCreateUser(DataUtil.safeToLong(object[++index]));
            campaignResourceDTO.setStatus(DataUtil.safeToString(object[++index]));
            campaignResourceDTO.setCreateUserName(DataUtil.safeToString(object[++index]));
            campaignResourceDTO.setCallNotDone(DataUtil.safeToLong(object[++index]));
            Date date = Date.from(campaignResourceDTO.getCreateDateTime());
            String dateView = format.format(date);
            campaignResourceDTO.setCreateDateTimeView(dateView);
            Optional<Campaign> campaignOptional = campaignRepository.findById(campaignResourceDTO.getCampaignId());
            if (campaignOptional.isPresent()) {
                campaignResourceDTO.setCampaignName(campaignOptional.get().getName());
            }
            campaignResourceDTOList.add(campaignResourceDTO);
        }
        return new PageImpl<>(campaignResourceDTOList, pageable, page.getTotalElements());
    }

    @Override
    public List<ComboDTO> getCampaignCombo() {
        List<Object[]> objects = campaignResourceRepository.getLstCampaign();
        List<ComboDTO> comboDTOList = new ArrayList<>();

        if (!objects.isEmpty()) {
            for (Object[] object : objects) {
                ComboDTO comboDTO = new ComboDTO();
                comboDTO.setLabel(DataUtil.safeToString(object[1]));
                comboDTO.setValue(DataUtil.safeToString(object[0]));
                comboDTOList.add(comboDTO);
            }
        } else {
            return null;
        }

        return comboDTOList;
    }

    @Override
    public Long uploadFile(MultipartFile file, Long campaignId) {
        try {
            String extension = FilenameUtils.getExtension(file.getOriginalFilename());
            if (extension != null && !extension.equalsIgnoreCase("xlsx") && !extension.equalsIgnoreCase("xls")) {
                throw new BadRequestAlertException("File import không đúng định dạng file excel", "", "");
            }
            List<ListTemplateDTO> lst = readTotalData(file, campaignId);
            if (lst.isEmpty()) {
                throw new BadRequestAlertException("File không có dữ liệu", "", "");
            }
            int count = 0;
            for (ListTemplateDTO dto : lst) {
                if (!dto.getLsTemplate().isEmpty()) {
                    count++;
                }
            }
            Optional<User> userLog = userService.getUserWithAuthorities();
            actionLogService.saveActionLog(new ActionLogDTO(userLog.get().getId(), Constants.ACTION_LOG_TYPE.INSERT + "",
                null, String.format("Upload: Nguồn dữ liệu chiến dịch [%s]", file.getOriginalFilename()),
                new Date(), Constants.MENU_ID.OMS, Constants.MENU_ITEM_ID.campaign_resource, "CONFIG_MENU_ITEM"));
            return (long) count;
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public Map<String, Long> importFile(MultipartFile file, Long campaignId, Long distinct) {
        try {
            List<ListTemplateDTO> lst = readTotalData(file, campaignId);
            List<CampaignResource> campaignResourceList = new ArrayList<>();
            List<String> phoneNumberLst = new ArrayList<>();
            List<String> phoneNumberLstAll = new ArrayList<>();
            List<CampaignResourceDetail> campaignResourceDetailList = new ArrayList<>();
            Optional<Campaign> campaignOptional = campaignRepository.findById(campaignId);
            Map<String, List<ListTemplateDTO>> map = new HashMap<>();
            List<ListTemplateDTO> lstSuccess = new ArrayList<>();
            List<ListTemplateDTO> lstError = new ArrayList<>();
            List<ListTemplateDTO> lstDuplicate = new ArrayList<>();
            List<ListTemplateDTO> lstBlackList = new ArrayList<>();
            Long campainResourceId = 0L;

            if (campaignOptional.isPresent()) {
                campaignResourceList = campaignResourceRepository.findByCampaignId(campaignId);
            }
            if (!campaignResourceList.isEmpty()) {
                for (CampaignResource campaignResource : campaignResourceList) {
                    campaignResourceDetailList = campaignResourceDetailRepository.findByCampaignResourceId(campaignResource.getId());
                    if (!campaignResourceDetailList.isEmpty()) {
                        for (CampaignResourceDetail campaignResourceDetail : campaignResourceDetailList) {
                            phoneNumberLst.add(campaignResourceDetail.getPhoneNumber());
                        }
                    }
                }
            }

            List<CampaignResource> campaignResourceLstAll = campaignResourceRepository.findByStatus(Constants.STATUS_ACTIVE);
            if (!campaignResourceLstAll.isEmpty()) {
                for (CampaignResource campaignResource : campaignResourceLstAll) {
                    campaignResourceDetailList = campaignResourceDetailRepository.findByCampaignResourceId(campaignResource.getId());
                    if (!campaignResourceDetailList.isEmpty()) {
                        for (CampaignResourceDetail campaignResourceDetail : campaignResourceDetailList) {
                            phoneNumberLstAll.add(campaignResourceDetail.getPhoneNumber());
                        }
                    }
                }
            }

            map = handleData(lst, phoneNumberLst, phoneNumberLstAll, distinct, campaignId);
            for (Map.Entry<String, List<ListTemplateDTO>> entry : map.entrySet()) {
                if (entry.getKey().equalsIgnoreCase("success")) {
                    lstSuccess = entry.getValue();
                } else if (entry.getKey().equalsIgnoreCase("error")) {
                    lstError = entry.getValue();
                } else if (entry.getKey().equalsIgnoreCase("duplicate")) {
                    lstDuplicate = entry.getValue();
                } else if (entry.getKey().equalsIgnoreCase("blackList")) {
                    lstBlackList = entry.getValue();
                }
            }

            List<Object[]> objects = campaignResourceRepository.getUserLogin(SecurityUtils.getCurrentUserLogin().get());
            Long userId = -1L;

            if (!objects.isEmpty()) {
                userId = DataUtil.safeToLong(objects.get(0)[0]);
            }


            CampaignResource campaignResource = new CampaignResource();
            campaignResource.setResourceName(file.getOriginalFilename());
            campaignResource.setCreateDateTime(Instant.now());
            campaignResource.setCampaignId(campaignId);
            int total = lstSuccess.size();

            campaignResource.setTotal((long) total);
            campaignResource.setWaitingCall((long) total);
            campaignResource.setWaitingShare((long) total);
            campaignResource.setCreateUser(userId);
            campaignResource.setStatus("1");
            try {
                campaignResource = campaignResourceRepository.save(campaignResource);
                campainResourceId = campaignResource.getId();
            } catch (Exception e) {
                throw new BadRequestAlertException(Translator.toLocale("campaign-resource.import.error"), "", "");
            }

            Long count = 0L;
            CampaignResourceDetail campaignResourceDetail = new CampaignResourceDetail();
            int idx = -1;
            for (ListTemplateDTO row : lstSuccess) {
                List<DataReadDynamicDTO> lsRow = row.getLsTemplate();
                for (DataReadDynamicDTO dto : lsRow) {
                    if ("MobilePhone".equals(dto.getCode())) {
                        idx = dto.getIdx();
                        campaignResourceDetail = new CampaignResourceDetail();
                        campaignResourceDetail.setCampaignResourceId(campaignResource.getId());
                        campaignResourceDetail.setPhoneNumber(dto.getPhoneNumber());
                        campaignResourceDetail.setCreateUser(userId);
                        campaignResourceDetail.setCreateDateTime(new Date());
                        campaignResourceDetail.setStatus(Constants.STATUS_ACTIVE);
                        campaignResourceDetail.setCampaignId(campaignId);
                        campaignResourceDetail.setCallStatus(Constants.CALL_STATUS.YET_CALL);
                        try {
                            campaignResourceDetail = campaignResourceDetailRepository.save(campaignResourceDetail);
                        } catch (Exception e) {
                            throw new BadRequestAlertException(Translator.toLocale("campaign-resource.import.error"), "", "");
                        }
                        for (DataReadDynamicDTO dtoDetail : lsRow) {
                            CampaignResourceTemplate campaignResourceTemplate = new CampaignResourceTemplate();
                            campaignResourceTemplate.setCampaignId(campaignId);
                            campaignResourceTemplate.setCampaignResourceId(campaignResource.getId());
                            campaignResourceTemplate.setCampaignResourceDetailId(campaignResourceDetail.getId());
                            campaignResourceTemplate.setType(dtoDetail.getType());
                            campaignResourceTemplate.setCode(dtoDetail.getCode());
                            campaignResourceTemplate.setName(dtoDetail.getName());
                            campaignResourceTemplate.setOrd(dtoDetail.getCell() + 1);
                            campaignResourceTemplate.setValue(dtoDetail.getPhoneNumber());
                            campaignResourceTemplate.setEditable(dtoDetail.getEditAble());
                            try {
                                campaignResourceTemplate = campaignResourceTemplateRepository.save(campaignResourceTemplate);
                            } catch (Exception e) {
                                throw new BadRequestAlertException(Translator.toLocale("campaign-resource.import.error"), "", "");
                            }
                            if (!"".equals(dtoDetail.getOptionValue())) {
                                String data[] = dtoDetail.getOptionValue().split(",");
                                for (int i = 0; i < data.length; i++) {
                                    CampaignTemplateOption option = new CampaignTemplateOption();
                                    option.setCampaignTemplateId(campaignResourceTemplate.getId());
                                    option.setCode(data[0]);
                                    option.setName(data[1]);
                                    option.setStatus("1");
                                    try {
                                        campaignTemplateOptionRepository.save(option);
                                    } catch (Exception e) {
                                        throw new BadRequestAlertException(Translator.toLocale("campaign-resource.import.error"), "", "");
                                    }
                                }
                            }

                        }
                        count++;
                    }

                }
            }
            if (!lstError.isEmpty()) {
                for (ListTemplateDTO dto : lstError) {
                    saveCampaignResourceError(dto, campaignResource, userId, "Số điện thoại sai định dạng");
                }
            }
            if (!lstDuplicate.isEmpty()) {
                for (ListTemplateDTO dto : lstDuplicate) {
                    saveCampaignResourceError(dto, campaignResource, userId, "Số điện thoại trùng");
                }
            }
            if (!lstBlackList.isEmpty()) {
                for (ListTemplateDTO dto : lstBlackList) {
                    saveCampaignResourceError(dto, campaignResource, userId, "Số điện thoại danh sách đen");
                }
            }

            Map<String, Long> resultMap = new HashMap<>();
            resultMap.put("total", uploadFile(file, campaignId));
            resultMap.put("success", count);
            resultMap.put("error", (long) lstError.size());
            resultMap.put("duplicate", (long) lstDuplicate.size());
            resultMap.put("blackList", (long) lstBlackList.size());
            resultMap.put("campainResourceId", campainResourceId);
            resultMap.put("campainScriptId", campaignOptional.get().getCampaignScriptId());


            return resultMap;
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public void changeStatus(Long id, String status) {
        Optional<CampaignResource> campaignResourceOptional = campaignResourceRepository.findById(id);
        if (campaignResourceOptional.isPresent()) {
            campaignResourceOptional.get().setStatus(status);
            campaignResourceRepository.save(campaignResourceOptional.get());
        }
        List<CampaignResourceDetail> lstDetail = campaignResourceDetailRepository.findByCampaignResourceId(id);
        for (CampaignResourceDetail detail : lstDetail) {
            detail.setStatus(status);
            campaignResourceDetailRepository.save(detail);
        }
        Optional<CampaignResource> campaignResource = campaignResourceRepository.findById(id);
        Optional<Campaign> campaign = campaignRepository.findById(campaignResource.get().getCampaignId());
        Optional<User> userLog = userService.getUserWithAuthorities();
        actionLogService.saveActionLog(new ActionLogDTO(userLog.get().getId(), Constants.ACTION_LOG_TYPE.UPDATE + "",
            null, String.format("Nguồn dữ liệu chiến dịch: Thay đổi trạng thái chiến dịch [%s]", campaign.get().getName()),
            new Date(), Constants.MENU_ID.OMS, Constants.MENU_ITEM_ID.campaign_resource, "CONFIG_MENU_ITEM"));
    }

    @Override
    public ByteArrayInputStream exportCampaignResourceDetail(Long campaignResourceId) {
        Optional<CampaignResource> campaignResourceOptional = campaignResourceRepository.findById(campaignResourceId);
        if (campaignResourceOptional.isPresent()) {
            Long campaignId = campaignResourceOptional.get().getCampaignId();
//            List<CampaignResourceDetail> campaignResourceDetailList = campaignResourceDetailRepository.findByCampaignResourceId(campaignResourceId);
            List<CampaignResourceDetailDTO> campaignResourceDetailList = campaignResourceDetailCustomRepository.getByCampaignResourceId(campaignResourceId);
            List<String> listCode = new ArrayList<>();
            List<ListExcelDynamicDTO> listExcelDynamicDTOList = new ArrayList<>();
            List<ExcelDynamicDTO> dtoList = getListDynamicExcel(campaignId);
            for (ExcelDynamicDTO excelDynamicDTO : dtoList) {
                listCode.add(excelDynamicDTO.getCode());
            }
            for (CampaignResourceDetailDTO campaignResourceDetail : campaignResourceDetailList) {
                List<ExcelDynamicDTO> list = new ArrayList<>();
                ListExcelDynamicDTO listExcelDynamicDTO = new ListExcelDynamicDTO();
                List<Object[]> objects = new ArrayList<>();
                try {
                    objects = campaignResourceTemplateRepository.findTemplateDynamic(campaignId, campaignResourceId, campaignResourceDetail.getId(), listCode);
                } catch (Exception e) {
                    throw new BadRequestAlertException(Translator.toLocale("campaign-resource.export.error"), "", "");
                }
                if (!objects.isEmpty()) {
                    int order = 0;
                    for (Object[] obj : objects) {
                        int index = -1;
                        ++order;
                        ExcelDynamicDTO excelDynamicDTO = new ExcelDynamicDTO();
                        excelDynamicDTO.setType(DataUtil.safeToString(obj[++index]));
                        excelDynamicDTO.setCode(DataUtil.safeToString(obj[++index]));
                        excelDynamicDTO.setName(DataUtil.safeToString(obj[++index]));
                        excelDynamicDTO.setOrd(DataUtil.safeToInt(obj[++index]));
                        excelDynamicDTO.setValue(DataUtil.safeToString(obj[++index]));
                        excelDynamicDTO.setEditAble(DataUtil.safeToString(obj[++index]));
                        list.add(excelDynamicDTO);
                    }

                    //assign_user_id
                    ExcelDynamicDTO excelDynamicDTO = new ExcelDynamicDTO();
                    excelDynamicDTO.setCode("assignUserId");
                    excelDynamicDTO.setName("Người gửi");
                    excelDynamicDTO.setOrd(++order);
                    excelDynamicDTO.setValue(campaignResourceDetail.getAssignUserName());
                    list.add(excelDynamicDTO);

                    //call_time
                    excelDynamicDTO = new ExcelDynamicDTO();
                    excelDynamicDTO.setCode("callTime");
                    excelDynamicDTO.setName("Ngày gọi");
                    excelDynamicDTO.setOrd(DataUtil.safeToInt(++order));
                    String callTime = campaignResourceDetail.getCallTime();
                    excelDynamicDTO.setValue(callTime);
                    list.add(excelDynamicDTO);

                    //calling_status
                    excelDynamicDTO = new ExcelDynamicDTO();
                    excelDynamicDTO.setCode("callingStatus");
                    excelDynamicDTO.setName("Trạng thái cuộc gọi");
                    excelDynamicDTO.setOrd(DataUtil.safeToInt(++order));
                    excelDynamicDTO.setValue(campaignResourceDetail.getCallingStatusName());
                    list.add(excelDynamicDTO);

                    //sale_status
                    excelDynamicDTO = new ExcelDynamicDTO();
                    excelDynamicDTO.setCode("saleStatus");
                    excelDynamicDTO.setName("Trạng thái khảo sát");
                    excelDynamicDTO.setOrd(DataUtil.safeToInt(++order));
                    excelDynamicDTO.setValue(campaignResourceDetail.getSaleStatusName());
                    list.add(excelDynamicDTO);

                } else {
                    throw new BadRequestAlertException(Translator.toLocale("campaign-resource.export.error"), "", "");
                }
                listExcelDynamicDTO.setLsDynamicDTO(list);
                listExcelDynamicDTOList.add(listExcelDynamicDTO);
            }

            ByteArrayInputStream byteArrayInputStream = null;
            ListExcelDynamicGroupDTO listExcelDynamicGroupDTO = new ListExcelDynamicGroupDTO();
            listExcelDynamicGroupDTO.setLsData(listExcelDynamicDTOList);
            try {
                byteArrayInputStream = excelUtils.buildExcelDynamic(listExcelDynamicGroupDTO, "");
                Optional<CampaignResource> campaignResource = campaignResourceRepository.findById(campaignResourceId);
                Optional<Campaign> campaign = campaignRepository.findById(campaignResource.get().getCampaignId());
                Optional<User> userLog = userService.getUserWithAuthorities();
                actionLogService.saveActionLog(new ActionLogDTO(userLog.get().getId(), Constants.ACTION_LOG_TYPE.EXPORT + "",
                    null, String.format("Xuất excel: Xuất dữ liệu theo nguồn - Chiến dịch [%s]", campaign.get().getName()),
                    new Date(), Constants.MENU_ID.OMS, Constants.MENU_ITEM_ID.campaign_resource, "CONFIG_MENU_ITEM"));
                return byteArrayInputStream;
            } catch (Exception e) {
                e.printStackTrace();
                throw new BadRequestAlertException(Translator.toLocale("campaign-resource.export.error"), "", "");
            }
        }
        return null;
    }

    @Override
    public ByteArrayInputStream exportCampaignResourceError(Long campaignResourceId) {

        Optional<CampaignResource> campaignResourceOptional = campaignResourceRepository.findById(campaignResourceId);
        if (campaignResourceOptional.isPresent()) {
            Long campaignId = campaignResourceOptional.get().getCampaignId();
            List<CampaignResourceError> campaignResourceErrorList = campaignResourceErrorRepository.findByCampaignResourceId(campaignResourceId);
            List<String> listCode = new ArrayList<>();
            List<ListExcelDynamicDTO> listExcelDynamicDTOList = new ArrayList<>();
            List<ExcelDynamicDTO> dtoList = getListDynamicExcel(campaignId);
            for (ExcelDynamicDTO excelDynamicDTO : dtoList) {
                listCode.add(excelDynamicDTO.getCode());
            }
            listCode.add("ERROR_TYPE");
            for (CampaignResourceError campaignResourceError : campaignResourceErrorList) {
                List<ExcelDynamicDTO> list = new ArrayList<>();
                ListExcelDynamicDTO listExcelDynamicDTO = new ListExcelDynamicDTO();
                List<Object[]> objects = new ArrayList<>();
                try {
                    objects = campaignResourceTemplateErrRepository.findTemplateDynamic(campaignId, campaignResourceId, campaignResourceError.getId(), listCode);
                } catch (Exception e) {
                    throw new BadRequestAlertException(Translator.toLocale("campaign-resource.export.error"), "", "");
                }
                if (!objects.isEmpty()) {
                    for (Object[] obj : objects) {
                        int index = -1;
                        ExcelDynamicDTO excelDynamicDTO = new ExcelDynamicDTO();
                        excelDynamicDTO.setType(DataUtil.safeToString(obj[++index]));
                        excelDynamicDTO.setCode(DataUtil.safeToString(obj[++index]));
                        excelDynamicDTO.setName(DataUtil.safeToString(obj[++index]));
                        excelDynamicDTO.setOrd(DataUtil.safeToInt(obj[++index]));
                        excelDynamicDTO.setValue(DataUtil.safeToString(obj[++index]));
                        excelDynamicDTO.setEditAble(DataUtil.safeToString(obj[++index]));
                        list.add(excelDynamicDTO);
                    }
                } else {
                    throw new BadRequestAlertException(Translator.toLocale("campaign-resource.export.error"), "", "");
                }
                listExcelDynamicDTO.setLsDynamicDTO(list);
                listExcelDynamicDTOList.add(listExcelDynamicDTO);
            }

            ByteArrayInputStream byteArrayInputStream = null;
            ListExcelDynamicGroupDTO listExcelDynamicGroupDTO = new ListExcelDynamicGroupDTO();
            listExcelDynamicGroupDTO.setLsData(listExcelDynamicDTOList);
            try {
                byteArrayInputStream = excelUtils.buildExcelDynamic(listExcelDynamicGroupDTO, "");
                Optional<CampaignResource> campaignResource = campaignResourceRepository.findById(campaignResourceId);
                Optional<Campaign> campaign = campaignRepository.findById(campaignResource.get().getCampaignId());
                Optional<User> userLog = userService.getUserWithAuthorities();
                actionLogService.saveActionLog(new ActionLogDTO(userLog.get().getId(), Constants.ACTION_LOG_TYPE.EXPORT + "",
                    null, String.format("Xuất excel: Xuất dữ liệu lỗi - Chiến dịch [%s]", campaign.get().getName()),
                    new Date(), Constants.MENU_ID.OMS, Constants.MENU_ITEM_ID.campaign_resource, "CONFIG_MENU_ITEM"));
                return byteArrayInputStream;
            } catch (Exception e) {
                e.printStackTrace();
                throw new BadRequestAlertException(Translator.toLocale("campaign-resource.export.error"), "", "");
            }
        }
        return null;
    }

    @Override
    public ByteArrayInputStream getTemplate(Long id) {
        List<ExcelDynamicDTO> dtoList = getListDynamicExcel(id);
        try {
            return excelUtils.buildExcelDynamicV1(dtoList, "");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private List<ExcelDynamicDTO> getListDynamicExcel(Long campaignId) {
        List<ExcelDynamicDTO> lsDynamic = new ArrayList<>();
        List<Object[]> lstObj = campaignResourceRepository.getDynamicTemplate(campaignId);
        if (!lstObj.isEmpty()) {
            for (Object[] obj : lstObj) {
                ExcelDynamicDTO dto = new ExcelDynamicDTO();
                int index = -1;
                dto.setType(DataUtil.safeToString(obj[++index]));
                dto.setCode(DataUtil.safeToString(obj[++index]));
                dto.setName(DataUtil.safeToString(obj[++index]));
                dto.setOrd(DataUtil.safeToInt(obj[++index]));
                dto.setId(DataUtil.safeToLong(obj[++index]));
                dto.setEditAble(DataUtil.safeToString(obj[++index]));
                dto.setOptionValue(DataUtil.safeToString(obj[++index]));
                lsDynamic.add(dto);
            }
        }
        return lsDynamic;
    }

    private List<ListTemplateDTO> readTotalData(MultipartFile file, Long campaignId) {
        List<ListTemplateDTO> lsTemplate = new ArrayList<>();
        List<DataReadDynamicDTO> lstData = new ArrayList<>();
        List<DataReadDynamicDTO> lstHeader = new ArrayList<>();
        List<ExcelDynamicDTO> lstDynamic = getListDynamicExcel(campaignId);
        if (lstDynamic.size() == 0) {
            throw new BadRequestAlertException("File không đúng định dạng chiến dịch", "CampaignResource", "");
        }
        try {
            Workbook workbook = WorkbookFactory.create(file.getInputStream());
            Sheet sheet = workbook.getSheetAt(0);
            for (Row row : sheet) {

                lstData = new ArrayList<>();
                if (row.getRowNum() == 0) {
                    for (int i = 0; i < lstDynamic.size(); i++) {
                        Cell cell = row.getCell(i);
                        DataReadDynamicDTO dto = new DataReadDynamicDTO();
                        if (!DataUtil.isNullOrEmpty(cell)) {
                            DataFormatter df = new DataFormatter();
                            String cellValue = df.formatCellValue(cell);
                            String[] str = cellValue.split("-");
                            dto.setId(Long.parseLong(str[0]));
                            dto.setCode(str[1]);
                            dto.setCell(i);
                            lstHeader.add(dto);
                        }
                    }
                    int index = 0;
                    boolean validateFile = true;
                    if (lstDynamic.size() != lstHeader.size()) {
                        validateFile = false;
                    } else {
                        for (ExcelDynamicDTO dto : lstDynamic) {
                            if (dto.getId() != lstHeader.get(index).getId()) {
                                validateFile = false;
                            }
                            index++;
                        }
                    }
                    if (!validateFile) {
                        throw new BadRequestAlertException("File không đúng định dạng chiến dịch", "CampaignResource", "");
                    }
                }

                if (row.getRowNum() > 0) {
                    for (int i = 0; i < lstDynamic.size(); i++) {
                        Cell cell = row.getCell(i);
                        if (!DataUtil.isNullOrEmpty(cell)) {
                            DataFormatter df = new DataFormatter();
                            String cellValue = df.formatCellValue(cell);
                            for (DataReadDynamicDTO dto : lstHeader) {
                                if (dto.getCell() == i) {
                                    DataReadDynamicDTO dataReadDynamicDTO = formatData(dto, lstDynamic, cellValue);
                                    dataReadDynamicDTO.setCell(i);
                                    dataReadDynamicDTO.setIdx(row.getRowNum());
                                    if (dataReadDynamicDTO.getCode().equals("MobilePhone")) {
                                        dataReadDynamicDTO.setPhoneNumber(formatPhoneNumber(cellValue));
                                    }
                                    lstData.add(dataReadDynamicDTO);
                                    break;
                                }
                            }
                        }
                    }
                    ListTemplateDTO template = new ListTemplateDTO();
                    template.setLsTemplate(lstData);
                    lsTemplate.add(template);
                }
            } //End for ROW
        } catch (IOException e) {
            e.printStackTrace();
        }
        List<ListTemplateDTO> lsRs = new ArrayList<>();
        for (ListTemplateDTO dto : lsTemplate) {
            if (!dto.getLsTemplate().isEmpty()) {
                lsRs.add(dto);
            }
        }
        return lsRs;
    }

    public String formatPhoneNumber(String phoneNumber) {
        if (phoneNumber != null && !phoneNumber.startsWith("0")) {
            return "0" + phoneNumber;
        }
        return phoneNumber;
    }

    /**
     * @param dto
     * @param lstDynamic
     * @param cellValue
     * @return
     */
    public DataReadDynamicDTO formatData(DataReadDynamicDTO dto, List<ExcelDynamicDTO> lstDynamic, String cellValue) {
        DataReadDynamicDTO dataReadDynamicDTO = new DataReadDynamicDTO();
        dataReadDynamicDTO.setName(dto.getName());
        dataReadDynamicDTO.setId(dto.getId());
        dataReadDynamicDTO.setPhoneNumber(cellValue);
        for (ExcelDynamicDTO d : lstDynamic) {
            if (d.getId() == dto.getId()) {
                dataReadDynamicDTO.setCode(d.getCode());
                dataReadDynamicDTO.setName(d.getName());
                dataReadDynamicDTO.setType(d.getType());
                dataReadDynamicDTO.setEditAble(d.getEditAble());
                dataReadDynamicDTO.setOptionValue(d.getOptionValue());
                break;
            }
        }
        return dataReadDynamicDTO;
    }

    private Boolean validatePhoneNumber(String phoneNumber) {
        Pattern pattern = Pattern.compile("^\\d{10}$");
        Matcher matcher = pattern.matcher(phoneNumber);
        return matcher.matches();
    }

    private Map<String, List<ListTemplateDTO>> handleData(List<ListTemplateDTO> uncheckLst, List<String> phoneNumberLst, List<String> phoneNumberLstAll, Long type, Long campaignId) {
        try {
            Map<String, List<ListTemplateDTO>> map = new HashMap<>();
            List<ListTemplateDTO> lstAfterDuplicateFilter = new ArrayList<>();
            List<ListTemplateDTO> lstAfterValidate = new ArrayList<>();
            List<ListTemplateDTO> duplicateLst = new ArrayList<>();
            List<ListTemplateDTO> errorLst = new ArrayList<>();
            List<ListTemplateDTO> blackListLst = new ArrayList<>();
            List<CampaignBlacklist> campaignBlacklistList = campaignBlackListRepository.findByCampaignId(campaignId);
            List<String> lst = new ArrayList<>();
            List<ListTemplateDTO> finalResult = new ArrayList<>();

            for (ListTemplateDTO dto : uncheckLst) {
                List<DataReadDynamicDTO> dataReadDynamicDTOList = dto.getLsTemplate();
                String phoneNumber = "";
                for (DataReadDynamicDTO dataReadDynamicDTO : dataReadDynamicDTOList) {
                    if (dataReadDynamicDTO.getCode().equals("MobilePhone")) {
                        phoneNumber = dataReadDynamicDTO.getPhoneNumber();
                        break;
                    }
                }
                if (!validatePhoneNumber(phoneNumber)) {
                    errorLst.add(dto);
                } else {
                    lstAfterValidate.add(dto);
                }
            }

            for (ListTemplateDTO dto : lstAfterValidate) {
                List<DataReadDynamicDTO> dataReadDynamicDTOList = dto.getLsTemplate();
                for (DataReadDynamicDTO dataReadDynamicDTO : dataReadDynamicDTOList) {
                    if (dataReadDynamicDTO.getCode().equals("MobilePhone")) {
                        lst.add(dataReadDynamicDTO.getPhoneNumber());
                        break;
                    }
                }
            }

            Map<String, List<ListTemplateDTO>> stringListMap = new HashMap<>();

            if (type.equals(Constants.DUPLICATE_FILTER_IN_FILE)) {
                stringListMap = filterDuplicate(lstAfterValidate, phoneNumberLst, lst, type);
            } else if (type.equals(Constants.DUPLICATE_FILTER_IN_CAMPAIGN)) {
                stringListMap = filterDuplicate(lstAfterValidate, phoneNumberLstAll, lst, type);
            } else if (type.equals(Constants.NO_DUPLICATE_FILTER)) {
                stringListMap = filterDuplicate(lstAfterValidate, new ArrayList<>(), new ArrayList<>(), type);
            }

            for (Map.Entry<String, List<ListTemplateDTO>> entry : stringListMap.entrySet()) {
                if (entry.getKey().equalsIgnoreCase("list-after-duplicate-filter")) {
                    lstAfterDuplicateFilter = entry.getValue();
                } else if (entry.getKey().equalsIgnoreCase("list-duplicate")) {
                    duplicateLst = entry.getValue();
                }
            }

            //Check DS den
            for (ListTemplateDTO p : lstAfterDuplicateFilter) {
                if (checkBlacklist(p, campaignBlacklistList)) {
                    blackListLst.add(p);
                } else {
                    finalResult.add(p);
                }
            }
            map.put("success", finalResult);
            map.put("duplicate", duplicateLst);
            map.put("error", errorLst);
            map.put("blackList", blackListLst);
            return map;
        } catch (Exception e) {
            throw new BadRequestAlertException(Translator.toLocale("campaign-resource.import.error"), "", "");
        }
    }

    private Map<String, List<ListTemplateDTO>> filterDuplicate(List<ListTemplateDTO> uncheckLst, List<String> checkLst, List<String> list, Long type) {
        try {
            List<ListTemplateDTO> lstDuplicate = new ArrayList<>();
            List<ListTemplateDTO> lstResult = new ArrayList<>();
            List<ListTemplateDTO> lstResultFinal = new ArrayList<>();
            if (!type.equals(Constants.NO_DUPLICATE_FILTER)) {
                for (ListTemplateDTO dto : uncheckLst) {
                    boolean checkDuplicate = true;
                    List<DataReadDynamicDTO> dataReadDynamicDTOList = dto.getLsTemplate();
                    DataReadDynamicDTO dynamicDTO = new DataReadDynamicDTO();
                    for (DataReadDynamicDTO dataReadDynamicDTO : dataReadDynamicDTOList) {
                        if (dataReadDynamicDTO.getCode().equals("MobilePhone")) {
                            dynamicDTO = dataReadDynamicDTO;
                            break;
                        }
                    }
                    if (lstResult.isEmpty()) {
                        lstResult.add(dto);
                    } else {
                        for (ListTemplateDTO listTemplateDTO : lstResult) {
                            List<DataReadDynamicDTO> dataReadDynamicDTOS = listTemplateDTO.getLsTemplate();
                            for (DataReadDynamicDTO dataReadDynamicDTO : dataReadDynamicDTOS) {
                                if (dataReadDynamicDTO.getCode().equals("MobilePhone")) {
                                    if (dynamicDTO.getPhoneNumber().equals(dataReadDynamicDTO.getPhoneNumber())) {
                                        checkDuplicate = false;
                                    }
                                    break;
                                }
                            }
                        }
                        if (!checkDuplicate) {
                            lstDuplicate.add(dto);
                        } else {
                            lstResult.add(dto);
                        }
                    }
                }
                for (ListTemplateDTO dto : lstResult) {
                    boolean checkDuplicate = true;
                    List<DataReadDynamicDTO> dataReadDynamicDTOList = dto.getLsTemplate();
                    DataReadDynamicDTO dynamicDTO = new DataReadDynamicDTO();
                    for (DataReadDynamicDTO dataReadDynamicDTO : dataReadDynamicDTOList) {
                        if (dataReadDynamicDTO.getCode().equals("MobilePhone")) {
                            dynamicDTO = dataReadDynamicDTO;
                            break;
                        }
                    }
                    if (!checkLst.isEmpty()) {
                        for (String phoneNumber : checkLst) {
                            if (phoneNumber.equals(dynamicDTO.getPhoneNumber())) {
                                checkDuplicate = false;
                            }
                        }
                    }
                    if (!checkDuplicate) {
                        lstDuplicate.add(dto);
                    } else {
                        lstResultFinal.add(dto);
                    }
                }
            } else {
                lstResultFinal = uncheckLst;
            }

            Map<String, List<ListTemplateDTO>> map = new HashMap<>();
            map.put("list-after-duplicate-filter", lstResultFinal);
            map.put("list-duplicate", lstDuplicate);

            return map;
        } catch (Exception e) {
            throw new BadRequestAlertException(Translator.toLocale("campaign-resource.import.error"), "", "");
        }
    }

    /**
     * Kiem tra sdt co nam trong blacklist hay khong
     *
     * @param p
     * @param campaignBlacklistList
     * @return
     */
    public boolean checkBlacklist(ListTemplateDTO p, List<CampaignBlacklist> campaignBlacklistList) {
        List<DataReadDynamicDTO> lsTemplate = p.getLsTemplate();
        if (lsTemplate.isEmpty()) return false;
        for (DataReadDynamicDTO o : lsTemplate) {
            if (o.getCode().equals("MobilePhone")) {
                for (CampaignBlacklist c : campaignBlacklistList) {
                    if (c.getPhoneNumber().equals(o.getPhoneNumber()))
                        return true;
                }
            }
        }
        return false;
    }

    private List<ExcelColumn> buildColumnExport() {
        List<ExcelColumn> lstColumn = new ArrayList<>();
        lstColumn.add(new ExcelColumn("campaignResourceName", Translator.toLocale("campaign-resource-detail.campaign-resource-name"), ExcelColumn.ALIGN_MENT.LEFT));
        lstColumn.add(new ExcelColumn("phoneNumber", Translator.toLocale("campaign-resource-detail.phone-number"), ExcelColumn.ALIGN_MENT.CENTER));
        lstColumn.add(new ExcelColumn("createDateTime", Translator.toLocale("campaign-resource-detail.create-date-time"), ExcelColumn.ALIGN_MENT.CENTER));
        lstColumn.add(new ExcelColumn("createUserName", Translator.toLocale("campaign-resource-detail.create-user-name"), ExcelColumn.ALIGN_MENT.CENTER));

        return lstColumn;
    }

    private void saveCampaignResourceError(ListTemplateDTO dto, CampaignResource campaignResource, Long userId, String errorType) {
        List<DataReadDynamicDTO> lsData = dto.getLsTemplate();
        if (lsData.isEmpty())
            return;
        String phoneNumber = "";
        for (DataReadDynamicDTO o : lsData) {
            if (o.getCode().equals("MobilePhone")) {
                phoneNumber = o.getPhoneNumber();
                break;
            }
        }
        CampaignResourceError campaignResourceError = new CampaignResourceError();
        campaignResourceError.setCampaignResourceId(campaignResource.getId());
        campaignResourceError.setPhoneNumber(phoneNumber);
        campaignResourceError.setCreateUser(userId);
        campaignResourceError.setCreateDateTime(Instant.now());
        campaignResourceError.setStatus(Constants.STATUS_ACTIVE);
        try {
            campaignResourceError = campaignResourceErrorRepository.save(campaignResourceError);
            CampaignResourceTemplateErr campaignResourceTemplateErr = new CampaignResourceTemplateErr();
            for (DataReadDynamicDTO dtoDetail : lsData) {

                campaignResourceTemplateErr = new CampaignResourceTemplateErr();
                campaignResourceTemplateErr.setCampaignId(campaignResource.getCampaignId());
                campaignResourceTemplateErr.setCampaignResourceId(campaignResource.getId());
                campaignResourceTemplateErr.setCampaignResourceDetailId(campaignResourceError.getId());
                campaignResourceTemplateErr.setType(dtoDetail.getType());
                campaignResourceTemplateErr.setCode(dtoDetail.getCode());
                campaignResourceTemplateErr.setName(dtoDetail.getName());
                campaignResourceTemplateErr.setOrd(dtoDetail.getCell() + 1);
                campaignResourceTemplateErr.setValue(dtoDetail.getPhoneNumber());
                campaignResourceTemplateErr.setEditable(dtoDetail.getEditAble());
                try {
                    campaignResourceTemplateErr = campaignResourceTemplateErrRepository.save(campaignResourceTemplateErr);
                } catch (Exception e) {
                    throw new BadRequestAlertException(Translator.toLocale("campaign-resource.import.error"), "", "");
                }
            }
            ////////////////////
            //Add Error Type;
            CampaignResourceTemplateErr campaignResourceErrorType = new CampaignResourceTemplateErr();
            campaignResourceErrorType.setCampaignId(campaignResource.getCampaignId());
            campaignResourceErrorType.setCampaignResourceId(campaignResource.getId());
            campaignResourceErrorType.setCampaignResourceDetailId(campaignResourceError.getId());
            campaignResourceErrorType.setType("1");
            campaignResourceErrorType.setCode("ERROR_TYPE");
            campaignResourceErrorType.setName("Loại lỗi");
            campaignResourceErrorType.setOrd(campaignResourceTemplateErr.getOrd() + 1);
            campaignResourceErrorType.setValue(errorType);
            campaignResourceErrorType.setEditable("1");
            try {
                campaignResourceTemplateErr = campaignResourceTemplateErrRepository.save(campaignResourceErrorType);
            } catch (Exception e) {
                throw new BadRequestAlertException(Translator.toLocale("campaign-resource.import.error"), "", "");
            }
        } catch (Exception e) {
            throw new BadRequestAlertException(Translator.toLocale("campaign-resource.import.error"), "", "");
        }
    }


    public RecordCallResultCustomRepository getRecordCallResultCustomRepository() {
        return recordCallResultCustomRepository;
    }

    public void setRecordCallResultCustomRepository(RecordCallResultCustomRepository recordCallResultCustomRepository) {
        this.recordCallResultCustomRepository = recordCallResultCustomRepository;
    }
}
