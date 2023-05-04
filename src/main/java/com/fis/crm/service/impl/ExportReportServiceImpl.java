package com.fis.crm.service.impl;

import com.fis.crm.repository.ReportBaseRepository;
import com.fis.crm.service.ExportReportService;
import com.fis.crm.service.dto.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ExportReportServiceImpl implements ExportReportService {
    @Autowired
    ReportBaseRepository reportBaseRepository;

    @Override
    public List<ReportComplaintDetailDTO> getReportComplaintDetail(RequestReportTicketDTO requestReportTicketDTO) {
        WSResReportComplaintDTO wsResReportComplaintDTO = reportBaseRepository.getReportComplaintDetail(requestReportTicketDTO);
        return wsResReportComplaintDTO.getResults();
    }

    @Override
    public Page<ReportComplaintDetailDTO> getReportComplaintWeb(RequestReportTicketDTO requestReportTicketDTO, Pageable pageable) {
        WSResReportComplaintDTO wsResReportComplaintDTO = reportBaseRepository.getReportComplaintDetail(requestReportTicketDTO);
        Page<ReportComplaintDetailDTO> page = new PageImpl<>(wsResReportComplaintDTO.getResults(), pageable, wsResReportComplaintDTO.getTotalRow());
        return page;
    }

    @Override
    public List<ReportProcessTimeDTO> getReportProcessTime(RequestReportTimeProcessDTO requestReportTimeProcessDTO) {
        WSResReportProcessTimeDTO wsResReportProcessTimeDTO = reportBaseRepository.getReportProcessTime(requestReportTimeProcessDTO);
        return wsResReportProcessTimeDTO.getResults();
    }

    @Override
    public Page<ReportProcessTimeDTO> getReportProcessTimeWeb(RequestReportTimeProcessDTO requestReportTimeProcessDTO, Pageable pageable) {
        WSResReportProcessTimeDTO wsResReportProcessTimeDTO = reportBaseRepository.getReportProcessTime(requestReportTimeProcessDTO);
        Page<ReportProcessTimeDTO> page = new PageImpl<>(wsResReportProcessTimeDTO.getResults(), pageable, wsResReportProcessTimeDTO.getTotalRow());
        return page;
    }

    @Override
    public List<ReportComplaintContactCenterDTO> getReportComplaintContactCenter(RequestReportComplaintContactCenterDTO requestReportComplaintContactCenterDTO) {
        WSReportComplaintContactCenterDTO wsReportComplaintContactCenterDTO = reportBaseRepository.getReportComplaintContactCenter(requestReportComplaintContactCenterDTO);
        return wsReportComplaintContactCenterDTO.getResults();
    }

    @Override
    public Page<ReportComplaintContactCenterDTO> getReportComplaintContactCenterWeb(RequestReportComplaintContactCenterDTO requestReportComplaintContactCenterDTO, Pageable pageable) {
        WSReportComplaintContactCenterDTO wsReportComplaintContactCenterDTO = reportBaseRepository.getReportComplaintContactCenter(requestReportComplaintContactCenterDTO);
        Page<ReportComplaintContactCenterDTO> page = new PageImpl<>(wsReportComplaintContactCenterDTO.getResults(), pageable, wsReportComplaintContactCenterDTO.getTotalRow());
        return page;
    }

    @Override
    public List<ProductivityReportDTO> getProductivity(RequestProductivityDTO requestProductivityDTO) {
        WSResProductivityDTO wsResProductivityDTO = reportBaseRepository.getProductivity(requestProductivityDTO);
        return wsResProductivityDTO.getResults();
    }

    @Override
    public Page<ProductivityReportDTO> getProductivityWeb(RequestProductivityDTO requestProductivityDTO, Pageable pageable) {
        WSResProductivityDTO wsResProductivityDTO = reportBaseRepository.getProductivity(requestProductivityDTO);
        Page<ProductivityReportDTO> page = new PageImpl<>(wsResProductivityDTO.getResults(), pageable, wsResProductivityDTO.getTotalRow());
        return page;
    }

    public Page<ProductivityReportDetailDTO> getLstProductivityDetailPage(RequestProductivityDTO requestProductivityDTO, Pageable pageable) {
        return reportBaseRepository.getLstProductivityDetailPage(requestProductivityDTO, pageable);
    }

    public List<ProductivityReportDetailDTO> getLstProductivityDetail(RequestProductivityDTO requestProductivityDTO) {
        return reportBaseRepository.getLstProductivityDetail(requestProductivityDTO);
    }
}
