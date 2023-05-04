package com.fis.crm.service.impl;

import com.fis.crm.commons.DataUtil;
import com.fis.crm.domain.Ticket;
import com.fis.crm.repository.TicketRepository;
import com.fis.crm.service.TicketService;
import com.fis.crm.service.dto.TicketDTO;
import com.fis.crm.service.mapper.TicketMapper;

import com.fis.crm.domain.Department;
import com.fis.crm.repository.DepartmentRepository;
import com.fis.crm.service.DepartmentService;
import com.fis.crm.service.dto.DepartmentDTO;
import com.fis.crm.service.mapper.DepartmentMapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link Department}.
 */
@Service
@Transactional
public class DepartmentServiceImpl implements DepartmentService {

  private final Logger log = LoggerFactory.getLogger(DepartmentServiceImpl.class);

  private final DepartmentRepository departmentRepository;

  private final DepartmentMapper departmentMapper;

  public DepartmentServiceImpl(DepartmentRepository departmentRepository, DepartmentMapper departmentMapper) {
    this.departmentRepository = departmentRepository;
    this.departmentMapper = departmentMapper;
  }

  @Override
  public DepartmentDTO save(DepartmentDTO departmentDTO) {
    log.debug("Request to save Ticket : {}", departmentDTO);
    Department department = departmentMapper.toEntity(departmentDTO);
      department = departmentRepository.save(department);
    return departmentMapper.toDto(department);
  }

  @Override
  public Optional<DepartmentDTO> partialUpdate(DepartmentDTO departmentDTO) {
    log.debug("Request to partially update Department : {}", departmentDTO);

//    return ticketRepository
//      .findById(ticketDTO.getId())
//      .map(
//        existingTicket -> {
//          ticketMapper.partialUpdate(existingTicket, ticketDTO);
//          return existingTicket;
//        }
//      )
//      .map(ticketRepository::save)
//      .map(ticketMapper::toDto);
      return null;
  }

  @Override
  @Transactional(readOnly = true)
  public Page<DepartmentDTO> findAll(Pageable pageable) {
    log.debug("Request to get all Department");
    return departmentRepository.findAll(pageable).map(departmentMapper::toDto);
  }

  @Override
  @Transactional(readOnly = true)
  public Optional<DepartmentDTO> findOne(Long id) {
    log.debug("Request to get Department : {}", id);
    return departmentRepository.findById(id).map(departmentMapper::toDto);
  }

  @Override
  public void delete(Long id) {
    log.debug("Request to delete Department : {}", id);
      departmentRepository.deleteById(id);
  }

    @Override
    public List<DepartmentDTO> getListDepartmentsForCombobox() {
        List<Object[]> lst = new ArrayList<Object[]>();
        List<DepartmentDTO> lstResult = new ArrayList<DepartmentDTO>();
        try {

            lst = departmentRepository.getListDepartmentsForCombobox();
            for (Object[] obj1 : lst){
                DepartmentDTO departmentDTO = new DepartmentDTO();

                departmentDTO.setDepartmentId(DataUtil.safeToLong(obj1[0]));
                departmentDTO.setDepartmentName(DataUtil.safeToString(obj1[1]));
                departmentDTO.setDepartmentCode(DataUtil.safeToString(obj1[2]));

                lstResult.add(departmentDTO);
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return lstResult;
    }

    @PersistenceContext
    EntityManager em;
}
