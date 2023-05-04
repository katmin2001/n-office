package com.fis.crm.service.impl;

import com.fis.crm.commons.DataUtil;
import com.fis.crm.commons.Translator;
import com.fis.crm.domain.Document;
import com.fis.crm.domain.User;
import com.fis.crm.repository.DocumentRepository;
import com.fis.crm.service.DocumentPostService;
import com.fis.crm.service.DocumentService;
import com.fis.crm.service.UserService;
import com.fis.crm.service.dto.DocumentDTO;
import com.fis.crm.service.dto.DocumentPostDTO;
import com.fis.crm.service.mapper.DocumentMapper;
import com.fis.crm.web.rest.errors.BadRequestAlertException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link Document}.
 */
@Service
@Transactional
public class DocumentServiceImpl implements DocumentService {

    private final Logger log = LoggerFactory.getLogger(DocumentServiceImpl.class);

    private final DocumentRepository documentRepository;

    private final DocumentMapper documentMapper;
    private final UserService userService;
    private final DocumentPostService documentPostService;

    public DocumentServiceImpl(DocumentRepository documentRepository, DocumentMapper documentMapper,
                               UserService userService, DocumentPostService documentPostService) {
        this.documentRepository = documentRepository;
        this.documentMapper = documentMapper;
        this.userService = userService;
        this.documentPostService = documentPostService;
    }

    @Override
    public DocumentDTO save(DocumentDTO documentDTO) {
        log.debug("Request to save Document : {}", documentDTO);
        Optional<User> user = userService.getUserWithAuthorities();
        if(!user.isPresent()) {
            throw new BadRequestAlertException("User not found","notification", "error.no.user");
        }
        //validate document
        if(documentDTO.getName() == null || documentDTO.getName().isEmpty()) {
            throw new BadRequestAlertException(Translator.toLocale("document.name.required"),"notification", "error.required");
        }
        if(documentDTO.getName().length() > 200) {
            throw new BadRequestAlertException(Translator.toLocale("document.name.maxlength","200"),"notification", "error.maxlength");
        }
        documentDTO.setCreateUser(user.get().getId());
        documentDTO.setCreateDatetime(Instant.now());
        Document document = documentMapper.toEntity(documentDTO);
        document = documentRepository.save(document);
        return documentMapper.toDto(document);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<DocumentDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Documents");
        return documentRepository.findAll(pageable)
            .map(documentMapper::toDto);
    }

    @Override
    public List<DocumentDTO> findAll() {
        return documentRepository.findAllDocument().stream()
            .map(DocumentDTO::new).collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<DocumentDTO> findOne(Long id) {
        log.debug("Request to get Document : {}", id);
        return documentRepository.findById(id)
            .map(documentMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Document : {}", id);
        //validate
        List<Document> documentList = documentRepository.findByParentId(id);
        if(!documentList.isEmpty()) {
            throw new BadRequestAlertException(Translator.toLocale("document.delete.hasChild"),"notification", "error.hasChild");
        }
        List<DocumentPostDTO> listPost = documentPostService.findByDocumentId(id);
        if(!listPost.isEmpty()) {
            throw new BadRequestAlertException(Translator.toLocale("document.delete.hasPost"),"notification", "error.hasPost");
        }

        documentRepository.deleteById(id);
    }


    @Override
    public List<DocumentDTO> findByParentId(Long parentId) {
        return documentRepository.findByParentId(parentId).stream()
            .map(documentMapper::toDto)
            .collect(Collectors.toList());
    }

    @Override
    public List<DocumentDTO> findAllNotInParent(Long id) {
        return documentRepository.findAllNotInParent(id).stream().
            map(documentMapper::toDto).collect(Collectors.toList());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void moveDocument(Long id, Long newParentId) {
        List<Document> lstDoc = documentRepository.getAllByByIdAndParent(newParentId, id);
        if(!DataUtil.isNullOrEmpty(lstDoc)) {
            throw new BadRequestAlertException(Translator.toLocale("document.move.canNotMoveToChild"),"document", "error.document.move.canNotMoveToChild");
        }
        documentRepository.findById(id).ifPresent(e-> {
            e.setParentId(newParentId);
            documentRepository.save(e);
        });
    }
}
