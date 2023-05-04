package com.fis.crm.service.dto;

import com.fis.crm.commons.DataUtil;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.Instant;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * A DTO for the {@link com.fis.crm.domain.Ticket} entity.
 */
public class TicketDTO implements Serializable {

    private Long ticketId;

    @NotNull(message = "{channel.type.is.required}")
    private String channelType;

    private String ticketCode;

    private Long customerId;

    private String status;

    private String fcr;

    private Long departmentId;

    private Long createUser;

    private Long confirmUser;

    private Instant createDatetime;

    private Instant updateDatetime;

    private Date confirmDatetime;

    private Instant confirmDeadline;

    private String cid;

    private String taxCode;

    private String companyName;

    private String address;


    private String customerName;


    private String satisfied;

    private Long no;
    private String createUserName;
    private String firstName;
    private String userName;

    //customer
    private String phoneNumber;
    private String name;
    private String email;
    private String contactPhone;
    private String channelName;
    private String processUser;
    private String processTime;
    private String processStatus;
    private String receiveDateTime;
    private List<ConfirmTicketDTO> confirmTicketDTOS;
    private List<ConfirmTicketAttachmentDTO> confirmTicketAttachmentDTOS;

    private String ticketStatus;
    private String ticketRequestStatus;
    private double compareDateConfirm;
    //ticketRequest
//    private String ticketRequestCode;
//    private Long requestType;
//    private Long bussinessType;
//    private Long priority;
//    private Long departmentIdRequest;
//    private String statusRequest;
//    private String content;
//    private Instant deadline;
//    private Instant confirmDate;
//    private Long timeNotify;
//    private Instant createDatetimeRequest;
//    private Long createUserRequest;

    private boolean disableCloseSave;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    private List<String> departments;

    public List<String> getDepartments() {
        return departments;
    }

    public void setDepartments(List<String> departments) {
        this.departments = departments;
    }

    public boolean isDisableCloseSave() {
        return disableCloseSave;
    }

    public void setDisableCloseSave(boolean disableCloseSave) {
        this.disableCloseSave = disableCloseSave;
    }

    List<TicketRequestDTO> listTicketRequestDTOS;

    public String getCreateUserName() {
        return createUserName;
    }

    public void setCreateUserName(String createUserName) {
        this.createUserName = createUserName;
    }

    private String recordLink;

    public String getRecordLink() {
        return recordLink;
    }

    public void setRecordLink(String recordLink) {
        this.recordLink = recordLink;
    }


    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public TicketDTO() {
    }

    public TicketDTO(Object[] objects) {
        int i = 0;
        this.ticketId = DataUtil.safeToLong(objects[i++]);
        this.ticketCode = DataUtil.safeToString(objects[i++]);
        this.channelType = DataUtil.safeToString(objects[i++]);
        this.channelName = DataUtil.safeToString(objects[i++]);
        this.customerName = DataUtil.safeToString(objects[i++]);
        this.cid = DataUtil.safeToString(objects[i++]);
        this.companyName = DataUtil.safeToString(objects[i++]);
        this.phoneNumber = DataUtil.safeToString(objects[i++]);
        this.email = DataUtil.safeToString(objects[i++]);
        this.processStatus = DataUtil.safeToString(objects[i++]);
        this.processUser = DataUtil.safeToString(objects[i++]);
        this.receiveDateTime = DataUtil.safeToString(objects[i++]);
        this.status = DataUtil.safeToString(objects[i++]);
        this.ticketStatus = DataUtil.safeToString(objects[i++]);
        this.ticketRequestStatus = DataUtil.safeToString(objects[i++]);
        String departmentsResult = DataUtil.safeToString(objects[i++]);
        this.departments = (departmentsResult == null || departmentsResult == "") ? null :
                            Arrays.asList(departmentsResult.split(","));
        this.createDatetime = DataUtil.safeToInstant(objects[i++]);
        this.confirmDatetime = DataUtil.safeToDate(objects[i++]);
        this.confirmDeadline = DataUtil.safeToInstant(objects[i++]);
        this.compareDateConfirm= DataUtil.safeToDouble(objects[i++]);
    }

    public Long getTicketId() {
        return ticketId;
    }

    public void setTicketId(Long ticketId) {
        this.ticketId = ticketId;
    }

    public String getChannelType() {
        return channelType;
    }

    public void setChannelType(String channelType) {
        this.channelType = channelType;
    }

    public String getTicketCode() {
        return ticketCode;
    }

    public void setTicketCode(String ticketCode) {
        this.ticketCode = ticketCode;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getFcr() {
        return fcr;
    }

    public void setFcr(String fcr) {
        this.fcr = fcr;
    }

    public Long getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Long departmentId) {
        this.departmentId = departmentId;
    }

    public Long getCreateUser() {
        return createUser;
    }

    public void setCreateUser(Long createUser) {
        this.createUser = createUser;
    }

    public Instant getCreateDatetime() {
        return createDatetime;
    }

    public void setCreateDatetime(Instant createDatetime) {
        this.createDatetime = createDatetime;
    }

    public Instant getUpdateDatetime() {
        return updateDatetime;
    }

    public void setUpdateDatetime(Instant updateDatetime) {
        this.updateDatetime = updateDatetime;
    }

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public String getTaxCode() {
        return taxCode;
    }

    public void setTaxCode(String taxCode) {
        this.taxCode = taxCode;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }


    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }


    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContactPhone() {
        return contactPhone;
    }

    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone;
    }


    public String getSatisfied() {
        return satisfied;
    }

    public void setSatisfied(String satisfied) {
        this.satisfied = satisfied;
    }

    public Long getConfirmUser() {
        return confirmUser;
    }

    public void setConfirmUser(Long confirmUser) {
        this.confirmUser = confirmUser;
    }

    public Date getConfirmDatetime() {
        return confirmDatetime;
    }

    public void setConfirmDatetime(Date confirmDatetime) {
        this.confirmDatetime = confirmDatetime;
    }

    public Instant getConfirmDeadline() {
        return confirmDeadline;
    }

    public void setConfirmDeadline(Instant confirmDeadline) {
        this.confirmDeadline = confirmDeadline;
    }

    public List<TicketRequestDTO> getListTicketRequestDTOS() {
        return listTicketRequestDTOS;
    }

    public void setListTicketRequestDTOS(List<TicketRequestDTO> listTicketRequestDTOS) {
        this.listTicketRequestDTOS = listTicketRequestDTOS;
    }

    public Long getNo() {
        return no;
    }

    public void setNo(Long no) {
        this.no = no;
    }

    public String getTicketStatus() {
        return ticketStatus;
    }

    public void setTicketStatus(String ticketStatus) {
        this.ticketStatus = ticketStatus;
    }

    public String getTicketRequestStatus() {
        return ticketRequestStatus;
    }

    public void setTicketRequestStatus(String ticketRequestStatus) {
        this.ticketRequestStatus = ticketRequestStatus;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TicketDTO)) {
            return false;
        }

        TicketDTO ticketDTO = (TicketDTO) o;
        if (this.ticketId == null) {
            return false;
        }
        return Objects.equals(this.ticketId, ticketDTO.ticketId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.ticketId);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TicketDTO{" +
            " ticketId=" + getTicketId() +
            ", channelType='" + getChannelType() + "'" +
            ", ticketCode='" + getTicketCode() + "'" +
            ", customerId=" + getCustomerId() +
            ", status='" + getStatus() + "'" +
            ", fcr='" + getFcr() + "'" +
            ", departmentId=" + getDepartmentId() +
            ", createUser=" + getCreateUser() +
            ", createDatetime='" + getCreateDatetime() + "'" +
            ", updateDatetime='" + getUpdateDatetime() + "'" +
            ", confirmDatetime='" + getConfirmDatetime() + "'" +
            ", confirmDeadline='" + getConfirmDeadline() + "'" +
            ", cid='" + getCid() + "'" +
            ", taxCode='" + getTaxCode() + "'" +
            ", companyName='" + getCompanyName() + "'" +
            ", address='" + getAddress() + "'" +
            ", satisfied='" + getSatisfied() + "'" +
            "}";
    }

    public String getChannelName() {
        return channelName;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }

    public String getProcessUser() {
        return processUser;
    }

    public void setProcessUser(String processUser) {
        this.processUser = processUser;
    }

    public String getProcessTime() {
        return processTime;
    }

    public void setProcessTime(String processTime) {
        this.processTime = processTime;
    }

    public String getProcessStatus() {
        return processStatus;
    }

    public void setProcessStatus(String processStatus) {
        this.processStatus = processStatus;
    }

    public String getReceiveDateTime() {
        return receiveDateTime;
    }

    public void setReceiveDateTime(String receiveDateTime) {
        this.receiveDateTime = receiveDateTime;
    }

    public List<ConfirmTicketDTO> getConfirmTicketDTOS() {
        return confirmTicketDTOS;
    }

    public void setConfirmTicketDTOS(List<ConfirmTicketDTO> confirmTicketDTOS) {
        this.confirmTicketDTOS = confirmTicketDTOS;
    }

    public List<ConfirmTicketAttachmentDTO> getConfirmTicketAttachmentDTOS() {
        return confirmTicketAttachmentDTOS;
    }

    public void setConfirmTicketAttachmentDTOS(List<ConfirmTicketAttachmentDTO> confirmTicketAttachmentDTOS) {
        this.confirmTicketAttachmentDTOS = confirmTicketAttachmentDTOS;
    }

    public double getCompareDateConfirm() {
        return compareDateConfirm;
    }

    public void setCompareDateConfirm(double compareDateConfirm) {
        this.compareDateConfirm = compareDateConfirm;
    }
}
