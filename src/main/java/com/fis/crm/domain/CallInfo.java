package com.fis.crm.domain;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "CALL_INFORMATION")
public class CallInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CALL_INFORMATION_GEN")
    @SequenceGenerator(name = "CALL_INFORMATION_GEN", sequenceName = "CALL_INFORMATION_SEQ", allocationSize = 1)
    @Column(name = "ID", nullable = false)
    private Long id;

    @Column(name = "number_phone", nullable = false)
    private String numberPhone;

    @Column(name = "extension")
    private String extension;

    @Column(name = "direction")
    private String direction;

    @Column(name = "call_id")
    private String callId;

    @Column(name = "time_start")
    private Date timeStart;

    @Column(name = "ringing_time")
    private String ringingTime;

    @Column(name = "talking_time")
    private String talkingTime;

    @Column(name = "call_status")
    private String callStatus;

    @Column(name = "recording")
    private String recording;

    @Column(name = "duration")
    private int duration;

    public String getCallId() {
        return callId;
    }

    public void setCallId(String callId) {
        this.callId = callId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumberPhone() {
        return numberPhone;
    }

    public void setNumberPhone(String numberPhone) {
        this.numberPhone = numberPhone;
    }

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public Date getTimeStart() {
        return timeStart;
    }

    public void setTimeStart(Date timeStart) {
        this.timeStart = timeStart;
    }

    public String getRingingTime() {
        return ringingTime;
    }

    public void setRingingTime(String ringingTime) {
        this.ringingTime = ringingTime;
    }

    public String getTalkingTime() {
        return talkingTime;
    }

    public void setTalkingTime(String talkingTime) {
        this.talkingTime = talkingTime;
    }

    public String getCallStatus() {
        return callStatus;
    }

    public void setCallStatus(String callStatus) {
        this.callStatus = callStatus;
    }

    public String getRecording() {
        return recording;
    }

    public void setRecording(String recording) {
        this.recording = recording;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }
}
