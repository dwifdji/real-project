package com._360pai.web.controller.withfudig;

import com._360pai.arch.common.ResponseModel;
import com._360pai.arch.common.enums.ApiCallResult;
import com._360pai.arch.common.utils.PageUtils;
import com._360pai.core.facade.withfudig.WithfudigRequirementInvestFacade;
import com._360pai.core.facade.withfudig.req.WithfudigRequirementInvestReq;
import com._360pai.web.controller.AbstractController;
import com._360pai.web.controller.account.resp.AccountBaseInfo;
import com.alibaba.dubbo.config.annotation.Reference;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * 描述 配资乐 我要投标
 *
 * @author : whisky_vip
 * @date : 2018/9/10 10:10
 */
@RestController
public class WithfudigRequirementInvestController extends AbstractController {

    @Reference(version = "1.0.0")
    WithfudigRequirementInvestFacade withfudigRequirementInvestFacade;

    /**
     * 描述  配资乐 我要投标
     *
     * @author : whisky_vip
     * @date : 2018/9/10 10:10
     */
    @PostMapping("/confined/withfudig/requirementInvestAdd")
    public ResponseModel requirementInvestAdd(@RequestBody WithfudigRequirementInvestReq.RequirementInvestAdd req) {
        isAuth();
        Assert.notNull(req.getRequirementId(), "requirementId 参数不能为空");
        AccountBaseInfo accountBaseInfo = loadCurLoginAccountInfo();
        if (!accountBaseInfo.isFunder()) {
            return ResponseModel.fail(ApiCallResult.NO_AUTH_FUNDER);
        }
        req.setAccountId(accountBaseInfo.getAccountId());
        req.setPartyId(accountBaseInfo.getPartyPrimaryId());
        int count = withfudigRequirementInvestFacade.requirementInvestAdd(req);
        return ResponseModel.wrapCount(count);
    }

    /**
     * 描述 个人中心 我的投标 列表
     *
     * @author : whisky_vip
     * @date : 2018/9/10 10:15
     */
    @PostMapping("/confined/withfudig/myRequirementInvestList")
    public ResponseModel myRequirementInvestList(@RequestBody WithfudigRequirementInvestReq.MyRequirementInvestList req) {
        isAuth();
        AccountBaseInfo accountBaseInfo = loadCurLoginAccountInfo();
        req.setPartyId(accountBaseInfo.getPartyPrimaryId());
        PageUtils.Page result = withfudigRequirementInvestFacade.myRequirementInvestList(req);
        return ResponseModel.succ(result);
    }

}
