package com.fis.crm.service.dto;

import java.util.List;

/**
 * @author tamdx
 */
public class DocumentPostDetail {
    private DocumentPostDTO documentPostDTO;
    private Boolean bookmarks = false;
    private List<DocumentPostAttachmentDTO> lstDocAttach;


    public Boolean getBookmarks() {
        return bookmarks;
    }

    public void setBookmarks(Boolean bookmarks) {
        this.bookmarks = bookmarks;
    }

    public DocumentPostDTO getDocumentPostDTO() {
        return documentPostDTO;
    }

    public void setDocumentPostDTO(DocumentPostDTO documentPostDTO) {
        this.documentPostDTO = documentPostDTO;
    }

    public List<DocumentPostAttachmentDTO> getLstDocAttach() {
        return lstDocAttach;
    }

    public void setLstDocAttach(List<DocumentPostAttachmentDTO> lstDocAttach) {
        this.lstDocAttach = lstDocAttach;
    }
}
