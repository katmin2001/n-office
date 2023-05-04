package com.fis.crm.service.dto;

import java.util.List;

public class WSResReportProcessTimeDTO {
    private List<ReportProcessTimeDTO> results;
    private Long totalRow;

    public List<ReportProcessTimeDTO> getResults() {
        return results;
    }

    public void setResults(List<ReportProcessTimeDTO> results) {
        this.results = results;
    }

    public Long getTotalRow() {
        return totalRow;
    }

    public void setTotalRow(Long totalRow) {
        this.totalRow = totalRow;
    }
}
