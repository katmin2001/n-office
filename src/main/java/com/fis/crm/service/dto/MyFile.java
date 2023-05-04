package com.fis.crm.service.dto;

import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;

public class MyFile implements Serializable {
    private static final long serialVersionUID = 1L;

    private MultipartFile multipartFile;

    private String description;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public MultipartFile getMultipartFile() {
        return multipartFile;
    }

    public void setMultipartFile(MultipartFile multipartFile) {
        this.multipartFile = multipartFile;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
