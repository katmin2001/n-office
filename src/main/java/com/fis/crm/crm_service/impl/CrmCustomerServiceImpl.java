package com.fis.crm.crm_service.impl;

import com.fis.crm.crm_entity.CrmCustomer;
import com.fis.crm.crm_entity.DTO.CrmCustomerDTO;
import com.fis.crm.crm_repository.CrmCustomerRepo;
import com.fis.crm.crm_service.CrmCustomerService;
import com.fis.crm.crm_util.DtoMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
//@Transactional
public class CrmCustomerServiceImpl implements CrmCustomerService {
    private final CrmCustomerRepo customerRepo;
    private final DtoMapper dtoMapper = new DtoMapper();

    public CrmCustomerServiceImpl(CrmCustomerRepo customerRepo) {
        this.customerRepo = customerRepo;
    }

    @Override
    public List<CrmCustomerDTO> getAllCustomers() {
        List<CrmCustomer> customers = customerRepo.findAll();
        List<CrmCustomerDTO> customerDTOs = new ArrayList<>();

        for (CrmCustomer customer : customers) {
            customerDTOs.add(dtoMapper.customerDTOMapper(customer));
        }

        return customerDTOs;
    }

    @Override
    public CrmCustomerDTO getCustomerById(Long customerId) {
        Optional<CrmCustomer> optionalCustomer = customerRepo.findById(customerId);
        if (optionalCustomer.isPresent()) {
            CrmCustomer customer = optionalCustomer.get();
            return dtoMapper.customerDTOMapper(customer);
        }

        return null;
    }

    @Override
    public CrmCustomer createCustomer(CrmCustomerDTO crmCustomerRequestDTO) {
        CrmCustomer crmCustomer = new CrmCustomer();
        crmCustomer.setName(crmCustomerRequestDTO.getName());
        crmCustomer.setPhone(crmCustomerRequestDTO.getPhone());
        crmCustomer.setEmail(crmCustomerRequestDTO.getEmail());
        crmCustomer.setAddress(crmCustomerRequestDTO.getAddress());
        return customerRepo.save(crmCustomer);
    }

    @Override
    public CrmCustomer updateCustomer(Long customerId, CrmCustomerDTO crmCustomerRequest) {
        CrmCustomer existingCustomer = customerRepo.findById(customerId).orElse(null);
        if (existingCustomer != null) {
            if (crmCustomerRequest.getName() != null) existingCustomer.setName(crmCustomerRequest.getName());
            if (crmCustomerRequest.getEmail() != null) existingCustomer.setEmail(crmCustomerRequest.getEmail());
            if (crmCustomerRequest.getPhone() != null) existingCustomer.setPhone(crmCustomerRequest.getPhone());
            if (crmCustomerRequest.getAddress() != null) existingCustomer.setAddress(crmCustomerRequest.getAddress());
            return customerRepo.save(existingCustomer);
        }
        return null;
    }
}
