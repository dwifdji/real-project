package com._360pai.core.facade.enrolling.req;

import com._360pai.arch.common.RequestModel;
import lombok.Data;

import java.io.Serializable;

/**
 * 描述：预招商请求参数
 * <p>
 * 作者： wuchuanqi
 * 版本： 1.0.0
 * 时间： 2018/8/20 11:13
 */
public class EnrollingReq {

    //预招商活动列表
    @Data
    public static class activitiesListReq extends RequestModel {

        private String status;//预招商活动专题

        private String info;//活动信息

        private String createdAtFrom;//送拍开始时间

        private String createdAtTo;//送拍结束时间

        private String agencyName;//送拍机构

        /**
         * 省id
         */
        private Integer provinceId;
        /**
         * 城市id
         */
        private Integer cityId;
        /**
         * 区县id
         */
        private Integer areaId;

        private String partyType;//委托人类型

        private String partyName;//委托人名称

        private String unionStatus;//是否是推广状态

        private String agencyId;//送拍机构id

        private String type;//1 抵押物预招商 2 债权预招商 3 物权预招商  4 全部

        private String  partyId;
        /**
         * '0 平台 1 第三方渠道淘宝';
         */
        private Integer thirdPartyStatus;


        private Integer noThirdPartyStatus;

        private String visibilityLevel;     //是否可见

        private String accountId;

        private Integer hallId; // 招商大厅id

        private Integer focusType;

        private String resourceId;

        private Integer excludeAppletHallActivity;
        private Integer excludeAssetPropertyActivity;
        private Integer excludeAlbumActivity;
        private Integer frontFlag;
    }

    //预招商id
    public static class activityIdReq extends RequestModel {

        private String activityId;

        private String type;

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getActivityId() {
            return activityId;
        }

        public void setActivityId(String activityId) {
            this.activityId = activityId;
        }
    }

    //编辑预招商详情请求
    public static class updateDetailReq implements Serializable {

        private String activityId;//预招商活动id

        private String detail;//详情信息

        public String getDetail() {
            return detail;
        }

        public void setDetail(String detail) {
            this.detail = detail;
        }

        public String getActivityId() {
            return activityId;
        }

        public void setActivityId(String activityId) {
            this.activityId = activityId;
        }
    }

    //设置预招商可见信息
    public static class updateVisibilityLevelReq implements Serializable {

        private String activityId;//预招商活动id

        private String visibilityLevel;//可见等级

        public String getVisibilityLevel() {
            return visibilityLevel;
        }

        public void setVisibilityLevel(String visibilityLevel) {
            this.visibilityLevel = visibilityLevel;
        }

        public String getActivityId() {
            return activityId;
        }

        public void setActivityId(String activityId) {
            this.activityId = activityId;
        }
    }


    //预招商id
    public static class activityIdTypeReq extends RequestModel {

        private String activityId;

        private String type; //预招商类型

        private String partyId;//登录人id

        private String accountId;//账号信息

        private String reqType;//请求的类型

        private String agencyCode;//连拍机构code

        private String mobile;//登录的手机号码

        private String seeStatus;//

        private Integer source;//来源

        private Integer foucsType;


        private Integer resourceId;
        /**
         * 区分web和小程序 0 web 1 applet
         */
        private Short pathType;

        public Short getPathType() {
            return pathType;
        }

        public void setPathType(Short pathType) {
            this.pathType = pathType;
        }

        public Integer getFoucsType() {
            return foucsType;
        }

        public void setFoucsType(Integer foucsType) {
            this.foucsType = foucsType;
        }

        public Integer getResourceId() {
            return resourceId;
        }

        public void setResourceId(Integer resourceId) {
            this.resourceId = resourceId;
        }

        public Integer getSource() {
            return source;
        }

        public void setSource(Integer source) {
            this.source = source;
        }

        public String getSeeStatus() {
            return seeStatus;
        }

        public void setSeeStatus(String seeStatus) {
            this.seeStatus = seeStatus;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getAgencyCode() {
            return agencyCode;
        }

        public void setAgencyCode(String agencyCode) {
            this.agencyCode = agencyCode;
        }

        public String getReqType() {
            return reqType;
        }

        public void setReqType(String reqType) {
            this.reqType = reqType;
        }

        public String getAccountId() {
            return accountId;
        }

        public void setAccountId(String accountId) {
            this.accountId = accountId;
        }

        public String getPartyId() {
            return partyId;
        }

        public void setPartyId(String partyId) {
            this.partyId = partyId;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getActivityId() {
            return activityId;
        }

        public void setActivityId(String activityId) {
            this.activityId = activityId;
        }
    }


    //预招商查询接口
    @Data
    public static class searchReq extends RequestModel {

        private String type;//预招商类型 1 抵押物预招商 2 债权预招商 3 物权预招商
        /**
         * 省id
         */
        private Integer provinceId;
        /**
         * 城市id
         */
        private Integer cityId;
        /**
         * 区县id
         */
        private Integer areaId;

        private String status;//抵押物状态 ONLINE 正在报名 CLOSED 报名结束 FINISHED 招商结束

        private String todayFlag;//今日上新字段查询

        private String query;//查询参数

        private String orderBy;

        private Integer source;

        private String category;

        private String mortgage;

        private String busTypeName;


        private String branchCom;//分公司名称
        /**
         * 委托方
         */
        private String partyId;
        /**
         * 起始价格
         */
        private String beginPrice;

        /**
         * 结束价格
         */
        private String endPrice;
        /**
         * 推广位id
         */
        private Integer activityPosterId;
    }


    //预招商tabid
    public static class tabReq extends RequestModel {

        private String tabId;

        public String getTabId() {
            return tabId;
        }

        public void setTabId(String tabId) {
            this.tabId = tabId;
        }
    }


    public static class activityAnnouncement extends RequestModel {

        private String activityId;        //活动id

        private String announcementId;    //模板id

        public String getActivityId() {
            return activityId;
        }

        public void setActivityId(String activityId) {
            this.activityId = activityId;
        }

        public String getAnnouncementId() {
            return announcementId;
        }

        public void setAnnouncementId(String announcementId) {
            this.announcementId = announcementId;
        }


    }

    //保存预招商进展
    public static class saveProgress extends RequestModel {

        private String activityId;        //活动id

        private String title;    //进展标题

        private String content;    //进展内容

        public String getActivityId() {
            return activityId;
        }

        public void setActivityId(String activityId) {
            this.activityId = activityId;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }
    }

    //保存抵押物结果
    public static class saveResult extends RequestModel {

        private String activityId;        //活动id

        private String buyer;    //买受人

        private String dealPrice;    //成交价格

        private String idNumber;    //证件号码


        public String getActivityId() {
            return activityId;
        }

        public void setActivityId(String activityId) {
            this.activityId = activityId;
        }

        public String getBuyer() {
            return buyer;
        }

        public void setBuyer(String buyer) {
            this.buyer = buyer;
        }

        public String getDealPrice() {
            return dealPrice;
        }

        public void setDealPrice(String dealPrice) {
            this.dealPrice = dealPrice;
        }

        public String getIdNumber() {
            return idNumber;
        }

        public void setIdNumber(String idNumber) {
            this.idNumber = idNumber;
        }
    }


    //预招商id
    public static class payCommission extends RequestModel {

        private String activityId;

        private String id;//订单的支付id

        private String partyId;//用户id

        private String memCode;//东方付通的账号code

        private String name;//名称

        private Integer source;//来源

        public Integer getSource() {
            return source;
        }

        public void setSource(Integer source) {
            this.source = source;
        }

        public String getMemCode() {
            return memCode;
        }

        public void setMemCode(String memCode) {
            this.memCode = memCode;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPartyId() {
            return partyId;
        }

        public void setPartyId(String partyId) {
            this.partyId = partyId;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getActivityId() {
            return activityId;
        }

        public void setActivityId(String activityId) {
            this.activityId = activityId;
        }
    }


    //预招商活动提交参数
    public static class saveActivityReq extends RequestModel {

        private String fields;//保存预招商的请求字段

        private Integer partyId;//用户id

        private String activityId;

        private String enrollingType;//预招商类型

        private Integer templateId;//模板id

        private String updateType; //1:後端修改

        private String operateId;//操作人id

        private Integer thirdPartyStatus;//标的发布来源


        public Integer getThirdPartyStatus() {
            return thirdPartyStatus;
        }

        public void setThirdPartyStatus(Integer thirdPartyStatus) {
            this.thirdPartyStatus = thirdPartyStatus;
        }

        public String getOperateId() {
            return operateId;
        }

        public void setOperateId(String operateId) {
            this.operateId = operateId;
        }

        public Integer getTemplateId() {
            return templateId;
        }

        public void setTemplateId(Integer templateId) {
            this.templateId = templateId;
        }


        public String getUpdateType() {
            return updateType;
        }

        public void setUpdateType(String updateType) {
            this.updateType = updateType;
        }

        public String getEnrollingType() {
            return enrollingType;
        }

        public void setEnrollingType(String enrollingType) {
            this.enrollingType = enrollingType;
        }

        public String getActivityId() {
            return activityId;
        }

        public void setActivityId(String activityId) {
            this.activityId = activityId;
        }

        public String getFields() {
            return fields;
        }

        public void setFields(String fields) {
            this.fields = fields;
        }

        public Integer getPartyId() {
            return partyId;
        }

        public void setPartyId(Integer partyId) {
            this.partyId = partyId;
        }
    }

    @Data
    public static class saveActivityThirdPartyReq extends RequestModel {
        //保存预招商的请求字段
        private String  fields;
        //用户id
        private Integer partyId;

        private String  activityId;
        //预招商类型
        private String  enrollingType;
        //模板id
        private Integer templateId;
        //1:後端修改
        private String  updateType;
        //操作人id
        private String  operatorId;

        private String options;

        private String thirdPartyStatus;
        private String thirdPartyTitle;
        private String thirdPartyUrl;
    }


    //预招商活动审核
    public static class auditEnrolling extends RequestModel {

        private String type;//预招商类型  1、抵押物预招商 2 债权预招商 3 物权预招商

        private String status;//审核状态 1 审核通过 2 审核拒绝

        private String activityId;//活动id

        private String reason;//审核拒绝原因

        private String operateId;//审核人id

        public String getOperateId() {
            return operateId;
        }

        public void setOperateId(String operateId) {
            this.operateId = operateId;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getActivityId() {
            return activityId;
        }

        public void setActivityId(String activityId) {
            this.activityId = activityId;
        }

        public String getReason() {
            return reason;
        }

        public void setReason(String reason) {
            this.reason = reason;
        }
    }

    //抵押物保证金操作
    public static class marginOperation extends RequestModel {
        private String depositId;//保证金id

        private String status;//1 没收保证金 2 释放保证金

        private String activityId;//活动id

        public String getDepositId() {
            return depositId;
        }

        public void setDepositId(String depositId) {
            this.depositId = depositId;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getActivityId() {
            return activityId;
        }

        public void setActivityId(String activityId) {
            this.activityId = activityId;
        }
    }

    //机构后台修改佣金以及用户名
    public static class agencyUpdateReq extends RequestModel {
        private String name;

        private String deposit;

        private String status;

        private String rejectReasion;

        private String id;


        public String getRejectReasion() {
            return rejectReasion;
        }

        public void setRejectReasion(String rejectReasion) {
            this.rejectReasion = rejectReasion;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getDeposit() {
            return deposit;
        }

        public void setDeposit(String deposit) {
            this.deposit = deposit;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

    }

    //抵押物保证金生成佣金信息
    public static class saveCommissionOrder extends RequestModel {

        private String amount;//订单金额

        private String activityId;//活动id

        private String applyId;//报名的订单id


        public String getApplyId() {
            return applyId;
        }

        public void setApplyId(String applyId) {
            this.applyId = applyId;
        }

        public String getAmount() {
            return amount;
        }

        public void setAmount(String amount) {
            this.amount = amount;
        }

        public String getActivityId() {
            return activityId;
        }

        public void setActivityId(String activityId) {
            this.activityId = activityId;
        }
    }

    public static class focusListReq extends RequestModel {


        private String[] focusList;


        public String[] getFocusList() {
            return focusList;
        }

        public void setFocusList(String[] focusList) {
            this.focusList = focusList;
        }

    }


    //签章回调
    public static class signCallBack extends RequestModel {


        private String activityId;//活动id

        private String contractId;//合同id

        private String partyId;//用户id

        private String status;//签章的转态

        public String getActivityId() {
            return activityId;
        }

        public void setActivityId(String activityId) {
            this.activityId = activityId;
        }

        public String getContractId() {
            return contractId;
        }

        public void setContractId(String contractId) {
            this.contractId = contractId;
        }

        public String getPartyId() {
            return partyId;
        }

        public void setPartyId(String partyId) {
            this.partyId = partyId;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }
    }


    //提交的订单
    public static class submitOrder extends RequestModel {


        private String activityId;//活动id

        private String orderNum;//支付的订单号

        private String payStatus;//支付的转态

        public String getPayStatus() {
            return payStatus;
        }

        public void setPayStatus(String payStatus) {
            this.payStatus = payStatus;
        }

        public String getActivityId() {
            return activityId;
        }

        public void setActivityId(String activityId) {
            this.activityId = activityId;
        }

        public String getOrderNum() {
            return orderNum;
        }

        public void setOrderNum(String orderNum) {
            this.orderNum = orderNum;
        }
    }


    //预招商数据回调
    public static class payCommissionCallBack extends RequestModel {


        private String busId;//订单的id

        private String orderNum;//支付的订单号

        private String payStatus;//支付的转态

        public String getBusId() {
            return busId;
        }

        public void setBusId(String busId) {
            this.busId = busId;
        }

        public String getOrderNum() {
            return orderNum;
        }

        public void setOrderNum(String orderNum) {
            this.orderNum = orderNum;
        }

        public String getPayStatus() {
            return payStatus;
        }

        public void setPayStatus(String payStatus) {
            this.payStatus = payStatus;
        }
    }


    //项目经理操作
    public static class projectManagerOperation extends RequestModel {


        private String activityId;//预招商活动信息

        private String staffId;//员工id

        private String type;//1 添加或者修改 2 取消

        public String getActivityId() {
            return activityId;
        }

        public void setActivityId(String activityId) {
            this.activityId = activityId;
        }

        public String getStaffId() {
            return staffId;
        }

        public void setStaffId(String staffId) {
            this.staffId = staffId;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }
    }


    //上传凭证
    public static class saveProofReq extends RequestModel {


        private String activityId;//预招商活动信息

        private String imgUrl;//参拍图片地址

        private String partyId;//登录用户

        public String getPartyId() {
            return partyId;
        }

        public void setPartyId(String partyId) {
            this.partyId = partyId;
        }

        public String getActivityId() {
            return activityId;
        }

        public void setActivityId(String activityId) {
            this.activityId = activityId;
        }

        public String getImgUrl() {
            return imgUrl;
        }

        public void setImgUrl(String imgUrl) {
            this.imgUrl = imgUrl;
        }
    }



    //结束时间修复
    public static class activityEndTime extends RequestModel {


        private String[] activityIds;//活动id

        public String[] getActivityIds() {
            return activityIds;
        }

        public void setActivityIds(String[] activityIds) {
            this.activityIds = activityIds;
        }
    }





    //处置 资金服务商操作
    public static class fundDisposalOperation extends RequestModel {


        private String activityId;//预招商活动信息

        private String disposalService;//资金服务商

        private String fundProvider;//处置服务商

        private String staffId;//员工id


        public String getActivityId() {
            return activityId;
        }

        public void setActivityId(String activityId) {
            this.activityId = activityId;
        }

        public String getDisposalService() {
            return disposalService;
        }

        public void setDisposalService(String disposalService) {
            this.disposalService = disposalService;
        }

        public String getFundProvider() {
            return fundProvider;
        }

        public void setFundProvider(String fundProvider) {
            this.fundProvider = fundProvider;
        }

        public String getStaffId() {
            return staffId;
        }

        public void setStaffId(String staffId) {
            this.staffId = staffId;
        }
    }
}
