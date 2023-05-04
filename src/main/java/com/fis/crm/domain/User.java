package com.fis.crm.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fis.crm.config.Constants;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

/**
 * A user.
 */
@Entity
@Table(name = "jhi_user")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class User extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "id", unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "JHI_USER_SEQ_GEN")
    @SequenceGenerator(name = "JHI_USER_SEQ_GEN", sequenceName = "JHI_USER_SEQ", allocationSize = 1)
    private Long id;

    @NotNull
    @Pattern(regexp = Constants.LOGIN_REGEX)
    @Size(min = 1, max = 50)
    @Column(length = 50, unique = true, nullable = false)
    private String login;

    @JsonIgnore
    @NotNull
    @Size(min = 60, max = 60)
    @Column(name = "password_hash", length = 60, nullable = false)
    private String password;

    @Size(max = 50)
    @Column(name = "first_name", length = 50)
    private String firstName;

    @Email
    @Size(min = 5, max = 254)
    @Column(length = 254, unique = true)
    private String email;

    @NotNull
    @Column(nullable = false)
    private Boolean activated = false;

    @Size(min = 2, max = 10)
    @Column(name = "lang_key", length = 10)
    private String langKey;

    @Size(max = 256)
    @Column(name = "image_url", length = 256)
    private String imageUrl;

    @Size(max = 20)
    @Column(name = "activation_key", length = 20)
    @JsonIgnore
    private String activationKey;

    @Size(max = 20)
    @Column(name = "reset_key", length = 20)
    @JsonIgnore
    private String resetKey;

    @Column(name = "reset_date")
    private Instant resetDate = null;

    @Column(name = "create_ticket")
    private Boolean createTicket;

    @Column(name = "process_Ticket")
    private Boolean processTicket;

    @Column(name = "confirm_Ticket")
    private Boolean confirmTicket;

    @Column(name = "department_id")
    private Long departmentId;

    @Column(name = "type")
    private String type;

    @Column(name = "internal_number")
    private String internalNumber;

    @Column(name = "internal_number_password")
    private String internalNumberPassword;

    @Column(name = "server_voice")
    private String serverVoice;

    @Column(name = "connection_channel")
    private String connectionChannel;

    @Column(name = "extend_val")
    private String extendVal;

    @JsonIgnore
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "jhi_user_authority",
        joinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "id")},
        inverseJoinColumns = {@JoinColumn(name = "authority_name", referencedColumnName = "name")}
    )
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @BatchSize(size = 20)
    private Set<Authority> authorities = new HashSet<>();

    @Column(name = "departments")
    private String departments;

    public String getDepartments() {
        return departments;
    }

    public void setDepartments(String departments) {
        this.departments = departments;
    }

    @OneToMany(mappedBy = "user")
    List<UserRole> userRoles;

    @Column(name = "active_datetime")
    private Instant activeDatetime;

    @Column(name = "max_login_fail")
    private Integer maxLoginFail;

    public List<UserRole> getUserRoles() {
        return userRoles;
    }

    public void setUserRoles(List<UserRole> userRoles) {
        this.userRoles = userRoles;
    }

    public Integer getMaxLoginFail() {
        return maxLoginFail;
    }

    public void setMaxLoginFail(Integer maxLoginFail) {
        this.maxLoginFail = maxLoginFail;
    }

    public Instant getActiveDatetime() {
        return activeDatetime;
    }

    public void setActiveDatetime(Instant activeDatetime) {
        this.activeDatetime = activeDatetime;
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

    // Lowercase the login before saving it in database
    public void setLogin(String login) {
        this.login = StringUtils.upperCase(login, Locale.ENGLISH);
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public Boolean isActivated() {
        return activated;
    }

    public void setActivated(Boolean activated) {
        this.activated = activated;
    }

    public String getActivationKey() {
        return activationKey;
    }

    public void setActivationKey(String activationKey) {
        this.activationKey = activationKey;
    }

    public String getResetKey() {
        return resetKey;
    }

    public void setResetKey(String resetKey) {
        this.resetKey = resetKey;
    }

    public Instant getResetDate() {
        return resetDate;
    }

    public void setResetDate(Instant resetDate) {
        this.resetDate = resetDate;
    }

    public Boolean isCreateTicket() {
        return createTicket;
    }

    public Long getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Long departmentId) {
        this.departmentId = departmentId;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Boolean getActivated() {
        return activated;
    }

    public Boolean getCreateTicket() {
        return createTicket;
    }

    public Boolean getProcessTicket() {
        return processTicket;
    }

    public Boolean getConfirmTicket() {
        return confirmTicket;
    }

    public void setCreateTicket(Boolean createTicket) {
        this.createTicket = createTicket;
    }

    public Boolean isProcessTicket() {
        return processTicket;
    }

    public void setProcessTicket(Boolean processTicket) {
        this.processTicket = processTicket;
    }

    public Boolean isConfirmTicket() {
        return confirmTicket;
    }

    public void setConfirmTicket(Boolean confirmTicket) {
        this.confirmTicket = confirmTicket;
    }

    public String getLangKey() {
        return langKey;
    }

    public void setLangKey(String langKey) {
        this.langKey = langKey;
    }

    public Set<Authority> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(Set<Authority> authorities) {
        this.authorities = authorities;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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

    public String getExtendVal() {
        return extendVal;
    }

    public void setExtendVal(String extendVal) {
        this.extendVal = extendVal;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof User)) {
            return false;
        }
        return id != null && id.equals(((User) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    @Override
    public String toString() {
        return "User{" +
            "id=" + id +
            ", login='" + login + '\'' +
            ", password='" + password + '\'' +
            ", firstName='" + firstName + '\'' +
            ", email='" + email + '\'' +
            ", activated=" + activated +
            ", langKey='" + langKey + '\'' +
            ", imageUrl='" + imageUrl + '\'' +
            ", activationKey='" + activationKey + '\'' +
            ", resetKey='" + resetKey + '\'' +
            ", resetDate=" + resetDate +
            ", createTicket=" + createTicket +
            ", processTicket=" + processTicket +
            ", confirmTicket=" + confirmTicket +
            ", departmentId=" + departmentId +
            ", type='" + type + '\'' +
            ", internalNumber='" + internalNumber + '\'' +
            ", internalNumberPassword='" + internalNumberPassword + '\'' +
            ", serverVoice='" + serverVoice + '\'' +
            ", connectionChannel='" + connectionChannel + '\'' +
            ", authorities=" + authorities +
            ", departments='" + departments + '\'' +
            '}';
    }
}
