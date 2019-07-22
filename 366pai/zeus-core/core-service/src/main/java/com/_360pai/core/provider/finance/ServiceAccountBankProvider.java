package com._360pai.core.provider.finance;

import com._360pai.core.facade.finance.ServiceAccountBankFacade;
import com._360pai.core.facade.finance.req.AccountBankReq;
import com._360pai.core.facade.finance.resp.AccountBankDTO;
import com._360pai.core.model.order.TServiceAccountBank;
import com._360pai.core.service.finance.ServiceAccountBankService;
import com.alibaba.dubbo.config.annotation.Service;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author xiaolei
 * @create 2018-10-07 00:02
 */
@Component
@Service(version = "1.0.0")
public class ServiceAccountBankProvider implements ServiceAccountBankFacade {

    @Autowired
    private ServiceAccountBankService serviceAccountBankService;

    @Override
    public int addAccountBank(AccountBankReq.AddAccountBank req) {
        return serviceAccountBankService.addAccountBank(req);
    }

    @Override
    public AccountBankDTO getBindingCardByPartyId(Integer partyId) {
        TServiceAccountBank source = serviceAccountBankService.getBindingCardByPartyId(partyId);
        AccountBankDTO target = null;
        if (source != null) {
            target = new AccountBankDTO();
            BeanUtils.copyProperties(source, target);
            target.setBankId(source.getId());
            target.setBankNoSuffix(source.getBankNo().substring(source.getBankNo().length() - 4));
        }
        return target;
    }


}
