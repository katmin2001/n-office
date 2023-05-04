package com.fis.crm.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * A EvaluateResultResponse.
 */
@Entity
@Table(name = "evaluate_result_response")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class EvaluateResultResponse implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "EVALUATE_RESULT_RESPONSE_GEN" )
    @SequenceGenerator(name = "EVALUATE_RESULT_RESPONSE_GEN", sequenceName = "EVALUATE_RESULT_RESPONSE_SEQ", allocationSize = 1)
    private Long id;

    @Column(name = "evaluate_result_id")
    private Long evaluateResultId;

    @Column(name = "content")
    private String content;

//    @Column(name = "create_user")
//    private Long createUser;

    @ManyToOne
    @JoinColumn(name = "create_user")
    private User user;

    @Column(name = "create_datetime")
    private Date createDatetime;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getEvaluateResultId() {
        return evaluateResultId;
    }

    public EvaluateResultResponse evaluateResultId(Long evaluateResultId) {
        this.evaluateResultId = evaluateResultId;
        return this;
    }

    public void setEvaluateResultId(Long evaluateResultId) {
        this.evaluateResultId = evaluateResultId;
    }

    public String getContent() {
        return content;
    }

    public EvaluateResultResponse content(String content) {
        this.content = content;
        return this;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Date getCreateDatetime() {
        return createDatetime;
    }

    public EvaluateResultResponse createDatetime(Date createDatetime) {
        this.createDatetime = createDatetime;
        return this;
    }

    public void setCreateDatetime(Date createDatetime) {
        this.createDatetime = createDatetime;
    }
}
