package com.fis.crm.service.mapper;

import com.fis.crm.domain.*;
import com.fis.crm.service.dto.ConfigMenuItemDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link ConfigMenuItem} and its DTO {@link ConfigMenuItemDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ConfigMenuItemMapper extends EntityMapper<ConfigMenuItemDTO, ConfigMenuItem> {}
