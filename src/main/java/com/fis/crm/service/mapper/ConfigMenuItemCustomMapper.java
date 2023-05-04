package com.fis.crm.service.mapper;

import com.fis.crm.commons.DataUtil;
import com.fis.crm.domain.ConfigMenu;
import com.fis.crm.domain.ConfigMenuItem;
import com.fis.crm.service.dto.FunctionItemDTO;
import com.fis.crm.service.dto.MenuItemDTO;
import org.springframework.stereotype.Service;

@Service
public class ConfigMenuItemCustomMapper {

    public MenuItemDTO convertConfigMenuToMenuItem(ConfigMenu configMenu) {
        MenuItemDTO menuItemDTO = new MenuItemDTO();
        menuItemDTO.setName(configMenu.getMenuName());
        menuItemDTO.setOrderIndex(configMenu.getOrderIndex() == null ? 100 : configMenu.getOrderIndex());
        menuItemDTO.setRoute(configMenu.getUrl());
        menuItemDTO.setIcon(configMenu.getIcon());
        menuItemDTO.setParentId(null);
        menuItemDTO.setMenuId(configMenu.getId());
        menuItemDTO.setType("sub");
        menuItemDTO.setCode(configMenu.getMenuCode());
        return menuItemDTO;
    }

    public MenuItemDTO convertConfigMenuItemToMenuItem(ConfigMenuItem configMenuItem) {
        MenuItemDTO menuItemDTO = new MenuItemDTO();
        menuItemDTO.setId(configMenuItem.getId());
        menuItemDTO.setName(configMenuItem.getMenuItemName());
        menuItemDTO.setOrderIndex(configMenuItem.getOrderIndex() == null ? 100 : configMenuItem.getOrderIndex());
        menuItemDTO.setMenuId(configMenuItem.getMenuId());
        menuItemDTO.setRoute(configMenuItem.getUrl());
        menuItemDTO.setIcon(configMenuItem.getIcon());
        menuItemDTO.setParentId(configMenuItem.getParentId());
        menuItemDTO.setTmp(configMenuItem.getType());
        menuItemDTO.setCode(configMenuItem.getMenuItemCode());
        menuItemDTO.setType("link");
        return menuItemDTO;
    }

    public FunctionItemDTO convertConfigMenuItemToFunctionItem(ConfigMenuItem configMenuItem) {
        FunctionItemDTO functionItemDTO = new FunctionItemDTO();
        functionItemDTO.setId(configMenuItem.getId());
        functionItemDTO.setName(configMenuItem.getMenuItemName());
        functionItemDTO.setParentId(configMenuItem.getParentId());
        functionItemDTO.setType(configMenuItem.getType());
        functionItemDTO.setMenuId(configMenuItem.getMenuId());
        functionItemDTO.setOrderIndex(configMenuItem.getOrderIndex() == null ? 100: configMenuItem.getOrderIndex());
        functionItemDTO.setCode(configMenuItem.getMenuItemCode());
        return functionItemDTO;
    }

    public FunctionItemDTO convertRowToMenuItem(Object[] row) {
        FunctionItemDTO functionItemDTO = new FunctionItemDTO();
        functionItemDTO.setId(DataUtil.safeToLong(row[0]));
        functionItemDTO.setName(DataUtil.safeToString(row[1]));
        functionItemDTO.setParentId(DataUtil.safeToLong(row[2]));
        functionItemDTO.setType(DataUtil.safeToLong(row[3]));
        functionItemDTO.setChecked(DataUtil.safeToLong(row[4]) == null ? false : true);
        functionItemDTO.setMenuId(DataUtil.safeToLong(row[5]));
        functionItemDTO.setOrderIndex(DataUtil.safeToInt(row[6], 100));
        functionItemDTO.setRoleMenuItemId(DataUtil.safeToLong(row[7]));
        return functionItemDTO;
    }

    public FunctionItemDTO convertToMenuItem(Object[] row) {
        FunctionItemDTO functionItemDTO = new FunctionItemDTO();
        functionItemDTO.setId(DataUtil.safeToLong(row[0]));
        functionItemDTO.setName(DataUtil.safeToString(row[1]));
        functionItemDTO.setParentId(DataUtil.safeToLong(row[2]));
        functionItemDTO.setType(DataUtil.safeToLong(row[3]));
        functionItemDTO.setOrderIndex(DataUtil.safeToInt(row[4], 100));
        functionItemDTO.setStatus(DataUtil.safeToInt(row[5]));
        return functionItemDTO;
    }
}
