package com.fis.crm.service.dto;

import java.io.Serializable;
import java.time.Instant;
import java.util.List;
import java.util.Objects;

/**
 * A DTO for the {@link com.fis.crm.domain.TicketRequest} entity.
 */
public class ConfirmTicketDTO implements Serializable {

    private Long confirmTicketId;

    private Long ticketId;

    private String content;

    private Long departmentId;

    private Long createUser;

    private Instant createDatetime;

    private String status;

    private String satisfied;

    private String userName;

    private String departmentName;

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    List<ConfirmTicketAttachmentDTO> listConfirmTicketAttachmentDTOs;

    public Long getConfirmTicketId() {
        return confirmTicketId;
    }

    public void setConfirmTicketId(Long confirmTicketId) {
        this.confirmTicketId = confirmTicketId;
    }

    public Long getTicketId() {
        return ticketId;
    }

    public void setTicketId(Long ticketId) {
        this.ticketId = ticketId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Long getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Long departmentId) {
        this.departmentId = departmentId;
    }

    public Long getCreateUser() {
        return createUser;
    }

    public void setCreateUser(Long createUser) {
        this.createUser = createUser;
    }

    public Instant getCreateDatetime() {
        return createDatetime;
    }

    public void setCreateDatetime(Instant createDatetime) {
        this.createDatetime = createDatetime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<ConfirmTicketAttachmentDTO> getListConfirmTicketAttachmentDTOs() {
        return listConfirmTicketAttachmentDTOs;
    }

    public void setListConfirmTicketAttachmentDTOs(List<ConfirmTicketAttachmentDTO> listConfirmTicketAttachmentDTOs) {
        this.listConfirmTicketAttachmentDTOs = listConfirmTicketAttachmentDTOs;
    }

    public String getSatisfied() {
        return satisfied;
    }

    public void setSatisfied(String satisfied) {
        this.satisfied = satisfied;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof ConfirmTicketDTO)) {
      return false;
    }

    ConfirmTicketDTO ticketRequestDTO = (ConfirmTicketDTO) o;
    if (this.confirmTicketId == null) {
      return false;
    }
    return Objects.equals(this.confirmTicketId, ticketRequestDTO.confirmTicketId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.confirmTicketId);
  }

  // prettier-ignore
    @Override
    public String toString() {
        return "ConfirmTicketDTO{" +
            " confirmTicketId=" + getConfirmTicketId() +
            ", ticketId=" + getTicketId() +
            ", content='" + getContent() + "'" +
            ", departmentId=" + getDepartmentId() +
            ", createUser=" + getCreateUser() +
            ", createDatetime=" + getCreateDatetime() +
            ", status='" + getStatus() + "'" +
            "}";
    }
}
