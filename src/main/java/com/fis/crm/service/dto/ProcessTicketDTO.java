package com.fis.crm.service.dto;

import java.io.Serializable;
import java.time.Instant;
import java.util.List;
import java.util.Objects;

/**
 * A DTO for the {@link com.fis.crm.domain.TicketRequest} entity.
 */
public class ProcessTicketDTO implements Serializable {

    private Long processTicketId;

    private Long ticketId;

    private Long ticketRequestId;

    private String fileName;

    private String content;

    private Long departmentId;

    private Instant createDatetime;

    private Long createUser;

    private String status;

    private String userName;

    private Long check;

    private String departmentName;

    List<ProcessTicketAttachmentDTO> listProcessTicketAttachmentDTOS;

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public List<ProcessTicketAttachmentDTO> getListProcessTicketAttachmentDTOS() {
        return listProcessTicketAttachmentDTOS;
    }

    public void setListProcessTicketAttachmentDTOS(List<ProcessTicketAttachmentDTO> listProcessTicketAttachmentDTOS) {
        this.listProcessTicketAttachmentDTOS = listProcessTicketAttachmentDTOS;
    }

    public Long getProcessTicketId() {
        return processTicketId;
    }

    public void setProcessTicketId(Long processTicketId) {
        this.processTicketId = processTicketId;
    }

    public Long getTicketId() {
        return ticketId;
    }

    public void setTicketId(Long ticketId) {
        this.ticketId = ticketId;
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

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Long getCheck() {
        return check;
    }

    public void setCheck(Long check) {
        this.check = check;
    }

    @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof ProcessTicketDTO)) {
      return false;
    }

    ProcessTicketDTO ticketRequestDTO = (ProcessTicketDTO) o;
    if (this.processTicketId == null) {
      return false;
    }
    return Objects.equals(this.processTicketId, ticketRequestDTO.processTicketId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.processTicketId);
  }

  // prettier-ignore
    @Override
    public String toString() {
        return "ProcessTicketDTO{" +
            " processTicketId=" + getProcessTicketId() +
            ", ticketId=" + getTicketId() +
            ", ticketRequestId=" + getTicketRequestId() +
            ", content='" + getContent() + "'" +
            ", departmentId=" + getDepartmentId() +
            ", createUser=" + getCreateUser() +
            ", createDatetime=" + getCreateDatetime() +
            ", status='" + getStatus() + "'" +
            "}";
    }
}
