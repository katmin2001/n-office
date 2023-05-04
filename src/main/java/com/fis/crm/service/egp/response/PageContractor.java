package com.fis.crm.service.egp.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "content",
    "totalPages",
    "pageSize",
    "currentPage",
    "totalElements",
    "sort",
    "first",
    "last",
    "empty"
})
public class PageContractor<T> {

    @JsonProperty("content")
    private List<T> content = null;
    @JsonProperty("totalPages")
    private Integer totalPages;
    @JsonProperty("pageSize")
    private Integer pageSize;
    @JsonProperty("currentPage")
    private Integer currentPage;
    @JsonProperty("totalElements")
    private Integer totalElements;
    @JsonProperty("sort")
    private Sort sort;
    @JsonProperty("first")
    private Boolean first;
    @JsonProperty("last")
    private Boolean last;
    @JsonProperty("empty")
    private Boolean empty;

    public List<T> getContent() {
        return content;
    }

    public void setContent(List<T> content) {
        this.content = content;
    }

    public Integer getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(Integer totalPages) {
        this.totalPages = totalPages;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(Integer currentPage) {
        this.currentPage = currentPage;
    }

    public Integer getTotalElements() {
        return totalElements;
    }

    public void setTotalElements(Integer totalElements) {
        this.totalElements = totalElements;
    }

    public Sort getSort() {
        return sort;
    }

    public void setSort(Sort sort) {
        this.sort = sort;
    }

    public Boolean getFirst() {
        return first;
    }

    public void setFirst(Boolean first) {
        this.first = first;
    }

    public Boolean getLast() {
        return last;
    }

    public void setLast(Boolean last) {
        this.last = last;
    }

    public Boolean getEmpty() {
        return empty;
    }

    public void setEmpty(Boolean empty) {
        this.empty = empty;
    }

    public PageContractor(List<T> content, Integer totalPages, Integer pageSize, Integer currentPage, Integer totalElements, Sort sort, Boolean first, Boolean last, Boolean empty) {
        this.content = content;
        this.totalPages = totalPages;
        this.pageSize = pageSize;
        this.currentPage = currentPage;
        this.totalElements = totalElements;
        this.sort = sort;
        this.first = first;
        this.last = last;
        this.empty = empty;
    }

    public PageContractor() {
    }

    @Override
    public String toString() {
        return "PageContractor{" +
            "content=" + content +
            ", totalPages=" + totalPages +
            ", pageSize=" + pageSize +
            ", currentPage=" + currentPage +
            ", totalElements=" + totalElements +
            ", sort=" + sort +
            ", first=" + first +
            ", last=" + last +
            ", empty=" + empty +
            '}';
    }
}
