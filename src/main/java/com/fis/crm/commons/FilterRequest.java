package com.fis.crm.commons;

import java.io.Serializable;
import java.util.Objects;

//x_1205_3 import jxl.demo.XML;

public class FilterRequest implements Serializable {

    public enum Operator {
        IN,
        EQ,
        NE,
        NOTIN,
        EQALL,
        AQANY,
        AS,
        LT,
        GT,
        GT_DATE,
        LT_DATE,
        LOE,
        GOE,
        BETWEEN,
        LIKE,
        EXACT,
        LIKE_BEGIN,
        LIKE_END,
        RANGE,
        STARTWITH,
        TRUNC_DAY_LOE,
        IS_NULL,
        IS_NOT_NULL
    }

    private String property;
    private String entity;
    private Object value;
    private boolean extract = true;
    private boolean notEqual;
    private boolean isNull;
    private boolean forLookup;
    private Operator operator;
    private String valueType;
    private String valueText;

    private boolean valueInRange; //Tham so truyen vao la 1 RangeValue
    private boolean valueInList; //Tham so truyen vao la 1 list

    @Override
    public int hashCode() {
        return Objects.hash(property, value, operator);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        final FilterRequest other = (FilterRequest) obj;
        return Objects.equals(this.property, other.property) && Objects.equals(this.value, other.value) && Objects.equals(this.operator, other.operator);
    }

    //thiendn1
    public FilterRequest(String property, Operator operator, Object value) {
        this.property = property;
        this.value = value;
        this.operator = operator;
    }

    public FilterRequest(String property, Operator operator, Object value, boolean extract) {
        this.property = property;
        this.value = value;
        this.extract = extract;
        this.operator = operator;
    }

    public FilterRequest(String property, Object value) {
        super();
        this.property = property;
        this.value = value;
    }

    //toan tu mot ngoi
    public  FilterRequest(String property,Operator operator){
        this.property= property;
        this.operator = operator;
        this.value = "unary_operator";
    }

    public FilterRequest(String property, Object value, boolean extract) {
        super();
        this.property = property;
        this.value = value;
        this.extract = extract;
    }

    public FilterRequest(String property, Object value, boolean extract, boolean isNull) {
        super();
        this.property = property;
        this.value = value;
        this.extract = extract;
        this.isNull = isNull;
    }

    public FilterRequest() {
    }

    public Enum getColumnProperty(Class refClass) {
        return Enum.valueOf(refClass, property.toUpperCase());
    }

    public String getProperty() {
        return property;
    }

    public Object getValue() {
        return value;
    }

    public void setProperty(String property) {
        this.property = property;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "Property: " + property + " -- Value: " + value;
    }

    public boolean isExtract() {
        return extract;
    }

    public void setExtract(boolean extract) {
        this.extract = extract;
    }

    public boolean isIsNull() {
        return isNull;
    }

    public void setIsNull(boolean isNull) {
        this.isNull = isNull;
    }

    public boolean isNotEqual() {
        return notEqual;
    }

    public void setNotEqual(boolean notEqual) {
        this.notEqual = notEqual;
    }

    public Operator getOperator() {
        return operator;
    }

    public void setOperator(Operator operator) {
        this.operator = operator;
    }

    public boolean isForLookup() {
        return forLookup;
    }

    public void setForLookup(boolean forLookup) {
        this.forLookup = forLookup;
    }

    public boolean isValueInRange() {
        return valueInRange;
    }

    public void setValueInRange(boolean valueInRange) {
        this.valueInRange = valueInRange;
    }

    public boolean isValueInList() {
        return valueInList;
    }

    public void setValueInList(boolean valueInList) {
        this.valueInList = valueInList;
    }

    public String getEntity() {
        return entity;
    }

    public void setEntity(String entity) {
        this.entity = entity;
    }

    public String getValueType() {
        return valueType;
    }

    public void setValueType(String valueType) {
        this.valueType = valueType;
    }

    public String getValueText() {
        return valueText;
    }

    public void setValueText(String valueText) {
        this.valueText = valueText;
    }

}
