package com.fis.crm.service;

public interface SendAutoEmailAndSMSService {
    void send();
    void updateCallInfor();
    void sumDashboard();
    void resetPass(String name,String userName,String password,String email,long createUserId);
}
