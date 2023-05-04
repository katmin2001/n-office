package com.fis.crm.repository;

import com.fis.crm.domain.ActionLog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@SuppressWarnings("unused")
@Repository
public interface ActionLogRepository extends JpaRepository<ActionLog, Long> {

    @Query(value = "select cm.MENU_NAME, cmi.MENU_ITEM_NAME, ju.LOGIN, al.NOTE, at.NAME, al.CREATE_DATETIME " +
        "from ACTION_LOG al " +
        "   , CONFIG_MENU cm " +
        "   , CONFIG_MENU_ITEM cmi " +
        "   , JHI_USER ju " +
        "   , ACTION_TYPE at " +
        "where al.MENU_ID = cm.ID " +
        "  and al.MENU_ITEM_ID = cmi.ID " +
        "  and al.USER_ID = ju.ID " +
        "  and at.ID = al.ACTION_TYPE " +
        "and (:menuId = -1 or al.MENU_ID = :menuId) " +
        "and (:menuItemId = -1 or al.MENU_ITEM_ID = :menuItemId) " +
        "and (:actionType = '-1' or al.ACTION_TYPE = :actionType) " +
        "and (:userId = -1 or al.USER_ID = :userId) " +
        "and (:startDate is null or al.CREATE_DATETIME >= to_date(:startDate, 'dd/MM/yyyy')) " +
        "and (:endDate is null or al.CREATE_DATETIME < to_date(:endDate, 'dd/MM/yyyy') + 1) " +
        "and (:note is null or lower(al.note) like :note escape '\\') " +
        "order by al.CREATE_DATETIME desc",
        nativeQuery = true,
        countQuery = "select count(*) " +
            "from ACTION_LOG al " +
            "   , CONFIG_MENU cm " +
            "   , CONFIG_MENU_ITEM cmi " +
            "   , JHI_USER ju " +
            "   , ACTION_TYPE at " +
            "where al.MENU_ID = cm.ID " +
            "  and al.MENU_ITEM_ID = cmi.ID " +
            "  and al.USER_ID = ju.ID " +
            "  and at.ID = al.ACTION_TYPE " +
            "and (:menuId = -1 or al.MENU_ID = :menuId) " +
            "and (:menuItemId = -1 or al.MENU_ITEM_ID = :menuItemId) " +
            "and (:actionType = '-1' or al.ACTION_TYPE = :actionType) " +
            "and (:userId = -1 or al.USER_ID = :userId) " +
            "and (:startDate is null or al.CREATE_DATETIME >= to_date(:startDate, 'dd/MM/yyyy')) " +
            "and (:endDate is null or al.CREATE_DATETIME < to_date(:endDate, 'dd/MM/yyyy') + 1) " +
            "and (:note is null or lower(al.note) like :note escape '\\')")
    Page<Object[]> getActionLog(@Param("menuId") Long menuId, @Param("menuItemId") Long menuItemId,
                                @Param("actionType") String actionType,
                                @Param("userId") Long userId,
                                @Param("startDate") String startDate,
                                @Param("endDate") String endDate,
                                @Param("note") String note, Pageable pageable);

    @Query(value = "select cm.MENU_NAME, cmi.MENU_ITEM_NAME, ju.LOGIN, al.NOTE, at.NAME, to_char(al.CREATE_DATETIME, 'dd/MM/yyyy HH24:mi:ss') " +
        "from ACTION_LOG al " +
        "   , CONFIG_MENU cm " +
        "   , CONFIG_MENU_ITEM cmi " +
        "   , JHI_USER ju " +
        "   , ACTION_TYPE at " +
        "where al.MENU_ID = cm.ID " +
        "  and al.MENU_ITEM_ID = cmi.ID " +
        "  and al.USER_ID = ju.ID " +
        "  and at.ID = al.ACTION_TYPE " +
        "and (:menuId = -1 or al.MENU_ID = :menuId) " +
        "and (:menuItemId = -1 or al.MENU_ITEM_ID = :menuItemId) " +
        "and (:actionType = '-1' or al.ACTION_TYPE = :actionType) " +
        "and (:userId = -1 or al.USER_ID = :userId) " +
        "and (:startDate is null or al.CREATE_DATETIME >= to_date(:startDate, 'dd/MM/yyyy')) " +
        "and (:endDate is null or al.CREATE_DATETIME < to_date(:endDate, 'dd/MM/yyyy') + 1) " +
        "and (:note is null or lower(al.note) like :note escape '\\') " +
        "order by al.CREATE_DATETIME desc",
        nativeQuery = true)
    List<Object[]> getAllActionLog(@Param("menuId") Long menuId, @Param("menuItemId") Long menuItemId,
                                @Param("actionType") String actionType,
                                @Param("userId") Long userId,
                                @Param("startDate") String startDate,
                                @Param("endDate") String endDate,
                                @Param("note") String note);
}
