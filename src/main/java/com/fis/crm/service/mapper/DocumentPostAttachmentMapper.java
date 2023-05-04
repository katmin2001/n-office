package com.fis.crm.service.mapper;


import com.fis.crm.domain.*;
import com.fis.crm.service.dto.DocumentPostAttachmentDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link DocumentPostAttachment} and its DTO {@link DocumentPostAttachmentDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface DocumentPostAttachmentMapper extends EntityMapper<DocumentPostAttachmentDTO, DocumentPostAttachment> {



    default DocumentPostAttachment fromId(Long id) {
        if (id == null) {
            return null;
        }
        DocumentPostAttachment documentPostAttachment = new DocumentPostAttachment();
        documentPostAttachment.setId(id);
        return documentPostAttachment;
    }
}
