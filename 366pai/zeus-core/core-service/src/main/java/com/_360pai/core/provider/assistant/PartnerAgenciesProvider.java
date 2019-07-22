package com._360pai.core.provider.assistant;

import com._360pai.core.facade.assistant.PartnerAgenciesFacade;
import com._360pai.core.facade.assistant.req.PartnerAgencyReq;
import com._360pai.core.model.assistant.PartnerAgency;
import com._360pai.core.service.assistant.PartnerAgencyService;
import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author zxiao
 * @Title: PartnerAgenciesProvider
 * @ProjectName zeus-parent
 * @Description:
 * @date 2018/8/22 18:48
 */
@Component
@Service(version = "1.0.0")
public class PartnerAgenciesProvider implements PartnerAgenciesFacade {
    @Autowired
    private PartnerAgencyService partnerAgencyService;

    @Override
    public PageInfo selectPartnerAgenciesList(PartnerAgencyReq req) {
        return partnerAgencyService.selectPartnerAgenciesList(req.getPage(), req.getPerPage(), "order_num asc");
    }

    @Override
    public int addPartnerAgency(PartnerAgencyReq req) {
        return partnerAgencyService.addPartnerAgency(copyPartnerAgency(req));
    }

    @Override
    public int editPartnerAgency(PartnerAgencyReq req) {
        return partnerAgencyService.editPartnerAgency(copyPartnerAgency(req));
    }

    @Override
    public int deletePartnerAgency(PartnerAgencyReq req) {
        return partnerAgencyService.deletePartnerAgency(copyPartnerAgency(req));
    }

    private PartnerAgency copyPartnerAgency(PartnerAgencyReq req) {
        PartnerAgency partnerAgency = new PartnerAgency();
        BeanUtils.copyProperties(req, partnerAgency);
        return partnerAgency;
    }
}
