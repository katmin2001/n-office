package com.fis.crm.service.dto;

import java.io.Serializable;

/**
 * A DTO for the {@link com.fis.crm.domain.ApDomain} entity.
 */
public class ApDomainDTO implements Serializable {

    private Long id;

    private String status;

    private String type;

    private String name;

    private String code;

    public ApDomainDTO() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ApDomainDTO)) {
            return false;
        }

        return id != null && id.equals(((ApDomainDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ApDomainDTO{" +
            "id=" + getId() +
            ", status='" + getStatus() + "'" +
            ", type='" + getType() + "'" +
            ", name='" + getName() + "'" +
            ", code='" + getCode() + "'" +
            "}";
    }
}
