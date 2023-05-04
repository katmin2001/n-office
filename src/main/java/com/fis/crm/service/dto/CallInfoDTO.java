package com.fis.crm.service.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public class CallInfoDTO {
    private String numberPhone;
    private String extension;
    private String direction;
    private String callId;
    @JsonFormat
        (shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy hh:mm:ss", timezone = "Asia/Ho_Chi_Minh")
    private Date timeStart;
    private String ringingTime;
    private String talkingTime;
    private String callStatus;
    private String recording;
    private int duration;

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

    public String getCallId() {
        return callId;
    }

    public void setCallId(String callId) {
        this.callId = callId;
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
