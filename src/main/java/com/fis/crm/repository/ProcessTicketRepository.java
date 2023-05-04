package com.fis.crm.repository;

import com.fis.crm.domain.ProccessTicket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data SQL repository for the TicketRequestAttachment entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProcessTicketRepository extends JpaRepository<ProccessTicket, Long> {

    @Query(value = "select process_ticket_id, ticket_request_id, content, u.LOGIN, create_datetime, (\n" +
        "select id from option_set_value v where instr(','||u.DEPARTMENTS||',',','||v.id||',')>0 and rownum=1 \n" +
        ") department_id" +
        " from process_ticket p join jhi_user u on p.create_user = u.id " +
        "where ticket_request_id =:ticketRequestId order by create_datetime desc ", nativeQuery = true)
    List<Object[]> getListHistoryProcessTickets(@Param("ticketRequestId") Long ticketRequestId);
}
