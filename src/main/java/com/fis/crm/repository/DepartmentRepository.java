package com.fis.crm.repository;

import com.fis.crm.domain.Ticket;
import com.fis.crm.domain.Department;
import com.fis.crm.service.dto.DepartmentDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data SQL repository for the Department entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DepartmentRepository extends JpaRepository<Department, Long> {

    @Query(value = "select id department_id,name department_name,code department_code from option_set_value v\n" +
        "where v.option_set_id=(select s.option_set_id from option_set s where s.code='PHONG_BAN') \n" +
        "and v.status=1 order by v.ord", nativeQuery = true)
    List<Object[]> getListDepartmentsForCombobox();

    @Query(value = "select id department_id,name department_name,code department_code from option_set_value where id= :departmentId", nativeQuery = true)
    List<Object[]> findDepartmentByDepartmentId(@Param("departmentId") Long departmentId);
}
