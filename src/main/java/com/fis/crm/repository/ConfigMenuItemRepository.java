package com.fis.crm.repository;

import com.fis.crm.domain.ConfigMenuItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data SQL repository for the ConfigMenuItem entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ConfigMenuItemRepository extends JpaRepository<ConfigMenuItem, Long> {

    Optional<List<ConfigMenuItem>> findByMenuIdAndStatus(Long menuId, Integer status);

    @Query("select cmi from ConfigMenuItem cmi where lower(cmi.menuItemCode)  = :menuItemCode")
    Optional<ConfigMenuItem> findByMenuItemCode(@Param("menuItemCode") String menuItemCode);

    Optional<ConfigMenuItem> findByIdOrMenuItemCode(Long id, String menuItemCode);


    @Query("select distinct cmi from User u, UserRole ur, RoleMenuItem rmi, ConfigMenuItem cmi " +
        "where u.id = ur.user.id " +
        "  and ur.role.id = rmi.role.id " +
        "  and cmi.id = rmi.configMenuItem.id " +
        "  and cmi.status = 1 " +
        "  and ur.status = '1' " +
        "  and rmi.status = '1' " +
        "  and u.id = :id")
    List<ConfigMenuItem> findAllMenuItemByUser(@Param("id") Long id);

    @Query(value = "with tmp as (select rmi.MENU_ITEM_ID, rmi.ID " +
        "             from ROLE jur, " +
        "                  ROLE_MENU_ITEM rmi " +
        "             where jur.ID = rmi.ROLE_ID " +
        "               and jur.ID = :roleId " +
        "               and jur.STATUS = '1' " +
        "               and rmi.STATUS = '1' " +
        "               and rmi.MENU_ID = :menuId) " +
        "select cmi.id, cmi.MENU_ITEM_NAME, cmi.PARENT_ID, cmi.TYPE, tmp.MENU_ITEM_ID, cmi.MENU_ID, cmi.ORDER_INDEX, tmp.ID as ROLE_MENU_ITEM_ID " +
        "from CONFIG_MENU_ITEM cmi " +
        "         left join tmp on cmi.id = tmp.MENU_ITEM_ID " +
        "where cmi.STATUS = 1 " +
        "and cmi.MENU_ID = :menuId", nativeQuery = true)
    List<Object[]> findFunctionByMenuIdAndRole(@Param("roleId") Long roleId, @Param("menuId") Long menuId);

    @Query(value = "SELECT distinct cm.ID, " +
        "       cm.MENU_ITEM_NAME, " +
        "       cm.PARENT_ID, " +
        "       cm.TYPE, " +
        "       cm.ORDER_INDEX, " +
        "       cm.STATUS " +
        "FROM CONFIG_MENU_ITEM cm " +
        "WHERE CM.MENU_ID = :menuId " +
        "START WITH id in (select cmi.id from CONFIG_MENU_ITEM cmi where cmi.MENU_ID = :menuId and cmi.status = 0 and cmi.TYPE = 2) " +
        "CONNECT BY PRIOR cm.PARENT_ID = cm.ID",
        nativeQuery = true)
    List<Object[]> findFunctionInActiveByMenuId(@Param("menuId") Long menuId);

}
