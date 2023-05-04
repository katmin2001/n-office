package com.fis.crm.repository;

import com.fis.crm.domain.Criteria;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@SuppressWarnings("unused")
@Repository
public interface CriteriaRepository extends JpaRepository<Criteria, Long> {

    @Query(value = "select c.ID id," +
        " c.CRITERIA_GROUP_ID criteriaGroupId," +
        " c.NAME name," +
        " c.SCORES scores," +
        " c.STATUS status," +
        " c.CREATE_DATETIME createDatetime," +
        " c.CREATE_USER createUser," +
        " c.UPDATE_DATETIME updateDatetime," +
        " c.UPDATE_USER updateUser\n" +
        "from CRITERIA c left join CRITERIA_DETAIL cd on c.ID = cd.CRITERIA_ID\n" +
        "where (:status is null or c.STATUS = :status)\n" +
        "  and (:criteriaGroupId = -1 or c.CRITERIA_GROUP_ID = :criteriaGroupId)\n" +
        "  and (:keyWord is null or upper(c.NAME) like '%' || upper(:keyWord) || '%' or upper(cd.CONTENT) like '%' || upper(:keyWord) || '%')\n" +
        "group by c.ID, c.CRITERIA_GROUP_ID, c.NAME, c.SCORES, c.STATUS, c.CREATE_DATETIME, c.CREATE_USER, c.UPDATE_DATETIME,\n" +
        "         c.UPDATE_USER\n" +
        "order by c.CREATE_DATETIME desc",
    countQuery = "select count(c.id)\n" +
        "from CRITERIA c left join CRITERIA_DETAIL cd on c.ID = cd.CRITERIA_ID\n" +
        "where (:status is null or c.STATUS = :status)\n" +
        "  and (:criteriaGroupId = -1 or c.CRITERIA_GROUP_ID = :criteriaGroupId)\n" +
        "  and (:keyWord is null or upper(c.NAME) like '%' || upper(:keyWord) || '%' or upper(cd.CONTENT) like '%' || upper(:keyWord) || '%')\n" +
        "group by c.ID, c.CRITERIA_GROUP_ID, c.NAME, c.SCORES, c.STATUS, c.CREATE_DATETIME, c.CREATE_USER, c.UPDATE_DATETIME,\n" +
        "         c.UPDATE_USER\n" +
        "order by c.CREATE_DATETIME desc",
    nativeQuery = true)
    Page<Object[]> search(@Param("status") String status,
                          @Param("criteriaGroupId") Long criteriaGroupId,
                          @Param("keyWord") String keyWord,
                          Pageable pageable);

    Optional<List<Criteria>> findByCriteriaGroupIdInAndStatus(List<Long> criteriaGroupId, String status);

    List<Criteria> findByCriteriaGroupIdAndStatus(Long criteriaGroupId, String status);

    @Query(value = "select id, name from criteria where status = '1'", nativeQuery = true)
    List<Object[]> loadCriteriaToCbx();

    @Query(value = "select * from criteria where CRITERIA_GROUP_ID = :id", nativeQuery = true)
    List<Criteria> findByCriteriaGroupId(@Param("id") Long id);

    @Query(value = "select count(c) from Criteria c where c.criteriaGroupId = :criteriaGroupId and c.name = :name and c.status = '1' and (:id = -1 or c.id <> :id)")
    Long checkExistsCriteria(@Param("criteriaGroupId") Long criteriaGroupId, @Param("name") String name, @Param("id") Integer id);
}
