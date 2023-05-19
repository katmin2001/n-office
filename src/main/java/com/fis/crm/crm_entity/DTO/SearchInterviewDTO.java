package com.fis.crm.crm_entity.DTO;


public class SearchInterviewDTO {
    private String startDay;
    private String endDay;
    private String ISID;
    private String interviewer;

    public SearchInterviewDTO() {
    }

    public SearchInterviewDTO(String startDay, String endDay, String ISID, String interviewer) {
        this.startDay = startDay;
        this.endDay = endDay;
        this.ISID = ISID;
        this.interviewer = interviewer;
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

    public String getInterviewer() {
        return interviewer;
    }

    public void setInterviewer(String interviewer) {
        this.interviewer = interviewer;
    }
}
