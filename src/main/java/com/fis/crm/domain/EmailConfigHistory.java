package com.fis.crm.domain;

import javax.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "EMAIL_CONFIG_HISTORY")
public class EmailConfigHistory {
    @Id
    @Column(name = "ID", unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "EMAIL_CONFIG_HISTORY_GEN")
    @SequenceGenerator(name = "EMAIL_CONFIG_HISTORY_GEN", sequenceName = "EMAIL_CONFIG_HISTORY_SEQ", allocationSize = 1)
    private Long id;

    @Column(name = "HOST")
    private String host;

    @Column(name = "PORT")
    private String port;

    @Column(name = "EMAIL")
    private String email;

    @Column(name = "PASSWORD")
    private String password;

    @Column(name = "SSL")
    private String ssl;

    @Column(name = "CREATE_DATETIME")
    private Instant createDateTime;

    @Column(name = "CREATE_USER")
    private Long createUser;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSsl() {
        return ssl;
    }

    public void setSsl(String ssl) {
        this.ssl = ssl;
    }

    public Instant getCreateDateTime() {
        return createDateTime;
    }

    public void setCreateDateTime(Instant createDateTime) {
        this.createDateTime = createDateTime;
    }

    public Long getCreateUser() {
        return createUser;
    }

    public void setCreateUser(Long createUser) {
        this.createUser = createUser;
    }
}
