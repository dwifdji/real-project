package com._360pai.admin.controller.asset;

import com._360pai.admin.controller.AbstractController;
import com._360pai.admin.controller.account.resp.AccountBaseInfo;
import com._360pai.arch.common.ResponseModel;
import com._360pai.arch.common.enums.ApiCallResult;
import com._360pai.arch.common.enums.SideType;
import com._360pai.core.common.constants.AssetEnum;
import com._360pai.core.facade.account.StaffFacade;
import com._360pai.core.facade.asset.AssetFacade;
import com._360pai.core.facade.asset.req.AssetReq;
import com.alibaba.dubbo.config.annotation.Reference;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.util.Assert;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * 描述:
 *
 * @author : whisky_vip
 * @date : 2018/8/16 10:32
 */
@RestController
public class AssetController extends AbstractController {

    @Reference(version = "1.0.0")
    AssetFacade assetFacade;
    @Reference(version = "1.0.0")
    StaffFacade staffFacade;

    /**
     * 获取标的列表
     */
    @RequiresPermissions("pmgl_pmbd:list")
    @GetMapping(value = "/admin/asset/list")
    public ResponseModel list(AssetReq.QueryReq assetReq) {
        return ResponseModel.succ(assetFacade.getAllAssetByPage(assetReq));
    }

    /**
     * 获取标的详情
     */
    @GetMapping(value = "/admin/asset/detail")
    public ResponseModel detail(AssetReq.AssetIdReq req) {
        Assert.notNull(req.getAssetId(), "assetId 参数不能为空");
        return ResponseModel.succ(assetFacade.getAsset(req.getAssetId()));
    }

    /**
     * 更新标的详情
     */
    @PostMapping(value = "/admin/asset/update")
    public ResponseModel update(@RequestBody AssetReq.UpdateReq req) {
        Assert.notNull(req.getId(), "id 参数不能为空");
        return ResponseModel.succ(assetFacade.update(req));
    }

    /**
     * 功能描述: 拍品修改
     *
     * @param:
     * @return:
     * @auther: zxiao
     * @date: 2018/9/21 13:02
     */
    @PostMapping(value = "/admin/asset/edit")
    public ResponseModel assetEdit(@RequestBody AssetReq.AddReq req) {
        req.setSideType(SideType.ADMIN);
        req.setOperatorId(loadCurLoginId());
        ResponseModel pageInfo = assetFacade.assetEdit(req);
        if (pageInfo instanceof ResponseModel) {
            return pageInfo;
        }
        staffFacade.saveAuctionActivityOperationRecord(loadCurLoginId(), "修改拍品信息", (Integer) pageInfo.getContent());
        return pageInfo;
    }


    /**
     * 功能描述: 拍品详情
     *
     * @param:
     * @return:
     * @auther: zxiao
     * @date: 2018/9/21 13:02
     */
    @PostMapping(value = "/admin/my/asset/detail")
    public ResponseModel AssetDetail(@RequestBody AssetReq.AddReq req) {
        Assert.notNull(req.getAssetId(), "拍品编号不存在");
        req.setSideType(SideType.ADMIN);
        Object pageInfo = assetFacade.assetDetail(req);
        return ResponseModel.succ(pageInfo);
    }

    /**
     * 功能描述: 商品详情页拍品详情
     *
     * @param:
     * @return:
     * @auther: zxiao
     * @date: 2018/9/21 13:02
     */
    @PostMapping(value = "/admin/my/asset/see")
    public ResponseModel seeAssetDetail(@RequestBody AssetReq.AddReq req) {
        Assert.notNull(req.getAssetId(), "拍品编号不存在");
        req.setSideType(SideType.ADMIN);
        Object pageInfo = assetFacade.MyDetail(req);
        return ResponseModel.succ(pageInfo);
    }

    /**
     *
     * 查看租赁权的详情
     * @param:
     * @return:
     * @auther: liuhaolei
     * @date: 2018/9/21 13:02
     */
    @GetMapping(value = "/admin/my/asset/getLeaseDetailById")
        public ResponseModel getLeaseDetailById(AssetReq.LeaseDataReq req) {

        if(req.getAssetId() == null) {
            return ResponseModel.fail(ApiCallResult.EMPTY.getDesc());
        }
        req.setWebFlag(AssetEnum.WebFlag.ADMIN.getKey());
        return assetFacade.getLeaseAssetById(req);
    }


    /**
     * 新增租赁权拍卖信息数据
     * @return
     */
    @PostMapping(value = "/admin/my/asset/saveOrUpdateLeaseAsset")
    public ResponseModel updateLeaseAsset(@RequestBody AssetReq.LeaseDataReq req) {
         if(req.getAssetId() == null) {
            return ResponseModel.fail(ApiCallResult.EMPTY.getDesc());
         }

         return assetFacade.saveOrUpdateLeaseAsset(req);
    }

}
