package com.fis.crm.service.dto;

import java.util.Date;
import java.util.List;

public class PageableDTO {

    private Integer pageNumber;
    private Integer pageSize;
    private String sort;

    public PageableDTO(){

    }

    public PageableDTO(Integer pageNumber, Integer pageSize, String sort) {
        this.pageNumber = pageNumber;
        this.pageSize = pageSize;
        this.sort = sort;
    }

    public Integer getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(Integer pageNumber) {
        this.pageNumber = pageNumber;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }
}
