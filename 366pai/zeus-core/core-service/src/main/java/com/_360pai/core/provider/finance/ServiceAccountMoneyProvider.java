package com._360pai.core.provider.finance;

import com._360pai.core.facade.finance.ServiceAccountMoneyFacade;
import com._360pai.core.facade.finance.resp.AccountMoneyDTO;
import com._360pai.core.model.order.TServiceAccountMoney;
import com._360pai.core.service.finance.ServiceAccountMoneyService;
import com.alibaba.dubbo.config.annotation.Service;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author xiaolei
 * @create 2018-10-03 13:27
 */
@Component
@Service(version = "1.0.0")
public class ServiceAccountMoneyProvider implements ServiceAccountMoneyFacade {

    @Autowired
    private ServiceAccountMoneyService serviceAccountMoneyService;

    @Override
    public AccountMoneyDTO getAccountMoneyByPartyId(Integer partyId) {

        TServiceAccountMoney accountMoneyByPartyId = serviceAccountMoneyService.getAccountMoneyByPartyId(partyId);
        AccountMoneyDTO      dto                   = new AccountMoneyDTO();
        if (null != accountMoneyByPartyId) {
            BeanUtils.copyProperties(accountMoneyByPartyId, dto);
        }
        return dto;
    }
}
