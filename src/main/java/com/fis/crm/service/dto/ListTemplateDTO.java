package com.fis.crm.service.dto;

import com.fis.crm.service.dto.DataReadDynamicDTO;

import java.util.List;

public class ListTemplateDTO {

   private List<DataReadDynamicDTO> lsTemplate;

    public List<DataReadDynamicDTO> getLsTemplate() {
        return lsTemplate;
    }

    public void setLsTemplate(List<DataReadDynamicDTO> lsTemplate) {
        this.lsTemplate = lsTemplate;
    }
}
