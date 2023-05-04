package com.fis.crm.service.dto;

import java.io.Serializable;

public class ActionTypeDTO implements Serializable {

    private Long id;
    private String name;

    public ActionTypeDTO(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public ActionTypeDTO() {
    }

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
}
