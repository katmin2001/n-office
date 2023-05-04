package com.fis.crm.service.mapper;

import com.fis.crm.domain.Ticket;
import com.fis.crm.service.dto.TicketDTO;
import com.fis.crm.domain.Department;
import com.fis.crm.service.dto.DepartmentDTO;
import org.mapstruct.Mapper;

/**
 * Mapper for the entity {@link Department} and its DTO {@link DepartmentDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface DepartmentMapper extends EntityMapper<DepartmentDTO, Department> {}
