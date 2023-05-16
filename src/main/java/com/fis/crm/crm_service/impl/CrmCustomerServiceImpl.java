package com.fis.crm.crm_service.impl;

import com.fis.crm.crm_entity.CrmCustomer;
import com.fis.crm.crm_entity.DTO.CrmCustomerRequestDTO;
import com.fis.crm.crm_repository.CrmCustomerRepo;
import com.fis.crm.crm_service.CrmCustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CrmCustomerServiceImpl implements CrmCustomerService {
    @Autowired
    CrmCustomerRepo customerRepo;

    @Override
    public List<CrmCustomer> getAllCustomers() {
        return customerRepo.findAll();
    }

    @Override
    public CrmCustomer getCustomerById(Long customerId) {
        Optional<CrmCustomer> optionalCustomer = customerRepo.findById(customerId);
        return optionalCustomer.orElse(null);
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
        // Kiểm tra xem khách hàng có tồn tại hay không
        CrmCustomer existingCustomer = customerRepo.findById(customerId)
            .orElseThrow(() -> new EntityNotFoundException("Không tìm thấy khách hàng với ID: " + customerId));

        existingCustomer.setName(crmCustomerRequestDTO.getName());
        existingCustomer.setEmail(crmCustomerRequestDTO.getEmail());
        existingCustomer.setPhone(crmCustomerRequestDTO.getPhone());
        existingCustomer.setAddress(crmCustomerRequestDTO.getAddress());

        // Lưu thông tin khách hàng đã được cập nhật vào cơ sở dữ liệu
        return customerRepo.save(existingCustomer);
    }
}
