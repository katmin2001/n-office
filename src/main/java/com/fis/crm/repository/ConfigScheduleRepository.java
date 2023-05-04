package com.fis.crm.repository;

import com.fis.crm.domain.ConfigSchedule;
import com.fis.crm.service.dto.ConfigScheduleSearch;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data  repository for the ConfigSchedule entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ConfigScheduleRepository extends JpaRepository<ConfigSchedule, Long> {
    @Query(value = "select * from config_schedule  where request_type = ?1 and bussiness_type = ?2", nativeQuery = true)
    List<ConfigSchedule> findOneByRequestTypeAndBussinessType(Long requestType, Long bussinessType);

    @Query(value = "SELECT\n" +
        "t1.BUSSINESSTYPE, t1.REQUESTTYPE, t1.PROCESSTIME, t1.CONFIRMTIME\n" +
        "FROM\n" +
        "(\n" +
        "SELECT\n" +
        "*\n" +
        "FROM\n" +
        "(\n" +
        "SELECT\n" +
        "cs.BUSSINESS_TYPE bussinessType,\n" +
        "cs.REQUEST_TYPE requestType,\n" +
        "(CASE\n" +
        "WHEN cs.REQUEST_TYPE = 1 THEN 'Thắc mắc'\n" +
        "WHEN cs.REQUEST_TYPE = 2 THEN 'Yêu cầu'\n" +
        "WHEN cs.REQUEST_TYPE = 3 THEN 'Khiếu nại'\n" +
        "END) AS requestTypeName,\n" +
        "(CASE\n" +
        "WHEN cs.BUSSINESS_TYPE = 1 THEN 'Xử lý'\n" +
        "WHEN cs.BUSSINESS_TYPE = 2 THEN 'Gia hạn'\n" +
        "WHEN cs.BUSSINESS_TYPE = 3 THEN 'Phân quyền'\n" +
        "END) AS bussinessTypeName,\n" +
        "cs.PROCESS_TIME processTime,\n" +
        "cs.CONFIRM_TIME confirmTime\n" +
        "FROM\n" +
        "CONFIG_SCHEDULE cs)) t1\n" +
        "WHERE\n" +
        "UPPER(t1.requestTypeName) LIKE CONCAT('%',:keySearch,'%') OR UPPER(bussinessTypeName) LIKE UPPER(:keySearch%)", nativeQuery = true)
    Page<ConfigSchedule> findConfigSchedule(@Param("keySearch") String keySearch, Pageable pageable);

//    @Query(value = "select new ConfigScheduleSearch(a.id, a.requestType, b.name, a.bussinessType, c.name, a.processTime, " +
//        "a.processTimeType, a.confirmTime, a.confirmTimeType, a.status) from ConfigSchedule a " +
//        " left join ApDomain b on a.requestType = b.code and b.type = 'REQUEST_TYPE' " +
//        " left join BussinessType c on a.bussinessType = c.id and c.status = 1 " +
//        " where 1=1 " +
//        " and (:keyword is null or lower(b.name) like :keyword escape '\\' or lower(c.name) like :keyword escape '\\' or lower(a.processTime) like :keyword escape '\\' or lower(a.confirmTime) like :keyword escape '\\') ",
//        countQuery = "select count(a) from ConfigSchedule a " +
//            " left join ApDomain b on a.requestType = b.code and b.type = 'REQUEST_TYPE' " +
//            " left join BussinessType c on a.bussinessType = c.id and c.status = 1 " +
//            " where 1=1 " +
//            " and (:keyword is null or lower(b.name) like :keyword escape '\\' or lower(c.name) like :keyword escape '\\' or lower(a.processTime) like :keyword escape '\\' or lower(a.confirmTime) like :keyword escape '\\') ")
//    Page<ConfigScheduleSearch> onSearchConfigSchedule(@Param("keyword") String keyword, Pageable pageable);

    @Query(value = "select cs.id,\n" +
        "       cs.request_type,\n" +
        "       (select name from option_set_value osv where osv.id = cs.request_type)   requestTypeStr,\n" +
        "       cs.bussiness_type,\n" +
        "       (select name from option_set_value osv where osv.id = cs.bussiness_type) bussinessTypeStr,\n" +
        "       cs.process_time,\n" +
        "       cs.process_time_type,\n" +
        "       cs.confirm_time,\n" +
        "       cs.confirm_time_type,\n" +
        "       cs.status\n" +
        "from config_schedule cs\n" +
        "where (cs.request_type in\n" +
        "       (select osv.id\n" +
        "        from option_set_value osv,\n" +
        "             option_set os\n" +
        "        where LOWER(osv.name) like :search \n" +
        "          and osv.OPTION_SET_ID = os.OPTION_SET_ID\n" +
        "          and os.code = 'LOAI_YEU_CAU'))\n" +
        "   or (cs.BUSSINESS_TYPE in\n" +
        "       (select osv.id\n" +
        "        from option_set_value osv,\n" +
        "             option_set os\n" +
        "        where LOWER(osv.name) like :search \n" +
        "          and osv.OPTION_SET_ID = os.OPTION_SET_ID\n" +
        "          and os.code = 'LOAI_NGHIEP_VU'))",
        countQuery = "select count(1)\n" +
            "from config_schedule cs\n" +
            "where (cs.request_type in\n" +
            "       (select osv.id\n" +
            "        from option_set_value osv,\n" +
            "             option_set os\n" +
            "        where LOWER(osv.name) like :search \n" +
            "          and osv.OPTION_SET_ID = os.OPTION_SET_ID\n" +
            "          and os.code = 'LOAI_YEU_CAU'))\n" +
            "   or (cs.BUSSINESS_TYPE in\n" +
            "       (select osv.id\n" +
            "        from option_set_value osv,\n" +
            "             option_set os\n" +
            "        where LOWER(osv.name) like :search \n" +
            "          and osv.OPTION_SET_ID = os.OPTION_SET_ID\n" +
            "          and os.code = 'LOAI_NGHIEP_VU'))\n"
        , nativeQuery = true)
    Page<Object[]> search(@Param("search") String search, Pageable pageable);

    @Query(value = "select to_char(sysdate,'DD/MM/YYYY HH24:mi:ss') current_date, process_time, process_time_type " +
        "from config_schedule " +
        "where request_type = to_number(:requestType) and bussiness_type =to_number(:bussinessType) and status = 1", nativeQuery = true)
    List<Object[]> getProcessTime(@Param("requestType") Long requestType, @Param("bussinessType") Long bussinessType);

    @Query(value = "select\n" +
        "       cs.id, \n" +
        "       cs.request_type,\n" +
        "       (select name from option_set_value osv where osv.id = cs.request_type) requestTypeStr,\n" +
        "       cs.bussiness_type,\n" +
        "       (select name from option_set_value osv where osv.id = cs.bussiness_type) bussinessTypeStr,\n" +
        "       cs.process_time,\n" +
        "       cs.process_time_type,\n" +
        "       cs.confirm_time,\n" +
        "       cs.confirm_time_type,\n" +
        "       cs.status\n" +
        "from config_schedule cs " +
        "where (cs.request_type in\n" +
        "       (select osv.id\n" +
        "        from option_set_value osv,\n" +
        "             option_set os\n" +
        "        where \n" +
        "          osv.OPTION_SET_ID = os.OPTION_SET_ID\n" +
        "          and os.code = 'LOAI_YEU_CAU'))\n" +
        "   and (cs.BUSSINESS_TYPE in\n" +
        "       (select osv.id\n" +
        "        from option_set_value osv,\n" +
        "             option_set os\n" +
        "        where \n" +
        "          osv.OPTION_SET_ID = os.OPTION_SET_ID\n" +
        "          and os.code = 'LOAI_NGHIEP_VU'))\n",
        countQuery = "select count(1) from config_schedule cs " +
            "where (cs.request_type in\n" +
            "       (select osv.id\n" +
            "        from option_set_value osv,\n" +
            "             option_set os\n" +
            "        where \n" +
            "          osv.OPTION_SET_ID = os.OPTION_SET_ID\n" +
            "          and os.code = 'LOAI_YEU_CAU'))\n" +
            "   and (cs.BUSSINESS_TYPE in\n" +
            "       (select osv.id\n" +
            "        from option_set_value osv,\n" +
            "             option_set os\n" +
            "        where \n" +
            "          osv.OPTION_SET_ID = os.OPTION_SET_ID\n" +
            "          and os.code = 'LOAI_NGHIEP_VU'))\n",
        nativeQuery = true)
    Page<Object[]> getAllConfigSchedule(Pageable pageable);

    @Query(value = "select new ConfigScheduleSearch(a.id, a.requestType, b.name, a.bussinessType, c.name, a.processTime, " +
        "a.processTimeType, a.confirmTime, a.confirmTimeType, a.status) from ConfigSchedule a " +
        " left join ApDomain b on a.requestType = b.code and b.type = 'REQUEST_TYPE' " +
        " left join BussinessType c on a.bussinessType = c.id and c.status = 1 " +
        " where 1=1 " +
        " and (:keyword is null or lower(b.name) like :keyword escape '\\' or lower(c.name) like :keyword escape '\\' or lower(a.processTime) like :keyword escape '\\' or lower(a.confirmTime) like :keyword escape '\\') ")
    List<ConfigScheduleSearch> export(@Param("keyword") String keyword);

    @Query(value = "select cs.id,\n" +
        "       cs.request_type,\n" +
        "       (select name from option_set_value osv where osv.id = cs.request_type)   requestTypeStr,\n" +
        "       cs.bussiness_type,\n" +
        "       (select name from option_set_value osv where osv.id = cs.bussiness_type) bussinessTypeStr,\n" +
        "       cs.process_time,\n" +
        "       cs.process_time_type,\n" +
        "       cs.confirm_time,\n" +
        "       cs.confirm_time_type,\n" +
        "       cs.status\n" +
        "from config_schedule cs\n" +
        "where (cs.request_type in\n" +
        "       (select osv.id\n" +
        "        from option_set_value osv,\n" +
        "             option_set os\n" +
        "        where osv.name like :search \n" +
        "          and osv.OPTION_SET_ID = os.OPTION_SET_ID\n" +
        "          and os.code = 'LOAI_YEU_CAU'))\n" +
        "   or (cs.BUSSINESS_TYPE in\n" +
        "       (select osv.id\n" +
        "        from option_set_value osv,\n" +
        "             option_set os\n" +
        "        where osv.name like :search \n" +
        "          and osv.OPTION_SET_ID = os.OPTION_SET_ID\n" +
        "          and os.code = 'LOAI_NGHIEP_VU'))", nativeQuery = true)
    List<Object[]> exportConfigSchedule(@Param("search") String search);

}
