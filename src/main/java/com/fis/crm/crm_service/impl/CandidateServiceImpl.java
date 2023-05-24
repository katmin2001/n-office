package com.fis.crm.crm_service.impl;

import com.fis.crm.crm_entity.CrmCandidate;
import com.fis.crm.crm_entity.DTO.*;
import com.fis.crm.crm_repository.CrmCandidateRepo;
import com.fis.crm.crm_repository.CrmUserRepo;
import com.fis.crm.crm_repository.CrmInterviewStatusRepo;
import com.fis.crm.crm_service.CandidateService;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class CandidateServiceImpl implements CandidateService {
    private final CrmCandidateRepo candidateRepo;
    private final CrmUserRepo userRepo;
    private final CrmInterviewStatusRepo interviewStatusRepo;
    public CandidateServiceImpl(CrmCandidateRepo candidateRepo, CrmUserRepo userRepo, CrmInterviewStatusRepo interviewStatusRepo) {
        this.candidateRepo = candidateRepo;
        this.userRepo = userRepo;
        this.interviewStatusRepo = interviewStatusRepo;
    }

    public static DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
    public static DateFormat dateFormatHour = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
    public static DateFormat dateFormatDB = new SimpleDateFormat("yyyy-MM-dd");
    @Override
    public List<CandidateDTO> getAllCandidate() {
        List<CrmCandidate> crmCandidates = candidateRepo.findAll();
        List<CandidateDTO> candidateDTOS = new ArrayList<>();
        for(CrmCandidate candidate:crmCandidates){
            InterviewStatusDTO interviewStatusDTO = new InterviewStatusDTO(candidate.getInterviewStatus().getIsid(),candidate.getInterviewStatus().getStatusName(),candidate.getInterviewStatus().getDescription());
            CrmUserDTO crmUserDTO = new CrmUserDTO(
                candidate.getUser().getUserid(),
                candidate.getUser().getUsername(),
                candidate.getUser().getFullname(),
                candidate.getUser().getCreatedate(),
                candidate.getUser().getPhone(),
                candidate.getUser().getBirthday(),
                candidate.getUser().getAddress(),
                candidate.getUser().getStatus());
            CandidateDTO candidateDTO = new CandidateDTO(candidate.getCandidateid(),
                candidate.getFullname(),
                candidate.getPhone(),
                dateFormat.format(candidate.getBirthday()),
                candidate.getAddress(),
                candidate.getStatus(),
                dateFormatHour.format(candidate.getCreateDate()),
                interviewStatusDTO,
                crmUserDTO
            );
            candidateDTOS.add(candidateDTO);
        }
        return candidateDTOS;
    }

    @Override
    public CandidateDTO getCandidateById(Long candidateId) {
        CrmCandidate candidate = candidateRepo.findById(candidateId).orElse(null);
        if(candidate == null){
            throw new NullPointerException();
        }
        InterviewStatusDTO interviewStatusDTO = new InterviewStatusDTO(candidate.getInterviewStatus().getIsid(),candidate.getInterviewStatus().getStatusName(),candidate.getInterviewStatus().getDescription());
        CrmUserDTO crmUserDTO = new CrmUserDTO(
            candidate.getUser().getUserid(),
            candidate.getUser().getUsername(),
            candidate.getUser().getFullname(),
            candidate.getUser().getCreatedate(),
            candidate.getUser().getPhone(),
            candidate.getUser().getBirthday(),
            candidate.getUser().getAddress(),
            candidate.getUser().getStatus());
        CandidateDTO candidateDTO = new CandidateDTO(candidate.getCandidateid(),
            candidate.getFullname(),
            candidate.getPhone(),
            dateFormat.format(candidate.getBirthday()),
            candidate.getAddress(),
            candidate.getStatus(),
            dateFormatHour.format(candidate.getCreateDate()),
            interviewStatusDTO,
            crmUserDTO
        );
        return candidateDTO;
    }
//    final static String DATE_FORMAT = "dd/mm/yyyy";
//
//    public static boolean isDateValid(String date)
//    {
//        try {
//            DateFormat df = new SimpleDateFormat(DATE_FORMAT);
//            df.setLenient(false);
//            df.parse(date);
//            return true;
//        } catch (ParseException e) {
//            return false;
//        }
//    }
    @Override
    public Result addCandidate(CandidateRequestDTO candidateDTO) {
        candidateDTO.setISID(Long.valueOf(1));
        CrmCandidate candidate = new CrmCandidate();
        candidate.setAddress(candidateDTO.getAddress());
        candidate.setFullname(candidateDTO.getFullname());
        candidate.setPhone(candidateDTO.getPhone());
        candidate.setBirthday(candidateDTO.getBirthday());
        long millis=System.currentTimeMillis();
        Date date=new Date(millis);
        candidate.setCreateDate(date);

        candidate.setStatus(true);
        candidate.setUser(userRepo.findById(candidateDTO.getManageId()).orElse(null));
        candidate.setInterviewStatus(interviewStatusRepo.findById(candidateDTO.getISID()).orElse(null));
        return new Result("SUCCESS","Thêm thành công!",candidateRepo.save(candidate));
    }

    @Override
    public Result updateCandidate(CandidateRequestDTO candidateDTO, Long candidateId) {
        CrmCandidate candidate = candidateRepo.findById(candidateId).orElse(null);
        if(candidate == null){
            throw new NullPointerException();
        }
        if(candidateDTO.getFullname() != null){
            candidate.setFullname(candidateDTO.getFullname());
        }
        if(candidateDTO.getAddress() != null){
            candidate.setAddress(candidateDTO.getAddress());
        }
        if(candidateDTO.getPhone() != null){
            candidate.setPhone(candidateDTO.getPhone());
        }
        if(candidateDTO.getBirthday() != null){
            candidate.setBirthday(candidateDTO.getBirthday());
        }
        if(candidateDTO.getStatus() != null){
            candidate.setStatus(candidateDTO.getStatus());
        }
        if(candidateDTO.getManageId() != null){
            if(userRepo.findById(candidateDTO.getManageId()).orElse(null) == null){
                throw new NullPointerException();
            }
            candidate.setUser(userRepo.findById(candidateDTO.getManageId()).orElse(null));
        }
        return new Result("SUCCESS","Cập nhật thành công!",candidateRepo.save(candidate));
    }

    @Override
    public Result deleteCandidate(Long candidateId) {
        CrmCandidate candidate = candidateRepo.findById(candidateId).orElse(null);
        if(candidate == null){
            throw new NullPointerException();
        }
        candidate.setStatus(false);
        return new Result("SUCCESS","Xoá thành công!",candidateRepo.save(candidate));
    }

    @Override
    public Result searchCandidate(SearchCandidateDTO searchCandidateDTO) {
        List<CrmCandidate> crmCandidates =  candidateRepo.searchCandidate(searchCandidateDTO.getStartDayCreate(),searchCandidateDTO.getEndDayCreate(),searchCandidateDTO.getStartDay(), searchCandidateDTO.getEndDay(), searchCandidateDTO.getISID(), searchCandidateDTO.getManageId());
        List<CandidateDTO> candidateDTOS = new ArrayList<>();
        for(CrmCandidate candidate:crmCandidates){
            InterviewStatusDTO interviewStatusDTO = new InterviewStatusDTO(candidate.getInterviewStatus().getIsid(),candidate.getInterviewStatus().getStatusName(),candidate.getInterviewStatus().getDescription());
            CrmUserDTO crmUserDTO = new CrmUserDTO(
                candidate.getUser().getUserid(),
                candidate.getUser().getUsername(),
                candidate.getUser().getFullname(),
                candidate.getUser().getCreatedate(),
                candidate.getUser().getPhone(),
                candidate.getUser().getBirthday(),
                candidate.getUser().getAddress(),
                candidate.getUser().getStatus());
            CandidateDTO candidateDTO = new CandidateDTO(candidate.getCandidateid(),
                candidate.getFullname(),
                candidate.getPhone(),
                dateFormat.format(candidate.getBirthday()),
                candidate.getAddress(),
                candidate.getStatus(),
                dateFormatHour.format(candidate.getCreateDate()),
                interviewStatusDTO,
                crmUserDTO
            );
            candidateDTOS.add(candidateDTO);
        }
        if(candidateDTOS.size() == 0){
            return new Result("NOT_FOUND","Không tồn tại kết quả!","");
        }
        else {
            return new Result("OK","Tìm kiếm thành công",candidateDTOS);
        }
    }
}
