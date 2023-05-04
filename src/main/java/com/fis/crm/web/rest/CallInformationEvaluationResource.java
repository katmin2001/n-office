package com.fis.crm.web.rest;

import com.fis.crm.repository.CallInformationEvaluationRepository;
import com.fis.crm.service.UserService;
import com.fis.crm.service.dto.CallInformationEvaluationDTO;
import com.fis.crm.web.rest.errors.BadRequestAlertException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.PaginationUtil;

import java.util.List;

@RestController
@RequestMapping("api/call-information-evaluation")
public class CallInformationEvaluationResource {

    final
    CallInformationEvaluationRepository callInformationEvaluationRepository;
    private final UserService userService;

    public CallInformationEvaluationResource(CallInformationEvaluationRepository callInformationEvaluationRepository,
                                             UserService userService) {
        this.callInformationEvaluationRepository = callInformationEvaluationRepository;
        this.userService = userService;
    }

    @GetMapping("get-call-info-by-id/{id}")
    public ResponseEntity<CallInformationEvaluationDTO> getCallInfoById(@PathVariable(value = "id") Long id) {
        try {
            return ResponseEntity.ok().body(callInformationEvaluationRepository.getCallInfoById(id));
        } catch (Exception e) {
            throw new BadRequestAlertException(e.getMessage(), "", "");
        }
    }

    @GetMapping("incoming-call")
    public ResponseEntity<List<CallInformationEvaluationDTO>> getIncomingCall(@RequestParam(value = "operator", required = false) Long operator,
                                                                              @RequestParam(value = "create_date_from") String create_date_from,
                                                                              @RequestParam(value = "create_date_to") String create_date_to,
                                                                              @RequestParam(value = "callDurationFrom", required = false) String callDurationFrom,
                                                                              @RequestParam(value = "callDurationTo", required = false) String callDurationTo,
                                                                              @RequestParam(value = "phoneNumber", required = false) String phone_number,
                                                                              Pageable pageable) {
        Page<CallInformationEvaluationDTO> page = callInformationEvaluationRepository
            .getIncomingCall(operator, create_date_from, create_date_to, callDurationFrom, phone_number, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    @GetMapping("outgoing-call")
    public ResponseEntity<?> getOutGoingCall(@RequestParam(value = "operator", required = false) Long operator,
                                             @RequestParam(value = "create_date_from") String create_date_from,
                                             @RequestParam(value = "create_date_to") String create_date_to,
                                             @RequestParam(value = "callDurationFrom", required = false) String callDurationFrom,
                                             @RequestParam(value = "callDurationTo", required = false) String callDurationTo,
                                             @RequestParam(value = "phoneNumber", required = false) String phone_number,
                                             Pageable pageable) {
        Page<CallInformationEvaluationDTO> page = callInformationEvaluationRepository
            .getOutGoingCall(operator, create_date_from, create_date_to, callDurationFrom, phone_number, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    @GetMapping("get-id-from-process")
    public ResponseEntity<?> getIdFromProcess(@RequestParam(value = "id_call", required = false) Long idCall,
                                              @RequestParam(value = "channel_id", required = false) Long channelId,
                                              @RequestParam(value = "evaluater_id", required = false) Long evaluaterId,
                                              @RequestParam(value = "evaluated_id", required = false) Long evaluatedId,
                                              @RequestParam(value = "evaluate_detail_id", required = false) Long evaluateDetailId) {
        try {
            Long id = callInformationEvaluationRepository.getIdFromProcess(idCall, channelId, evaluaterId, evaluatedId, evaluateDetailId);
            return ResponseEntity.ok().body(id);
        } catch (Exception e) {
            throw new BadRequestAlertException(e.getMessage(), "", "");
        }
    }
}
