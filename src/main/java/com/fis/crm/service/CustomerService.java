package com.fis.crm.service;

import com.fis.crm.service.dto.TicketDTO;
import com.fis.crm.service.dto.CustomerDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.fis.crm.domain.Customer}.
 */
public interface CustomerService {
  /**
   * Save a ticket.
   *
   * @param customerDTO the entity to save.
   * @return the persisted entity.
   */
  CustomerDTO save(CustomerDTO customerDTO);

  /**
   * Partially updates a ticket.
   *
   * @param customerDTO the entity to update partially.
   * @return the persisted entity.
   */
  Optional<CustomerDTO> partialUpdate(CustomerDTO customerDTO);

  /**
   * Get all the tickets.
   *
   * @param pageable the pagination information.
   * @return the list of entities.
   */
  Page<CustomerDTO> findAll(Pageable pageable);

  /**
   * Get the "id" ticket.
   *
   * @param id the id of the entity.
   * @return the entity.
   */
  Optional<CustomerDTO> findOne(Long id);

  /**
   * Delete the "id" ticket.
   *
   * @param id the id of the entity.
   */
  void delete(Long id);

  List<CustomerDTO> getListCustomersForCombobox();
}
