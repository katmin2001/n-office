package com.fis.crm.service.impl;

import com.fis.crm.commons.DataUtil;
import com.fis.crm.domain.Customer;
import com.fis.crm.domain.Ticket;
import com.fis.crm.repository.CustomerRepository;
import com.fis.crm.service.CustomerService;
import com.fis.crm.service.dto.CustomerDTO;
import com.fis.crm.service.mapper.CustomerMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link Ticket}.
 */
@Service
@Transactional
public class CustomerServiceImpl implements CustomerService {

  private final Logger log = LoggerFactory.getLogger(CustomerServiceImpl.class);

  private final CustomerRepository customerRepository;

  private final CustomerMapper customerMapper;

  public CustomerServiceImpl(CustomerRepository customerRepository, CustomerMapper customerMapper) {
    this.customerRepository = customerRepository;
    this.customerMapper = customerMapper;
  }

  @Override
  public CustomerDTO save(CustomerDTO customerDTO) {
    log.debug("Request to save Customer : {}", customerDTO);
    Customer customer = customerMapper.toEntity(customerDTO);
    customer = customerRepository.save(customer);
    return customerMapper.toDto(customer);
  }

  @Override
  public Optional<CustomerDTO> partialUpdate(CustomerDTO customerDTO) {
    log.debug("Request to partially update Customer : {}", customerDTO);

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
  public Page<CustomerDTO> findAll(Pageable pageable) {
    log.debug("Request to get all Customers");
    return customerRepository.findAll(pageable).map(customerMapper::toDto);
  }

  @Override
  @Transactional(readOnly = true)
  public Optional<CustomerDTO> findOne(Long customerId) {
    log.debug("Request to get Customer : {}", customerId);
    return customerRepository.findById(customerId).map(customerMapper::toDto);
  }

  @Override
  public void delete(Long customerId) {
    log.debug("Request to delete Customer : {}", customerId);
      customerRepository.deleteById(customerId);
  }

    @Override
    public List<CustomerDTO> getListCustomersForCombobox() {
        List<Object[]> lst = new ArrayList<Object[]>();
        List<CustomerDTO> lstResult = new ArrayList<CustomerDTO>();
        try {

            lst = customerRepository.getListCustomersForCombobox();
            for (Object[] obj1 : lst){
                CustomerDTO customerDTO = new CustomerDTO();

                customerDTO.setCustomerId(DataUtil.safeToLong(obj1[0]));
                customerDTO.setName(DataUtil.safeToString(obj1[1]));


                lstResult.add(customerDTO);
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return lstResult;
    }

}
