package com.fis.crm.service;


public abstract class InOutBoundService {

    final EvaluateAssignmentDetailService evaluateAssignmentDetailService;

    protected InOutBoundService(EvaluateAssignmentDetailService evaluateAssignmentDetailService) {
        this.evaluateAssignmentDetailService = evaluateAssignmentDetailService;
    }

    public boolean updateAfterEvaluate(Long objectId, Long evaluateAssignmentDetailId) {
        this.updateStatusCall(objectId);
        this.updateTotalEvaluated(evaluateAssignmentDetailId);
        return true;
    }

    public abstract boolean updateStatusCall(Long objectId);

    public boolean updateTotalEvaluated(Long evaluateAssignmentDetailId) {
        return evaluateAssignmentDetailService.updateTotalEvaluated(evaluateAssignmentDetailId);
    }
}
