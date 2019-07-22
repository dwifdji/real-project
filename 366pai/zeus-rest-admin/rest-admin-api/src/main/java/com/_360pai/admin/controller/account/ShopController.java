package com._360pai.admin.controller.account;

import com._360pai.admin.controller.AbstractController;
import com._360pai.arch.common.ResponseModel;
import com._360pai.core.common.constants.PartyEnum;
import com._360pai.core.facade.account.AccountFacade;
import com._360pai.core.facade.account.req.AcctReq;
import com._360pai.core.facade.payment.AuctionOrderFacade;
import com._360pai.core.facade.payment.req.AuctionOrderReq;
import com._360pai.core.facade.shop.ShopFacade;
import com._360pai.core.facade.shop.req.ShopReq;
import com.alibaba.dubbo.config.annotation.Reference;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author xdrodger
 * @Title: ShopController
 * @ProjectName zeus
 * @Description:
 * @date 2018/11/29 17:21
 */
@RestController
public class ShopController extends AbstractController {

    @Reference(version = "1.0.0")
    private ShopFacade shopFacade;
    @Reference(version = "1.0.0")
    private AccountFacade accountFacade;
    @Reference(version = "1.0.0")
    private AuctionOrderFacade auctionOrderFacade;

    /**
     * 未认证店铺信息修改申请列表接口
     */
    @GetMapping(value = "/admin/unauthorized/shop/update/apply/list")
    public ResponseModel unauthorizedShopUpdateApplyList(ShopReq.QueryReq req) {
        return ResponseModel.succ(shopFacade.getUpdateApplyRecordListByPage(req));
    }

    /**
     * 未认证店铺信息修改申请详情接口
     */
    @GetMapping(value = "/admin/unauthorized/shop/update/apply/detail")
    public ResponseModel unauthorizedShopUpdateApplyDetail(ShopReq.BaseReq req) {
        return ResponseModel.succ(shopFacade.getUpdateApplyRecord(req));
    }

    /**
     * 个人店铺信息修改申请列表接口
     */
    @GetMapping(value = "/admin/user/shop/update/apply/list")
    public ResponseModel userShopUpdateApplyList(ShopReq.QueryReq req) {
        req.setPartyType(PartyEnum.Type.user.name());
        return ResponseModel.succ(shopFacade.getUpdateApplyRecordListByPage(req));
    }

    /**
     * 个人店铺信息修改申请详情接口
     */
    @GetMapping(value = "/admin/user/shop/update/apply/detail")
    public ResponseModel userShopUpdateApplyDetail(ShopReq.BaseReq req) {
        req.setPartyType(PartyEnum.Type.user.name());
        return ResponseModel.succ(shopFacade.getUpdateApplyRecord(req));
    }

    /**
     * 企业店铺信息修改申请列表接口
     */
    @GetMapping(value = "/admin/company/shop/update/apply/list")
    public ResponseModel companyShopUpdateApplyList(ShopReq.QueryReq req) {
        req.setPartyType(PartyEnum.Type.company.name());
        return ResponseModel.succ(shopFacade.getUpdateApplyRecordListByPage(req));
    }

    /**
     * 企业店铺信息修改申请详情接口
     */
    @GetMapping(value = "/admin/company/shop/update/apply/detail")
    public ResponseModel companyShopUpdateApplyDetail(ShopReq.BaseReq req) {
        req.setPartyType(PartyEnum.Type.company.name());
        return ResponseModel.succ(shopFacade.getUpdateApplyRecord(req));
    }

    /**
     * 未认证店铺列表接口
     */
    @GetMapping(value = "/admin/unauthorized/shop/list")
    public ResponseModel unauthorizedShopList(ShopReq.QueryReq req) {
        return shopFacade.getShopListByPage(req);
    }

    /**
     * 未认证店铺详情接口
     */
    @GetMapping(value = "/admin/unauthorized/shop/detail")
    public ResponseModel unauthorizedShopDetail(ShopReq.BaseReq req) {
        return ResponseModel.succ(shopFacade.getShopDetail(req));
    }

    /**
     * 个人店铺列表接口
     */
    @GetMapping(value = "/admin/user/shop/list")
    public ResponseModel userShopList(ShopReq.QueryReq req) {
        req.setPartyType(PartyEnum.Type.user.name());
        return shopFacade.getShopListByPage(req);
    }

    /**
     * 个人店铺列表接口
     */
    @GetMapping(value = "/admin/user/shop/detail")
    public ResponseModel userShopDetail(ShopReq.BaseReq req) {
        req.setPartyType(PartyEnum.Type.user.name());
        return ResponseModel.succ(shopFacade.getShopDetail(req));
    }

    /**
     * 企业店铺列表接口
     */
    @GetMapping(value = "/admin/company/shop/list")
    public ResponseModel companyShopList(ShopReq.QueryReq req) {
        req.setPartyType(PartyEnum.Type.company.name());
        return shopFacade.getShopListByPage(req);
    }

    /**
     * 企业店铺详情接口
     */
    @GetMapping(value = "/admin/company/shop/detail")
    public ResponseModel companyShopDetail(ShopReq.BaseReq req) {
        req.setPartyType(PartyEnum.Type.company.name());
        return ResponseModel.succ(shopFacade.getShopDetail(req));
    }

    /**
     * 店铺信息修改申请通过接口
     */
    @PostMapping(value = "/admin/shop/update/apply/approve")
    public ResponseModel updateApplyApprove(@RequestBody ShopReq.BaseReq req) {
        Assert.notNull(req.getApplyId(), "applyId 参数不能为空");
        req.setOperatorId(loadCurLoginId());
        return shopFacade.updateApplyApprove(req);
    }

    /**
     * 店铺信息修改申请拒绝接口
     */
    @PostMapping(value = "/admin/shop/update/apply/reject")
    public ResponseModel updateApplyReject(@RequestBody ShopReq.BaseReq req) {
        Assert.notNull(req.getApplyId(), "applyId 参数不能为空");
        req.setOperatorId(loadCurLoginId());
        return shopFacade.updateApplyReject(req);
    }

    /**
     * 推荐分润列表接口
     */
    @RequiresPermissions("dpgl_frjl:invite_commission_list")
    @GetMapping(value = "/admin/shop/invite/commission/list")
    public ResponseModel inviteCommissionList(AcctReq.QueryReq req) {
        return ResponseModel.succ(accountFacade.getInviteCommissionListByPage(req));
    }

    /**
     * 推荐分润详情接口
     */
    @GetMapping(value = "/admin/shop/invite/commission/detail")
    public ResponseModel getInviteCommissionDetail(AcctReq.BaseReq req) {
        return ResponseModel.succ(accountFacade.getInviteCommission(req));
    }

    /**
     * 成交分润列表接口
     */
    @RequiresPermissions("dpgl_frjl:deal_commission_list")
    @GetMapping(value = "/admin/shop/deal/commission/list")
    public ResponseModel shopDealCommissionList(AuctionOrderReq.QueryReq req) {
        return ResponseModel.succ(auctionOrderFacade.getShopDealCommissionListByPage(req));
    }

    /**
     * 成交分润详情接口
     */
    @GetMapping(value = "/admin/shop/deal/commission/detail")
    public ResponseModel shopDealCommissionDetail(AuctionOrderReq.OrderIdReq req) {
        return ResponseModel.succ(auctionOrderFacade.getShopDealCommissionDetail(req));
    }

    /**
     * 邀请的店铺列表接口
     */
    @GetMapping(value = "/admin/invited/shop/list")
    public ResponseModel invitedShopList(ShopReq.QueryReq req) {
        return ResponseModel.succ(shopFacade.getInvitedShopListByPage(req));
    }
}
