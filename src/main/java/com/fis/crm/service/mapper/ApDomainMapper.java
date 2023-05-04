package com.fis.crm.service.mapper;


import com.fis.crm.domain.*;
import com.fis.crm.service.dto.ApDomainDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link ApDomain} and its DTO {@link ApDomainDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ApDomainMapper extends EntityMapper<ApDomainDTO, ApDomain> {



    default ApDomain fromId(Long id) {
        if (id == null) {
            return null;
        }
        ApDomain apDomain = new ApDomain();
        apDomain.setId(id);
        return apDomain;
    }
}
