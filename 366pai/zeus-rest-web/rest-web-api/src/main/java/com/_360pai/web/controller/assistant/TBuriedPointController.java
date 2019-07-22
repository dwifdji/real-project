package com._360pai.web.controller.assistant;

import com._360pai.arch.common.ResponseModel;
import com._360pai.arch.common.utils.DateUtil;
import com._360pai.arch.common.utils.ToolUtil;
import com._360pai.arch.web.utils.IpUtils;
import com._360pai.core.facade.assistant.TBuriedPointFacade;
import com._360pai.core.facade.assistant.req.BuriedPointReq;
import com._360pai.core.facade.assistant.req.FrontErrorReq;
import com._360pai.web.controller.AbstractController;
import com._360pai.web.controller.account.resp.AccountBaseInfo;
import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 描述 埋点
 *
 * @author : whisky_vip
 * @date : 2018/10/8 12:52
 */
@RestController
public class TBuriedPointController extends AbstractController {

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
            req.setUserId(accountBaseInfo.getAccountId() == null ? null : accountBaseInfo.getAccountId().toString());
            req.setUserType(accountBaseInfo.getType());
            req.setCreateTime(DateUtil.format(new Date(), DateUtil.NORM_DATETIME_PATTERN));
            req.setDeviceType("web");

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
     * 错误信息上传接口
     */
    @PostMapping(value = "/open/assistant/error/upload")
    public ResponseModel errorUpload(@RequestBody FrontErrorReq req, HttpServletRequest request) {
        int id ;
        try {
            AccountBaseInfo accountBaseInfo = loadCurLoginAccountInfo();
            Integer loginId = accountBaseInfo.getAccountId() == null ? -1 : accountBaseInfo.getAccountId();
            req.setLoginId(loginId);
            Date now = new Date();
            req.setCreateTime(now);
            req.setUpdateTime(now);

            String clientIp = ToolUtil.getClientIp(request);
            JSONObject map = ipUtils.getAddressByIPTencent(clientIp);
            Map<String, String> browserMap = ToolUtil.getOsAndBrowserInfo(request);
            req.setDeviceMark(browserMap.get("os") + "-" + browserMap.get("browser"));
            req.setCity("" + map.get("city"));
            req.setProvince("" + map.get("province"));
            req.setIp(clientIp);
            req.setApplication("1");
            id = tBuriedPointFacade.insertFrontError(req);
        } catch (Exception e) {
            return ResponseModel.succ();
        }
        Map<String, Object> data = new HashMap<>();
        data.put("id", id);
        return ResponseModel.succ(data);
    }
}
