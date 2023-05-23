package com.fis.crm.crm_service;

import com.fis.crm.crm_entity.CrmCustomer;
import com.fis.crm.crm_entity.DTO.CrmCustomerDTO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public interface CrmCustomerService {
    public List<CrmCustomerDTO> getAllCustomers();

    public CrmCustomerDTO getCustomerById(Long customerId);

    public CrmCustomer createCustomer(CrmCustomerDTO crmCustomerRequestDTO);

    public CrmCustomer updateCustomer(Long customerId, CrmCustomerDTO crmCustomerRequestDTO);
}
