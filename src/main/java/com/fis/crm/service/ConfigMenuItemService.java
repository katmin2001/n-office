package com.fis.crm.service;

import com.fis.crm.service.dto.ConfigMenuItemAddDTO;
import com.fis.crm.service.dto.ConfigMenuItemDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;
import java.util.function.Consumer;

/**
 * Service Interface for managing {@link com.fis.crm.domain.ConfigMenuItem}.
 */
public interface ConfigMenuItemService {

    Boolean save(ConfigMenuItemDTO configMenuItemDTO, Consumer consumer);

    /**
     * Partially updates a configMenuItem.
     *
     * @param configMenuItemDTO the entity to update partially.
     * @return the persisted entity.
     */
//  Optional<ConfigMenuItemDTO> partialUpdate(ConfigMenuItemDTO configMenuItemDTO);

    /**
     * Get all the configMenuItems.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ConfigMenuItemDTO> findAll(Pageable pageable);

    /**
     * Get the "id" configMenuItem.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ConfigMenuItemDTO> findOne(Long id);

    /**
     * Delete the "id" configMenuItem.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    Boolean update(ConfigMenuItemDTO configMenuItemDTO, Consumer consumer);

    Boolean addFunctionMenu(ConfigMenuItemAddDTO configMenuItemAddDTO, Consumer consumer);

    Boolean action(ConfigMenuItemDTO configMenuItemDTO, Consumer consumer);
}
