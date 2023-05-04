package com.fis.crm.service.dto;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A DTO for the {@link com.fis.crm.domain.Ticket} entity.
 */
public class DepartmentDTO implements Serializable {

    private Long departmentId;

    private String departmentName;

    private String departmentCode;

    private String status;

    public Long getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Long departmentId) {
        this.departmentId = departmentId;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDepartmentCode() {
        return departmentCode;
    }

    public void setDepartmentCode(String departmentCode) {
        this.departmentCode = departmentCode;
    }

    @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof DepartmentDTO)) {
      return false;
    }

    DepartmentDTO departmentDTO = (DepartmentDTO) o;
    if (this.departmentId == null) {
      return false;
    }
    return Objects.equals(this.departmentId, departmentDTO.departmentId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.departmentId);
  }

    @Override
    public String toString() {
        return "DepartmentDTO{" +
            "departmentId=" + departmentId +
            ", departmentName='" + departmentName + '\'' +
            ", departmentCode='" + departmentCode + '\'' +
            ", status='" + status + '\'' +
            '}';
    }
}
