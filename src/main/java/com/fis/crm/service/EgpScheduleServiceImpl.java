package com.fis.crm.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fis.crm.commons.DataUtil;
import com.fis.crm.domain.CompanySuspend;
import com.fis.crm.domain.ContractorsSuspend;
import com.fis.crm.domain.CustomerRegister;
import com.fis.crm.domain.CustomerRegisterReserve;
import com.fis.crm.repository.CompanySuspendRepository;
import com.fis.crm.repository.ContractorsSuspendRepository;
import com.fis.crm.repository.CustomerRegisterRepository;
import com.fis.crm.repository.CustomerRegisterReserveRepository;
import com.fis.crm.service.egp.EGPResponse;
import com.fis.crm.service.egp.request.ContractorSuspended;
import com.fis.crm.service.egp.request.Criteria;
import com.fis.crm.service.egp.request.FeePendingDate;
import com.fis.crm.service.egp.request.PmContractorStatus;
import com.fis.crm.service.egp.request.ReportContractorSuspended;
import com.fis.crm.service.egp.request.ReportRegister;
import com.fis.crm.service.egp.request.RequestRegister;
import com.fis.crm.service.egp.request.Status;
import com.fis.crm.service.egp.response.ApproveContent;
import com.fis.crm.service.egp.response.PageContractor;
import com.fis.crm.service.egp.response.PmContractStatusContent;
import com.fis.crm.service.egp.response.PmContractorStatusData;
import com.fis.crm.service.egp.response.ReportContractorSuspendedContent;
import com.fis.crm.service.egp.response.ReportContractorSuspendedData;
import com.fis.crm.service.egp.response.ReportRegisterData;
import com.fis.crm.service.egp.response.ReserveContent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.TimeZone;

@Service
@Transactional
public class EgpScheduleServiceImpl implements EgpScheduleService {

    private final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");

    private Logger log = LoggerFactory.getLogger(EgpScheduleServiceImpl.class);

    private final EgpService egpService;

    private final CompanySuspendRepository companySuspendRepository;

    private final ContractorsSuspendRepository contractorsSuspendRepository;

    private final CustomerRegisterRepository customerRegisterRepository;

    private final CustomerRegisterReserveRepository customerRegisterReserveRepository;

    private final ObjectMapper objectMapper;

    public EgpScheduleServiceImpl(EgpService egpService, CompanySuspendRepository companySuspendRepository, ContractorsSuspendRepository contractorsSuspendRepository, CustomerRegisterRepository customerRegisterRepository, CustomerRegisterReserveRepository customerRegisterReserveRepository, ObjectMapper objectMapper) {
        this.egpService = egpService;
        this.companySuspendRepository = companySuspendRepository;
        this.contractorsSuspendRepository = contractorsSuspendRepository;
        this.customerRegisterRepository = customerRegisterRepository;
        this.customerRegisterReserveRepository = customerRegisterReserveRepository;
        this.objectMapper = objectMapper;
    }

    @Override
    public void getReportContractorSuspended() throws Exception {
        FeePendingDate feePendingDate = new FeePendingDate();
        Instant to = Instant.now();
        int hour = to.atZone(ZoneOffset.UTC).getHour();
        to = to.atZone(ZoneOffset.UTC)
            .withHour(hour).withMinute(0).withSecond(0).toInstant();
        Instant from = to.minus(1, ChronoUnit.HOURS);
        log.debug("From date >>> " + from);
        log.debug("To date >>> " + to);
        feePendingDate.setLessThanOrEqual(to.toString());
        feePendingDate.setGreaterThanOrEqual(from.toString());

        ContractorSuspended contractorSuspended = new ContractorSuspended();
        contractorSuspended.setFeePendingDate(feePendingDate);

        ReportContractorSuspended reportContractorSuspended = new ReportContractorSuspended();
        reportContractorSuspended.setContractorSuspended(contractorSuspended);
        reportContractorSuspended.setPageNumber("0");
        reportContractorSuspended.setPageSize(500);
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
        Optional<EGPResponse<ReportContractorSuspendedData>> result = egpService.reportContractorSuspended(reportContractorSuspended);
        if (result.isPresent()) {
            ReportContractorSuspendedData data = objectMapper.convertValue(result.get().getData(), ReportContractorSuspendedData.class);
            PageContractor<ReportContractorSuspendedContent> pageContractor = data.getPage();
            int i = 0;
            while (i <= (pageContractor.getTotalPages() - 1)) {
                if (i == 0) {
                    List<ReportContractorSuspendedContent> reportContractorSuspendedContentList = pageContractor.getContent();
                    for (ReportContractorSuspendedContent reportContractorSuspendedContent : reportContractorSuspendedContentList) {
                        CompanySuspend companySuspend = new CompanySuspend();
                        companySuspend.setCid(reportContractorSuspendedContent.getOrgCode());
                        companySuspend.setTaxCode(reportContractorSuspendedContent.getTaxCode());
                        companySuspend.setName(reportContractorSuspendedContent.getOrgFullName());
                        companySuspend.setReason(reportContractorSuspendedContent.getReason());
                        companySuspend.setSuspendTime(simpleDateFormat.parse(reportContractorSuspendedContent.getCreatedDate()));
                        companySuspend.setInsertDate(new Date());
                        companySuspendRepository.save(companySuspend);
                    }
                } else {
                    reportContractorSuspended.setPageNumber(String.valueOf(i));
                    Optional<EGPResponse<ReportContractorSuspendedData>> result1 = egpService.reportContractorSuspended(reportContractorSuspended);
                    if (result1.isPresent()) {
                        ReportContractorSuspendedData data1 = objectMapper.convertValue(result1.get().getData(), ReportContractorSuspendedData.class);
                        PageContractor<ReportContractorSuspendedContent> pageContractor1 = data1.getPage();
                        List<ReportContractorSuspendedContent> reportContractorSuspendedContentList1 = pageContractor1.getContent();
                        for (ReportContractorSuspendedContent reportContractorSuspendedContent : reportContractorSuspendedContentList1) {
                            CompanySuspend companySuspend = new CompanySuspend();
                            companySuspend.setCid(reportContractorSuspendedContent.getOrgCode());
                            companySuspend.setTaxCode(reportContractorSuspendedContent.getTaxCode());
                            companySuspend.setName(reportContractorSuspendedContent.getOrgFullName());
                            companySuspend.setReason(reportContractorSuspendedContent.getReason());
                            companySuspend.setSuspendTime(simpleDateFormat.parse(reportContractorSuspendedContent.getCreatedDate()));
                            companySuspend.setInsertDate(new Date());
                            companySuspendRepository.save(companySuspend);
                        }
                    }
                }
                i++;
            }
        }
        log.debug("Success");
    }

    @Override
    public void getPmContractorStatus() throws Exception {
        FeePendingDate feePendingDate = new FeePendingDate();
        Instant to = Instant.now();
        int hour = to.atZone(ZoneOffset.UTC).getHour();
        to = to.atZone(ZoneOffset.UTC)
            .withHour(hour).withMinute(0).withSecond(0).toInstant();
//        to = to.minus(1, ChronoUnit.HOURS);
        Instant from = to.minus(1, ChronoUnit.HOURS);
//        Instant from = to.minus(90, ChronoUnit.DAYS);
        log.debug("From date >>> " + from);
        log.debug("To date >>> " + to);
        feePendingDate.setLessThanOrEqual(to.toString());
        feePendingDate.setGreaterThanOrEqual(from.toString());
        Status status = new Status();
        status.setEquals("1");
        Criteria criteria = new Criteria();
        criteria.setFeePendingDate(feePendingDate);
        criteria.setStatus(status);
        PmContractorStatus pmContractorStatus = new PmContractorStatus();
        pmContractorStatus.setCriteria(criteria);
        pmContractorStatus.setPageNumber("0");
        pmContractorStatus.setPageSize(500);
        Optional<EGPResponse<PmContractorStatusData>> result = egpService.pmContractorStatus(pmContractorStatus);
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
        if (result.isPresent()) {
            PmContractorStatusData pmContractorStatusData = objectMapper.convertValue(result.get().getData(), PmContractorStatusData.class);
            PageContractor<PmContractStatusContent> pageContractor = pmContractorStatusData.getPageContractor();
            int i = 0;
            while (i <= pageContractor.getTotalPages() - 1) {
                if (i == 0) {
                    List<PmContractStatusContent> contractStatusContentList = pageContractor.getContent();
                    for (PmContractStatusContent content : contractStatusContentList) {
                        ContractorsSuspend contractorsSuspend = new ContractorsSuspend();
                        contractorsSuspend.setCid(content.getId());
                        contractorsSuspend.setTaxCode(content.getTaxCode());
                        contractorsSuspend.setName(content.getName());
                        contractorsSuspend.setAddress(String.valueOf(content.getOfficePro()));
                        if (!DataUtil.isNullOrEmpty(content.getDebtFee())) {
                            contractorsSuspend.setFeeType(content.getDebtFee());
                        }
                        if (!DataUtil.isNullOrEmpty(content.getFeePendingDate())) {
                            contractorsSuspend.setSuspendTime(simpleDateFormat.parse(content.getFeePendingDate()));
                        }
                        contractorsSuspend.setInsertDate(new Date());
                        contractorsSuspendRepository.save(contractorsSuspend);
                    }
                } else {
                    pmContractorStatus.setPageNumber(String.valueOf(i));
                    Optional<EGPResponse<PmContractorStatusData>> result1 = egpService.pmContractorStatus(pmContractorStatus);
                    if (result1.isPresent()) {
                        PmContractorStatusData pmContractorStatusData1 = objectMapper.convertValue(result1.get().getData(), PmContractorStatusData.class);
                        PageContractor<PmContractStatusContent> pageContractor1 = pmContractorStatusData1.getPageContractor();
                        List<PmContractStatusContent> contractStatusContentList1 = pageContractor1.getContent();
                        for (PmContractStatusContent content : contractStatusContentList1) {
                            ContractorsSuspend contractorsSuspend = new ContractorsSuspend();
                            contractorsSuspend.setCid(content.getId());
                            contractorsSuspend.setTaxCode(content.getTaxCode());
                            contractorsSuspend.setName(content.getName());
                            contractorsSuspend.setAddress(String.valueOf(content.getOfficePro()));
                            if (!DataUtil.isNullOrEmpty(content.getDebtFee())) {
                                contractorsSuspend.setFeeType(content.getDebtFee());
                            }
                            if (!DataUtil.isNullOrEmpty(content.getFeePendingDate())) {
                                contractorsSuspend.setSuspendTime(simpleDateFormat.parse(content.getFeePendingDate()));
                            }
                            contractorsSuspend.setInsertDate(new Date());
                            contractorsSuspendRepository.save(contractorsSuspend);
                        }
                    }
                }
                i++;
            }
        }
    }

    @Override
    public void getRequestRegister() throws Exception {
        FeePendingDate feePendingDate = new FeePendingDate();
        Instant to = Instant.now();
        int hour = to.atZone(ZoneOffset.UTC).getHour();
        to = to.atZone(ZoneOffset.UTC)
            .withHour(hour).withMinute(0).withSecond(0).toInstant();
//        to = to.minus(1, ChronoUnit.HOURS);
        Instant from = to.minus(1, ChronoUnit.HOURS);
//        Instant from = to.minus(90, ChronoUnit.DAYS);
        log.debug("From date >>> " + from);
        log.debug("To date >>> " + to);
        feePendingDate.setLessThanOrEqual(to.toString());
        feePendingDate.setGreaterThanOrEqual(from.toString());
        ReportRegister reportRegister = new ReportRegister();
        reportRegister.setRequestDate(feePendingDate);
        RequestRegister requestRegister = new RequestRegister();
        requestRegister.setReportRegister(reportRegister);
        requestRegister.setPageNumber("0");
        requestRegister.setPageSize(500);
        Optional<EGPResponse<ReportRegisterData>> result = egpService.getRequestRegister(requestRegister);
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
        if (result.isPresent()) {
            ReportRegisterData reportRegisterData = objectMapper.convertValue(result.get().getData(), ReportRegisterData.class);
            PageContractor<ApproveContent> approveContentPageContractor = reportRegisterData.getApprove();
            int i = 0;
            while (i <= approveContentPageContractor.getTotalPages() - 1) {
                if (i == 0) {
                    List<ApproveContent> approveContentList = approveContentPageContractor.getContent();
                    for (ApproveContent approveContent : approveContentList) {
                        CustomerRegister customerRegister = new CustomerRegister();
                        customerRegister.setRecordsCode(approveContent.getOrgCode());
                        customerRegister.setCid(approveContent.getId());
                        customerRegister.setTaxCode(approveContent.getTaxCode());
                        customerRegister.setName(approveContent.getOrgFullName());
                        customerRegister.setRequestType(approveContent.getType());
                        customerRegister.setSendDate(approveContent.getRequestDate());
                        customerRegister.setApproveDate(approveContent.getApprovedDate());
                        if (!DataUtil.isNullOrEmpty(approveContent.getProcessingTerm())) {
                            customerRegister.setDeadline(String.valueOf(approveContent.getProcessingTerm()));
                        }
                        if (!DataUtil.isNullOrEmpty(approveContent.getCaStatus())) {
                            customerRegister.setCtsStatus(String.valueOf(approveContent.getCaStatus()));
                        }
                        if (!DataUtil.isNullOrEmpty(approveContent.getCaApprovalDate())) {
                            customerRegister.setReleaseTime(simpleDateFormat.parse(approveContent.getCaApprovalDate().toString()));
                        }
                        customerRegisterRepository.save(customerRegister);
                    }
                } else {
                    requestRegister.setPageNumber(String.valueOf(i));
                    Optional<EGPResponse<ReportRegisterData>> result1 = egpService.getRequestRegister(requestRegister);
                    if (result1.isPresent()) {
                        ReportRegisterData reportRegisterData1 = objectMapper.convertValue(result1.get().getData(), ReportRegisterData.class);
                        PageContractor<ApproveContent> approveContentPageContractor1 = reportRegisterData1.getApprove();
                        List<ApproveContent> approveContentList = approveContentPageContractor1.getContent();
                        for (ApproveContent approveContent : approveContentList) {
                            CustomerRegister customerRegister = new CustomerRegister();
                            customerRegister.setRecordsCode(approveContent.getOrgCode());
                            customerRegister.setCid(approveContent.getId());
                            customerRegister.setTaxCode(approveContent.getTaxCode());
                            customerRegister.setName(approveContent.getOrgFullName());
                            customerRegister.setRequestType(approveContent.getType());
                            customerRegister.setSendDate(approveContent.getRequestDate());
                            customerRegister.setApproveDate(approveContent.getApprovedDate());
                            if (!DataUtil.isNullOrEmpty(approveContent.getProcessingTerm())) {
                                customerRegister.setDeadline(String.valueOf(approveContent.getProcessingTerm()));
                            }
                            if (!DataUtil.isNullOrEmpty(approveContent.getCaStatus())) {
                                customerRegister.setCtsStatus(String.valueOf(approveContent.getCaStatus()));
                            }
                            if (!DataUtil.isNullOrEmpty(approveContent.getCaApprovalDate())) {
                                customerRegister.setReleaseTime(simpleDateFormat.parse(approveContent.getCaApprovalDate().toString()));
                            }
                            customerRegisterRepository.save(customerRegister);
                        }
                    }
                }
                i++;
            }

            PageContractor<ReserveContent> reserveContentPageContractor = reportRegisterData.getReserve();
            int j = 0;
            while (j <= reserveContentPageContractor.getTotalPages() - 1) {
                if (j == 0) {
                    List<ReserveContent> reserveContentList = reserveContentPageContractor.getContent();
                    for (ReserveContent reserveContent : reserveContentList) {
                        CustomerRegisterReserve customerRegisterReserve = new CustomerRegisterReserve();
                        customerRegisterReserve.setRecordsCode(reserveContent.getOrgCode());
                        customerRegisterReserve.setCid(reserveContent.getId());
                        customerRegisterReserve.setTaxCode(reserveContent.getTaxCode());
                        customerRegisterReserve.setName(reserveContent.getOrgFullName());
                        customerRegisterReserve.setRequestType(reserveContent.getType());
                        customerRegisterReserve.setSendDate(reserveContent.getRequestDate());
                        customerRegisterReserve.setApproveDate(reserveContent.getApprovedDate());
                        if (!DataUtil.isNullOrEmpty(reserveContent.getProcessingTerm())) {
                            customerRegisterReserve.setDeadline(String.valueOf(reserveContent.getProcessingTerm()));
                        }
                        customerRegisterReserve.setDocNo(reserveContent.getDocNo());
                        customerRegisterReserve.setReasons(reserveContent.getReason());
                        customerRegisterReserveRepository.save(customerRegisterReserve);
                    }
                } else {
                    requestRegister.setPageNumber(String.valueOf(j));
                    Optional<EGPResponse<ReportRegisterData>> result1 = egpService.getRequestRegister(requestRegister);
                    if (result1.isPresent()) {
                        ReportRegisterData reportRegisterData1 = objectMapper.convertValue(result1.get().getData(), ReportRegisterData.class);
                        PageContractor<ReserveContent> reserveContentPageContractor1 = reportRegisterData1.getReserve();
                        List<ReserveContent> reserveContentList = reserveContentPageContractor1.getContent();
                        for (ReserveContent reserveContent : reserveContentList) {
                            CustomerRegisterReserve customerRegisterReserve = new CustomerRegisterReserve();
                            customerRegisterReserve.setRecordsCode(reserveContent.getOrgCode());
                            customerRegisterReserve.setCid(reserveContent.getId());
                            customerRegisterReserve.setTaxCode(reserveContent.getTaxCode());
                            customerRegisterReserve.setName(reserveContent.getOrgFullName());
                            customerRegisterReserve.setRequestType(reserveContent.getType());
                            customerRegisterReserve.setSendDate(reserveContent.getRequestDate());
                            customerRegisterReserve.setApproveDate(reserveContent.getApprovedDate());
                            if (!DataUtil.isNullOrEmpty(reserveContent.getProcessingTerm())) {
                                customerRegisterReserve.setDeadline(String.valueOf(reserveContent.getProcessingTerm()));
                            }
                            customerRegisterReserve.setDocNo(reserveContent.getDocNo());
                            customerRegisterReserve.setReasons(reserveContent.getReason());
                            customerRegisterReserveRepository.save(customerRegisterReserve);
                        }
                    }
                }
                j++;
            }
        }
    }
}
