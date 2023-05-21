package com.fis.crm.crm_entity;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "crm_project")
public class CrmProjectRequest {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)

    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CRM_PROJECT_SEQ_GEN")
    @SequenceGenerator(name = "CRM_PROJECT_SEQ_GEN", sequenceName = "CRM_PROJECT_SEQ", allocationSize = 1)
    @Id
    @Column(name = "id")
    private Long id;
    private String name;
    private String code;
    @Column(name = "customer_id")
    private Long customerId;
    @Column(name = "manager_id")
    private Long managerId;
    @Column(name = "privacy_id")
    private Long privacyId;
    @Column(name = "status_id")
    private Long statusId;
    private String description;
    @Column(name = "start_date")
    private LocalDate startDate;
    @Column(name = "end_date")
    private LocalDate endDate;
    @Column(name = "finish_date")
    private LocalDate finishDate;

    public CrmProjectRequest(Long id, String name, String code, Long customerId, Long managerId, Long privacyId, Long statusId, String description, LocalDate startDate, LocalDate endDate, LocalDate finishDate) {
        this.id = id;
        this.name = name;
        this.code = code;
        this.customerId = customerId;
        this.managerId = managerId;
        this.privacyId = privacyId;
        this.statusId = statusId;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
        this.finishDate = finishDate;
    }

    public CrmProjectRequest() {
    }

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

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public Long getManagerId() {
        return managerId;
    }

    public void setManagerId(Long managerId) {
        this.managerId = managerId;
    }

    public Long getPrivacyId() {
        return privacyId;
    }

    public void setPrivacyId(Long privacyId) {
        this.privacyId = privacyId;
    }

    public Long getStatusId() {
        return statusId;
    }

    public void setStatusId(Long statusId) {
        this.statusId = statusId;
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
