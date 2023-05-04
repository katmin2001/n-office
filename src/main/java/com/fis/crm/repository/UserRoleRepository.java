package com.fis.crm.repository;

import com.fis.crm.domain.UserRole;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@SuppressWarnings("unused")
@Repository
public interface UserRoleRepository extends JpaRepository<UserRole, Long> {


    @Query(value = "select ju.LOGIN, ju.FIRST_NAME, jur.ID " +
        "from ROLE r, " +
        "     ( select jur.USER_ID, jur.ROLE_ID , jur.ID from JHI_USER_ROLE jur where jur.STATUS = '1') jur, " +
        "     JHI_USER ju " +
        "where r.ID = jur.ROLE_ID " +
        "  and jur.USER_ID = ju.ID " +
        "  and r.ID = :ROLE_ID " +
        "  and r.STATUS = '1' " +
        "  and ju.ACTIVATED = 1 " +
        "  and (:SEARCH is null or lower(ju.LOGIN) like :SEARCH escape '\\'" +
        "    OR " +
        "       lower(ju.FIRST_NAME) like :SEARCH escape '\\') " +
        "order by ju.LOGIN asc",
        nativeQuery = true,
        countQuery = "select count(*) " +
            "from ROLE r, " +
            "     ( select jur.USER_ID, jur.ROLE_ID from JHI_USER_ROLE jur where jur.STATUS = '1' group by jur.USER_ID, jur.ROLE_ID) jur, " +
            "     JHI_USER ju " +
            "where r.ID = jur.ROLE_ID " +
            "  and jur.USER_ID = ju.ID " +
            "  and r.ID = :ROLE_ID " +
            "  and r.STATUS = '1' " +
            "  and ju.ACTIVATED = 1 " +
            "  and (:SEARCH is null or lower(ju.LOGIN) like :SEARCH escape '\\' " +
            "    OR " +
            "       lower(ju.FIRST_NAME) like :SEARCH escape '\\') ")
    Page<Object[]> findAllByRoleIdAndSearch(@Param("ROLE_ID") Long roleId, @Param("SEARCH") String search, Pageable pageable);

    @Query(value = "select *\n" +
        "from jhi_user_role jur\n" +
        "where status = 1\n" +
        "  and jur.role_id=:roleId and jur.user_id=:userId", nativeQuery = true)
    List<UserRole> findByUserIdAndRoleId(@Param("roleId") Long roleId,
                                         @Param("userId") Long userId);

    @Query(value = "delete from jhi_user_role jur where user_id=:userId", nativeQuery = true)
//    void deleteUserRoleByRoleIdAndUserId(@Param("roleId") Long roleId,
//                                         @Param("userId") Long userId);

    void deleteByUserId(@Param("userId") Long userId);

    List<UserRole> findByUserId(Long userId);

    List<UserRole> findByUserIdAndStatus(Long userId, String status);
}
