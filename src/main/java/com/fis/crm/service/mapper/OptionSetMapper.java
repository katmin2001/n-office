package com.fis.crm.service.mapper;

import com.fis.crm.domain.OptionSet;
import com.fis.crm.service.dto.OptionSetDTO;
import org.mapstruct.Mapper;

/**
 * Mapper for the entity {@link OptionSet} and its DTO {@link OptionSetDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface OptionSetMapper extends EntityMapper<OptionSetDTO, OptionSet> {

    default OptionSet fromOptionSetId(Long id) {
        if (id == null) {
            return null;
        }
        OptionSet optionSet = new OptionSet();
        optionSet.setOptionSetId(id);
        return optionSet;
    }
}
