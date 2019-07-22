package com._360pai.web.controller.comprador;

import com._360pai.arch.common.ResponseModel;
import com._360pai.arch.common.utils.PageUtils;
import com._360pai.core.facade.comprador.CompradorRecommendFacade;
import com._360pai.core.facade.comprador.req.MyRecommendListReq;
import com._360pai.core.facade.comprador.req.RecommendAddReq;
import com._360pai.web.controller.AbstractController;
import com._360pai.web.controller.account.resp.AccountBaseInfo;
import com.alibaba.dubbo.config.annotation.Reference;
import com.google.common.collect.Maps;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * 描述 我要推介
 *
 * @author : whisky_vip
 * @date : 2018/9/4 15:15
 */
@RestController
public class RecommendController extends AbstractController {
    @Reference(version = "1.0.0")
    CompradorRecommendFacade compradorRecommendFacade;

    /**
     * 描述: 我要推介 记录增加
     *
     * @author : whisky_vip
     * @date : 2018/8/28 10:38
     */
    @PostMapping("/confined/comprador/recommendAdd")
    public ResponseModel recommendAdd(@RequestBody RecommendAddReq req) {
        isAuth();
        AccountBaseInfo accountBaseInfo = loadCurLoginAccountInfo();
        req.setAccountId(accountBaseInfo.getAccountId());
        req.setPartyId(accountBaseInfo.getPartyPrimaryId());
        int id = compradorRecommendFacade.recommendAdd(req);
        Map<String, Integer> result = Maps.newHashMap();
        result.put("recommendId", id);
        return ResponseModel.succ(result);

    }

    /**
     * 描述 个人中心 我的大买办推介需求 列表
     *
     * @author : whisky_vip
     * @date : 2018/8/28 10:38
     */
    @PostMapping("/confined/comprador/myRecommendList")
    public ResponseModel myRecommendList(@RequestBody MyRecommendListReq req) {
        isAuth();
        AccountBaseInfo accountBaseInfo = loadCurLoginAccountInfo();
        req.setPartyId(accountBaseInfo.getPartyPrimaryId());
        PageUtils.Page result = compradorRecommendFacade.myRecommendList(req);
        return ResponseModel.succ(result);
    }
}
