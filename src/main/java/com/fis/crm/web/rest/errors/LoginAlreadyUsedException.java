package com.fis.crm.web.rest.errors;

import com.fis.crm.commons.Translator;

public class LoginAlreadyUsedException extends BadRequestAlertException {

    private static final long serialVersionUID = 1L;

    public LoginAlreadyUsedException() {
//        super(ErrorConstants.LOGIN_ALREADY_USED_TYPE, "Login name already used!", "userManagement", "userexists");
        super(ErrorConstants.LOGIN_ALREADY_USED_TYPE, Translator.toLocale("error.login-existed"), "userManagement", "userexists");
    }
}
