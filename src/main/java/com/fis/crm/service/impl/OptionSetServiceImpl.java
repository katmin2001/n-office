package com.fis.crm.service.impl;

import com.fis.crm.commons.DataUtil;
import com.fis.crm.commons.FileUtils;
import com.fis.crm.config.Constants;
import com.fis.crm.domain.ConfigSchedule;
import com.fis.crm.domain.OptionSet;
import com.fis.crm.domain.OptionSetValue;
import com.fis.crm.domain.User;
import com.fis.crm.repository.OptionSetRepository;
import com.fis.crm.repository.OptionSetValueRepository;
import com.fis.crm.service.OptionSetService;
import com.fis.crm.service.OptionSetValueService;
import com.fis.crm.service.UserService;
import com.fis.crm.service.dto.ConfigScheduleDTO;
import com.fis.crm.service.dto.OptionSetDTO;
import com.fis.crm.service.dto.OptionSetValueDTO;
import com.fis.crm.service.mapper.OptionSetMapper;
import liquibase.util.StringUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.time.Instant;
import java.util.*;

/**
 * Service Implementation for managing {@link OptionSet}.
 */
@Service
@Transactional
public class OptionSetServiceImpl implements OptionSetService {

    private final Logger log = LoggerFactory.getLogger(OptionSetServiceImpl.class);

    private final OptionSetRepository optionSetRepository;
    private final OptionSetMapper optionSetMapper;
    @Autowired
    private ActionLogServiceImpl actionLogServiceImpl;
    @Autowired
    private UserService userService;

    @Autowired
    private OptionSetValueServiceImpl optionSetValueService;

    @Autowired
    private OptionSetValueRepository optionSetValueRepository;

    @PersistenceContext
    EntityManager em;

    public OptionSetServiceImpl(OptionSetRepository optionSetRepository, OptionSetMapper optionSetMapper) {
        this.optionSetRepository = optionSetRepository;
        this.optionSetMapper = optionSetMapper;
    }

    @Override
    public OptionSetDTO save(OptionSetDTO optionSetDTO) {
        log.debug("Request to save optionSet  : {}", optionSetDTO);
        Optional<User> user = userService.getUserWithAuthorities();
        Instant now = Instant.now();
        if (optionSetDTO.getOptionSetId() == null)  //add new
        {
            optionSetDTO.setCreateUser(user.get().getId());
            optionSetDTO.setCreateDate(now);
//            optionSetDTO.setUpdateUser(user.get().getId());
//            optionSetDTO.setUpdateDate(now);
            optionSetDTO.setStatus("1"); //1 hieu luc , 0 het hieu luc
        } else {  //update
            optionSetDTO.setUpdateUser(user.get().getId());
            optionSetDTO.setUpdateDate(now);
        }
        OptionSet optionSet = optionSetMapper.toEntity(optionSetDTO);
        //validate new
        //List<ConfigSchedule> cfg = configScheduleRepository.findAll();
        OptionSetDTO result = new OptionSetDTO();
        if (optionSetDTO.getOptionSetId() == null) {
            Optional<OptionSet> objExisting = optionSetRepository.findOptionSetByCode(optionSetDTO.getCode().trim());
            if (objExisting.isPresent()) {
                result.setErrorCodeConfig("Mã loại danh mục đã tồn tại");
                return result;
            }
        }
        //validate update
        else if (optionSetDTO.getOptionSetId() != null) {
            Optional<OptionSetDTO> oldObj = optionSetRepository.findById(optionSetDTO.getOptionSetId()).map(optionSetMapper::toDto);
            if (!oldObj.isPresent()) {
                result.setErrorCodeConfig("Loại danh mục chưa tồn tại");
                return result;
            }
            //set lai gia tri ko dc thay doi
            optionSet.setCode(oldObj.get().getCode());
            optionSet.setFromDate(oldObj.get().getFromDate());
            optionSet.setCreateDate(oldObj.get().getCreateDate());
            optionSet.setCreateUser(oldObj.get().getCreateUser());

        }
        optionSet = optionSetRepository.save(optionSet); //save or update
        if (optionSetDTO.getOptionSetId() == null) {
            actionLogServiceImpl.saveActionLog(user.get().getId(), Constants.ACTION_LOG_TYPE.INSERT + "", optionSet.getOptionSetId(), "Option_Set", "", now);
        } else
            actionLogServiceImpl.saveActionLog(user.get().getId(), Constants.ACTION_LOG_TYPE.UPDATE + "", optionSet.getOptionSetId(), "Option_Set", "", now);
        return optionSetMapper.toDto(optionSet);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<OptionSetDTO> findAll(Pageable pageable) {
        log.debug("Request to get all OptionSet");
        return optionSetRepository.findAll(pageable)
            .map(optionSetMapper::toDto);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<OptionSetDTO> findOne(Long id) {
        log.debug("Request to get OptionSet : {}", id);
        return optionSetRepository.findById(id)
            .map(optionSetMapper::toDto);
    }

    @Override
    public OptionSetDTO delete(Long id) {
        log.debug("Request to delete OptionSet : {}", id);
        OptionSetDTO result = new OptionSetDTO();
        result.setOptionSetId(id);
        result.setPage(1);
        result.setSize(1);
        List<OptionSetValueDTO> lstValue = findOptSetValueByOptionSetId(result);
        if (lstValue.isEmpty())
            optionSetRepository.deleteById(id);
        else {
            result.setErrorCodeConfig("Loại danh mục đang chứa các danh mục!");
        }
        return result;
    }

    @Override
    public List<OptionSetDTO> find(OptionSetDTO obj) {
        List<Object[]> lst = new ArrayList<Object[]>();
        List<OptionSetDTO> lstResult = new ArrayList<OptionSetDTO>();
        try {
            StringBuilder sb = new StringBuilder(" " +
                " SELECT * FROM ( SELECT os.OPTION_SET_ID optionSetId,os.CODE code, os.NAME name, " +
                " os.FROM_DATE fromDate,os.END_DATE endDate, os.STATUS status, " +
                " os.CREATE_DATE createDate,os.CREATE_USER createUser, " +
                " os.UPDATE_DATE updateDate,os.UPDATE_USER updateUser " +
                " FROM OPTION_SET os " +
                " WHERE 1=1 ");
            if (StringUtil.isNotEmpty(obj.getCode())) {
                sb.append(" AND os.code like (:code) ");
            }
            if (StringUtil.isNotEmpty(obj.getName())) {
                sb.append(" AND os.name like (:name) ");
            }
            if (StringUtil.isNotEmpty(obj.getStatus())) {
                sb.append(" AND os.status = :status ");
            }
            sb.append(" ORDER BY os.UPDATE_DATE desc ) ");
            sb.append(" WHERE ( ROWNUM > :limit and ROWNUM <= :offset ) ");

            Query query = em.createNativeQuery(sb.toString());
            if (StringUtil.isNotEmpty(obj.getCode())) {
                query.setParameter("code", "%" + obj.getCode().trim() + "%");
            }
            if (StringUtil.isNotEmpty(obj.getName())) {
                query.setParameter("name", "%" + obj.getName().trim() + "%");
            }
            if (StringUtil.isNotEmpty(obj.getStatus())) {
                query.setParameter("status", obj.getStatus());
            }
            int limit = obj.getSize();
            int page = obj.getPage();
            query.setParameter("limit", (page * limit) - limit);
            query.setParameter("offset", page * limit);

            lst = query.getResultList();
            for (Object[] obj1 : lst) {
                OptionSetDTO cal = new OptionSetDTO();
                cal.setOptionSetId(DataUtil.safeToLong(obj1[0]));
                cal.setCode(DataUtil.safeToString(obj1[1]));
                cal.setName(DataUtil.safeToString(obj1[2]));
                cal.setFromDate(DataUtil.safeToInstant(obj1[3]));
                cal.setEndDate(DataUtil.safeToInstant(obj1[4]));
                cal.setStatus(DataUtil.safeToString(obj1[5]));
                cal.setCreateDate(DataUtil.safeToInstant(obj1[6]));
                cal.setCreateUser(DataUtil.safeToLong(obj1[7]));
                cal.setUpdateDate(DataUtil.safeToInstant(obj1[8]));
                cal.setUpdateUser(DataUtil.safeToLong(obj1[9]));
                lstResult.add(cal);
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return lstResult;
    }

    public OptionSetDTO changeStatus(OptionSetDTO obj) {
        Optional<OptionSetDTO> optionSV = findOne(obj.getOptionSetId());
        if (null != optionSV.get().getStatus()) {
            if (optionSV.get().getStatus().equals("1"))  //change status
                optionSV.get().setStatus("0");
            else
                optionSV.get().setStatus("1");
        } else
            optionSV.get().setStatus("1"); //default

        OptionSetDTO result = save(optionSV.get());
        return result;
    }

    @Override
    public List<OptionSetValueDTO> findOptSetValueByOptionSetId(OptionSetDTO opt) {
        List<Object[]> lst = new ArrayList<Object[]>();
        List<OptionSetValueDTO> lstResult = new ArrayList<OptionSetValueDTO>();
        try {
            StringBuilder sb = new StringBuilder(" " +
                " SELECT * FROM ( SELECT os.ID id,os.OPTION_SET_ID optionSetId,os.CODE code, os.NAME name, " +
                " os.ORD ord,os.GROUP_NAME groupName, " +
                " os.FROM_DATE fromDate,os.END_DATE endDate, os.STATUS status, " +
                " os.CREATE_DATE createDate,os.CREATE_USER createUser, " +
                " os.UPDATE_DATE updateDate,os.UPDATE_USER updateUser, " +
                " os.C1 c1,os.C2 c2 " +
                " FROM OPTION_SET_VALUE os join OPTION_SET os1 on os.OPTION_SET_ID = os1.OPTION_SET_ID " +
                " WHERE 1=1 AND os1.OPTION_SET_ID= :optId  ");
            sb.append(" ORDER BY os.UPDATE_DATE desc ) ");
            sb.append(" WHERE ( ROWNUM > :limit and ROWNUM <= :offset ) ");

            Query query = em.createNativeQuery(sb.toString());
            query.setParameter("optId", opt.getOptionSetId());
            int limit = opt.getSize();
            int page = opt.getPage();
            query.setParameter("limit", (page * limit) - limit);
            query.setParameter("offset", page * limit);

            lst = query.getResultList();
            for (Object[] obj1 : lst) {
                OptionSetValueDTO cal = new OptionSetValueDTO();
                cal.setId(DataUtil.safeToLong(obj1[0]));
                cal.setOptionSetId(DataUtil.safeToLong(obj1[1]));
                cal.setCode(DataUtil.safeToString(obj1[2]));
                cal.setName(DataUtil.safeToString(obj1[3]));
                cal.setOrd(DataUtil.safeToInt(obj1[4]));
                cal.setGroupName(DataUtil.safeToString(obj1[5]));
                cal.setFromDate(DataUtil.safeToInstant(obj1[6]));
                cal.setEndDate(DataUtil.safeToInstant(obj1[7]));
                cal.setStatus(DataUtil.safeToString(obj1[8]));
                cal.setCreateDate(DataUtil.safeToInstant(obj1[9]));
                cal.setCreateUser(DataUtil.safeToLong(obj1[10]));
                cal.setUpdateDate(DataUtil.safeToInstant(obj1[11]));
                cal.setUpdateUser(DataUtil.safeToLong(obj1[12]));
                cal.setC1(DataUtil.safeToString(obj1[13]));
                cal.setC2(DataUtil.safeToString(obj1[14]));
                lstResult.add(cal);
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return lstResult;
    }

    @Override
    public OptionSetValueDTO importList(MultipartFile file) {
        OptionSetValueDTO result = new OptionSetValueDTO();
        try {
            String name = file.getOriginalFilename();
            int validateFile = FileUtils.validateAttachFileExcel(file, name);
            Map<String, String> MapTimeType = new HashMap<>(3);
            if (validateFile != 0) {
                result.setErrorCodeConfig("Lỗi định dạng file");
                return result;
            } else {
                XSSFWorkbook workbook = new XSSFWorkbook(file.getInputStream());
                XSSFSheet worksheet = workbook.getSheetAt(0);
                ConfigScheduleDTO temp = new ConfigScheduleDTO();
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
                    List<OptionSetValueDTO> listConfig = new ArrayList<>();
                    Cell headerCellError = lstRow.get(1).createCell(9); //set header
                    headerCellError.setCellValue("Thông tin lỗi");
                    //boolean checkAllRow = true;
                    for (int i = 1; i < lstRow.size(); i++) {
                        Row row = lstRow.get(i);
                        XSSFWorkbook wb = (XSSFWorkbook) row.createCell(9).getRow().getSheet().getWorkbook();
                        //style cell error
                        XSSFCell xssfCell = (XSSFCell) row.createCell(9);
                        CellStyle cellStyle = wb.createCellStyle();
                        XSSFFont font = wb.createFont();
                        font.setBold(true);
                        font.setColor(IndexedColors.RED.getIndex());
                        cellStyle.setFont(font);
                        cellStyle.setWrapText(true);
                        cellStyle.setFillForegroundColor(IndexedColors.RED.getIndex());
                        xssfCell.setCellStyle(cellStyle);
                        DataFormatter formatter = new DataFormatter();
                        if (row.getCell(0) == null || row.getCell(1) == null || row.getCell(2) == null
                            || row.getCell(3) == null || row.getCell(4) == null || row.getCell(5) == null
                            || row.getCell(6) == null || row.getCell(7) == null || row.getCell(8) == null
                        ) {
                            //cellError
                            xssfCell.setCellValue("Dữ liệu dòng chưa đủ");
                            result.setErrorCodeConfig("formatError");
                            xssfCell.setCellStyle(cellStyle);
                            continue;
                        }
                        if (!StringUtils.isNotEmpty(formatter.formatCellValue(row.getCell(0))) ||
                            !StringUtils.isNotEmpty(formatter.formatCellValue(row.getCell(1))) ||
                            !StringUtils.isNotEmpty(formatter.formatCellValue(row.getCell(2))) ||
                            !StringUtils.isNotEmpty(formatter.formatCellValue(row.getCell(3))) ||
                            !StringUtils.isNotEmpty(formatter.formatCellValue(row.getCell(4))) ||
                            !StringUtils.isNotEmpty(formatter.formatCellValue(row.getCell(5))) ||
                            !StringUtils.isNotEmpty(formatter.formatCellValue(row.getCell(6))) ||
                            !StringUtils.isNotEmpty(formatter.formatCellValue(row.getCell(7))) ||
                            !StringUtils.isNotEmpty(formatter.formatCellValue(row.getCell(8)))
                        ) {
                            xssfCell.setCellValue("Dữ liệu dòng chưa đủ");
                            xssfCell.setCellStyle(cellStyle);
                            result.setErrorCodeConfig("formatError");
                            continue;
                        }
                        //get object from each row
                        OptionSetValueDTO optionSetValueDTO = new OptionSetValueDTO();
                        //check format , check type
                        StringBuilder errorInfo = new StringBuilder();
                        boolean checkFormat = true;
                        if (!row.getCell(0).getCellType().toString().equals("NUMERIC")
                            && !row.getCell(0).getCellType().toString().equals("STRING")
                        ) {
                            errorInfo.append("'Mã loại danh mục' không đúng định dạng\n");
                            checkFormat = false;
                        }
                        if (!row.getCell(1).getCellType().toString().equals("NUMERIC")
                            && !row.getCell(1).getCellType().toString().equals("STRING")) {
                            errorInfo.append("'Mã danh mục' không đúng định dạng\n");
                            checkFormat = false;
                        }
                        if (!row.getCell(2).getCellType().toString().equals("STRING")
                        ) {
                            errorInfo.append("'Tên' không đúng định dạng\n");
                            checkFormat = false;
                        }
                        if (!row.getCell(3).getCellType().toString().equals("NUMERIC")) {
                            errorInfo.append("'Thứ tự' không đúng định dạng\n");
                            checkFormat = false;
                        }

                        if (!row.getCell(4).getCellType().toString().equals("STRING")) {
                            errorInfo.append("'Nhóm' không đúng định dạng\n");
                            checkFormat = false;
                        }
//                        if (!row.getCell(5).getCellType().toString().equals("STRING")) {
//                            errorInfo.append("'Ngày hiệu lực' không đúng định dạng\n");
//                            checkFormat = false;
//                        }
//
//                        if (!row.getCell(6).getCellType().toString().equals("STRING")) {
//                            errorInfo.append("'Ngày hết hiệu lực' không đúng định dạng\n");
//                            checkFormat = false;
//                        }
                        if (!row.getCell(7).getCellType().toString().equals("STRING")) {
                            errorInfo.append("'Trạng thái' không đúng định dạng\n");
                            checkFormat = false;
                        }
                        if (!row.getCell(8).getCellType().toString().equals("STRING")) {
                            errorInfo.append("'ID' không đúng định dạng\n");
                            checkFormat = false;
                        }
                        else if(!row.getCell(8).getStringCellValue().toUpperCase().equals("U") && !row.getCell(8).getStringCellValue().toUpperCase().equals("N")){
                            errorInfo.append("'ID' phải là 'U'(Cập nhật) 'N'(Nhập mới) \n");
                            checkFormat = false;
                        }

                        List<OptionSet> checkOptionSetCode = optionSetRepository.getByCode(row.getCell(0).getStringCellValue());
                        List<OptionSetValue> checkOptionSetValueCode = optionSetValueRepository.getByCode(row.getCell(1).getStringCellValue());
                        if(row.getCell(8).getStringCellValue().equals("U")){
                            if(checkOptionSetCode.size() < 1){
                                errorInfo.append("'Mã loại danh mục' không tồn tại\n");
                                checkFormat = false;
                            }
                            if(checkOptionSetValueCode.size() < 1){
                                errorInfo.append("'Mã danh mục' không tồn tại\n");
                                checkFormat = false;
                            }
                            if(checkOptionSetValueCode.size() > 0){
                                optionSetValueDTO.setId(checkOptionSetValueCode.get(0).getId());
                            }
                        }
                        if(row.getCell(8).getStringCellValue().equals("N")){
                            if(checkOptionSetCode.size() < 1){
                                errorInfo.append("'Mã loại danh mục' không tồn tại");
                                checkFormat = false;
                            }
                            if(checkOptionSetValueCode.size() > 0){
                                errorInfo.append("'Mã danh mục' đã tồn tại\n");
                                checkFormat = false;
                            }
                        }

                        if (false == checkFormat) {
                            xssfCell.setCellValue(errorInfo.toString());
                            xssfCell.setCellStyle(cellStyle);
                            result.setErrorCodeConfig("formatError");
                            continue;
                        }


                        //ma loai danh muc
//                        if (row.getCell(0).getCellType().toString().equals("NUMERIC")
//                        ) {
//                            optionSetValueDTO.setOptionSetId(String.valueOf((long) row.getCell(0).getNumericCellValue()));
//                        } else
//                            optionSetValueDTO.setOptionSetId(String.valueOf(row.getCell(0).getStringCellValue()));
//                        //ma danh muc
//                        if (row.getCell(1).getCellType().toString().equals("NUMERIC")
//                        ) {
//                            optionSetValueDTO.setCode(String.valueOf((long) row.getCell(1).getNumericCellValue()));
//                        } else
                            optionSetValueDTO.setOptionSetId(checkOptionSetCode.get(0).getOptionSetId());
                            optionSetValueDTO.setCode(row.getCell(1).getStringCellValue());

                        optionSetValueDTO.setName(String.valueOf(row.getCell(2).getStringCellValue()));
                        optionSetValueDTO.setOrd((int) row.getCell(3).getNumericCellValue());
                        optionSetValueDTO.setGroupName(row.getCell(4).getStringCellValue());
//                        optionSetValueDTO.setFromDateView(row.getCell(5).getDateCellValue());
//                        optionSetValueDTO.setEndDateView(row.getCell(6).getDateCellValue());
                        if(row.getCell(7).getStringCellValue().equals("A")){
                            optionSetValueDTO.setStatus("1");
                        }
                        if(row.getCell(7).getStringCellValue().equals("I")){
                            optionSetValueDTO.setStatus("0");
                        }

                        listConfig.add(optionSetValueDTO);
                    }
                    if (StringUtils.isEmpty(result.getErrorCodeConfig())) { //thoa man
                        for (int k = 0; k < listConfig.size(); k++) {
                            OptionSetValueDTO rowOption = listConfig.get(k);
                                optionSetValueService.save(rowOption);
                        }
                    } else {   //formatError == errorConfig
                        result.setXssfWorkbook(workbook);
                    }
                    //configScheduleService.importList(listConfig);
                }
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return result;
    }

    @Override
    public Page<OptionSetDTO> searchOptionSet(OptionSetDTO optionSetDTO, Pageable pageable) {
        return optionSetRepository.search(DataUtil.makeLikeParam(optionSetDTO.getCode()), DataUtil.makeLikeParam(optionSetDTO.getName()), optionSetDTO.getStatus(), pageable).map(OptionSetDTO::new);
    }

}
