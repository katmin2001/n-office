package com.fis.crm.service.impl;

import com.fis.crm.commons.DataUtil;
import com.fis.crm.config.Constants;
import com.fis.crm.domain.OptionSetValue;
import com.fis.crm.domain.User;
import com.fis.crm.repository.OptionSetValueRepository;
import com.fis.crm.service.OptionSetValueService;
import com.fis.crm.service.UserService;
import com.fis.crm.service.dto.*;
import com.fis.crm.service.mapper.OptionSetValueMapper;
import liquibase.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link OptionSetValue}.
 */
@Service
@Transactional
public class OptionSetValueServiceImpl implements OptionSetValueService {

    private final Logger log = LoggerFactory.getLogger(OptionSetValueServiceImpl.class);

    private final OptionSetValueRepository optionSetValueRepository;
    private final OptionSetValueMapper optionSetValueMapper;
    @Autowired
    private UserService userService;
    @Autowired
    private ActionLogServiceImpl actionLogServiceImpl;
    @Autowired
    private OptionSetServiceImpl optionSetServiceImpl;
    @PersistenceContext
    EntityManager em;

    public OptionSetValueServiceImpl(OptionSetValueRepository optionSetRepository, OptionSetValueMapper optionSetValueMapper) {
        this.optionSetValueRepository = optionSetRepository;
        this.optionSetValueMapper = optionSetValueMapper;
    }

    @Override
    public OptionSetValueDTO save(OptionSetValueDTO optionSetValueDTO) {
        log.debug("Request to save optionSetValue  : {}", optionSetValueDTO);
        Optional<User> user = userService.getUserWithAuthorities();
        Instant now = Instant.now();
        if (optionSetValueDTO.getId() == null)  //add new
        {
            optionSetValueDTO.setCreateUser(user.get().getId());
            optionSetValueDTO.setCreateDate(now);
//            optionSetValueDTO.setUpdateUser(user.get().getId());
//            optionSetValueDTO.setUpdateDate(now);
            optionSetValueDTO.setStatus("1"); //1 hieu luc , 0 het hieu luc
        } else {  //update
            optionSetValueDTO.setUpdateUser(user.get().getId());
            optionSetValueDTO.setUpdateDate(now);
        }
        OptionSetValue optionSetValue = optionSetValueMapper.toEntity(optionSetValueDTO);
        //validate new
        OptionSetValueDTO result = new OptionSetValueDTO();
        if (optionSetValueDTO.getId() == null) {
            Optional<OptionSetValue> objExisting = optionSetValueRepository.findOptionSetValueByCode(optionSetValueDTO.getCode().trim());
            if (objExisting.isPresent()) {
                result.setErrorCodeConfig("Mã danh mục đã tồn tại");
                return result;
            }
            if (optionSetValueDTO.getOptionSetId() == null) {
                result.setErrorCodeConfig("Chưa khai báo loại danh mục");
                return result;
            }
            Optional<OptionSetDTO> optionSet = optionSetServiceImpl.findOne(optionSetValueDTO.getOptionSetId());
            if (!optionSet.isPresent()) {
                result.setErrorCodeConfig("Loại danh mục chưa tồn tại");
                return result;
            }
            OptionSetValue maxObj = findMaxIdByOptionSet(optionSetValueDTO.getOptionSetId());
            optionSetValue.setOrd(maxObj.getOrd());
        }
        //validate update
        else if (optionSetValueDTO.getId() != null) {
            Optional<OptionSetValueDTO> oldObj = optionSetValueRepository.findById(optionSetValueDTO.getId()).map(optionSetValueMapper::toDto);
            if (!oldObj.isPresent()) {
                result.setErrorCodeConfig("Danh mục chưa tồn tại");
                return result;
            }
//            if (oldObj.get().getOrd() != null && optionSetValue.getOrd() != null) {
//                Optional<OptionSetValue> objFoundByOrd = optionSetValueRepository.findOptSetValueByOrd(optionSetValue.getOrd());
//                objFoundByOrd.get().setOrd(oldObj.get().getOrd());
//                optionSetValueRepository.save(objFoundByOrd.get());
//            }

            //set lai gia tri ko dc thay doi
            optionSetValue.setoptionSetId(oldObj.get().getOptionSetId());
            optionSetValue.setCode(oldObj.get().getCode());
            optionSetValue.setFromDate(oldObj.get().getFromDate());
            optionSetValue.setCreateDate(oldObj.get().getCreateDate());
            optionSetValue.setCreateUser(oldObj.get().getCreateUser());
        }
        optionSetValue = optionSetValueRepository.save(optionSetValue); //save or update
        if (optionSetValueDTO.getId() == null) {
            actionLogServiceImpl.saveActionLog(user.get().getId(), Constants.ACTION_LOG_TYPE.INSERT + "", optionSetValue.getId(), "Option_Set Value", "", now);
        } else
            actionLogServiceImpl.saveActionLog(user.get().getId(), Constants.ACTION_LOG_TYPE.UPDATE + "", optionSetValue.getId(), "Option_Set Valuet", "", now);
        return optionSetValueMapper.toDto(optionSetValue);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<OptionSetValueDTO> findAll(Pageable pageable) {
        log.debug("Request to get all OptionSetValue");
        return optionSetValueRepository.findAll(pageable)
            .map(optionSetValueMapper::toDto);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<OptionSetValueDTO> findOne(Long id) {
        log.debug("Request to get OptionSetValue : {}", id);
        return optionSetValueRepository.findById(id)
            .map(optionSetValueMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete OptionSetValue : {}", id);
        optionSetValueRepository.deleteById(id);
    }

    @Override
    public OptionSetValue findMaxIdByOptionSet(Long id) {
        OptionSetValue lst = new OptionSetValue();
        BigDecimal MAX;
        try {
            StringBuilder sb = new StringBuilder(
                " select count(b.id)+1 ORD from option_set a , option_set_value b " +
                    " where a.option_set_id = b.option_set_id and a.option_set_id=:optionSetId ");
            Query query = em.createNativeQuery(sb.toString());
            query.setParameter("optionSetId", id);
            MAX = (BigDecimal) query.getSingleResult();
            lst.setOrd(MAX.intValue());
            System.out.println(MAX);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return lst;
    }

    @Override
    public List<OptionSetValueDTO> find(OptionSetValueDTO obj) {
        List<Object[]> lst = new ArrayList<Object[]>();
        List<OptionSetValueDTO> lstResult = new ArrayList<OptionSetValueDTO>();
        try {
            StringBuilder sb = new StringBuilder(" " +
                " SELECT * FROM ( SELECT os.ID id,os.OPTION_SET_ID optionSetId,os.CODE code, os.NAME name, " +
                " os.ORD ord,os.GROUP_NAME groupName, " +
                " os.FROM_DATE fromDate,os.END_DATE endDate, os.STATUS status, " +
                " os.CREATE_DATE createDate,os.CREATE_USER createUser, " +
                " os.UPDATE_DATE updateDate,os.UPDATE_USER updateUser, " +
                " os.C1 c1,os.C2 c2 " +
                " FROM OPTION_SET_VALUE os " +
                " WHERE 1=1 ");
            if (StringUtil.isNotEmpty(obj.getCode())) {
                sb.append(" AND os.code like (:code) ");
            }
            if (StringUtil.isNotEmpty(obj.getName())) {
                sb.append(" AND os.name like (:name) ");
            }
            if (StringUtil.isNotEmpty(obj.getStatus())) {
                sb.append(" AND os.status = :status ");
            }
            sb.append(" ORDER BY os.UPDATE_DATE desc ) ");
            sb.append(" WHERE ( ROWNUM > :limit and ROWNUM <= :offset ) ");

            Query query = em.createNativeQuery(sb.toString());
            if (StringUtil.isNotEmpty(obj.getCode())) {
                query.setParameter("code", "%" + obj.getCode().trim() + "%");
            }
            if (StringUtil.isNotEmpty(obj.getName())) {
                query.setParameter("name", "%" + obj.getName().trim() + "%");
            }
            if (StringUtil.isNotEmpty(obj.getStatus())) {
                query.setParameter("status", obj.getStatus());
            }
            int limit = obj.getSize();
            int page = obj.getPage();
            query.setParameter("limit", (page * limit) - limit);
            query.setParameter("offset", page * limit);

            lst = query.getResultList();
            for (Object[] obj1 : lst) {
                OptionSetValueDTO cal = new OptionSetValueDTO();
                cal.setId(DataUtil.safeToLong(obj1[0]));
                cal.setOptionSetId(DataUtil.safeToLong(obj1[1]));
                cal.setCode(DataUtil.safeToString(obj1[2]));
                cal.setName(DataUtil.safeToString(obj1[3]));
                cal.setOrd(DataUtil.safeToInt(obj1[4]));
                cal.setGroupName(DataUtil.safeToString(obj1[5]));
                cal.setFromDate(DataUtil.safeToInstant(obj1[6]));
                cal.setEndDate(DataUtil.safeToInstant(obj1[7]));
                cal.setStatus(DataUtil.safeToString(obj1[8]));
                cal.setCreateDate(DataUtil.safeToInstant(obj1[9]));
                cal.setCreateUser(DataUtil.safeToLong(obj1[10]));
                cal.setUpdateDate(DataUtil.safeToInstant(obj1[11]));
                cal.setUpdateUser(DataUtil.safeToLong(obj1[12]));
                cal.setC1(DataUtil.safeToString(obj1[13]));
                cal.setC2(DataUtil.safeToString(obj1[14]));
                lstResult.add(cal);
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return lstResult;
    }

    @Override
    public OptionSetValueDTO changeStatus(OptionSetValueDTO obj) {
        Optional<OptionSetValueDTO> optionSV = findOne(obj.getId());
        if (null != optionSV.get().getStatus()) {
            if (optionSV.get().getStatus().equals("1"))  //change status
                optionSV.get().setStatus("0");
            else
                optionSV.get().setStatus("1");
        } else
            optionSV.get().setStatus("1"); //default

        OptionSetValueDTO result = save(optionSV.get());
        return result;
    }

    public List<OptionSetValueDTO> getListDataOptionSetValue(QuerySearchOptionSetDTO querySearchOptionSetDTO) {
        return optionSetValueRepository.getListData(DataUtil.makeLikeParam(querySearchOptionSetDTO.getCode()),
            DataUtil.makeLikeParam(querySearchOptionSetDTO.getName()),
            querySearchOptionSetDTO.getStatus(),
            querySearchOptionSetDTO.getOptionSetId()).stream().map(OptionSetValueDTO::new).collect(Collectors.toList());
    }

    public Page<OptionSetValueDTO> getListDataOptionSetValue(QuerySearchOptionSetDTO querySearchOptionSetDTO, Pageable pageable) {
        return optionSetValueRepository.getListData(DataUtil.makeLikeParam(querySearchOptionSetDTO.getCode()),
            DataUtil.makeLikeParam(querySearchOptionSetDTO.getName()), querySearchOptionSetDTO.getStatus(),
            querySearchOptionSetDTO.getOptionSetId(),
            pageable).map(OptionSetValueDTO::new);
    }

    @Override
    public Optional<List<OptionSetValueDTO>> findOptSetValueByOptionSetCode(String code) {
        return optionSetValueRepository.findOptSetValueByOptionSetCode(code).map(optionSetValueMapper::toDto);
    }

    @Override
    public List<BussinessTypeDTO> getBusinessTypeForCombobox() {
        return optionSetValueRepository.getListBusinessTypeCombobox().stream()
            .map(BussinessTypeDTO::new)
            .collect(Collectors.toList());
    }

    @Override
    public List<RequestTypeDTO> getRequestTypeForCombobox() {
        return optionSetValueRepository.getListRequestTypeCombobox().stream()
            .map(RequestTypeDTO::new)
            .collect(Collectors.toList());
    }
}
