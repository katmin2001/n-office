package com.fis.crm.service.dto;

import com.fis.crm.commons.DataUtil;

public class SummaryDTO {
    private String type;
    private Long totalDay;
    private Long totalMonth;

    public SummaryDTO(Object[] obj) {
        this.type = DataUtil.safeToString(obj[0]);
        this.totalDay = DataUtil.safeToLong(obj[1]);
        this.totalMonth = DataUtil.safeToLong(obj[2]);
    }


    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Long getTotalDay() {
        return totalDay;
    }

    public void setTotalDay(Long totalDay) {
        this.totalDay = totalDay;
    }

    public Long getTotalMonth() {
        return totalMonth;
    }

    public void setTotalMonth(Long totalMonth) {
        this.totalMonth = totalMonth;
    }
}
