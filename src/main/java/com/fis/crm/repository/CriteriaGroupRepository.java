package com.fis.crm.repository;

import com.fis.crm.domain.CriteriaGroup;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CriteriaGroupRepository extends JpaRepository<CriteriaGroup,Long> {

    @Query(value = "select * from criteria_group where lower(name) like lower(:name) and status = '1' order by update_datetime desc ", nativeQuery = true)
    CriteriaGroup findByName(@Param("name") String name);

    @Query(value = "select * from criteria_group where status = '1' order by update_datetime desc ", nativeQuery = true)
    Page<CriteriaGroup> findAll(Pageable pageable);

    @Query(value = "select * from criteria_group where status = '1' order by update_datetime desc ", nativeQuery = true)
    List<CriteriaGroup> findAllByStatus();

    Optional<List<CriteriaGroup>> findAllByStatus(String status);

    @Query(value = "select id, name from criteria_group where status = '1' order by NLSSORT(name, 'nls_sort = Vietnamese') ASC", nativeQuery = true)
    List<Object[]> loadCriteriaGroupToCbx();

    List<CriteriaGroup> findAllByOrderByUpdateDatetimeDesc();

    @Query(value = "select * from criteria_group cg where cg.name like :name or cg.scores = :score order by update_datetime desc " , nativeQuery = true)
    Page<CriteriaGroup> searchCriteriaGroup(@Param("name") String name, @Param("score") Double score, Pageable pageable);

}
