package com.fis.crm.crm_util;

import com.fis.crm.crm_entity.CrmCustomer;
import com.fis.crm.crm_entity.CrmProject;
import com.fis.crm.crm_entity.DTO.CrmCustomerDTO;
import com.fis.crm.crm_entity.DTO.CrmProjectDTO;

public class CrmProjectMapper {
    private final UserDtoMapper userDtoMapper = new UserDtoMapper();

    public CrmProjectDTO toDTO(CrmProject project) {
        CrmProjectDTO dto = new CrmProjectDTO();
        dto.setId(project.getId());
        dto.setName(project.getName());
        dto.setCode(project.getCode());
        dto.setCustomer(project.getCustomer());
        dto.setManager(userDtoMapper.userDtoMapper(project.getManager()));
        dto.setPrivacy(project.getPrivacy());
        dto.setStatus(project.getStatus());
        dto.setDescription(project.getDescription());
        dto.setStartDate(project.getStartDate());
        dto.setEndDate(project.getEndDate());
        dto.setFinishDate(project.getFinishDate());
        return dto;
    }

}
