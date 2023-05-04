package com.fis.crm.service;

import com.fis.crm.config.Constants;
import com.fis.crm.web.rest.errors.BusinessException;
import org.springframework.stereotype.Service;

@Service
public class InOutBoundFactory {

    final InOutBoundService inBoundService;

    final InOutBoundService outBoundService;

    public InOutBoundFactory(InOutBoundService inBoundService, InOutBoundService outBoundService) {
        this.inBoundService = inBoundService;
        this.outBoundService = outBoundService;
    }

    public InOutBoundService getInOutBoundService(String channelType) throws Exception {
        switch (channelType) {
            case Constants.CHANNEL_TYPE.IN:
                return inBoundService;
            case Constants.CHANNEL_TYPE.OUT:
                return outBoundService;
            default:
                throw new BusinessException("100", "Khong ton tai service");
        }
    }

}
