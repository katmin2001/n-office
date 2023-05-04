package com.fis.crm.service.dto;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A DTO for the {@link com.fis.crm.domain.Ticket} entity.
 */
public class CustomerDTO implements Serializable {

    private Long customerId;

    private String phoneNumber;

    private String email;

    private String name;

    private String contactPhone;

    private Instant createDatetime;

    private Instant updateDatetime;

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContactPhone() {
        return contactPhone;
    }

    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone;
    }

    public Instant getCreateDatetime() {
        return createDatetime;
    }

    public void setCreateDatetime(Instant createDatetime) {
        this.createDatetime = createDatetime;
    }

    public Instant getUpdateDatetime() {
        return updateDatetime;
    }

    public void setUpdateDatetime(Instant updateDatetime) {
        this.updateDatetime = updateDatetime;
    }

    @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof CustomerDTO)) {
      return false;
    }

    CustomerDTO customerDTO = (CustomerDTO) o;
    if (this.customerId == null) {
      return false;
    }
    return Objects.equals(this.customerId, customerDTO.customerId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.customerId);
  }

  // prettier-ignore
    @Override
    public String toString() {
        return "CustomerDTO{" +
            " customerId=" + getCustomerId() +
            ", phoneNumber='" + getPhoneNumber() + "'" +
            ", email='" + getEmail() + "'" +
            ", name='" + getName() + "'" +
            ", contactPhone='" + getContactPhone() + "'" +
            ", createDatetime='" + getCreateDatetime() + "'" +
            ", updateDatetime='" + getUpdateDatetime() + "'" +
            "}";
    }
}
