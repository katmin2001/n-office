package com.fis.crm.repository.impl;

import com.fis.crm.commons.DataUtil;
import com.fis.crm.commons.MaperUtils;
import com.fis.crm.repository.CallInformationEvaluationRepository;
import com.fis.crm.repository.UserRepository;
import com.fis.crm.service.UserService;
import com.fis.crm.service.dto.CallInformationEvaluationDTO;
import org.hibernate.Session;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.sql.CallableStatement;
import java.sql.Date;
import java.sql.Types;
import java.util.List;

@Repository
public class CallInformationEvaluationCustomRepositoryImpl implements CallInformationEvaluationRepository {

    final
    UserRepository userRepository;

    final UserService userService;

    final
    EntityManager entityManager;

    public CallInformationEvaluationCustomRepositoryImpl(EntityManager entityManager, UserRepository userRepository,
                                                         UserService userService) {
        this.entityManager = entityManager;
        this.userRepository = userRepository;
        this.userService = userService;
    }

    @Override
    public CallInformationEvaluationDTO getCallInfoById(Long assign_detail_id) {
        String mainSql = "select d.user_id,e.channel_id, e.start_date, e.end_date, e.create_user from evaluate_assignment_detail d, evaluate_assignment e\n" +
            "where d.evaluate_assignment_id = e.id\n" +
            "and d.id = :id";
        Query query = entityManager.createNativeQuery(mainSql);
        query.setParameter("id", assign_detail_id);
        return new MaperUtils(query.getResultList())
            .add("operator")
            .add("channelId")
            .add("startDate")
            .add("endDate")
            .add("assign_user_id")
            .transform(CallInformationEvaluationDTO.class).get(0);
    }

    @Override
    public Page<CallInformationEvaluationDTO> getIncomingCall(Long user_id,
                                                              String create_date_from,
                                                              String create_date_to,
                                                              String call_time,
                                                              String phone_number,
                                                              Pageable pageable) {
        String mainSql = "select ticket_id,u.id evaluatedId, 'Cuộc gọi đến' channel_type, 1 channel_id, c.phone_number, u.first_name, to_char(t.create_datetime,'dd/MM/yyyy HH24:mi:ss') create_date,\n" +
            " row_number() over(order by t.ticket_id) rn from ticket t,customer c, jhi_user u where t.create_user = u.id \n" +
            "and t.customer_id=c.customer_id \n" +
            "and nvl(t.evaluate_status,0) =0 " +
            "and t.create_datetime >= :create_date_from " +
            "and t.create_datetime < trunc(:create_date_to)+1 ";
        if (user_id != null) {
            mainSql += "and t.create_user = :user_id ";
        }
        if (phone_number != null) {
            mainSql += "and c.phone_number like  :phone_number ";
        }

        String selectSql = "select * from (" + mainSql + ") where rn between (:page_num * :page_size + 1) and (:page_num * :page_size + :page_size) order by rn";
        Query selectQuery = entityManager.createNativeQuery(selectSql);

        String countSql = "select COUNT(*) from (" + mainSql + ")";
        Query countQuery = entityManager.createNativeQuery(countSql);

//        selectQuery.setParameter("user_id", user_id);
        selectQuery.setParameter("create_date_from", Date.valueOf(create_date_from));
        selectQuery.setParameter("create_date_to", Date.valueOf(create_date_to));
        if (phone_number != null) {
            selectQuery.setParameter("phone_number", phone_number);
        }
        if (user_id != null) {
            selectQuery.setParameter("user_id", user_id);
        }
        selectQuery.setParameter("page_num", pageable.getOffset());
        selectQuery.setParameter("page_size", pageable.getPageSize());
        List<CallInformationEvaluationDTO> callInformationEvaluationDTOS = new MaperUtils(selectQuery.getResultList())
            .add("id")
            .add("operator")
            .add("channelType")
            .add("channelId")
            .add("phoneNumber")
            .add("operatorName")
            .add("createDate")
            .transform(CallInformationEvaluationDTO.class);

        if (user_id != null) {
            countQuery.setParameter("user_id", user_id);
        }
        countQuery.setParameter("create_date_from", Date.valueOf(create_date_from));
        countQuery.setParameter("create_date_to", Date.valueOf(create_date_to));
        if (phone_number != null) {
            countQuery.setParameter("phone_number", phone_number);
        }
        Long count = DataUtil.safeToLong(countQuery.getSingleResult());
        return new PageImpl<>(callInformationEvaluationDTOS, pageable, count);
    }

    @Override
    public Page<CallInformationEvaluationDTO> getOutGoingCall(Long user_id,
                                                              String call_time_from,
                                                              String call_time_to,
                                                              String call_time,
                                                              String phone_number,
                                                              Pageable pageable) {
        String mainSql = "select d.id, u.id evaluatedId, 'Cuộc gọi đi' channel_type, 2 channel_id, u.first_name, to_char(d.create_datetime,'dd/MM/yyyy HH24:mi:ss') create_date, \n" +
        "d.phone_number, row_number() over(order by d.id) rn from campaign_resource_detail d, jhi_user u \n" +
            "where d.call_status = 2 " +
            "and nvl(d.evaluate_status,0) =0 " +
            "and u.id = d.assign_user_id " +
            "and call_time >= :call_time_from and call_time< trunc(:call_time_to)+1 ";
        if (user_id != null) {
            mainSql += "and d.assign_user_id = :user_id ";
        }
        if (phone_number != null) {
            mainSql += "and d.phone_number like :phone_number ";
        }
        String selectSql = "select * from (" + mainSql + ") where rn between (:page_num * :page_size + 1) and (:page_num * :page_size + :page_size) order by rn";
        Query selectQuery = entityManager.createNativeQuery(selectSql);

        String countSql = "select COUNT(*) from (" + mainSql + ")";
        Query countQuery = entityManager.createNativeQuery(countSql);

        if (user_id != null) {
            selectQuery.setParameter("user_id", user_id);
        }
        selectQuery.setParameter("call_time_from", Date.valueOf(call_time_from));
        selectQuery.setParameter("call_time_to", Date.valueOf(call_time_to));
        if (phone_number != null) {
            selectQuery.setParameter("phone_number", phone_number);
        }
        selectQuery.setParameter("page_num", pageable.getOffset());
        selectQuery.setParameter("page_size", pageable.getPageSize());

        List<CallInformationEvaluationDTO> callInformationEvaluationDTOS = new MaperUtils(selectQuery.getResultList())
            .add("id")
            .add("operator")
            .add("channelType")
            .add("channelId")
            .add("operatorName")
            .add("createDate")
            .add("phoneNumber")
            .transform(CallInformationEvaluationDTO.class);

        if (user_id != null) {
            countQuery.setParameter("user_id", user_id);
        }
        countQuery.setParameter("call_time_from", Date.valueOf(call_time_from));
        countQuery.setParameter("call_time_to", Date.valueOf(call_time_to));
        if (phone_number != null) {
            countQuery.setParameter("phone_number", phone_number);
        }
        Long count = DataUtil.safeToLong(countQuery.getSingleResult());
        return new PageImpl<>(callInformationEvaluationDTOS, pageable, count);
    }

    @Override
    public Long getIdFromProcess(Long idCall, Long channelId, Long evaluaterId, Long evaluatedId, Long evaluateDetailId) {
        Session session = entityManager.unwrap(Session.class);
        final long[] id = new long[1];
        Long userId = userService.getUserWithAuthorities().get().getId();

        try {
            session.doWork(connection -> {
                CallableStatement statement = connection.prepareCall(" {? = call pck_util.build_evaluate (?,?,?,?,?,?)}");
                statement.registerOutParameter(1, Types.INTEGER);
                statement.setLong(2, idCall);
                statement.setString(3, channelId.toString());
                statement.setString(4, evaluaterId != null ? evaluaterId.toString() : userId.toString());
                statement.setString(5, evaluatedId != null ? evaluatedId.toString() : "");
                statement.setString(6, evaluateDetailId != null ? evaluateDetailId.toString() : "");
                statement.registerOutParameter(7, Types.VARCHAR);
                statement.executeUpdate();
                String strError = statement.getString(7);
                if (strError != null && !"".equals(strError)) {
                    id[0] = -1L;
                } else {
                    id[0] = !DataUtil.isNullOrEmpty(statement.getInt(1))
                        ? DataUtil.safeToLong(statement.getInt(1)) : 0L;
                }
            });
            return id[0];
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }
}
