package com.fis.crm.service.impl;

import com.fis.crm.service.DocumentPostBookmarksService;
import com.fis.crm.domain.DocumentPostBookmarks;
import com.fis.crm.repository.DocumentPostBookmarksRepository;
import com.fis.crm.service.dto.DocumentPostBookmarksDTO;
import com.fis.crm.service.mapper.DocumentPostBookmarksMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link DocumentPostBookmarks}.
 */
@Service
@Transactional
public class DocumentPostBookmarksServiceImpl implements DocumentPostBookmarksService {

    private final Logger log = LoggerFactory.getLogger(DocumentPostBookmarksServiceImpl.class);

    private final DocumentPostBookmarksRepository documentPostBookmarksRepository;

    private final DocumentPostBookmarksMapper documentPostBookmarksMapper;

    public DocumentPostBookmarksServiceImpl(DocumentPostBookmarksRepository documentPostBookmarksRepository, DocumentPostBookmarksMapper documentPostBookmarksMapper) {
        this.documentPostBookmarksRepository = documentPostBookmarksRepository;
        this.documentPostBookmarksMapper = documentPostBookmarksMapper;
    }

    @Override
    public DocumentPostBookmarksDTO save(DocumentPostBookmarksDTO documentPostBookmarksDTO) {
        log.debug("Request to save DocumentPostBookmarks : {}", documentPostBookmarksDTO);
        DocumentPostBookmarks documentPostBookmarks = documentPostBookmarksMapper.toEntity(documentPostBookmarksDTO);
        documentPostBookmarks = documentPostBookmarksRepository.save(documentPostBookmarks);
        return documentPostBookmarksMapper.toDto(documentPostBookmarks);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<DocumentPostBookmarksDTO> findAll(Pageable pageable) {
        log.debug("Request to get all DocumentPostBookmarks");
        return documentPostBookmarksRepository.findAll(pageable)
            .map(documentPostBookmarksMapper::toDto);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<DocumentPostBookmarksDTO> findOne(Long id) {
        log.debug("Request to get DocumentPostBookmarks : {}", id);
        return documentPostBookmarksRepository.findById(id)
            .map(documentPostBookmarksMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete DocumentPostBookmarks : {}", id);
        documentPostBookmarksRepository.deleteById(id);
    }

    @Override
    public boolean existsByDocumentPostIdAndAndUserId(Long docPostId, Long userId) {
        return documentPostBookmarksRepository.existsByDocumentPostIdAndAndUserId(docPostId, userId);
    }
}
