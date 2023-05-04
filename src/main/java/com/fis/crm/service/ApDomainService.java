package com.fis.crm.service;

import com.fis.crm.service.dto.ApDomainDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.fis.crm.domain.ApDomain}.
 */
public interface ApDomainService {

    /**
     * Save a apDomain.
     *
     * @param apDomainDTO the entity to save.
     * @return the persisted entity.
     */
    ApDomainDTO save(ApDomainDTO apDomainDTO);

    /**
     * Get all the apDomains.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ApDomainDTO> findAll(Pageable pageable);


    /**
     * Get the "id" apDomain.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ApDomainDTO> findOne(Long id);

    /**
     * Delete the "id" apDomain.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    List<ApDomainDTO> findAllByType(String type);
}
