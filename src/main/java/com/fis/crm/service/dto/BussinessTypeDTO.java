package com.fis.crm.service.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fis.crm.commons.DataUtil;

import java.io.Serializable;

/**
 * A DTO for the {@link com.fis.crm.domain.BussinessType} entity.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class BussinessTypeDTO implements Serializable {

    private Long id;

    private Long status;

    private String name;

    private String code;

    public BussinessTypeDTO() {
    }

    public BussinessTypeDTO(Object[] objects){
        int i = 0;
        this.id = DataUtil.safeToLong(objects[i++]);
        this.code = DataUtil.safeToString(objects[i++]);
        this.name = DataUtil.safeToString(objects[i++]);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getStatus() {
        return status;
    }

    public void setStatus(Long status) {
        this.status = status;
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
        if (!(o instanceof BussinessTypeDTO)) {
            return false;
        }

        return id != null && id.equals(((BussinessTypeDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "BussinessTypeDTO{" +
            "id=" + getId() +
            ", status=" + getStatus() +
            ", name='" + getName() + "'" +
            "}";
    }
}
