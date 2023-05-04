package com.fis.crm.web.rest;

import com.fis.crm.commons.DateUtil;
import com.fis.crm.commons.ExportUtils;
import com.fis.crm.commons.Translator;
import com.fis.crm.config.Constants;
import com.fis.crm.service.ActionLogService;
import com.fis.crm.service.dto.ActionLogDTO;
import com.fis.crm.service.dto.ExcelColumn;
import com.fis.crm.service.dto.ExcelTitle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.PaginationUtil;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api")
public class ActionLogResource {

    private final Logger log = LoggerFactory.getLogger(ActionLogResource.class);

    private final ActionLogService actionLogService;
    private final ExportUtils exportUtils;

    public ActionLogResource(ActionLogService actionLogService, ExportUtils exportUtils) {
        this.actionLogService = actionLogService;
        this.exportUtils = exportUtils;
    }

    @PostMapping("/action-logs")
    public ResponseEntity<List<ActionLogDTO>> getAllActionLogs(@RequestBody ActionLogDTO actionLogDTO, Pageable pageable) {
        Page<ActionLogDTO> page = actionLogService.getActionLog(actionLogDTO, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    @PostMapping("/action-logs/report")
    public ResponseEntity<Resource> actionLogsReport(@RequestBody ActionLogDTO actionLogDTO) throws Exception {
        List<ActionLogDTO> lstDatas = actionLogService.getAllActionLog(actionLogDTO);

        List<ExcelColumn> lstColumn = new ArrayList<>();
        lstColumn.add(new ExcelColumn("menuName", "Phân hệ thao tác", ExcelColumn.ALIGN_MENT.LEFT));
        lstColumn.add(new ExcelColumn("menuItemName", "Chức năng thao tác", ExcelColumn.ALIGN_MENT.LEFT));
        lstColumn.add(new ExcelColumn("userName", "Người thao tác", ExcelColumn.ALIGN_MENT.LEFT));
        lstColumn.add(new ExcelColumn("createDatetimeName", "Thời gian thao tác", ExcelColumn.ALIGN_MENT.LEFT));
        lstColumn.add(new ExcelColumn("note", "Nội dung thao tác", ExcelColumn.ALIGN_MENT.LEFT));
        lstColumn.add(new ExcelColumn("actionTypeName", "Loại thao tác", ExcelColumn.ALIGN_MENT.LEFT));

        String title = "Lịch sử thao tác hệ thống";
        String fileName = Translator.toLocale("evaluate.result.export.fileName");
        ExcelTitle excelTitle = new ExcelTitle(title, DateUtil.dateToString(new Date(), Constants.DATE_FORMAT_DDMMYYY), DateUtil.dateToString(new Date(), Constants.DATE_FORMAT_DDMMYYY));

        ByteArrayInputStream byteArrayInputStream = exportUtils.onExport(lstColumn, lstDatas, 3, 0, excelTitle, true);
        InputStreamResource resource = new InputStreamResource(byteArrayInputStream);
        return ResponseEntity.ok()
            .contentLength(byteArrayInputStream.available())
            .header("filename", fileName)
            .contentType(MediaType.parseMediaType("application/octet-stream"))
            .body(resource);
    }
}
