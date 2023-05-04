package com.fis.crm.service.impl;

import com.fis.crm.commons.CheckCharacterUtil;
import com.fis.crm.commons.CurrentUser;
import com.fis.crm.commons.Translator;
import com.fis.crm.config.Constants;
import com.fis.crm.domain.CampaignScript;
import com.fis.crm.domain.CampaignScriptQuestion;
import com.fis.crm.domain.CampaignScriptQuestion_;
import com.fis.crm.domain.User;
import com.fis.crm.service.ActionLogService;
import com.fis.crm.service.UserService;
import com.fis.crm.service.dto.ActionLogDTO;
import com.fis.crm.web.rest.errors.CodeExisted;
import com.fis.crm.repository.CampaignScriptQuestionRepository;
import com.fis.crm.repository.CampaignScriptRepository;
import com.fis.crm.service.CampaignScriptQuestionService;
import com.fis.crm.service.dto.CampaignScriptQuestionRequestDTO;
import com.fis.crm.service.dto.CampaignScriptQuestionResponseDTO;
import com.fis.crm.service.mapper.CampaignScriptQuestionRequestMapper;
import com.fis.crm.service.mapper.CampaignScriptQuestionResponseMapper;
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
import java.util.stream.Collectors;

@Service
@Transactional
public class CampaignScriptQuestionServiceImpl implements CampaignScriptQuestionService {

    private final Logger log = LoggerFactory.getLogger(CampaignScriptQuestionServiceImpl.class);

    private final CampaignScriptQuestionRepository campaignScriptQuestionRepository;
    private final CampaignScriptQuestionRequestMapper campaignScriptQuestionMapper;
    private final CampaignScriptQuestionResponseMapper campaignScriptQuestionResponseMapper;
    private final CampaignScriptRepository campaignScriptRepository;
    private final CurrentUser currentUser;
    private final EntityManager entityManager;
    private final ActionLogService actionLogService;
    private final UserService userService;

    public CampaignScriptQuestionServiceImpl(
        CampaignScriptQuestionRepository campaignScriptQuestionRepository,
        CampaignScriptQuestionRequestMapper campaignScriptQuestionMapper,
        CampaignScriptQuestionResponseMapper campaignScriptQuestionResponseMapper,
        CampaignScriptRepository campaignScriptRepository,
        CurrentUser currentUser,
        EntityManager entityManager,
        ActionLogService actionLogService, UserService userService) {
        this.campaignScriptQuestionRepository = campaignScriptQuestionRepository;
        this.campaignScriptQuestionMapper = campaignScriptQuestionMapper;
        this.campaignScriptQuestionResponseMapper = campaignScriptQuestionResponseMapper;
        this.campaignScriptRepository = campaignScriptRepository;
        this.currentUser = currentUser;
        this.entityManager = entityManager;
        this.actionLogService = actionLogService;
        this.userService = userService;
    }

    @Override
    public CampaignScriptQuestionResponseDTO save(CampaignScriptQuestionRequestDTO dto) throws BadRequestException, CodeExisted, WrongFormat {
        log.info("Saving new question .....");
        CampaignScriptQuestion entity = campaignScriptQuestionMapper.toEntity(dto);
        if (entity.getStatus() == null) entity.setStatus("1");
        //gen code
        if (dto.getCode() != null) {
            if(!validQuestionCode(dto.getCode())){
                throw new WrongFormat(Translator.toLocale("question.code.wrong-format"));
            }
            Integer check = campaignScriptQuestionRepository.findByCodeAndCampaignScriptId(entity.getCode(),  dto.getCampaignScriptId()).size();
            if(check >=1){
                throw new CodeExisted(Translator.toLocale("question.code.existed"));
            } else{
                entity.setCode(dto.getCode());
            }

        } else {
            entity.setCode(this.genQuestionCodeInOrder(dto.getCampaignScriptId()));
        }

        //setPosition
        if (entity.getPosition() == null) {
            entity.setPosition(genPosition(dto.getCampaignScriptId()));
        } else {
            checkExistPosition(dto);
        }
        //set campaignScript
        CampaignScript campaignScript = campaignScriptRepository.findById(dto.getCampaignScriptId()).get();
        entity.setCampaignScript(campaignScript);

        //create user
        entity.setCreateUser(currentUser.getCurrentUser());
        entity.setUpdateUser(currentUser.getCurrentUser());

        entity = campaignScriptQuestionRepository.save(entity);
        log.info(entity.getCampaignScript().toString());
        CampaignScriptQuestionResponseDTO response = campaignScriptQuestionResponseMapper.toDto(entity);
        response.setCreateFullName(entity.getCreateUser().getFirstName());
        response.setUpdateFullName(entity.getUpdateUser().getFirstName());
        log.info("Save new question successful");
        log.info(response.toString());
        Optional<User> userLog = userService.getUserWithAuthorities();
        if (dto.getPosition() != null){
            actionLogService.saveActionLog(new ActionLogDTO(userLog.get().getId(), Constants.ACTION_LOG_TYPE.INSERT + "",
                null, String.format("Thêm mới câu hỏi kịch bản chiến dịch: [%s] - Vị trí: [%s]", campaignScript.getName(), dto.getPosition()),
                new Date(), Constants.MENU_ID.OMS, Constants.MENU_ITEM_ID.campain_script, "CONFIG_MENU_ITEM"));
        }
        return response;
    }

    public Boolean validQuestionCode(String code){
        Pattern p = Pattern.compile("^C[0-9]+$");
        return p.matcher(code).find();
    }

    public String genQuestionCodeInOrder(Long campaignScriptId){
        List<CampaignScriptQuestion> questions = campaignScriptQuestionRepository.findByCampaignScriptId(campaignScriptId);
        if(questions==null || questions.size()==0) return "C1";
        questions.sort((o1, o2) -> o2.getId().compareTo(o1.getId()));
        int code=0;
        if(CheckCharacterUtil.checkCharacterNumber(questions.get(0).getCode().substring(1,2))) {
             code= Integer.parseInt(questions.get(0).getCode().substring(1)) + 1;
        }else{
            code= Integer.parseInt(questions.get(0).getCode().substring(2)) + 1;
        }
        return  "C" + code;
    }

    @Override
    public List<CampaignScriptQuestionResponseDTO> findAllByCampaignScriptId(Long campaignScriptId) {
        List<CampaignScriptQuestionResponseDTO> result = new ArrayList<>();
        List<CampaignScriptQuestion> questions = campaignScriptQuestionRepository.findByCampaignScriptId(campaignScriptId);
        for (CampaignScriptQuestion q : questions) {
            result.add(campaignScriptQuestionResponseMapper.toDto(q));
        }
        return result;
    }

    @Override
    public Page<CampaignScriptQuestionResponseDTO> findAllByCampaignScriptIdPageable(Long campaignScriptId, Pageable pageable) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<CampaignScriptQuestionResponseDTO> cq = cb.createQuery(CampaignScriptQuestionResponseDTO.class);
        Root<CampaignScriptQuestion> root = cq.from(CampaignScriptQuestion.class);
        List<Predicate> predicates = new ArrayList<>();
        predicates.add(cb.equal(root.get(CampaignScriptQuestion_.CAMPAIGN_SCRIPT).get("id"), campaignScriptId));
//        predicates.add(cb.equal(root.get(CampaignScriptQuestion_.DISPLAY), "1"));
        Predicate[] p = predicates.toArray(new Predicate[0]);
        cq.select(cb.construct(CampaignScriptQuestionResponseDTO.class,
            root.get(CampaignScriptQuestion_.ID),
            root.get(CampaignScriptQuestion_.CAMPAIGN_SCRIPT).get("id"),
            root.get(CampaignScriptQuestion_.CAMPAIGN_SCRIPT).get("name"),
            root.get(CampaignScriptQuestion_.CODE),
            root.get(CampaignScriptQuestion_.POSITION),
            root.get(CampaignScriptQuestion_.DISPLAY),
            root.get(CampaignScriptQuestion_.CONTENT),
            root.get(CampaignScriptQuestion_.STATUS),
            root.get(CampaignScriptQuestion_.CREATE_DATETIME),
            root.get(CampaignScriptQuestion_.UPDATE_DATETIME),
            root.join(CampaignScriptQuestion_.CREATE_USER, JoinType.LEFT).get("id"),
            root.join(CampaignScriptQuestion_.UPDATE_USER, JoinType.LEFT).get("id"),
            root.join(CampaignScriptQuestion_.CREATE_USER, JoinType.LEFT).get("login"),
            root.join(CampaignScriptQuestion_.UPDATE_USER, JoinType.LEFT).get("login"),
            root.join(CampaignScriptQuestion_.CREATE_USER, JoinType.LEFT).get("firstName"),
            root.join(CampaignScriptQuestion_.UPDATE_USER, JoinType.LEFT).get("firstName")
        ))
            .where(p).orderBy(cb.asc(root.get("position")));
        List<CampaignScriptQuestionResponseDTO> dtoList = entityManager.createQuery(cq)
            .setFirstResult((int) pageable.getOffset())
            .setMaxResults(pageable.getPageSize())
            .getResultList();

        CriteriaQuery<Long> countQuery = cb.createQuery(Long.class);
        Root<CampaignScriptQuestion> rootCount = countQuery.from(CampaignScriptQuestion.class);
        countQuery.select(cb.count(rootCount)).where(p);
        Long count = entityManager.createQuery(countQuery).getSingleResult();

        Page<CampaignScriptQuestionResponseDTO> dtoPage;
        if (pageable == null) {
            dtoPage = new PageImpl<>(dtoList);
        } else {
            dtoPage = new PageImpl<CampaignScriptQuestionResponseDTO>(dtoList, pageable, count);
        }
        log.info(dtoPage.toString());
        return dtoPage;
    }

    @Override
    public Boolean checkExistPosition(CampaignScriptQuestionRequestDTO dto) throws BadRequestException {
        List<CampaignScriptQuestion> questions = campaignScriptQuestionRepository.findByPositionAndCampaignScriptId(dto.getPosition(), dto.getCampaignScriptId());
        if (questions.size() >= 1) {
            log.error("Question in position " + dto.getPosition() + " was existed!");
            throw new BadRequestException(Translator.toLocale("question.position.existed", dto.getPosition().toString()));
        }
        return true;
    }

    @Override
    public Integer genPosition(Long campaignScripId) {
        List<CampaignScriptQuestion> questions = campaignScriptQuestionRepository.findByCampaignScriptId(campaignScripId);
        if (questions==null ||questions.size() == 0) return 1;
        questions.sort((o1, o2) -> o2.getPosition() - o1.getPosition());
        return questions.get(0).getPosition() + 1;
    }

    @Override
    public Integer getId(Long campaignScripId, String content) {
        return campaignScriptQuestionRepository.findByCampaignScriptIdAndAndContent(campaignScripId,content);
    }

    @Override
    public List<CampaignScriptQuestionResponseDTO> getAllQuestionByCampaignId(Long campaignId) {
        return campaignScriptQuestionRepository.findAllQuestionByCampaignId(campaignId)
            .stream().map(x-> campaignScriptQuestionResponseMapper.toDto(x)).collect(Collectors.toList());
    }

    @Override
    public CampaignScriptQuestionResponseDTO updateQuestion(CampaignScriptQuestionRequestDTO dto) throws BadRequestException {
        log.info("Updating question .....");
        CampaignScriptQuestion entity = campaignScriptQuestionRepository.findById(dto.getId()).get();
        if (dto.getContent() != null) entity.setContent(dto.getContent());
        if (dto.getPosition() != null) {
            if (!dto.getPosition().equals(entity.getPosition())) {
                checkExistPosition(dto);
                entity.setPosition(dto.getPosition());
            }
        }
        if (dto.getDisplay() != null) entity.setDisplay(dto.getDisplay());
        if (dto.getContent() != null) entity.setContent(dto.getContent());
        if (dto.getStatus() != null) entity.setStatus(dto.getStatus());
        entity.setUpdateUser(currentUser.getCurrentUser());
        CampaignScriptQuestionResponseDTO response = campaignScriptQuestionResponseMapper.toDto(entity);
        response.setCreateFullName(entity.getCreateUser().getFirstName());
        response.setUpdateFullName(entity.getUpdateUser().getFirstName());
        log.info("Update question successful");
        log.info(response.toString());
        CampaignScript campaignScript = campaignScriptRepository.findById(dto.getCampaignScriptId()).get();
        Optional<User> userLog = userService.getUserWithAuthorities();
        actionLogService.saveActionLog(new ActionLogDTO(userLog.get().getId(), Constants.ACTION_LOG_TYPE.UPDATE + "",
            null, String.format("Cập nhật câu hỏi kịch bản chiến dịch: [%s] - Vị trí: [%s]", campaignScript.getName(), dto.getPosition()),
            new Date(), Constants.MENU_ID.OMS, Constants.MENU_ITEM_ID.campain_script, "CONFIG_MENU_ITEM"));
        return response;
    }

    @Override
    public void deleteQuestion(Long id, Long campaignScriptId) throws Exception {
        log.info("Processing delete question....");
        CampaignScript campaignScript = campaignScriptRepository.getOne(campaignScriptId);
        if (checkCampaignScriptInUse(campaignScriptId)) {
            throw new Exception("This Campaign script in used.");
        }
        CampaignScriptQuestion question = campaignScriptQuestionRepository.getOne(id);
        campaignScriptQuestionRepository.deleteById(id);
        Optional<User> userLog = userService.getUserWithAuthorities();
        actionLogService.saveActionLog(new ActionLogDTO(userLog.get().getId(), Constants.ACTION_LOG_TYPE.DELETE + "",
            null, String.format("Xóa câu hỏi kịch bản chiến dịch: [%s]", campaignScript.getName()),
            new Date(), Constants.MENU_ID.OMS, Constants.MENU_ITEM_ID.campain_script, "CONFIG_MENU_ITEM"));
        log.info("Delete question successfull!");
    }

    @Override
    public void deleteAllQuestion(Long campaignScriptId) throws Exception {
        log.info("Processing delete question....");
        if (!checkCampaignScriptInUse(campaignScriptId)) {
            List<CampaignScriptQuestion> questions = campaignScriptQuestionRepository.findByCampaignScriptId(campaignScriptId);
            for (CampaignScriptQuestion question : questions) {
                campaignScriptQuestionRepository.deleteById(question.getId());
            }
        }else {
            throw new Exception("This Campaign script in used.");
        }
        log.info("Delete question successfull!");
    }

    //Kiem tra xem kich ban da su dung chua
    public Boolean checkCampaignScriptInUse(Long campaignScriptId) {
        CampaignScript campaignScript = campaignScriptRepository.getOne(campaignScriptId);
        if (campaignScript.getStatus().equals("1")) return false;
        return true;
    }
}
