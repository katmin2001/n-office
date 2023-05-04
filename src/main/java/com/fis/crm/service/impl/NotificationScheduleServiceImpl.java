package com.fis.crm.service.impl;

import com.fis.crm.service.NotificationScheduleService;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.sql.CallableStatement;

@Service
@Transactional
public class NotificationScheduleServiceImpl implements NotificationScheduleService {

    private Logger log = LoggerFactory.getLogger(NotificationScheduleServiceImpl.class);
    private final EntityManager entityManager;

    public NotificationScheduleServiceImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public void runNotification() {
        Session session = entityManager.unwrap(Session.class);
        session.doWork(connection -> {
            CallableStatement vcstmt = null;
            try {
                String vstrSQL = "{call pck_util.run_notification(sysdate) }";
                vcstmt = connection.prepareCall(vstrSQL);
                vcstmt.executeQuery();
            } catch (Exception ex) {
                log.error(ex.getMessage());
            }
        });
    }
}
