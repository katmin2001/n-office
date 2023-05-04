package com.fis.crm.service.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A DTO for the {@link com.fis.crm.domain.TicketRequest} entity.
 */
public class TicketRequestAttachmentDTO implements Serializable {

    private Long ticketRequestAttachmentId;

    private Long ticketRequestId;

    private String fileName;

    private String fillNameEncrypt;

    private Instant createDatetime;

    private Long createUser;

    private String status;

    private String typeFile;

    private int size;
    private String keySearch;
    private String errorCodeConfig;
    private String importType;

    @JsonIgnore
    private XSSFWorkbook xssfWorkbook;

    public XSSFWorkbook getXssfWorkbook() {
        return xssfWorkbook;
    }

    public void setXssfWorkbook(XSSFWorkbook xssfWorkbook) {
        this.xssfWorkbook = xssfWorkbook;
    }

    public Long getTicketRequestAttachmentId() {
        return ticketRequestAttachmentId;
    }

    public void setTicketRequestAttachmentId(Long ticketRequestAttachmentId) {
        this.ticketRequestAttachmentId = ticketRequestAttachmentId;
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

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public String getKeySearch() {
        return keySearch;
    }

    public void setKeySearch(String keySearch) {
        this.keySearch = keySearch;
    }

    public String getErrorCodeConfig() {
        return errorCodeConfig;
    }

    public void setErrorCodeConfig(String errorCodeConfig) {
        this.errorCodeConfig = errorCodeConfig;
    }

    public String getImportType() {
        return importType;
    }

    public void setImportType(String importType) {
        this.importType = importType;
    }

    public String getTypeFile() {
        return typeFile;
    }

    public void setTypeFile(String typeFile) {
        this.typeFile = typeFile;
    }

    @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof TicketRequestAttachmentDTO)) {
      return false;
    }

    TicketRequestAttachmentDTO ticketRequestDTO = (TicketRequestAttachmentDTO) o;
    if (this.ticketRequestAttachmentId == null) {
      return false;
    }
    return Objects.equals(this.ticketRequestAttachmentId, ticketRequestDTO.ticketRequestAttachmentId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.ticketRequestAttachmentId);
  }

  // prettier-ignore
    @Override
    public String toString() {
        return "TicketRequestAttachmentDTO{" +
            " ticketRequestAttachmentId=" + getTicketRequestAttachmentId() +
            ", ticketRequestId=" + getTicketRequestId() +
            ", fileName='" + getFileName() + "'" +
            ", fillNameEncrypt='" + getFillNameEncrypt() + "'" +
            ", createDatetime=" + getCreateDatetime() +
            ", createUser=" + getCreateUser() +
            ", status='" + getStatus() + "'" +
            "}";
    }
}
