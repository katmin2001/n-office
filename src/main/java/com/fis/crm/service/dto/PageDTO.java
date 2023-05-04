package com.fis.crm.service.dto;

public class PageDTO {
    private Long offSet;
    private int pageSize;

    public Long getOffSet() {
        return offSet;
    }

    public void setOffSet(Long offSet) {
        this.offSet = offSet;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }
}
