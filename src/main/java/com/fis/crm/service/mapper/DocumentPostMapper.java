package com.fis.crm.service.mapper;


import com.fis.crm.domain.*;
import com.fis.crm.service.dto.DocumentPostDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link DocumentPost} and its DTO {@link DocumentPostDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface DocumentPostMapper extends EntityMapper<DocumentPostDTO, DocumentPost> {



    default DocumentPost fromId(Long id) {
        if (id == null) {
            return null;
        }
        DocumentPost documentPost = new DocumentPost();
        documentPost.setId(id);
        return documentPost;
    }
}
