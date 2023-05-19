package com.fis.crm.crm_service;

import com.fis.crm.crm_entity.CrmCustomer;
import com.fis.crm.crm_entity.DTO.CrmCustomerDTO;
import com.fis.crm.crm_entity.DTO.CrmCustomerRequestDTO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public interface CrmCustomerService {
    public List<CrmCustomerDTO> getAllCustomers();

    public CrmCustomerDTO getCustomerById(Long customerId);

    public CrmCustomer createCustomer(CrmCustomerRequestDTO crmCustomerRequestDTO);

    public CrmCustomer updateCustomer(Long customerId, CrmCustomerRequestDTO crmCustomerRequestDTO);
}
