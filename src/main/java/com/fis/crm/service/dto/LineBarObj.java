package com.fis.crm.service.dto;

import com.fis.crm.commons.DataUtil;

public class LineBarObj {
    private String date;
    private Long statusNew;
    private Long statusProcessing;
    private Long statusProcessed;
    private Long statusConfirm;
    private Long statusClose;
    private Long evaluated;
    private Long nonEvaluated;
    private Long sendError;
    private Long sendSuccess;
    private Long total;

    public static LineBarObj getSuVuHeThong(Object[] obj) {
        LineBarObj lineBarObj = new LineBarObj();
        lineBarObj.date = DataUtil.safeToString(obj[0]);
        lineBarObj.statusNew = DataUtil.safeToLong(obj[1]);
        lineBarObj.statusProcessing = DataUtil.safeToLong(obj[2]);
        lineBarObj.statusProcessed = DataUtil.safeToLong(obj[3]);
        lineBarObj.statusConfirm = DataUtil.safeToLong(obj[4]);
        lineBarObj.statusClose = DataUtil.safeToLong(obj[5]);
        return lineBarObj;
    }

    public static LineBarObj getReceivedAssignedDept(Object[] obj) {
        LineBarObj lineBarObj = new LineBarObj();
        lineBarObj.date = DataUtil.safeToString(obj[1]);
        lineBarObj.statusNew = DataUtil.safeToLong(obj[2]);
        lineBarObj.statusProcessed = DataUtil.safeToLong(obj[3]);
        return lineBarObj;
    }

    public static LineBarObj callOutInEvaluateSummary(Object[] obj) {
        LineBarObj lineBarObj = new LineBarObj()
            .date(DataUtil.safeToString(obj[0]))
            .evaluated(DataUtil.safeToLong(obj[1]))
            .nonEvaluated(DataUtil.safeToLong(obj[2]));
        return lineBarObj;
    }
    public static LineBarObj emailSmsMarketingSummary(Object[] obj) {
        LineBarObj lineBarObj = new LineBarObj()
            .date(DataUtil.safeToString(obj[0]))
            .sendSuccess(DataUtil.safeToLong(obj[1]))
            .sendError(DataUtil.safeToLong(obj[2]))
            .total(DataUtil.safeToLong(obj[3]));
        return lineBarObj;
    }

    public LineBarObj date(String date) {
        this.date = date;
        return this;
    }

    public LineBarObj evaluated(Long evaluated) {
        this.evaluated = evaluated;
        return this;
    }

    public LineBarObj nonEvaluated(Long nonEvaluated) {
        this.nonEvaluated = nonEvaluated;
        return this;
    }

    public LineBarObj sendSuccess(Long sendSuccess) {
        this.sendSuccess = sendSuccess;
        return this;
    }

    public LineBarObj sendError(Long sendError) {
        this.sendError = sendError;
        return this;
    }

    public LineBarObj total(Long total) {
        this.total = total;
        return this;
    }

    public Long getEvaluated() {
        return evaluated;
    }

    public void setEvaluated(Long evaluated) {
        this.evaluated = evaluated;
    }

    public Long getNonEvaluated() {
        return nonEvaluated;
    }

    public void setNonEvaluated(Long nonEvaluated) {
        this.nonEvaluated = nonEvaluated;
    }

    public Long getSendError() {
        return sendError;
    }

    public void setSendError(Long sendError) {
        this.sendError = sendError;
    }

    public Long getSendSuccess() {
        return sendSuccess;
    }

    public void setSendSuccess(Long sendSuccess) {
        this.sendSuccess = sendSuccess;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Long getStatusNew() {
        return statusNew;
    }

    public void setStatusNew(Long statusNew) {
        this.statusNew = statusNew;
    }

    public Long getStatusProcessing() {
        return statusProcessing;
    }

    public void setStatusProcessing(Long statusProcessing) {
        this.statusProcessing = statusProcessing;
    }

    public Long getStatusProcessed() {
        return statusProcessed;
    }

    public void setStatusProcessed(Long statusProcessed) {
        this.statusProcessed = statusProcessed;
    }

    public Long getStatusConfirm() {
        return statusConfirm;
    }

    public void setStatusConfirm(Long statusConfirm) {
        this.statusConfirm = statusConfirm;
    }

    public Long getStatusClose() {
        return statusClose;
    }

    public void setStatusClose(Long statusClose) {
        this.statusClose = statusClose;
    }


}
