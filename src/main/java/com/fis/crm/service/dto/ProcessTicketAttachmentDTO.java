package com.fis.crm.service.dto;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A DTO for the {@link com.fis.crm.domain.TicketRequest} entity.
 */
public class ProcessTicketAttachmentDTO implements Serializable {

    private Long processTicketAttachmentId;

    private Long ticketRequestId;

    private String fileName;

    private String fillNameEncrypt;

    private Instant createDatetime;

    private Long createUser;

    private String status;

    public Long getProcessTicketAttachmentId() {
        return processTicketAttachmentId;
    }

    public void setProcessTicketAttachmentId(Long processTicketAttachmentId) {
        this.processTicketAttachmentId = processTicketAttachmentId;
    }

    public Long getTicketRequestId() {
        return ticketRequestId;
    }

    public void setTicketRequestId(Long ticketRequestId) {
        this.ticketRequestId = ticketRequestId;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFillNameEncrypt() {
        return fillNameEncrypt;
    }

    public void setFillNameEncrypt(String fillNameEncrypt) {
        this.fillNameEncrypt = fillNameEncrypt;
    }

    public Instant getCreateDatetime() {
        return createDatetime;
    }

    public void setCreateDatetime(Instant createDatetime) {
        this.createDatetime = createDatetime;
    }

    public Long getCreateUser() {
        return createUser;
    }

    public void setCreateUser(Long createUser) {
        this.createUser = createUser;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof ProcessTicketAttachmentDTO)) {
      return false;
    }

    ProcessTicketAttachmentDTO processTicketAttachmentDTO = (ProcessTicketAttachmentDTO) o;
    if (this.processTicketAttachmentId == null) {
      return false;
    }
    return Objects.equals(this.processTicketAttachmentId, processTicketAttachmentDTO.processTicketAttachmentId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.processTicketAttachmentId);
  }

  // prettier-ignore
    @Override
    public String toString() {
        return "ProcessTicketAttachmentDTO{" +
            " processTicketAttachmentId=" + getProcessTicketAttachmentId() +
            ", ticketRequestId=" + getTicketRequestId() +
            ", fileName='" + getFileName() + "'" +
            ", fillNameEncrypt='" + getFillNameEncrypt() + "'" +
            ", createDatetime=" + getCreateDatetime() +
            ", createUser=" + getCreateUser() +
            ", status='" + getStatus() + "'" +
            "}";
    }
}
