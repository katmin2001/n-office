package com.fis.crm.crm_entity.DTO;

import com.fis.crm.crm_entity.CrmUserRole;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.Date;
import java.util.Set;

public class Crm_UserDTO {
    private Long userId;
    @NotNull(message = "Tên tài khoản không được bỏ trống")
    private String username;
    @NotNull(message = "Họ tên không được bỏ trống")
    private String fullName;
    @NotNull(message = "Ngày tạo không được bỏ trống")
    private Date createDate;
    @NotNull(message = "Số điện thoại không được bỏ trống")
    @Pattern(regexp = "^(?:\\+?84|0)(?:\\d{9})$", message = "Nhập không đúng định dạng")
    private String phone;
    @NotNull(message = "Ngày tạo không được bỏ trống")
    private Date birthday;
    @NotNull(message = "Địa chỉ không được bỏ trống")
    private String address;
    @NotNull(message = "Trạng thái không được bỏ trống")
    private String status;

    private Set<CrmRoleDTO> userRoles;

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

    public Crm_UserDTO(Long userId, String username, String fullName, Date createDate, String phone, Date birthday, String address, String status, Set<CrmRoleDTO> userRoles) {
        this.userId = userId;
        this.username = username;
        this.fullName = fullName;
        this.createDate = createDate;
        this.phone = phone;
        this.birthday = birthday;
        this.address = address;
        this.status = status;
        this.userRoles = userRoles;
    }

    public Crm_UserDTO() {
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

    public Set<CrmRoleDTO> getUserRoles() {
        return userRoles;
    }

    public void setUserRoles(Set<CrmRoleDTO> userRoles) {
        this.userRoles = userRoles;
    }
}
