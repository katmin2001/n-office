package com.fis.crm.service.dto;

public class ExcelTitle {
    private String title;
    private String dateExportTitle;
    private String dateExportPattern;

    public ExcelTitle() {
    }

    public ExcelTitle(String title, String dateExportTitle, String dateExportPattern) {
        this.title = title;
        this.dateExportTitle = dateExportTitle;
        this.dateExportPattern = dateExportPattern;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDateExportTitle() {
        return dateExportTitle;
    }

    public void setDateExportTitle(String dateExportTitle) {
        this.dateExportTitle = dateExportTitle;
    }

    public String getDateExportPattern() {
        return dateExportPattern;
    }

    public void setDateExportPattern(String dateExportPattern) {
        this.dateExportPattern = dateExportPattern;
    }
}
