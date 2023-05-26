package com.fis.crm.crm_entity.DTO;


import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class RegisterUserDto extends CrmUserDTO {
    @Size(min = 3, max = 60, message = "Độ dài mật khẩu không hợp lệ")
    @NotNull(message = "Mật khẩu không được bỏ trống")
    private String password;

    public RegisterUserDto() {
        super();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
