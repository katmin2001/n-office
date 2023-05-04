package com.fis.crm.service.dto;

import com.fis.crm.domain.TicketRequest;

import java.util.Date;
import java.util.List;

public class ServiceResult {

    private Status status = Status.SUCCESS;
    private String message;
    private Object data;

    List<TicketRequestAttachmentDTO> listRequestAttachmentDTOList;
    public enum Status {
        SUCCESS, FAILED;
    }

    private Long ticketId;
    private List<Long> listTicketRequestIds;

    private Date processDate;

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public List<TicketRequestAttachmentDTO> getListRequestAttachmentDTOList() {
        return listRequestAttachmentDTOList;
    }

    public void setListRequestAttachmentDTOList(List<TicketRequestAttachmentDTO> listRequestAttachmentDTOList) {
        this.listRequestAttachmentDTOList = listRequestAttachmentDTOList;
    }

    public Long getTicketId() {
        return ticketId;
    }

    public void setTicketId(Long ticketId) {
        this.ticketId = ticketId;
    }

    public List<Long> getListTicketRequestIds() {
        return listTicketRequestIds;
    }

    public void setListTicketRequestIds(List<Long> listTicketRequestIds) {
        this.listTicketRequestIds = listTicketRequestIds;
    }

    public Date getProcessDate() {
        return processDate;
    }

    public void setProcessDate(Date processDate) {
        this.processDate = processDate;
    }
}
