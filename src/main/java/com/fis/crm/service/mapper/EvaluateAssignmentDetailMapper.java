package com.fis.crm.service.mapper;


import com.fis.crm.domain.*;
import com.fis.crm.service.dto.EvaluateAssignmentDetailDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link EvaluateAssignmentDetail} and its DTO {@link EvaluateAssignmentDetailDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface EvaluateAssignmentDetailMapper extends EntityMapper<EvaluateAssignmentDetailDTO, EvaluateAssignmentDetail> {



    default EvaluateAssignmentDetail fromId(Long id) {
        if (id == null) {
            return null;
        }
        EvaluateAssignmentDetail evaluateAssignmentDetail = new EvaluateAssignmentDetail();
        evaluateAssignmentDetail.setId(id);
        return evaluateAssignmentDetail;
    }
}
