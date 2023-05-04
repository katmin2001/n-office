package com.fis.crm.service;

import com.fis.crm.domain.ConfigSchedule;
import com.fis.crm.service.dto.ConfigScheduleDTO;

import com.fis.crm.service.dto.ConfigScheduleSearch;
import com.fis.crm.service.dto.ServiceResult;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.fis.crm.domain.ConfigSchedule}.
 */
public interface ConfigScheduleService {

    /**
     * Save a configSchedule.
     *
     * @param configScheduleDTO the entity to save.
     * @return the persisted entity.
     */
    ConfigScheduleDTO save(ConfigScheduleDTO configScheduleDTO);

    /**
     * Get all the configSchedules.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ConfigScheduleDTO> findAll(Pageable pageable);


    /**
     * Get the "id" configSchedule.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ConfigScheduleDTO> findOne(Long id);

    /**
     * Delete the "id" configSchedule.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

//    List<ConfigScheduleDTO> find(ConfigScheduleDTO configScheduleDTO);
    ConfigScheduleDTO importList(MultipartFile file) throws Exception;

    ServiceResult getProcessTime(ConfigScheduleDTO configScheduleDTO);


    void importConfigSchedule(MultipartFile file) throws IOException;

    Page<ConfigScheduleDTO> onSearchConfigSchedule(ConfigScheduleDTO configScheduleDTO, Pageable pageable);
    List<ConfigScheduleDTO> export(ConfigScheduleDTO configScheduleDTO);
}
