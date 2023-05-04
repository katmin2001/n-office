package com.fis.crm.service.mapper;


import com.fis.crm.domain.*;
import com.fis.crm.service.dto.DocumentPostBookmarksDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link DocumentPostBookmarks} and its DTO {@link DocumentPostBookmarksDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface DocumentPostBookmarksMapper extends EntityMapper<DocumentPostBookmarksDTO, DocumentPostBookmarks> {



    default DocumentPostBookmarks fromId(Long id) {
        if (id == null) {
            return null;
        }
        DocumentPostBookmarks documentPostBookmarks = new DocumentPostBookmarks();
        documentPostBookmarks.setId(id);
        return documentPostBookmarks;
    }
}
