package com.fis.crm.service.impl;

import com.fis.crm.repository.ActionTypeRepository;
import com.fis.crm.service.ActionTypeService;
import com.fis.crm.service.dto.ActionTypeDTO;
import com.fis.crm.service.mapper.ActionTypeMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ActionTypeServiceImpl implements ActionTypeService {

    private final Logger log = LoggerFactory.getLogger(ActionTypeServiceImpl.class);

    private final ActionTypeRepository actionTypeRepository;
    private final ActionTypeMapper actionTypeMapper;


    public ActionTypeServiceImpl(ActionTypeRepository actionTypeRepository, ActionTypeMapper actionTypeMapper) {

        this.actionTypeRepository = actionTypeRepository;
        this.actionTypeMapper = actionTypeMapper;
    }

    @Override
    @Transactional(readOnly = true)
    public List<ActionTypeDTO> findAll() {
        return actionTypeMapper.toDto(actionTypeRepository.findAll());
    }
}
