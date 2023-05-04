package com.fis.crm.service.impl;

import com.fis.crm.config.Constants;
import com.fis.crm.service.DocumentPostAttachmentService;
import com.fis.crm.domain.DocumentPostAttachment;
import com.fis.crm.repository.DocumentPostAttachmentRepository;
import com.fis.crm.service.dto.DocumentPostAttachmentDTO;
import com.fis.crm.service.mapper.DocumentPostAttachmentMapper;
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
 * Service Implementation for managing {@link DocumentPostAttachment}.
 */
@Service
@Transactional
public class DocumentPostAttachmentServiceImpl implements DocumentPostAttachmentService {

    private final Logger log = LoggerFactory.getLogger(DocumentPostAttachmentServiceImpl.class);

    private final DocumentPostAttachmentRepository documentPostAttachmentRepository;

    private final DocumentPostAttachmentMapper documentPostAttachmentMapper;

    public DocumentPostAttachmentServiceImpl(DocumentPostAttachmentRepository documentPostAttachmentRepository, DocumentPostAttachmentMapper documentPostAttachmentMapper) {
        this.documentPostAttachmentRepository = documentPostAttachmentRepository;
        this.documentPostAttachmentMapper = documentPostAttachmentMapper;
    }

    @Override
    public DocumentPostAttachmentDTO save(DocumentPostAttachmentDTO documentPostAttachmentDTO) {
        log.debug("Request to save DocumentPostAttachment : {}", documentPostAttachmentDTO);
        DocumentPostAttachment documentPostAttachment = documentPostAttachmentMapper.toEntity(documentPostAttachmentDTO);
        documentPostAttachment = documentPostAttachmentRepository.save(documentPostAttachment);
        return documentPostAttachmentMapper.toDto(documentPostAttachment);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<DocumentPostAttachmentDTO> findAll(Pageable pageable) {
        log.debug("Request to get all DocumentPostAttachments");
        return documentPostAttachmentRepository.findAll(pageable)
            .map(documentPostAttachmentMapper::toDto);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<DocumentPostAttachmentDTO> findOne(Long id) {
        log.debug("Request to get DocumentPostAttachment : {}", id);
        return documentPostAttachmentRepository.findById(id)
            .map(documentPostAttachmentMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete DocumentPostAttachment : {}", id);
        documentPostAttachmentRepository.deleteById(id);
    }

    @Override
    public List<DocumentPostAttachmentDTO> findAllByDocumentPostId(Long documentPostId) {
        return documentPostAttachmentRepository.findAllByDocumentPostIdAndStatusOrderByCreateDatetimeDesc(documentPostId, Constants.STATUS_ACTIVE).stream().
            map(documentPostAttachmentMapper::toDto).collect(Collectors.toList());
    }
}
