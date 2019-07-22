package com._360pai.core.facade.enrolling.req;

import com._360pai.arch.common.RequestModel;
import com._360pai.core.facade.activity.req.ActivityReq;
import lombok.Data;

import java.io.File;
import java.util.List;

/**
 * 描述：预招商请求参数
 * <p>
 * 作者： wuchuanqi
 * 版本： 1.0.0
 * 时间： 2018/8/20 11:13
 */
public class EnrollingImportReq {





    @Data
    public static class getUserListReq extends RequestModel {
         private String  name;

    }



    @Data
    public static class getUploadActivityListReq extends RequestModel {
        private String  type;

        private Integer  operatorId;

        private String  userName;

        private String  name; //招商名称

        private String  contactPerson; //项目经理

    }


    @Data
    public static class uploadActivityReq extends RequestModel {
        private File file;
        private String userId;

        private Integer operatorId;

        private byte[] bytes;


    }


    @Data
    public static class getUploadActivityDetailsReq extends RequestModel {
        private String activityId;

        private String type;

    }


    @Data
    public static class updateImagesReq extends RequestModel {
        private String activityId;

        private String[] images;

    }



    @Data
    public static class activityIdReq extends RequestModel {
        private String activityId;

        private String[] activityIds;

        private String[] images;


        private String deleteFlag;


    }



    @Data
    public static class auditActivityReq extends RequestModel {
        private String[] activityIds;

        private String type;

        private String beginAt;

        private String endAt;

        private String refuseReason;

        private String status;

        private List<String> activityIdList;

        private Integer operatorId;


        private String operatorAt;

    }


    @Data
    public static class updateActivityReq extends RequestModel {
        /**
         *
         */
        private java.lang.Integer id;
        /**
         *
         */
        private java.lang.Integer activityId;
        /**
         * 招商会名称
         */
        private java.lang.String name;
        /**
         * 分公司名称
         */
        private java.lang.String branchComName;
        /**
         * 债务人
         */
        private java.lang.String debtor;
        /**
         * 债务人行业
         */
        private java.lang.String debtorBus;
        /**
         * 经营状况
         */
        private java.lang.String busStates;
        /**
         * 省份
         */
        private java.lang.String debtorPro;
        /**
         * 城市
         */
        private java.lang.String debtorCity;
        /**
         * 地区
         */
        private java.lang.String debtorArea;
        /**
         * 所在地
         */
        private java.lang.String debtLocation;
        /**
         * 抵押物所在地
         */
        private java.lang.String pawnLocation;
        /**
         * 省份
         */
        private java.lang.String pawnPro;
        /**
         * 城市
         */
        private java.lang.String pawnCity;
        /**
         * 地区
         */
        private java.lang.String pawnArea;
        /**
         * 基准日
         */
        private java.lang.String baseDate;
        /**
         * 未偿本金
         */
        private java.lang.String outstandingPrincipal;
        /**
         * 未偿利息
         */
        private java.lang.String outstandingInterest;
        /**
         * 违约金
         */
        private java.lang.String dedit;
        /**
         * 其他
         */
        private java.lang.String otherInfo;
        /**
         * 债权总额
         */
        private java.lang.String totalDebt;
        /**
         * 资产包户数
         */
        private java.lang.String assetNum;
        /**
         * 资产包来源
         */
        private java.lang.String assetSource;
        /**
         * 评估值
         */
        private java.lang.String assetValue;
        /**
         * 评估基准日
         */
        private java.lang.String assetBaseDate;
        /**
         * 评估报告出具日
         */
        private java.lang.String reportDate;
        /**
         * 担保方式
         */
        private java.lang.String assureMeans;
        /**
         * 保证人名称
         */
        private java.lang.String assureName;
        /**
         * 具体担保措施
         */
        private java.lang.String specificAssureMeans;
        /**
         * 房地产性质
         */
        private java.lang.String realtyCharacter;
        /**
         * 土地面积
         */
        private java.lang.String landArea;
        /**
         * 房屋建筑面积
         */
        private java.lang.String buildingArea;
        /**
         * 抵质押顺位
         */
        private java.lang.String pledgeSequence;
        /**
         * 其他担保方式情况说明
         */
        private java.lang.String guaranteeThat;
        /**
         * 所处诉讼环节
         */
        private java.lang.String litigationLink;
        /**
         * 是否采取司法保全
         */
        private java.lang.String hasGuarantee;
        /**
         * 如已采取司法保全
         */
        private java.lang.String ifGuarantee;
        /**
         * 项目亮点
         */
        private java.lang.String projectWindow;
        /**
         * 备注
         */
        private java.lang.String remarks;
        /**
         * 资产描述
         */
        private java.lang.String assetDesc;
        /**
         * 项目联系人
         */
        private java.lang.String contactPerson;
        /**
         * 联系电话
         */
        private java.lang.String contactPhone;
        /**
         * 处置服务商
         */
        private java.lang.String disposalService;
        /**
         * 资金服务商
         */
        private java.lang.String fundProvider;




        private java.lang.String debtorProId;

        private java.lang.String debtorCityId;

        private java.lang.String debtorAreaId;


        private java.lang.String pawnProId;

        private java.lang.String pawnCityId;

        private java.lang.String pawnAreaId;


    }




    @Data
    public static class updateRealActivityReq extends RequestModel {
        /**
         *
         */
        private java.lang.Integer id;
        /**
         *
         */
        private java.lang.Integer activityId;
        /**
         * 招商会名称
         */
        private java.lang.String name;
        /**
         * 所在地
         */
        private java.lang.String pawnLocation;
        /**
         * 省份
         */
        private java.lang.String pawnPro;
        /**
         * 城市
         */
        private java.lang.String pawnCity;
        /**
         * 地区
         */
        private java.lang.String pawnArea;
        /**
         * 省份id
         */
        private java.lang.String pawnProId;
        /**
         * 城市id
         */
        private java.lang.String pawnCityId;
        /**
         * 地区id
         */
        private java.lang.String pawnAreaId;
        /**
         * 市场参考价
         */
        private java.lang.String refPrice;
        /**
         * 商品类型
         */
        private java.lang.String busTypeName;
        /**
         * 权力人类型
         */
        private java.lang.String powerType;
        /**
         * 共有情况
         */
        private java.lang.String ownedCondition;
        /**
         * 共有数量
         */
        private java.lang.String ownedNum;
        /**
         * 所有权来源
         */
        private java.lang.String ownedSource;
        /**
         * 房地产权证号
         */
        private java.lang.String realLicense;
        /**
         * 土地使用权证号
         */
        private java.lang.String landLicense;
        /**
         * 建筑面积
         */
        private java.lang.String buildArea;
        /**
         * 登记日期
         */
        private java.lang.String recordDate;
        /**
         * 房屋类型
         */
        private java.lang.String housingType;
        /**
         * 套内面积
         */
        private java.lang.String insideSpace;
        /**
         * 装修程度
         */
        private java.lang.String decorate;
        /**
         * 竣工日期
         */
        private java.lang.String completionDate;
        /**
         * 总层数
         */
        private java.lang.String totalNum;
        /**
         * 所在层数
         */
        private java.lang.String storyNum;
        /**
         * 承担方式
         */
        private java.lang.String bearWay;
        /**
         * 公用事业欠费
         */
        private java.lang.String publicOwe;
        /**
         * 物业管理欠费
         */
        private java.lang.String tenementOwe;
        /**
         * 增值税及附加
         */
        private java.lang.String vatAddition;
        /**
         * 土地增值税
         */
        private java.lang.String landVat;
        /**
         * 个人所得税
         */
        private java.lang.String personalVat;
        /**
         * 备注
         */
        private java.lang.String remark;
        /**
         * 土地面积
         */
        private java.lang.String landArea;
        /**
         * 分摊面积
         */
        private java.lang.String sharingArea;
        /**
         * 土地用途
         */
        private java.lang.String landUse;
        /**
         * 使用权来源
         */
        private java.lang.String useSource;
        /**
         * 使用期限
         */
        private java.lang.String usePeriod;
        /**
         * 是否有抵押
         */
        private java.lang.String ifMortgage;
        /**
         * 权利人
         */
        private java.lang.String holder;
        /**
         * 权利种类
         */
        private java.lang.String rightType;
        /**
         * 他项产权号
         */
        private java.lang.String otherEquity;
        /**
         * 权利价值
         */
        private java.lang.String rightValue;
        /**
         * 使用情况
         */
        private java.lang.String usageType;
        /**
         * 户籍/工商注册
         */
        private java.lang.String registration;
        /**
         * 户籍数/工商注册数
         */
        private java.lang.String registrationNum;
        /**
         * 租赁情况
         */
        private java.lang.String leaseCondition;
        /**
         * 租赁期限
         */
        private java.lang.String leaseDeadline;
        /**
         * 使用人
         */
        private java.lang.String leaseUser;
        /**
         * 押金
         */
        private java.lang.String leaseDeposit;
        /**
         * 租金
         */
        private java.lang.String leaseRent;
        /**
         * 租金支付至
         */
        private java.lang.String leasePayTo;
        /**
         * 与当事人关系
         */
        private java.lang.String relationship;
        /**
         * 室内物品
         */
        private java.lang.String indoorGoods;
        /**
         * 联系电话
         */
        private java.lang.String leasePhone;
        /**
         * 备注
         */
        private java.lang.String leaseRemark;
        /**
         * 有无限制
         */
        private java.lang.String unlimited;
        /**
         * 限制人
         */
        private java.lang.String limitOne;
        /**
         * 限制方式
         */
        private java.lang.String limitType;
        /**
         * 其他限制
         */
        private java.lang.String limitOther;
        /**
         * 限制备注
         */
        private java.lang.String limitRemark;
        /**
         * 处置服务商
         */
        private java.lang.String disposalService;
        /**
         * 资金服务商
         */
        private java.lang.String fundProvider;

        private java.lang.String contactName;


        private java.lang.String contactPhone;



        /**
         * 上传业务人员ID
         */
        private java.lang.Integer operatorId;
        /**
         *
         */
        private java.lang.String extra;
        /**
         *
         */
        private java.util.Date beginAt;
        /**
         *
         */
        private java.util.Date endAt;
        /**
         *
         */
        private java.lang.Boolean deleteFlag;
        /**
         *
         */
        private java.util.Date createTime;
        /**
         *
         */
        private java.util.Date updateTime;


    }












    @Data
    public static class getFundServiceReq extends RequestModel {
        private String  activityId;

        private String  name;

        private String  type;//1 处置服务商 2 资金服务商


    }

}
