package com.winback.admin.controller.operate;

import com.alibaba.dubbo.config.annotation.Reference;
import com.winback.arch.common.ResponseModel;
import com.winback.arch.common.enums.ApiCallResult;
import com.winback.core.facade._case.CaseFacade;
import com.winback.core.facade._case.req.CaseCommReq;
import com.winback.core.facade.operate.OperationFacade;
import com.winback.core.facade.operate.req.OperationReq;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * create by liuhaolei on 2019-01-22
 * 运营管理控制器
 */
@RestController
public class OperationController {

    @Reference(version = "1.0.0")
    private OperationFacade operationFacade;


    @Reference(version = "1.0.0")
    private CaseFacade caseFacade;

    /**
     * 新增bannel页配置数据
     * @return
     */
    @PostMapping("/confined/operate/saveOperateIcon")
    public ResponseModel saveOperateIcon(@RequestBody OperationReq.SaveOperateIconReq releaseAreaReq) {
        if(StringUtils.isBlank(releaseAreaReq.getName()) || StringUtils.isBlank(releaseAreaReq.getImgUrl())) {
            return ResponseModel.fail(ApiCallResult.EMPTY.getDesc());
        }

        if(StringUtils.isBlank(releaseAreaReq.getGroupType()) || StringUtils.isBlank(releaseAreaReq.getType())) {
            return ResponseModel.fail(ApiCallResult.EMPTY.getDesc());
        }

        return operationFacade.saveReleaseArea(releaseAreaReq);
    }


    /**
     * 修改bannel页配置数据
     * @return
     */
    @PostMapping("/confined/operate/updateOrDeleteOperateIcon")
    public ResponseModel updateOrDeleteOperateIcon(@RequestBody OperationReq.UpdateOperateIconReq releaseAreaReq){
        if(StringUtils.isBlank(releaseAreaReq.getId()) || StringUtils.isBlank(releaseAreaReq.getUpdateFlag())) {
            return ResponseModel.fail(ApiCallResult.EMPTY.getDesc());
        }
        if("0".equals(releaseAreaReq.getUpdateFlag())) {
            if(StringUtils.isBlank(releaseAreaReq.getName()) || StringUtils.isBlank(releaseAreaReq.getImgUrl())) {
                return ResponseModel.fail(ApiCallResult.EMPTY.getDesc());
            }

            if(StringUtils.isBlank(releaseAreaReq.getGroupType()) || StringUtils.isBlank(releaseAreaReq.getType())) {
                return ResponseModel.fail(ApiCallResult.EMPTY.getDesc());
            }
        }

        return operationFacade.updateOrDeleteReleaseArea(releaseAreaReq);
    }


    /**
     * 获取首页配置列表
     * @return
     */
    @RequiresPermissions(value = {"operation_mgt_2_1_1", "operation_mgt_2_2_1", "operation_mgt_2_3_1", "operation_mgt_2_4_1"}, logical = Logical.OR)
    @GetMapping("/confined/operate/getOperateIconList")
    public ResponseModel getOperateIconList(OperationReq.ListOperateIconReq releaseAreaReq){

        return operationFacade.getReleaseAreaList(releaseAreaReq);
    }

    /**
     * 获取首页配置项的详情
     * @return
     */
    @GetMapping("/confined/operate/getOperateIconById")
    public ResponseModel getOperateIconById(OperationReq.ListOperateIconReq releaseAreaReq){
        if(StringUtils.isBlank(releaseAreaReq.getId())) {
            return ResponseModel.fail(ApiCallResult.EMPTY.getDesc());
        }
        return operationFacade.getOperateIconById(releaseAreaReq);
    }

    /**
     * 保存广告位
     * @param req
     * @return
     */
    @PostMapping("/confined/operate/saveAdvertisingSpace")
    public ResponseModel saveAdvertisingSpace(@RequestBody OperationReq.SaveAdvertisingSpace req){
        if(StringUtils.isBlank(req.getName()) || StringUtils.isBlank(req.getImgUrl())||StringUtils.isBlank(req.getType())) {
            return ResponseModel.fail(ApiCallResult.EMPTY.getDesc());
        }

        return operationFacade.saveAdvertisingSpace(req);
    }

    /**
     * 修改或者删除广告位
     * @param req
     * @return
     */
    @PostMapping("/confined/operate/updateOrDeleteAS")
    public ResponseModel updateOrDeleteAS(@RequestBody OperationReq.UpdateAdvertisingSpace req){
        if(StringUtils.isBlank(req.getId()) || StringUtils.isBlank(req.getUpdateFlag())) {
            return ResponseModel.fail(ApiCallResult.EMPTY.getDesc());
        }

        if("0".equals(req.getUpdateFlag())) {
            if(StringUtils.isBlank(req.getName()) || StringUtils.isBlank(req.getImgUrl())||StringUtils.isBlank(req.getType())) {
                return ResponseModel.fail(ApiCallResult.EMPTY.getDesc());
            }

        }

        return operationFacade.updateOrDeleteAS(req);
    }

    /**
     * 查询广告位列表数据
     * @param
     * @return
     */
    @RequiresPermissions(value = {"operation_mgt_4_1_1", "operation_mgt_5_1_1"}, logical = Logical.OR)
    @GetMapping("/confined/operate/getAdvertisingSpaceList")
    public ResponseModel getAdvertisingSpaceList(OperationReq.ListAdvertisingSpaceReq req){

        return operationFacade.getAdvertisingSpaceList(req);
    }

    /**
     * 根据id查询广告位详情
     * @param
     * @return
     */
    @GetMapping("/confined/operate/getAdvertisingSpaceById")
    public ResponseModel getAdvertisingSpaceById(OperationReq.ListAdvertisingSpaceReq req){
        if(StringUtils.isBlank(req.getId())) {
            return ResponseModel.fail(ApiCallResult.EMPTY.getDesc());
        }

        return operationFacade.getAdvertisingSpaceById(req);
    }

    /**
     * 保存App公告
     * @param req
     * @return
     */
    @PostMapping("/confined/operate/saveOperateAnnouncement")
    public ResponseModel saveOperateAnnouncement(@RequestBody OperationReq.AnnouncementSaveReq req){
        if(StringUtils.isBlank(req.getName()) || req.getBeginTime() == null
                || req.getEndTime() == null) {
            return ResponseModel.fail(ApiCallResult.EMPTY.getDesc());
        }
        return operationFacade.saveOperateAnnouncement(req);
    }


    /**
     * 修改App公告
     * @param
     * @return
     */
    @PostMapping("/confined/operate/updateOrDeleteOA")
    public ResponseModel updateOrDeleteOA(@RequestBody OperationReq.AnnouncementUpdateReq req){
        if(StringUtils.isBlank(req.getUpdateFlag()) || StringUtils.isBlank(req.getId())) {
            return ResponseModel.fail(ApiCallResult.EMPTY.getDesc());
        }

        if("0".equals(req.getUpdateFlag())) {
            if(StringUtils.isBlank(req.getName()) || req.getBeginTime() == null
                    || req.getEndTime() == null) {
                return ResponseModel.fail(ApiCallResult.EMPTY.getDesc());
            }
        }

        return operationFacade.updateOrDeleteOA(req);
    }

    /**
     * 查询App列表
     * @param
     * @return
     */
    @RequiresPermissions("operation_mgt_3_1_1")
    @GetMapping("/confined/operate/getAnnouncementList")
    public ResponseModel getAnnouncementList(OperationReq.AnnouncementListReq req){

        return operationFacade.getAnnouncementList(req);
    }

    /**
     * 查询App列表
     * @param
     * @return
     */
    @GetMapping("/confined/operate/getAnnouncementById")
    public ResponseModel getAnnouncementById(OperationReq.AnnouncementListReq req){
        if(StringUtils.isBlank(req.getId())) {
            return ResponseModel.fail(ApiCallResult.EMPTY.getDesc());
        }

        return operationFacade.getAnnouncementById(req);
    }

    /**
     * 查询所有案由
     * @return
     */
    @RequiresPermissions(value = {"operation_mgt_2_2_1"}, logical = Logical.OR)
    @GetMapping("/open/operate/getCaseBriefList")
    public ResponseModel getCaseBriefList(){
        return operationFacade.getCaseBriefList();
    }

    /**
     * 查询所有合同类型
     * @return
     */
    @RequiresPermissions("operation_mgt_2_3_1")
    @GetMapping("/open/operate/getContractTypeList")
    public ResponseModel getContractTypeList(){
        return operationFacade.getContractTypeList();
    }

    /**
     * 查询所有案件类型
     * @return
     */
    @GetMapping("/open/operate/getAllCaseType")
    public ResponseModel getAllCaseType(){
        return operationFacade.getAllCaseType();
    }


    /**
     * 查询所有案件状态
     * @return
     */
    @PostMapping("/open/case/getCaseStatusList")
    public ResponseModel getCaseStatusList(CaseCommReq req){
        return caseFacade.getCaseStatusList();
    }

    /**
     * 根据不同类型查询所有案件步骤
     * @return
     */
    @GetMapping("/open/operate/getAllCaseStep")
    public ResponseModel getAllCaseStep(OperationReq.OpenType openType){
        return operationFacade.getAllCaseStep(openType);
    }
}
