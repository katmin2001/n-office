package com.fis.crm.repository;

import com.fis.crm.service.dto.CallInformationEvaluationDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CallInformationEvaluationRepository {

    CallInformationEvaluationDTO getCallInfoById(Long assign_detail_id);

    Page<CallInformationEvaluationDTO> getIncomingCall(Long user_id,
                                                       String create_date_from,
                                                       String create_date_to,
                                                       String call_time,
                                                       String phone_number,
                                                       Pageable pageable);

    Page<CallInformationEvaluationDTO> getOutGoingCall(Long user_id,
                                                       String call_time_from,
                                                       String call_time_to,
                                                       String call_time,
                                                       String phone_number,
                                                       Pageable pageable);

    Long getIdFromProcess(Long idCall, Long channelId, Long evaluaterId, Long evaluatedId, Long evaluateDetailId);
}
