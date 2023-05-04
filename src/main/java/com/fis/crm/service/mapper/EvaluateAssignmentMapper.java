package com.fis.crm.service.mapper;


import com.fis.crm.domain.*;
import com.fis.crm.service.dto.EvaluateAssignmentDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link EvaluateAssignment} and its DTO {@link EvaluateAssignmentDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface EvaluateAssignmentMapper extends EntityMapper<EvaluateAssignmentDTO, EvaluateAssignment> {



    default EvaluateAssignment fromId(Long id) {
        if (id == null) {
            return null;
        }
        EvaluateAssignment evaluateAssignment = new EvaluateAssignment();
        evaluateAssignment.setId(id);
        return evaluateAssignment;
    }
}
