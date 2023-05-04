package com.fis.crm.service;

import com.fis.crm.domain.OptionSet;
import com.fis.crm.service.dto.ConfigScheduleDTO;
import com.fis.crm.service.dto.OptionSetDTO;
import com.fis.crm.service.dto.OptionSetValueDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.fis.crm.domain.OptionSet}.
 */
public interface OptionSetService {

    /**
     * Save a configSchedule.
     *
     * @param optionSetDTO the entity to save.
     * @return the persisted entity.
     */
    OptionSetDTO save(OptionSetDTO optionSetDTO);

    /**
     * Get all the configSchedules.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<OptionSetDTO> findAll(Pageable pageable);


    /**
     * Get the "id" configSchedule.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<OptionSetDTO> findOne(Long id);

    /**
     * Delete the "id" configSchedule.
     *
     * @param id the id of the entity.
     */
    OptionSetDTO delete(Long id);
    List<OptionSetDTO> find(OptionSetDTO optionSetDTO);
    OptionSetDTO changeStatus(OptionSetDTO obj);
    List<OptionSetValueDTO> findOptSetValueByOptionSetId(OptionSetDTO opt);
    OptionSetValueDTO importList(MultipartFile file);

    Page<OptionSetDTO> searchOptionSet(OptionSetDTO optionSetDTO, Pageable pageable);
}
