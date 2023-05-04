package com.fis.crm.service.dto;

import java.util.List;

public class AnswersDTO {
    private Long id;
    private String type;
    private String value;
    private String content;
    private Integer minValue;
    private Integer maxValue;
    private List<AnswersDTO> radioValues;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public List<AnswersDTO> getRadioValues() {
        return radioValues;
    }

    public void setRadioValues(List<AnswersDTO> radioValues) {
        this.radioValues = radioValues;
    }

    public Integer getMinValue() {
        return minValue;
    }

    public void setMinValue(Integer minValue) {
        this.minValue = minValue;
    }

    public Integer getMaxValue() {
        return maxValue;
    }

    public void setMaxValue(Integer maxValue) {
        this.maxValue = maxValue;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}


