package com.fis.crm.repository;

import com.fis.crm.domain.Ticket;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;

/**
 * Spring Data  repository for the Ticket entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long> {

    @Query(value = "select * from ticket inner join jhi_user on ticket.create_user = jhi_user.id where ticket.cid =:cid order by ticket.CREATE_DATETIME desc ",
        countQuery = "select count(1) \n " +
            " from ticket inner join jhi_user on ticket.create_user = jhi_user.id where ticket.cid =:cid  ", nativeQuery = true)
    Page<Ticket> getListHistorySupports(@Param("cid") String cid, Pageable pageable);

    @Procedure(value = "PCK_UTIL.UPDATE_TICKET_CONFIRM_DATE")
    void updateTicketConfirmDate(long ticketId);

    @Query(value = "call pck_util.update_ticket_status(:ticketId)", nativeQuery = true)
    void updateTicketStatus(@Param("ticketId") Long ticketId);


    @Query(value = " select distinct t.ticket_id, t.ticket_code                                                                          ma_xu_vu,\n" +
        "       t.chanel_type,\n" +
        "       (select name from ap_domain ap where type = 'CHANEL_TYPE' and ap.code = t.chanel_type) channel_type_name,\n" +
        "       c.name                                                                                 ten_kh,\n" +
        "       t.cid                                                                                  ma_dinh_danh,\n" +
        "       t.company_name                                                                         ten_co_quan,\n" +
        "       c.phone_number,\n" +
        "       c.email,\n" +
        "       (select name from ap_domain ap where type = 'TICKET_STATUS' and ap.code = t.status)    trang_thai_xl,\n" +
        "       (select first_name from jhi_user u where u.id = t.create_user)                         nguoi_xu_ly,\n" +
        "       to_char(t.create_datetime, 'dd/mm/yyyy HH24:mi:ss')                                    thoi_diem_tiep_nhan,\n" +
        "       t.STATUS, \n" +
        "       t.ticket_status, \n" +
        "       t.ticket_request_status, \n" +
        "       t.create_departments, \n" +
        "       t.CREATE_DATETIME, \n" +
        "       t.CONFIRM_DATETIME, \n" +
        "       t.CONFIRM_DEADLINE \n" +
        "from ticket t,\n" +
        "     customer c," +
        "     jhi_user ju, \n" +
        "     ticket_request tr \n" +
        "where t.customer_id = c.customer_id and ju.ID = t.CREATE_USER \n" +
        "  and t.status in (:status)\n" +
        "  and (:channel is null or t.chanel_type = :channel)\n" +
        "  and (:phone is null or c.phone_number like :phone)\n" +
        "  and (:emails is null or lower (c.email) like lower (:emails))\n" +
        "  and (:idCode is null or  lower (t.cid) like lower (:idCode))\n" +
        "  and (:receiveUser is null or  lower (ju.FIRST_NAME) like lower (:receiveUser))\n" +
        "  and (:fromDate is null or  trunc(t.CREATE_DATETIME) >= TO_DATE(:fromDate,'dd/mm/yyyy'))\n" +
        "  and (:toDate is null or  trunc(t.CREATE_DATETIME) <= TO_DATE(:toDate, 'dd/mm/yyyy')) " +
        "  and (t.ticket_id = tr.ticket_id) \n" +
        "  and ((tr.department_id is not null and tr.department_id in (:departments)) or (tr.department_id is null)) \n"+
        " order by (case when 1 in :status then TICKET_REQUEST_STATUS else TICKET_STATUS end) desc,\n" +
        "                                       (case when 5 in :status then CONFIRM_DATETIME else CREATE_DATETIME end) asc",


//        countQuery = " select count(1) " +
//            "from ( " +
//            " select distinct t.ticket_id, t.ticket_code                                                                          ma_xu_vu,\n" +
//            "       t.CREATE_DATETIME \n" +
//            "from ticket t,\n" +
//            "     customer c, \n" +
//            "     jhi_user ju, " +
//            "     ticket_request tr \n" +
//            "where t.customer_id = c.customer_id and ju.ID = t.CREATE_USER \n" +
//            "  and t.status in (:status)\n" +
//            "  and (:channel is null or t.chanel_type = :channel)\n" +
//            "  and (:phone is null or c.phone_number like :phone)\n" +
//            "  and (:emails is null or lower (c.email) like lower (:emails))\n" +
//            "  and (:idCode is null or  lower (t.cid) like lower (:idCode))\n" +
//            "  and (:receiveUser is null or  lower (ju.FIRST_NAME) like lower (:receiveUser))\n" +
//            "  and (:fromDate is null or  trunc(t.CREATE_DATETIME) >= TO_DATE(:fromDate,'dd/mm/yyyy'))\n" +
//            "  and (:toDate is null or  trunc(t.CREATE_DATETIME) <= TO_DATE(:toDate, 'dd/mm/yyyy')) "+
//            "  and (t.ticket_id = tr.ticket_id) \n" +
//            "  and ((tr.department_id is not null and tr.department_id in :departments) or (tr.department_id is null)) order by t.CREATE_DATETIME desc  " +
//            ")"
//        ,
        nativeQuery = true)
    List<Object[]> searchTicket(@Param("channel") String channel,
                                @Param("phone") String phone,
                                @Param("emails") String emails,
                                @Param("idCode") String idCode,
                                @Param("receiveUser") String receiveUser,
                                @Param("fromDate") String fromDate,
                                @Param("toDate") String toDate,
                                @Param("status") List<Long> status,
                                @Param("departments") List<String> departmentsList
    );

    @Query(value = " select distinct t.ticket_id, t.ticket_code                                                                          ma_xu_vu,\n" +
        "       t.chanel_type,\n" +
        "       (select name from ap_domain ap where type = 'CHANEL_TYPE' and ap.code = t.chanel_type) channel_type_name,\n" +
        "       c.name                                                                                 ten_kh,\n" +
        "       t.cid                                                                                  ma_dinh_danh,\n" +
        "       t.company_name                                                                         ten_co_quan,\n" +
        "       c.phone_number,\n" +
        "       c.email,\n" +
        "       (select name from ap_domain ap where type = 'TICKET_STATUS' and ap.code = t.status)    trang_thai_xl,\n" +
        "       (select first_name from jhi_user u where u.id = t.create_user)                         nguoi_xu_ly,\n" +
        "       to_char(t.create_datetime, 'dd/mm/yyyy HH24:mi:ss')                                    thoi_diem_tiep_nhan,\n" +
        "       t.STATUS, \n" +
        "       t.ticket_status, \n" +
        "       t.ticket_request_status, \n" +
        "       t.create_departments, \n" +
        "       t.CREATE_DATETIME, \n" +
        "       t.CONFIRM_DATETIME, \n" +
        "       t.CONFIRM_DEADLINE \n" +
        "from ticket t,\n" +
        "     customer c," +
        "     jhi_user ju, \n" +
        "     ticket_request tr \n" +
        "where t.customer_id = c.customer_id and ju.ID = t.CREATE_USER \n" +
        "  and t.status in (:status)\n" +
        "  and (:channel is null or t.chanel_type = :channel)\n" +
        "  and (:phone is null or c.phone_number like :phone)\n" +
        "  and (:emails is null or lower (c.email) like lower (:emails))\n" +
        "  and (:idCode is null or  lower (t.cid) like lower (:idCode))\n" +
        "  and (:receiveUser is null or  lower (ju.FIRST_NAME) like lower (:receiveUser))\n" +
        "  and (:fromDate is null or  trunc(t.CREATE_DATETIME) >= TO_DATE(:fromDate,'dd/mm/yyyy'))\n" +
        "  and (:toDate is null or  trunc(t.CREATE_DATETIME) <= TO_DATE(:toDate, 'dd/mm/yyyy')) " +
        "  and (t.ticket_id = tr.ticket_id) \n" +
        " order by (case when 1 in :status then TICKET_REQUEST_STATUS else TICKET_STATUS end) desc,\n" +
        "                                       (case when 5 in :status then CONFIRM_DATETIME else CREATE_DATETIME end) asc",
        countQuery = " select count(1) " +
            "from ( " + " select distinct t.ticket_id, t.ticket_code                                                                          ma_xu_vu,\n" +
            "       t.chanel_type,\n" +
            "       (select name from ap_domain ap where type = 'CHANEL_TYPE' and ap.code = t.chanel_type) channel_type_name,\n" +
            "       c.name                                                                                 ten_kh,\n" +
            "       t.cid                                                                                  ma_dinh_danh,\n" +
            "       t.company_name                                                                         ten_co_quan,\n" +
            "       c.phone_number,\n" +
            "       c.email,\n" +
            "       (select name from ap_domain ap where type = 'TICKET_STATUS' and ap.code = t.status)    trang_thai_xl,\n" +
            "       (select first_name from jhi_user u where u.id = t.create_user)                         nguoi_xu_ly,\n" +
            "       to_char(t.create_datetime, 'dd/mm/yyyy HH24:mi:ss')                                    thoi_diem_tiep_nhan,\n" +
            "       t.STATUS, \n" +
            "       t.ticket_status, \n" +
            "       t.ticket_request_status, \n" +
            "       t.create_departments, \n" +
            "       t.CREATE_DATETIME, \n" +
            "       t.CONFIRM_DATETIME, \n" +
            "       t.CONFIRM_DEADLINE \n" +
            "from ticket t,\n" +
            "     customer c," +
            "     jhi_user ju, \n" +
            "     ticket_request tr \n" +
            "where t.customer_id = c.customer_id and ju.ID = t.CREATE_USER \n" +
            "  and t.status in (:status)\n" +
            "  and (:channel is null or t.chanel_type = :channel)\n" +
            "  and (:phone is null or c.phone_number like :phone)\n" +
            "  and (:emails is null or lower (c.email) like lower (:emails))\n" +
            "  and (:idCode is null or  lower (t.cid) like lower (:idCode))\n" +
            "  and (:receiveUser is null or  lower (ju.FIRST_NAME) like lower (:receiveUser))\n" +
            "  and (:fromDate is null or  trunc(t.CREATE_DATETIME) >= TO_DATE(:fromDate,'dd/mm/yyyy'))\n" +
            "  and (:toDate is null or  trunc(t.CREATE_DATETIME) <= TO_DATE(:toDate, 'dd/mm/yyyy')) " +
            "  and (t.ticket_id = tr.ticket_id) \n" +
            " order by (case when 1 in :status then TICKET_REQUEST_STATUS else TICKET_STATUS end) desc,\n" +
            "                                       (case when 5 in :status then CONFIRM_DATETIME else CREATE_DATETIME end) asc)",
        nativeQuery = true)
    Page<Object[]> searchTicketByCC(@Param("channel") String channel,
                                    @Param("phone") String phone,
                                    @Param("emails") String emails,
                                    @Param("idCode") String idCode,
                                    @Param("receiveUser") String receiveUser,
                                    @Param("fromDate") String fromDate,
                                    @Param("toDate") String toDate,
                                    @Param("status") List<Long> status, Pageable pageable);

    @Query(value = " select  t.ticket_id, t.ticket_code                                                                          ma_xu_vu,\n" +
        "       t.chanel_type,\n" +
        "       (select name from ap_domain ap where type = 'CHANEL_TYPE' and ap.code = t.chanel_type) channel_type_name,\n" +
        "       c.name                                                                                 ten_kh,\n" +
        "       t.cid                                                                                  ma_dinh_danh,\n" +
        "       t.company_name                                                                         ten_co_quan,\n" +
        "       c.phone_number,\n" +
        "       c.email,\n" +
        "       (select name from ap_domain ap where type = 'TICKET_STATUS' and ap.code = t.status)    trang_thai_xl,\n" +
        "       (select first_name from jhi_user u where u.id = t.create_user)                         nguoi_xu_ly,\n" +
        "       to_char(t.create_datetime, 'dd/mm/yyyy HH24:mi:ss')                                    thoi_diem_tiep_nhan,\n" +
        "       t.STATUS,t.ticket_status ,ticket_request_status,1 p1,1 p2,confirm_datetime , confirm_deadline , "+
        "  confirm_datetime-confirm_deadline p3,1 p6,1 p7,1 p8,1 p9,1 p10 \n" +
        "from ticket t,\n" +
        "     customer c, \n" +
        "     jhi_user ju " +
        "where t.customer_id = c.customer_id and ju.ID = t.CREATE_USER \n" +
        "  and status in (:status)\n" +
        "  and (:channel is null or t.chanel_type = :channel)\n" +
        "  and (:phone is null or c.phone_number like :phone)\n" +
        "  and (:emails is null or lower (c.email) like lower (:emails))\n" +
        "  and (:idCode is null or  lower (t.cid) like lower (:idCode))\n" +
        "  and (:receiveUser is null or  lower (ju.LOGIN) like lower (:receiveUser))\n" +
        "  and (:fromDate is null or  trunc(t.CREATE_DATETIME) >= TO_DATE(:fromDate,'dd/mm/yyyy'))\n" +
        "  and (:toDate is null or  trunc(t.CREATE_DATETIME) <= TO_DATE(:toDate, 'dd/mm/yyyy'))   order by (case when 1 in :status then t.TICKET_REQUEST_STATUS else t.TICKET_STATUS end) desc," +
        "   (case when 5 in :status then t.CONFIRM_DATETIME else t.CREATE_DATETIME end) asc  ",
        nativeQuery = true)
    List<Object[]> exportExcelTicket(@Param("channel") String channel,
                                     @Param("phone") String phone,
                                     @Param("emails") String emails,
                                     @Param("idCode") String idCode,
                                     @Param("receiveUser") String receiveUser,
                                     @Param("fromDate") String fromDate,
                                     @Param("toDate") String toDate,
                                     @Param("status") List<Long> status);


    @Query(value = " update ticket set STATUS =:status, CONFIRM_USER =:confirmUser, CONFIRM_DATETIME =:confirmDatetime, " +
        "SATISFIED =:satisfied " +
        " where TICKET_ID =:ticketId", nativeQuery = true)
    void updateTicket(@Param("status") String status, @Param("confirmUser") Long confirmUser, @Param("confirmDatetime") Instant confirmDatetime,
                      @Param("satisfied") String satisfied, @Param("ticketId") Long ticketId);
}
