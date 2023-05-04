package com.fis.crm.service;


import com.fis.crm.commons.Translator;
import com.fis.crm.config.Constants;
import com.fis.crm.domain.CampaignResourceDetail;
import com.fis.crm.repository.CampaignResourceDetailRepository;
import com.fis.crm.web.rest.errors.BusinessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class OutBoundService extends InOutBoundService {

    final CampaignResourceDetailRepository campaignResourceDetailRepository;
    final EvaluateAssignmentDetailService evaluateAssignmentDetailService;

    public OutBoundService(CampaignResourceDetailRepository campaignResourceDetailRepository, EvaluateAssignmentDetailService evaluateAssignmentDetailService) {
        super(evaluateAssignmentDetailService);
        this.campaignResourceDetailRepository = campaignResourceDetailRepository;
        this.evaluateAssignmentDetailService = evaluateAssignmentDetailService;
    }


    @Override
    public boolean updateStatusCall(Long objectId) {
        campaignResourceDetailRepository.updateEvaluateStatus(objectId,Constants.IN_OUT_EVALUATE_STATUS.EVALUATED);
//        CampaignResourceDetail campaignResourceDetail = campaignResourceDetailRepository.findById(objectId).orElseThrow(()->
//            new BusinessException("101", Translator.toLocale("call.notfound")));
//        campaignResourceDetail.setEvaluateStatus(Constants.IN_OUT_EVALUATE_STATUS.EVALUATED);
//        campaignResourceDetailRepository.save(campaignResourceDetail);
        return true;
    }
}
