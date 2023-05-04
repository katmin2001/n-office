package com.fis.crm.service.dto;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A DTO for the {@link com.fis.crm.domain.TicketRequest} entity.
 */
public class ConfirmTicketAttachmentDTO implements Serializable {

    private Long confirmTicketAttachmentId;

    private Long ticketRequestId;

    private Long ticketId;

    private String fileName;

    private String fillNameEncrypt;

    private Instant createDatetime;

    private Long createUser;

    private String status;



    public Long getConfirmTicketAttachmentId() {
        return confirmTicketAttachmentId;
    }

    public void setConfirmTicketAttachmentId(Long confirmTicketAttachmentId) {
        this.confirmTicketAttachmentId = confirmTicketAttachmentId;
    }

    public Long getTicketRequestId() {
        return ticketRequestId;
    }

    public void setTicketRequestId(Long ticketRequestId) {
        this.ticketRequestId = ticketRequestId;
    }

    public Long getTicketId() {
        return ticketId;
    }

    public void setTicketId(Long ticketId) {
        this.ticketId = ticketId;
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
    if (!(o instanceof ConfirmTicketAttachmentDTO)) {
      return false;
    }

    ConfirmTicketAttachmentDTO confirmTicketAttachmentDTO = (ConfirmTicketAttachmentDTO) o;
    if (this.confirmTicketAttachmentId == null) {
      return false;
    }
    return Objects.equals(this.confirmTicketAttachmentId, confirmTicketAttachmentDTO.confirmTicketAttachmentId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.confirmTicketAttachmentId);
  }

  // prettier-ignore
    @Override
    public String toString() {
        return "ConfirmTicketAttachmentDTO{" +
            " confirmTicketAttachmentId=" + getConfirmTicketAttachmentId() +
            ", ticketRequestId=" + getTicketRequestId() +
            ", fileName='" + getFileName() + "'" +
            ", fillNameEncrypt='" + getFillNameEncrypt() + "'" +
            ", createDatetime=" + getCreateDatetime() +
            ", createUser=" + getCreateUser() +
            ", status='" + getStatus() + "'" +
            "}";
    }
}
