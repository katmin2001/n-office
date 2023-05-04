package com.fis.crm.service.dto;

import java.util.List;

public class WSResReportComplaintDTO {
    private List<ReportComplaintDetailDTO> results;
    private Long totalRow;

    public List<ReportComplaintDetailDTO> getResults() {
        return results;
    }

    public void setResults(List<ReportComplaintDetailDTO> results) {
        this.results = results;
    }

    public Long getTotalRow() {
        return totalRow;
    }

    public void setTotalRow(Long totalRow) {
        this.totalRow = totalRow;
    }
}
