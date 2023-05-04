package com.fis.crm.service.dto;

import java.io.Serializable;

/**
 * A DTO for the {@link com.fis.crm.domain.GroupUser} entity.
 */
public class GroupUserDTO implements Serializable {

    private Long id;

    private String name;

    private String status;

    private Long numberData;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getNumberData() {
        return numberData;
    }

    public void setNumberData(Long numberData) {
        this.numberData = numberData;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof GroupUserDTO)) {
            return false;
        }

        return id != null && id.equals(((GroupUserDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "GroupUserDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", status='" + getStatus() + "'" +
            "}";
    }
}
