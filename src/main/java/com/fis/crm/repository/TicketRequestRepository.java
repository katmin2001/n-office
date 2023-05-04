package com.fis.crm.repository;

import com.fis.crm.domain.Ticket;
import com.fis.crm.domain.TicketRequest;
import com.fis.crm.service.dto.TicketRequestDTO;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data SQL repository for the TicketRequest entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TicketRequestRepository extends JpaRepository<TicketRequest, Long> {

    @Query(value = "select no from (select * from ticket_request where ticket_id=:ticketId " +
        "and ticket_request.no is not null order by ticket_request.no desc) where rownum <= 1", nativeQuery = true)
    List<Object[]> getMaxNo(@Param("ticketId") Long ticketId);


/*    @Query(value = " select * from ticket_request a where a.ticket_id =:ticketId ", nativeQuery = true)
    List<TicketRequest> getTicketRequestByTicketId(@Param("ticketId") Long ticketId);*/
    @Query(value = " select  a.ticket_request_id,\n" +
        " a.ticket_id,\n" +
        " a.ticket_request_code,\n" +
        " a.request_type,\n" +
        " a.bussiness_type,\n" +
        " a.priority,\n" +
        " a.department_id,\n" +
        " a.status,\n" +
        " a.deadline,\n" +
        " a.content,\n" +
        " a.confirm_datetime,\n" +
        " a.time_notify,\n" +
        " a.create_user,\n" +
        " a.update_user,\n" +
        " a.create_datetime,\n" +
        " a.UPDATE_DATIME,\n" +
        " a.no, b.login, a.create_departments from ticket_request a left join JHI_USER b on a.CREATE_USER = b.ID where a.ticket_id =:ticketId ", nativeQuery = true)
    List<Object[]> getTicketRequestByTicketId(@Param("ticketId") Long ticketId);

    List<TicketRequest> findByTicketId(Long ticketId);

}
