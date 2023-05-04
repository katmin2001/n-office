package com.fis.crm.service;

import com.fis.crm.service.dto.TicketDTO;
import com.fis.crm.service.dto.DepartmentDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.fis.crm.domain.Department}.
 */
public interface DepartmentService {
  /**
   * Save a ticket.
   *
   * @param departmentDTO the entity to save.
   * @return the persisted entity.
   */
  DepartmentDTO save(DepartmentDTO departmentDTO);

  /**
   * Partially updates a ticket.
   *
   * @param departmentDTO the entity to update partially.
   * @return the persisted entity.
   */
  Optional<DepartmentDTO> partialUpdate(DepartmentDTO departmentDTO);

  /**
   * Get all the tickets.
   *
   * @param pageable the pagination information.
   * @return the list of entities.
   */
  Page<DepartmentDTO> findAll(Pageable pageable);

  /**
   * Get the "departmentId" department.
   *
   * @param departmentId the id of the entity.
   * @return the entity.
   */
  Optional<DepartmentDTO> findOne(Long departmentId);

  /**
   * Delete the "id" ticket.
   *
   * @param departmentId the id of the entity.
   */
  void delete(Long departmentId);

  List<DepartmentDTO>  getListDepartmentsForCombobox();
}
