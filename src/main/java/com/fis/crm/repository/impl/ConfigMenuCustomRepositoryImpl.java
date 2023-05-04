package com.fis.crm.repository.impl;

import com.fis.crm.commons.DataUtil;
import com.fis.crm.domain.User;
import com.fis.crm.repository.ConfigMenuCustomRepository;
import com.fis.crm.security.SecurityUtils;
import com.fis.crm.service.UserService;
import com.fis.crm.service.dto.MenuItemDTO;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Repository
public class ConfigMenuCustomRepositoryImpl implements ConfigMenuCustomRepository {
    private final EntityManager entityManager;

    private final UserService userService;

    public ConfigMenuCustomRepositoryImpl(EntityManager entityManager,UserService userService) {
        this.entityManager = entityManager;
        this.userService=userService;
    }

    @Override
    public List<MenuItemDTO> getMenuTreeLogin(String login) {
        User user = userService.findOneByLogin(SecurityUtils.getCurrentUserLogin().get()).get();
        String sql = "SELECT *\n" +
            "FROM (SELECT DISTINCT M.ID        AS ID,\n" +
            "                      0           AS LEVELs,\n" +
            "                      M.MENU_CODE AS MENU_ITEM_CODE,\n" +
            "                      M.MENU_NAME AS MENU_ITEM_NAME,\n" +
            "                      1           as IS_DEFAULT,\n" +
            "                      M.ORDER_INDEX,\n" +
            "                      NULL        AS MENU_ID,\n" +
            "                      M.STATUS,\n" +
            "                      M.DESCRIPTION,\n" +
            "                      M.UPDATE_TIME,\n" +
            "                      M.UPDATE_USER,\n" +
            "                      M.URL,\n" +
            "                      M.ICON,\n" +
            "                      NULL        AS PARENT_ID\n" +
            "      FROM CONFIG_MENU_ITEM cm\n" +
            "               LEFT JOIN CONFIG_MENU M on cm.MENU_ID = M.ID\n" +
            "      WHERE cm.STATUS = 1   and cm.type=1\n" +
            "        AND M.STATUS = 1\n" +
            "      ORDER BY M.ORDER_INDEX, M.MENU_NAME)\n" +
            "\n" +
            "UNION ALL\n" +
            "\n" +
            "SELECT *\n" +
            "FROM (SELECT distinct cm.ID AS ID,\n" +
            "                      level,\n" +
            "                      lpad(' ', 2 * level, ' ') || cm.MENU_ITEM_CODE,\n" +
            "                      cm.MENU_ITEM_NAME,\n" +
            "                      cm.IS_DEFAULT,\n" +
            "                      cm.ORDER_INDEX,\n" +
            "                      cm.MENU_ID,\n" +
            "                      cm.STATUS,\n" +
            "                      cm.DESCRIPTION,\n" +
            "                      cm.UPDATE_TIME,\n" +
            "                      cm.UPDATE_USER,\n" +
            "                      cm.URL,\n" +
            "                      cm.ICON,\n" +
            "                      cm.PARENT_ID\n" +
            "      FROM CONFIG_MENU_ITEM cm\n" +
            "               LEFT JOIN CONFIG_MENU M on cm.MENU_ID = M.ID\n" +
            "      WHERE M.STATUS = 1\n" +
            "        AND CM.STATUS = 1   and cm.type=1 \n" +
            "      START WITH PARENT_ID IS NULL\n" +
            "      CONNECT BY PRIOR cm.ID = cm.PARENT_ID\n" +
            "      ORDER SIBLINGS BY cm.ORDER_INDEX, cm.MENU_ITEM_NAME) cm\n" +
            "WHERE MENU_ID IN (SELECT distinct M.id\n" +
            "                  FROM CONFIG_MENU_ITEM cm\n" +
            "                           LEFT JOIN CONFIG_MENU M on cm.MENU_ID = M.ID\n" +
            "                  WHERE cm.STATUS = 1\n" +
            "                    AND M.STATUS = 1)\n" +
            "  AND STATUS = 1" +
            " and exists (select 1 from ROLE_MENU_ITEM rm where (rm.menu_item_id=cm.id or rm.menu_item_id=cm.parent_id)\n" +
            "            and rm.role_id in (select ur.role_id from JHI_USER_ROLE ur where user_id=:user_id ))";
        Query query = entityManager.createNativeQuery(sql);
        query.setParameter("user_id",user.getId());
        return this.convertRowToDTOList(query.getResultList());
    }

    private List<MenuItemDTO> convertRowToDTOList(List<Object[]> rows) {
        Map<Long, MenuItemDTO> menuMap = new HashMap<>();
        Map<Long, MenuItemDTO> childMenuMap = new HashMap<>();
        for (Object[] row : rows) {
            MenuItemDTO configMenuDTO = this.convertRowToMenuItem(row);
            if (configMenuDTO.getLevel() == 0) {
                menuMap.put(configMenuDTO.getId(), configMenuDTO);
                continue;
            } else {
                childMenuMap.put(configMenuDTO.getId(), configMenuDTO);
            }
            if (configMenuDTO.getParentId() != null && childMenuMap.get(configMenuDTO.getParentId()) != null) {
                childMenuMap.get(configMenuDTO.getParentId()).addChild(configMenuDTO);
            } else if (configMenuDTO.getMenuId() != null && menuMap.get(configMenuDTO.getMenuId()) != null) {
                menuMap.get(configMenuDTO.getMenuId()).addChild(configMenuDTO);
            }
        }
        menuMap.values().forEach(menuItemDTO -> {
            if (!DataUtil.isNullOrEmpty(menuItemDTO.getChildren())) {
                menuItemDTO.setType("sub");
            }
        });
        childMenuMap.values().forEach(configMenuDTO -> {
            if (configMenuDTO.getChildren() != null && configMenuDTO.getChildren().size() > 0) {
                configMenuDTO.setType("sub");
            } else {
                configMenuDTO.setType("link");
            }
        });
        return menuMap.values().stream()
            .sorted((o1, o2) -> {
                if (o1.getOrderIndex() != null && o2.getOrderIndex() != null) {
                    return o1.getOrderIndex().compareTo(o2.getOrderIndex());
                } else return 0;
            })
            .collect(Collectors.toList());
    }

    private MenuItemDTO convertRowToMenuItem(Object[] row) {
        MenuItemDTO menuItemDTO = new MenuItemDTO();
        menuItemDTO.setId(((BigDecimal) row[0]).longValue());
        menuItemDTO.setLevel(row[1] != null ? ((BigDecimal) row[1]).intValue() : null);
        menuItemDTO.setName((String) row[3]);
        menuItemDTO.setIsDefault(row[4] != null ? ((BigDecimal) row[4]).intValue() : null);
        menuItemDTO.setOrderIndex(row[5] != null ? ((BigDecimal) row[5]).intValue() : null);
        menuItemDTO.setMenuId(row[6] != null ? ((BigDecimal) row[6]).longValue() : null);
        menuItemDTO.setRoute((String) row[11]);
        menuItemDTO.setIcon((String) row[12]);
        menuItemDTO.setParentId(row[13] != null ? ((BigDecimal) row[13]).longValue() : null);
        return menuItemDTO;
    }
}
