package com.fis.crm.service.dto;

import java.util.List;

public class RecordCallResultDTO {
    private List<AnswersDTO> lstResourceTemplate;
    private List<AnswersDTO> lstCampaignCallResult;

    public List<AnswersDTO> getLstResourceTemplate() {
        return lstResourceTemplate;
    }

    public void setLstResourceTemplate(List<AnswersDTO> lstResourceTemplate) {
        this.lstResourceTemplate = lstResourceTemplate;
    }

    public List<AnswersDTO> getLstCampaignCallResult() {
        return lstCampaignCallResult;
    }

    public void setLstCampaignCallResult(List<AnswersDTO> lstCampaignCallResult) {
        this.lstCampaignCallResult = lstCampaignCallResult;
    }
}
