package com._360pai.core.facade.shop.req;


import com._360pai.arch.common.RequestModel;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.NotBlank;

import java.math.BigDecimal;

public class ShopReq {

    @Getter
    @Setter
    public static class ShopListReq extends RequestModel {
        /**
         *拍品类型
         */
        private String categoryId;
        /**
         *委托方类型
         */
        private String partyCategoryId;
        /**
         *资产类型
         */
        private Integer propertyNameId;
        /**
         *城市
         */
        private Integer cityId;
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
         * 店铺id
         */
        private String shopId;

        /**
         * 起始价格
         */
        private String beginPrice;

        /**
         * 结束价格
         */
        private String endPrice;

        /**
         * 搜索人openId
         */
        private String openId;

        /**
         * 上架或者下架标识
         */
        private String deleteFlag;

        private String outFlag;

        private String startingPrice;


    }


    /**
     * 首页展示设置请求参数
     */
    @Getter
    @Setter
    public static class HomePageReq extends RequestModel{

        /**
         * 要进行首页展示的拍品id
         */
        private String[] homePageArray;

        private String shopId;

    }

    @Setter
    @Getter
    public static class OutOfStocks extends RequestModel{

        /**
         * 要进行下架的商品ids
         */
        private String[] outOfStocks;

        private String shopId;

        private String outFlag;

        private String deleteFlag;
    }

    @Setter
    @Getter
    public static class UpOfStocks extends RequestModel{

        /**
         * 要进行下架的商品ids
         */
        private String[] upOfStocks;
        /**
         * 店铺id
         */
        private String shopId;
    }

    @Setter
    @Getter
    public static class SearchRecordReq extends RequestModel{
        private String query;

        private String shopId;

        private String openId;

        private String beginTime;

        private String endTime;
    }

    @Getter
    @Setter
    public static  class DeleteRecordReq extends RequestModel{

        private String shopId;

        private String[] records;

        private String deleteType; //0全部删除 1部分删除

        private String openId;
    }

    @Setter
    @Getter
    public static class AnnouncementRrq {
        public static final String NEWS = "NEWS"; //新闻类
        public static final String ANNOUNCEMENT = "ANNOUNCEMENT"; //平台公告

        /**
         *
         */
        private Integer id;
        /**
         *
         */
        private String title;
        /**
         *
         */
        private String url;
        /**
         *
         */
        private String detail;
        /**
         *
         */
        private java.util.Date createdAt;
        /**
         *
         */
        private java.util.Date expiredAt;
        /**
         *
         */
        private java.util.Date publicAt;
        /**
         *
         */
        private String category;


    }

    @Setter
    @Getter
    public static class ShopMessageReq extends RequestModel{

        private String shopId;

        private String openId;

        private String type;
    }

    @Setter
    @Getter
    public static class ShopMessageDetailReq extends RequestModel{
        private String messageId;

        private String type;

        private String openId;

        private String shopId;
    }

    @Setter
    @Getter
    public static class BaseReq extends RequestModel{
        private Integer shopId;
        private Integer partyId;
        private String openId;
        private Integer operatorId;
        private String reason;
        private Integer applyId;
        private Integer id;
        private String partyType;
        private Integer accountId;
    }

    @Setter
    @Getter
    public static class QueryReq extends RequestModel{
        private String createdAtFrom;
        private String createdAtTo;
        private String q;
        private String status;
        private Integer defaultAgencyId;
        private String inviteCode;
        private String partyType;
        private String defaultAgencyName;
        private Integer shopId;
    }

    @Setter
    @Getter
    public static class UpdateApplyReq extends RequestModel{
        private Integer shopId;
        private String name;
        private String logoUrl;
    }

    @Setter
    @Getter
    public static class UpdateReq extends RequestModel{
        private Integer shopId;
        /**
         * 是否隐藏联系电话中间4位
         */
        private java.lang.Boolean isHideContactPhone;
    }

    @Setter
    @Getter
    public static class UpdateContactPhoneReq extends RequestModel{
        private Integer shopId;
        @NotBlank
        private String contactPhone;
        @NotBlank
        private String smsCode;
    }

 	@Setter
    @Getter
    public static class ShopGuidesReq extends RequestModel{

        private String type;

        private String openId;
    }

    @Setter
    @Getter
    public static class ShopGuideUpdateReq extends RequestModel{

        private String openId;

        private String type;
    } 

	@Setter
    @Getter
    public static class ShopNotifyReq extends RequestModel{

        private String auctionId;

        private String type;
    }

    @Setter
    @Getter
    public static  class ShopBidRecord extends  RequestModel{
        private String auctionId;
    }

    @Setter
    @Getter
    public static class CreateReq extends RequestModel{
        private String openId;
        private Integer partyId;
        private BigDecimal amount = BigDecimal.ZERO;
    }

    @Setter
    @Getter
    public static class EnrollingReq extends RequestModel{
        private String type;

        private String status;

        private String beginPrice;

        private String endPrice;

        private Integer provinceId;

        private Integer cityId;

        private Integer shopId;

        private String query;

        private String outFlag;
    }


    @Setter
    @Getter
    public static class UpOrDownEnrollingReq extends RequestModel{

        /**
         * 要进行下架的商品ids
         */
        private String[] outOfStocks;

        private String shopId;

        private String outFlag;

        private String deleteFlag;
    }


    @Setter
    @Getter
    public static class AuctionEnrollingReq extends RequestModel{


        private Integer shopId;

        private String query;

        private String openId;


    }




}
 