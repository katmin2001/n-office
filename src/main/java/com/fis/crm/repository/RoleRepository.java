package com.fis.crm.repository;

import com.fis.crm.domain.Role;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@SuppressWarnings("unused")
@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    @Query(value = "select ID, NAME, total, CREATE_DATETIME, FIRST_NAME , STATUS from (select r.ID\n" +
        "     , r.NAME\n" +
        "     , (select count(*)\n" +
        "        from ROLE rl,\n" +
        "             JHI_USER_ROLE jur,\n" +
        "             JHI_USER ju\n" +
        "        where rl.id = jur.ROLE_ID\n" +
        "          and jur.USER_ID = ju.ID\n" +
        "          and rl.ID = r.id\n" +
        "          and rl.STATUS = '1'\n" +
        "          and ju.ACTIVATED = 1\n" +
        "          and jur.STATUS = '1') as total\n" +
        "     , r.CREATE_DATETIME\n" +
        "     , JU.FIRST_NAME\n" +
        "     , r.STATUS\n" +
        "from role r\n" +
        "         left join JHI_USER JU on r.CREATE_USER = JU.ID\n" +
        "    where 1 = 1 and (:NAME is null or lower(r.NAME) like :NAME escape '\\')\n" +
        "    and ( :FIRST_NAME is null or lower(JU.FIRST_NAME) like :FIRST_NAME)\n" +
        "    and (:CREATE_DATETIME is null or r.CREATE_DATETIME >= to_date(:CREATE_DATETIME, 'dd/MM/yyyy'))\n" +
        "    and (:CREATE_DATETIME is null or r.CREATE_DATETIME < to_date(:CREATE_DATETIME, 'dd/MM/yyyy') + 1)\n" +
        ") where 1 = 1\n" +
        "and (:total = -1 or total = :total)\n" +
        "order by CREATE_DATETIME desc",
        countQuery = "select count(*) from (select r.ID\n" +
            "     , r.NAME\n" +
            "     , (select count(*)\n" +
            "        from ROLE rl,\n" +
            "             JHI_USER_ROLE jur,\n" +
            "             JHI_USER ju\n" +
            "        where rl.id = jur.ROLE_ID\n" +
            "          and jur.USER_ID = ju.ID\n" +
            "          and rl.ID = r.id\n" +
            "          and rl.STATUS = '1'\n" +
            "          and ju.ACTIVATED = 1\n" +
            "          and jur.STATUS = '1') as total\n" +
            "     , r.CREATE_DATETIME\n" +
            "     , JU.FIRST_NAME\n" +
            "     , r.STATUS\n" +
            "from role r\n" +
            "         left join JHI_USER JU on r.CREATE_USER = JU.ID\n" +
            "    where 1 = 1 and (:NAME is null or lower(r.NAME) like :NAME escape '\\')\n" +
            "    and ( :FIRST_NAME is null or lower(JU.FIRST_NAME) like :FIRST_NAME)\n" +
            "    and (:CREATE_DATETIME is null or r.CREATE_DATETIME >= to_date(:CREATE_DATETIME, 'dd/MM/yyyy'))\n" +
            "    and (:CREATE_DATETIME is null or r.CREATE_DATETIME < to_date(:CREATE_DATETIME, 'dd/MM/yyyy') + 1)\n" +
            ") where 1 = 1\n" +
            "and (:total = -1 or total = :total)\n" +
            "order by CREATE_DATETIME desc",
        nativeQuery = true)
    Page<Object[]> getRole(@Param("NAME") String name, @Param("FIRST_NAME") String firstName,
                           @Param("CREATE_DATETIME") String createDate,
                           @Param("total") Long total,
                           Pageable pageable);

    Optional<Role> findByName(String name);

    @Query("select distinct r from Role r left join r.roleMenuItemList rmi on rmi.menuId = :menuId left join rmi.configMenuItem where r.id = :id and r.status = '1'")
    @EntityGraph(value = "graph.Role.roleMenuItemList")
    Optional<Role> getRoleMenuItemByRole(@Param("id") Long id, @Param("menuId") Long menuId);

    @Query(value = "select * from role where status=1", nativeQuery = true)
    List<Role> getAllRoleGroup();

    @Query(value = "select *\n" +
        "from role r\n" +
        "where status = 1\n" +
        "  and r.ID in (select jur.role_id from jhi_user_role jur where jur.user_id = :userId and status = 1)", nativeQuery = true)
    List<Role> getRoleGroupsOfUser(@Param("userId") Long userId);
}
