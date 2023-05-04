package com.fis.crm.service.impl;

import com.fis.crm.domain.ProccessTicket;
import com.fis.crm.domain.ProccessTicketAttachment;

import com.fis.crm.repository.ProcessTicketRepository;
import com.fis.crm.repository.ProcessTicketAttachmentRepository;

import com.fis.crm.service.ProcessTicketService;
import com.fis.crm.service.ProcessTicketAttachmentService;

import com.fis.crm.service.dto.ProcessTicketDTO;
import com.fis.crm.service.dto.ProcessTicketAttachmentDTO;

import com.fis.crm.service.dto.TicketRequestAttachmentDTO;
import com.fis.crm.service.mapper.ProcessTicketMapper;
import com.fis.crm.service.mapper.ProcessTicketAttachmentMapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link ProccessTicketAttachment}.
 */
@Service
@Transactional
public class ProcessTicketAttachmentServiceImpl implements ProcessTicketAttachmentService {

    private final Logger log = LoggerFactory.getLogger(ProcessTicketAttachmentServiceImpl.class);

    private final ProcessTicketAttachmentRepository processTicketAttachmentRepository;

    private final ProcessTicketAttachmentMapper processTicketAttachmentMapper;

    public ProcessTicketAttachmentServiceImpl(ProcessTicketAttachmentRepository processTicketAttachmentRepository,
                                              ProcessTicketAttachmentMapper processTicketAttachmentMapper) {
        this.processTicketAttachmentRepository = processTicketAttachmentRepository;
        this.processTicketAttachmentMapper = processTicketAttachmentMapper;
    }

    @Override
    public ProcessTicketAttachmentDTO save(ProcessTicketAttachmentDTO processTicketAttachmentDTO) {
        log.debug("Request to save ProcessTicketAttachment : {}", processTicketAttachmentDTO);
        ProccessTicketAttachment proccessTicketAttachment = processTicketAttachmentMapper.toEntity(processTicketAttachmentDTO);
        proccessTicketAttachment = processTicketAttachmentRepository.save(proccessTicketAttachment);
        return processTicketAttachmentMapper.toDto(proccessTicketAttachment);
    }

    @Override
    public Optional<ProcessTicketAttachmentDTO> partialUpdate(ProcessTicketAttachmentDTO processTicketAttachmentDTO) {
        log.debug("Request to partially update ProcessTicketAttachment : {}", processTicketAttachmentDTO);

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
    public Page<ProcessTicketAttachmentDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ProcessTicketAttachments");
        return processTicketAttachmentRepository.findAll(pageable).map(processTicketAttachmentMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ProcessTicketAttachmentDTO> findOne(Long id) {
        log.debug("Request to get ProcessTicketAttachment : {}", id);
        return processTicketAttachmentRepository.findById(id).map(processTicketAttachmentMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete ProcessTicketAttachment : {}", id);
        processTicketAttachmentRepository.deleteById(id);
    }

    public List<ProcessTicketAttachmentDTO> findAllByTicketRequestId(Long ticketRequestId) {
        return processTicketAttachmentRepository.findAllByTicketRequestId(ticketRequestId).stream().map(processTicketAttachmentMapper::toDto).collect(Collectors.toList());
    }

    @Override
    public List<ProcessTicketAttachmentDTO> findByTicketRequestId(Long ticketId) {
        return processTicketAttachmentRepository.findAllByTicketRequestId(ticketId)
            .stream().map(processTicketAttachmentMapper::toDto).collect(Collectors.toList());
    }
}
