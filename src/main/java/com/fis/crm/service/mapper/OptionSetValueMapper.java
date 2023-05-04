package com.fis.crm.service.mapper;

import com.fis.crm.domain.OptionSetValue;
import com.fis.crm.service.dto.OptionSetValueDTO;
import org.mapstruct.Mapper;

/**
 * Mapper for the entity {@link OptionSetValue} and its DTO {@link OptionSetValueDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface OptionSetValueMapper extends EntityMapper<OptionSetValueDTO, OptionSetValue> {

    default OptionSetValue fromId(Long id) {
        if (id == null) {
            return null;
        }
        OptionSetValue optionSetValue = new OptionSetValue();
        optionSetValue.setId(id);
        return optionSetValue;
    }
}
