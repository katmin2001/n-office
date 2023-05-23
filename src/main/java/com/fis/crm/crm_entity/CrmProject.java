package com.fis.crm.crm_entity;

import javax.persistence.*;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "crm_project")
public class CrmProject {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;


    private String name;

    private String code;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private CrmCustomer customer;

    @ManyToOne
    @JoinColumn(name = "manager_id")
    private CrmUser manager;

    @ManyToOne
    @JoinColumn(name = "privacy_id")
    private CrmProjectPrivacy privacy;

    @ManyToOne
    @JoinColumn(name = "status_id")
    private CrmProjectStatus status;

    private String description;

    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;

    @Column(name = "finish_date")
    private LocalDate finishDate;

    // Getters and Setters


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

    public CrmUser getManager() {
        return manager;
    }

    public void setManager(CrmUser manager) {
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

