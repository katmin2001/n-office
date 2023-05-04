package com.fis.crm.service.dto;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public class DocumentPostForm {
    private DocumentPostDTO documentPostDTO;
    private List<MultipartFile> documentAttachs;

    public DocumentPostDTO getDocumentPostDTO() {
        return documentPostDTO;
    }

    public void setDocumentPostDTO(DocumentPostDTO documentPostDTO) {
        this.documentPostDTO = documentPostDTO;
    }

    public List<MultipartFile> getDocumentAttachs() {
        return documentAttachs;
    }

    public void setDocumentAttachs(List<MultipartFile> documentAttachs) {
        this.documentAttachs = documentAttachs;
    }
}
