package com.fis.crm.web.rest;

import com.fis.crm.commons.DateUtil;
import com.fis.crm.commons.ExportUtils;
import com.fis.crm.commons.Translator;
import com.fis.crm.config.Constants;
import com.fis.crm.service.CompanySuspendService;
import com.fis.crm.service.dto.CompanySuspendDTO;
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
@RequestMapping("/api/company-suspend")
public class CompanySuspendResource {

    private final ExportUtils exportUtils;

    private final CompanySuspendService companySuspendService;

    private final Logger log = LoggerFactory.getLogger(CompanySuspendResource.class);

    public CompanySuspendResource(CompanySuspendService companySuspendService, ExportUtils exportUtils) {
        this.companySuspendService = companySuspendService;
        this.exportUtils = exportUtils;
    }

    @GetMapping("/search")
    public ResponseEntity<List<CompanySuspendDTO>> search(@RequestParam(required = false) String cid,
                                                          @RequestParam(required = false) String taxCode,
                                                          @RequestParam(required = false) String name,
                                                          @RequestParam(required = false) String suspendTimeFrom,
                                                          @RequestParam(required = false) String suspendTimeTo,
                                                          Pageable pageable) {
        log.info("REST request to search in CompanySuspendResource");
        CompanySuspendDTO companySuspendDTO = new CompanySuspendDTO();
        companySuspendDTO.setCid(cid);
        companySuspendDTO.setTaxCode(taxCode);
        companySuspendDTO.setName(name);
        companySuspendDTO.setSuspendTimeFrom(suspendTimeFrom);
        companySuspendDTO.setSuspendTimeTo(suspendTimeTo);
        try {
            Page<CompanySuspendDTO> page = companySuspendService
                .search(companySuspendDTO, pageable);
            HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
            return ResponseEntity.ok().headers(headers).body(page.getContent());
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return null;
    }

    @PostMapping("/export")
    public ResponseEntity<?> export(@RequestBody CompanySuspendDTO companySuspendDTO, Pageable pageable) throws Exception {
        try {
            List<CompanySuspendDTO> lstDatas = companySuspendService
                .search(companySuspendDTO, pageable).getContent();
            for (CompanySuspendDTO data : lstDatas) {
                SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
                data.setSuspendTimeDisplay(formatter.format(data.getSuspendTime()));
            }
            List<ExcelColumn> lstColumn = new ArrayList<>();
            lstColumn.add(new ExcelColumn("cid", "Mã định danh", ExcelColumn.ALIGN_MENT.LEFT));
            lstColumn.add(new ExcelColumn("taxCode", "Mã số thuế", ExcelColumn.ALIGN_MENT.LEFT));
            lstColumn.add(new ExcelColumn("name", "Tên đơn vị", ExcelColumn.ALIGN_MENT.LEFT));
            lstColumn.add(new ExcelColumn("reason", "Lý do", ExcelColumn.ALIGN_MENT.LEFT));
            lstColumn.add(new ExcelColumn("suspendTimeDisplay", "Ngày tạm ngừng", ExcelColumn.ALIGN_MENT.CENTER));

            String title = "Danh sách các cơ quan/đơn vị chấm dứt/tạm ngừng do tình trạng pháp lý";
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
