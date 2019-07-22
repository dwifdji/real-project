package com._360pai.applet.controller.numberJump;

import com._360pai.applet.controller.NumberJumpAbstractController;
import com._360pai.applet.controller.account.resp.AccountBaseInfo;
import com._360pai.applet.controller.account.vo.ProfileInfo;
import com._360pai.applet.shiro.NumberJumpShiroAuthService;
import com._360pai.arch.common.ResponseModel;
import com._360pai.arch.common.enums.ApiCallResult;
import com._360pai.arch.common.utils.DateUtil;
import com._360pai.arch.common.utils.ToolUtil;
import com._360pai.arch.web.utils.IpUtils;
import com._360pai.core.facade.account.AccountFacade;
import com._360pai.core.facade.applet.CalculatorFacade;
import com._360pai.core.facade.applet.req.CalculatorReq;
import com._360pai.core.facade.applet.resp.CalculatorResp;
import com._360pai.core.facade.assistant.TBuriedPointFacade;
import com._360pai.core.facade.assistant.req.BuriedPointReq;
import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.util.Assert;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 描述
 *
 * @author : whisky_vip
 * @date : 2019/5/27 13:31
 */
@RestController()
@RequestMapping("/v2")
@Slf4j
public class CalculatorV2Controller extends NumberJumpAbstractController {

    @Autowired
    private NumberJumpShiroAuthService shiroAuthService;
    @Reference(version = "1.0.0")
    CalculatorFacade   numberJumpFacade;
    @Reference(version = "1.0.0")
    AccountFacade      accountFacade;
    @Reference(version = "1.0.0")
    TBuriedPointFacade tBuriedPointFacade;

    @Autowired
    private IpUtils ipUtils;
    @PostMapping(value = "/open/buriedPoint/insert")
    public ResponseModel buriedPointInsert(@RequestBody BuriedPointReq req, HttpServletRequest request) {
        // 埋点一直返回成功，不影响后面流程
        int id ;
        try {
            AccountBaseInfo accountBaseInfo = loadCurLoginAccountInfo();
            if (accountBaseInfo.getCurrentPartyId() != null) {
                req.setUserId(accountBaseInfo.getCurrentPartyId() + "");
                req.setUserType(accountBaseInfo.getType());
            } else if (accountBaseInfo.getAccountId() != null) {
                req.setUserId(accountBaseInfo.getAccountId() + "");
                req.setUserType(accountBaseInfo.getType());
            } else {
                req.setUserId(accountBaseInfo.getOpenId());
                req.setUserType("applet");
            }
            req.setCreateTime(DateUtil.format(new Date(), DateUtil.NORM_DATETIME_PATTERN));
            req.setDeviceType("applet");

            String     clientIp = ToolUtil.getClientIp(request);
            JSONObject map      = ipUtils.getAddressByIPTencent(clientIp);

            Map<String, String> browserMap = ToolUtil.getOsAndBrowserInfo(request);
            req.setDeviceMark(browserMap.get("os") + "-" + browserMap.get("browser"));

            req.setCity("" + map.get("city"));
            req.setProvince("" + map.get("province"));
            req.setIp(clientIp);
            id = tBuriedPointFacade.insert(req);
        } catch (Exception e) {
            return ResponseModel.succ();
        }
        return ResponseModel.succ(id);
    }
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
        String              ticket = shiroAuthService.saveTicket(getCurRequest(), response, resp.getLoginId(), null);
        Map<String, Object> data   = new HashMap<>();
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
        ProfileInfo     profileInfo = new ProfileInfo();
        BeanUtils.copyProperties(accountInfo, profileInfo);
        boolean subscribe = accountFacade.checkUserIsSubscribeMp360(accountInfo.getExtBindId());
        profileInfo.setSubscribe(subscribe);
        return ResponseModel.succ(profileInfo);
    }

    /**
     * 获取账户信息
     */
    @GetMapping(value = "/confined/numberJump/getUnreadBroadcastCount")
    public ResponseModel getUnreadBroadcastCount() {
        AccountBaseInfo     accountInfo = loadCurLoginAccountInfo();
        int                 count       = numberJumpFacade.getUnreadBroadcastCount(accountInfo.getExtBindId());
        Map<String, Object> map         = Maps.newHashMap();
        map.put("unreadBroadcastCount", count);
        return ResponseModel.succ(map);
    }

    /**
     * 债权计算器接口
     */
    @PostMapping(value = "/confined/numberJump/debt/calculator")
    public ResponseModel debtCalculatorV2(@RequestBody CalculatorReq.DebtCalculatorReq req) {
        req.setExtBindId(loadCurLoginId());
        return numberJumpFacade.debtCalculatorV2(req);
    }

    /**
     * 本息计算器接口
     */
    @PostMapping(value = "/confined/numberJump/principal/interest/calculator")
    public ResponseModel principalInterestCalculatorV2(@RequestBody CalculatorReq.PrincipalInterestCalculatorReq req) {
        req.setExtBindId(loadCurLoginId());
        return numberJumpFacade.principalInterestCalculatorV2(req);
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
    public ResponseModel getCalculatorHistoryV2(CalculatorReq.QueryHistoryReq req) {
        req.setExtBindId(loadCurLoginId());
        return ResponseModel.succ(numberJumpFacade.getCalculatorHistoryV2(req));
    }

    /**
     * 计算器播报列表接口（分页）
     */
    @GetMapping(value = "/confined/numberJump/calculator/broadcast/list")
    public ResponseModel getCalculatorBroadcastListV2(CalculatorReq.QueryBroadcastReq req) {
        req.setExtBindId(loadCurLoginId());
        return ResponseModel.succ(numberJumpFacade.getCalculatorBroadcastListV2(req));
    }

    /**
     * 债权计算器播报详情接口（分页）
     */
    @GetMapping(value = "/confined/numberJump/dept/calculator/broadcast")
    public ResponseModel getDeptCalculatorBroadcastV2(CalculatorReq.QueryReq req) {
        req.setExtBindId(loadCurLoginId());
        return numberJumpFacade.getDeptCalculatorBroadcastV2(req);
    }

    /**
     * 本息计算器播报详情接口（分页）
     */
    @GetMapping(value = "/confined/numberJump/principal/interest/calculator/broadcast")
    public ResponseModel getPrincipalInterestCalculatorBroadcastV2(CalculatorReq.QueryReq req) {
        return numberJumpFacade.getPrincipalInterestCalculatorBroadcastV2(req);
    }

    /**
     * 债权计算器详情接口
     */
    @GetMapping(value = "/confined/numberJump/debt/calculator/detail")
    public ResponseModel getDebtCalculatorDetailV2(CalculatorReq.QueryReq req) {
        Assert.notNull(req.getId(), "id 不能为空");
        req.setExtBindId(loadCurLoginId());
        return numberJumpFacade.getDebtCalculatorDetailV2(req);
    }

    /**
     * 本息计算器详情接口
     */
    @GetMapping(value = "/confined/numberJump/principal/interest/calculator/detail")
    public ResponseModel getPrincipalInterestCalculatorDetailV2(CalculatorReq.QueryReq req) {
        Assert.notNull(req.getId(), "id 不能为空");
        req.setExtBindId(loadCurLoginId());
        return numberJumpFacade.getPrincipalInterestCalculatorDetailV2(req);
    }


    /**
     * 债权计算器删除接口
     */
    @PostMapping(value = "/confined/numberJump/debt/calculator/del")
    public ResponseModel debtCalculatorDel(@RequestBody CalculatorReq.QueryReq req) {
        Assert.notNull(req.getId(), "id 不能为空");
        req.setExtBindId(loadCurLoginId());
        return numberJumpFacade.debtCalculatorDel(req);
    }

    /**
     * 本息计算器删除接口
     */
    @PostMapping(value = "/confined/numberJump/principal/interest/calculator/del")
    public ResponseModel principalInterestCalculatorDel(@RequestBody CalculatorReq.QueryReq req) {
        Assert.notNull(req.getId(), "id 不能为空");
        req.setExtBindId(loadCurLoginId());
        return numberJumpFacade.principalInterestCalculatorDel(req);
    }

    /**
     * 债权计算器结案接口
     */
    @PostMapping(value = "/confined/numberJump/debt/calculator/close")
    public ResponseModel debtCalculatorClose(@RequestBody CalculatorReq.QueryReq req) {
        Assert.notNull(req.getId(), "id 不能为空");
        req.setExtBindId(loadCurLoginId());
        return numberJumpFacade.debtCalculatorClose(req);
    }

    /**
     * 本息计算器结案接口
     */
    @PostMapping(value = "/confined/numberJump/principal/interest/calculator/close")
    public ResponseModel principalInterestCalculatorClose(@RequestBody CalculatorReq.QueryReq req) {
        Assert.notNull(req.getId(), "id 不能为空");
        req.setExtBindId(loadCurLoginId());
        return numberJumpFacade.principalInterestCalculatorClose(req);
    }

    /**
     * 本息计算器结案接口
     */
    @GetMapping(value = "/confined/numberJump/getRelativeList")
    public ResponseModel getRelativeList(CalculatorReq.QueryRelativeListReq req) {
        Assert.notNull(req.getProjectName(), "projectName 不能为空");
        req.setExtBindId(loadCurLoginId());
        return numberJumpFacade.getRelativeList(req);
    }

    /**
     * 本息计算器结案接口
     */
    @GetMapping(value = "/confined/numberJump/getRelativeBroadcastList")
    public ResponseModel getRelativeBroadcastList(CalculatorReq.QueryRelativeListReq req) {
        Assert.notNull(req.getProjectName(), "projectName 不能为空");
        req.setExtBindId(loadCurLoginId());
        return numberJumpFacade.getRelativeBroadcastList(req);
    }

    /**
     * 计算器查询地域以及项目经理接口
     */
    @GetMapping(value = "/confined/numberJump/getCalculatorQueryCondition")
    public ResponseModel getCalculatorQueryCondition(CalculatorReq.CalculatorQueryConditionReq req) {
        Assert.notNull(req.getType(), "type 不能为空");
        req.setExtBindId(loadCurLoginId());
        if (req.getBroadcastFlag() == null) {
            req.setBroadcastFlag(false);
        }
        return numberJumpFacade.getCalculatorQueryCondition(req);
    }

}
