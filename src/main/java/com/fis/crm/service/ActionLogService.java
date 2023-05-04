package com.fis.crm.service;

import com.fis.crm.service.dto.ActionLogDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

/**
 * Service Interface for managing {@link com.fis.crm.domain.ActionLog}.
 */
public interface ActionLogService {

    /**
     * Save a configSchedule.
     *
     * @param actionLogDTO the entity to save.
     * @return the persisted entity.
     */
    ActionLogDTO save(ActionLogDTO actionLogDTO);

    /**
     * Get all the configSchedules.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ActionLogDTO> findAll(Pageable pageable);


    /**
     * Get the "id" configSchedule.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ActionLogDTO> findOne(Long id);

    /**
     * Delete the "id" configSchedule.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    Consumer<ActionLogDTO> getConsumerWriteLog();

    void saveActionLog(ActionLogDTO actionLogDTO);

    /**
     * Get all the configSchedules.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ActionLogDTO> getActionLog(ActionLogDTO actionLogDTO, Pageable pageable);

    List<ActionLogDTO> getAllActionLog(ActionLogDTO actionLogDTO);
}
