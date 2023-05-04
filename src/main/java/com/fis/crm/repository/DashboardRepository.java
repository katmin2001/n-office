package com.fis.crm.repository;

import com.fis.crm.domain.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data SQL repository for the Customer entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DashboardRepository extends JpaRepository<Customer, Long> {
    @Query(value = "select TYPE, total_day, total_month from dashboard_summary where \n" +
        " (:type IS NULL OR type = :type) \n" +
        " AND issue_date=trunc(to_date(:viewDate, 'yyyyMMdd'))", nativeQuery = true)
    List<Object[]> getSummary(@Param("type") String type, @Param("viewDate") String date);

    @Query(value = "select m.days sum_month,chua_xu_ly,dang_xu_ly,da_xu_ly,xac_nhan_kh,hoan_thanh from day_in_month m left join \n" +
        "(select to_char(sum_month,'dd') sum_month,sum(status_new) chua_xu_ly,\n" +
        "        sum(status_processing) dang_xu_ly, sum(status_processed) da_xu_ly,sum(status_confirm) xac_nhan_kh,\n" +
        "        sum(status_close) hoan_thanh from ticket_summary   where 1=1\n" +
        "        and  trunc(sum_month,'MM') =trunc(to_date(:viewDate,'yyyyMMdd'),'MM')\n" +
        "        group by to_char(sum_month,'dd') )  on to_number(sum_month)=days\n" +
        "        where to_number(days)<=to_char(LAST_DAY(sysdate),'DD')\n" +
        "        order  by to_number(days) ", nativeQuery = true)
    List<Object[]> getSuVuHeThong(@Param("viewDate") String date);

    @Query(value = "select d.id dept_id,d.name dept_name,chua_xu_ly,hoan_thanh from \n" +
        "(select id ,name  from option_set_value v\n" +
        "        where v.option_set_id=(select s.option_set_id from option_set s where s.code='PHONG_BAN') \n" +
        "        and v.status=1) d left join \n" +
        " (select  dept_id,dept_name, sum(status_new) chua_xu_ly,  sum(status_close) hoan_thanh\n" +
        "         from ticket_process_summary where 1=1\n" +
        "         and sum_month = trunc(to_date(:viewDate,'yyyyMMdd'),'MM')\n" +
        "         group by dept_id,dept_name ) s on d.id=s.dept_id\n" +
        "          order by d.id ", nativeQuery = true)
    List<Object[]> getReceivedAssignedDept(@Param("viewDate") String date);

    @Query(value = "select d.name dept_name,nvl(total,0) total from (select id ,name  from option_set_value v\n" +
        "                where v.option_set_id=(select s.option_set_id from option_set s where s.code='PHONG_BAN') \n" +
        "                and v.status=1) d left join \n" +
        "(select dept_id,sum(status_new+status_close) total from ticket_process_summary where 1=1\n" +
        "         and sum_month=trunc(to_date(:viewDate,'yyyyMMdd'),'MM')\n" +
        "         group by dept_id) s on d.id=s.dept_id", nativeQuery = true)
    List<Object[]> getDeptPercent(@Param("viewDate") String date);

    @Query(value = " select d.name bussiness_type_name,nvl(total,0) total from (select id ,name  from option_set_value v\n" +
        "                where v.option_set_id=(select s.option_set_id from option_set s where s.code='LOAI_NGHIEP_VU') \n" +
        "                and v.status=1) d left join         \n" +
        "(select bussiness_type_id, sum(status_new+status_close) total from ticket_process_summary where 1=1\n" +
        "        and sum_month=trunc(to_date(:viewDate,'yyyyMMdd'),'MM')\n" +
        "         group by bussiness_type_id) s   on d.id=s.bussiness_type_id", nativeQuery = true)
    List<Object[]> getBusinessTypePercent(@Param("viewDate") String date);

    @Query(value = "select d.name group_name ,nvl(total,0) total from (select id ,name  from group_user where status=1) d left join         \n" +
        "(select group_id,sum(total) total from outgoing_summary where 1=1\n" +
        "         AND sum_month=trunc(to_date(:viewDate,'yyyyMMdd'),'MM')\n" +
        "         group by group_id) s   on d.id=s.group_id ", nativeQuery = true)
    List<Object[]> getCallOutGroup(@Param("viewDate") String date);


    @Query(value = "select 'T '||m.days sum_month,da_danh_gia,chua_danh_gia from day_in_month m left join \n" +
        "(select to_char(sum_month,'MM') sum_month,sum(evaluated) da_danh_gia,sum(non_evaluated) chua_danh_gia \n" +
        "         from evaluate_summary where type =  ? \n" +
        "         and  to_char(sum_month,'YYYY') = to_char(to_date(?,'yyyyMMdd'),'YYYY') \n" +
        "         group by to_char(sum_month,'MM') ) s on m.days=to_number(s.sum_month)\n" +
        "         where m.days<=12 order by to_number(m.days) ", nativeQuery = true)
    List<Object[]> callOutInEvaluateSummary(Integer type, String date);

    @Query(value = "select 'T '||m.days sum_month,send_Success,send_error,total from day_in_month m left join \n" +
        "        (select to_char(sum_month,'MM') sum_month,sum(send_Success) send_Success,sum(send_error) send_error,\n" +
        "        sum(send_Success+send_error) total \n" +
        "                 from email_sms_summary where type =  ? \n" +
        "                 and  to_char(sum_month,'YYYY') = to_char(to_date(?,'yyyyMMdd'),'YYYY') \n" +
        "                 group by to_char(sum_month,'MM') ) s on m.days=to_number(s.sum_month)\n" +
        "                 where m.days<=12 order by to_number(m.days)", nativeQuery = true)
    List<Object[]> emailSmsMarketingSummary(Integer type, String date);

}
