package com.fis.crm.service.dto;

import java.io.Serializable;
import java.util.Objects;

public class CriteriaGroupLoadDTO implements Serializable {

    private Long id;
    private String name;
    private String status;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CriteriaGroupLoadDTO)) {
            return false;
        }

        CriteriaGroupLoadDTO CriteriaGroupLoadDTO = (CriteriaGroupLoadDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, CriteriaGroupLoadDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }

    @Override
    public String toString() {
        return "CriteriaGroupLoadDTO{" +
            "id=" + id +
            ", name='" + name + '\'' +
            '}';
    }
}
