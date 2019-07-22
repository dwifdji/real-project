package com._360pai.core.provider.withfudig;

import com._360pai.arch.common.utils.OrderCodeGenerator;
import com._360pai.arch.common.utils.PageUtils;
import com._360pai.core.common.constants.WithfudigEnum;
import com._360pai.core.facade.withfudig.WithfudigRequirementFacade;
import com._360pai.core.facade.withfudig.req.WithfudigRequirementReq;
import com._360pai.core.facade.withfudig.resp.MyRequirementDetailResp;
import com._360pai.core.facade.withfudig.resp.WithfudigRequirementDetailResp;
import com._360pai.core.model.withfudig.TWithfudigRequirement;
import com._360pai.core.service.withfudig.WithfudigRequirementService;
import com._360pai.core.utils.ServiceMessageUtils;
import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.Random;

/**
 * 描述
 *
 * @author : whisky_vip
 * @date : 2018/9/7 15:58
 */
@Component
@Service(version = "1.0.0")
public class WithfudigRequirementProvider implements WithfudigRequirementFacade {

    String[] defaultImg =
            {
            "https://cdn-images.360pai.com/FjrgR0nLv1FxJfUFXvNYLbHBosxX",
            "https://cdn-images.360pai.com/FmXSjVjmlwVGZReLbqePhfF5DJWC",
            "https://cdn-images.360pai.com/Frm-bNhgsCR4zPDHmVKyYx8Pcfm8"
            };

    @Autowired
    private WithfudigRequirementService withfudigRequirementService;
    @Autowired
    private ServiceMessageUtils serviceMessageUtils;

    @Override
    public PageUtils.Page requirementListForPlatform(WithfudigRequirementReq.RequirementListForPlatform req) {

        return withfudigRequirementService.selectListForPlatform(req);
    }

    @Override
    public int requirementAdd(WithfudigRequirementReq.RequirementAdd req) {
        TWithfudigRequirement model = new TWithfudigRequirement();
        BeanUtils.copyProperties(req, model);
        model.setCreatedTime(new Date());
        model.setRequirementNo(OrderCodeGenerator.INSTANCE.getOrderCode(OrderCodeGenerator.WITHFUDIG_REQUIREMENT_NO));
        model.setRequirementStatus(WithfudigEnum.RequirementStatus.WAITING_PASS.getValue() + "");
        // 需求延后
//        model.setExtra(getImg());
        int i = withfudigRequirementService.requirementAdd(model);
        if (i > 0) {
            // 发送客服邮件
            serviceMessageUtils.withfudigAdd(model.getId());
        }
        return i;
    }

    @Override
    public int requirementRelateAssertId(WithfudigRequirementReq.RequirementRelateAssertId req) {

        return withfudigRequirementService.requirementRelateAssertId(req);
    }

    @Override
    public int requirementUpdate(WithfudigRequirementReq.RequirementUpdate req) {
        return withfudigRequirementService.requirementUpdate(req);
    }

    @Override
    public int updateFollowCount(String requirementId) {
        return withfudigRequirementService.updateFollowCount(requirementId);
    }

    @Override
    public int specialNoticeUpdate(WithfudigRequirementReq.SpecialNoticeUpdate req) {
        return withfudigRequirementService.specialNoticeUpdate(req);
    }

    @Override
    public PageUtils.Page requirementFollowList(WithfudigRequirementReq.RequirementListForPlatform req) {
        return withfudigRequirementService.requirementFollowList(req);
    }

    @Override
    public PageUtils.Page requirementListForAdmin(WithfudigRequirementReq.RequirementListForAdmin req) {
        return withfudigRequirementService.requirementListForAdmin(req);
    }

    @Override
    public WithfudigRequirementDetailResp requirementDetail(WithfudigRequirementReq.RequirementDetailReq req, Boolean isAdmin) {
        return withfudigRequirementService.requirementDetail(req, isAdmin);
    }

    @Override
    public int updateRequirementStatus(WithfudigRequirementReq.RequirementStatusUpdate req) {
        return withfudigRequirementService.updateRequirementStatus(req);
    }

    @Override
    public int requirementFinished(WithfudigRequirementReq.RequirementId req) {
        return withfudigRequirementService.requirementFinished(req);
    }

    @Override
    public PageUtils.Page myRequirementList(WithfudigRequirementReq.MyRequirementList req) {
        List<MyRequirementDetailResp> list = withfudigRequirementService.myRequirementList(req);
        return PageUtils.fen(req.getPage(), req.getPerPage(), list);
    }

    private JSONObject getImg() {
        int i = new Random().nextInt(3);
        String[] s = {defaultImg[i]};
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("image", s);
        return jsonObject;
    }
}
