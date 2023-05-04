package com.fis.crm.repository;

import com.fis.crm.service.dto.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReportBaseRepository {
    WSResReportComplaintDTO getReportComplaintDetail(RequestReportTicketDTO requestReportTicketDTO);

    WSResReportProcessTimeDTO getReportProcessTime(RequestReportTimeProcessDTO reportTimeProcessDTO);

    WSReportComplaintContactCenterDTO getReportComplaintContactCenter(RequestReportComplaintContactCenterDTO requestReportComplaintContactCenterDTO);

    WSResProductivityDTO getProductivity(RequestProductivityDTO requestProductivityDTO);

    Page<ProductivityReportDetailDTO> getLstProductivityDetailPage(RequestProductivityDTO requestProductivityDTO, Pageable pageable);

    List<ProductivityReportDetailDTO> getLstProductivityDetail(RequestProductivityDTO requestProductivityDTO);

}
