package com.fis.crm.service.dto;

import java.io.Serializable;

public class CopyScriptRequestDTO implements Serializable {

    private Long id;

    private String newName;

    public CopyScriptRequestDTO() {
    }

    public CopyScriptRequestDTO(Long id, String newName) {
        this.id = id;
        this.newName = newName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNewName() {
        return newName;
    }

    public void setNewName(String newName) {
        this.newName = newName;
    }
}
