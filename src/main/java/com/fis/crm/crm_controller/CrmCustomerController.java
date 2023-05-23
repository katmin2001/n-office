package com.fis.crm.crm_controller;

import com.fis.crm.crm_entity.CrmCustomer;
import com.fis.crm.crm_entity.DTO.CrmCustomerDTO;
import com.fis.crm.crm_service.CrmCustomerService;
import com.fis.crm.crm_service.impl.CrmCustomerServiceImpl;
import lombok.Data;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/customer")
public class CrmCustomerController {
    private final CrmCustomerService crmCustomerService;

    public CrmCustomerController(CrmCustomerServiceImpl crmCustomerService) {
        this.crmCustomerService = crmCustomerService;
    }

    @GetMapping("/")
    public List<CrmCustomerDTO> getAllCustomers() {
        return crmCustomerService.getAllCustomers();
    }

    @GetMapping("/{customerId}")
    public CrmCustomerDTO getCustomerById(@PathVariable Long customerId) {
        return crmCustomerService.getCustomerById(customerId); // trả về null nếu không tìm thấy khách hàng với id tương ứng
    }

    @PostMapping("/")
    public CrmCustomer createCustomer(@RequestBody CrmCustomerDTO newCrmCustomer) {
        CrmCustomer crmCustomer = crmCustomerService.createCustomer(newCrmCustomer);
        return crmCustomer;
    }

    @PutMapping("/{customerId}")
    public CrmCustomer updateCustomer(@PathVariable("customerId") Long customerId, @RequestBody CrmCustomerDTO crmCustomerUpdate) {
        CrmCustomer customer = crmCustomerService.updateCustomer(customerId, crmCustomerUpdate);
        return customer;
    }
}
