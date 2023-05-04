package com.fis.crm.service;

import com.fis.crm.domain.OptionSetValue;
import com.fis.crm.service.dto.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.fis.crm.domain.OptionSetValue}.
 */
public interface OptionSetValueService {

    /**
     * Save a configSchedule.
     *
     * @param optionSetValueDTO the entity to save.
     * @return the persisted entity.
     */
    OptionSetValueDTO save(OptionSetValueDTO optionSetValueDTO);

    /**
     * Get all the configSchedules.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<OptionSetValueDTO> findAll(Pageable pageable);


    /**
     * Get the "id" configSchedule.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<OptionSetValueDTO> findOne(Long id);

    /**
     * Delete the "id" configSchedule.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    OptionSetValue findMaxIdByOptionSet(Long id);

    List<OptionSetValueDTO> find(OptionSetValueDTO obj);

    OptionSetValueDTO changeStatus(OptionSetValueDTO optionSetValueDTO);

    List<OptionSetValueDTO> getListDataOptionSetValue(QuerySearchOptionSetDTO querySearchOptionSetDTO);

    Page<OptionSetValueDTO> getListDataOptionSetValue(QuerySearchOptionSetDTO querySearchOptionSetDTO, Pageable pageable);

    Optional<List<OptionSetValueDTO>> findOptSetValueByOptionSetCode(String code);

    List<BussinessTypeDTO> getBusinessTypeForCombobox();

    List<RequestTypeDTO> getRequestTypeForCombobox();
}
