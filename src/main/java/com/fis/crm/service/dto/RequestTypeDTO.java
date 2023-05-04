package com.fis.crm.service.dto;

import com.fis.crm.commons.DataUtil;

public class RequestTypeDTO {
    private Long id;

    private String name;

    private String code;

    public RequestTypeDTO() {
    }

    public RequestTypeDTO(Object[] objects){
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
}
