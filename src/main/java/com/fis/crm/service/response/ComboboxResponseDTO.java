package com.fis.crm.service.response;

public class ComboboxResponseDTO {

    private Object value;
    private String label;

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }
}
