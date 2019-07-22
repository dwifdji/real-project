package com._360pai.applet.controller.numberJump;

import com._360pai.applet.controller.NumberJumpAbstractController;
import com._360pai.applet.controller.account.resp.AccountBaseInfo;
import com._360pai.applet.controller.account.vo.ProfileInfo;
import com._360pai.applet.shiro.NumberJumpShiroAuthService;
import com._360pai.arch.common.ResponseModel;
import com._360pai.arch.common.enums.ApiCallResult;
import com._360pai.arch.common.utils.JsonUtil;
import com._360pai.core.facade.applet.CalculatorFacade;
import com._360pai.core.facade.applet.req.CalculatorReq;
import com._360pai.core.facade.applet.resp.CalculatorResp;
import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.util.Assert;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * @author xdrodger
 * @Title: NumberJumpController
 * @ProjectName dev2-zeus
 * @Description:
 * @date 2019-04-28 09:13
 */
@RestController
@Slf4j
public class CalculatorController extends NumberJumpAbstractController {

    @Autowired
    private NumberJumpShiroAuthService shiroAuthService;
    @Reference(version = "1.0.0")
    private CalculatorFacade numberJumpFacade;

    /**
     * 登录
     */
    @PostMapping(value = "/open/numberJump/login")
    public ResponseModel login(@RequestBody CalculatorReq.LoginReq req, HttpServletResponse response) {
        if (StringUtils.isEmpty(req.getCode())) {
            return ResponseModel.fail(ApiCallResult.EMPTY);
        }
        CalculatorResp.LoginResp resp = numberJumpFacade.login(req);
        //保存 ticket
        String ticket = shiroAuthService.saveTicket(getCurRequest(),response, resp.getLoginId(), null);
        Map<String, Object> data = new HashMap<>();
        data.put("_calculator_id", resp.getLoginId());
        data.put("_calculator_ticket", ticket);
        return ResponseModel.succ(data);
    }

    /**
     * 获取账户信息
     */
    @GetMapping(value = "/confined/numberJump/profile")
    public ResponseModel profile() {
        AccountBaseInfo accountInfo = loadCurLoginAccountInfo();
        ProfileInfo profileInfo = new ProfileInfo();
        BeanUtils.copyProperties(accountInfo, profileInfo);
        return ResponseModel.succ(profileInfo);
    }
    /**
     * 获取账户信息
     */
    @GetMapping(value = "/confined/numberJump/getUnreadBroadcastCount")
    public ResponseModel getUnreadBroadcastCount() {
        AccountBaseInfo accountInfo = loadCurLoginAccountInfo();
        int count = numberJumpFacade.getUnreadBroadcastCount(accountInfo.getExtBindId());
        Map<String,Object> map = Maps.newHashMap();
        map.put("unreadBroadcastCount",count);
        return ResponseModel.succ(map);
    }
    /**
     * 债权计算器接口
     */
    @PostMapping(value = "/confined/numberJump/debt/calculator")
    public ResponseModel debtCalculator(@RequestBody CalculatorReq.DebtCalculatorReq req) {
        req.setExtBindId(loadCurLoginId());
        return numberJumpFacade.debtCalculator(req);
    }

    /**
     * 本息计算器接口
     */
    @PostMapping(value = "/confined/numberJump/principal/interest/calculator")
    public ResponseModel principalInterestCalculator(@RequestBody CalculatorReq.PrincipalInterestCalculatorReq req) {
        req.setExtBindId(loadCurLoginId());
        return numberJumpFacade.principalInterestCalculator(req);
    }

    /**
     * 计算器实时播报支付接口
     */
    @PostMapping(value = "/confined/numberJump/calculator/broadcast/pay")
    public ResponseModel calculatorBroadcastPay(@RequestBody CalculatorReq.CalculatorBroadcastPayReq req) {
        Assert.notNull(req.getCalculatorId(), "calculatorId 不能为空");
        Assert.notNull(req.getType(), "type 不能为空");
        return numberJumpFacade.calculatorBroadcastPay(req);
    }

    /**
     * 计算器实时播报支付回调接口
     */
    @PostMapping(value = "/confined/numberJump/calculator/broadcast/pay/callBack")
    public ResponseModel calculatorBroadcastPayCallBack(@RequestBody CalculatorReq.CalculatorBroadcastPayCallBackReq req) {
        Assert.notNull(req.getOrderId(), "orderId 不能为空");
        return numberJumpFacade.calculatorBroadcastPayCallBack(req);
    }

    /**
     * 计算器历史记录接口（分页）
     */
    @GetMapping(value = "/confined/numberJump/calculator/history")
    public ResponseModel getCalculatorHistory(CalculatorReq.QueryReq req) {
        req.setExtBindId(loadCurLoginId());
        return ResponseModel.succ(numberJumpFacade.getCalculatorHistory(req));
    }

    /**
     * 计算器播报列表接口（分页）
     */
    @GetMapping(value = "/confined/numberJump/calculator/broadcast/list")
    public ResponseModel getCalculatorBroadcastList(CalculatorReq.QueryReq req) {
        req.setExtBindId(loadCurLoginId());
        return ResponseModel.succ(numberJumpFacade.getCalculatorBroadcastList(req));
    }

    /**
     * 债权计算器播报详情接口（分页）
     */
    @GetMapping(value = "/confined/numberJump/dept/calculator/broadcast")
    public ResponseModel getDeptCalculatorBroadcast(CalculatorReq.QueryReq req) {
        req.setExtBindId(loadCurLoginId());
        return numberJumpFacade.getDeptCalculatorBroadcast(req);
    }

    /**
     * 本息计算器播报详情接口（分页）
     */
    @GetMapping(value = "/confined/numberJump/principal/interest/calculator/broadcast")
    public ResponseModel getPrincipalInterestCalculatorBroadcast(CalculatorReq.QueryReq req) {
        return numberJumpFacade.getPrincipalInterestCalculatorBroadcast(req);
    }

    /**
     * 债权计算器详情接口
     */
    @GetMapping(value = "/confined/numberJump/debt/calculator/detail")
    public ResponseModel getDebtCalculatorDetail(CalculatorReq.QueryReq req) {
        Assert.notNull(req.getId(), "id 不能为空");
        req.setExtBindId(loadCurLoginId());
        return numberJumpFacade.getDebtCalculatorDetail(req);
    }

    /**
     * 本息计算器详情接口
     */
    @GetMapping(value = "/confined/numberJump/principal/interest/calculator/detail")
    public ResponseModel getPrincipalInterestCalculatorDetail(CalculatorReq.QueryReq req) {
        Assert.notNull(req.getId(), "id 不能为空");
        req.setExtBindId(loadCurLoginId());
        return numberJumpFacade.getPrincipalInterestCalculatorDetail(req);
    }
}

