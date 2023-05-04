package com.fis.crm.service.dto;

import java.util.List;

/**
 * @author tamdx
 */
public class DocumentPostApprove {
    private List<Long> lstDocPostId;
    private String approveStatus;

    public List<Long> getLstDocPostId() {
        return lstDocPostId;
    }

    public void setLstDocPostId(List<Long> lstDocPostId) {
        this.lstDocPostId = lstDocPostId;
    }

    public String getApproveStatus() {
        return approveStatus;
    }

    public void setApproveStatus(String approveStatus) {
        this.approveStatus = approveStatus;
    }
}
