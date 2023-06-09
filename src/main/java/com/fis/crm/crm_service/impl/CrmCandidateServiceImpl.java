package com.fis.crm.crm_service.impl;

import com.fis.crm.crm_entity.CrmCandidate;
import com.fis.crm.crm_entity.CrmUser;
import com.fis.crm.crm_entity.DTO.*;
import com.fis.crm.crm_repository.CrmCandidateRepo;
import com.fis.crm.crm_repository.CrmUserRepo;
import com.fis.crm.crm_repository.CrmInterviewStatusRepo;
import com.fis.crm.crm_service.CrmCandidateService;
import com.fis.crm.crm_util.DtoMapper;
import com.fis.crm.service.impl.ActionLogServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class CrmCandidateServiceImpl implements CrmCandidateService {
    private final Logger log = LoggerFactory.getLogger(CrmCandidateServiceImpl.class);
    private final CrmCandidateRepo candidateRepo;
    private final CrmUserRepo userRepo;
    private final CrmInterviewStatusRepo interviewStatusRepo;
    public CrmCandidateServiceImpl(CrmCandidateRepo candidateRepo, CrmUserRepo userRepo, CrmInterviewStatusRepo interviewStatusRepo) {
        this.candidateRepo = candidateRepo;
        this.userRepo = userRepo;
        this.interviewStatusRepo = interviewStatusRepo;
    }
    private final DtoMapper mapper = new DtoMapper();
    public static DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
    public static DateFormat dateFormatHour = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
    public static DateFormat dateFormatDB = new SimpleDateFormat("yyyy-MM-dd");
    @Override
    public List<CandidateDTO> getAllCandidate() {
        List<CrmCandidate> crmCandidates = candidateRepo.findAll();
        List<CandidateDTO> candidateDTOS = new ArrayList<>();
        for(CrmCandidate candidate:crmCandidates){
            InterviewStatusDTO interviewStatusDTO = new InterviewStatusDTO(candidate.getInterviewStatus().getIsid(),candidate.getInterviewStatus().getStatusName(),candidate.getInterviewStatus().getDescription());
            CrmUser crmUser = userRepo.findCrmUserByUserid(candidate.getUser().getUserid());
            CrmUserDTO crmUserDTO = mapper.userDtoMapper(crmUser);
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
            log.error("Không tồn tại ứng viên!", new NullPointerException());
        }
        InterviewStatusDTO interviewStatusDTO = new InterviewStatusDTO(candidate.getInterviewStatus().getIsid(),candidate.getInterviewStatus().getStatusName(),candidate.getInterviewStatus().getDescription());
        CrmUser crmUser = userRepo.findCrmUserByUserid(candidate.getUser().getUserid());
        CrmUserDTO crmUserDTO = mapper.userDtoMapper(crmUser);
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
            log.error("Không tồn tại ứng viên!", new NullPointerException());
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
                log.error("Không tồn tại user!", new NullPointerException());
            }
            candidate.setUser(userRepo.findById(candidateDTO.getManageId()).orElse(null));
        }
        return new Result("SUCCESS","Cập nhật thành công!",candidateRepo.save(candidate));
    }

    @Override
    public Result deleteCandidate(Long candidateId) {
        CrmCandidate candidate = candidateRepo.findById(candidateId).orElse(null);
        if(candidate == null){
            log.error("Không tồn tại ứng viên!", new NullPointerException());
        }
        candidate.setStatus(false);
        return new Result("SUCCESS","Xoá thành công!",candidateRepo.save(candidate));
    }

    @Override
    public Result searchCandidate(SearchCandidateDTO searchCandidateDTO) {
        List<CrmCandidate> crmCandidates =  candidateRepo.searchCandidate(searchCandidateDTO.getStartDayCreate(),
        searchCandidateDTO.getEndDayCreate(),
        searchCandidateDTO.getStartDay(),
        searchCandidateDTO.getEndDay(),
        searchCandidateDTO.getISID(),
        searchCandidateDTO.getManageId());
        List<CandidateDTO> candidateDTOS = new ArrayList<>();
        for(CrmCandidate candidate:crmCandidates){
            InterviewStatusDTO interviewStatusDTO = new InterviewStatusDTO(candidate.getInterviewStatus().getIsid(),candidate.getInterviewStatus().getStatusName(),candidate.getInterviewStatus().getDescription());
            CrmUser crmUser = userRepo.findCrmUserByUserid(candidate.getUser().getUserid());
            CrmUserDTO crmUserDTO = mapper.userDtoMapper(crmUser);
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
//        List<CandidateDTO> candidateDTOS = candidateRepo.searchCandidate(searchCandidateDTO.getStartDayCreate(),
//                                                                        searchCandidateDTO.getEndDayCreate(),
//                                                                        searchCandidateDTO.getStartDay(),
//                                                                        searchCandidateDTO.getEndDay(),
//                                                                        searchCandidateDTO.getISID(),
//                                                                        searchCandidateDTO.getManageId());
//        for(CandidateDTO candidateDTO: candidateDTOS){
//            CrmCandidate candidate = candidateRepo.findById(candidateDTO.getCandidateId()).orElseThrow(NullPointerException::new);
//            CrmUser crmUser = userRepo.findCrmUserByUserid(candidate.getUser().getUserid());
//            CrmUserDTO crmUserDTO = mapper.userDtoMapper(crmUser);
//            candidateDTO.setCrmUserDTO(crmUserDTO);
//        }

        if(candidateDTOS.size() == 0){
            return new Result("NOT_FOUND","Không tồn tại kết quả!","");
        }
        else {
            return new Result("OK","Tìm kiếm thành công",candidateDTOS);
        }
    }
}
