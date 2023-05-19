package com.fis.crm.crm_entity.DTO;

public class InterviewStatusDTO {
    private Long ISID;
    private String statusName;
    private String description;

    public InterviewStatusDTO() {
    }

    public InterviewStatusDTO(Long ISID, String statusName, String description) {
        this.ISID = ISID;
        this.statusName = statusName;
        this.description = description;
    }

    public Long getISID() {
        return ISID;
    }

    public void setISID(Long ISID) {
        this.ISID = ISID;
    }

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
