package com.fis.crm.commons;


public class ExcelColumn {
    public enum DATA_TYPE {
        TEXT, DOUBLE, INT, DATE, DATE_TIME
    }

    public enum ALIGN_MENT {
        LEFT, RIGHT, CENTER
    }

    public ExcelColumn() {
    }

    public ExcelColumn(String column, String title, ALIGN_MENT align) {
        this.column = column;
        this.title = title;
        this.align = align;
    }

    private String column;
    private String title;
    private DATA_TYPE type;
    private ALIGN_MENT align;

    public ALIGN_MENT getAlign() {
        return align;
    }

    public void setAlign(ALIGN_MENT align) {
        this.align = align;
    }

    public String getColumn() {
        return column;
    }

    public void setColumn(String column) {
        this.column = column;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public DATA_TYPE getType() {
        return type;
    }

    public void setType(DATA_TYPE type) {
        this.type = type;
    }
}
