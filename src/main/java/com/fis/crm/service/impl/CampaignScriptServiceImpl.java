package com.fis.crm.service.impl;

import com.fis.crm.commons.CurrentUser;
import com.fis.crm.commons.DataUtil;
import com.fis.crm.commons.FileUtils;
import com.fis.crm.config.Constants;
import com.fis.crm.domain.CampaignScript;
import com.fis.crm.domain.User;
import com.fis.crm.repository.CampaignScriptRepository;
import com.fis.crm.service.*;
import com.fis.crm.service.dto.*;
import com.fis.crm.service.mapper.CampaignScriptMapper;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityManager;
import javax.sql.DataSource;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link CampaignScript}.
 */
@Service
@Transactional
public class CampaignScriptServiceImpl implements CampaignScriptService {

    private final Logger log = LoggerFactory.getLogger(CampaignScriptServiceImpl.class);

    private final CampaignScriptRepository campaignScriptRepository;
    private final CampaignScriptMapper campaignScriptMapper;
    private final CampaignScriptQuestionService campaignScriptQuestionService;
    private final CampaignScriptAnswerService campaignScriptAnswerService;
    private final CurrentUser currentUser;
    private final DataSource dataSource;
    private final EntityManager entityManager;
    private final ActionLogService actionLogService;
    private final UserService userService;

    public CampaignScriptServiceImpl(CampaignScriptRepository campaignScriptRepository,
                                     CampaignScriptMapper campaignScriptMapper,
                                     CampaignScriptQuestionService campaignScriptQuestionService,
                                     CampaignScriptAnswerService campaignScriptAnswerService,
                                     CurrentUser currentUser, DataSource dataSource, EntityManager entityManager, ActionLogService actionLogService, UserService userService) {
        this.campaignScriptRepository = campaignScriptRepository;
        this.campaignScriptMapper = campaignScriptMapper;
        this.campaignScriptQuestionService = campaignScriptQuestionService;
        this.campaignScriptAnswerService = campaignScriptAnswerService;
        this.currentUser = currentUser;
        this.dataSource = dataSource;
        this.entityManager = entityManager;
        this.actionLogService = actionLogService;
        this.userService = userService;
    }

    @Override
    public CampaignScriptDTO save(CampaignScriptDTO campaignScriptDTO) {
        log.debug("Request to save CampaignScript : {}", campaignScriptDTO);
        CampaignScript campaignScript = campaignScriptMapper.toEntity(campaignScriptDTO);
        campaignScript.setCreateDatetime(new Date().toInstant());
        campaignScript.setStatus("1");
//        campaignScript.setCode(GenCodeUtils.genCodeForCampaignScript());
//        while(campaignScriptRepository.findByCode(campaignScript.getCode()).size()>=1){
//            campaignScript.setCode(GenCodeUtils.genCodeForCampaignScript());
//        }

        campaignScript.setCode(genCodeInOrder());

        campaignScript.setCreateUser(currentUser.getCurrentUser());
        campaignScript.setUpdateUser(currentUser.getCurrentUser());
        campaignScript.setUpdateDatetime(Instant.now());

        campaignScript = campaignScriptRepository.save(campaignScript);
        CampaignScriptDTO dto = campaignScriptMapper.toDto(campaignScript);
        dto.setCreateFullName(campaignScript.getCreateUser().getFirstName());
        dto.setUpdateFullName(campaignScript.getUpdateUser().getFirstName());
        Optional<User> userLog = userService.getUserWithAuthorities();
        actionLogService.saveActionLog(new ActionLogDTO(userLog.get().getId(), Constants.ACTION_LOG_TYPE.INSERT + "",
            null, String.format("Thêm mới kịch bản chiến dịch: [%s]", campaignScriptDTO.getName()),
            new Date(), Constants.MENU_ID.OMS, Constants.MENU_ITEM_ID.campain_script, "CONFIG_MENU_ITEM"));
        return dto;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CampaignScriptDTO> findAll(Pageable pageable) {
        log.debug("Request to get all CampaignScripts");
        return campaignScriptRepository.findAll(pageable)
            .map(campaignScriptMapper::toDto);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<CampaignScriptDTO> findOne(Long id) {
        log.debug("Request to get CampaignScript : {}", id);
        return campaignScriptRepository.findById(id)
            .map(campaignScriptMapper::toDto);
    }

    @Override
    public void delete(Long id) throws Exception {
        log.debug("Request to delete CampaignScript : {}", id);
        CampaignScript cs = campaignScriptRepository.getOne(id);
        if (cs.getStatus().equals("0")) {
            throw new Exception("This campaign script in used!");
        }
        //xoa cac cau hoi va cau tra loi lien quan
        log.info("Deleting related questions and answers...");
        campaignScriptQuestionService.deleteAllQuestion(id);
        log.info("Deleting campaign scripts...");
        campaignScriptRepository.deleteById(id);
        log.info("Delete successful!");
    }

    @Override
    public boolean validateBeforeAdd(CampaignScriptDTO campaignScriptDTO) {
        List<CampaignScript> listCampaingScript = findByName(campaignScriptDTO);
        if (listCampaingScript != null && !listCampaingScript.isEmpty()) {
            if (campaignScriptDTO.getName().equals(listCampaingScript.get(0).getName())) {
                return true;
            }
            return false;
        }
        return true;
    }

    @Override
    public boolean checkName(String name) {
        CampaignScript campaignScript = campaignScriptRepository.findByName(name.toLowerCase());
        if (campaignScript == null) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean checkNameToLowerCase(String name) {
        CampaignScript campaignScript = campaignScriptRepository.findByName(name.toLowerCase());
        if (campaignScript == null) {
            return true;
        } else {
            if (campaignScript.getName().equalsIgnoreCase(name)) {
                return false;
            } else {
                return true;
            }
        }
    }

    public List<CampaignScript> findByName(CampaignScriptDTO campaignScriptDTO) {
        return campaignScriptRepository.findCampaignScriptByName(campaignScriptDTO.getName());
    }

    public List<CampaignScriptListCbxDTO> getListCampaignScriptForCombobox() {
        List<Object[]> lst = new ArrayList<Object[]>();
        List<CampaignScriptListCbxDTO> lstResult = new ArrayList<>();
        try {

            lst = campaignScriptRepository.getListCampaignScriptForCombobox();
            for (Object[] obj1 : lst) {
                CampaignScriptListCbxDTO campaignScriptDTO = new CampaignScriptListCbxDTO();

                campaignScriptDTO.setId(DataUtil.safeToLong(obj1[0]));
                campaignScriptDTO.setCampaignScriptName(DataUtil.safeToString(obj1[1]));


                lstResult.add(campaignScriptDTO);
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return lstResult;
    }

    @Override
    public List<CampaignScriptQuestionResponseDTO> uploadFile(MultipartFile file, Long campaignScriptId, Integer deleteAllQuestion) throws Exception {
        //deleteAllQuestion: 2: k xoa, 1: xoa tat ca cau hoi cu
        if (deleteAllQuestion == 1) {
            log.info("Deleting all old question....");
            campaignScriptQuestionService.deleteAllQuestion(campaignScriptId);
        }

        log.info("Reading file...");
        // kiem tra dinh dang file
        String fileName = file.getOriginalFilename();
        int validFile = FileUtils.validateAttachFileExcel(file, fileName);
        if (validFile != 0) {
            throw new IllegalArgumentException("The specified file is not Excel file");
        }
        XSSFWorkbook workbook = new XSSFWorkbook(file.getInputStream());
        Sheet sheet = workbook.getSheetAt(0);

        Iterator<Row> iterator = sheet.iterator();
        Long questionId = null;
        while (iterator.hasNext()) {
            Row nextRow = iterator.next();
            if (nextRow.getRowNum() == 0) {
                continue;
            }
            Iterator<Cell> cellIterator = nextRow.cellIterator();
            CampaignScriptQuestionRequestDTO dto = new CampaignScriptQuestionRequestDTO();
            Integer checkType = 0; //0: cau hoi, 1: cau tra loi
            CampaignScriptAnswerRequestDTO answerDto = new CampaignScriptAnswerRequestDTO();
            while (cellIterator.hasNext()) {
                Cell cell = cellIterator.next();
                Object cellValue = cell.getCellType();
                if (cellValue == null || cellValue.toString().isEmpty()) {
                    continue;
                }
                int columnIndex = cell.getColumnIndex();
                switch (columnIndex) {
                    case 1: //ma cau hoi
                        dto.setCode(cell.getStringCellValue());
                        dto.setCampaignScriptId(campaignScriptId);
                        dto.setDisplay("1");
                        dto.setStatus("1");
                        checkType = 0;
                        break;
                    case 2: //ma cau tra loi
                        answerDto.setCode(cell.getStringCellValue());
//                        answerDto.setCampaignScriptId(campaignScriptId);
                        answerDto.setDisplay("1");
                        answerDto.setStatus("1");
                        if (questionId != null) {
                            answerDto.setCampaignScriptQuestionId(questionId);
                        }
                        checkType = 1;
                        break;
                    case 3: //noi dung
                        if (checkType == 0) dto.setContent(cell.getStringCellValue());
                        else answerDto.setContent(cell.getStringCellValue());
                        break;
                    case 4: //loai cau tra loi
                        String type = cell.getStringCellValue();
                        if (type.toLowerCase().equals("one")) {
                            answerDto.setType("1");
                        } else if (type.toLowerCase().equals("multi")) {
                            answerDto.setType("2");
                        } else if (type.toLowerCase().equals("text")) {
                            answerDto.setType("3");
                        } else if (type.toLowerCase().equals("rank")) {
                            answerDto.setType("4");
                        }
                        break;
                    case 5: //min
                        answerDto.setMin(Integer.valueOf((int) cell.getNumericCellValue()));
                        break;
                    case 6: //max
                        answerDto.setMax(Integer.valueOf((int) cell.getNumericCellValue()));
                        break;
                    default:
                        break;
                }
            }
            if (checkType == 0 && dto.getCampaignScriptId() != null)
                questionId = campaignScriptQuestionService.save(dto).getId();
            else if (answerDto.getCampaignScriptQuestionId() != null && answerDto.getDisplay() != null) {
                campaignScriptAnswerService.save(answerDto);
            }
        }
        CampaignScript campaignScript = campaignScriptRepository.findById(campaignScriptId).get();
        Optional<User> userLog = userService.getUserWithAuthorities();
        actionLogService.saveActionLog(new ActionLogDTO(userLog.get().getId(), Constants.ACTION_LOG_TYPE.INSERT + "",
            null, String.format("Import file kịch bản: [%s] vào kịch bản [%s]", file.getOriginalFilename(), campaignScript.getName()),
            new Date(), Constants.MENU_ID.OMS, Constants.MENU_ITEM_ID.campain_script, "CONFIG_MENU_ITEM"));
        return campaignScriptQuestionService.findAllByCampaignScriptId(campaignScriptId);
    }

    @Override
    public CampaignScriptDTO updateCampaignScript(CampaignScriptDTO dto) {

        CampaignScript campaignScript = campaignScriptRepository.getOne(dto.getId());
        if (dto.getName() != null) campaignScript.setName(dto.getName());
        if (dto.getStartContent() != null) campaignScript.setStartContent(dto.getStartContent());
        if (dto.getEndContent() != null) campaignScript.setEndContent(dto.getEndContent());
        if (dto.getStatus() != null) campaignScript.setStatus(dto.getStatus());
        campaignScript.setUpdateDatetime(new Date().toInstant());
        campaignScript.setUpdateUser(currentUser.getCurrentUser());

        campaignScript = campaignScriptRepository.save(campaignScript);
        CampaignScriptDTO responseDto = campaignScriptMapper.toDto(campaignScript);
        responseDto.setCreateFullName(campaignScript.getCreateUser().getFirstName());
        responseDto.setUpdateFullName(campaignScript.getUpdateUser().getFirstName());
        Optional<User> userLog = userService.getUserWithAuthorities();
        if ("1".equals(dto.getStatus())){
            actionLogService.saveActionLog(new ActionLogDTO(userLog.get().getId(), Constants.ACTION_LOG_TYPE.UPDATE + "",
                null, String.format("Cập nhật kịch bản chiến dịch: [%s]", dto.getName()),
                new Date(), Constants.MENU_ID.OMS, Constants.MENU_ITEM_ID.campain_script, "CONFIG_MENU_ITEM"));
        }
        if ("2".equals(dto.getStatus())){
            actionLogService.saveActionLog(new ActionLogDTO(userLog.get().getId(), Constants.ACTION_LOG_TYPE.DELETE + "",
                null, String.format("Xóa kịch bản chiến dịch: [%s]", dto.getName()),
                new Date(), Constants.MENU_ID.OMS, Constants.MENU_ITEM_ID.campain_script, "CONFIG_MENU_ITEM"));
        }
        return responseDto;
    }

    @Override
    public String copyScript(CopyScriptRequestDTO copyScriptRequestDTO) {
        String checkScript = isError(copyScriptRequestDTO.getId(), copyScriptRequestDTO.getNewName());
        Optional<User> userLog = userService.getUserWithAuthorities();
        actionLogService.saveActionLog(new ActionLogDTO(userLog.get().getId(), Constants.ACTION_LOG_TYPE.INSERT + "",
            null, String.format("Copy kịch bản chiến dịch: [%s]", copyScriptRequestDTO.getNewName()),
            new Date(), Constants.MENU_ID.OMS, Constants.MENU_ITEM_ID.campain_script, "CONFIG_MENU_ITEM"));
        return checkScript;
    }

    private String isError(Long id, String name) {
        String returnOut = null;
        String query = "{ call PCK_UTIL.copy_campaign_script(?,?,?) }";
        ResultSet rs = null;
        try (Connection conn = dataSource.getConnection();
             CallableStatement stmt = conn.prepareCall(query)) {
            stmt.setLong(1, id);
            stmt.setString(2, name);
            stmt.registerOutParameter(3, Types.NVARCHAR);
            stmt.executeUpdate();
            returnOut = stmt.getString(3);
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return returnOut;
    }

    public String genCodeInOrder() {
        List<CampaignScript> campaignScripts = campaignScriptRepository.findByOrderByCreateDatetimeDesc();
        Long newId = (campaignScripts==null || campaignScripts.size()==0)?1:campaignScripts.get(0).getId() + 1;
        return "CS" + newId;
    }
}
