package com.fis.crm.service.impl;

import com.fis.crm.commons.DataUtil;
import com.fis.crm.commons.FileUtils;
import com.fis.crm.config.Constants;
import com.fis.crm.domain.CampaignEmailBlackList;
import com.fis.crm.domain.CampaignEmailBlackList_;
import com.fis.crm.domain.CampaignEmailMarketing;
import com.fis.crm.domain.User;
import com.fis.crm.repository.CampaignEmailBlackListRepository;
import com.fis.crm.repository.CampaignEmailMarketingRepository;
import com.fis.crm.service.ActionLogService;
import com.fis.crm.service.CampaignEmailBlackListService;
import com.fis.crm.service.UserService;
import com.fis.crm.service.dto.ActionLogDTO;
import com.fis.crm.service.dto.CampaignEmailBlackListDTO;
import com.fis.crm.service.dto.UserDTO;
import com.fis.crm.service.mapper.CampaignEmailBlackListMapper;
import com.fis.crm.web.rest.errors.BadRequestAlertException;
import com.fis.crm.web.rest.errors.BusinessException;
import com.fis.crm.web.rest.errors.ErrorCodeResponse;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
@Transactional
public class CampaignEmailBlackListServiceImpl implements CampaignEmailBlackListService {

    private final Logger log = LoggerFactory.getLogger(CampaignEmailBlackListServiceImpl.class);

    final
    CampaignEmailBlackListRepository campaignEmailBlackListRepository;

    final
    CampaignEmailBlackListMapper campaignEmailBlackListMapper;

    final
    CampaignEmailMarketingRepository campaignEmailMarketingRepository;

    final
    UserService userService;

    final
    EntityManager entityManager;
    private final ActionLogService actionLogService;

    public CampaignEmailBlackListServiceImpl(CampaignEmailBlackListRepository campaignEmailBlackListRepository, CampaignEmailBlackListMapper campaignEmailBlackListMapper, EntityManager entityManager, UserService userService, CampaignEmailMarketingRepository campaignEmailMarketingRepository, ActionLogService actionLogService) {
        this.campaignEmailBlackListRepository = campaignEmailBlackListRepository;
        this.campaignEmailBlackListMapper = campaignEmailBlackListMapper;
        this.entityManager = entityManager;
        this.userService = userService;
        this.campaignEmailMarketingRepository = campaignEmailMarketingRepository;
        this.actionLogService = actionLogService;
    }

    @Override
    public Page<CampaignEmailBlackListDTO> findCampaignEmailBlackList(Long campaignEmailId,
                                                                      String email,
                                                                      String fullName,
                                                                      Pageable pageable) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<CampaignEmailBlackList> criteriaQuery = cb.createQuery(CampaignEmailBlackList.class);
        Root<CampaignEmailBlackList> root = criteriaQuery.from(CampaignEmailBlackList.class);
        List<Predicate> predicates = new ArrayList<>();
        if (campaignEmailId != null) {
            predicates.add(cb.equal(root.get(CampaignEmailBlackList_.CAMPAIGN_EMAIL_ID), campaignEmailId));
        }
        if (email != null) {
            predicates.add(cb.like(cb.lower(root.get(CampaignEmailBlackList_.EMAIL)), "%" + email.toLowerCase() + "%"));
        }
        if (fullName != null) {
            predicates.add(cb.like(cb.lower(root.get(CampaignEmailBlackList_.FULL_NAME)), "%" + fullName.toLowerCase() + "%"));
        }
        predicates.add(cb.equal(root.get(CampaignEmailBlackList_.STATUS), Constants.STATUS_ACTIVE.toString()));
        criteriaQuery.where(cb.and(predicates.toArray(new Predicate[0])));
        criteriaQuery.orderBy(cb.desc(root.get(CampaignEmailBlackList_.CREATE_DATE_TIME)));

        List<CampaignEmailBlackList> rs = entityManager.createQuery(criteriaQuery)
            .setFirstResult((int) pageable.getOffset())
            .setMaxResults(pageable.getPageSize())
            .getResultList();

        rs.forEach(campaignEmailBlackList -> {
            Optional<UserDTO> userDTO = userService.findFirstById(campaignEmailBlackList.getCreateUser());
            userDTO.ifPresent(dto -> campaignEmailBlackList.setCreateUserName(dto.getFullName()));
        });

        CriteriaQuery<Long> countQuery = cb.createQuery(Long.class);
        Root<CampaignEmailBlackList> rootCount = countQuery.from(CampaignEmailBlackList.class);
        countQuery.select(cb.count(rootCount)).where(cb.and(predicates.toArray(new Predicate[predicates.size()])));
        Long count = entityManager.createQuery(countQuery).getSingleResult();
        List<CampaignEmailBlackListDTO> rsDTOs = rs.stream().map(campaignEmailBlackListMapper::toDto).collect(Collectors.toList());
        return new PageImpl<>(rsDTOs, pageable, count);
    }

    @Override
    public CampaignEmailBlackListDTO save(CampaignEmailBlackListDTO campaignEmailBlackListDTO) {
        Optional<User> userOptional = userService.getUserWithAuthorities();
        User user = new User();
        if (userOptional.isPresent()) {
            user = userOptional.get();
        }
        campaignEmailBlackListDTO.setStatus(Constants.STATUS_ACTIVE);
        campaignEmailBlackListDTO.setCreateDateTime(new Date());
        campaignEmailBlackListDTO.setCreateUser(user.getId());
        CampaignEmailBlackList campaignEmailBlackList = campaignEmailBlackListMapper.toEntity(campaignEmailBlackListDTO);
        CampaignEmailBlackList save = campaignEmailBlackListRepository.save(campaignEmailBlackList);
        Optional<User> userLog = userService.getUserWithAuthorities();
        actionLogService.saveActionLog(new ActionLogDTO(userLog.get().getId(), Constants.ACTION_LOG_TYPE.INSERT + "",
            null, String.format("Thêm mới: Danh sách đen gửi email [%s]", campaignEmailBlackListDTO.getEmail()),
            new Date(), Constants.MENU_ID.EMAIL_MARKETING, Constants.MENU_ITEM_ID.campaign_email_blacklist, "CONFIG_MENU_ITEM"));
        return campaignEmailBlackListMapper.toDto(save);
    }

    @Override
    public CampaignEmailBlackListDTO delete(Long id) {
        Optional<CampaignEmailBlackList> campaignEmailBlackListOptional = campaignEmailBlackListRepository.findById(id);
        CampaignEmailBlackList campaignEmailBlackList = new CampaignEmailBlackList();
        if (campaignEmailBlackListOptional.isPresent()) {
            campaignEmailBlackList = campaignEmailBlackListOptional.get();
            campaignEmailBlackList.setStatus(Constants.STATUS_INACTIVE.toString());
            Optional<User> userLog = userService.getUserWithAuthorities();
            actionLogService.saveActionLog(new ActionLogDTO(userLog.get().getId(), Constants.ACTION_LOG_TYPE.DELETE + "",
                null, String.format("Xóa: Danh sách đen gửi email [%s]", campaignEmailBlackListOptional.get().getEmail()),
                new Date(), Constants.MENU_ID.EMAIL_MARKETING, Constants.MENU_ITEM_ID.campaign_email_blacklist, "CONFIG_MENU_ITEM"));
        } else {
            throw new RuntimeException("Id not found");
        }
        return campaignEmailBlackListMapper.toDto(campaignEmailBlackListRepository.save(campaignEmailBlackList));
    }

    @Override
    public Boolean isExistEmail(String email) {
        CampaignEmailBlackList campaignEmailBlackList = campaignEmailBlackListRepository
            .findCampaignEmailBlackListByEmailAndStatus(email, Constants.STATUS_ACTIVE);
        return campaignEmailBlackList != null;
    }

    @Override
    public Boolean isExistEmail(Long campaignId,String email) {
        List<CampaignEmailBlackList> ls=campaignEmailBlackListRepository.findByCampaignEmailId(campaignId);
        if(ls!=null)
        {
            for(CampaignEmailBlackList c:ls)
            {
                if(c.getEmail().equals(email))
                    return true;
            }
        }
        return false;
    }

    @Override
    public CampaignEmailBlackListDTO importFile(MultipartFile file, Long campaignEmailId) {
        CampaignEmailBlackListDTO result = new CampaignEmailBlackListDTO();
        int total = 0, error = 0, success = 0, duplicate = 0;
        try {
            String fileName = file.getOriginalFilename();
            int validFile = FileUtils.validateAttachFileExcel(file, fileName);
            if (validFile != 0) {
                result.setErrorCodeConfig("Lỗi định dạng file");
                return result;
            } else {
                Workbook workbook = null;
                if (fileName.contains(".xlsx")) {
                    workbook = new XSSFWorkbook(file.getInputStream());
                } else {
                    workbook = new HSSFWorkbook(file.getInputStream());
                }
//                XSSFWorkbook workbook = new XSSFWorkbook(file.getInputStream());
                Sheet worksheet = workbook.getSheetAt(0);
                //ConfigScheduleDTO temp = new ConfigScheduleDTO();
                Iterator<Row> rowIterator = worksheet.rowIterator();
                List<Row> lstRow = new ArrayList<>();
                while (rowIterator.hasNext()) {
                    Row row = rowIterator.next();
                    lstRow.add(row);
                }
                if (lstRow.size() <= 1) {
                    result.setErrorCodeConfig("File rỗng");
                    return result;
                } else {
                    List<CampaignEmailBlackListDTO> listConfig = new ArrayList<>();
                    Cell headerCellError = lstRow.get(1).createCell(4); //set header
                    headerCellError.setCellValue("Thông tin lỗi");
                    for (int i = 2; i < lstRow.size(); i++) {
                        Row row = lstRow.get(i);
                        Workbook wb = row.createCell(4).getRow().getSheet().getWorkbook();
                        Cell xssfCell = row.createCell(4);
                        CellStyle cellStyle = wb.createCellStyle();
                        Font font = wb.createFont();
                        font.setBold(true);
                        font.setColor(IndexedColors.RED.getIndex());
                        cellStyle.setFont(font);
                        cellStyle.setWrapText(true);
                        cellStyle.setFillForegroundColor(IndexedColors.RED.getIndex());
                        xssfCell.setCellStyle(cellStyle);
                        DataFormatter formatter = new DataFormatter();
                        if (row.getCell(0) == null || row.getCell(1) == null) {
                            //cellError
                            xssfCell.setCellValue("Dữ liệu dòng chưa đủ");
                            result.setErrorCodeConfig("Dữ liệu dòng chưa đủ");
                            xssfCell.setCellStyle(cellStyle);
                            continue;
                        }
                        if (!StringUtils.isNotEmpty(formatter.formatCellValue(row.getCell(0))) &&
                            !StringUtils.isNotEmpty(formatter.formatCellValue(row.getCell(1))) &&
                            !StringUtils.isNotEmpty(formatter.formatCellValue(row.getCell(2)))) {

                            xssfCell.setCellValue("Dữ liệu không đúng format");
                            xssfCell.setCellStyle(cellStyle);
                            result.setErrorCodeConfig("Dữ liệu không đúng format");
                            continue;
                        }
                        StringBuilder errorInfo = new StringBuilder();
                        boolean checkFormat = true;
                        Pattern VALID_EMAIL_ADDRESS_REGEX = Pattern.compile("\\b[\\w.%-]+@[-.\\w]+\\.[A-Za-z]{2,4}\\b");
//                        if (!row.getCell(1).getCellType().toString().equals("NUMERIC")) {
//                            errorInfo.append("'ID email tiếp thị' không đúng định dạng\n");
//                            checkFormat = false;
//                        }
                        if (!row.getCell(1).getCellType().toString().equals("STRING")) {
                            errorInfo.append("'Email' phải ở dạng chữ\n");
                            checkFormat = false;
                        } else if (!VALID_EMAIL_ADDRESS_REGEX.matcher(row.getCell(1).getStringCellValue()).matches()) {
                            errorInfo.append("'Email' không đúng định dạng\n");
                            checkFormat = false;
                        }
                        if (!DataUtil.isNullOrEmpty(row.getCell(2))) {
                            if (!row.getCell(2).getCellType().toString().equals("STRING")) {
                                errorInfo.append("'Họ và tên' không đúng định dạng\n");
                                checkFormat = false;
                            }
                        }

                        if (!checkFormat) {
                            xssfCell.setCellValue(errorInfo.toString());
                            xssfCell.setCellStyle(cellStyle);
                            result.setErrorCodeConfig(errorInfo.toString() + "\n");
                            error++;
                            continue;
                        }

                        CampaignEmailBlackListDTO config = new CampaignEmailBlackListDTO();
//                        config.setCampaignEmailId((long) row.getCell(1).getNumericCellValue());
                        config.setCampaignEmailId(campaignEmailId);
                        config.setEmail(row.getCell(1).getStringCellValue());
                        if (!DataUtil.isNullOrEmpty(row.getCell(2))) {
                            config.setFullName(row.getCell(2).getStringCellValue());
                        }

                        List<CampaignEmailBlackList> objExisting = campaignEmailBlackListRepository.findByEmail(config.getEmail());
                        Optional<CampaignEmailMarketing> campaignEmailMarketingOptional = campaignEmailMarketingRepository.findById(config.getCampaignEmailId());
                        if (!campaignEmailMarketingOptional.isPresent()) {
                            errorInfo.append("ID email tiếp thị không tồn tại");
                            xssfCell.setCellValue(errorInfo.toString());
                            error++;
                            result.setErrorCodeConfig("ID email tiếp thị " + campaignEmailMarketingOptional.get().getId() + " không tồn tại");
                            continue;
                        }
                        if (!objExisting.isEmpty()) {
                            errorInfo.append("Email đã tồn tại");
                            xssfCell.setCellValue(errorInfo.toString());
                            duplicate++;
                            result.setErrorCodeConfig("Email " + objExisting.get(0).getEmail() + " đã tồn tại");
                            continue;
                        }
                        listConfig.add(config);
                    }
                    if (StringUtils.isEmpty(result.getErrorCodeConfig())) { //thoa man
                        for (CampaignEmailBlackListDTO rowConfigOk : listConfig) {
                            success++;
                            save(rowConfigOk);
                        }
                    } else {   //formatError == errorConfig
                        result.setXssfWorkbook(workbook);
                    }
                }
            }
            Optional<User> userLog = userService.getUserWithAuthorities();
            actionLogService.saveActionLog(new ActionLogDTO(userLog.get().getId(), Constants.ACTION_LOG_TYPE.INSERT + "",
                null, String.format("Upload: Danh sách đen gửi email file [%s]", file.getOriginalFilename()),
                new Date(), Constants.MENU_ID.EMAIL_MARKETING, Constants.MENU_ITEM_ID.campaign_email_blacklist, "CONFIG_MENU_ITEM"));
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new BadRequestAlertException("Có lỗi xảy ra", "", "");
        }
        total = success + error + duplicate;
        result.setTotal(total);
        result.setSuccess(success);
        result.setError(error);
        result.setDuplicate(duplicate);
        return result;
    }

    @Override
    public List<CampaignEmailBlackListDTO> findAllByCampaignEmailIdAndEmailLikeAndFullNameLike(Long campaignEmailId, String email, String fullName) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<CampaignEmailBlackList> criteriaQuery = cb.createQuery(CampaignEmailBlackList.class);
        Root<CampaignEmailBlackList> root = criteriaQuery.from(CampaignEmailBlackList.class);
        List<Predicate> predicates = new ArrayList<>();
        if (campaignEmailId != null) {
            predicates.add(cb.equal(root.get(CampaignEmailBlackList_.CAMPAIGN_EMAIL_ID), campaignEmailId));
        }
        if (email != null) {
            predicates.add(cb.like(root.get(CampaignEmailBlackList_.EMAIL), "%" + email + "%"));
        }
        if (fullName != null) {
            predicates.add(cb.like(root.get(CampaignEmailBlackList_.FULL_NAME), "%" + fullName + "%"));
        }
        predicates.add(cb.equal(root.get(CampaignEmailBlackList_.STATUS), Constants.STATUS_ACTIVE.toString()));
        criteriaQuery.where(cb.and(predicates.toArray(new Predicate[0])));
        criteriaQuery.orderBy(cb.desc(root.get(CampaignEmailBlackList_.CREATE_DATE_TIME)));

        List<CampaignEmailBlackList> rs = entityManager.createQuery(criteriaQuery)
            .getResultList();
        return campaignEmailBlackListMapper.toDto(rs);
    }
}
