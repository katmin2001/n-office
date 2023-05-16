package com.fis.crm.crm_controller;

import com.fis.crm.crm_entity.CrmCustomer;
import com.fis.crm.crm_entity.DTO.CrmCustomerRequestDTO;
import com.fis.crm.crm_service.CrmCustomerService;
import com.fis.crm.crm_service.impl.CrmCustomerServiceImpl;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@Data
@RestController
@RequestMapping("/customer")
public class CrmCustomerController {
    @Autowired
    private final CrmCustomerService crmCustomerService;

    public CrmCustomerController(CrmCustomerServiceImpl crmCustomerService) {
        this.crmCustomerService = crmCustomerService;
    }

    @GetMapping("/")
    public List<CrmCustomer> getAllCustomers() {
        return crmCustomerService.getAllCustomers();
    }

    @GetMapping("/{customerId}")
    public CrmCustomer getCustomerById(@PathVariable Long customerId) {
        return crmCustomerService.getCustomerById(customerId); // trả về null nếu không tìm thấy khách hàng với id tương ứng
    }

    @PostMapping("/")
    public CrmCustomer createCustomer(@RequestBody CrmCustomerRequestDTO crmCustomerRequestDTO) {
        CrmCustomer crmCustomer = crmCustomerService.createCustomer(crmCustomerRequestDTO);
        return crmCustomer;
    }

    @PutMapping("/{customerId}")
    public CrmCustomer updateCustomer(@PathVariable("customerId") Long customerId, @RequestBody CrmCustomerRequestDTO crmCustomerRequestDTO) {
        CrmCustomer crmCustomer = crmCustomerService.updateCustomer(customerId,crmCustomerRequestDTO);
        return crmCustomer;
    }
}
