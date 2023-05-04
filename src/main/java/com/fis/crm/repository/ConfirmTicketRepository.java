package com.fis.crm.repository;

import com.fis.crm.domain.ConfirmTicket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data SQL repository for the ConfirmTicket entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ConfirmTicketRepository extends JpaRepository<ConfirmTicket, Long> {

    @Query(value = " select confirm_ticket_id, ticket_id, content, u.LOGIN, create_datetime, (\n" +
        "select id from option_set_value v where instr(','||u.DEPARTMENTS||',',','||v.id||',')>0 and rownum=1 \n" +
        ") department_id " +
        " from confirm_ticket c join jhi_user u on c.create_user = u.id where ticket_id =:ticketId order by create_datetime desc ", nativeQuery = true)
    List<Object[]> getListHistoryConfirmTickets(@Param("ticketId") Long ticketId);

    List<ConfirmTicket> getAllByTicketId(Long ticketId);

}
