package com.fis.crm.service.dto;


import com.fis.crm.config.Constants;

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
        this.pattern = "dd/MM/yyyy HH:mm:ss";
        this.columnWidth = Constants.WIDTH * 30;
    }

    public ExcelColumn(String column, String title, ALIGN_MENT align, String pattern) {
        this.column = column;
        this.title = title;
        this.align = align;
        this.pattern = pattern;
        this.columnWidth = Constants.WIDTH * 30;
    }

    public ExcelColumn(String column, String title, ALIGN_MENT align, Integer columnWidth) {
        this.column = column;
        this.title = title;
        this.align = align;
        this.pattern = "dd/MM/yyyy HH:mm:ss";
        this.columnWidth = Constants.WIDTH * columnWidth;
    }

    private String column;
    private String title;
    private String pattern;
    private DATA_TYPE type;
    private ALIGN_MENT align;
    private Integer columnWidth;

    public Integer getColumnWidth() {
        return columnWidth;
    }

    public void setColumnWidth(Integer columnWidth) {
        this.columnWidth = columnWidth;
    }

    public String getPattern() {
        return pattern;
    }

    public void setPattern(String pattern) {
        this.pattern = pattern;
    }

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
