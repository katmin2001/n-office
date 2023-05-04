package com.fis.crm.service;

import com.fis.crm.service.dto.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ExportReportService {
    List<ReportComplaintDetailDTO> getReportComplaintDetail(RequestReportTicketDTO requestReportTicketDTO);

    Page<ReportComplaintDetailDTO> getReportComplaintWeb(RequestReportTicketDTO requestReportTicketDTO, Pageable pageable);

    List<ReportProcessTimeDTO> getReportProcessTime(RequestReportTimeProcessDTO requestReportTimeProcessDTO);

    Page<ReportProcessTimeDTO> getReportProcessTimeWeb(RequestReportTimeProcessDTO requestReportTimeProcessDTO, Pageable pageable);

    List<ReportComplaintContactCenterDTO> getReportComplaintContactCenter(RequestReportComplaintContactCenterDTO requestReportComplaintContactCenterDTO);

    Page<ReportComplaintContactCenterDTO> getReportComplaintContactCenterWeb(RequestReportComplaintContactCenterDTO requestReportComplaintContactCenterDTO, Pageable pageable);

    Page<ProductivityReportDTO> getProductivityWeb(RequestProductivityDTO requestProductivityDTO, Pageable pageable);

    List<ProductivityReportDTO> getProductivity(RequestProductivityDTO requestProductivityDTO);

    Page<ProductivityReportDetailDTO> getLstProductivityDetailPage(RequestProductivityDTO requestProductivityDTO, Pageable pageable);

    List<ProductivityReportDetailDTO> getLstProductivityDetail(RequestProductivityDTO requestProductivityDTO);

}
