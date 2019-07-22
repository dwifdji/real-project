package com._360pai.core.provider.withfudig;

import com._360pai.arch.common.utils.DateUtil;
import com._360pai.arch.common.utils.OrderCodeGenerator;
import com._360pai.arch.common.utils.PageUtils;
import com._360pai.core.condition.withfudig.TWithfudigRequirementInvestCondition;
import com._360pai.core.facade.withfudig.WithfudigRequirementInvestFacade;
import com._360pai.core.facade.withfudig.req.WithfudigRequirementInvestReq;
import com._360pai.core.facade.withfudig.resp.WithfudigRequirementInvestDetailResp;
import com._360pai.core.model.withfudig.TWithfudigRequirementInvest;
import com._360pai.core.service.withfudig.WithfudigRequirementInvestService;
import com.alibaba.dubbo.config.annotation.Service;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

/**
 * 描述
 *
 * @author : whisky_vip
 * @date : 2018/9/7 15:59
 */
@Component
@Service(version = "1.0.0")
public class WithfudigRequirementInvestProvider implements WithfudigRequirementInvestFacade {

    @Autowired
    private WithfudigRequirementInvestService withfudigRequirementInvestService;

    @Override
    public PageUtils.Page requirementInvestList(WithfudigRequirementInvestReq.RequirementInvestList req) {
        TWithfudigRequirementInvestCondition condition = new TWithfudigRequirementInvestCondition();
        condition.setRequirementId(req.getRequirementId());
        condition.setCreatedTime(StringUtils.isNotBlank(req.getCreatedTime()) ? DateUtil.parse(req.getCreatedTime(), DateUtil.NORM_DATE_PATTERN) : null);
        condition.setRemark(req.getRemarked());

        List<WithfudigRequirementInvestDetailResp> list = withfudigRequirementInvestService.requirementInvestList(condition);

        if (StringUtils.isNotBlank(req.getContact())) {
            list.removeIf(model -> !(model.getContactPhone().contains(req.getContact()) || model.getContactName().contains(req.getContact())));
        }

        return PageUtils.fen(req.getPage(), req.getPerPage(), list);
    }

    @Override
    public int requirementInvestMatchSuccess(WithfudigRequirementInvestReq.RequirementInvestRemark req) {
        return withfudigRequirementInvestService.requirementInvestMatchSuccess(req);
    }

    @Override
    public int requirementInvestRemark(WithfudigRequirementInvestReq.RequirementInvestRemark req) {
        return withfudigRequirementInvestService.requirementInvestRemark(req);
    }

    @Override
    public int requirementInvestAdd(WithfudigRequirementInvestReq.RequirementInvestAdd req) {
        TWithfudigRequirementInvest tWithfudigRequirementInvest = new TWithfudigRequirementInvest();
        BeanUtils.copyProperties(req, tWithfudigRequirementInvest);
        tWithfudigRequirementInvest.setCreatedTime(new Date());
        tWithfudigRequirementInvest.setInvestNo(OrderCodeGenerator.INSTANCE.getOrderCode(OrderCodeGenerator.WITHFUDIG_REQUIREMENT_INVEST_NO));
        return withfudigRequirementInvestService.requirementInvestAdd(tWithfudigRequirementInvest);
    }

    @Override
    public PageUtils.Page myRequirementInvestList(WithfudigRequirementInvestReq.MyRequirementInvestList req) {
        return withfudigRequirementInvestService.myRequirementInvestList(req);
    }
}
