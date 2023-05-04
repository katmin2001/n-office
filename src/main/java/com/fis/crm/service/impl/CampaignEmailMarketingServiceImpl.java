package com.fis.crm.service.impl;

import com.fis.crm.commons.DataUtil;
import com.fis.crm.config.Constants;
import com.fis.crm.domain.*;
import com.fis.crm.repository.CampaignEmailBlackListRepository;
import com.fis.crm.repository.CampaignEmailMarketingRepository;
import com.fis.crm.repository.CampaignEmailResourceRepository;
import com.fis.crm.repository.CampaignResourceRepository;
import com.fis.crm.service.ActionLogService;
import com.fis.crm.service.CampaignEmailMarketingService;
import com.fis.crm.service.UserService;
import com.fis.crm.service.dto.ActionLogDTO;
import com.fis.crm.service.dto.CampaignEmailMarketingDTO;
import com.fis.crm.service.dto.CampaignEmailResourceDTO;
import com.fis.crm.service.mapper.CampaignEmailMarketingMapper;
import com.fis.crm.service.mapper.CampaignEmailResourceMapper;
import com.fis.crm.web.rest.errors.BadRequestAlertException;
import com.fis.crm.web.rest.errors.BusinessException;
import com.fis.crm.web.rest.errors.ErrorCodeResponse;
import org.apache.commons.io.FilenameUtils;
import org.apache.poi.ss.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.orm.jpa.EntityManagerFactoryInfo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityManager;
import javax.sql.DataSource;
import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@Transactional
public class CampaignEmailMarketingServiceImpl implements CampaignEmailMarketingService {

    public static final Pattern VALID_EMAIL_ADDRESS_REGEX =
        Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

    private final CampaignEmailMarketingMapper campaignEmailMarketingMapper;

    private final CampaignEmailMarketingRepository campaignEmailMarketingRepository;

    private final UserService userService;

    private final CampaignResourceRepository campaignResourceRepository;

    private final CampaignEmailResourceRepository campaignEmailResourceRepository;

    private final CampaignEmailResourceMapper campaignEmailResourceMapper;

    private final CampaignEmailBlackListRepository campaignEmailBlackListRepository;
    private final ActionLogService actionLogService;

    private final EntityManager entityManager;

    public CampaignEmailMarketingServiceImpl(CampaignEmailMarketingRepository campaignEmailMarketingRepository,
                                             CampaignEmailMarketingMapper campaignEmailMarketingMapper,
                                             UserService userService, CampaignResourceRepository campaignResourceRepository,
                                             CampaignEmailResourceRepository campaignEmailResourceRepository,
                                             CampaignEmailResourceMapper campaignEmailResourceMapper,
                                             CampaignEmailBlackListRepository campaignEmailBlackListRepository,
                                             ActionLogService actionLogService, EntityManager entityManager) {
        this.campaignEmailMarketingRepository = campaignEmailMarketingRepository;
        this.campaignEmailMarketingMapper = campaignEmailMarketingMapper;
        this.userService = userService;
        this.campaignResourceRepository = campaignResourceRepository;
        this.campaignEmailResourceRepository = campaignEmailResourceRepository;
        this.campaignEmailResourceMapper = campaignEmailResourceMapper;
        this.campaignEmailBlackListRepository = campaignEmailBlackListRepository;
        this.actionLogService = actionLogService;
        this.entityManager = entityManager;
    }

    @Override
    public Page<CampaignEmailMarketingDTO> search(Long id, String fromDate, String toDate, String status, Pageable pageable) {
        if (!DataUtil.isNullOrEmpty(fromDate)) {
            fromDate += " 00:00:00";
        }
        if (!DataUtil.isNullOrEmpty(toDate)) {
            toDate += " 23:59:00";
        }
        Page<Object[]> page = campaignEmailMarketingRepository.search(id, fromDate.equals("") ? 0L : 1L, fromDate, toDate.equals("") ? 0L : 1L, toDate, Long.valueOf(status), pageable);
        List<Object[]> lst = page.getContent();
        List<CampaignEmailMarketingDTO> lstDto = new ArrayList<>();
        for (Object[] obj : lst) {
            CampaignEmailMarketingDTO dto = new CampaignEmailMarketingDTO();
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
            dto.setIsUsed(DataUtil.safeToString(obj[++index]));
            if (!DataUtil.isNullOrEmpty(dto.getCreateUser())) {
                Object[] objects = campaignResourceRepository.getUserName(dto.getCreateUser());
                if (!DataUtil.isNullOrEmpty(objects)) {
                    dto.setCreateUserName(DataUtil.safeToString(objects[0]));
                }
            }
            List<CampaignEmailResource> list = campaignEmailResourceRepository.findByCampaignEmailMarketingIdAndSendStatusNotLike(dto.getId(), "0");
            dto.setTotalData(list.isEmpty() ? 0L : (long) list.size());
            if (!DataUtil.isNullOrEmpty(dto.getCreateDateTime())) {
                Date date = Date.from(dto.getCreateDateTime());
                SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                String dateView = format.format(date);
                dto.setCreateDateTimeView(dateView);
            }
            lstDto.add(dto);
        }
        return new PageImpl<>(lstDto, pageable, page.getTotalElements());
    }

    @Override
    public CampaignEmailMarketingDTO save(CampaignEmailMarketingDTO dto) {
//        dto.setStatus(Constants.STATUS_ACTIVE);
        Long check;
        if (DataUtil.isNullOrEmpty(dto.getId())) {
            check = campaignEmailMarketingRepository.checkExistsCampaignEmailMarketing(dto.getName(), -1);
            Long userId = userService.getUserIdLogin();
            dto.setCreateUser(userId);
            dto.setCreateDateTime(Instant.now());
        } else {
            check = campaignEmailMarketingRepository.checkExistsCampaignEmailMarketing(dto.getName(), Math.toIntExact(dto.getId()));
            Optional<CampaignEmailMarketing> optional = campaignEmailMarketingRepository.findById(dto.getId());
            if (optional.isPresent()) {
                dto.setCreateUser(optional.get().getCreateUser());
                dto.setCreateDateTime(optional.get().getCreateDateTime());
            }
        }

        if (check > 0) {
            throw new BadRequestAlertException("Tên đã được sử dụng, vui lòng nhập thông tin khác", "", "");
        }

        try {
            CampaignEmailMarketing campaignEmailMarketing = campaignEmailMarketingMapper.toEntity(dto);
            campaignEmailMarketing = campaignEmailMarketingRepository.save(campaignEmailMarketing);
            Optional<User> userLog = userService.getUserWithAuthorities();
            if (dto.getId() != null) {
                actionLogService.saveActionLog(new ActionLogDTO(userLog.get().getId(), Constants.ACTION_LOG_TYPE.UPDATE + "",
                    null, String.format("Cập nhật: Chiến dịch email marketing [%s]", dto.getName()),
                    new Date(), Constants.MENU_ID.EMAIL_MARKETING, Constants.MENU_ITEM_ID.campaign_email_marketing, "CONFIG_MENU_ITEM"));
            } else {
                actionLogService.saveActionLog(new ActionLogDTO(userLog.get().getId(), Constants.ACTION_LOG_TYPE.INSERT + "",
                    null, String.format("Thêm mới: Chiến dịch email marketing [%s]", dto.getName()),
                    new Date(), Constants.MENU_ID.EMAIL_MARKETING, Constants.MENU_ITEM_ID.campaign_email_marketing, "CONFIG_MENU_ITEM"));
            }

            return campaignEmailMarketingMapper.toDto(campaignEmailMarketing);
        } catch (Exception e) {
            throw new BadRequestAlertException("Lưu thất bại", "", "");
        }
    }

    @Override
    public void delete(Long id) {
        Optional<User> userLog = userService.getUserWithAuthorities();
        Optional<CampaignEmailMarketing> campaignEmailMarketing = campaignEmailMarketingRepository.findById(id);
        actionLogService.saveActionLog(new ActionLogDTO(userLog.get().getId(), Constants.ACTION_LOG_TYPE.DELETE + "",
            null, String.format("Xóa: Chiến dịch email marketing [%s]", campaignEmailMarketing.get().getName()),
            new Date(), Constants.MENU_ID.EMAIL_MARKETING, Constants.MENU_ITEM_ID.campaign_email_marketing, "CONFIG_MENU_ITEM"));
        campaignEmailMarketingRepository.deleteById(id);
    }

    @Override
    public List<CampaignEmailMarketingDTO> findAll() {
        List<Object[]> lst = campaignEmailMarketingRepository.getToCombobox();
        List<CampaignEmailMarketingDTO> campaignEmailMarketingDTOList = new ArrayList<>();
        if (!lst.isEmpty()) {
            for (Object[] obj : lst) {
                int index = -1;
                CampaignEmailMarketingDTO dto = new CampaignEmailMarketingDTO();
                dto.setId(DataUtil.safeToLong(obj[++index]));
                dto.setName(DataUtil.safeToString(obj[++index]));
                campaignEmailMarketingDTOList.add(dto);
            }
            return campaignEmailMarketingDTOList;
        }
        return null;
    }

    @Override
    public List<CampaignEmailMarketingDTO> findAllSearch() {
        List<Object[]> lst = campaignEmailMarketingRepository.getToComboboxSearch();
        List<CampaignEmailMarketingDTO> campaignEmailMarketingDTOList = new ArrayList<>();
        if (!lst.isEmpty()) {
            for (Object[] obj : lst) {
                int index = -1;
                CampaignEmailMarketingDTO dto = new CampaignEmailMarketingDTO();
                dto.setId(DataUtil.safeToLong(obj[++index]));
                dto.setName(DataUtil.safeToString(obj[++index]));
                campaignEmailMarketingDTOList.add(dto);
            }
            return campaignEmailMarketingDTOList;
        }
        return null;
    }

    @Override
    public CampaignEmailMarketingDTO findOne(Long id) {
        Optional<CampaignEmailMarketing> optional = campaignEmailMarketingRepository.findById(id);
        if (optional.isPresent()) {
            CampaignEmailMarketingDTO dto = campaignEmailMarketingMapper.toDto(optional.get());
            SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
            dto.setStartDateView(format.format(Date.from(dto.getStartDate())));
            dto.setEndDateView(format.format(Date.from(dto.getEndDate())));
            return dto;
        }
        return null;
    }

    @Override
    public List<CampaignEmailMarketingDTO> findAllOrderByNameASC() {
        List<CampaignEmailMarketing> campaignEmailMarketingList = campaignEmailMarketingRepository
            .findAllByStatusOrderByNameAsc(Constants.STATUS_ACTIVE);
        return campaignEmailMarketingMapper.toDto(campaignEmailMarketingList);
    }

    @Override
    public Map<String, Long> importFile(MultipartFile file, Long id, Long duplicateFilter) {
        String extension = FilenameUtils.getExtension(file.getOriginalFilename());
        if (extension != null && !extension.equalsIgnoreCase("xlsx") && !extension.equalsIgnoreCase("xls")) {
            throw new BadRequestAlertException("File import không đúng định dạng file excel", "", "");
        }
        long size = file.getSize();
        if (size > 5120000) {
            throw new BusinessException(ErrorCodeResponse.MAX_FILE_SIZE);
//            throw new BadRequestAlertException("File import có dung lượng không được vượt quá 10MB", "", "");
        }
        List<CampaignEmailResourceDTO> lstImport = readFile(file);
        Map<String, List<CampaignEmailResourceDTO>> rs = validateImport(lstImport, id);
        List<CampaignEmailResourceDTO> lstSuccess = new ArrayList<>();
        List<CampaignEmailResourceDTO> lstError = new ArrayList<>();
        List<CampaignEmailResourceDTO> lstBlackList = new ArrayList<>();
        for (Map.Entry<String, List<CampaignEmailResourceDTO>> entry : rs.entrySet()) {
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
        List<CampaignEmailResourceDTO> lstSuccessAfterDuplicateFilter = duplicateFilter(lstSuccess, duplicateFilter, id);
        Long userId = userService.getUserIdLogin();
        if (lstSuccessAfterDuplicateFilter != null) {
            for (CampaignEmailResourceDTO dto : lstSuccessAfterDuplicateFilter) {
                dto.setCampaignEmailMarketingId(id);
                dto.setCreateDateTime(Instant.now());
                dto.setCreateUser(userId);
                dto.setSendStatus("1");
                campaignEmailResourceRepository.save(campaignEmailResourceMapper.toEntity(dto));
            }
            Optional<CampaignEmailMarketing> optional = campaignEmailMarketingRepository.findById(id);
            if (optional.isPresent()) {
                CampaignEmailMarketing campaignEmailMarketing = optional.get();
                campaignEmailMarketing.setIsUsed("1");
                campaignEmailMarketingRepository.save(campaignEmailMarketing);
            }

        }
        Map<String, Long> resultMap = new HashMap<>();
        resultMap.put("total", (long) lstImport.size());
        if (lstSuccessAfterDuplicateFilter != null) {
            resultMap.put("success", (long) lstSuccessAfterDuplicateFilter.size());
        } else {
            resultMap.put("success", 0L);
        }
        resultMap.put("error", (long) lstError.size());
        resultMap.put("blackList", (long) lstBlackList.size());
        Optional<User> userLog = userService.getUserWithAuthorities();
        actionLogService.saveActionLog(new ActionLogDTO(userLog.get().getId(), Constants.ACTION_LOG_TYPE.UPDATE + "",
            null, String.format("Import: Chiến dịch email marketing"),
            new Date(), Constants.MENU_ID.EMAIL_MARKETING, Constants.MENU_ITEM_ID.campaign_email_marketing, "CONFIG_MENU_ITEM"));
        return resultMap;
    }

    private Map<String, List<CampaignEmailResourceDTO>> validateImport(List<CampaignEmailResourceDTO> lstImport, Long id) {
        Map<String, List<CampaignEmailResourceDTO>> rs = new HashMap<>();
        List<CampaignEmailResourceDTO> lstEmailSuccess = new ArrayList<>();
        List<CampaignEmailResourceDTO> lstEmailError = new ArrayList<>();
        List<CampaignEmailResourceDTO> lstEmailBlackList = new ArrayList<>();
        List<CampaignEmailBlackList> blackList = campaignEmailBlackListRepository.findByCampaignEmailId(id);
        List<String> emailBlackList = new ArrayList<>();
        for (CampaignEmailBlackList campaignEmailBlackList : blackList) {
            emailBlackList.add(campaignEmailBlackList.getEmail());
        }
        for (CampaignEmailResourceDTO campaignEmailResourceDTO : lstImport) {
            boolean checkBlackList = false;
            if (campaignEmailResourceDTO.getError()) {
                lstEmailError.add(campaignEmailResourceDTO);
            } else {
                if (!validateEmail(campaignEmailResourceDTO.getEmail())) {
                    lstEmailError.add(campaignEmailResourceDTO);
                } else {
                    for (String emailBL : emailBlackList) {
                        if (emailBL.equals(campaignEmailResourceDTO.getEmail())) {
                            checkBlackList = true;
                            break;
                        }
                    }
                    if (!checkBlackList) {
                        lstEmailSuccess.add(campaignEmailResourceDTO);
                    } else {
                        lstEmailBlackList.add(campaignEmailResourceDTO);
                    }
                }
            }
        }
        rs.put("error", lstEmailError);
        rs.put("blackList", lstEmailBlackList);
        rs.put("success", lstEmailSuccess);
        return rs;
    }

    private boolean validateEmail(String email) {
        if (DataUtil.isNullOrEmpty(email)) {
            return false;
        } else {
            Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(email);
            return matcher.find();
        }
    }

    private List<CampaignEmailResourceDTO> duplicateFilter(List<CampaignEmailResourceDTO> lst, Long duplicateFilterType, Long id) {
        List<CampaignEmailResourceDTO> lstResult = new ArrayList<>();
        if (duplicateFilterType.equals(Constants.NO_DUPLICATE_FILTER)) {
            return lst;
        }
        //Loc trung tren file
        for (CampaignEmailResourceDTO campaignEmailResourceDTO : lst) {
            boolean checkDuplicate = false;
            if (lstResult.isEmpty()) {
                lstResult.add(campaignEmailResourceDTO);
            } else {
                for (CampaignEmailResourceDTO dto : lstResult) {
                    if (dto.getEmail().equals(campaignEmailResourceDTO.getEmail())) {
                        checkDuplicate = true;
                        break;
                    }
                }
                if (!checkDuplicate) {
                    lstResult.add(campaignEmailResourceDTO);
                }
            }
        }
        if (duplicateFilterType.equals(Constants.DUPLICATE_FILTER_IN_FILE)) {
            List<CampaignEmailResourceDTO> lstResultOne = new ArrayList<>();
            List<CampaignEmailResource> lstInCampaign = campaignEmailResourceRepository.findByCampaignEmailMarketingIdAndSendStatusNotLike(id, "0");
            for (CampaignEmailResourceDTO data : lstResult) {
                boolean isCheck = false;
                for (CampaignEmailResource check : lstInCampaign) {
                    if (data.getEmail().toLowerCase().equals(check.getEmail().toLowerCase())) {
                        isCheck = true;
                        break;
                    }
                }
                if (!isCheck) {
                    lstResultOne.add(data);
                }
            }
            return lstResultOne;
        } else if (duplicateFilterType.equals(Constants.DUPLICATE_FILTER_IN_CAMPAIGN)) {
            List<CampaignEmailResourceDTO> lstResultOne = new ArrayList<>();
            List<CampaignEmailResource> lstInCampaign = campaignEmailResourceRepository.findBySendStatusNotLike("0");

            for (CampaignEmailResourceDTO data : lstResult) {
                boolean isCheck = false;
                for (CampaignEmailResource check : lstInCampaign) {
                    if (data.getEmail().toLowerCase().equals(check.getEmail().toLowerCase())) {
                        isCheck = true;
                        break;
                    }
                }
                if (!isCheck) {
                    lstResultOne.add(data);
                }
            }
            return lstResultOne;
        }
        return null;
    }


    private List<CampaignEmailResourceDTO> readFile(MultipartFile file) {
        List<CampaignEmailResourceDTO> lst = new ArrayList<>();
        try {
            Workbook workbook = WorkbookFactory.create(file.getInputStream());
            Sheet sheet = workbook.getSheetAt(0);
            for (Row row : sheet) {
                if (row.getRowNum() > 0) {
                    CampaignEmailResourceDTO dto = new CampaignEmailResourceDTO();
                    dto.setError(false);
                    for (int i = 0; i <= 10; i++) {
                        Cell cell = row.getCell(i);
                        if (!DataUtil.isNullOrEmpty(cell)) {
                            DataFormatter df = new DataFormatter();
                            String cellValue = df.formatCellValue(cell);
                            if (i == 0) {
                                dto.setEmail(DataUtil.safeToString(cellValue));
                                if (cellValue.length() > 50) {
                                    dto.setError(true);
                                }
                            }
                            if (i == 1) {
                                dto.setC1(DataUtil.safeToString(cellValue));
                                if (cellValue.length() > 200) {
                                    dto.setError(true);
                                }
                            }
                            if (i == 2) {
                                dto.setC2(DataUtil.safeToString(cellValue));
                                if (cellValue.length() > 200) {
                                    dto.setError(true);
                                }
                            }
                            if (i == 3) {
                                dto.setC3(DataUtil.safeToString(cellValue));
                                if (cellValue.length() > 200) {
                                    dto.setError(true);
                                }
                            }
                            if (i == 4) {
                                dto.setC4(DataUtil.safeToString(cellValue));
                                if (cellValue.length() > 200) {
                                    dto.setError(true);
                                }
                            }
                            if (i == 5) {
                                dto.setC5(DataUtil.safeToString(cellValue));
                                if (cellValue.length() > 200) {
                                    dto.setError(true);
                                }
                            }
                            if (i == 6) {
                                dto.setC6(DataUtil.safeToString(cellValue));
                                if (cellValue.length() > 200) {
                                    dto.setError(true);
                                }
                            }
                            if (i == 7) {
                                dto.setC7(DataUtil.safeToString(cellValue));
                                if (cellValue.length() > 200) {
                                    dto.setError(true);
                                }
                            }
                            if (i == 8) {
                                dto.setC8(DataUtil.safeToString(cellValue));
                                if (cellValue.length() > 200) {
                                    dto.setError(true);
                                }
                            }
                            if (i == 9) {
                                dto.setC9(DataUtil.safeToString(cellValue));
                                if (cellValue.length() > 200) {
                                    dto.setError(true);
                                }
                            }
                            if (i == 10) {
                                dto.setC10(DataUtil.safeToString(cellValue));
                                if (cellValue.length() > 200) {
                                    dto.setError(true);
                                }
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
}
