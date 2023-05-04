package com.fis.crm.service.impl;

import com.fis.crm.commons.DataUtil;
import com.fis.crm.config.Constants;
import com.fis.crm.domain.CampaignSMSBlackList;
import com.fis.crm.domain.CampaignSMSMarketing;
import com.fis.crm.domain.CampaignSMSResource;
import com.fis.crm.domain.User;
import com.fis.crm.repository.CampaignResourceRepository;
import com.fis.crm.repository.CampaignSMSBlackListRepository;
import com.fis.crm.repository.CampaignSMSMarketingRepository;
import com.fis.crm.repository.CampaignSMSResourceRepository;
import com.fis.crm.service.ActionLogService;
import com.fis.crm.service.CampaignSMSMarketingService;
import com.fis.crm.service.UserService;
import com.fis.crm.service.dto.ActionLogDTO;
import com.fis.crm.service.dto.CampaignSMSMarketingDTO;
import com.fis.crm.service.dto.CampaignSMSResourceDTO;
import com.fis.crm.service.mapper.CampaignSMSMarketingMapper;
import com.fis.crm.service.mapper.CampaignSMSResourceMapper;
import com.fis.crm.web.rest.errors.BadRequestAlertException;
import org.apache.poi.ss.usermodel.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@Transactional
public class CampaignSMSMarketingServiceImpl implements CampaignSMSMarketingService {
    private final CampaignSMSMarketingRepository campaignSMSMarketingRepository;

    private final CampaignSMSMarketingMapper campaignSMSMarketingMapper;

    private final CampaignResourceRepository campaignResourceRepository;

    private final UserService userService;
    private final ActionLogService actionLogService;

    private final CampaignSMSResourceRepository campaignSMSResourceRepository;

    private final CampaignSMSResourceMapper campaignSMSResourceMapper;

    private final CampaignSMSBlackListRepository campaignSMSBlackListRepository;

    public CampaignSMSMarketingServiceImpl(CampaignSMSMarketingRepository campaignSMSMarketingRepository, CampaignSMSMarketingMapper campaignSMSMarketingMapper, CampaignResourceRepository campaignResourceRepository, UserService userService, ActionLogService actionLogService, CampaignSMSResourceRepository campaignSMSResourceRepository, CampaignSMSResourceMapper campaignSMSResourceMapper, CampaignSMSBlackListRepository campaignSMSBlackListRepository) {
        this.campaignSMSMarketingRepository = campaignSMSMarketingRepository;
        this.campaignSMSMarketingMapper = campaignSMSMarketingMapper;
        this.campaignResourceRepository = campaignResourceRepository;
        this.userService = userService;
        this.actionLogService = actionLogService;
        this.campaignSMSResourceRepository = campaignSMSResourceRepository;
        this.campaignSMSResourceMapper = campaignSMSResourceMapper;
        this.campaignSMSBlackListRepository = campaignSMSBlackListRepository;
    }

    @Override
    public Page<CampaignSMSMarketingDTO> search(Long id, String startDate, String endDate, String status, Pageable pageable) {
        Page<Object[]> page = campaignSMSMarketingRepository.search(id, startDate.equals("") ? 0L : 1L, startDate, endDate.equals("") ? 0L : 1L, endDate, Long.valueOf(status), pageable);
        List<Object[]> lst = page.getContent();
        List<CampaignSMSMarketingDTO> lstDto = new ArrayList<>();
        for (Object[] obj : lst) {
            CampaignSMSMarketingDTO dto = new CampaignSMSMarketingDTO();
            int index = -1;
            dto.setId(DataUtil.safeToLong(obj[++index]));
            dto.setName(DataUtil.safeToString(obj[++index]));
            dto.setStartDate(DataUtil.safeToInstant(obj[++index]));
            dto.setEndDate(DataUtil.safeToInstant(obj[++index]));
            dto.setStatus(DataUtil.safeToString(obj[++index]));
            dto.setTitle(DataUtil.safeToString(obj[++index]));
            dto.setContent(DataUtil.safeToString(obj[++index]));
            dto.setCreateDateTime(DataUtil.safeToInstant(obj[++index]));
            dto.setCreateUser(DataUtil.safeToLong(obj[++index]));
            dto.setUpdateDateTime(DataUtil.safeToInstant(obj[++index]));
            dto.setUpdateUser(DataUtil.safeToLong(obj[++index]));
            if (!DataUtil.isNullOrEmpty(dto.getCreateUser())) {
                Object[] objects = campaignResourceRepository.getUserName(dto.getCreateUser());
                if (!DataUtil.isNullOrEmpty(objects)) {
                    dto.setCreateUserName(DataUtil.safeToString(objects[0]));
                }
            }
            if (!DataUtil.isNullOrEmpty(dto.getUpdateUser())) {
                Object[] objects = campaignResourceRepository.getUserName(dto.getUpdateUser());
                if (!DataUtil.isNullOrEmpty(objects)) {
                    dto.setUpdateUserName(DataUtil.safeToString(objects[0]));
                }
            }
            List<CampaignSMSResource> list = campaignSMSResourceRepository.findByCampaignSmsMarketingIdAndSendStatusNotLike(dto.getId(), "0");
            dto.setTotalData(list.isEmpty() ? 0L : (long) list.size());
            if (!DataUtil.isNullOrEmpty(dto.getCreateDateTime())) {
                Date date = Date.from(dto.getCreateDateTime());
                SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                String dateView = format.format(date);
                dto.setCreateDateTimeView(dateView);
            }
            if (!DataUtil.isNullOrEmpty(dto.getUpdateDateTime())) {
                Date date = Date.from(dto.getUpdateDateTime());
                SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                String dateView = format.format(date);
                dto.setUpdateDateTimeView(dateView);
            }
            lstDto.add(dto);
        }
        return new PageImpl<>(lstDto, pageable, page.getTotalElements());
    }

    @Override
    public List<CampaignSMSMarketingDTO> findAll() {
        return campaignSMSMarketingMapper.toDto(campaignSMSMarketingRepository.findAllByStatusOrderByNameAsc(Constants.STATUS_ACTIVE));
    }

    @Override
    public List<CampaignSMSMarketingDTO> findAllSearch() {
        return campaignSMSMarketingMapper.toDto(campaignSMSMarketingRepository.findAll());
    }

    @Override
    public CampaignSMSMarketingDTO save(CampaignSMSMarketingDTO dto) {
        Long check;
        if (DataUtil.isNullOrEmpty(dto.getId())) {
            check = campaignSMSMarketingRepository.checkExistsCampaignSMSMarketing(dto.getName(), -1);
            Long userId = userService.getUserIdLogin();
            dto.setCreateUser(userId);
            dto.setCreateDateTime(Instant.now());
        } else {
            check = campaignSMSMarketingRepository.checkExistsCampaignSMSMarketing(dto.getName(), Math.toIntExact(dto.getId()));
            Optional<CampaignSMSMarketing> optional = campaignSMSMarketingRepository.findById(dto.getId());
            if (optional.isPresent()) {
                dto.setCreateUser(optional.get().getCreateUser());
                dto.setCreateDateTime(optional.get().getCreateDateTime());
            }
        }

        if (check > 0) {
            throw new BadRequestAlertException("Tên đã được sử dụng, vui lòng nhập thông tin khác", "", "");
        }

        Optional<User> userOptional = userService.getUserWithAuthorities();
        User currentUser;
        if (userOptional.isPresent()) {
            currentUser = userOptional.get();

            if (dto.getId() != null){
                dto.setUpdateDateTime(Instant.now());
                dto.setUpdateUser(currentUser.getId());
            }

            CampaignSMSMarketing campaignSMSMarketing = campaignSMSMarketingMapper.toEntity(dto);
            campaignSMSMarketing = campaignSMSMarketingRepository.save(campaignSMSMarketing);


            if (dto.getId() != null) {
                actionLogService.saveActionLog(new ActionLogDTO(currentUser.getId(), Constants.ACTION_LOG_TYPE.UPDATE + "",
                    null, String.format("Cập nhật: Chiến dịch SMS marketing [%s]", dto.getName()),
                    new Date(), Constants.MENU_ID.SMS_MARKETING, Constants.MENU_ITEM_ID.campaign_sms_marketing, "CONFIG_MENU_ITEM"));
            } else {
                actionLogService.saveActionLog(new ActionLogDTO(currentUser.getId(), Constants.ACTION_LOG_TYPE.INSERT + "",
                    null, String.format("Thêm mới: Chiến dịch SMS marketing [%s]", dto.getName()),
                    new Date(), Constants.MENU_ID.SMS_MARKETING, Constants.MENU_ITEM_ID.campaign_sms_marketing, "CONFIG_MENU_ITEM"));
            }

            return campaignSMSMarketingMapper.toDto(campaignSMSMarketing);
        }
        return null;
    }

    @Override
    public CampaignSMSMarketingDTO findOne(Long id) {
        Optional<CampaignSMSMarketing> optional = campaignSMSMarketingRepository.findById(id);
        if (optional.isPresent()) {
            CampaignSMSMarketingDTO dto = campaignSMSMarketingMapper.toDto(optional.get());
            SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
            dto.setStartDateView(format.format(Date.from(dto.getStartDate())));
            dto.setEndDateView(format.format(Date.from(dto.getEndDate())));
            return dto;
        }
        return null;
    }

    @Override
    public void delete(Long id) {
        Optional<CampaignSMSMarketing> byId = campaignSMSMarketingRepository.findById(id);
        Optional<User> userLog = userService.getUserWithAuthorities();
        actionLogService.saveActionLog(new ActionLogDTO(userLog.get().getId(), Constants.ACTION_LOG_TYPE.DELETE + "",
            null, String.format("Xóa: Chiến dịch SMS marketing [%s]", byId.get().getName()),
            new Date(), Constants.MENU_ID.SMS_MARKETING, Constants.MENU_ITEM_ID.campaign_sms_marketing, "CONFIG_MENU_ITEM"));
        campaignSMSMarketingRepository.deleteById(id);
    }

    @Override
    public Map<String, Long> importFile(MultipartFile file, Long id, Long duplicateFilter) {
        List<CampaignSMSResourceDTO> lstImport = readFile(file);
        Map<String, List<CampaignSMSResourceDTO>> rs = validateImport(lstImport, id);
        List<CampaignSMSResourceDTO> lstSuccess = new ArrayList<>();
        List<CampaignSMSResourceDTO> lstError = new ArrayList<>();
        List<CampaignSMSResourceDTO> lstBlackList = new ArrayList<>();
        for (Map.Entry<String, List<CampaignSMSResourceDTO>> entry : rs.entrySet()) {
            if (entry.getKey().equalsIgnoreCase("success")) {
                lstSuccess = entry.getValue();
            }
            if (entry.getKey().equalsIgnoreCase("error")) {
                lstError = entry.getValue();
            }
            if (entry.getKey().equalsIgnoreCase("blackList")) {
                lstBlackList = entry.getValue();
            }
        }
        List<CampaignSMSResourceDTO> lstSuccessAfterDuplicateFilter = duplicateFilter(lstSuccess, duplicateFilter, id);
        Long userId = userService.getUserIdLogin();
        for (CampaignSMSResourceDTO dto : lstSuccessAfterDuplicateFilter) {
            dto.setCampaignSmsMarketingId(id);
            dto.setCreateDateTime(Instant.now());
            dto.setCreateUser(userId);
            dto.setSendStatus("1");
            campaignSMSResourceRepository.save(campaignSMSResourceMapper.toEntity(dto));
        }
        Optional<CampaignSMSMarketing> optional = campaignSMSMarketingRepository.findById(id);
        if (optional.isPresent()) {
            CampaignSMSMarketing campaignSMSMarketing = optional.get();
            campaignSMSMarketing.setIsUsed("1");
            campaignSMSMarketingRepository.save(campaignSMSMarketing);
        }
        Map<String, Long> resultMap = new HashMap<>();
        resultMap.put("total", Long.valueOf(lstImport.size()));
        resultMap.put("success", Long.valueOf(lstSuccessAfterDuplicateFilter.size()));
        resultMap.put("error", Long.valueOf(lstError.size()));
        resultMap.put("blackList", Long.valueOf(lstBlackList.size()));
        Optional<User> userLog = userService.getUserWithAuthorities();
        actionLogService.saveActionLog(new ActionLogDTO(userLog.get().getId(), Constants.ACTION_LOG_TYPE.UPDATE + "",
            null, String.format("Import: Chiến dịch SMS marketing"),
            new Date(), Constants.MENU_ID.SMS_MARKETING, Constants.MENU_ITEM_ID.campaign_sms_marketing, "CONFIG_MENU_ITEM"));
        return resultMap;
    }

    @Override
    public List<CampaignSMSMarketingDTO> findAllOrderByNameASC() {
        List<CampaignSMSMarketing> campaignSMSMarketingList = campaignSMSMarketingRepository
            .findAllByStatusOrderByNameAsc(Constants.STATUS_ACTIVE);
        return campaignSMSMarketingMapper.toDto(campaignSMSMarketingList);
    }

    private Map<String, List<CampaignSMSResourceDTO>> validateImport(List<CampaignSMSResourceDTO> lstImport, Long id) {
        Map<String, List<CampaignSMSResourceDTO>> rs = new HashMap<>();
        List<CampaignSMSResourceDTO> lstSMSSuccess = new ArrayList<>();
        List<CampaignSMSResourceDTO> lstSMSError = new ArrayList<>();
        List<CampaignSMSResourceDTO> lstSMSBlackList = new ArrayList<>();
        List<CampaignSMSBlackList> blackList = campaignSMSBlackListRepository.findByCampaignSMSIdAndStatus(id, "1");
        List<String> smsBlackList = new ArrayList<>();
        for (CampaignSMSBlackList campaignSMSBlackList : blackList) {
            smsBlackList.add(campaignSMSBlackList.getPhoneNumber());
        }
        for (CampaignSMSResourceDTO campaignSMSResourceDTO : lstImport) {
            boolean checkBlackList = false;
            if (!validatePhoneNumber(campaignSMSResourceDTO.getPhoneNumber())) {
                lstSMSError.add(campaignSMSResourceDTO);
            } else {
                for (String smsBL : smsBlackList) {
                    if (smsBL.equals(campaignSMSResourceDTO.getPhoneNumber())) {
                        checkBlackList = true;
                        break;
                    }
                }
                if (!checkBlackList) {
                    lstSMSSuccess.add(campaignSMSResourceDTO);
                } else {
                    lstSMSBlackList.add(campaignSMSResourceDTO);
                }
            }
        }
        rs.put("error", lstSMSError);
        rs.put("blackList", lstSMSBlackList);
        rs.put("success", lstSMSSuccess);
        return rs;
    }

    private List<CampaignSMSResourceDTO> duplicateFilter(List<CampaignSMSResourceDTO> lst, Long duplicateFilterType, Long id) {
        if (duplicateFilterType.equals(Constants.NO_DUPLICATE_FILTER)) {
            return lst;
        }
        //Loc trung tren file
        List<CampaignSMSResourceDTO> lstResult = new ArrayList<>();
        for (CampaignSMSResourceDTO campaignSMSResourceDTO : lst) {
            boolean checkDuplicate = false;
            if (lstResult.isEmpty()) {
                lstResult.add(campaignSMSResourceDTO);
            } else {
                for (CampaignSMSResourceDTO dto : lstResult) {
                    if (dto.getPhoneNumber().equals(campaignSMSResourceDTO.getPhoneNumber())) {
                        checkDuplicate = true;
                        break;
                    }
                }
                if (!checkDuplicate) {
                    lstResult.add(campaignSMSResourceDTO);
                }
            }
        }
        if (duplicateFilterType.equals(Constants.DUPLICATE_FILTER_IN_FILE)) {
            List<CampaignSMSResourceDTO> lstResultOne = new ArrayList<>();
            List<CampaignSMSResource> lstInCampaign = campaignSMSResourceRepository.findByCampaignSmsMarketingIdAndSendStatusNotLike(id, "0");
            for (CampaignSMSResourceDTO dto : lst) {
                boolean checkDuplicate = false;
                for (CampaignSMSResource campaignSMSResource : lstInCampaign) {
                    if (campaignSMSResource.getPhoneNumber().equals(dto.getPhoneNumber())) {
                        checkDuplicate = true;
                        break;
                    }
                }
                if (!checkDuplicate) {
                    lstResultOne.add(dto);
                }
            }
            return lstResultOne;
        }
        if (duplicateFilterType.equals(Constants.DUPLICATE_FILTER_IN_CAMPAIGN)) {
            List<CampaignSMSResourceDTO> lstResultOne = new ArrayList<>();
            List<CampaignSMSResource> lstInCampaign = campaignSMSResourceRepository.findBySendStatusNotLike("0");
            for (CampaignSMSResourceDTO dto : lst) {
                boolean checkDuplicate = false;
                for (CampaignSMSResource campaignSMSResource : lstInCampaign) {
                    if (campaignSMSResource.getPhoneNumber().equals(dto.getPhoneNumber())) {
                        checkDuplicate = true;
                        break;
                    }
                }
                if (!checkDuplicate) {
                    lstResultOne.add(dto);
                }
            }
            return lstResultOne;
        }
        return null;
    }

    private List<CampaignSMSResourceDTO> readFile(MultipartFile file) {
        List<CampaignSMSResourceDTO> lst = new ArrayList<>();
        try {
            Workbook workbook = WorkbookFactory.create(file.getInputStream());
            Sheet sheet = workbook.getSheetAt(0);
            for (Row row : sheet) {
                if (row.getRowNum() > 0) {
                    CampaignSMSResourceDTO dto = new CampaignSMSResourceDTO();
                    for (int i = 0; i <= 10; i++) {
                        Cell cell = row.getCell(i);
                        if (!DataUtil.isNullOrEmpty(cell)) {
                            String cellValue = handleCell(cell.getCellType(), cell);
                            if (i == 0) {
                                dto.setPhoneNumber(DataUtil.safeToString(cellValue));
                            }
                            if (i == 1) {
                                dto.setC1(DataUtil.safeToString(cellValue));
                            }
                            if (i == 2) {
                                dto.setC2(DataUtil.safeToString(cellValue));
                            }
                            if (i == 3) {
                                dto.setC3(DataUtil.safeToString(cellValue));
                            }
                            if (i == 4) {
                                dto.setC4(DataUtil.safeToString(cellValue));
                            }
                            if (i == 5) {
                                dto.setC5(DataUtil.safeToString(cellValue));
                            }
                            if (i == 6) {
                                dto.setC6(DataUtil.safeToString(cellValue));
                            }
                            if (i == 7) {
                                dto.setC7(DataUtil.safeToString(cellValue));
                            }
                            if (i == 8) {
                                dto.setC8(DataUtil.safeToString(cellValue));
                            }
                            if (i == 9) {
                                dto.setC9(DataUtil.safeToString(cellValue));
                            }
                            if (i == 10) {
                                dto.setC10(DataUtil.safeToString(cellValue));
                            }
                        }
                    }
                    lst.add(dto);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return lst;
    }

    private boolean validatePhoneNumber(String phoneNumber) {
        if (DataUtil.isNullOrEmpty(phoneNumber)) {
            return false;
        } else {
            Pattern pattern = Pattern.compile("^\\d{10}$");
            Matcher matcher = pattern.matcher(phoneNumber);
            return matcher.find();
        }
    }

    private String handleCell(CellType type, Cell cell) {
        if (type == CellType.STRING) {
            return cell.getStringCellValue();
        } else if (type == CellType.NUMERIC) {
            return String.valueOf(cell.getNumericCellValue());
        } else if (type == CellType.FORMULA) {
            return handleCell(cell.getCachedFormulaResultType(), cell);
        } else {
            return null;
        }
    }
}
