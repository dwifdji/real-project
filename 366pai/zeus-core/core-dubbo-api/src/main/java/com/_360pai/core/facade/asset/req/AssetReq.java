package com._360pai.core.facade.asset.req;

import com._360pai.arch.common.RequestModel;
import com._360pai.core.facade.disposal.req.City;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @author : whisky_vip
 * @date : 2018/8/15 14:55
 */
public class AssetReq {
    @Getter
    @Setter
    public static class AssetIdReq extends RequestModel {
        private Integer assetId;
        private Integer partyId;
    }

    @Getter
    @Setter
    public static class AddReq extends RequestModel {
        private String fields;
        private Integer assetId;
        private Integer partyId;
        //默认初始值为0
        private Integer spvId = 0;
        private Integer requirementId;
        private Date deadline;
        private String cost;
        private Double period;
        private Integer disposalId;
        private Integer operatorId;
        private String type;
        private Boolean jointStatus;//全网连拍状态true 联拍 false不联拍

    }

    @Getter
    @Setter
    public static class AddDisposalReq extends RequestModel {
        private String fields;
        private Integer assetId;
        private Integer partyId;
        private Integer requirementId;
        private Integer disposalId;
        private Disposal disposal;
        private City[] providerAreas;
        private String[] extra;
        private Integer accountId;
    }

    @Getter
    @Setter
    public static class Disposal extends RequestModel {
        private String caseDescription;
        private String requireDescription;
        private String disposalName;
        private Date deadline;
        private String cost;
        private Double period;
        private City[] providerAreas;
        private String[] extra;
    }

    @Getter
    @Setter
    public static class QueryReq extends RequestModel {
        private Integer agencyId;
        private String status;
        private String partyName;
        private String partyType;
        private String agencyName;

        private String q;
        private String createdAtFrom;
        private String createdAtTo;
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
        private String categoryId;
        private Integer propertyId;
        private Integer partyId;


        /**
         * 资产类型 1 债权 2 物权 3 租赁
         */
        private String type;

        private String name;

        //企业员工id
        private Integer leaseStaffId;

        private String categoryGroupId;


    }

    public static class UpdateReq extends RequestModel {
        /**
         *
         */
        @NotNull
        private Integer id;
        /**
         * 标的名称
         */
        private String name;

        /**
         * 详情
         */
        private String detail;

        /**
         * 特别告知
         */
        private String specialDetail;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getDetail() {
            return detail;
        }

        public void setDetail(String detail) {
            this.detail = detail;
        }

        public String getSpecialDetail() {
            return specialDetail;
        }

        public void setSpecialDetail(String specialDetail) {
            this.specialDetail = specialDetail;
        }
    }

    @Getter
    @Setter
    public static class UploadReportReq extends RequestModel{
        private Integer assetId;
        private BigDecimal price;
        private String[] reports;
        private Integer partyId;
    }


    @Getter
    @Setter
    public static class LeaseDataReq extends RequestModel{

        /**
         *  标的id
         */
        private Integer leaseDraftId;
        /**
         *  标的id
         */
        private Integer assetId;
        /**
         * 状态
         */
        private String status;
        /**
         * 创建时间
         */
        private java.util.Date createdAt;
        /**
         * 标的编号
         */
        private String code;
        /**
         * 标的名称
         */
        private String name;
        /**
         * 标的所在省id
         */
        private String provinceId;
        /**
         * 标的所在地
         */
        private String cityId;
        /**
         * 标的所在区id
         */
        private String areaId;
        /**
         * 委托人ID
         */
        private Integer partyId;
        /**
         * 标的物可能由多个
         */
        private Integer quantity;
        /**
         * 剩余个数
         */
        private Integer remain;
        /**
         * 标的物类型
         */
        private String assetType;
        /**
         * 备注
         */
        private String remark;
        /**
         * 上传机构ID
         */
        private Integer agencyId;
        /**
         * 保留价
         */
        private String reservePrice;
        /**
         * 描述文件
         */
        private String descriptionDoc;
        /**
         * 标的详情
         */
        private String detail;
        /**
         * 联系人
         */
        private String contactName;
        /**
         * 联系人手机号
         */
        private String contactPhone;
        /**
         * 联系人QQ
         */
        private String contactQq;
        /**
         * 包含图片的JSON数据
         */
        private com.alibaba.fastjson.JSONObject extra;
        /**
         * 期望拍卖模式
         */
        private String expectedMode;
        /**
         * 参考价
         */
        private java.math.BigDecimal refPrice;
        /**
         * 起拍价
         */
        private java.math.BigDecimal startingPrice;
        /**
         * 期望拍卖开始时间
         */
        private java.util.Date beginAt;
        /**
         * 期望拍卖结束时间
         */
        private java.util.Date endAt;
        /**
         * 标的属性ID
         */
        private Integer propertyId;
        /**
         * 标的类型ID(模板ID)
         */
        private Integer categoryId;
        /**
         * 标的物选项
         */
        private String options;
        /**
         * 交接天数 仅作为 属性
         */
        private Integer handoverDays;
        /**
         * 支付天数 仅作为 属性
         */
        private Integer payDays;
        /**
         * 特别说明
         */
        private String specialDetail;
        /**
         * 银行账户名
         */
        private String bankAccountName;
        /**
         * 银行账户号码
         */
        private String bankAccountNumber;
        /**
         * 银行ID
         */
        private Integer bankId;
        /**
         * 用户手动输入的银行名称
         */
        private String bankName;
        /**
         * 尽调报告 金额
         */
        private java.math.BigDecimal tuneReport;
        /**
         * 尽调报告url
         */
        private com.alibaba.fastjson.JSONObject tuneReportUrl;
        /**
         * 尽调报告是否授权  false未授权  true授权
         */
        private Integer tuneReportAuthorization;
        /**
         * 评估报告 1:基础评估 2:完整评估
         */
        private java.math.BigDecimal evaluationReport;
        /**
         * 评估报告url
         */
        private com.alibaba.fastjson.JSONObject evaluationReportUrl;
        /**
         * 债券转让协议url
         */
        private String claimsTransferUrl;
        /**
         * 转让公告url
         */
        private String transferAnnouncementUrl;
        /**
         * 更新时间
         */
        private java.util.Date updatedAt;
        /**
         * 线上 线下操作 0:线下操作 1:线上操作
         */
        private Integer onlined;
        /**
         * 业务类型 0:拍卖 1:配置乐 2:处置服务商
         */
        private Integer busType;
        /**
         * 债权本金
         */
        private java.math.BigDecimal debtPrincipal;
        /**
         * 债权利息
         */
        private java.math.BigDecimal debtInterest;
        /**
         * 上拍来源：0 平台 1 机构
         */
        private String comeFrom;
        /**
         * 标的连拍状态 0 联拍 1不联拍
         */
        private Integer jointStatus;
        /**
         * 标识老数据 1:true老数据  0:false新数据
         */
        private Boolean oldData;
        /**
         * spvID  初始值为0
         */
        private Integer spvId;
        /**
         * 删除标志（0 否 1 是）
         */
        private Boolean deleteFlag;
        /**
         * 标的小状态
         */
        private String subStatus;
        /**
             * 租赁类型
         */
        private String leaseType;
        /**
         * 区域类型
         */
        private String areaType;
        /**
         * 租赁面积
         */
        private Integer leaseArea;
        /**
         * 租赁间数
         */
        private Integer leaseRoomNumber;
        /**
         * 出租物品地址
         */
        private String leaseAddress;
        /**
         * 权力情况
         */
        private String powerSituation;
        /**
         * 法律用途
         */
        private String legalPurposes;
        /**
         * 现状描述
         */
        private String currentDesc;
        /**
         * 出租房名称
         */
        private String lessorName;
        /**
         * 出租方地址
         */
        private String lessorAddress;
        /**
         * 出租方联系人
         */
        private String lessorContact;
        /**
         * 出租房联系方式
         */
        private String lessorContactNumber;
        /**
         * 竞租方资格
         */
        private String bidderQualification;
        /**
         * 是否有房产证
         */
        private Boolean deedFlag;
        /**
         * 资料预审方式
         */
        private String preAuditMethod;
        /**
         * 原租户名称
         */
        private String originalTenantName;
        /**
         * 原租户联系方式
         */
        private String originalTenantNumber;
        /**
         * 优先资格
         */
        private String priorityQualification;
        /**
         * 经营能力
         */
        private String managementCapacity;
        /**
         * 注册地址
         */
        private String registeredAddress;
        /**
         * 现在经营业务
         */
        private String businessNow;
        /**
         * 租赁开始时间
         */
        private java.util.Date leaseStartTime;
        /**
         * 租赁结束时间
         */
        private java.util.Date leaseEndTime;
        /**
         * 出租用途类型约定
         */
        private String operatingType;
        /**
         * 业态约定
         */
        private String businessAgreement;
        /**
         * 租金约定
         */
        private String rentAgreement;
        /**
         * 房屋结构约定
         */
        private String houseStructureAgreement;
        /**
         * 维护约定
         */
        private String maintainAgreement;
        /**
         * 安全约定
         */
        private String safetyAgreement;
        /**
         * 其他约定
         */
        private String lesseeOtherAgreement;
        /**
         * 征收约定
         */
        private String levyAgreement;
        /**
         * 续租约定
         */
        private String renewalAgreement;
        /**
         * 终止合同约定
         */
        private String terminationContractAgreement;
        /**
         * 争议处理约定
         */
        private String disputeResolutionAgreement;
        /**
         * 违约责任约定
         */
        private String defaultLiabilityAgreement;
        /**
         * 合同其他约定
         */
        private String contractOtherAgreement;
        /**
         * 出租房佣金比例
         */
        private String lessorCommissionRate;
        /**
         * 承租方佣金比例
         */
        private String lesseeCommissionRate;
        /**
         * 报名结束时间
         */
        private java.util.Date applyEndTime;
        /**
         * 审核结束时间
         */
        private java.util.Date approvalEndTime;
        /**
         * 租金年递增比例
         */
        private String annualIncrementRate;
        /**
         * 加价幅度
         */
        private BigDecimal increment;
        /**
         * 减价幅度
         */
        private BigDecimal reduction;
        /**
         * 当前价
         */
        private BigDecimal currentPrice;
        /**
         * 降价周期
         */
        private Integer reductionPeriod;
        /**
         * 预展时间
         */
        private Date previewAt;
        /**
         * 保证金
         */
        private BigDecimal deposit;
        /**
         * 资产亮点
         */
        private String brightSpot;
        /**
         * 服务机构id
         */
        private Integer serverAgencyId;

        /**
         * 租金支付约定
         */
        private String paymentCycle;

        /**
         * 费用承担方
         */
        private String costBearer;

        /**
         * 镇id
         */
        private String townId;

        /**
         * 区域数组JSON对象
         */
        private com.alibaba.fastjson.JSONObject assetLeaseArea;

        /**
         * 房屋结构
         */
        private String houseStructure;

        /**
         * 租赁编号
         */
        private String leaseCode;

        /**
         * 是否为web调用
         */
        private String webFlag;

        /**
         * 实际用途
         */
        private String actualUse;
        /**
         * 带看开始时间
         */
        private Date viewBeginTime;

        /**
         * 带看结束时间
         */
        private Date viewEndTime;
    }
}
