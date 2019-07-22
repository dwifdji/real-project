package com._360pai.core.provider.comprador;

import com._360pai.arch.common.utils.DateUtil;
import com._360pai.arch.common.utils.OrderCodeGenerator;
import com._360pai.arch.common.utils.PageUtils;
import com._360pai.core.condition.comprador.TCompradorRequirementRecommenderCondition;
import com._360pai.core.facade.comprador.CompradorRequirementRecommenderFacade;
import com._360pai.core.facade.comprador.req.MyRequirementRecommenderListReq;
import com._360pai.core.facade.comprador.req.RequiementRecommenderRemarkReq;
import com._360pai.core.facade.comprador.req.RequirementRecommenderAddReq;
import com._360pai.core.facade.comprador.req.RequirementRecommenderQueryReq;
import com._360pai.core.facade.comprador.resp.RequirementRecommenderDetailResp;
import com._360pai.core.model.comprador.TCompradorRequirementRecommender;
import com._360pai.core.service.comprador.CompradorRequirementRecommenderService;
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
 * @date : 2018/9/5 09:14
 */
@Component
@Service(version = "1.0.0")
public class CompradorRequirementRecommenderProvider implements CompradorRequirementRecommenderFacade {

    @Autowired
    private CompradorRequirementRecommenderService compradorRequirementRecommenderService;

    @Override
    public int requirementRecommenderAdd(RequirementRecommenderAddReq req) {
        TCompradorRequirementRecommender tCompradorRequirementRecommender = new TCompradorRequirementRecommender();
        BeanUtils.copyProperties(req, tCompradorRequirementRecommender);
        tCompradorRequirementRecommender.setCreatedTime(new Date());
        tCompradorRequirementRecommender.setRecommenderNo(OrderCodeGenerator.INSTANCE.getOrderCode(OrderCodeGenerator.COMPRADOR_REQUIREMENT_RECOMMENDER_NO));
        return compradorRequirementRecommenderService.requirementRecommenderAdd(tCompradorRequirementRecommender);
    }


    @Override
    public PageUtils.Page requirementRecommenderList(RequirementRecommenderQueryReq req) {

        TCompradorRequirementRecommenderCondition condition = new TCompradorRequirementRecommenderCondition();
        condition.setRequirementId(req.getRequirementId());
        condition.setCreatedTime(StringUtils.isNotBlank(req.getCreatedTime()) ? DateUtil.parse(req.getCreatedTime(), DateUtil.NORM_DATE_PATTERN) : null);
        condition.setRemark(req.getRemarked());

        List<RequirementRecommenderDetailResp> list = compradorRequirementRecommenderService.requirementRecommenderList(condition);

        if (StringUtils.isNotBlank(req.getContact())) {
            list.removeIf(model -> !(model.getContactPhone().contains(req.getContact()) || model.getContactName().contains(req.getContact())));
        }

        return PageUtils.fen(req.getPage(), req.getPerPage(), list);
    }

    @Override
    public int requirementRecommenderRemark(RequiementRecommenderRemarkReq req) {
        return compradorRequirementRecommenderService.requirementRecommenderRemark(req);
    }

    @Override
    public int requirementRecommenderMatchSuccess(RequiementRecommenderRemarkReq req) {
        return compradorRequirementRecommenderService.requirementRecommenderMatchSuccess(req);
    }

    @Override
    public PageUtils.Page myRequirementRecommenderList(MyRequirementRecommenderListReq req) {
        return compradorRequirementRecommenderService.myRequirementRecommenderList(req);
    }
}
