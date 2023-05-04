package com.fis.crm.service.mapper;


import com.fis.crm.domain.EvaluateResult;
import com.fis.crm.domain.EvaluateResultHistory;
import com.fis.crm.service.dto.EvaluateResultDTO;
import org.mapstruct.Mapper;

/**
 * Mapper for the entity {@link EvaluateResult} and its DTO {@link EvaluateResultDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface EvaluateResultHistoryMapper extends EntityMapper<EvaluateResult, EvaluateResultHistory> {

    default EvaluateResult fromId(Long id) {
        if (id == null) {
            return null;
        }
        EvaluateResult evaluateResult = new EvaluateResult();
        evaluateResult.setId(id);
        return evaluateResult;
    }
}
