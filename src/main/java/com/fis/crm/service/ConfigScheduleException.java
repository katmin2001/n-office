package com.fis.crm.service;

public class ConfigScheduleException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public ConfigScheduleException() {
        super("Existing config schedule !");
    }
}
