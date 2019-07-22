package com._360pai.core.facade.activity.req;

import com._360pai.arch.common.RequestModel;
import com.alibaba.fastjson.JSONArray;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;

/**
 * @author : whisky_vip
 * @date : 2018/8/15 18:34
 */
public class ActivityReq extends RequestModel{

    @Getter
    @Setter
    public static class ActivityId extends RequestModel {
        private Integer activityId;
        private Integer assetId;
        private String agencyCode;
        private Integer partyId;
        private List<Integer> ids;
        private String reason;
        private Integer agencyId;
        private Integer staffId;
        private Integer accountId;
        private String visibilityLevel;
        private Integer operatorId;
        /**
         * 线上 线下操作 0:线下操作 1:线上操作
         */
        private java.lang.Integer onlined;
        private Integer shopId; //关注活动的类型


        /**
         *机构自己联排的列表
         */
        private String joint;

        private String jointAgencyId;

        private String type; //关注的类型

        private Integer resourceId; //关注来源的id

        private String url;

        private String imageUrl;

        private boolean appletFlag = false;
        /**
         * 银行类全线下标志
         */
        private java.lang.Boolean bankOfflineFlag = false;

        /**
         * 活动类型（拍卖 auction，招商 enrolling）
         */
        private String activityType;
        /**
         * 服务商类型（处置 dispose，资金 fund）
         */
        private String providerType;
    }

    @Getter
    @Setter
    public static class Search extends RequestModel {
        /**
         *拍品类型
         */
        private Integer categoryId;
        /**
         *委托方类型
         */
        private Integer partyCategoryId;
        /**
         *资产类型
         */
        private Integer propertyNameId;
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
        /**
         *机构code
         */
        private String agencyCode;
        /**
         * 搜索条件
         */
        private String query;

        /**
         * 拍卖方式
         * ENGLISH = 1  # 增价 升价拍
         * DUTCH = 2  # 减价 降价拍模式
         * SEALED = 3  # 暗标 一口价暗标
         * PUBLIC = 4  # 明标 一口价明标
         * FREE = 5  # 自由报价 自由出价 择优成交
         * FLASH = 6  # 秒杀 限时秒杀
         */
        private String activityMode;
        /**
         *   拍卖状态
         */
        private String activityStatus;

        /**
         *今日上新type
         */
        private String todayFlag;

        /**
         * 店铺拍品ids
         */
        private List<Integer> auctionIdList;

        /**
         * 起始价格
         */
        private String beginPrice;

        /**
         * 结束价格
         */
        private String endPrice;

        /**
         * 排序 “startingPrice_asc”：起拍价升序排列 “startingPrice_desc”：起拍价降序排列 “viewCount_asc” : 浏览量升序排列 “viewCount_desc”:浏览量降序排列
         */
        private String orderBy;

        /**
         *机构自己联排的列表
         */
        private String joint;

        /**
         *连拍的机构id
         */
        private String jointAgencyId;
        /**
         * 商品分类
         */
        private String busTypeName;


        /**
         * 资产类别 1 区属国有资产2 区属集体资产 3 镇属国有资产
         */
        private String assetClass;

        /**
         * 资产类别 1 区属国有资产2 区属集体资产 3 镇属国有资产
         */
        private String leaseAreaType;

        /**
         * 资产类别 资产分类
         */
        private String assetLeaseType;

        /**
         * 区中的镇
         */
        private String townId;

        /**
         * 推广位id
         */
        private Integer activityPosterId;
    }

    @Getter
    @Setter
    public static class QueryReq extends RequestModel {
        private Integer agencyId;
        private String status;
        private String q;
        private String createdAtFrom;
        private String createdAtTo;
        private String previewAtFrom;
        private String previewAtTo;
        private String beginAtFrom;
        private String beginAtTo;
        private String assetPropertyName;
        private String activityName;
        private String agencyName;
        private String activityStatus;
        private String partyType;
        private String partyName;
        private String categoryGroupId;
        private String unionStatus;
        private Integer propertyId;
        private Integer partyId;
        /**
         * 拍卖方式
         * ENGLISH = 1  # 增价 升价拍
         * DUTCH = 2  # 减价 降价拍模式
         * SEALED = 3  # 暗标 一口价暗标
         * PUBLIC = 4  # 明标 一口价明标
         * FREE = 5  # 自由报价 自由出价 择优成交
         * FLASH = 6  # 秒杀 限时秒杀
         */
        private String mode;

        /**
         * 上拍来源：0 平台 1 机构
         */
        private String comeFrom;
        private String visibilityLevel;

        private Integer excludeAppletHallActivity;
        private Integer hallId;
        private Integer excludeAssetPropertyActivity;
        private Integer excludeAlbumActivity;
        private Integer frontFlag;


        /**
         * 资产类型 1 债权 2 物权 3 租赁
         */
        private String type;

        private String name;

        //企业员工id
        private Integer leaseStaffId;

        private String categoryId;

        private Integer id;

        private String agencyCode;
    }

    @Getter
    @Setter
    public static class UpdateReq extends RequestModel {
        /**
         * 活动id
         */
        @NotNull
        private Integer id;
        /**
         * 参拍区域白名单
         */
        private List<Integer> whitelistCities;
        /**
         * 参拍区域黑名单
         */
        private List<Integer> blacklistCities;
        /**
         * 委托人佣金比例
         */
        private java.math.BigDecimal commissionPercentSeller;
        /**
         * 买受人佣金比例
         */
        private java.math.BigDecimal commissionPercentBuyer;
        /**
         * 拍卖师姓名
         */
        private String auctioneerName;
        /**
         * 拍卖师联系方式
         */
        private String auctioneerPhone;
        /**
         * 拍卖师QQ
         */
        private String auctioneerQq;

        private Integer operatorId;



    }

    @Getter
    @Setter
    public static class SignDelegationAgreementCallBackReq extends RequestModel {
        private String contractId;
        private Integer activityId;
        private Integer sellerPartyId;
        boolean hasSuccess;
    }

    @Getter
    @Setter
    public static class SignAdditionalAgreementCallBackReq extends RequestModel {
        private String contractId;
        private Integer activityId;
        private Integer sellerPartyId;
        boolean hasSuccess;
    }

    @Getter
    @Setter
    public static class ModifyReservePriceReq extends RequestModel {
        private Integer activityId;
        private BigDecimal reservePrice;
        private Integer operatorId;
    }



    @Getter
    @Setter
    public static class JointListReq extends RequestModel {
        private String type;// 列表类型
        private String previewAtForm;
        private String previewAtTo;
        private String beginAtFrom;
        private String beginAtTo;
        private String assetName;
        private String auctionType;
        private String categoryId;
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
        private String status;
        private String jointStatus;
        private Integer agencyId;

        private Integer isJoint;




    }



    @Getter
    @Setter
    public static class JointReq extends RequestModel {
        private String type;// 列表类型
        private String[] ids;
        private Integer agencyId;
        private Integer isDel;// 删除标志

        private Integer isJoint;//联拍



    }


    @Getter
    @Setter
    public static class setJointStatusReq extends RequestModel {
        private Integer jointStatus;

    }


    @Data
    public static class ActivityServiceProviderReq extends RequestModel {
        private Integer activityId;
        /**
         * 活动类型（拍卖 auction，招商 enrolling）
         */
        private String activityType;
        /**
         * 服务商类型（处置 dispose，资金 fund）
         */
        private String providerType;
        /**
         * 处置服务商id
         */
        private Integer disposeProviderId;
        /**
         * 资金服务商名称
         */
        private String fundProviderName;

        private Integer id;

        private Integer orderNum;

        private String q;
    }

    @Data
    public static class ActivityPosterAddReq extends RequestModel {
        /**
         * 推广位名称
         */
        private String name;
        /**
         * 开始时间
         */
        private java.util.Date beginAt;
        /**
         * 结束时间
         */
        private java.util.Date endAt;
        /**
         * 机构显示标志
         */
        private Boolean agencyDisplayFlag;
        /**
         * 图片链接
         */
        private String imgUrl;
        /**
         * 自动添加资产标志
         */
        private Boolean autoFlag;
        /**
         * 省id
         */
        private Integer provinceId;
        /**
         * 市id
         */
        private Integer cityId;
        /**
         * 区县id
         */
        private Integer areaId;
        /**
         * 资产类型
         */
        private String category;
        /**
         * 商品分类
         */
        private String busType;
        /**
         * 状态
         */
        private String status;
        /**
         * 活动id，逗号分隔
         */
        private JSONArray activityIds;
        /**
         * 排序号
         */
        private Integer orderNum;
    }

    @Data
    public static class ActivityPosterUpdateReq extends RequestModel {
        private Integer id;
        /**
         * 推广位名称
         */
        private String name;
        /**
         * 开始时间
         */
        private java.util.Date beginAt;
        /**
         * 结束时间
         */
        private java.util.Date endAt;
        /**
         * 机构显示标志
         */
        private Boolean agencyDisplayFlag;
        /**
         * 图片链接
         */
        private String imgUrl;
        /**
         * 自动添加资产标志
         */
        private Boolean autoFlag;
        /**
         * 省id
         */
        private Integer provinceId;
        /**
         * 市id
         */
        private Integer cityId;
        /**
         * 区县id
         */
        private Integer areaId;
        /**
         * 资产类型
         */
        private String category;
        /**
         * 商品分类
         */
        private String busType;
        /**
         * 状态
         */
        private String status;
        /**
         * 活动id，逗号分隔
         */
        private JSONArray activityIds;
        /**
         * 排序号
         */
        private Integer orderNum;
    }
}
