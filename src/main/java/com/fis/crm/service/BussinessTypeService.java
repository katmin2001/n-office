package com.fis.crm.service;

import com.fis.crm.service.dto.BussinessTypeDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.fis.crm.domain.BussinessType}.
 */
public interface BussinessTypeService {

    /**
     * Save a bussinessType.
     *
     * @param bussinessTypeDTO the entity to save.
     * @return the persisted entity.
     */
    BussinessTypeDTO save(BussinessTypeDTO bussinessTypeDTO);

    /**
     * Get all the bussinessTypes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<BussinessTypeDTO> findAll(Pageable pageable);


    /**
     * Get the "id" bussinessType.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<BussinessTypeDTO> findOne(Long id);

    /**
     * Delete the "id" bussinessType.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
