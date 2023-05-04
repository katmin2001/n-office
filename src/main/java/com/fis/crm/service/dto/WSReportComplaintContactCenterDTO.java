package com.fis.crm.service.dto;

import java.util.List;

public class WSReportComplaintContactCenterDTO {
    private List<ReportComplaintContactCenterDTO> results;
    private Long totalRow;

    public List<ReportComplaintContactCenterDTO> getResults() {
        return results;
    }

    public void setResults(List<ReportComplaintContactCenterDTO> results) {
        this.results = results;
    }

    public Long getTotalRow() {
        return totalRow;
    }

    public void setTotalRow(Long totalRow) {
        this.totalRow = totalRow;
    }
}
