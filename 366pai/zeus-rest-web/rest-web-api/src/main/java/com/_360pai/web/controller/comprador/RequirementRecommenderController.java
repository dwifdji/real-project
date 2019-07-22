package com._360pai.web.controller.comprador;

import com._360pai.arch.common.ResponseModel;
import com._360pai.arch.common.utils.PageUtils;
import com._360pai.core.facade.comprador.CompradorRequirementRecommenderFacade;
import com._360pai.core.facade.comprador.req.MyRequirementRecommenderListReq;
import com._360pai.core.facade.comprador.req.RequirementRecommenderAddReq;
import com._360pai.web.controller.AbstractController;
import com._360pai.web.controller.account.resp.AccountBaseInfo;
import com.alibaba.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * 描述 我有资产 投标
 *
 * @author : whisky_vip
 * @date : 2018/9/4 15:15
 */
@RestController
public class RequirementRecommenderController extends AbstractController {
    @Reference(version = "1.0.0")
    CompradorRequirementRecommenderFacade compradorRequirementRecommenderFacade;

    /**
     * 描述 我有资产 对大买办需求单对应的响应 投标
     *
     * @author : whisky_vip
     * @date : 2018/8/30 10:10
     */
    @PostMapping("/confined/comprador/requirementRecommenderAdd")
    public ResponseModel requirementRecommender(@RequestBody RequirementRecommenderAddReq req) {
        isAuth();
        AccountBaseInfo accountBaseInfo = loadCurLoginAccountInfo();
        req.setAccountId(accountBaseInfo.getAccountId());
        req.setPartyId(accountBaseInfo.getPartyPrimaryId());
        int count = compradorRequirementRecommenderFacade.requirementRecommenderAdd(req);
        return ResponseModel.wrapCount(count);
    }

    /**
     * 描述 个人中心 我有资产 投标进展
     *
     * @author : whisky_vip
     * @date : 2018/9/5 14:50
     */
    @PostMapping("/confined/comprador/myRequirementRecommenderList")
    public ResponseModel myRequirementRecommenderList(@RequestBody MyRequirementRecommenderListReq req) {
        isAuth();
        AccountBaseInfo accountBaseInfo = loadCurLoginAccountInfo();
        req.setPartyId(accountBaseInfo.getPartyPrimaryId());
        PageUtils.Page result = compradorRequirementRecommenderFacade.myRequirementRecommenderList(req);
        return ResponseModel.succ(result);
    }


}

