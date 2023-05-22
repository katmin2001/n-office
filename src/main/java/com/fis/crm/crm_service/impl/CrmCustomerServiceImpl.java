package com.fis.crm.crm_service.impl;

import com.fis.crm.crm_entity.CrmCustomer;
import com.fis.crm.crm_entity.DTO.CrmCustomerDTO;
import com.fis.crm.crm_entity.DTO.CrmCustomerRequestDTO;
import com.fis.crm.crm_repository.CrmCustomerRepo;
import com.fis.crm.crm_service.CrmCustomerService;
import com.fis.crm.crm_util.CrmCustomerMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
//@Transactional
public class CrmCustomerServiceImpl implements CrmCustomerService {
    private final CrmCustomerRepo customerRepo;

    public CrmCustomerServiceImpl(CrmCustomerRepo customerRepo) {
        this.customerRepo = customerRepo;
    }

    @Override
    public List<CrmCustomerDTO> getAllCustomers() {
        List<CrmCustomer> customers = customerRepo.findAll();
        List<CrmCustomerDTO> customerDTOs = new ArrayList<>();

        for (CrmCustomer customer : customers) {
            customerDTOs.add(CrmCustomerMapper.toDTO(customer));
        }

        return customerDTOs;
    }

    @Override
    public CrmCustomerDTO getCustomerById(Long customerId) {
        Optional<CrmCustomer> optionalCustomer = customerRepo.findById(customerId);
        if (optionalCustomer.isPresent()) {
            CrmCustomer customer = optionalCustomer.get();
            return CrmCustomerMapper.toDTO(customer);
        }

        return null;
    }

    @Override
    public CrmCustomer createCustomer(CrmCustomerRequestDTO crmCustomerRequestDTO) {
        CrmCustomer crmCustomer = new CrmCustomer();
        crmCustomer.setName(crmCustomerRequestDTO.getName());
        crmCustomer.setPhone(crmCustomerRequestDTO.getPhone());
        crmCustomer.setEmail(crmCustomerRequestDTO.getEmail());
        crmCustomer.setAddress(crmCustomerRequestDTO.getAddress());
        return customerRepo.save(crmCustomer);
    }

    @Override
    public CrmCustomer updateCustomer(Long customerId, CrmCustomerRequestDTO crmCustomerRequestDTO) {
        CrmCustomer existingCustomer = customerRepo.findById(customerId).orElse(null);
        if (existingCustomer != null) {
            existingCustomer.setName(crmCustomerRequestDTO.getName());
            existingCustomer.setEmail(crmCustomerRequestDTO.getEmail());
            existingCustomer.setPhone(crmCustomerRequestDTO.getPhone());
            existingCustomer.setAddress(crmCustomerRequestDTO.getAddress());
            return customerRepo.save(existingCustomer);
        }
        return null;
    }
}
