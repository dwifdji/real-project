package com._360pai.web.controller.activity;

import com._360pai.arch.common.ResponseModel;
import com._360pai.core.facade.assistant.NotifyPartyActivityFacade;
import com._360pai.core.facade.assistant.req.NotifyPartyActivityReq;
import com._360pai.gateway.common.alisms.AliSmsTemplateEnums;
import com._360pai.gateway.controller.req.alisms.FAliSmsSendReq;
import com._360pai.gateway.facade.AliSmsFacade;
import com._360pai.web.controller.AbstractController;
import com._360pai.web.controller.account.resp.AccountBaseInfo;
import com.alibaba.dubbo.config.annotation.Reference;
import com.github.pagehelper.PageInfo;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zxiao
 * @Title: ActivityNotifyController
 * @ProjectName zeus-parent
 * @Description:
 * @date 2018/8/28 11:04
 */
@RestController
public class ActivityNotifyController extends AbstractController {

    @Reference(version = "1.0.0")
    private NotifyPartyActivityFacade notifyPartyActivityFacade;
    @Reference(version = "1.0.0")
    private AliSmsFacade aliSmsFacade;

    /**
     * 功能描述:  提醒
     *
     * @param:
     * @return:
     * @auther: zxiao
     * @date: 2018/8/28 13:52
     */
    @PostMapping("/confined/activity/notify_me")
    public ResponseModel notifyMe(@RequestBody NotifyPartyActivityReq.notifyReq req) {
        ResponseModel model = new ResponseModel().warpSuccess().setTimestamp(System.currentTimeMillis());
        AccountBaseInfo accountBaseInfo = loadCurLoginAccountInfo();
        assert accountBaseInfo != null;
//        isAuth();
        req.setAccountId(accountBaseInfo.getAccountId());
        req.setPartyId(accountBaseInfo.getPartyPrimaryId());
        notifyPartyActivityFacade.notifyMe(req);
        return model;
    }

    /**
     * 功能描述:  我的提醒 列表
     *
     * @param:
     * @return:
     * @auther: zxiao
     * @date: 2018/8/28 13:52
     */
    @GetMapping("/confined/activity/my_notify")
    public ResponseModel myNotify(NotifyPartyActivityReq.notifyReq req) {
        ResponseModel model = new ResponseModel().warpSuccess().setTimestamp(System.currentTimeMillis());
        AccountBaseInfo accountBaseInfo = loadCurLoginAccountInfo();
        Integer partyPrimaryId = accountBaseInfo.getPartyPrimaryId();
        req.setPartyId(partyPrimaryId);
        req.setAccountId(accountBaseInfo.getAccountId());
        PageInfo pageInfo = notifyPartyActivityFacade.myNotify(req);
        model.setContent(pageInfo);
        return model;
    }

    /**
     * 功能描述: 取消提醒   批量操作
     *
     * @param:
     * @return:
     * @auther: zxiao
     * @date: 2018/9/12 13:03
     */
    @PostMapping("/confined/activity/notify_me/cancel")
    public ResponseModel cancelNotifyMe(@RequestBody NotifyPartyActivityReq.notifyReq req) {
        Assert.notNull(req.getIds(), "id不能为空");
        ResponseModel model = new ResponseModel().warpSuccess().setTimestamp(System.currentTimeMillis());
        int i = notifyPartyActivityFacade.cancelNotifyMe(req);
        if (i <= 0) {
            return ResponseModel.fail();
        }
        return model;
    }

    /**
     * 功能描述: 取消提醒
     *
     * @param:
     * @return:
     * @auther: zxiao
     * @date: 2018/9/12 13:03
     */
    @PostMapping("/confined/activity/notify/cancel")
    public ResponseModel cancelNotify(@RequestBody NotifyPartyActivityReq.notifyReq req) {
        Assert.notNull(req.getActivityId(), "活动ID不能为空");
        AccountBaseInfo accountBaseInfo = loadCurLoginAccountInfo();
        Integer partyPrimaryId = accountBaseInfo.getPartyPrimaryId();
        ResponseModel model = new ResponseModel().warpSuccess().setTimestamp(System.currentTimeMillis());
        req.setAccountId(accountBaseInfo.getAccountId());
        int i = notifyPartyActivityFacade.cancel(req, partyPrimaryId);
        if (i <= 0) {
            return ResponseModel.fail();
        }
        return model;
    }

}
