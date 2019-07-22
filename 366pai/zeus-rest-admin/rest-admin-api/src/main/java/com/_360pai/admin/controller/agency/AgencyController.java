package com._360pai.admin.controller.agency;

import com._360pai.admin.controller.AbstractController;
import com._360pai.arch.common.ResponseModel;
import com._360pai.core.facade.account.AccountFacade;
import com._360pai.core.facade.account.req.AccountReq;
import com._360pai.core.facade.account.req.AgencyApplyReq;
import com._360pai.core.facade.account.req.AgencyReq;
import com.alibaba.dubbo.config.annotation.Reference;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AgencyController extends AbstractController {

    public static final Logger LOGGER = LoggerFactory.getLogger(AgencyController.class);

    @Reference(version = "1.0.0")
    private AccountFacade accountFacade;

    /**
     * 机构列表接口
     */
    @RequiresPermissions(value = {"yhgl_jg:list", "pmgl_pmhd:list"}, logical = Logical.OR)
    @GetMapping(value = "/admin/agency/list")
    public ResponseModel agencyList(AgencyReq.QueryReq req) {
        return ResponseModel.succ(accountFacade.getAgencyListByPage(req));
    }

    /**
     * 机构详情接口
     */
    @GetMapping(value = "/admin/agency/detail")
    public ResponseModel agencyDetail(AgencyReq.BaseReq req) {
        Assert.notNull(req.getAgencyId(), "agencyId 参数不能为空");
        return ResponseModel.succ(accountFacade.getAgencyById(req));
    }

    /**
     * 机构申请列表接口
     */
    @RequiresPermissions("yhgl_jg:apply_list")
    @GetMapping(value = "/admin/agency/apply/list")
    public ResponseModel applyList(AgencyApplyReq.QueryReq req) {
        return ResponseModel.succ(accountFacade.getAgencyApplyListByPage(req));
    }

    /**
     * 机构申请详情接口
     */
    @GetMapping(value = "/admin/agency/apply/detail")
    public ResponseModel applyDetail(AgencyApplyReq.BaseReq req) {
        Assert.notNull(req.getId(), "agencyId 参数不能为空");
        return ResponseModel.succ(accountFacade.getAgencyApplyRecordById(req));
    }

    /**
     * 更新机构申请详情接口
     *
     */
    @PostMapping(value = "/admin/agency/apply/update")
    public ResponseModel applyUpdate(@RequestBody AgencyApplyReq.UpdateReq req) {
        Assert.notNull(req.getId(), "id 参数不能为空");
        return ResponseModel.succ(accountFacade.agencyApplyUpdate(req));
    }

    /**
     * 机构申请审核通过接口
     *
     */
    @PostMapping(value = "/admin/agency/apply/approve")
    public ResponseModel applyApprove(@RequestBody AgencyApplyReq.BaseReq req) {
        Assert.notNull(req.getId(), "id 参数不能为空");
        req.setOperatorId(loadCurLoginId());
        return ResponseModel.succ(accountFacade.agencyApplyApprove(req));
    }

    /**
     * 机构申请审核拒绝接口
     *
     */
    @PostMapping(value = "/admin/agency/apply/reject")
    public ResponseModel applyReject(@RequestBody AgencyApplyReq.BaseReq req) {
        Assert.notNull(req.getId(), "id 参数不能为空");
        if (StringUtils.isEmpty(req.getRemark())) {
            return ResponseModel.fail("备注不能为空");
        }
        req.setOperatorId(loadCurLoginId());
        return ResponseModel.succ(accountFacade.agencyApplyReject(req));
    }

    /**
     * 更新机构信息接口
     *
     */
    @PostMapping(value = "/admin/agency/update")
    public ResponseModel agencyUpdate(@RequestBody AgencyReq.UpdateReq req) {
        Assert.notNull(req.getId(), "id 参数不能为空");
        return ResponseModel.succ(accountFacade.updateAgency(req));
    }

    /**
     * 机构账户列表接口
     */
    @GetMapping(value = "/admin/agency/account/list")
    public ResponseModel agencyAccountList(AccountReq.QueryReq req) {
        Assert.notNull(req.getAgencyId(), "agencyId 参数不能为空");
        return ResponseModel.succ(accountFacade.getAccountListByPage(req));
    }

    /**
     * 设置机构代理商接口
     *
     */
    @PostMapping(value = "/admin/agency/set/channel/agent")
    public ResponseModel setChannelAgent(@RequestBody AgencyReq.BaseReq req) {
        Assert.notNull(req.getAgencyId(), "agencyId 参数不能为空");
        Assert.notNull(req.getIsChannelAgent(), "isChannelAgent 参数不能为空");
        return ResponseModel.succ(accountFacade.agencySetChannelAgent(req));
    }

    /**
     * 选择机构代理商接口
     *
     */
    @PostMapping(value = "/admin/agency/select/channel/agent")
    public ResponseModel selectChannelAgent(@RequestBody AgencyReq.BaseReq req) {
        Assert.notNull(req.getAgencyId(), "agencyId 参数不能为空");
        Assert.notNull(req.getChannelAgentAgencyId(), "channelAgentAgencyId 参数不能为空");
        return ResponseModel.succ(accountFacade.agencySelectChannelAgent(req));
    }

    /**
     * 取消选择机构代理商接口
     *
     */
    @PostMapping(value = "/admin/agency/cancel/select/channel/agent")
    public ResponseModel cancelSelectChannelAgent(@RequestBody AgencyReq.BaseReq req) {
        Assert.notNull(req.getAgencyId(), "agencyId 参数不能为空");
        return ResponseModel.succ(accountFacade.agencyCancelSelectChannelAgent(req));
    }

    /**
     * 机构代理商列表接口
     */
    @GetMapping(value = "/admin/agency/channel/agent/list")
    public ResponseModel channelAgentList(AgencyReq.QueryReq req) {
        req.setIsChannelAgent(true);
        return ResponseModel.succ(accountFacade.getAgencyListByPage(req));
    }

    /**
     * 开通机构站点接口
     *
     */
    @PostMapping(value = "/admin/agency/portal/online")
    public ResponseModel agencyPortalOnline(@RequestBody AgencyReq.BaseReq req) {
        Assert.notNull(req.getAgencyId(), "agencyId 参数不能为空");
        return ResponseModel.succ(accountFacade.agencyPortalOnline(req));
    }

    /**
     * 关闭机构站点接口
     *
     */
    @PostMapping(value = "/admin/agency/portal/offline")
    public ResponseModel agencyPortalOffline(@RequestBody AgencyReq.BaseReq req) {
        Assert.notNull(req.getAgencyId(), "agencyId 参数不能为空");
        return ResponseModel.succ(accountFacade.agencyPortalOffline(req));
    }
}
