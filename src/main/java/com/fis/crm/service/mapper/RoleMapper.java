package com.fis.crm.service.mapper;


import com.fis.crm.domain.ActionLog;
import com.fis.crm.domain.Role;
import com.fis.crm.service.dto.RoleDTO;
import org.mapstruct.Mapper;

/**
 * Mapper for the entity {@link ActionLog} and its DTO {@link RoleDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface RoleMapper extends EntityMapper<RoleDTO, Role> {

}
