package com.fis.crm.service;

import com.fis.crm.commons.Translator;
import com.fis.crm.config.Constants;
import com.fis.crm.domain.Ticket;
import com.fis.crm.repository.TicketRepository;
import com.fis.crm.web.rest.errors.BusinessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class InBoundService extends InOutBoundService {

    final EvaluateAssignmentDetailService evaluateAssignmentDetailService;
    final TicketRepository ticketRepository;

    public InBoundService(TicketRepository ticketRepository, EvaluateAssignmentDetailService evaluateAssignmentDetailService) {
        super(evaluateAssignmentDetailService);
        this.evaluateAssignmentDetailService = evaluateAssignmentDetailService;
        this.ticketRepository = ticketRepository;
    }

    @Override
    public boolean updateStatusCall(Long objectId) {
        Ticket ticket = ticketRepository.findById(objectId).orElseThrow(() -> new BusinessException("101", Translator.toLocale("call.notfound")));
        ticket.setEvaluateStatus(Constants.IN_OUT_EVALUATE_STATUS.EVALUATED);
        ticketRepository.save(ticket);
        return true;
    }
}
