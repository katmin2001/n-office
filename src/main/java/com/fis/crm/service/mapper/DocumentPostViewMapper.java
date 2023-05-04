package com.fis.crm.service.mapper;


import com.fis.crm.domain.*;
import com.fis.crm.service.dto.DocumentPostViewDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link DocumentPostView} and its DTO {@link DocumentPostViewDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface DocumentPostViewMapper extends EntityMapper<DocumentPostViewDTO, DocumentPostView> {



    default DocumentPostView fromId(Long id) {
        if (id == null) {
            return null;
        }
        DocumentPostView documentPostView = new DocumentPostView();
        documentPostView.setId(id);
        return documentPostView;
    }
}
