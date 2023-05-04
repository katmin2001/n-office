package com.fis.crm.service.impl;

import com.fis.crm.service.SendAutoEmailAndSMSService;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.sql.CallableStatement;

@Service
@Transactional
public class SendAutoEmailAndSMSServiceImpl implements SendAutoEmailAndSMSService {

    private Logger log = LoggerFactory.getLogger(SendAutoEmailAndSMSServiceImpl.class);
    private final EntityManager entityManager;

    public SendAutoEmailAndSMSServiceImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public void send() {
        Session session = entityManager.unwrap(Session.class);
        session.doWork(connection -> {
            CallableStatement vcstmt = null;
            try {
                String vstrSQL = "{call pck_util.send_email_sms }";
                vcstmt = connection.prepareCall(vstrSQL);
                vcstmt.executeQuery();
            } catch (Exception ex) {
                log.error(ex.getMessage());
            }
        });
    }

    @Override
    public void updateCallInfor() {
        Session session = entityManager.unwrap(Session.class);
        session.doWork(connection -> {
            CallableStatement vcstmt = null;
            try {
                String vstrSQL = "{call pck_util.update_call_infor }";
                vcstmt = connection.prepareCall(vstrSQL);
                vcstmt.executeQuery();
            } catch (Exception ex) {
                log.error(ex.getMessage());
            }
        });
    }

    @Override
    public void sumDashboard() {
        Session session = entityManager.unwrap(Session.class);
        session.doWork(connection -> {
            CallableStatement vcstmt = null;
            try {
                String vstrSQL = "{call pck_util.summary_dashboard('1') }";
                vcstmt = connection.prepareCall(vstrSQL);
                vcstmt.executeQuery();
            } catch (Exception ex) {
                log.error(ex.getMessage());
            }
        });
    }
    @Override
    public void resetPass(String name,String userName,String password,String email,long createUserId) {
        Session session = entityManager.unwrap(Session.class);
        session.doWork(connection -> {
            CallableStatement vcstmt = null;
            try {
                String vstrSQL = "{call pck_util.send_email_resetpass(?,?,?,?,?) }";
                vcstmt = connection.prepareCall(vstrSQL);
                vcstmt.setString(1,name);
                vcstmt.setString(2,password);
                vcstmt.setString(3,userName);
                vcstmt.setString(4,email);
                vcstmt.setLong(5,createUserId);
                vcstmt.executeQuery();
            } catch (Exception ex) {
                log.error(ex.getMessage());
            }
        });
    }
}
