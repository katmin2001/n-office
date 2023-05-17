package com.fis.crm.crm_entity.DTO;

import javax.persistence.Basic;
import javax.persistence.Column;
import java.sql.Date;

public class CandidateDTO {
    private String fullname;

    private String phone;

    private Date birthday;

    private String address;

    private Boolean status;

    private Date createDate;
    private Long manageId;
    private Long ISID;

    public CandidateDTO() {
    }

    public CandidateDTO( String fullname, String phone, Date birthday, String address, Boolean status, Date createDate, Long manageId, Long ISID) {
        this.fullname = fullname;
        this.phone = phone;
        this.birthday = birthday;
        this.address = address;
        this.status = status;
        this.createDate = createDate;
        this.manageId = manageId;
        this.ISID = ISID;
    }


    public Long getISID() {
        return ISID;
    }

    public void setISID(Long ISID) {
        this.ISID = ISID;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Long getManageId() {
        return manageId;
    }

    public void setManageId(Long manageId) {
        this.manageId = manageId;
    }
}
