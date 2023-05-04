package com.fis.crm.service.impl;

import com.fis.crm.commons.DataUtil;
import com.fis.crm.commons.Translator;
import com.fis.crm.config.Constants;
import com.fis.crm.domain.ConfigMenuItem;
import com.fis.crm.domain.Role;
import com.fis.crm.domain.RoleMenuItem;
import com.fis.crm.repository.ConfigMenuItemRepository;
import com.fis.crm.repository.RoleRepository;
import com.fis.crm.service.ConfigMenuItemService;
import com.fis.crm.service.UserService;
import com.fis.crm.service.dto.ActionLogDTO;
import com.fis.crm.service.dto.ConfigMenuItemAddDTO;
import com.fis.crm.service.dto.ConfigMenuItemDTO;
import com.fis.crm.service.dto.FunctionItemDTO;
import com.fis.crm.service.mapper.ConfigMenuItemMapper;
import com.fis.crm.web.rest.errors.BusinessException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link ConfigMenuItem}.
 */
@Service
@Transactional
public class ConfigMenuItemServiceImpl implements ConfigMenuItemService {

    private final Logger log = LoggerFactory.getLogger(ConfigMenuItemServiceImpl.class);

    private final ConfigMenuItemRepository configMenuItemRepository;
    private final ConfigMenuItemMapper configMenuItemMapper;
    private final RoleRepository roleRepository;
    private final UserService userService;

    public ConfigMenuItemServiceImpl(ConfigMenuItemRepository configMenuItemRepository, ConfigMenuItemMapper configMenuItemMapper, RoleRepository roleRepository, UserService userService) {
        this.configMenuItemRepository = configMenuItemRepository;
        this.configMenuItemMapper = configMenuItemMapper;
        this.roleRepository = roleRepository;
        this.userService = userService;
    }

    @Override
    public Boolean save(ConfigMenuItemDTO configMenuItemDTO, Consumer consumer) {
        log.debug("Request to save ConfigMenuItem : {}", configMenuItemDTO);
        validateBeforeConfigMenuItem(configMenuItemDTO);
        ConfigMenuItem configMenuItem = configMenuItemMapper.toEntity(configMenuItemDTO);
        configMenuItem.setStatus(Constants.STATUS_ACTIVE_INT);
        configMenuItem = configMenuItemRepository.save(configMenuItem);
        if(consumer != null) {
            Long userId = userService.getUserIdLogin();
            consumer.accept(new ActionLogDTO(userId, Constants.ACTION_LOG_TYPE.INSERT + "",
                configMenuItem.getId(), String.format("Thêm mới thao tác: [%s-%s]", configMenuItem.getMenuItemCode(), configMenuItem.getMenuItemName()),
                new Date(), Constants.MENU_ID.SYSTEM_MANAGEMENT, Constants.MENU_ITEM_ID.MANAGEMENT_ROLE, "CONFIG_MENU_ITEM"));
        }
        return true;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ConfigMenuItemDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ConfigMenuItems");
        return configMenuItemRepository.findAll(pageable).map(configMenuItemMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ConfigMenuItemDTO> findOne(Long id) {
        log.debug("Request to get ConfigMenuItem : {}", id);
        return configMenuItemRepository.findById(id).map(configMenuItemMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete ConfigMenuItem : {}", id);
        configMenuItemRepository.deleteById(id);
    }

    @Override
    public Boolean update(ConfigMenuItemDTO configMenuItemDTO, Consumer consumer) {
        validateBeforeConfigMenuItem(configMenuItemDTO);
        ConfigMenuItem configMenuItem = configMenuItemRepository.findByIdOrMenuItemCode(configMenuItemDTO.getId(), configMenuItemDTO.getMenuItemCode()).orElseThrow(() -> new BusinessException("101",
            Translator.toLocale("config.menu.item.action.notfound")));
        String codeOld = configMenuItem.getMenuItemCode();
        String nameOld = configMenuItem.getMenuItemName();
        configMenuItem.menuItemName(configMenuItemDTO.getMenuItemName());
        configMenuItem.setUpdateTime(new Date());
        configMenuItem = configMenuItemRepository.save(configMenuItem);
        if(consumer != null) {
            Long userId = userService.getUserIdLogin();
            consumer.accept(new ActionLogDTO(userId, Constants.ACTION_LOG_TYPE.UPDATE + "",
                configMenuItem.getId(), String.format("Cập nhật thao tác: [%s-%s] thành [%s-%s]", codeOld, nameOld, configMenuItem.getMenuItemCode(), configMenuItem.getMenuItemName()),
                new Date(), Constants.MENU_ID.SYSTEM_MANAGEMENT, Constants.MENU_ITEM_ID.MANAGEMENT_ROLE, "CONFIG_MENU_ITEM"));
        }
        return true;
    }

    @Override
    public Boolean addFunctionMenu(ConfigMenuItemAddDTO configMenuItemAddDTO, Consumer consumer) {
        Role role = roleRepository.getRoleMenuItemByRole(configMenuItemAddDTO.getRoleId(), configMenuItemAddDTO.getMenuId()).orElseThrow(() ->
            new BusinessException("101", Translator.toLocale("role.not.found")));
        List<RoleMenuItem> roleMenuItemList = role.getRoleMenuItemList();
        // TODO cap nhat chuc nang va tac dong cho quyen he thong
        List<FunctionItemDTO> functionItemDTOS = configMenuItemAddDTO.getFunctionItemDTOS();
//        List<RoleMenuItem> roleMenuItemsAdd = new ArrayList<>();
//        List<RoleMenuItem> roleMenuItemsRemove = new ArrayList<>();
        Long userId = userService.getUserIdLogin();
        StringBuilder stringBuilderAdd = new StringBuilder();
        StringBuilder stringBuilderRemove = new StringBuilder();
        roleMenuItemList.forEach(roleMenuItem -> {
            Long configMenuItemId = roleMenuItem.getConfigMenuItem().getId();
//            if(roleMenuItem.getMenuId().equals(configMenuItemAddDTO.getMenuId())) {
                if(functionItemDTOS.stream().anyMatch(functionItemDTO -> functionItemDTO.getId().equals(configMenuItemId))) {
                    if(Constants.STATUS_INACTIVE_STR.equals(roleMenuItem.getStatus())) {
                        roleMenuItem.setStatus(Constants.STATUS_ACTIVE);
                        roleMenuItem.setUpdateUser(userId);
                        roleMenuItem.setUpdateDatetime(new Date());
//                        roleMenuItemsAdd.add(roleMenuItem);
                        stringBuilderAdd.append(String.format("%s, ", roleMenuItem.getConfigMenuItem().getMenuItemName()));
                    }
                } else {
                    if(Constants.STATUS_ACTIVE.equals(roleMenuItem.getStatus())) {
                        roleMenuItem.setStatus(Constants.STATUS_INACTIVE_STR);
                        roleMenuItem.setUpdateUser(userId);
                        roleMenuItem.setUpdateDatetime(new Date());
//                        roleMenuItemsRemove.add(roleMenuItem);
                        stringBuilderRemove.append(String.format("%s, ", roleMenuItem.getConfigMenuItem().getMenuItemName()));
                    }
                }
//            }
        });

        List<FunctionItemDTO> roleMenuItemsAddNew = functionItemDTOS.stream().filter(
            functionItemDTO -> roleMenuItemList.stream().filter(roleMenuItem -> roleMenuItem.getMenuId().equals(configMenuItemAddDTO.getMenuId())).noneMatch(roleMenuItem -> roleMenuItem.getConfigMenuItem().getId().equals(functionItemDTO.getId()))
        ).collect(Collectors.toList());

        if(!DataUtil.isNullOrEmpty(roleMenuItemsAddNew)) {
            roleMenuItemList.addAll(roleMenuItemsAddNew.stream().map(functionItemDTO -> {
                ConfigMenuItem configMenuItem = configMenuItemRepository.findById(functionItemDTO.getId()).get();
                stringBuilderAdd.append(String.format("%s, ", configMenuItem.getMenuItemName()));
                RoleMenuItem roleMenuItem = new RoleMenuItem();
                roleMenuItem.setMenuId(functionItemDTO.getMenuId());
                roleMenuItem.setRole(role);
                roleMenuItem.setCreateUser(userId);
                roleMenuItem.setCreateDatetime(new Date());
                roleMenuItem.setConfigMenuItem(new ConfigMenuItem(functionItemDTO.getId()));
                roleMenuItem.setStatus(Constants.STATUS_ACTIVE);
                return roleMenuItem;
            }).collect(Collectors.toList()));
        }
        roleRepository.save(role);
        if(consumer != null) {
            if(!stringBuilderAdd.toString().isEmpty() ||  !stringBuilderRemove.toString().isEmpty()) {
                String note = String.format("Thay đổi cấu hình nhóm quyền [%s]: \n" +
                    "o\tBổ sung: [%s];\n" +
                    "o\tThu hồi: [%s]\n", role.getName(), stringBuilderAdd, stringBuilderRemove);
                consumer.accept(new ActionLogDTO(userId, Constants.ACTION_LOG_TYPE.UPDATE + "",
                    role.getId(), note, new Date(),
                    Constants.MENU_ID.SYSTEM_MANAGEMENT, Constants.MENU_ITEM_ID.MANAGEMENT_ROLE, "CONFIG_MENU_ITEM"));
            }
        }
        return true;
    }

    @Override
    public Boolean action(ConfigMenuItemDTO configMenuItemDTO, Consumer consumer) {
        ConfigMenuItem configMenuItem = configMenuItemRepository.findById(configMenuItemDTO.getId()).orElseThrow(() -> new BusinessException("101",
            Translator.toLocale("config.menu.item.action.notfound")));

        if(configMenuItem.getStatus().equals(Constants.STATUS_INACTIVE)) {
            configMenuItem.setStatus(Constants.STATUS_ACTIVE_INT);
        } else {
            configMenuItem.setStatus(Constants.STATUS_INACTIVE);
        }
        configMenuItem.setUpdateTime(new Date());
        configMenuItemRepository.save(configMenuItem);
        if(consumer != null) {
            if(configMenuItem.getStatus().equals(Constants.STATUS_INACTIVE)) {
                Long userId = userService.getUserIdLogin();
                String note = String.format("Vô hiệu hoá thao tác: [%s-%s]", configMenuItem.getMenuItemCode(), configMenuItem.getMenuItemName());
                consumer.accept(new ActionLogDTO(userId, Constants.ACTION_LOG_TYPE.UPDATE + "",
                    configMenuItem.getId(), note, new Date(),
                    Constants.MENU_ID.SYSTEM_MANAGEMENT, Constants.MENU_ITEM_ID.MANAGEMENT_ROLE, "CONFIG_MENU_ITEM"));
            } else
            if(configMenuItem.getStatus().equals(Constants.STATUS_ACTIVE_INT)) {
                Long userId = userService.getUserIdLogin();
                Optional<ConfigMenuItem> optionalConfigMenuItem = configMenuItemRepository.findById(configMenuItem.getParentId());
                if(optionalConfigMenuItem.isPresent()) {
                    String note = String.format("[%s]: Khôi phục thao tác [%s]", optionalConfigMenuItem.get().getMenuItemName(),
                        configMenuItem.getMenuItemName());
                    consumer.accept(new ActionLogDTO(userId, Constants.ACTION_LOG_TYPE.DELETE + "",
                        configMenuItem.getId(), note, new Date(),
                        Constants.MENU_ID.SYSTEM_MANAGEMENT, Constants.MENU_ITEM_ID.MANAGEMENT_ROLE, "CONFIG_MENU_ITEM"));
                }
            }
        }
        return true;
    }

    void validateBeforeConfigMenuItem(ConfigMenuItemDTO configMenuItemDTO) {
        if(!StringUtils.hasLength(configMenuItemDTO.getMenuItemCode())) {
            throw new BusinessException("101", Translator.toLocale("config.menu.item.action.code.empty"));
        }
        if(!StringUtils.hasLength(configMenuItemDTO.getMenuItemName())) {
            throw new BusinessException("101", Translator.toLocale("config.menu.item.action.name.empty"));
        }
        if(configMenuItemDTO.getId() == null) {
            if(configMenuItemRepository.findByMenuItemCode(configMenuItemDTO.getMenuItemCode().toLowerCase()).isPresent()) {
               throw new BusinessException("101", Translator.toLocale("config.menu.item.action.code.exist"));
            }
        }
    }

}
