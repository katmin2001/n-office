package com.fis.crm.service.dto;

import java.util.List;

public class WSResProductivityDTO {
    private List<ProductivityReportDTO> results;
    private Long totalRow;

    public List<ProductivityReportDTO> getResults() {
        return results;
    }

    public void setResults(List<ProductivityReportDTO> results) {
        this.results = results;
    }

    public Long getTotalRow() {
        return totalRow;
    }

    public void setTotalRow(Long totalRow) {
        this.totalRow = totalRow;
    }
}
