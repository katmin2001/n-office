package com.fis.crm.crm_util;

import com.fis.crm.crm_entity.CrmCustomer;
import com.fis.crm.crm_entity.DTO.CrmCustomerDTO;

public class CrmCustomerMapper {
    public static CrmCustomerDTO toDTO(CrmCustomer customer) {
        CrmCustomerDTO dto = new CrmCustomerDTO();
        dto.setId(customer.getId());
        dto.setName(customer.getName());
        dto.setPhone(customer.getPhone());
        dto.setEmail(customer.getEmail());
        dto.setAddress(customer.getAddress());
        return dto;
    }

    public static CrmCustomer toEntity(CrmCustomerDTO dto) {
        CrmCustomer customer = new CrmCustomer();
        customer.setId(dto.getId());
        customer.setName(dto.getName());
        customer.setPhone(dto.getPhone());
        customer.setEmail(dto.getEmail());
        customer.setAddress(dto.getAddress());
        return customer;
    }
}
