package com.fis.crm.service.dto;

import com.fis.crm.config.Constants;
import com.fis.crm.domain.Authority;
import com.fis.crm.domain.User;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.time.Instant;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * A DTO representing a user, with his authorities.
 */
public class AdminUserDTO {

    private Long id;

    @NotBlank
    @Pattern(regexp = Constants.LOGIN_REGEX)
    @Size(min = 1, max = 50)
    private String login;

    private String password;

    @Size(max = 50)
    private String firstName;

    @Email
    @Size(min = 5, max = 254)
    private String email;

    @Size(max = 256)
    private String imageUrl;

    private boolean activated = true;

    @Size(min = 2, max = 10)
    private String langKey;

    private String createdBy;

    private Instant createdDate;

    private String lastModifiedBy;

    private Instant lastModifiedDate;

    private Long departmentId;

    private String departmentName;

    private Set<String> authorities;

    private String type;

    private String internalNumber;

    private String internalNumberPassword;

    private String serverVoice;

    private String connectionChannel;

    private String[] departments;

    private Instant activeDatetime;

    public AdminUserDTO() {
        // Empty constructor needed for Jackson.
    }

    public AdminUserDTO(Long id,
                        String login, String firstName, String email,
                        String imageUrl, String createdBy,
                        Instant createdDate, String lastModifiedBy,
                        Instant lastModifiedDate, Long departmentId, String type,
                        Boolean activated, String internalNumber, String internalNumberPassword,
                        String serverVoice, String connectionChannel,String departments
    ) {
        this.id = id;
        this.login = login;
        this.firstName = firstName;
        this.email = email;
        this.imageUrl = imageUrl;
        this.createdBy = createdBy;
        this.createdDate = createdDate;
        this.lastModifiedBy = lastModifiedBy;
        this.lastModifiedDate = lastModifiedDate;
        this.departmentId = departmentId;
        this.type = type;
        this.activated = activated;
        this.internalNumber = internalNumber;
        this.internalNumberPassword = internalNumberPassword;
        this.serverVoice = serverVoice;
        this.connectionChannel = connectionChannel;
        this.departments = (departments!=null && !departments.isEmpty())? departments.split(","):null;
    }

    public AdminUserDTO(User user) {
        this.id = user.getId();
        this.login = user.getLogin();
        this.firstName = user.getFirstName();
        this.email = user.getEmail();
        this.activated = user.isActivated();
        this.imageUrl = user.getImageUrl();
        this.langKey = user.getLangKey();
        this.createdBy = user.getCreatedBy();
        this.createdDate = user.getCreatedDate();
        this.lastModifiedBy = user.getLastModifiedBy();
        this.lastModifiedDate = user.getLastModifiedDate();
        this.authorities = user.getAuthorities().stream().map(Authority::getName).collect(Collectors.toSet());
        this.departmentId = user.getDepartmentId();
        this.type = user.getType();
        this.internalNumber = user.getInternalNumber();
        this.internalNumberPassword = user.getInternalNumberPassword();
        this.serverVoice = user.getServerVoice();
        this.connectionChannel = user.getConnectionChannel();
        this.departments = (user.getDepartments()!=null&&!user.getDepartments().isEmpty())?user.getDepartments().split(","):null;
        this.activeDatetime = user.getActiveDatetime();
    }

    public Instant getActiveDatetime() {
        return activeDatetime;
    }

    public void setActiveDatetime(Instant activeDatetime) {
        this.activeDatetime = activeDatetime;
    }

    public String[] getDepartments() {
        return departments;
    }

    public void setDepartments(String[] departments) {
        this.departments = departments;
    }

    public Long getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Long departmentId) {
        this.departmentId = departmentId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public boolean isActivated() {
        return activated;
    }

    public void setActivated(boolean activated) {
        this.activated = activated;
    }

    public String getLangKey() {
        return langKey;
    }

    public void setLangKey(String langKey) {
        this.langKey = langKey;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Instant getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }

    public String getLastModifiedBy() {
        return lastModifiedBy;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public Instant getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(Instant lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public Set<String> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(Set<String> authorities) {
        this.authorities = authorities;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public String getInternalNumber() {
        return internalNumber;
    }

    public void setInternalNumber(String internalNumber) {
        this.internalNumber = internalNumber;
    }

    public String getInternalNumberPassword() {
        return internalNumberPassword;
    }

    public void setInternalNumberPassword(String internalNumberPassword) {
        this.internalNumberPassword = internalNumberPassword;
    }

    public String getServerVoice() {
        return serverVoice;
    }

    public void setServerVoice(String serverVoice) {
        this.serverVoice = serverVoice;
    }

    public String getConnectionChannel() {
        return connectionChannel;
    }

    public void setConnectionChannel(String connectionChannel) {
        this.connectionChannel = connectionChannel;
    }

    @Override
    public String toString() {
        return "AdminUserDTO{" +
            "id=" + id +
            ", login='" + login + '\'' +
            ", password='" + password + '\'' +
            ", firstName='" + firstName + '\'' +
            ", email='" + email + '\'' +
            ", imageUrl='" + imageUrl + '\'' +
            ", activated=" + activated +
            ", langKey='" + langKey + '\'' +
            ", createdBy='" + createdBy + '\'' +
            ", createdDate=" + createdDate +
            ", lastModifiedBy='" + lastModifiedBy + '\'' +
            ", lastModifiedDate=" + lastModifiedDate +
            ", departmentId=" + departmentId +
            ", departmentName='" + departmentName + '\'' +
            ", authorities=" + authorities +
            ", type='" + type + '\'' +
            ", internalNumber='" + internalNumber + '\'' +
            ", internalNumberPassword='" + internalNumberPassword + '\'' +
            ", serverVoice='" + serverVoice + '\'' +
            ", connectionChannel='" + connectionChannel + '\'' +
            ", departments=" + Arrays.toString(departments) +
            '}';
    }
}
