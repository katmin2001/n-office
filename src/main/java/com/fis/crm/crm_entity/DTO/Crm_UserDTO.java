package com.fis.crm.crm_entity.DTO;

import java.util.Date;

public class Crm_UserDTO {
    private Long userId;
    private String username;
    private String fullName;
    private Date createDate;
    private String phone;
    private Date birthday;
    private String address;
    private String status;

    public Crm_UserDTO() {
    }

    public Crm_UserDTO(Long userId, String username, String fullName, Date createDate, String phone, Date birthday, String address, String status) {
        this.userId = userId;
        this.username = username;
        this.fullName = fullName;
        this.createDate = createDate;
        this.phone = phone;
        this.birthday = birthday;
        this.address = address;
        this.status = status;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
