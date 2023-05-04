package com.fis.crm.service.mapper;

import com.fis.crm.domain.*;
import com.fis.crm.service.dto.ConfigMenuDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link ConfigMenu} and its DTO {@link ConfigMenuDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ConfigMenuMapper extends EntityMapper<ConfigMenuDTO, ConfigMenu> {}
