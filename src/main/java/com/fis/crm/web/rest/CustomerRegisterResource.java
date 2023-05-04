package com.fis.crm.web.rest;

import com.fis.crm.commons.DateUtil;
import com.fis.crm.commons.ExportUtils;
import com.fis.crm.commons.Translator;
import com.fis.crm.config.Constants;
import com.fis.crm.service.CustomerRegisterService;
import com.fis.crm.service.dto.CustomerRegisterDTO;
import com.fis.crm.service.dto.ExcelColumn;
import com.fis.crm.service.dto.ExcelTitle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.InputStreamResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.PaginationUtil;

import java.io.ByteArrayInputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/customer-register")
public class CustomerRegisterResource {

    private final Logger log = LoggerFactory.getLogger(CustomerRegisterResource.class);

    private final ExportUtils exportUtils;

    private final CustomerRegisterService customerRegisterService;

    public CustomerRegisterResource(ExportUtils exportUtils, CustomerRegisterService customerRegisterService) {
        this.exportUtils = exportUtils;
        this.customerRegisterService = customerRegisterService;
    }

    @GetMapping("/search")
    public ResponseEntity<List<CustomerRegisterDTO>> search(@RequestParam(required = false) String cid,
                                                            @RequestParam(required = false) String taxCode,
                                                            @RequestParam(required = false) String name,
                                                            @RequestParam(required = false) String requestType,
                                                            @RequestParam(required = false) String deadline,
                                                            @RequestParam(required = false) String ctsStatus,
                                                            @RequestParam String sendDateFrom,
                                                            @RequestParam String sendDateTo,
                                                            Pageable pageable) {
        log.info("REST request to search in CustomerRegisterResource");
        CustomerRegisterDTO customerRegisterDTO = new CustomerRegisterDTO();
        customerRegisterDTO.setCid(cid);
        customerRegisterDTO.setTaxCode(taxCode);
        customerRegisterDTO.setName(name);
        customerRegisterDTO.setRequestType(requestType);
        customerRegisterDTO.setDeadline(deadline);
        customerRegisterDTO.setCtsStatus(ctsStatus);
        customerRegisterDTO.setSendDateFrom(sendDateFrom);
        customerRegisterDTO.setSendDateTo(sendDateTo);
        try {
            Page<CustomerRegisterDTO> page = customerRegisterService
                .search(customerRegisterDTO, pageable);
            HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
            return ResponseEntity.ok().headers(headers).body(page.getContent());
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return null;
    }

    @PostMapping("/export")
    public ResponseEntity<?> export(@RequestBody CustomerRegisterDTO customerRegisterDTO, Pageable pageable) throws Exception {
        try {
            List<CustomerRegisterDTO> lstDatas = customerRegisterService
                .search(customerRegisterDTO, pageable).getContent();
            for (CustomerRegisterDTO data : lstDatas) {
                SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
                data.setSendDateDisplay(formatter.format(data.getSendDate()));
                data.setApproveDateDisplay(formatter.format(data.getApproveDate()));
                data.setReleaseTimeDisplay(formatter.format(data.getReleaseTime()));
                String requestType = data.getRequestType();
                String deadline = data.getDeadline();
                String ctsStatus = data.getCtsStatus();

                if ("1".equals(requestType))
                    data.setRequestTypeDisplay("Đăng ký mới");
                else data.setRequestTypeDisplay("Cập nhật thông tin");

                if ("1".equals(deadline))
                    data.setDeadlineDisplay("Đúng hạn");
                else data.setDeadlineDisplay("Quá hạn");

                if ("1".equals(ctsStatus))
                    data.setCtsStatusDisplay("Đã cấp");
                else data.setCtsStatusDisplay("Chưa cấp");


            }
            List<ExcelColumn> lstColumn = new ArrayList<>();
            if (customerRegisterDTO.getExportType() == 2){
                lstColumn.add(new ExcelColumn("recordsCode", "Mã hồ sơ", ExcelColumn.ALIGN_MENT.LEFT, 20));
            }
            lstColumn.add(new ExcelColumn("cid", "Mã định danh", ExcelColumn.ALIGN_MENT.LEFT, 20));
            lstColumn.add(new ExcelColumn("taxCode", "Mã số thuế", ExcelColumn.ALIGN_MENT.LEFT, 20));
            lstColumn.add(new ExcelColumn("name", "Tên đơn vị", ExcelColumn.ALIGN_MENT.LEFT, 30));
            lstColumn.add(new ExcelColumn("requestTypeDisplay", "Loại yêu cầu", ExcelColumn.ALIGN_MENT.LEFT, 20));
            lstColumn.add(new ExcelColumn("sendDateDisplay", "Thời điểm gửi", ExcelColumn.ALIGN_MENT.LEFT, 20));
            lstColumn.add(new ExcelColumn("approveDateDisplay", "Thời điểm duyệt", ExcelColumn.ALIGN_MENT.LEFT, 20));
            lstColumn.add(new ExcelColumn("deadlineDisplay", "Hạn xử lý", ExcelColumn.ALIGN_MENT.CENTER, 20));
            if (customerRegisterDTO.getExportType() == 1){
                lstColumn.add(new ExcelColumn("ctsStatusDisplay", "Tình trạng cấp CTS", ExcelColumn.ALIGN_MENT.CENTER, 20));
                lstColumn.add(new ExcelColumn("releaseTimeDisplay", "Thời điểm cấp", ExcelColumn.ALIGN_MENT.CENTER, 20));
            }
            if (customerRegisterDTO.getExportType() == 2){
                lstColumn.add(new ExcelColumn("reasons", "Lý do bảo lưu", ExcelColumn.ALIGN_MENT.LEFT, 20));
            }

            String title = "Danh sách các yêu cầu đăng ký tham gia";
            String fileName = Translator.toLocale("exportFileExcel.export.fileName");
            ExcelTitle excelTitle = new ExcelTitle(title, "Ngày xuất: ", DateUtil.dateToString(new Date(), Constants.DATE_FORMAT_DDMMYYY));

            ByteArrayInputStream byteArrayInputStream = exportUtils.onExport(lstColumn, lstDatas, 3, 0, excelTitle, true);
            InputStreamResource resource = new InputStreamResource(byteArrayInputStream);
            return ResponseEntity.ok()
                .contentLength(byteArrayInputStream.available())
                .header("filename", fileName)
                .contentType(MediaType.parseMediaType("application/octet-stream"))
                .body(resource);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return ResponseEntity.badRequest().body("Error");
    }
}
