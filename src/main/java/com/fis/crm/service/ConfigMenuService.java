package com.fis.crm.service;

import com.fis.crm.service.dto.ConfigMenuDTO;
import com.fis.crm.service.dto.FunctionItemDTO;
import com.fis.crm.service.dto.MenuItemDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.fis.crm.domain.ConfigMenu}.
 */
public interface ConfigMenuService {
    /**
     * Save a configMenu.
     *
     * @param configMenuDTO the entity to save.
     * @return the persisted entity.
     */
    ConfigMenuDTO save(ConfigMenuDTO configMenuDTO);

    List<ConfigMenuDTO> findAll();

    /**
     * Get the "id" configMenu.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ConfigMenuDTO> findOne(Long id);

    /**
     * Delete the "id" configMenu.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    List<MenuItemDTO> getMenuTreeLogin(String login);

    List<FunctionItemDTO> getFunctionByMenuAndRole(Long roleId, Long menuId);

    List<FunctionItemDTO> getConfigMenuItemByMenu(Long id);

    List<FunctionItemDTO> getConfigMenuItemInActiveByMenu(Long id);

}
