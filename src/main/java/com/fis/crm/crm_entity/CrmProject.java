package com.fis.crm.crm_entity;

import lombok.Data;

import javax.persistence.*;
import java.sql.Date;
import java.util.List;
import java.util.Set;

@Data
@Entity
@Table(name = "CRM_PROJECT")
public class CrmProject {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "ID")
    private Long id;
    @Column(name = "NAME")
    private String name;
    @Column(name = "CODE")
    private String code;
    @Column(name = "DESCRIPTION")
    private String description;
    @Column(name = "START_DATE")
    private Date startDate;
    @Column(name = "END_DATE")
    private Date endDate;
    @Column(name = "FINISH_DATE")
    private Date finishDate;
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
    @OneToMany(mappedBy = "project")
    private List<CrmStage> stages;
    @OneToMany(mappedBy = "project")
    private List<CrmProjectMember> members;

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Date getFinishDate() {
        return finishDate;
    }

    public void setFinishDate(Date finishDate) {
        this.finishDate = finishDate;
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

    public List<CrmStage> getStages() {
        return stages;
    }

    public void setStages(List<CrmStage> stages) {
        this.stages = stages;
    }

    public List<CrmProjectMember> getMembers() {
        return members;
    }

    public void setMembers(List<CrmProjectMember> members) {
        this.members = members;
    }

}
