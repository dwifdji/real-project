package com.winback.web.controller.home;

import com.alibaba.dubbo.config.annotation.Reference;
import com.winback.arch.common.ResponseModel;
import com.winback.core.commons.constants.OperateEnum;
import com.winback.core.facade._case.CaseFacade;
import com.winback.core.facade.account.vo.Lawyer;
import com.winback.core.facade.operate.OperationFacade;
import com.winback.core.facade.operate.req.OperationReq;
import com.winback.web.controller.AbstractController;
import com.winback.web.vo.LoginInfo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * create by liuhaolei on 2019-01-24
 */
@RestController
    public class HomePageController extends AbstractController {

    @Reference(version = "1.0.0")
    private OperationFacade operationFacade;
    @Reference(version = "1.0.0")
    private CaseFacade caseFacade;

    /**
     * 查询所有banner类别列表
     * @param req
     * @return
     */
    @GetMapping("/open/homePage/getBannerList")
    public ResponseModel getBannerList(OperationReq.AppBannerListReq req){


        return operationFacade.getBannerList(req);
    }


    /**
     * 快速发布
     * @param
     * @return
     */
    @GetMapping("/open/homePage/getQuickRelease")
    public ResponseModel getQuickRelease(OperationReq.OperateIconListReq req){
        req.setGroupType(OperateEnum.OperateIconEnum.QUICK_RELEASE.getType());
        req.setLimitSize(req.getLimitSize()==null?5:req.getLimitSize());
        return operationFacade.getQuickRelease(req);
    }

    /**
     * 导航栏
     * @param
     * @return
     */
    @GetMapping("/open/homePage/getNavigationBar")
    public ResponseModel getNavigationBar(OperationReq.OperateIconListReq req){
        req.setGroupType(OperateEnum.OperateIconEnum.NAVIGATION_BAR.getType());
        req.setLimitSize(req.getLimitSize()==null?5:req.getLimitSize());
        return operationFacade.getNavigationBar(req);
    }

    /**
     *  优质案件
     * @param
     * @return
     */
    @GetMapping("/open/homePage/getQualityCase")
    public ResponseModel getQualityCase(OperationReq.OperateIconListReq req){
        req.setGroupType(OperateEnum.OperateIconEnum.QUALITY_CASE.getType());
        req.setLimitSize(req.getLimitSize()==null?8:req.getLimitSize());
        return operationFacade.getQualityCase(req);
    }

    /**
     *  合同范本
     * @param
     * @return
     */
    @GetMapping("/open/homePage/getContractModel")
    public ResponseModel getContractModel(OperationReq.OperateIconListReq req){
        req.setGroupType(OperateEnum.OperateIconEnum.CONTRACT_MODEL.getType());
        req.setLimitSize(req.getLimitSize()==null?3:req.getLimitSize());
        return operationFacade.getContractModel(req);
    }

    /**
     * 查询不需要统计的icon配置项（快速发布, 导航栏配置）
     * @param req
     * @return
     */
    @GetMapping("/open/homePage/getHomeIconList")
    public ResponseModel getHomeIconList(OperationReq.OperateIconListReq req){


        return operationFacade.getOperateIconList(req);
    }

    /**
     * 查询需要统计的icon配置项（优质案件", 合同范本）
     * @param
     * @return
     */
    @GetMapping("/open/homePage/getHomeIconCategory")
    public ResponseModel getHomeIconCategory(OperationReq.OperateIconListReq req){

        return operationFacade.getHomeIconCategory(req);
    }


    /**
     * 查询最新上线的20个案件
     * @return
     */
    @GetMapping("/open/homePage/getRecommendCaseList")
    public ResponseModel getRecommendedCaseList(OperationReq.CaseSizeReq caseSizeReq){
        LoginInfo loginInfo = loadCurLoginInfo();
        caseSizeReq.setLawyerFlag(loginInfo.getLawyer() == null? "0":"1");

        return caseFacade.getRecommendedCaseList(caseSizeReq);
    }


    /**
     * APP查询平台公告列表
     * @param
     * @return
     */
    @GetMapping("/open/homePage/getHomeAnnouncementList")
    public ResponseModel getHomeAnnouncementList(OperationReq.HomeAnnouncementReq req) {

        return operationFacade.getHomeAnnouncementList(req);
    }

}
