package com.fis.crm.service.impl;

import com.fis.crm.commons.DataUtil;
import com.fis.crm.commons.FileUtils;
import com.fis.crm.config.Constants;
import com.fis.crm.domain.*;
import com.fis.crm.repository.CampaignRepository;
import com.fis.crm.repository.CampaignSMSBlackListRepository;
import com.fis.crm.repository.CampaignSMSMarketingRepository;
import com.fis.crm.service.ActionLogService;
import com.fis.crm.service.CampaignSMSBlackListService;
import com.fis.crm.service.UserService;
import com.fis.crm.service.dto.ActionLogDTO;
import com.fis.crm.service.dto.CampaignSMSBlackListDTO;
import com.fis.crm.service.dto.UserDTO;
import com.fis.crm.service.mapper.CampaignSMSBlackListMapper;
import com.fis.crm.web.rest.errors.BadRequestAlertException;
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
import java.util.stream.Collectors;

@Service
@Transactional
public class CampaignSMSBlackListServiceImpl implements CampaignSMSBlackListService {

    private final Logger log = LoggerFactory.getLogger(CampaignSMSBlackListServiceImpl.class);

    final
    CampaignSMSBlackListRepository campaignSMSBlackListRepository;

    final
    CampaignSMSBlackListMapper campaignSMSBlackListMapper;

    final
    CampaignSMSMarketingRepository campaignSMSMarketingRepository;

    final
    UserService userService;
    private final ActionLogService actionLogService;
    private final CampaignRepository campaignRepository;

    final
    EntityManager entityManager;

    public CampaignSMSBlackListServiceImpl(CampaignSMSBlackListRepository campaignSMSBlackListRepository, CampaignSMSBlackListMapper campaignSMSBlackListMapper, CampaignSMSMarketingRepository campaignSMSMarketingRepository, UserService userService, ActionLogService actionLogService, CampaignRepository campaignRepository, EntityManager entityManager) {
        this.campaignSMSBlackListRepository = campaignSMSBlackListRepository;
        this.campaignSMSBlackListMapper = campaignSMSBlackListMapper;
        this.campaignSMSMarketingRepository = campaignSMSMarketingRepository;
        this.userService = userService;
        this.actionLogService = actionLogService;
        this.campaignRepository = campaignRepository;
        this.entityManager = entityManager;
    }

    @Override
    public Page<CampaignSMSBlackListDTO> findCampaignSMSBlackList(Long campaignSMSId,
                                                                  String phoneNumber,
                                                                  String fullName,
                                                                  Pageable pageable) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<CampaignSMSBlackList> criteriaQuery = cb.createQuery(CampaignSMSBlackList.class);
        Root<CampaignSMSBlackList> root = criteriaQuery.from(CampaignSMSBlackList.class);
        List<Predicate> predicates = new ArrayList<>();
        if (campaignSMSId != null) {
            predicates.add(cb.equal(root.get(CampaignSMSBlackList_.CAMPAIGN_SM_SID), campaignSMSId));
        }
        if (phoneNumber != null) {
            predicates.add(cb.like(root.get(CampaignSMSBlackList_.PHONE_NUMBER), "%" + phoneNumber + "%"));
        }
        if (fullName != null) {
            predicates.add(cb.like(root.get(CampaignSMSBlackList_.FULL_NAME), "%" + fullName + "%"));
        }
        predicates.add(cb.equal(root.get(CampaignSMSBlackList_.STATUS), Constants.STATUS_ACTIVE));
        criteriaQuery.where(cb.and(predicates.toArray(new Predicate[0])));
        criteriaQuery.orderBy(cb.desc(root.get(CampaignSMSBlackList_.CREATE_DATE_TIME)));

        List<CampaignSMSBlackList> rs = entityManager.createQuery(criteriaQuery)
            .setFirstResult((int) pageable.getOffset())
            .setMaxResults(pageable.getPageSize())
            .getResultList();

        rs.forEach(campaignEmailBlackList -> {
            Optional<UserDTO> userDTO = userService.findFirstById(campaignEmailBlackList.getCreateUser());
            userDTO.ifPresent(dto -> campaignEmailBlackList.setCreateUserName(dto.getFullName()));
            CampaignSMSMarketing campaignSMSMarketing = campaignSMSMarketingRepository.getOne(campaignEmailBlackList.getCampaignSMSId());
            System.out.println(campaignSMSMarketing.toString());
            if (campaignSMSMarketing.getName() != null){
                campaignEmailBlackList.setCampaignSMSName(campaignSMSMarketing.getName());
            }
        });

        CriteriaQuery<Long> countQuery = cb.createQuery(Long.class);
        Root<CampaignSMSBlackList> rootCount = countQuery.from(CampaignSMSBlackList.class);
        countQuery.select(cb.count(rootCount)).where(cb.and(predicates.toArray(new Predicate[predicates.size()])));
        Long count = entityManager.createQuery(countQuery).getSingleResult();
        List<CampaignSMSBlackListDTO> rsDTOs = rs.stream().map(campaignSMSBlackListMapper::toDto).collect(Collectors.toList());
        return new PageImpl<>(rsDTOs, pageable, count);
    }

    @Override
    public CampaignSMSBlackListDTO save(CampaignSMSBlackListDTO campaignSMSBlackListDTO) {
        Optional<User> userOptional = userService.getUserWithAuthorities();
        User user = new User();
        if (userOptional.isPresent()) {
            user = userOptional.get();
        }
        campaignSMSBlackListDTO.setStatus(Constants.STATUS_ACTIVE);
        campaignSMSBlackListDTO.setCreateDateTime(new Date());
        campaignSMSBlackListDTO.setCreateUser(user.getId());
        CampaignSMSBlackList campaignSMSBlackList = campaignSMSBlackListMapper.toEntity(campaignSMSBlackListDTO);
        CampaignSMSBlackList save = campaignSMSBlackListRepository.save(campaignSMSBlackList);
        Optional<User> userLog = userService.getUserWithAuthorities();
        actionLogService.saveActionLog(new ActionLogDTO(userLog.get().getId(), Constants.ACTION_LOG_TYPE.INSERT + "",
            null, String.format("Thêm mới: Danh sách đen gửi SMS [%s]", campaignSMSBlackListDTO.getPhoneNumber()),
            new Date(), Constants.MENU_ID.SMS_MARKETING, Constants.MENU_ITEM_ID.campaign_sms_black_list, "CONFIG_MENU_ITEM"));
        return campaignSMSBlackListMapper.toDto(save);
    }

    @Override
    public CampaignSMSBlackListDTO delete(Long id) {
        Optional<CampaignSMSBlackList> campaignSMSBlackListOptional = campaignSMSBlackListRepository.findById(id);
        CampaignSMSBlackList campaignSMSBlackList = new CampaignSMSBlackList();
        if (campaignSMSBlackListOptional.isPresent()) {
            campaignSMSBlackList = campaignSMSBlackListOptional.get();
            campaignSMSBlackList.setStatus(Constants.STATUS_INACTIVE.toString());
            Optional<User> userLog = userService.getUserWithAuthorities();
            actionLogService.saveActionLog(new ActionLogDTO(userLog.get().getId(), Constants.ACTION_LOG_TYPE.INSERT + "",
                null, String.format("Xóa: Danh sách đen gửi SMS [%s]", campaignSMSBlackListOptional.get().getPhoneNumber()),
                new Date(), Constants.MENU_ID.SMS_MARKETING, Constants.MENU_ITEM_ID.campaign_sms_black_list, "CONFIG_MENU_ITEM"));
        } else {
            throw new RuntimeException("Id not found");
        }
        return campaignSMSBlackListMapper.toDto(campaignSMSBlackListRepository.save(campaignSMSBlackList));
    }

    @Override
    public Boolean isExistPhoneNumber(String phoneNumber) {
        CampaignSMSBlackList campaignSMSBlackList = campaignSMSBlackListRepository
            .findCampaignSMSBlackListByPhoneNumberAndStatus(phoneNumber, Constants.STATUS_ACTIVE);
        return campaignSMSBlackList != null;
    }

    @Override
    public Boolean isExistPhoneNumber(Long campaignId,String phoneNumber) {
        List<CampaignSMSBlackList> ls=campaignSMSBlackListRepository.findByCampaignSMSIdAndStatus(campaignId, "1");
        if(ls!=null)
        {
            for(CampaignSMSBlackList c:ls)
            {
                if(c.getPhoneNumber().equals(phoneNumber))
                    return true;
            }
        }
        return false;
    }

    @Override
    public CampaignSMSBlackListDTO importFile(MultipartFile file, Long campaignSmsId) {
        CampaignSMSBlackListDTO result = new CampaignSMSBlackListDTO();
        int total = 0, error = 0, success = 0, duplicate = 0;
        result.setErrorCodeConfig(new ArrayList<>());
        try {
            String fileName = file.getOriginalFilename();
            int validFile = FileUtils.validateAttachFileExcel(file, fileName);
            if (validFile != 0) {
                result.getErrorCodeConfig().add("Lỗi định dạng file");
                return result;
            } else {
                Workbook workbook = null;
                if (fileName.contains(".xlsx")) {
                    workbook = new XSSFWorkbook(file.getInputStream());
                } else {
                    workbook = new HSSFWorkbook(file.getInputStream());
                }
                Sheet worksheet = workbook.getSheetAt(0);
                Iterator<Row> rowIterator = worksheet.rowIterator();
                List<Row> lstRow = new ArrayList<>();
                while (rowIterator.hasNext()) {
                    Row row = rowIterator.next();
                    lstRow.add(row);
                }
                if (lstRow.size() <= 1) {
                    result.getErrorCodeConfig().add("File rỗng");
                    return result;
                } else {
                    List<CampaignSMSBlackListDTO> listConfig = new ArrayList<>();
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
                            result.getErrorCodeConfig().add("Dữ liệu dòng chưa đủ");
                            xssfCell.setCellStyle(cellStyle);
                            continue;
                        }
                        if (!StringUtils.isNotEmpty(formatter.formatCellValue(row.getCell(0))) &&
                            !StringUtils.isNotEmpty(formatter.formatCellValue(row.getCell(1))) &&
                            !StringUtils.isNotEmpty(formatter.formatCellValue(row.getCell(2)))) {

                            xssfCell.setCellValue("Dữ liệu không đúng format");
                            xssfCell.setCellStyle(cellStyle);
                            result.getErrorCodeConfig().add("Dữ liệu không đúng format");
                            continue;
                        }
                        StringBuilder errorInfo = new StringBuilder();
                        boolean checkFormat = true;
                        if(!row.getCell(1).getCellType().toString().equals("STRING")){
                            errorInfo.append("Số điện thoại "+row.getCell(1).toString()+"không đúng định dạng\n");
                            checkFormat = false;
                        }else if (!row.getCell(1).getStringCellValue().matches("^(84|0[3|5|7|8|9])([0-9]{8,14})$")) {
                            errorInfo.append(" Số điện thoại "+ row.getCell(1).getStringCellValue()+" không đúng định dạng\n" );
                            checkFormat = false;
                        }
                        if (!DataUtil.isNullOrEmpty(row.getCell(2))) {
                            if (!row.getCell(2).getCellType().toString().equals("STRING")) {
                                errorInfo.append("Họ và tên không đúng định dạng\n");
                                checkFormat = false;
                            }
                        }

                        if (!checkFormat) {
                            error++;
                            xssfCell.setCellValue(errorInfo.toString());
                            xssfCell.setCellStyle(cellStyle);
                            result.getErrorCodeConfig().add(errorInfo.toString());
                            continue;
                        }

                        CampaignSMSBlackListDTO config = new CampaignSMSBlackListDTO();
                        config.setCampaignSMSId(campaignSmsId);
                        config.setPhoneNumber(row.getCell(1).getStringCellValue());
                        if (!DataUtil.isNullOrEmpty(row.getCell(2))) {
                            config.setFullName(row.getCell(2).getStringCellValue());
                        }

                        List<CampaignSMSBlackList> objExisting = campaignSMSBlackListRepository.findByPhoneNumber(config.getPhoneNumber());
                        Optional<CampaignSMSMarketing> campaignSMSMarketingOptional = campaignSMSMarketingRepository.findById(config.getCampaignSMSId());
                        if (!campaignSMSMarketingOptional.isPresent()) {
                            errorInfo.append("ID sms tiếp thị không tồn tại");
                            xssfCell.setCellValue(errorInfo.toString());
                            result.getErrorCodeConfig().add("ID sms tiếp thị "+campaignSMSMarketingOptional.get().getId()+" không tồn tại\n");
                            continue;
                        }
                        if (!objExisting.isEmpty()) {
                            duplicate++;
                            errorInfo.append("Số điện thoại đã tồn tại");
                            xssfCell.setCellValue(errorInfo.toString());
                            result.getErrorCodeConfig().add("Số điện thoại " + objExisting.get(0).getPhoneNumber() + " đã tồn tại\n");
                            continue;
                        }
                        listConfig.add(config);
                    }
//                    if (StringUtils.isEmpty(result.getErrorCodeConfig())) { //thoa man
                    if(result.getErrorCodeConfig().size()==0){
                        for (CampaignSMSBlackListDTO rowConfigOk : listConfig) {
                            success++;
                            save(rowConfigOk);
                        }
                    } else {   //formatError == errorConfig
                        result.setXssfWorkbook(workbook);
                    }
                }
            }
            Optional<Campaign> campaign = campaignRepository.findById(campaignSmsId);
            Optional<User> userLog = userService.getUserWithAuthorities();
            actionLogService.saveActionLog(new ActionLogDTO(userLog.get().getId(), Constants.ACTION_LOG_TYPE.INSERT + "",
                null, String.format("Import: Danh sách đen gửi SMS"),
                new Date(), Constants.MENU_ID.SMS_MARKETING, Constants.MENU_ITEM_ID.campaign_sms_black_list, "CONFIG_MENU_ITEM"));
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
    public List<CampaignSMSBlackListDTO> findAllByCampaignSMSIdAndPhoneNumberLikeAndFullNameLike(Long campaignSMSId,
                                                                                                 String phoneNumber,
                                                                                                 String fullName) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<CampaignSMSBlackList> criteriaQuery = cb.createQuery(CampaignSMSBlackList.class);
        Root<CampaignSMSBlackList> root = criteriaQuery.from(CampaignSMSBlackList.class);
        List<Predicate> predicates = new ArrayList<>();
        if (campaignSMSId != null) {
            predicates.add(cb.equal(root.get(CampaignSMSBlackList_.CAMPAIGN_SM_SID), campaignSMSId));
        }
        if (phoneNumber != null) {
            predicates.add(cb.like(root.get(CampaignSMSBlackList_.PHONE_NUMBER), "%" + phoneNumber + "%"));
        }
        if (fullName != null) {
            predicates.add(cb.like(root.get(CampaignSMSBlackList_.FULL_NAME), "%" + fullName + "%"));
        }
        predicates.add(cb.equal(root.get(CampaignSMSBlackList_.STATUS), Constants.STATUS_ACTIVE.toString()));
        criteriaQuery.where(cb.and(predicates.toArray(new Predicate[0])));
        criteriaQuery.orderBy(cb.desc(root.get(CampaignSMSBlackList_.CREATE_DATE_TIME)));

        List<CampaignSMSBlackList> rs = entityManager.createQuery(criteriaQuery)
            .getResultList();
        return campaignSMSBlackListMapper.toDto(rs);
    }
}
