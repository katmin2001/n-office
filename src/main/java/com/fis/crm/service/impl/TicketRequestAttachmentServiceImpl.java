package com.fis.crm.service.impl;

import com.fis.crm.commons.FileUtils;
import com.fis.crm.domain.TicketRequest;
import com.fis.crm.domain.TicketRequestAttachment;
import com.fis.crm.repository.TicketRequestAttachmentRepository;
import com.fis.crm.repository.TicketRequestRepository;
import com.fis.crm.security.SecurityUtils;
import com.fis.crm.service.TicketRequestAttachmentService;
import com.fis.crm.service.TicketRequestService;
import com.fis.crm.service.dto.ConfigScheduleDTO;
import com.fis.crm.service.dto.TicketRequestAttachmentDTO;
import com.fis.crm.service.dto.TicketRequestDTO;
import com.fis.crm.service.mapper.TicketRequestAttachmentMapper;
import com.fis.crm.service.mapper.TicketRequestMapper;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link TicketRequestAttachment}.
 */
@Service
@Transactional
public class TicketRequestAttachmentServiceImpl implements TicketRequestAttachmentService {

    private final Logger log = LoggerFactory.getLogger(TicketRequestAttachmentServiceImpl.class);

    private final TicketRequestAttachmentRepository ticketRequestAttachmentRepository;

    private final TicketRequestAttachmentMapper ticketRequestAttachmentMapper;

    public TicketRequestAttachmentServiceImpl(TicketRequestAttachmentRepository ticketRequestAttachmentRepository,
                                              TicketRequestAttachmentMapper ticketRequestAttachmentMapper) {
        this.ticketRequestAttachmentRepository = ticketRequestAttachmentRepository;
        this.ticketRequestAttachmentMapper = ticketRequestAttachmentMapper;
    }

    @Override
    public TicketRequestAttachmentDTO save(TicketRequestAttachmentDTO ticketRequestAttachmentDTO) {
        log.debug("Request to save TicketRequest : {}", ticketRequestAttachmentDTO);
        TicketRequestAttachment ticketRequestAttachment = ticketRequestAttachmentMapper.toEntity(ticketRequestAttachmentDTO);
        ticketRequestAttachment.setCreateDatetime(new Date().toInstant());
//        ticketRequestAttachment.setCreateUser(SecurityUtils.getCurrentUserLogin().get());
        ticketRequestAttachment = ticketRequestAttachmentRepository.save(ticketRequestAttachment);
        return ticketRequestAttachmentMapper.toDto(ticketRequestAttachment);
    }

    @Override
    public Optional<TicketRequestAttachmentDTO> partialUpdate(TicketRequestAttachmentDTO ticketRequestAttachmentDTO) {
        log.debug("Request to partially update TicketRequest : {}", ticketRequestAttachmentDTO);

//    return ticketRequestAttachmentRepository
//      .findById(ticketRequestAttachmentDTO.getTicketRequestAttachmentId())
//      .map(
//        existingTicketRequest -> {
//            ticketRequestAttachmentMapper.partialUpdate(existingTicketRequest, ticketRequestAttachmentDTO);
//          return existingTicketRequest;
//        }
//      )
//      .map(ticketRequestAttachmentRepository::save)
//      .map(ticketRequestAttachmentMapper::toDto);
        return null;

    }

    @Override
    @Transactional(readOnly = true)
    public Page<TicketRequestAttachmentDTO> findAll(Pageable pageable) {
        log.debug("Request to get all TicketRequestAttachments");
        return ticketRequestAttachmentRepository.findAll(pageable).map(ticketRequestAttachmentMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<TicketRequestAttachmentDTO> findOne(Long id) {
        log.debug("Request to get TicketRequestAttachment : {}", id);
        return ticketRequestAttachmentRepository.findById(id).map(ticketRequestAttachmentMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete TicketRequestAttachment : {}", id);
        ticketRequestAttachmentRepository.deleteById(id);
    }

    @Override
    public TicketRequestAttachmentDTO importList(MultipartFile file) {
        TicketRequestAttachmentDTO result = new TicketRequestAttachmentDTO();
        try {
            String name = file.getOriginalFilename();
            int validateFile = FileUtils.validateAttachFile(file, name);
            Map<String, String> timeType = new HashMap<>(3);
            timeType.put("P", "1");
            timeType.put("P", "1");
            timeType.put("g", "2");
            timeType.put("G", "2");
            timeType.put("n", "3");
            timeType.put("N", "3");

            if (validateFile != 0) {
                result.setErrorCodeConfig("Lỗi định dạng file");
                return result;
            } else {
                List<TicketRequestAttachmentDTO> tempStudentList = new ArrayList<TicketRequestAttachmentDTO>();
                XSSFWorkbook workbook = new XSSFWorkbook(file.getInputStream());
                XSSFSheet worksheet = workbook.getSheetAt(0);
                TicketRequestAttachmentDTO temp = new TicketRequestAttachmentDTO();
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
                    FileOutputStream out = new FileOutputStream(new File("fileError.xlsx"));
                    List<TicketRequestAttachmentDTO> listTicketRequestAttachment = new ArrayList<>();
                    Cell headerCellError = lstRow.get(1).createCell(8); //set header
                    headerCellError.setCellValue("Thông tin lỗi");
                    for (int i = 2; i < lstRow.size(); i++) {
                        Row row = lstRow.get(i);
                        XSSFWorkbook wb = (XSSFWorkbook) row.createCell(8).getRow().getSheet().getWorkbook();
                        //style cell error
                        XSSFCell xssfCell = (XSSFCell) row.createCell(8);
                        CellStyle cellStyle = wb.createCellStyle();
                        XSSFFont font = wb.createFont();
                        font.setBold(true);
                        font.setColor(IndexedColors.RED.getIndex());
                        cellStyle.setFont(font);
                        cellStyle.setFillForegroundColor(IndexedColors.RED.getIndex());
                        xssfCell.setCellStyle(cellStyle);
                        DataFormatter formatter = new DataFormatter();
                        if (row.getCell(0) == null || row.getCell(1) == null || row.getCell(2) == null
                            || row.getCell(3) == null || row.getCell(4) == null || row.getCell(5) == null
                            || row.getCell(6) == null || row.getCell(7) == null
                        ) {
                            //cellError
                            xssfCell.setCellValue("Dữ liệu dòng chưa đủ");
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

                            xssfCell.setCellValue("Dữ liệu không đúng format");
                            xssfCell.setCellStyle(cellStyle);
                            result.setErrorCodeConfig("formatError");
                            continue;
                        }
                        if (!row.getCell(1).getCellType().toString().equals("NUMERIC") ||
                            !row.getCell(2).getCellType().toString().equals("NUMERIC") ||
                            !row.getCell(3).getCellType().toString().equals("NUMERIC") ||
                            !row.getCell(4).getCellType().toString().equals("STRING") ||
                            !row.getCell(5).getCellType().toString().equals("NUMERIC") ||
                            !row.getCell(6).getCellType().toString().equals("STRING") ||
                            !row.getCell(7).getCellType().toString().equals("STRING")
                        ) {
                            StringBuilder errorInfo = new StringBuilder("");
                            if (!row.getCell(1).getCellType().toString().equals("NUMERIC")) {
                                errorInfo.append("'Loại yêu cầu' không đúng định dạng");
                            }
                            if (!row.getCell(2).getCellType().toString().equals("NUMERIC")) {
                                errorInfo.append("'Loại nghiệp vụ' không đúng định dạng");
                            }
                            if (!row.getCell(3).getCellType().toString().equals("NUMERIC")) {
                                errorInfo.append("'Thời gian xử lý' không đúng định dạng");
                            }
                            if (!row.getCell(4).getCellType().toString().equals("STRING")) {
                                errorInfo.append("'Đơn vị thời gian xử lý' không đúng định dạng");
                            }
                            if (row.getCell(5).getCellType().toString().equals("NUMERIC")) {
                                errorInfo.append("'Thời gian xác nhận' không đúng định dạng");
                            }
                            if (!row.getCell(6).getCellType().toString().equals("STRING")) {
                                errorInfo.append("'Đơn vị thời gian xác nhận' không đúng định dạng");
                            }
                            if (!row.getCell(7).getCellType().toString().equals("STRING")) {
                                errorInfo.append("'ID' không đúng định dạng");
                            }
                            xssfCell.setCellValue(errorInfo.toString());
                            xssfCell.setCellStyle(cellStyle);
                            result.setErrorCodeConfig("formatError");
                            continue;
                        }

                    }
                    result.setXssfWorkbook(workbook);
                    //configScheduleService.importList(listConfig);
                }
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return result;

    }

    @Override
    public List<TicketRequestAttachmentDTO> findByTicketId(Long ticketId) {
        return ticketRequestAttachmentRepository.findAllByTicketRequestId(ticketId)
            .stream().map(ticketRequestAttachmentMapper::toDto).collect(Collectors.toList());
    }
}
