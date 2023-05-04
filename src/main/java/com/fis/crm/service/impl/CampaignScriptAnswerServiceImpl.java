package com.fis.crm.service.impl;

import com.fis.crm.commons.CurrentUser;
import com.fis.crm.commons.Translator;
import com.fis.crm.config.Constants;
import com.fis.crm.domain.*;
import com.fis.crm.service.ActionLogService;
import com.fis.crm.service.UserService;
import com.fis.crm.service.dto.ActionLogDTO;
import com.fis.crm.web.rest.errors.CodeExisted;
import com.fis.crm.repository.CampaignScriptAnswerRepository;
import com.fis.crm.repository.CampaignScriptQuestionRepository;
import com.fis.crm.repository.CampaignScriptRepository;
import com.fis.crm.service.CampaignScriptAnswerService;
import com.fis.crm.service.dto.CampaignScriptAnswerRequestDTO;
import com.fis.crm.service.dto.CampaignScriptAnswerResponseDTO;
import com.fis.crm.service.mapper.CampaignScriptAnswerRequestMapper;
import com.fis.crm.service.mapper.CampaignScriptAnswerResponseMapper;
import com.fis.crm.web.rest.errors.WrongFormat;
import io.undertow.util.BadRequestException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.criteria.*;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

@Service
@Transactional
public class CampaignScriptAnswerServiceImpl implements CampaignScriptAnswerService {

    private final Logger log = LoggerFactory.getLogger(CampaignScriptAnswerServiceImpl.class);
    private final CampaignScriptAnswerRepository campaignScriptAnswerRepository;
    private final CampaignScriptRepository campaignScriptRepository;
    private final CampaignScriptQuestionRepository campaignScriptQuestionRepository;
    private final CampaignScriptAnswerRequestMapper campaignScriptAnswerRequestMapper;
    private final CampaignScriptAnswerResponseMapper campaignScriptAnswerResponseMapper;
    private final CurrentUser currentUser;
    private final EntityManager entityManager;
    private final ActionLogService actionLogService;
    private final UserService userService;

    public CampaignScriptAnswerServiceImpl(CampaignScriptAnswerRepository campaignScriptAnswerRepository,
                                           CampaignScriptAnswerRequestMapper campaignScriptAnswerRequestMapper,
                                           CampaignScriptAnswerResponseMapper campaignScriptAnswerResponseMapper,
                                           CampaignScriptRepository campaignScriptRepository,
                                           CampaignScriptQuestionRepository campaignScriptQuestionRepository,
                                           CurrentUser currentUser,
                                           EntityManager entityManager, ActionLogService actionLogService, UserService userService) {
        this.campaignScriptAnswerRepository = campaignScriptAnswerRepository;
        this.campaignScriptAnswerRequestMapper = campaignScriptAnswerRequestMapper;
        this.campaignScriptAnswerResponseMapper = campaignScriptAnswerResponseMapper;
        this.campaignScriptRepository = campaignScriptRepository;
        this.campaignScriptQuestionRepository = campaignScriptQuestionRepository;
        this.currentUser = currentUser;
        this.entityManager = entityManager;
        this.actionLogService = actionLogService;
        this.userService = userService;
    }

    @Override
    public CampaignScriptAnswerResponseDTO save(CampaignScriptAnswerRequestDTO dto) throws BadRequestException, CodeExisted, WrongFormat {
        log.info("Saving new answer....");
        CampaignScriptAnswer entity = campaignScriptAnswerRequestMapper.toEntity(dto);
        if(entity.getStatus()==null) entity.setStatus("1");
        if(entity.getDisplay()==null) entity.setDisplay("1");
        if(entity.getMin()!=null && entity.getMax() !=null && entity.getType().equals("4")){
            if(entity.getMin()<0 || entity.getMax()<0){
                throw new BadRequestException(Translator.toLocale("answer.min-or-max-less-than-zero"));
            } else if(entity.getMax() <= entity.getMin()){
                throw new BadRequestException(Translator.toLocale("answer.min-must-be-less-or-equal-max"));
            }
        }

        //set question
        CampaignScriptQuestion question = campaignScriptQuestionRepository.getOne(dto.getCampaignScriptQuestionId());
        entity.setQuestion(question);
        entity.setCampaignScriptId(entity.getQuestion().getCampaignScript().getId());

        //gen code
        if (dto.getCode() != null) {
            if(!validAnswerCode(dto.getCode())){
                throw new WrongFormat(Translator.toLocale("answer.code.wrong-format"));
            }
            int check = campaignScriptAnswerRepository.findByCodeAndCampaignScriptQuestionId(entity.getCode(), dto.getCampaignScriptQuestionId()).size();
            if(check >=1){
                throw new CodeExisted(Translator.toLocale("answer.code.existed"));
            }else {
                entity.setCode(dto.getCode());
            }
        } else {
            entity.setCode(this.genAnswerCodeInOrder(question.getId(), question.getCode()));
        }

        //set position
        if (entity.getPosition() == null) {
            entity.setPosition(genPosition(dto.getCampaignScriptQuestionId()));
        } else {
            checkPosition(dto);
        }

        //create user
        entity.setCreateUser(currentUser.getCurrentUser());
        entity.setUpdateUser(currentUser.getCurrentUser());

        entity = campaignScriptAnswerRepository.save(entity);
        log.info("Save answer successful!");
        CampaignScriptAnswerResponseDTO response = campaignScriptAnswerResponseMapper.toDto(entity);
        response.setCreateFullName(entity.getCreateUser().getFirstName());
        response.setUpdateFullName(entity.getUpdateUser().getFirstName());
        log.info(response.toString());
        CampaignScript campaignScript = campaignScriptRepository.findById(question.getCampaignScript().getId()).get();
        Optional<User> userLog = userService.getUserWithAuthorities();
        actionLogService.saveActionLog(new ActionLogDTO(userLog.get().getId(), Constants.ACTION_LOG_TYPE.INSERT + "",
            null, String.format("Thêm mới câu trả lời: Kịch bản [%s] - Mã câu hỏi [%s] - Câu trả lời vị trí [%s]",campaignScript.getName(), question.getCode(), dto.getPosition()),
            new Date(), Constants.MENU_ID.OMS, Constants.MENU_ITEM_ID.campain_script, "CONFIG_MENU_ITEM"));
        return response;
    }

    public Boolean validAnswerCode(String code){
        Pattern p = Pattern.compile("^C[0-9]+_[0-9]+$");
        return p.matcher(code).find();
    }

    public String genAnswerCodeInOrder(Long campaignScriptQuestionId, String questionCode){
        List<CampaignScriptAnswer> answers = campaignScriptAnswerRepository.findByCampaignScriptQuestionId(campaignScriptQuestionId);
        if(answers==null || answers.size()==0) return questionCode+"_1";
        answers.sort((o1, o2) -> o2.getId().compareTo(o1.getId()));
        int index = answers.get(0).getCode().lastIndexOf("_");
        int code = Integer.valueOf(answers.get(0).getCode().substring(index+1))+1;
        return questionCode + "_" + code;
    }

    @Override
    public Integer genPosition(Long questionId) {
        List<CampaignScriptAnswer> answers = campaignScriptAnswerRepository.findByCampaignScriptQuestionId(questionId);
        if (answers == null || answers.size() == 0) return 1;
        answers.sort((o1, o2) -> o2.getPosition() - o1.getPosition());
        return answers.get(0).getPosition() + 1;
    }

    @Override
    public Page<CampaignScriptAnswerResponseDTO> findAllByCampaignScriptQuestionIdPageable(Long campaignScriptQuestionId, Pageable pageable) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<CampaignScriptAnswerResponseDTO> cq = cb.createQuery(CampaignScriptAnswerResponseDTO.class);
        Root<CampaignScriptAnswer> root = cq.from(CampaignScriptAnswer.class);
        List<Predicate> predicates = new ArrayList<>();
        predicates.add(cb.equal(root.get(CampaignScriptAnswer_.QUESTION).get("id"), campaignScriptQuestionId));
//        predicates.add(cb.equal(root.get(CampaignScriptAnswer_.DISPLAY), "1"));
        Predicate[] p = predicates.toArray(new Predicate[0]);
        cq.select(cb.construct(CampaignScriptAnswerResponseDTO.class,
            root.get(CampaignScriptAnswer_.ID),
            root.get(CampaignScriptAnswer_.CAMPAIGN_SCRIPT_ID),
            root.get(CampaignScriptAnswer_.QUESTION).get("id"),
            root.get(CampaignScriptAnswer_.CODE),
            root.get(CampaignScriptAnswer_.TYPE),
            root.get(CampaignScriptAnswer_.POSITION),
            root.get(CampaignScriptAnswer_.DISPLAY),
            root.get(CampaignScriptAnswer_.CONTENT),
            root.get(CampaignScriptAnswer_.MIN),
            root.get(CampaignScriptAnswer_.MAX),
            root.get(CampaignScriptAnswer_.STATUS),
            root.join(CampaignScriptAnswer_.CREATE_USER, JoinType.LEFT).get("id"),
            root.join(CampaignScriptAnswer_.UPDATE_USER, JoinType.LEFT).get("id"),
            root.get(CampaignScriptAnswer_.CREATE_DATETIME),
            root.get(CampaignScriptAnswer_.UPDATE_DATETIME),
            root.join(CampaignScriptAnswer_.CREATE_USER, JoinType.LEFT).get("login"),
            root.join(CampaignScriptAnswer_.UPDATE_USER, JoinType.LEFT).get("login"),
            root.join(CampaignScriptAnswer_.CREATE_USER, JoinType.LEFT).get("firstName"),
            root.join(CampaignScriptAnswer_.UPDATE_USER, JoinType.LEFT).get("firstName"),
            root.get(CampaignScriptAnswer_.QUESTION).get("code")
        ))
            .where(p).orderBy(cb.asc(root.get("position")));;
        List<CampaignScriptAnswerResponseDTO> dtoList = entityManager.createQuery(cq)
            .setFirstResult((int) pageable.getOffset())
            .setMaxResults(pageable.getPageSize())
            .getResultList();

        CriteriaQuery<Long> countQuery = cb.createQuery(Long.class);
        Root<CampaignScriptAnswer> rootCount = countQuery.from(CampaignScriptAnswer.class);
        countQuery.select(cb.count(rootCount)).where(p);
        Long count = entityManager.createQuery(countQuery).getSingleResult();

        Page<CampaignScriptAnswerResponseDTO> dtoPage;
        if (pageable == null) {
            dtoPage = new PageImpl<>(dtoList);
        } else {
            dtoPage = new PageImpl<CampaignScriptAnswerResponseDTO>(dtoList, pageable, count);
        }
        log.info(dtoPage.toString());
        return dtoPage;
    }

    @Override
    public Boolean checkPosition(CampaignScriptAnswerRequestDTO dto) throws BadRequestException {
        List<CampaignScriptAnswer> answers = campaignScriptAnswerRepository.findByCampaignScriptQuestionIdAndPosition(dto.getCampaignScriptQuestionId(), dto.getPosition());
        if (answers.size() >= 1) {
            log.error("Answer in position " + dto.getPosition() + " was existed!");
            throw new BadRequestException(Translator.toLocale("answer.position.existed", dto.getPosition().toString()));
        }
        return true;
    }

    @Override
    public CampaignScriptAnswerResponseDTO updateAnswer(CampaignScriptAnswerRequestDTO dto) throws BadRequestException {
        log.info("Updating answer...");
        CampaignScriptAnswer answer = campaignScriptAnswerRepository.getOne(dto.getId());
//        if(answer.getStatus()==null) answer.setStatus("1");
//        if(answer.getDisplay()==null) answer.setDisplay("1");
        if(dto.getMin()!=null && dto.getMax() !=null && dto.getType().equals("4")){
            if(dto.getMin()<0 || dto.getMax()<0){
                throw new BadRequestException(Translator.toLocale("answer.min-or-max-less-than-zero"));
            } else if(dto.getMax() <= dto.getMin()){
                throw new BadRequestException(Translator.toLocale("answer.min-must-be-less-or-equal-max"));
            }
        }
        if (dto.getType() != null) answer.setType(dto.getType());
        if (dto.getPosition() != null) {
            if (!dto.getPosition().equals(answer.getPosition())) {
                checkPosition(dto);
                answer.setPosition(dto.getPosition());
            }
        }
        if (dto.getDisplay() != null) answer.setDisplay(dto.getDisplay());
        if (dto.getContent() != null) answer.setContent(dto.getContent());
        if (dto.getMin() != null) answer.setMin(dto.getMin());
        if (dto.getMax() != null) answer.setMax(dto.getMax());
        if (dto.getStatus() != null) answer.setStatus(dto.getStatus());
        answer.setUpdateUser(currentUser.getCurrentUser());
        log.info("Update successful!");
        CampaignScriptAnswerResponseDTO response = campaignScriptAnswerResponseMapper.toDto(answer);
        response.setCreateFullName(answer.getCreateUser().getFirstName());
        response.setUpdateFullName(answer.getUpdateUser().getFirstName());
        CampaignScriptQuestion question = campaignScriptQuestionRepository.getOne(dto.getCampaignScriptQuestionId());
        CampaignScript campaignScript = campaignScriptRepository.findById(question.getCampaignScript().getId()).get();
        Optional<User> userLog = userService.getUserWithAuthorities();
        actionLogService.saveActionLog(new ActionLogDTO(userLog.get().getId(), Constants.ACTION_LOG_TYPE.UPDATE + "",
            null, String.format("Cập nhật câu trả lời: Kịch bản [%s] - Mã câu hỏi [%s] - Câu trả lời vị trí [%s]",campaignScript.getName(), question.getCode(), dto.getPosition()),
            new Date(), Constants.MENU_ID.OMS, Constants.MENU_ITEM_ID.campain_script, "CONFIG_MENU_ITEM"));
        return response;
    }

    @Override
    public CampaignScriptAnswerResponseDTO getById(Long answerId) {
        CampaignScriptAnswer entity = campaignScriptAnswerRepository.getOne(answerId);
        return campaignScriptAnswerResponseMapper.toDto(entity);
    }

    @Override
    public void deleteAnswer(Long id) throws Exception {
        CampaignScriptAnswer answer = campaignScriptAnswerRepository.getOne(id);
        CampaignScript cs = answer.getQuestion().getCampaignScript();
        if (cs.getStatus().equals("0")) {
            throw new Exception("This Campaign script is used.");
        }
        log.info("Deleting answer...");
        CampaignScriptQuestion question = campaignScriptQuestionRepository.getOne(answer.getQuestion().getId());
        CampaignScript campaignScript = campaignScriptRepository.findById(question.getCampaignScript().getId()).get();
        Optional<User> userLog = userService.getUserWithAuthorities();
        actionLogService.saveActionLog(new ActionLogDTO(userLog.get().getId(), Constants.ACTION_LOG_TYPE.DELETE + "",
            null, String.format("Xóa câu trả lời: Kịch bản [%s] - Mã câu hỏi [%s] - Câu trả lời vị trí [%s]",campaignScript.getName(), question.getCode(), answer.getPosition()),
            new Date(), Constants.MENU_ID.OMS, Constants.MENU_ITEM_ID.campain_script, "CONFIG_MENU_ITEM"));
        campaignScriptAnswerRepository.deleteById(id);
        log.info("Delete successful!");
    }

    @Override
    public void deleteAllAnswer(Long campaignScriptQuestionId) throws Exception {
        log.info("Processing delete answer....");
        CampaignScriptQuestion question = campaignScriptQuestionRepository.getOne(campaignScriptQuestionId);
        CampaignScript campaignScript = question.getCampaignScript();
        log.info(campaignScript.toString());
        if (campaignScript.getStatus().equals("1")) {
            List<CampaignScriptAnswer> answers = campaignScriptAnswerRepository.findByCampaignScriptQuestionId(campaignScriptQuestionId);
            for (CampaignScriptAnswer answer : answers) {
                campaignScriptAnswerRepository.deleteById(answer.getId());
            }
        }else{
            throw new Exception("This Campaign script is used.");
        }
        log.info("Delete answer successful!");
    }
}
