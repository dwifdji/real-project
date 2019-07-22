package com._360pai.web.controller.comprador;

import com._360pai.arch.common.RequestModel;
import com._360pai.arch.common.ResponseModel;
import com._360pai.arch.common.utils.PageUtils;
import com._360pai.core.common.constants.ServiceConfigEnum;
import com._360pai.core.exception.BusinessException;
import com._360pai.core.facade.assistant.ServiceConfigFacade;
import com._360pai.core.facade.comprador.CompradorRequirementFacade;
import com._360pai.core.facade.comprador.req.CompradorRequirementQueryReq;
import com._360pai.core.facade.comprador.req.CompradorRequirementReq;
import com._360pai.core.facade.comprador.req.MyRequirementListReq;
import com._360pai.core.facade.comprador.req.RequirementListForPlatformReq;
import com._360pai.core.facade.comprador.resp.CompradorDetailResp;
import com._360pai.core.facade.comprador.resp.CompradorListByBuildingTypeResp;
import com._360pai.web.controller.AbstractController;
import com._360pai.web.controller.account.resp.AccountBaseInfo;
import com.alibaba.dubbo.config.annotation.Reference;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 描述
 *
 * @author : whisky_vip
 * @date : 2018/9/4 15:16
 */
@Slf4j
@RestController
public class RequirementController extends AbstractController {

    @Reference(version = "1.0.0")
    CompradorRequirementFacade compradorRequirementFacade;

    @Reference(version = "1.0.0")
    ServiceConfigFacade serviceConfigFacade;

    /**
     * 描述 大买办 发布需求价格
     *
     * @author : whisky_vip
     * @date : 2018/10/10 16:57
     */
    @PostMapping("/open/comprador/compradorPrice")
    public ResponseModel getCompradorPrice() {
        String                  price  = serviceConfigFacade.selectByConfigType(ServiceConfigEnum.COMPRADOR_REQUIREMENT_PRICE);
        Map<String, BigDecimal> result = Maps.newHashMap();
        result.put("compradorPrice", new BigDecimal(price));
        return ResponseModel.succ(result);
    }

    /**
     * 根据类型返回前端 每种类型5条数据，根据时间排序，最新5条
     *
     * @author : whisky_vip
     * @date : 2018/9/1 10:36
     */
    @PostMapping("/open/comprador/requirementListByBuildingType")
    public ResponseModel requirementListByBuildingType(@RequestBody RequestModel req) {
        List<CompradorListByBuildingTypeResp> resp = compradorRequirementFacade.requirementListByBuildingType();
        return ResponseModel.succ(resp);
    }


    /**
     * 描述 资产大买办 列表 更多
     *
     * @author : whisky_vip
     * @date : 2018/8/30 10:07
     */
    @PostMapping("/open/comprador/requirementList")
    public ResponseModel requirementListForPlatform(@RequestBody RequirementListForPlatformReq req) {
        PageUtils.Page resp = compradorRequirementFacade.requirementListForPlatform(req);
        return ResponseModel.succ(resp);
    }

    /**
     * 描述 我的置业需求
     *
     * @author : whisky_vip
     * @date : 2018/8/30 10:07
     */
    @PostMapping("/confined/comprador/myRequirementList")
    public ResponseModel myRequirementList(@RequestBody MyRequirementListReq req) {
        isAuth();
        AccountBaseInfo accountBaseInfo = loadCurLoginAccountInfo();
        req.setPartyId(accountBaseInfo.getPartyPrimaryId());
        PageUtils.Page resp = compradorRequirementFacade.myRequirementList(req);
        return ResponseModel.succ(resp);
    }

    /**
     * 描述: 提交大买办需求 数据
     *
     * @author : whisky_vip
     * @date : 2018/8/28 10:38
     */
    @PostMapping("/confined/comprador/requirementAdd")
    public ResponseModel requirementAdd(@RequestBody CompradorRequirementReq.CompradorRequirementAdd req) {
        isAuth();
        AccountBaseInfo accountBaseInfo = loadCurLoginAccountInfo();
        req.setAccountId(accountBaseInfo.getAccountId());
        req.setPartyId(accountBaseInfo.getPartyPrimaryId());
        checkParamLegal(req);
        int                     id                  = compradorRequirementFacade.requirementAdd(req);
        HashMap<Object, Object> objectObjectHashMap = Maps.newHashMap();
        objectObjectHashMap.put("requirementId", id);
        return ResponseModel.succ(objectObjectHashMap);
    }

    /**
     * 描述: 大买办需求单 修改
     *
     * @author : whisky_vip
     * @date : 2018/8/28 10:38
     */
    @PostMapping("/confined/comprador/requirementUpdate")
    public ResponseModel requirementUpdate(@RequestBody CompradorRequirementReq.RequirementUpdate req) {
        isAuth();
        AccountBaseInfo accountBaseInfo = loadCurLoginAccountInfo();
        Assert.notNull(req.getRequirementId(), "requirementId 参数不对");
        req.setPartyId(accountBaseInfo.getPartyPrimaryId());
        checkParamLegal(req);
        int count = compradorRequirementFacade.requirementUpdate(req);
        return ResponseModel.wrapCount(count);

    }

    /**
     * 描述: 查看详情数据
     *
     * @author : whisky_vip
     * @date : 2018/8/28 10:38
     */
    @PostMapping("/confined/comprador/requirementDetail")
    public ResponseModel requirementDetail(@RequestBody CompradorRequirementQueryReq req) {
        Assert.notNull(req.getRequirementId(), "requirementId 参数不对");
        AccountBaseInfo accountBaseInfo = loadCurLoginAccountInfo();
        req.setPartyId(accountBaseInfo.getPartyPrimaryId());
        CompradorDetailResp result = compradorRequirementFacade.requirementDetail(req, false);
        return ResponseModel.succ(result);
    }

    private void checkParamLegal(CompradorRequirementReq.CompradorRequirementAdd req) {
        if (null != req.getStartArea() && null != req.getEndArea()) {
            if (req.getEndArea().compareTo(req.getStartArea()) < 0) {
                throw new BusinessException("请正确填写面积数据");
            }
        }
        if (null != req.getStartPrice() && null != req.getEndPrice()) {
            if (req.getEndPrice().compareTo(req.getStartPrice()) < 0) {
                throw new BusinessException("请正确填写价格区间");
            }
        }
    }

    private void checkParamLegal(CompradorRequirementReq.RequirementUpdate req) {
        if (null != req.getStartArea() && null != req.getEndArea()) {
            if (req.getEndArea().compareTo(req.getStartArea()) < 0) {
                throw new BusinessException("请正确填写面积数据");
            }
        }
        if (null != req.getStartPrice() && null != req.getEndPrice()) {
            if (req.getEndPrice().compareTo(req.getStartPrice()) < 0) {
                throw new BusinessException("请正确填写价格区间");
            }
        }
    }
}
