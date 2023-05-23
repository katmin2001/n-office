package com.fis.crm.crm_entity.DTO;

import com.fis.crm.crm_entity.CrmCustomer;
import com.fis.crm.crm_entity.CrmProjectPrivacy;
import com.fis.crm.crm_entity.CrmProjectStatus;
import java.time.LocalDate;

public class CrmProjectDTO {
    private Long id;
    private String name;
    private String code;
    private CrmCustomer customer;
    private Crm_UserDTO manager;
    private CrmProjectPrivacy privacy;
    private CrmProjectStatus status;
    private String description;
    private LocalDate startDate;
    private LocalDate endDate;
    private LocalDate finishDate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public CrmCustomer getCustomer() {
        return customer;
    }

    public void setCustomer(CrmCustomer customer) {
        this.customer = customer;
    }

    public Crm_UserDTO getManager() {
        return manager;
    }

    public void setManager(Crm_UserDTO manager) {
        this.manager = manager;
    }

    public CrmProjectPrivacy getPrivacy() {
        return privacy;
    }

    public void setPrivacy(CrmProjectPrivacy privacy) {
        this.privacy = privacy;
    }

    public CrmProjectStatus getStatus() {
        return status;
    }

    public void setStatus(CrmProjectStatus status) {
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public LocalDate getFinishDate() {
        return finishDate;
    }

    public void setFinishDate(LocalDate finishDate) {
        this.finishDate = finishDate;
    }
}
