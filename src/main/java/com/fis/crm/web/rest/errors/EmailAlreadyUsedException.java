package com.fis.crm.web.rest.errors;

import com.fis.crm.commons.Translator;

public class EmailAlreadyUsedException extends BadRequestAlertException {

    private static final long serialVersionUID = 1L;

    public EmailAlreadyUsedException() {
        super(ErrorConstants.EMAIL_ALREADY_USED_TYPE, Translator.toLocale("email-exists"), "userManagement", "emailexists");
    }
}
