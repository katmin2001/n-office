package com.fis.crm.service.impl;

import com.fis.crm.commons.DataUtil;
import com.fis.crm.config.Constants;
import com.fis.crm.domain.ConfigMenu;
import com.fis.crm.domain.ConfigMenuItem;
import com.fis.crm.repository.ConfigMenuItemRepository;
import com.fis.crm.repository.ConfigMenuRepository;
import com.fis.crm.service.ConfigMenuService;
import com.fis.crm.service.UserService;
import com.fis.crm.service.dto.ConfigMenuDTO;
import com.fis.crm.service.dto.FunctionItemDTO;
import com.fis.crm.service.dto.MenuItemDTO;
import com.fis.crm.service.mapper.ConfigMenuItemCustomMapper;
import com.fis.crm.service.mapper.ConfigMenuMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link ConfigMenu}.
 */
@Service
@Transactional
public class ConfigMenuServiceImpl implements ConfigMenuService {

    private final Logger log = LoggerFactory.getLogger(ConfigMenuServiceImpl.class);

    private final ConfigMenuRepository configMenuRepository;
    private final ConfigMenuItemRepository configMenuItemRepository;

    private final ConfigMenuMapper configMenuMapper;
    private final UserService userService;
    private final ConfigMenuItemCustomMapper configMenuItemCustomMapper;

    public ConfigMenuServiceImpl(ConfigMenuRepository configMenuRepository, ConfigMenuItemRepository configMenuItemRepository, ConfigMenuMapper configMenuMapper, UserService userService, ConfigMenuItemCustomMapper configMenuItemCustomMapper) {
        this.configMenuRepository = configMenuRepository;
        this.configMenuItemRepository = configMenuItemRepository;
        this.configMenuMapper = configMenuMapper;
        this.userService = userService;
        this.configMenuItemCustomMapper = configMenuItemCustomMapper;
    }

    @Override
    public ConfigMenuDTO save(ConfigMenuDTO configMenuDTO) {
        log.debug("Request to save ConfigMenu : {}", configMenuDTO);
        ConfigMenu configMenu = configMenuMapper.toEntity(configMenuDTO);
        configMenu = configMenuRepository.save(configMenu);
        return configMenuMapper.toDto(configMenu);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ConfigMenuDTO> findAll() {
        return configMenuRepository.findByStatusOrderByOrderIndex(Integer.parseInt(Constants.STATUS_ACTIVE)).map(configMenuMapper::toDto).get();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ConfigMenuDTO> findOne(Long id) {
        log.debug("Request to get ConfigMenu : {}", id);
        return configMenuRepository.findById(id).map(configMenuMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete ConfigMenu : {}", id);
        configMenuRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<MenuItemDTO> getMenuTreeLogin(String login) {
        Long userId = userService.getUserIdLogin();
        List<ConfigMenuItem> configMenuItems = configMenuItemRepository.findAllMenuItemByUser(userId);
        Set<Long> ids = configMenuItems.stream().map(ConfigMenuItem::getMenuId).collect(Collectors.toSet());
        List<ConfigMenu> configMenus = configMenuRepository.findByStatusAndIdInOrderByOrderIndex(Integer.parseInt(Constants.STATUS_ACTIVE), ids);

        List<MenuItemDTO> result = configMenus.stream().map(configMenu -> configMenuItemCustomMapper.convertConfigMenuToMenuItem(configMenu)).collect(Collectors.toList());
        List<MenuItemDTO> lstFunction = configMenuItems.stream().map(configMenuItem -> configMenuItemCustomMapper.convertConfigMenuItemToMenuItem(configMenuItem)).collect(Collectors.toList());
        buildChildrenMenu(lstFunction, result);
        return result;
    }

    @Override
    @Transactional(readOnly = true)
    public List<FunctionItemDTO> getFunctionByMenuAndRole(Long roleId, Long menuId) {
        List<FunctionItemDTO> lstFunction = configMenuItemRepository.findFunctionByMenuIdAndRole(roleId, menuId).stream().map(object
            -> configMenuItemCustomMapper.convertRowToMenuItem(object)).collect(Collectors.toList());

        List<FunctionItemDTO> lstResult = lstFunction.stream().sorted(Comparator.comparingLong(FunctionItemDTO::getOrderIndex)).filter(menuItemDTO -> menuItemDTO.getParentId() == null)
            .collect(Collectors.toList());
        lstFunction.removeAll(lstResult);
        buildFunctionMenu(lstFunction, lstResult);
        return  lstResult;
    }

    @Override
    public List<FunctionItemDTO> getConfigMenuItemByMenu(Long id) {
        List<FunctionItemDTO> lstFunction = configMenuItemRepository.findByMenuIdAndStatus(id, Integer.parseInt(Constants.STATUS_ACTIVE)).map(configMenuItems
            -> configMenuItems.stream().map(configMenuItem -> configMenuItemCustomMapper.convertConfigMenuItemToFunctionItem(configMenuItem)).collect(Collectors.toList())).get();
        List<FunctionItemDTO> lstResult = lstFunction.stream().sorted(Comparator.comparingLong(FunctionItemDTO::getOrderIndex)).filter(menuItemDTO -> menuItemDTO.getParentId() == null)
            .collect(Collectors.toList());
        lstFunction.removeAll(lstResult);
        buildFunctionMenu(lstFunction, lstResult);
        return lstResult;
    }

    @Override
    public List<FunctionItemDTO> getConfigMenuItemInActiveByMenu(Long id) {
        List<FunctionItemDTO> lstFunction = configMenuItemRepository.findFunctionInActiveByMenuId(id).stream().map(objects
            -> configMenuItemCustomMapper.convertToMenuItem(objects)).collect(Collectors.toList());

        List<FunctionItemDTO> lstResult = lstFunction.stream().sorted(Comparator.comparingLong(FunctionItemDTO::getOrderIndex)).filter(menuItemDTO -> menuItemDTO.getParentId() == null && Constants.STATUS_ACTIVE.equals(menuItemDTO.getStatus().toString()))
            .collect(Collectors.toList());
        lstFunction.removeAll(lstResult);
        buildFunctionMenu(lstFunction, lstResult);
        return lstResult;
    }

    public void buildFunctionMenu(List<FunctionItemDTO> lstFunction, List<FunctionItemDTO> lstMenu) {
        for (FunctionItemDTO menu : lstMenu) {
            List<FunctionItemDTO> lst = lstFunction.stream().sorted(Comparator.comparingLong(FunctionItemDTO::getOrderIndex)).filter(item -> {
                if(Objects.nonNull(item.getParentId()) && item.getParentId().equals(menu.getId())){
                    if( Long.valueOf(1).equals(item.getType())) {
                        return true;
                    } else {
                        if(menu.getRoles() == null) {
                            menu.setRoles(new ArrayList<>());
                        }
                        menu.getRoles().add(item);
                        return false;
                    }

                }
                return false;
            }).collect(Collectors.toList());
            if(Objects.nonNull(lst) && !lst.isEmpty()) {
                menu.setChildren(lst);
                lstFunction.removeAll(lst);
                if(!DataUtil.isNullOrEmpty(menu.getRoles())) {
                    lstFunction.removeAll(menu.getRoles());
                }
                buildFunctionMenu(lstFunction, menu.getChildren());
            }
        }
    }

    public void buildChildrenMenu(List<MenuItemDTO> lstFunction, List<MenuItemDTO> lstMenu) {
        for (MenuItemDTO menu : lstMenu) {
            List<MenuItemDTO> lst = lstFunction.stream().sorted(Comparator.comparingLong(MenuItemDTO::getOrderIndex)).filter(item -> {
                if((Objects.isNull(item.getParentId())  && Long.valueOf(1).equals(item.getTmp()) && item.getMenuId().equals(menu.getMenuId()))
                    || (Objects.nonNull(item.getParentId()) && item.getParentId().equals(menu.getId()))) {
                    if( Long.valueOf(1).equals(item.getTmp())) {
                        return true;
                    } else {
                        if(menu.getRoles() == null) {
                            menu.setRoles(new ArrayList<>());
                        }
                        menu.getRoles().add(item);
                        return false;
                    }

                }
                return false;
            }).collect(Collectors.toList());
            if(Objects.nonNull(lst) && !lst.isEmpty()) {
                menu.setType("sub");
                menu.setChildren(lst);
                lstFunction.removeAll(lst);
                if(!DataUtil.isNullOrEmpty(menu.getRoles())) {
                    lstFunction.removeAll(menu.getRoles());
                }
                buildChildrenMenu(lstFunction, menu.getChildren());
            }
        }
    }
}
