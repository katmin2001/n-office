package com.fis.crm.service.dto;

import com.fis.crm.domain.User;

/**
 * A DTO representing a user, with only the public attributes.
 */
public class UserDTO {

    private Long id;

    private String login;

    private String fullName;

    private Boolean createTicket;

    private Boolean processTicket;

    private Boolean confirmTicket;

    private Long numberData;

    private String internalNumber;

    private String extendVal;

    public String getInternalNumber() {
        return internalNumber;
    }

    public void setInternalNumber(String internalNumber) {
        this.internalNumber = internalNumber;
    }



    public UserDTO() {
        // Empty constructor needed for Jackson.
    }

    public UserDTO(User user) {
        this.id = user.getId();
        // Customize it here if you need, or not, firstName/lastName/etc
        this.login = user.getLogin();
        this.fullName = user.getFirstName();
        this.createTicket = user.isCreateTicket();
        this.processTicket = user.isProcessTicket();
        this.confirmTicket = user.isConfirmTicket();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public Boolean isCreateTicket() {
        return createTicket;
    }

    public void setCreateTicket(boolean createTicket) {
        this.createTicket = createTicket;
    }

    public Boolean isProcessTicket() {
        return processTicket;
    }

    public void setProcessTicket(boolean processTicket) {
        this.processTicket = processTicket;
    }

    public Boolean isConfirmTicket() {
        return confirmTicket;
    }

    public void setConfirmTicket(boolean confirmTicket) {
        this.confirmTicket = confirmTicket;
    }

    public Long getNumberData() {
        return numberData;
    }

    public void setNumberData(Long numberData) {
        this.numberData = numberData;
    }

    public String getExtendVal() {
        return extendVal;
    }

    public void setExtendVal(String extendVal) {
        this.extendVal = extendVal;
    }

    // prettier-ignore

    @Override
    public String toString() {
        return "UserDTO{" +
            "id=" + id +
            ", login='" + login + '\'' +
            ", fullName='" + fullName + '\'' +
            ", createTicket='" + createTicket + '\'' +
            ", processTicket='" + processTicket + '\'' +
            ", confirmTicket='" + confirmTicket + '\'' +
            '}';
    }
}
