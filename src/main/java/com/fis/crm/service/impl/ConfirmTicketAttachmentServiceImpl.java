package com.fis.crm.service.impl;

import com.fis.crm.domain.ProccessTicketAttachment;
import com.fis.crm.domain.ConfirmTicketAttachment;

import com.fis.crm.repository.ProcessTicketAttachmentRepository;
import com.fis.crm.repository.ConfirmTicketAttachmentRepository;

import com.fis.crm.service.ProcessTicketAttachmentService;
import com.fis.crm.service.ConfirmTicketAttachmentService;

import com.fis.crm.service.dto.ProcessTicketAttachmentDTO;
import com.fis.crm.service.dto.ConfirmTicketAttachmentDTO;

import com.fis.crm.service.mapper.ProcessTicketAttachmentMapper;
import com.fis.crm.service.mapper.ConfirmTicketAttachmentMapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link ConfirmTicketAttachment}.
 */
@Service
@Transactional
public class ConfirmTicketAttachmentServiceImpl implements ConfirmTicketAttachmentService {

    private final Logger log = LoggerFactory.getLogger(ConfirmTicketAttachmentServiceImpl.class);

    private final ConfirmTicketAttachmentRepository confirmTicketAttachmentRepository;

    private final ConfirmTicketAttachmentMapper confirmTicketAttachmentMapper;

    public ConfirmTicketAttachmentServiceImpl(ConfirmTicketAttachmentRepository confirmTicketAttachmentRepository,
                                              ConfirmTicketAttachmentMapper confirmTicketAttachmentMapper) {
        this.confirmTicketAttachmentRepository = confirmTicketAttachmentRepository;
        this.confirmTicketAttachmentMapper = confirmTicketAttachmentMapper;
    }

    @Override
    public ConfirmTicketAttachmentDTO save(ConfirmTicketAttachmentDTO confirmTicketAttachmentDTO) {
        log.debug("Request to save ConfirmTicketAttachment : {}", confirmTicketAttachmentDTO);
        ConfirmTicketAttachment confirmTicketAttachment = confirmTicketAttachmentMapper.toEntity(confirmTicketAttachmentDTO);
        confirmTicketAttachment = confirmTicketAttachmentRepository.save(confirmTicketAttachment);
        return confirmTicketAttachmentMapper.toDto(confirmTicketAttachment);
    }

    @Override
    public Optional<ConfirmTicketAttachmentDTO> partialUpdate(ConfirmTicketAttachmentDTO confirmTicketAttachmentDTO) {
        log.debug("Request to partially update ConfirmTicketAttachment : {}", confirmTicketAttachmentDTO);

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
    public Page<ConfirmTicketAttachmentDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ConfirmTicketAttachment");
        return confirmTicketAttachmentRepository.findAll(pageable).map(confirmTicketAttachmentMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ConfirmTicketAttachmentDTO> findOne(Long id) {
        log.debug("Request to get ConfirmTicketAttachment : {}", id);
        return confirmTicketAttachmentRepository.findById(id).map(confirmTicketAttachmentMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete ConfirmTicketAttachment : {}", id);
        confirmTicketAttachmentRepository.deleteById(id);
    }

    public List<ConfirmTicketAttachmentDTO> getAllByTicketId(Long ticketId) {
        return confirmTicketAttachmentRepository.getAllByTicketId(ticketId).map(confirmTicketAttachmentMapper::toDto).get();
    }
}
