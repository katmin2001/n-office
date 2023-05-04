package com.fis.crm.repository;

import com.fis.crm.domain.ActionLog;
import com.fis.crm.domain.OptionSet;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


/**
 * Spring Data  repository for the ConfigSchedule entity.
 */
@SuppressWarnings("unused")
@Repository
public interface OptionSetRepository extends JpaRepository<OptionSet, Long> {
    @Query(value = " SELECT * FROM OPTION_SET WHERE CODE = ?1 ", nativeQuery = true)
    Optional<OptionSet> findOptionSetByCode(String code);

    @Query(value = " SELECT * FROM OPTION_SET WHERE CODE = ?1 ", nativeQuery = true)
    List<OptionSet> getByCode(String code);

    @Query(value = " SELECT " +
        " os.OPTION_SET_ID ," +
        " os.CODE ," +
        " os.NAME ," +
        " os.FROM_DATE ," +
        " os.END_DATE ," +
        " os.STATUS ," +
        " ju.LOGIN ," +
        " os.CREATE_DATE ," +
        " os.UPDATE_DATE ," +
        " uu.LOGIN updateUser," +
        " os.ENGLISH_NAME englishName " +
        " FROM " +
        " OPTION_SET os " +
        " LEFT JOIN JHI_USER ju ON ju.ID = os.CREATE_USER" +
        " LEFT JOIN JHI_USER uu ON uu.ID = os.UPDATE_USER  where 1=1 " +
        " AND (:code is null or lower(os.code) like :code) " +
        " AND (:name is null or lower(os.name) like :name) " +
        " AND (:status is null or os.status = :status) order by os.CREATE_DATE desc",
        countQuery = "select count (1)" +
            " FROM " +
            " OPTION_SET os " +
            " LEFT JOIN JHI_USER ju ON ju.ID = os.CREATE_USER " +
            " LEFT JOIN JHI_USER uu ON uu.ID = os.UPDATE_USER  " +
            " where 1=1 " +
            " AND (:code is null or lower(os.code) like :code) " +
            " AND (:name is null or lower(os.name) like :name) " +
            " AND (:status is null or os.status = :status)"
        , nativeQuery = true)
    Page<Object[]> search(@Param("code") String code,
                           @Param("name") String name,
                           @Param("status") String status,
                          Pageable pageable);
}
