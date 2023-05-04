package com.fis.crm.service.mapper;


import com.fis.crm.domain.*;
import com.fis.crm.service.dto.GroupUserDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link GroupUser} and its DTO {@link GroupUserDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface GroupUserMapper extends EntityMapper<GroupUserDTO, GroupUser> {



    default GroupUser fromId(Long id) {
        if (id == null) {
            return null;
        }
        GroupUser groupUser = new GroupUser();
        groupUser.setId(id);
        return groupUser;
    }
}
