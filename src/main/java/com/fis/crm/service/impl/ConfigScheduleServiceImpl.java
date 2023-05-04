package com.fis.crm.service.impl;

import com.fis.crm.commons.DataUtil;
import com.fis.crm.commons.DateUtil;
import com.fis.crm.commons.FileUtils;
import com.fis.crm.commons.Translator;
import com.fis.crm.config.Constants;
import com.fis.crm.domain.ConfigSchedule;
import com.fis.crm.domain.OptionSetValue;
import com.fis.crm.domain.User;
import com.fis.crm.repository.ConfigScheduleRepository;
import com.fis.crm.repository.OptionSetValueRepository;
import com.fis.crm.service.ActionLogService;
import com.fis.crm.service.ConfigScheduleService;
import com.fis.crm.service.UserService;
import com.fis.crm.service.dto.ActionLogDTO;
import com.fis.crm.service.dto.ConfigScheduleDTO;
import com.fis.crm.service.dto.ServiceResult;
import com.fis.crm.service.mapper.ConfigScheduleMapper;
import com.fis.crm.web.rest.errors.BadRequestAlertException;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.hibernate.id.IdentifierGenerator.ENTITY_NAME;

/**
 * Service Implementation for managing {@link ConfigSchedule}.
 */
@Service
@Transactional
public class ConfigScheduleServiceImpl implements ConfigScheduleService {

    private final Logger log = LoggerFactory.getLogger(ConfigScheduleServiceImpl.class);

    private final ConfigScheduleRepository configScheduleRepository;
    private final ConfigScheduleMapper configScheduleMapper;
    private final OptionSetValueRepository optionSetValueRepository;
    private final ActionLogService actionLogService;
    @Autowired
    private ActionLogServiceImpl actionLogServiceImpl;
    @Autowired
    private UserService userService;

    public ConfigScheduleServiceImpl(ConfigScheduleRepository configScheduleRepository, ConfigScheduleMapper configScheduleMapper,
                                     OptionSetValueRepository optionSetValueRepository, ActionLogService actionLogService) {
        this.configScheduleRepository = configScheduleRepository;
        this.configScheduleMapper = configScheduleMapper;
        this.optionSetValueRepository = optionSetValueRepository;
        this.actionLogService = actionLogService;
    }

    @Override
    public ConfigScheduleDTO save(ConfigScheduleDTO configScheduleDTO) {
        log.debug("Request to save ConfigSchedule : {}", configScheduleDTO);
        Optional<User> userOptional = userService.getUserWithAuthorities();
        if (!userOptional.isPresent()) {
            throw new BadRequestAlertException(Translator.toLocale("configSchedule.userNotExis"), ENTITY_NAME, "configSchedule.userNotExis");
        }
        User user = userOptional.get();
        Instant now = Instant.now();
        if (configScheduleDTO.getId() == null)  //add new
        {
            configScheduleDTO.setCreateUser(user.getId());
            configScheduleDTO.setCreateDate(now);
            configScheduleDTO.setUpdateUser(user.getId());
            configScheduleDTO.setUpdateDate(now);
            configScheduleDTO.setStatus("1");
        } else {  //update
            configScheduleDTO.setUpdateUser(user.getId());
            configScheduleDTO.setUpdateDate(now);
        }
        ConfigSchedule configSchedule = configScheduleMapper.toEntity(configScheduleDTO);
        //validate new
        if (configScheduleDTO.getId() == null) {
            List<ConfigSchedule> objExisting = configScheduleRepository.findOneByRequestTypeAndBussinessType(configScheduleDTO.getRequestType(), configScheduleDTO.getBussinessType());
            if (!objExisting.isEmpty()) {
                throw new BadRequestAlertException(Translator.toLocale("configSchedule.idExisted"), "configSchedule", "configSchedule.idExisted");
            }
        }
        //validate update
        else if (configScheduleDTO.getId() != null) {
            Optional<ConfigScheduleDTO> oldObj = configScheduleRepository.findById(configScheduleDTO.getId()).map(configScheduleMapper::toDto);
            if (!oldObj.isPresent()) {
                throw new BadRequestAlertException(Translator.toLocale("configSchedule.idNotExisted"), "configSchedule", "configSchedule.idNotExisted");
            }
            List<ConfigSchedule> objExisting = configScheduleRepository.findOneByRequestTypeAndBussinessType(configScheduleDTO.getRequestType(), configScheduleDTO.getBussinessType());
            if (!objExisting.isEmpty() && !objExisting.get(0).getId().equals(configScheduleDTO.getId())) {
                //throw new BadRequestAlertException("A configSchedule with request_type and bussiness_type already exist", "CONFIG_SCHEDULE", "objExists");
                throw new BadRequestAlertException(Translator.toLocale("configSchedule.idExisted"), "configSchedule", "configSchedule.idExisted");
            }
            //set lai gia tri ko dc thay doi
            configSchedule.setStatus(oldObj.get().getStatus());
            configSchedule.setCreateUser(oldObj.get().getCreateUser());
            configSchedule.setCreateDate(oldObj.get().getCreateDate());

        }

        configSchedule = configScheduleRepository.save(configSchedule);
        Long id = configSchedule.getId();
        Optional<ConfigSchedule> byId = configScheduleRepository.findById(id);
        Optional<OptionSetValue> byId1 = optionSetValueRepository.findById(byId.get().getRequestType().longValue());
        Optional<OptionSetValue> byId2 = optionSetValueRepository.findById(byId.get().getBussinessType().longValue());
        if (configScheduleDTO.getId() == null){
            Optional<User> userLog = userService.getUserWithAuthorities();
            actionLogService.saveActionLog(new ActionLogDTO(userLog.get().getId(), Constants.ACTION_LOG_TYPE.INSERT + "",
                null, String.format("Thêm mới cấu hình hạn sử lý và hạn xác nhận: [%s - %s]", byId1.get().getName(), byId2.get().getName()),
                new Date(), Constants.MENU_ID.VOC, Constants.MENU_ITEM_ID.configVOC, "CONFIG_MENU_ITEM"));
        } else {
            Optional<User> userLog = userService.getUserWithAuthorities();
            actionLogService.saveActionLog(new ActionLogDTO(userLog.get().getId(), Constants.ACTION_LOG_TYPE.UPDATE + "",
                null, String.format("Cập nhật cấu hình hạn sử lý và hạn xác nhận: [%s - %s]", byId1.get().getName(), byId2.get().getName()),
                new Date(), Constants.MENU_ID.VOC, Constants.MENU_ITEM_ID.configVOC, "CONFIG_MENU_ITEM"));
        }
        if (configScheduleDTO.getId() == null) {
            actionLogServiceImpl.saveActionLog(user.getId(), Constants.ACTION_LOG_TYPE.INSERT + "", configSchedule.getId(), "Config_schedule", "", now);
        } else
            actionLogServiceImpl.saveActionLog(user.getId(), Constants.ACTION_LOG_TYPE.UPDATE + "", configSchedule.getId(), "Config_schedule", "", now);
        return configScheduleMapper.toDto(configSchedule);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ConfigScheduleDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ConfigSchedules");
//        return configScheduleRepository.findAll(pageable)
//            .map(configScheduleMapper::toDto);
        return configScheduleRepository.getAllConfigSchedule(pageable)
            .map(ConfigScheduleDTO::new);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<ConfigScheduleDTO> findOne(Long id) {
        log.debug("Request to get ConfigSchedule : {}", id);
        return configScheduleRepository.findById(id)
            .map(configScheduleMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete ConfigSchedule : {}", id);
        Optional<User> userLog = userService.getUserWithAuthorities();
        Optional<ConfigSchedule> byId = configScheduleRepository.findById(id);
        Optional<OptionSetValue> byId1 = optionSetValueRepository.findById(byId.get().getRequestType().longValue());
        Optional<OptionSetValue> byId2 = optionSetValueRepository.findById(byId.get().getBussinessType().longValue());
        actionLogService.saveActionLog(new ActionLogDTO(userLog.get().getId(), Constants.ACTION_LOG_TYPE.DELETE + "",
            null, String.format("Xóa cấu hình hạn sử lý và hạn xác nhận: [%s - %s]", byId1.get().getName(), byId2.get().getName()),
            new Date(), Constants.MENU_ID.VOC, Constants.MENU_ITEM_ID.configVOC, "CONFIG_MENU_ITEM"));
        configScheduleRepository.deleteById(id);
    }

    @Override
    public ConfigScheduleDTO importList(MultipartFile file) throws Exception {
        ConfigScheduleDTO result = new ConfigScheduleDTO();

        String name = file.getOriginalFilename();
        int validateFile = FileUtils.validateAttachFileExcel(file, name);
        Map<String, String> MapTimeType = new HashMap<>(3);
        MapTimeType.put("P", "1"); //phut - gio - ngay
        MapTimeType.put("G", "2");
        MapTimeType.put("N", "3");

        if (validateFile != 0) {
            throw new BadRequestAlertException(Translator.toLocale("configSchedule.fileImportErrorFormat"), "configSchedule", "configSchedule.fileImportErrorFormat");
        } else {
            Workbook workbook = null;
            if (name.contains(".xlsx")) {
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
                result.setErrorCodeConfig(Translator.toLocale("configSchedule.emptyFile"));
                return result;
            } else {
                List<ConfigScheduleDTO> listConfig = new ArrayList<>();
                Cell headerCellError = lstRow.get(1).createCell(8); //set header
                headerCellError.setCellValue(Translator.toLocale("configSchedule.error.information"));
                //boolean checkAllRow = true;
                for (int i = 2; i < lstRow.size(); i++) {
                    Row row = lstRow.get(i);
                    Workbook wb = row.createCell(8).getRow().getSheet().getWorkbook();
                    //style cell error
                    Cell xssfCell = row.createCell(8);
                    CellStyle cellStyle = wb.createCellStyle();
                    Font font = wb.createFont();
                    font.setBold(true);
                    font.setColor(IndexedColors.RED.getIndex());
                    cellStyle.setFont(font);
                    cellStyle.setWrapText(true);
                    cellStyle.setFillForegroundColor(IndexedColors.RED.getIndex());
                    xssfCell.setCellStyle(cellStyle);
                    DataFormatter formatter = new DataFormatter();
                    if (row.getCell(0) == null && row.getCell(1) == null && row.getCell(2) == null
                        && row.getCell(7) == null && row.getCell(3) == null &&
                        row.getCell(4) == null && row.getCell(5) == null
                        && row.getCell(6) == null)
                        continue;

                    if (row.getCell(0) == null || row.getCell(1) == null || row.getCell(2) == null
                        || row.getCell(7) == null
                    ) {
                        //cellError
                        xssfCell.setCellValue(Translator.toLocale("configSchedule.data.notEnough"));
                        result.setErrorCodeConfig("formatError");
                        xssfCell.setCellStyle(cellStyle);
                        continue;

                    } else if (!row.getCell(7).equals("D") && (row.getCell(3) == null ||
                        row.getCell(4) == null || row.getCell(5) == null
                        || row.getCell(6) == null)) {
                        xssfCell.setCellValue(Translator.toLocale("configSchedule.data.notEnough"));
                        result.setErrorCodeConfig("formatError");
                        xssfCell.setCellStyle(cellStyle);
                        continue;
                    }
                    if (!StringUtils.isNotEmpty(formatter.formatCellValue(row.getCell(0))) &&
                        !StringUtils.isNotEmpty(formatter.formatCellValue(row.getCell(1))) &&
                        !StringUtils.isNotEmpty(formatter.formatCellValue(row.getCell(2))) &&
                        !StringUtils.isNotEmpty(formatter.formatCellValue(row.getCell(3))) &&
                        !StringUtils.isNotEmpty(formatter.formatCellValue(row.getCell(4))) &&
                        !StringUtils.isNotEmpty(formatter.formatCellValue(row.getCell(5))) &&
                        !StringUtils.isNotEmpty(formatter.formatCellValue(row.getCell(6))) &&
                        !StringUtils.isNotEmpty(formatter.formatCellValue(row.getCell(7)))) {

                        xssfCell.setCellValue(Translator.toLocale("configSchedule.data.notFormat"));
                        xssfCell.setCellStyle(cellStyle);
                        result.setErrorCodeConfig("formatError");
                        continue;
                    }
                    //check format , check type
                    StringBuilder errorInfo = new StringBuilder();
                    boolean checkFormat = true;
                    if (!row.getCell(1).getCellType().toString().equals("STRING")) {
                        errorInfo.append(Translator.toLocale("configSchedule.requestType.notFormat"));
                        checkFormat = false;
                    }
//                    else if ((int) row.getCell(1).getNumericCellValue() < 1
//                        || (int) row.getCell(1).getNumericCellValue() > 3
//                    )

                    else {
                        Long requestTypeId =  optionSetValueRepository.getRequestTypeByCode(row.getCell(1).getStringCellValue());
                        if(requestTypeId==null) {
                            errorInfo.append(Translator.toLocale("configSchedule.requestType.mustCorrectFormat"));
                            checkFormat = false;
                        }
                    }
                    if (!row.getCell(2).getCellType().toString().equals("STRING")) {
                        errorInfo.append(Translator.toLocale("configSchedule.majorType.notFormat"));
                        checkFormat = false;
                    } else {
                        Long bussinessTypeId =  optionSetValueRepository.getBussinessTypeByCode(row.getCell(2).getStringCellValue());
                        if(bussinessTypeId==null) {
                            errorInfo.append(Translator.toLocale("configSchedule.requestType.mustCorrectFormat2"));
                            checkFormat = false;
                        }
                    }
                    if (row.getCell(3) != null && !row.getCell(3).getCellType().toString().equals("NUMERIC")) {
                        errorInfo.append(Translator.toLocale("configSchedule.timeRequest.notFormat"));
                        checkFormat = false;
                    }
                    if (row.getCell(4) != null && !row.getCell(4).getCellType().toString().equals("STRING")) {
                        errorInfo.append(Translator.toLocale("configSchedule.timeUnitRequest.notFormat"));
                        checkFormat = false;
                    } else if (row.getCell(4) != null && !row.getCell(4).getStringCellValue().toUpperCase().equals("P")
                        && !row.getCell(4).getStringCellValue().toUpperCase().equals("G")
                        && !row.getCell(4).getStringCellValue().toUpperCase().equals("N")
                    ) {
                        errorInfo.append(Translator.toLocale("configSchedule.timeUnit.mustCorrect"));
                        checkFormat = false;
                    }
                    if (row.getCell(5) != null && !row.getCell(5).getCellType().toString().equals("NUMERIC")) {
                        errorInfo.append(Translator.toLocale("configSchedule.timeConfirm.notFormat"));
                        checkFormat = false;
                    }
                    if (row.getCell(6) != null && !row.getCell(6).getCellType().toString().equals("STRING")) {
                        errorInfo.append(Translator.toLocale("configSchedule.timeUnitConfirm.notFormat"));
                        checkFormat = false;
                    } else if (row.getCell(6) != null && !row.getCell(6).getStringCellValue().toUpperCase().equals("P")
                        && !row.getCell(6).getStringCellValue().toUpperCase().equals("G")
                        && !row.getCell(6).getStringCellValue().toUpperCase().equals("N")
                    ) {
                        errorInfo.append(Translator.toLocale("configSchedule.timeUnitConfirm.mustCorrect"));
                        checkFormat = false;
                    }
                    if (!row.getCell(7).getCellType().toString().equals("STRING")) {
                        errorInfo.append(Translator.toLocale("configSchedule.idNotFormat"));
                        checkFormat = false;
                    } else if (!row.getCell(7).getStringCellValue().toUpperCase().equals("U")
                        && !row.getCell(7).getStringCellValue().toUpperCase().equals("N")
                        && !row.getCell(7).getStringCellValue().toUpperCase().equals("D")
                    ) {
                        errorInfo.append(Translator.toLocale("configSchedule.id.mustCorrect"));
                        checkFormat = false;
                    }

                    if (false == checkFormat) {
                        xssfCell.setCellValue(errorInfo.toString());
                        xssfCell.setCellStyle(cellStyle);
                        result.setErrorCodeConfig("formatError");
                        continue;
                    }

                    //get object from each row
                    ConfigScheduleDTO config = new ConfigScheduleDTO();
                    Long requestTypeId =  optionSetValueRepository.getRequestTypeByCode(row.getCell(1).getStringCellValue());
                    Long bussinessTypeId =  optionSetValueRepository.getBussinessTypeByCode(row.getCell(2).getStringCellValue());
                    config.setRequestType(requestTypeId);
                    config.setBussinessType(bussinessTypeId);
                    if (row.getCell(3) != null) config.setProcessTime((double) row.getCell(3).getNumericCellValue());
                    if (row.getCell(4) != null)
                        config.setProcessTimeType(MapTimeType.get(row.getCell(4).getStringCellValue().toUpperCase()));
                    if (row.getCell(5) != null) config.setConfirmTime((double) row.getCell(5).getNumericCellValue());
                    if (row.getCell(6) != null)
                        config.setConfirmTimeType(MapTimeType.get(row.getCell(6).getStringCellValue().toUpperCase()));
                    config.setImportType(row.getCell(7).getStringCellValue().toUpperCase());

                    List<ConfigSchedule> objExisting = configScheduleRepository.findOneByRequestTypeAndBussinessType(config.getRequestType(), config.getBussinessType());
                    //System.out.println("---------------ID------ :"+objExisting.get(0).getId());
                    if (config.getImportType().equals("N")) {  ////Nhap moi
                        if (!objExisting.isEmpty()) {
                            errorInfo.append(Translator.toLocale("configSchedule.configRequestType.exist"));
                            xssfCell.setCellValue(errorInfo.toString());
                            result.setErrorCodeConfig("formatError");
                            //checkAllRow = false;
                            continue;
                        }
                    } else if (config.getImportType().equals("U")) { //Update
                        if (objExisting.isEmpty()) {
                            errorInfo.append(Translator.toLocale("configSchedule.configRequestType.notExist"));
                            xssfCell.setCellValue(errorInfo.toString());
                            result.setErrorCodeConfig("formatError");
                            continue;
                        }
                        config.setId(objExisting.get(0).getId());
                    } else { // "D"  Xoa
                        if (objExisting.isEmpty()) {
                            errorInfo.append(Translator.toLocale("configSchedule.configRequestType.notExist"));
                            xssfCell.setCellValue(errorInfo.toString());
                            result.setErrorCodeConfig("formatError");
                            continue;
                        }
                        config.setId(objExisting.get(0).getId());
                    }
                    listConfig.add(config);
                }
                if (StringUtils.isEmpty(result.getErrorCodeConfig())) { //thoa man
                    for (int k = 0; k < listConfig.size(); k++) {
                        ConfigScheduleDTO rowConfigOk = listConfig.get(k);
                        if (rowConfigOk.getImportType().equals("U") || rowConfigOk.getImportType().equals("N")) {
                            save(rowConfigOk);
                        } else {
                            delete(rowConfigOk.getId());
                        }
                    }
                }
            }
            result.setXssfWorkbook(workbook);
        }
        Optional<User> userLog = userService.getUserWithAuthorities();
        actionLogService.saveActionLog(new ActionLogDTO(userLog.get().getId(), Constants.ACTION_LOG_TYPE.INSERT + "",
            null, String.format("Thêm mới file excel: [%s]", file.getOriginalFilename()),
            new Date(), Constants.MENU_ID.VOC, Constants.MENU_ITEM_ID.configVOC, "CONFIG_MENU_ITEM"));
        return result;
    }

    @Override
    public ServiceResult getProcessTime(ConfigScheduleDTO configScheduleDTO) {
        return getProcessTime(configScheduleDTO.getRequestType(), configScheduleDTO.getBussinessType());
    }


    private ServiceResult getProcessTime(Long requestType, Long businessType) {
        ServiceResult serviceResult = new ServiceResult();
        List<Object[]> list = configScheduleRepository.getProcessTime(requestType,
            businessType);
        int processTime = 0;
        String currentDate = "";
        String processTimeType = "";
        for (Object[] obj : list) {
            currentDate = DataUtil.safeToString(obj[0]);
            processTime = DataUtil.safeToInt(obj[1]);
            processTimeType = DataUtil.safeToString(obj[2]);
        }

        Date date = DateUtil.convertToDate(currentDate, "dd/MM/yyyy HH:mm:ss");
        if (processTimeType.equals("1")) {
            date.setMinutes(date.getMinutes() + processTime);
        }
        if (processTimeType.equals("2")) {
            date.setHours(date.getHours() + processTime);
        }
        if (processTimeType.equals("3")) {
            date.setDate(date.getDate() + processTime);
        }


        serviceResult.setStatus(ServiceResult.Status.SUCCESS);
        serviceResult.setProcessDate(date);
        return serviceResult;
    }

    @Override
    public void importConfigSchedule(MultipartFile file) throws IOException {
        Workbook workbook = getWorkbook(file.getInputStream(), file.getOriginalFilename());
        Sheet sheet = workbook.getSheetAt(0);
        int startRow = 2;
        Row header = sheet.getRow(startRow);
        Map<String, Integer> mapRequestType = mapRequestType();
        Map<String, Integer> mapBusinessType = mapBusinessType();

        int maxRow = sheet.getLastRowNum();
        List<ConfigScheduleDTO> lstData = new ArrayList<>();
        for (int rowIdx = startRow + 1; rowIdx <= maxRow; rowIdx++) {
            Row row = sheet.getRow(rowIdx);
            int col = 1;
            ConfigScheduleDTO dto = new ConfigScheduleDTO();
            dto.setRequestTypeStr(getStringValue(row, col++));
            dto.setBussinessTypeStr(getStringValue(row, col++));
            dto.setProcessTimeStr(getStringValue(row, col++));
            dto.setProcessTimeType(getStringValue(row, col++));
            dto.setConfirmTimeStr(getStringValue(row, col++));
            dto.setConfirmTimeType(getStringValue(row, col++));
            dto.setImportType(getStringValue(row, col++));
            validateConfigSchedule(dto, mapRequestType, mapBusinessType);
            lstData.add(dto);
        }
        System.out.println(lstData.size());
    }

    private void validateConfigSchedule(ConfigScheduleDTO dto, Map<String, Integer> mapRequestType,
                                        Map<String, Integer> mapBusinessType) {
        StringBuilder errorMsg = new StringBuilder();
        if (!DataUtil.validString(dto.getRequestTypeStr())) {
            errorMsg.append(Translator.toLocale("configSchedule.requestTypeNull")).append(",");
        } else if (!mapRequestType.containsKey(dto.getRequestTypeStr())) {
            errorMsg.append(Translator.toLocale("configSchedule.requestTypeInvalid")).append(",");
        }

        if (!DataUtil.validString(dto.getBussinessTypeStr())) {
            errorMsg.append(Translator.toLocale("configSchedule.businessTypeNull")).append(",");
        } else if (!mapBusinessType.containsKey(dto.getBussinessTypeStr())) {
            errorMsg.append(Translator.toLocale("configSchedule.businessTypeInvalid")).append(",");
        }

        dto.setError(errorMsg.toString());

    }

    private Map<String, Integer> mapRequestType() {
        Map<String, Integer> map = new HashMap<>();
        Stream.of(1, 2, 3).forEach(requestType -> {
            map.put(Translator.toLocale("requestType." + requestType), requestType);
        });
        return map;
    }

    private Map<String, Integer> mapBusinessType() {
        Map<String, Integer> map = new HashMap<>();
        Stream.of(1, 2, 3, 4).forEach(businessType -> {
            map.put(Translator.toLocale("businessType." + businessType), businessType);
        });
        return map;
    }

    private Map<String, Integer> mapTimeType() {
        Map<String, Integer> mapTimeType = new HashMap<>();
        mapTimeType.put("P", 1); //phut - gio - ngay
        mapTimeType.put("G", 2);
        mapTimeType.put("N", 3);
        return mapTimeType;
    }

    private String getStringValue(Row row, int colIdx) {
        Cell cell = row.getCell(colIdx);
        String value = null;
        if (cell != null) {
            CellType cellType = row.getCell(colIdx).getCellType();
            switch (cellType) {
                case NUMERIC:
                    value = String.valueOf(cell.getNumericCellValue());
                    break;
                case FORMULA:
                    value = getValueOfFormula(cell);
                    break;
                default:
                    value = cell.getStringCellValue().trim();
                    break;

            }
        }
        return value;
    }

    private String getValueOfFormula(Cell cell) {
        String value;
        try {
            value = String.valueOf(cell.getNumericCellValue());
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
            value = cell.getStringCellValue();
        }
        return value;
    }

    private Workbook getWorkbook(InputStream inputStream, String fileName) throws IOException {
        Workbook workbook = null;
        if (fileName.endsWith("xlsx")) {
            workbook = new XSSFWorkbook(inputStream);
        } else if (fileName.endsWith("xls")) {
            workbook = new HSSFWorkbook(inputStream);
        } else {
            throw new IllegalArgumentException("The specified file is not Excel file");
        }
        return workbook;
    }

    @Override
    public Page<ConfigScheduleDTO> onSearchConfigSchedule(ConfigScheduleDTO configScheduleDTO, Pageable pageable) {
//        return configScheduleRepository.onSearchConfigSchedule(DataUtil.makeLikeQuery(configScheduleDTO.getKeySearch()), pageable);
        if (configScheduleDTO.getKeySearch() != null && !configScheduleDTO.getKeySearch().isEmpty()) {
            return configScheduleRepository.search("%" + configScheduleDTO.getKeySearch().toLowerCase() + "%", pageable)
                .map(ConfigScheduleDTO::new);
        }
        return this.findAll(pageable);
    }

    @Override
    public List<ConfigScheduleDTO> export(ConfigScheduleDTO configScheduleDTO) {
//        List<ConfigScheduleSearch> list = configScheduleRepository.export(DataUtil.makeLikeQuery(configScheduleDTO.getKeySearch()));
        List<ConfigScheduleDTO> list = configScheduleRepository.exportConfigSchedule(configScheduleDTO.getKeySearch() == null ? "%%" : "%"+configScheduleDTO.getKeySearch()+"%")
            .stream().map(ConfigScheduleDTO::new)
            .collect(Collectors.toList());
        for (ConfigScheduleDTO o : list) {
            if (("1").equals(o.getProcessTimeType())) {
                o.setProcessTimeStr(o.getProcessTime() + Translator.toLocale("exportFileExcel.minute"));
            } else if ("2".equals(o.getProcessTimeType())) {
                o.setProcessTimeStr(o.getProcessTime() + Translator.toLocale("exportFileExcel.hours"));
            } else if ("3".equals(o.getProcessTimeType())) {
                o.setProcessTimeStr(o.getProcessTime() + Translator.toLocale("exportFileExcel.day"));
            }

            if (("1").equals(o.getConfirmTimeType())) {
                o.setConfirmTimeStr(o.getConfirmTime() + Translator.toLocale("exportFileExcel.minute"));
            } else if ("2".equals(o.getConfirmTimeType())) {
                o.setConfirmTimeStr(o.getConfirmTime() + Translator.toLocale("exportFileExcel.hours"));
            } else if ("3".equals(o.getConfirmTimeType())) {
                o.setConfirmTimeStr(o.getConfirmTime() + Translator.toLocale("exportFileExcel.day"));
            }
        }
        Optional<User> userLog = userService.getUserWithAuthorities();
        actionLogService.saveActionLog(new ActionLogDTO(userLog.get().getId(), Constants.ACTION_LOG_TYPE.EXPORT + "",
            null, Translator.toLocale("export-config-authorziration-complete"),
            new Date(), Constants.MENU_ID.VOC, Constants.MENU_ITEM_ID.configVOC, "CONFIG_MENU_ITEM"));
        return list;
    }
}
