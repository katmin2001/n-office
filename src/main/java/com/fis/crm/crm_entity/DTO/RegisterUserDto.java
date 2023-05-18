package com.fis.crm.crm_entity.DTO;

import javax.validation.constraints.Size;

public class RegisterUserDto extends Crm_UserDTO{
    @Size(min = 1, max = 100)
    private String password;

    public RegisterUserDto() {
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
