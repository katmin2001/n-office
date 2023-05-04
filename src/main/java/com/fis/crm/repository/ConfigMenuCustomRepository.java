package com.fis.crm.repository;

import com.fis.crm.service.dto.MenuItemDTO;

import java.util.List;

public interface ConfigMenuCustomRepository {
    List<MenuItemDTO> getMenuTreeLogin(String login);
}
