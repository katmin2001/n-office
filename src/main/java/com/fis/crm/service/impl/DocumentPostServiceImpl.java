package com.fis.crm.service.impl;

import com.fis.crm.commons.DataUtil;
import com.fis.crm.commons.FileUtils;
import com.fis.crm.commons.Translator;
import com.fis.crm.config.Constants;
import com.fis.crm.domain.*;
import com.fis.crm.repository.DocumentPostAttachmentRepository;
import com.fis.crm.repository.DocumentPostBookmarksRepository;
import com.fis.crm.repository.DocumentPostViewRepository;
import com.fis.crm.service.ActionLogService;
import com.fis.crm.service.DocumentPostService;
import com.fis.crm.repository.DocumentPostRepository;
import com.fis.crm.service.UserService;
import com.fis.crm.service.dto.*;
import com.fis.crm.service.mapper.DocumentPostAttachmentMapper;
import com.fis.crm.service.mapper.DocumentPostMapper;
import com.fis.crm.service.mapper.DocumentPostViewMapper;
import com.fis.crm.web.rest.errors.BadRequestAlertException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static org.hibernate.id.IdentifierGenerator.ENTITY_NAME;

/**
 * Service Implementation for managing {@link DocumentPost}.
 */
@Service
@Transactional
public class DocumentPostServiceImpl implements DocumentPostService {

    private final Logger log = LoggerFactory.getLogger(DocumentPostServiceImpl.class);

    private final DocumentPostRepository documentPostRepository;
    private final DocumentPostAttachmentRepository documentPostAttachmentRepository;
    private final DocumentPostViewRepository documentPostViewRepository;
    private final DocumentPostBookmarksRepository documentPostBookmarksRepository;

    private final DocumentPostMapper documentPostMapper;
    private final DocumentPostAttachmentMapper documentPostAttachmentMapper;
    private final DocumentPostViewMapper documentPostViewMapper;

    private final UserService userService;
    private final ActionLogService actionLogService;

    @Value("${file-manager.attach-file}")
    private String attachPath;
    @Value("${file-manager.file-attach-ext}")
    private String attachExtension;

    public DocumentPostServiceImpl(DocumentPostRepository documentPostRepository, DocumentPostAttachmentRepository documentPostAttachmentRepository, DocumentPostViewRepository documentPostViewRepository, DocumentPostBookmarksRepository documentPostBookmarksRepository, DocumentPostMapper documentPostMapper, DocumentPostAttachmentMapper documentPostAttachmentMapper, DocumentPostViewMapper documentPostViewMapper, UserService userService, ActionLogService actionLogService) {
        this.documentPostRepository = documentPostRepository;
        this.documentPostAttachmentRepository = documentPostAttachmentRepository;
        this.documentPostViewRepository = documentPostViewRepository;
        this.documentPostBookmarksRepository = documentPostBookmarksRepository;
        this.documentPostMapper = documentPostMapper;
        this.documentPostAttachmentMapper = documentPostAttachmentMapper;
        this.documentPostViewMapper = documentPostViewMapper;
        this.userService = userService;
        this.actionLogService = actionLogService;
    }

    /**
     * Save bai viet
     * @param documentPostDTO the entity to save.
     * @param documentAttachs
     * @return
     * @throws Exception
     */
    @Override
    public DocumentPostDTO save(DocumentPostDTO documentPostDTO, List<MultipartFile> documentAttachs) throws Exception{
        log.debug("Request to save DocumentPost : {}", documentPostDTO);
        List<Long> lstDelAttachs = null;
        User user = userService.getUserWithAuthorities().orElseThrow(() -> new BadRequestAlertException(Translator.toLocale("configSchedule.userNotExis"), ENTITY_NAME, "configSchedule.userNotExis"));
        Instant now = Instant.now();
        Pattern pattern = Pattern.compile("<p>[&nbsp;\\s*]*<\\/p>");
        Matcher matcher = pattern.matcher(documentPostDTO.getContent());
        if(matcher.find()) {
            throw new BadRequestAlertException(Translator.toLocale("documentPost.contentNull"), "documentPost", "documentPost.contentNull");
        }
        DocumentPost documentPost;
        List<DocumentPost> lstCheck;
        if(documentPostDTO.getId() == null) {
            lstCheck = documentPostRepository.queryCheckTitleForInsert(documentPostDTO.getDocumentId(), documentPostDTO.getTitle());
            documentPostDTO.setCreateUser(user.getId());
            documentPostDTO.setCreateDatetime(now);
            documentPostDTO.setStatus(Constants.STATUS_ACTIVE);
            documentPostDTO.setApproveStatus(Constants.STATUS_APPROVE_1);
            documentPostDTO.setViewTotal(0L);
            documentPost = documentPostMapper.toEntity(documentPostDTO);
        } else {
            lstDelAttachs = documentPostDTO.getLstDelAttachs();
            documentPost = documentPostRepository.findByIdAndStatus(documentPostDTO.getId(), Constants.STATUS_ACTIVE).orElseThrow(() -> new BadRequestAlertException(Translator.toLocale("documentPost.idNotExist"), "documentPost", "documentPost.idNotExist"));
            lstCheck = documentPostRepository.queryCheckTitleForUpdate(documentPostDTO.getId(), documentPostDTO.getDocumentId(), documentPostDTO.getTitle());
            documentPost.setTitle(documentPostDTO.getTitle());
            documentPost.setContent(documentPostDTO.getContent());
            documentPost.setTags(documentPostDTO.getTags());
            documentPost.setDocumentId(documentPostDTO.getDocumentId());
        }
        if(!DataUtil.isNullOrEmpty(lstCheck)) {
            throw new BadRequestAlertException(Translator.toLocale("documentPost.titleExisted"), "documentPost", "documentPost.titleExisted");
        }

        documentPost = documentPostRepository.save(documentPost);

        List<DocumentPostAttachment> lstAttach = new ArrayList<>();
        if(documentAttachs != null) {
            for(MultipartFile file : documentAttachs) {
                if(FileUtils.validateAttachFile(file, attachExtension, 10.0) > 0) {
                    throw new BadRequestAlertException(Translator.toLocale("documentPost.fileInvalid"), "documentPost", "documentPost.fileInvalid");
                }
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("ddMMyyyyHHmmss");
                String encryptFileName = simpleDateFormat.format(new Date()) + "_" + file.getOriginalFilename();
                FileUtils.copyAttachFile(file, attachPath, encryptFileName);
                DocumentPostAttachment docAttach = new DocumentPostAttachment();
                docAttach.setFileNameEncrypt(encryptFileName);
                docAttach.setFileName(file.getOriginalFilename());
                docAttach.setCapacity(Double.valueOf(file.getSize()));
                docAttach.setDocumentPostId(documentPost.getId());
                docAttach.setDownloadTotal(0L);
                docAttach.setCreateDatetime(now);
                docAttach.setCreateUser(user.getId());
                docAttach.setStatus(Constants.STATUS_ACTIVE);
                lstAttach.add(docAttach);
            }
            documentPostAttachmentRepository.saveAll(lstAttach);
        }
        if(!DataUtil.isNullOrEmpty(lstDelAttachs)) {
            //Truong hop cap nhat thi xoa file dinh kem
            documentPostAttachmentRepository.findAllByIdIn(lstDelAttachs).stream().forEach(attach -> {
                attach.setStatus(Constants.STATUS_INACTIVE.toString());
                documentPostAttachmentRepository.save(attach);
            });
        }
        Optional<User> userLog = userService.getUserWithAuthorities();
        if (documentPostDTO.getId() != null){
            actionLogService.saveActionLog(new ActionLogDTO(userLog.get().getId(), Constants.ACTION_LOG_TYPE.UPDATE + "",
                null, String.format("Cập nhật: Quản lý bài viết"),
                new Date(), Constants.MENU_ID.KMS, Constants.MENU_ITEM_ID.kms_management, "CONFIG_MENU_ITEM"));
        } else {
            actionLogService.saveActionLog(new ActionLogDTO(userLog.get().getId(), Constants.ACTION_LOG_TYPE.INSERT + "",
                null, String.format("Thêm mới: Quản lý bài viết"),
                new Date(), Constants.MENU_ID.KMS, Constants.MENU_ITEM_ID.kms_management, "CONFIG_MENU_ITEM"));
        }

        return documentPostMapper.toDto(documentPost);
    }

    /**
     * Tim kiem bai viet
     * @param title
     * @param pageable the pagination information.
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public Page<DocumentPostDTO> findAll(String title, Pageable pageable) {
        log.debug("Request to get all DocumentPosts");
        return documentPostRepository.queryDocumentPost(DataUtil.makeLikeQuery(title), pageable)
            .map(documentPostMapper::toDto);
    }

    /**
     * Danh sach cac bai viet chua duoc phe duyet
     * @param pageable
     * @return
     */
    @Override
    public Page<DocumentPostNotApprove> findAllNotApprove(Pageable pageable) {
        return documentPostRepository.searchDocumentNotApprove(Constants.STATUS_ACTIVE, Constants.STATUS_APPROVE_1, pageable);
    }

    /**
     * Lay thong tin 1 bai viet
     * @param id the id of the entity.
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<DocumentPostDTO> findOne(Long id) {
        log.debug("Request to get DocumentPost : {}", id);
        return documentPostRepository.findById(id)
            .map(documentPostMapper::toDto);
    }

    /**
     * Xoa bai viet
     * @param id the id of the entity.
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Long id) {
        log.debug("Request to delete DocumentPost : {}", id);
        DocumentPost doc = documentPostRepository.findByIdAndStatus(id, Constants.STATUS_ACTIVE).orElseThrow(() -> new BadRequestAlertException(Translator.toLocale("documentPost.idNotExist"), "documentPost", "documentPost.idNotExist"));
        doc.setStatus(Constants.STATUS_INACTIVE.toString());
        documentPostRepository.save(doc);
        documentPostAttachmentRepository.findAllByDocumentPostIdAndStatusOrderByCreateDatetimeDesc(id, Constants.STATUS_ACTIVE).stream().forEach(attach -> {
            attach.setStatus(Constants.STATUS_INACTIVE.toString());
            documentPostAttachmentRepository.save(attach);
        });
        Optional<User> userLog = userService.getUserWithAuthorities();
        actionLogService.saveActionLog(new ActionLogDTO(userLog.get().getId(), Constants.ACTION_LOG_TYPE.DELETE + "",
            null, String.format("Xóa: Quản lý bài viết"),
            new Date(), Constants.MENU_ID.KMS, Constants.MENU_ITEM_ID.kms_management, "CONFIG_MENU_ITEM"));
    }

    /**
     * Lay danh sach bai viet trong 1 thu muc
     * @param documentId
     * @return
     */
    @Override
    public List<DocumentPostDTO> findByDocumentId(Long documentId) {
        return documentPostRepository.findByDocumentId(documentId).stream()
            .map(documentPostMapper::toDto).collect(Collectors.toList());
    }

    /**
     * Lay thong tin chi tiet cua 1 bai viet
     * @param id
     * @param isView
     * @return
     */
    @Override
    public Optional<DocumentPostDetail> getDocumentPostById(Long id, boolean isView) {
        DocumentPostDetail result = new DocumentPostDetail();

        User user = userService.getUserWithAuthorities().orElseThrow(() -> new BadRequestAlertException(Translator.toLocale("configSchedule.userNotExis"), ENTITY_NAME, "configSchedule.userNotExis"));

        //Lay thong tin chi tiet bai viet

        DocumentPostDTO doc = documentPostRepository.findByIdAndStatus(id, Constants.STATUS_ACTIVE).map(documentPostMapper::toDto).orElseThrow(() -> new BadRequestAlertException(Translator.toLocale("documentPost.idNotExist"), "documentPost", "documentPost.idNotExist"));
        result.setDocumentPostDTO(doc);

        //Tang so luong luot view len 1
        if(isView) {
            if(Constants.STATUS_APPROVE_2.equalsIgnoreCase(doc.getApproveStatus())) {
                documentPostRepository.updateViewTotal(id);

                //Luu lai thong tin vao document_post_view
                DocumentPostViewDTO docView = new DocumentPostViewDTO();
                docView.setDocumentPostId(id);
                docView.setUserId(user.getId());
                docView.setCreateDatetime(Instant.now());
                documentPostViewRepository.save(documentPostViewMapper.toEntity(docView));
            }
            result.setBookmarks(documentPostBookmarksRepository.existsByDocumentPostIdAndAndUserId(id, user.getId()));
        }

        List<DocumentPostAttachmentDTO> lstAttach = documentPostAttachmentRepository.findAllByPostAndStatus(id, Constants.STATUS_ACTIVE);
        result.setLstDocAttach(lstAttach);

        return Optional.of(result);
    }

    /**
     * Chuyen thu muc cua bai viet
     * @param srcDocId
     * @param desDocId
     */
    @Override
    public void moveDocument(Long srcDocId, Long desDocId) {
        documentPostRepository.moveDocument(srcDocId, desDocId);
    }

    /**
     * Phe duyet bai viet
     * @param documentPostApprove
     */
    @Override
    public void approveDocument(DocumentPostApprove documentPostApprove) {
        if(!Constants.STATUS_APPROVE_0.equalsIgnoreCase(documentPostApprove.getApproveStatus()) &&
        !Constants.STATUS_APPROVE_2.equalsIgnoreCase(documentPostApprove.getApproveStatus())) {
            throw new BadRequestAlertException(Translator.toLocale("documentPost.approveStatus.invalid"), "documentPost", "documentPost.approveStatus.invalid");
        }
        User user = userService.getUserWithAuthorities().orElseThrow(() -> new BadRequestAlertException(Translator.toLocale("configSchedule.userNotExis"), ENTITY_NAME, "configSchedule.userNotExis"));
        Instant now = Instant.now();
        List<DocumentPost> lstDocPost = documentPostRepository.findAllByIdIn(documentPostApprove.getLstDocPostId()).stream()
            .peek(e-> {
                e.setApproveStatus(documentPostApprove.getApproveStatus());
                if(Constants.STATUS_APPROVE_0.equalsIgnoreCase(documentPostApprove.getApproveStatus())) {
                    e.setStatus(Constants.STATUS_INACTIVE.toString());
                }
                e.setApproveDatetime(now);
                e.setApproveUser(user.getId());
            }).collect(Collectors.toList());
        documentPostRepository.saveAll(lstDocPost);
    }

    /**
     * Bookmark bai viet
     * @param id
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void bookmarkDocument(Long id) {
        User user = userService.getUserWithAuthorities().orElseThrow(() -> new BadRequestAlertException(Translator.toLocale("configSchedule.userNotExis"), ENTITY_NAME, "configSchedule.userNotExis"));
        if(!documentPostBookmarksRepository.existsByDocumentPostIdAndAndUserId(id, user.getId())) {
            DocumentPostBookmarks bookmarks = new DocumentPostBookmarks();
            bookmarks.setDocumentPostId(id);
            bookmarks.setUserId(user.getId());
            bookmarks.setCreateDatetime(Instant.now());
            documentPostBookmarksRepository.save(bookmarks);
        }
    }

    /**
     * remove bookmark
     * @param id
     */
    @Override
    public void removeBookmark(Long id) {
        User user = userService.getUserWithAuthorities().orElseThrow(() -> new BadRequestAlertException(Translator.toLocale("configSchedule.userNotExis"), ENTITY_NAME, "configSchedule.userNotExis"));

        DocumentPostBookmarks bookmarks = documentPostBookmarksRepository.findByDocumentPostId(id)
            .orElseThrow(() -> new BadRequestAlertException(Translator.toLocale("documentPostBookmark.notExisted"), ENTITY_NAME, "documentPostBookmark.notExisted"));
        if(!user.getId().equals(bookmarks.getUserId())) {
            throw new BadRequestAlertException(Translator.toLocale("documentPostBookmark.dontPermission"), "documentPost", "documentPostBookmark.dontPermission");
        }
        documentPostBookmarksRepository.delete(bookmarks);
    }

    /**
     * Lay danh sach bai viet top view
     * @param pageable
     * @return
     */
    @Override
    public Page<DocumentPostDTO> getTopView(Pageable pageable) {
        return documentPostRepository.findAllByStatusAndApproveStatus(Constants.STATUS_ACTIVE, Constants.STATUS_APPROVE_2, pageable).map(documentPostMapper::toDto);
    }

    /**
     * Lay danh sach bai viet duoc bookmark
     * @param pageable
     * @return
     */
    @Override
    public Page<DocumentPostDTO> getBookmark(Pageable pageable) {
        User user = userService.getUserWithAuthorities().orElseThrow(() -> new BadRequestAlertException(Translator.toLocale("configSchedule.userNotExis"), ENTITY_NAME, "configSchedule.userNotExis"));
        return documentPostRepository.findAllBookmark(user.getId(), pageable).map(documentPostMapper::toDto);
    }

    /**
     * Lay danh sach bai viet moi nhat
     * @param pageable
     * @return
     */
    @Override
    public Page<DocumentPostDTO> getNewPost(Pageable pageable) {
        return documentPostRepository.findAllByStatusAndApproveStatus(Constants.STATUS_ACTIVE, Constants.STATUS_APPROVE_2, pageable).map(documentPostMapper::toDto);
    }

    @Override
    public Resource getFile(Long id) {
        Optional<DocumentPostAttachment> documentPostAttachmentOptional = documentPostAttachmentRepository.findById(id);
        if (documentPostAttachmentOptional.isPresent()) {
            try {
                File file = new File(attachPath + File.separator + documentPostAttachmentOptional.get().getFileNameEncrypt());
                if (!file.exists()) {
                    throw new BadRequestAlertException("File không tồn tại", ENTITY_NAME, "");
                }
                InputStreamResource resource = new InputStreamResource(new FileInputStream(file));
                return resource;
            } catch (FileNotFoundException e) {
                throw new BadRequestAlertException("File không tồn tại", ENTITY_NAME, "");
            }
        } else {
            throw new BadRequestAlertException("File không tồn tại", ENTITY_NAME, "");
        }
    }
}
