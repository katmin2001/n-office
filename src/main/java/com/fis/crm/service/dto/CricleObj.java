package com.fis.crm.service.dto;

import com.fis.crm.commons.DataUtil;

public class CricleObj {
    private String name;
    private Long value;

    public CricleObj(Object[] obj) {
        this.name = DataUtil.safeToString(obj[0]);
        this.value = DataUtil.safeToLong(obj[1]);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getValue() {
        return value;
    }

    public void setValue(Long value) {
        this.value = value;
    }
}
