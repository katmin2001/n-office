package com.fis.crm.service.impl;

import com.fis.crm.service.DocumentPostViewService;
import com.fis.crm.domain.DocumentPostView;
import com.fis.crm.repository.DocumentPostViewRepository;
import com.fis.crm.service.dto.DocumentPostViewDTO;
import com.fis.crm.service.dto.DocumentPostViewDetail;
import com.fis.crm.service.mapper.DocumentPostViewMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link DocumentPostView}.
 */
@Service
@Transactional
public class DocumentPostViewServiceImpl implements DocumentPostViewService {

    private final Logger log = LoggerFactory.getLogger(DocumentPostViewServiceImpl.class);

    private final DocumentPostViewRepository documentPostViewRepository;

    private final DocumentPostViewMapper documentPostViewMapper;

    public DocumentPostViewServiceImpl(DocumentPostViewRepository documentPostViewRepository, DocumentPostViewMapper documentPostViewMapper) {
        this.documentPostViewRepository = documentPostViewRepository;
        this.documentPostViewMapper = documentPostViewMapper;
    }

    @Override
    public DocumentPostViewDTO save(DocumentPostViewDTO documentPostViewDTO) {
        log.debug("Request to save DocumentPostView : {}", documentPostViewDTO);
        DocumentPostView documentPostView = documentPostViewMapper.toEntity(documentPostViewDTO);
        documentPostView = documentPostViewRepository.save(documentPostView);
        return documentPostViewMapper.toDto(documentPostView);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<DocumentPostViewDTO> findAll(Pageable pageable) {
        log.debug("Request to get all DocumentPostViews");
        return documentPostViewRepository.findAll(pageable)
            .map(documentPostViewMapper::toDto);
    }

    @Override
    public Page<DocumentPostViewDetail> findAllByPostId(Long postId, Pageable pageable) {
        return documentPostViewRepository.searchUserView(postId, pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<DocumentPostViewDTO> findOne(Long id) {
        log.debug("Request to get DocumentPostView : {}", id);
        return documentPostViewRepository.findById(id)
            .map(documentPostViewMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete DocumentPostView : {}", id);
        documentPostViewRepository.deleteById(id);
    }
}
