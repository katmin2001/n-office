package com.fis.crm.web.rest;

import com.fis.crm.commons.DateUtil;
import com.fis.crm.commons.ExportUtils;
import com.fis.crm.commons.Translator;
import com.fis.crm.config.Constants;
import com.fis.crm.domain.User;
import com.fis.crm.security.SecurityUtils;
import com.fis.crm.service.*;
import com.fis.crm.service.dto.*;
import com.fis.crm.web.rest.errors.BadRequestAlertException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

import java.io.ByteArrayInputStream;
import java.text.ParseException;
import java.util.*;

/**
 * REST controller for managing {@link com.fis.crm.domain.EvaluateResult}.
 */
@RestController
@RequestMapping("/api")
public class EvaluateResultResource {

    private final Logger log = LoggerFactory.getLogger(EvaluateResultResource.class);

    private static final String ENTITY_NAME = "evaluateResult";

    private final EvaluateResultService evaluateResultService;
    private final ExportUtils exportUtils;
    private final EvaluateResultDetailService evaluateResultDetailService;
    private final UserService userService;
    private final ActionLogService actionLogService;
    private final EvaluateResultHistoryService evaluateResultHistoryService;

    public EvaluateResultResource(EvaluateResultService evaluateResultService, ExportUtils exportUtils, EvaluateResultDetailService evaluateResultDetailService, UserService userService, ActionLogService actionLogService, EvaluateResultHistoryService evaluateResultHistoryService) {
        this.evaluateResultService = evaluateResultService;
        this.exportUtils = exportUtils;
        this.evaluateResultDetailService = evaluateResultDetailService;
        this.userService = userService;
        this.actionLogService = actionLogService;
        this.evaluateResultHistoryService = evaluateResultHistoryService;
    }

    /**
     * danh gia tieu chi
     * @param evaluateResultDTO
     * @return
     * @throws Exception
     */
    @PostMapping("/evaluate-results")
    public ResponseEntity<Boolean> createEvaluateResult(@RequestBody EvaluateResultDTO evaluateResultDTO) throws Exception {
        log.debug("REST request to save EvaluateResult : {}", evaluateResultDTO);
        Boolean result = evaluateResultService.createEvaluateResult(evaluateResultDTO);
        return ResponseEntity.ok().body(result);
    }

    /**
     * danh gia lai cuoc goi
     * @param evaluateResultDTO
     * @return
     * @throws Exception
     */
    @PutMapping("/evaluate-results")
    public ResponseEntity<Boolean> updateEvaluateResult(@RequestBody EvaluateResultDTO evaluateResultDTO) throws Exception {
        log.debug("REST request to update EvaluateResult : {}", evaluateResultDTO);
        if (evaluateResultDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Boolean result = evaluateResultService.reEvaluateResult(evaluateResultDTO);
        return ResponseEntity.ok().body(result);
    }

    @GetMapping("/evaluate-results/{id}/{type}")
    public ResponseEntity<EvaluateResultDTO> getEvaluateResult(@PathVariable Long id, @PathVariable String type) {
        log.debug("REST request to get EvaluateResult : {}", id);
        Optional<EvaluateResultDTO> evaluateResultDTO = evaluateResultService.findOne(id, type);
        return ResponseUtil.wrapOrNotFound(evaluateResultDTO);
    }

    /**
     * Lay thong tin ticket
     * @param id
     * @return
     */
    @GetMapping("/evaluate-results/get-for-evaluate")
    public ResponseEntity<EvaluateResultDTO> getTicketForEvaluate(@RequestParam Long id) {
        log.debug("REST request to delete getTicketForEvaluate : {},", id);
        return ResponseUtil.wrapOrNotFound(evaluateResultService.getTicketForEvaluate(id));
    }

    /**
     * Tim kiem danh gia cuoc goi
     * @param evaluateResultDTO
     * @param pageable
     * @return
     */
    @PostMapping("/evaluate-results/all")
    public ResponseEntity<List<EvaluateResultDTO>> getAllEvaluateResult(@RequestBody EvaluateResultDTO evaluateResultDTO, Pageable pageable) throws ParseException {
        if(checkRole().equals("0"))
        {
            User user = userService.findOneByLogin(SecurityUtils.getCurrentUserLogin().get()).get();
            evaluateResultDTO.setEvaluatedId(user.getId());
        }
        Page<EvaluateResultDTO> page = evaluateResultService.getEvaluateResults(evaluateResultDTO, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    public String checkRole() {
        try{
            User user = userService.findOneByLogin(SecurityUtils.getCurrentUserLogin().get()).get();
            List<UserDTO> lsLead=userService.getLeadTDV();
            //Neu la role TDV thi chi load minh TDV
            for(UserDTO l:lsLead)
            {
                if(l.getLogin().equals(user.getLogin()))
                {
                    return "1";
                }
            }
            return "0";
        } catch (Exception e){
            throw new BadRequestAlertException("", "", "");
        }
    }

    @PostMapping("/evaluate-results/export")
    public ResponseEntity<Resource> export(@RequestBody EvaluateResultDTO evaluateResultDTO) throws Exception {
        if(checkRole().equals("0"))
        {
            User user = userService.findOneByLogin(SecurityUtils.getCurrentUserLogin().get()).get();
            evaluateResultDTO.setEvaluatedId(user.getId());
        }
        List<EvaluateResultDTO> lstDatas = evaluateResultService.getAllEvaluateResults(evaluateResultDTO);

        List<ExcelColumn> lstColumn = new ArrayList<>();
        lstColumn.add(new ExcelColumn("channelTypeName", "Kênh tiếp nhận", ExcelColumn.ALIGN_MENT.LEFT));
        lstColumn.add(new ExcelColumn("phoneNumber", "Số điện thoại", ExcelColumn.ALIGN_MENT.LEFT));
        lstColumn.add(new ExcelColumn("evaluaterName", "Người đánh giá", ExcelColumn.ALIGN_MENT.LEFT));
        lstColumn.add(new ExcelColumn("createDatetimeName", "Ngày đánh giá", ExcelColumn.ALIGN_MENT.LEFT));
        lstColumn.add(new ExcelColumn("evaluatedName", "Tổng đài viên", ExcelColumn.ALIGN_MENT.LEFT));
        lstColumn.add(new ExcelColumn("recordDateName", "Ngày gọi", ExcelColumn.ALIGN_MENT.LEFT));
        lstColumn.add(new ExcelColumn("content", "Nội dung đánh giá", ExcelColumn.ALIGN_MENT.LEFT));
        lstColumn.add(new ExcelColumn("suggest", "Đề xuất đánh giá", ExcelColumn.ALIGN_MENT.LEFT));
        lstColumn.add(new ExcelColumn("totalScores", "Điểm", ExcelColumn.ALIGN_MENT.RIGHT));
        lstColumn.add(new ExcelColumn("totalScoresNew", "Điểm cập nhật", ExcelColumn.ALIGN_MENT.RIGHT));
        lstColumn.add(new ExcelColumn("criteriaRatingName", "Xếp loại", ExcelColumn.ALIGN_MENT.LEFT));
        lstColumn.add(new ExcelColumn("error1Name", "Lỗi chuyên môn", ExcelColumn.ALIGN_MENT.LEFT));
        lstColumn.add(new ExcelColumn("error2Name", "Lỗi nghiệp vụ", ExcelColumn.ALIGN_MENT.LEFT));

        String title = "Danh sách đánh giá cuộc gọi";
        String fileName = Translator.toLocale("evaluate.result.export.fileName");
        ExcelTitle excelTitle = new ExcelTitle(title, DateUtil.dateToString(new Date(), Constants.DATE_FORMAT_DDMMYYY), DateUtil.dateToString(new Date(), Constants.DATE_FORMAT_DDMMYYY));

        ByteArrayInputStream byteArrayInputStream = exportUtils.onExport(lstColumn, lstDatas, 3, 0, excelTitle, true);
        InputStreamResource resource = new InputStreamResource(byteArrayInputStream);
        Optional<User> userLog = userService.getUserWithAuthorities();
        actionLogService.saveActionLog(new ActionLogDTO(userLog.get().getId(), Constants.ACTION_LOG_TYPE.EXPORT + "",
            null, String.format("Xuất excel: Danh sách cuộc gọi được giám sát"),
            new Date(), Constants.MENU_ID.QA, Constants.MENU_ITEM_ID.evaluate_list, "CONFIG_MENU_ITEM"));
        return ResponseEntity.ok()
            .contentLength(byteArrayInputStream.available())
            .header("filename", fileName)
            .contentType(MediaType.parseMediaType("application/octet-stream"))
            .body(resource);
    }

    @PostMapping("/evaluate-results/reportDetail")
    public ResponseEntity<Resource> reportDetail(@RequestBody EvaluateResultDTO evaluateResultDTO) throws Exception {
        List<EvaluateResultDTO> lstDatas = evaluateResultService.getAllEvaluateResults(evaluateResultDTO);

        List<ExcelColumn> lstColumn = new ArrayList<>();
        lstColumn.add(new ExcelColumn("evaluaterName", "Người đánh giá", ExcelColumn.ALIGN_MENT.LEFT));
        lstColumn.add(new ExcelColumn("createDatetimeName", "Ngày chấm điểm", ExcelColumn.ALIGN_MENT.LEFT));
        lstColumn.add(new ExcelColumn("evaluatedName", "Tổng đài viên", ExcelColumn.ALIGN_MENT.LEFT));
        lstColumn.add(new ExcelColumn("content", "Nội dung đánh giá", ExcelColumn.ALIGN_MENT.LEFT));
        lstColumn.add(new ExcelColumn("suggest", "Đề xuất đánh giá", ExcelColumn.ALIGN_MENT.LEFT));
        lstColumn.add(new ExcelColumn("totalScores", "Điểm", ExcelColumn.ALIGN_MENT.RIGHT));
        lstColumn.add(new ExcelColumn("totalScoresNew", "Điểm cập nhật", ExcelColumn.ALIGN_MENT.RIGHT));
        lstColumn.add(new ExcelColumn("criteriaRatingName", "Xếp loại", ExcelColumn.ALIGN_MENT.LEFT));
        lstColumn.add(new ExcelColumn("error1Name", "Lỗi chuyên môn", ExcelColumn.ALIGN_MENT.LEFT));
        lstColumn.add(new ExcelColumn("error2Name", "Lỗi nghiệp vụ", ExcelColumn.ALIGN_MENT.LEFT));

        String title = "Báo cáo điểm đánh giá tổng đài viên";
        String fileName = Translator.toLocale("evaluate.result.export.fileName");
        ExcelTitle excelTitle = new ExcelTitle(title, DateUtil.dateToString(new Date(), Constants.DATE_FORMAT_DDMMYYY), DateUtil.dateToString(new Date(), Constants.DATE_FORMAT_DDMMYYY));

        ByteArrayInputStream byteArrayInputStream = exportUtils.onExport(lstColumn, lstDatas, 3, 0, excelTitle, true);
        InputStreamResource resource = new InputStreamResource(byteArrayInputStream);
        Optional<User> userLog = userService.getUserWithAuthorities();
        actionLogService.saveActionLog(new ActionLogDTO(userLog.get().getId(), Constants.ACTION_LOG_TYPE.EXPORT + "",
            null, String.format("Xuất excel: Báo cáo đánh giá tổng đài viên"),
            new Date(), Constants.MENU_ID.QA, Constants.MENU_ITEM_ID.report_evaluate_score, "CONFIG_MENU_ITEM"));
        return ResponseEntity.ok()
            .contentLength(byteArrayInputStream.available())
            .header("filename", fileName)
            .contentType(MediaType.parseMediaType("application/octet-stream"))
            .body(resource);
    }


    /**
     * Bao cao Chat luong cuoc goi
     * @param evaluateResultDTO
     * @param pageable
     * @return
     * @throws Exception
     */
    @PostMapping("/evaluate-results/ratingCall")
    public ResponseEntity<List<EvaluateResultRatingDTO>> getRatingCall(@RequestBody EvaluateResultDTO evaluateResultDTO, Pageable pageable) throws Exception {
        Page<EvaluateResultRatingDTO> page = evaluateResultService.getRatingCall(evaluateResultDTO, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    @PostMapping("/evaluate-results/ratingCallReport")
    public ResponseEntity<Resource> ratingCallReport(@RequestBody EvaluateResultDTO evaluateResultDTO) throws Exception {
        List<EvaluateResultRatingDTO> lstDatas = evaluateResultService.getAllRatingCall(evaluateResultDTO);

        List<ExcelColumn> lstColumn = new ArrayList<>();
        lstColumn.add(new ExcelColumn("criteriaRatingName", "Xếp loại đánh giá", ExcelColumn.ALIGN_MENT.LEFT));
        lstColumn.add(new ExcelColumn("times", "Số lần", ExcelColumn.ALIGN_MENT.RIGHT));
        lstColumn.add(new ExcelColumn("totalCall", "Tổng số cuộc gọi", ExcelColumn.ALIGN_MENT.RIGHT));
        lstColumn.add(new ExcelColumn("rate", "Tỷ lệ", ExcelColumn.ALIGN_MENT.RIGHT));

        String title = "Báo cáo thống kê chất lượng cuộc gọi";
        String fileName = Translator.toLocale("evaluate.result.export.fileName");
        ExcelTitle excelTitle = new ExcelTitle(title, DateUtil.dateToString(new Date(), Constants.DATE_FORMAT_DDMMYYY), DateUtil.dateToString(new Date(), Constants.DATE_FORMAT_DDMMYYY));

        ByteArrayInputStream byteArrayInputStream = exportUtils.onExport(lstColumn, lstDatas, 3, 0, excelTitle, true);
        InputStreamResource resource = new InputStreamResource(byteArrayInputStream);
        Optional<User> userLog = userService.getUserWithAuthorities();
        actionLogService.saveActionLog(new ActionLogDTO(userLog.get().getId(), Constants.ACTION_LOG_TYPE.EXPORT + "",
            null, String.format("Xuất excel: Báo cáo thống kê chất lượng cuộc gọi"),
            new Date(), Constants.MENU_ID.QA, Constants.MENU_ITEM_ID.report_evaluate_rating, "CONFIG_MENU_ITEM"));
        return ResponseEntity.ok()
            .contentLength(byteArrayInputStream.available())
            .header("filename", fileName)
            .contentType(MediaType.parseMediaType("application/octet-stream"))
            .body(resource);
    }

    @PostMapping("/evaluate-results/criteriaCall")
    public ResponseEntity<List<CriteriaCallDTO>> getCriteriaCall(@RequestBody EvaluateResultDetailDTO evaluateResultDetailDTO, Pageable pageable) {
        Page<CriteriaCallDTO> page = evaluateResultDetailService.getCriteriaDetailCall(evaluateResultDetailDTO, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    @PostMapping("/evaluate-results/criteriaCallReport")
    public ResponseEntity<Resource> criteriaCallReport(@RequestBody EvaluateResultDetailDTO evaluateResultDetailDTO) throws Exception {
        List<CriteriaCallDTO> lstDatas = evaluateResultDetailService.getAllCriteriaDetailCall(evaluateResultDetailDTO);

        List<ExcelColumn> lstColumn = new ArrayList<>();
        lstColumn.add(new ExcelColumn("criteriaName", "Tiêu chí", ExcelColumn.ALIGN_MENT.LEFT));
        lstColumn.add(new ExcelColumn("criteriaPerCall", "Lỗi/Tổng cuộc gọi", ExcelColumn.ALIGN_MENT.RIGHT));
        lstColumn.add(new ExcelColumn("rate", "Tỷ lệ", ExcelColumn.ALIGN_MENT.RIGHT));

        String title = "Báo cáo tiêu chí cuộc gọi";
        String fileName = Translator.toLocale("evaluate.result.export.fileName");
        ExcelTitle excelTitle = new ExcelTitle(title, DateUtil.dateToString(new Date(), Constants.DATE_FORMAT_DDMMYYY), DateUtil.dateToString(new Date(), Constants.DATE_FORMAT_DDMMYYY));

        ByteArrayInputStream byteArrayInputStream = exportUtils.onExport(lstColumn, lstDatas, 3, 0, excelTitle, true);
        InputStreamResource resource = new InputStreamResource(byteArrayInputStream);
        Optional<User> userLog = userService.getUserWithAuthorities();
        actionLogService.saveActionLog(new ActionLogDTO(userLog.get().getId(), Constants.ACTION_LOG_TYPE.EXPORT + "",
            null, String.format("Xuất excel: Báo cáo thống kê lỗi theo tiêu chí"),
            new Date(), Constants.MENU_ID.QA, Constants.MENU_ITEM_ID.report_evaluate_criteria, "CONFIG_MENU_ITEM"));
        return ResponseEntity.ok()
            .contentLength(byteArrayInputStream.available())
            .header("filename", fileName)
            .contentType(MediaType.parseMediaType("application/octet-stream"))
            .body(resource);
    }

    @GetMapping("/evaluate-results/getEvaluateHistory/{id}")
    public ResponseEntity<List<List<String>>> getEvaluateHistory(@PathVariable("id") Long id) {
        Map<String, List<String>> map = evaluateResultHistoryService.getEvaluateResultHistoryByEvaluate(id);
        List<String> listContent = new ArrayList<>();
        List<String> listSuggest = new ArrayList<>();
        for (Map.Entry<String, List<String>> entry : map.entrySet()) {
            if (entry.getKey().equalsIgnoreCase("content")) {
                listContent = entry.getValue();
            } else if (entry.getKey().equalsIgnoreCase("suggest")) {
                listSuggest = entry.getValue();
            }
        }
        List<List<String>> lst = new ArrayList<>();
        lst.add(listContent);
        lst.add(listSuggest);

        return ResponseEntity.ok().body(lst);
    }
}
