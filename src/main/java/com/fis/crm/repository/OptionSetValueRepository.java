package com.fis.crm.repository;

import com.fis.crm.domain.OptionSetValue;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


/**
 * Spring Data  repository for the OptionSetValue entity.
 */
@SuppressWarnings("unused")
@Repository
public interface OptionSetValueRepository extends JpaRepository<OptionSetValue, Long> {
    @Query(value = " SELECT * FROM OPTION_SET_VALUE WHERE CODE = ?1 ", nativeQuery = true)
    Optional<OptionSetValue> findOptionSetValueByCode(String code);

    @Query(value = " SELECT * FROM OPTION_SET_VALUE WHERE ORD = ?1 ", nativeQuery = true)
    Optional<OptionSetValue> findOptSetValueByOrd(Integer ord);

    @Query(value = "SELECT * FROM OPTION_SET_VALUE WHERE CODE = ?1", nativeQuery = true)
    List<OptionSetValue> getByCode(String code);

    @Query(value = " select osv.ID,\n" +
        "       osv.CODE,\n" +
        "       osv.NAME,\n" +
        "       osv.ORD,\n" +
        "       osv.GROUP_NAME,\n" +
        "       osv.FROM_DATE,\n" +
        "       osv.END_DATE,\n" +
        "       osv.STATUS,\n" +
        "       osv.CREATE_DATE,\n" +
        "       userCreate.FIRST_NAME as createUserName,\n" +
        "       osv.UPDATE_DATE,\n" +
        "       updateUser.FIRST_NAME as updateUserName\n" +
        "from OPTION_SET_VALUE osv\n" +
        "         left join JHI_USER userCreate on osv.CREATE_USER = userCreate.ID\n" +
        "         left join JHI_USER updateUser on osv.UPDATE_USER = updateUser.ID\n" +
        "where 1 = 1\n" +
        "  and (:code is null or osv.CODE like :code)\n" +
        "  and (:keyName is null or osv.name like :keyName)\n" +
        "  and (:status is null or osv.STATUS = TO_NUMBER(:status))\n" +
        " and (:optionSetId is null or osv.option_set_id = :optionSetId)", nativeQuery = true)
    List<Object[]> getListData(@Param("code") String code, @Param("keyName") String keyName, @Param("status") Long status,
                               @Param("optionSetId") Long optionSetId);

    @Query(value = " select osv.ID,\n" +
        "       osv.CODE,\n" +
        "       osv.NAME,\n" +
        "       osv.ORD,\n" +
        "       osv.GROUP_NAME,\n" +
        "       osv.FROM_DATE,\n" +
        "       osv.END_DATE,\n" +
        "       osv.STATUS,\n" +
        "       osv.CREATE_DATE,\n" +
        "       userCreate.LOGIN as createUserName,\n" +
        "       osv.UPDATE_DATE,\n" +
        "       updateUser.LOGIN as updateUserName,\n" +
        "       osv.c1 as c1,\n" +
        "       osv.c2 as c2\n" +
        " from OPTION_SET_VALUE osv\n" +
        "         left join JHI_USER userCreate on osv.CREATE_USER = userCreate.ID\n" +
        "         left join JHI_USER updateUser on osv.UPDATE_USER = updateUser.ID\n" +
        " where 1 = 1  \n" +
        "  and (:optionSetId is null or osv.option_set_id = TO_NUMBER(:optionSetId)) \n" +
        "  and (:code is null or lower(osv.CODE) like :code)\n" +
        "  and (:keyName is null or lower(osv.NAME) like :keyName)\n" +
        "  and (:status is null or osv.STATUS = TO_NUMBER(:status)) order by osv.CREATE_DATE desc",
        countQuery = " select count (1)" +
            "from OPTION_SET_VALUE osv\n" +
            "         left join JHI_USER userCreate on osv.CREATE_USER = userCreate.ID\n" +
            "         left join JHI_USER updateUser on osv.UPDATE_USER = updateUser.ID \n" +
            " where 1 = 1  \n" +
            "  and (:optionSetId is null or osv.option_set_id = TO_NUMBER(:optionSetId)) \n" +
            "  and (:code is null or lower(osv.CODE) like :code)\n" +
            "  and (:keyName is null or lower(osv.NAME) like :keyName)\n" +
            "  and (:status is null or osv.STATUS = TO_NUMBER(:status))  order by osv.CREATE_DATE desc",
        nativeQuery = true)
    Page<Object[]> getListData(@Param("code") String code,
                               @Param("keyName") String keyName,
                               @Param("status") Long status,
                               @Param("optionSetId") Long optionSetId,
                               Pageable pageable);

    @Query(value = " SELECT osv FROM OptionSetValue osv WHERE osv.optionSetId = (select os.optionSetId from OptionSet os where os.code = :code) and osv.status = '1'")
    Optional<List<OptionSetValue>> findOptSetValueByOptionSetCode(@Param("code") String code);

    @Query(value = "select id,code,name " +
        "from option_set_value v " +
        "where v.option_set_id=(" +
        "select s.option_set_id from option_set s \n" +
        "where code='LOAI_NGHIEP_VU')  and status=1 ORDER BY V.ord", nativeQuery = true)
    List<Object[]> getListBusinessTypeCombobox();

    @Query(value = "select id,code,name " +
        "from option_set_value v " +
        "where v.option_set_id=(" +
        "select s.option_set_id from option_set s \n" +
        "where code='LOAI_YEU_CAU')  and status=1 ORDER BY V.ord", nativeQuery = true)
    List<Object[]> getListRequestTypeCombobox();

    @Query(value = "select id " +
        "from option_set_value v " +
        "where v.option_set_id=(" +
        "select s.option_set_id from option_set s \n" +
        "where code='LOAI_YEU_CAU') and v.code = upper(:code)  and status=1 ORDER BY V.ord", nativeQuery = true)
    Long getRequestTypeByCode(@Param("code") String code);

    @Query(value = "select id " +
        "from option_set_value v " +
        "where v.option_set_id=(" +
        "select s.option_set_id from option_set s \n" +
        "where code='LOAI_NGHIEP_VU') and v.code = upper(:code)  and status= 1 ORDER BY V.ord", nativeQuery = true)
    Long getBussinessTypeByCode(@Param("code") String code);

}
