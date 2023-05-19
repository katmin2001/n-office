package com.fis.crm.crm_entity;

import javax.persistence.*;

@Entity
@Table(name = "CRM_CUSTOMER")
public class CrmCustomer {
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CRM_CUSTOMER_SEQ_GEN")
    @SequenceGenerator(name = "CRM_CUSTOMER_SEQ_GEN", sequenceName = "CRM_CUSTOMER_SEQ", allocationSize = 1)
    @Id
    @Column(name = "id")
    private Long id;
    @Column(name = "name")
    private String name;
    @Column(name = "phone")
    private String phone;
    @Column(name = "email")
    private String email;
    @Column(name = "address")
    private String address;
//    @OneToMany(mappedBy = "customer")
//    private List<CrmProject> projects;
//
//    public List<CrmProject> getProjects() {
//        return projects;
//    }
//
//    public void setProjects(List<CrmProject> projects) {
//        this.projects = projects;
//    }

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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public CrmCustomer(Long id, String name, String phone, String email, String address) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
    }

    public CrmCustomer() {
    }

    @Override
    public String toString() {
        return "CrmCustomer{" +
            "id=" + id +
            ", name='" + name + '\'' +
            ", phone='" + phone + '\'' +
            ", email='" + email + '\'' +
            ", address='" + address + '\'' +
            '}';
    }

//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (!(o instanceof CrmCustomer)) return false;
//        CrmCustomer that = (CrmCustomer) o;
//        return Objects.equals(getId(), that.getId()) && Objects.equals(getName(), that.getName()) && Objects.equals(getPhone(), that.getPhone()) && Objects.equals(getEmail(), that.getEmail()) && Objects.equals(getAddress(), that.getAddress());
//    }
//
//    @Override
//    public int hashCode() {
//        return Objects.hash(getId(), getName(), getPhone(), getEmail(), getAddress());
//    }
}
