package com.fis.crm.service.mapper;


import com.fis.crm.domain.*;
import com.fis.crm.service.dto.BussinessTypeDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link BussinessType} and its DTO {@link BussinessTypeDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface BussinessTypeMapper extends EntityMapper<BussinessTypeDTO, BussinessType> {



    default BussinessType fromId(Long id) {
        if (id == null) {
            return null;
        }
        BussinessType bussinessType = new BussinessType();
        bussinessType.setId(id);
        return bussinessType;
    }
}
