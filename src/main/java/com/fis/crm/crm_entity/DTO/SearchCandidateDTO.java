package com.fis.crm.crm_entity.DTO;

import java.sql.Date;

public class SearchCandidateDTO {
    private String startDayCreate;
    private String endDayCreate;
    private String startDay;
    private String endDay;
    private String ISID;
    private String manageId;

    public SearchCandidateDTO() {
    }

    public SearchCandidateDTO(String startDayCreate, String endDayCreate, String startDay, String endDay, String ISID, String manageId) {
        this.startDayCreate = startDayCreate;
        this.endDayCreate = endDayCreate;
        this.startDay = startDay;
        this.endDay = endDay;
        this.ISID = ISID;
        this.manageId = manageId;
    }

    public String getStartDayCreate() {
        return startDayCreate;
    }

    public void setStartDayCreate(String startDayCreate) {
        this.startDayCreate = startDayCreate;
    }

    public String getEndDayCreate() {
        return endDayCreate;
    }

    public void setEndDayCreate(String endDayCreate) {
        this.endDayCreate = endDayCreate;
    }

    public String getStartDay() {
        return startDay;
    }

    public void setStartDay(String startDay) {
        this.startDay = startDay;
    }

    public String getEndDay() {
        return endDay;
    }

    public void setEndDay(String endDay) {
        this.endDay = endDay;
    }

    public String getISID() {
        return ISID;
    }

    public void setISID(String ISID) {
        this.ISID = ISID;
    }

    public String getManageId() {
        return manageId;
    }

    public void setManageId(String manageId) {
        this.manageId = manageId;
    }
}
