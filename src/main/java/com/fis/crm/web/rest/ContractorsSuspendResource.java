package com.fis.crm.web.rest;

import com.fis.crm.commons.DateUtil;
import com.fis.crm.commons.ExportUtils;
import com.fis.crm.commons.Translator;
import com.fis.crm.config.Constants;
import com.fis.crm.service.ContractorsSuspendService;
import com.fis.crm.service.dto.ContractorsSuspendDTO;
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
@RequestMapping("/api/contractors-suspend")
public class ContractorsSuspendResource {

    private final ExportUtils exportUtils;

    private final ContractorsSuspendService contractorsSuspendService;

    private final Logger log = LoggerFactory.getLogger(ContractorsSuspendResource.class);

    public ContractorsSuspendResource(ContractorsSuspendService contractorsSuspendService, ExportUtils exportUtils) {
        this.contractorsSuspendService = contractorsSuspendService;
        this.exportUtils = exportUtils;
    }

    @GetMapping("/search")
    public ResponseEntity<List<ContractorsSuspendDTO>> search(@RequestParam(required = false) String cid,
                                                              @RequestParam(required = false) String taxCode,
                                                              @RequestParam(required = false) String name,
                                                              @RequestParam(required = false) String address,
                                                              @RequestParam(required = false) String feeType,
                                                              @RequestParam(required = false) String suspendTimeFrom,
                                                              @RequestParam(required = false) String suspendTimeTo,
                                                              Pageable pageable) {
        log.info("REST request to search in ContractorsSuspendResource");
        ContractorsSuspendDTO contractorsSuspendDTO = new ContractorsSuspendDTO();
        contractorsSuspendDTO.setCid(cid);
        contractorsSuspendDTO.setTaxCode(taxCode);
        contractorsSuspendDTO.setName(name);
        contractorsSuspendDTO.setAddress(address);
        contractorsSuspendDTO.setFeeType(feeType);
        contractorsSuspendDTO.setSuspendTimeFrom(suspendTimeFrom);
        contractorsSuspendDTO.setSuspendTimeTo(suspendTimeTo);
        try {
            Page<ContractorsSuspendDTO> page = contractorsSuspendService
                .search(contractorsSuspendDTO, pageable);
            HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
            return ResponseEntity.ok().headers(headers).body(page.getContent());
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return null;
    }

    @PostMapping("/export")
    public ResponseEntity<?> export(@RequestBody ContractorsSuspendDTO contractorsSuspendDTO, Pageable pageable) throws Exception {
        try {
            List<ContractorsSuspendDTO> lstDatas = contractorsSuspendService
                .search(contractorsSuspendDTO, pageable).getContent();
            for (ContractorsSuspendDTO data : lstDatas) {
                SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
                data.setSuspendTimeDisplay(formatter.format(data.getSuspendTime()));
                String type = data.getFeeType();
                switch (type) {
                    case "1":
                        data.setFeeTypeDisplay("Phí đăng ký");
                        break;
                    case "2":
                        data.setFeeTypeDisplay("Phí duy trì");
                        break;
                    case "3":
                        data.setFeeTypeDisplay("Phí nộp HSDT");
                        break;
                    case "4":
                        data.setFeeTypeDisplay("Phí nộp HSDX");
                        break;
                    default:
                        data.setFeeTypeDisplay("Phí trúng thầu");
                        break;
                }

            }
            List<ExcelColumn> lstColumn = new ArrayList<>();
            lstColumn.add(new ExcelColumn("cid", "Mã định danh", ExcelColumn.ALIGN_MENT.LEFT));
            lstColumn.add(new ExcelColumn("taxCode", "Mã số thuế", ExcelColumn.ALIGN_MENT.LEFT));
            lstColumn.add(new ExcelColumn("name", "Tên đơn vị", ExcelColumn.ALIGN_MENT.LEFT));
            lstColumn.add(new ExcelColumn("address", "Tỉnh/thành", ExcelColumn.ALIGN_MENT.LEFT));
            lstColumn.add(new ExcelColumn("feeTypeDisplay", "Loại phí", ExcelColumn.ALIGN_MENT.LEFT));
            lstColumn.add(new ExcelColumn("suspendTimeDisplay", "Ngày tạm ngừng", ExcelColumn.ALIGN_MENT.CENTER));

            String title = "Danh sách các nhà thầu tạm ngừng do nợ phí";
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
