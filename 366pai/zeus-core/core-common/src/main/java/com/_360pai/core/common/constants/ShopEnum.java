package com._360pai.core.common.constants;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ShopEnum {

    public static final String AUCTIONSTATUS = "[{\"id\": \"ahead\",\"name\": \"" + ActivityEnum.OnlineStatus.UPCOMING.getValue() + "\"}," +
            "{\"id\": \"beginAuction\",\"name\": \"" + ActivityEnum.OnlineStatus.SALE.getValue() + "\"}," +
            "{\"id\": \"success\",\"name\": \"成交\"}," +
            "{\"id\": \"end\",\"name\": \"结束\"}]";


    public enum ShopGuideType{

        SHOP_PAYMENT("1", "开店支付服务费"),
        SHOP_NAME("2", "发送店铺名称" ),
        ADD_APPLET("3", "添加小程序"),
        UP_SHELVES("4", "上架货物"),
        SEARCH_TIPS("5", "搜索提示");

        private String type;

        private String typeDesc;

        private ShopGuideType(String type, String typeDesc) {
            this.type = type;
            this.typeDesc = typeDesc;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getTypeDesc() {
            return typeDesc;
        }

        public void setTypeDesc(String typeDesc) {
            this.typeDesc = typeDesc;
        }

        public static String getTypeDesc(String type) {
            ShopGuideType[] values = ShopGuideType.values();

            for (ShopGuideType shopGuideType : values) {
                if(type.equals(shopGuideType.getType())) {
                    return shopGuideType.getTypeDesc();
                }
            }
            return "";
        }
    }


    public enum ShopGuideStatus{

        NO_READ("0", "未提示"),
        HAS_READ("1", "已提示");

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getStatusDesc() {
            return statusDesc;
        }

        public void setStatusDesc(String statusDesc) {
            this.statusDesc = statusDesc;
        }

        private String status;

        private String statusDesc;

        private ShopGuideStatus(String status, String statusDesc) {
            this.status = status;
            this.statusDesc = statusDesc;
        }

        public static String getStatusDesc(String status) {
            ShopGuideStatus[] values = ShopGuideStatus.values();
            for (ShopGuideStatus shopGuideStatus: values) {
                if(status.equals(shopGuideStatus.getStatus())) {
                    return shopGuideStatus.getStatusDesc();
                }
            }

            return "";
        }

    }

    public enum MessageTypeEnum{

        MESSAGE_TYPE("1", "MESSAGE", "店铺信息"),
        ANNOUNCEMENT_TYPE("2", "SHOP_ANNOUNCEMENT", "平台公告");

        private String typeNum;

        private String type;

        private String typeDesc;



        public String getTypeNum() {
            return typeNum;
        }

        public void setTypeNum(String typeNum) {
            this.typeNum = typeNum;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getTypeDesc() {
            return typeDesc;
        }

        public void setTypeDesc(String typeDesc) {
            this.typeDesc = typeDesc;
        }

         MessageTypeEnum(String typeNum, String type, String typeDesc) {
            this.typeNum = typeNum;
            this.type = type;
            this.typeDesc = typeDesc;
        }

        public static String getTypeByNum(String typeNum) {
            MessageTypeEnum[] values = MessageTypeEnum.values();
            for (MessageTypeEnum messageTypeEnum: values) {
                if(messageTypeEnum.getTypeNum().equals(typeNum)) {
                    return messageTypeEnum.getType();
                }
            }
            return "";
        }

        public static String getTypeNumByType(String type) {
            MessageTypeEnum[] values = MessageTypeEnum.values();
            for (MessageTypeEnum messageTypeEnum: values) {
                if(messageTypeEnum.getType().equals(type)) {
                    return messageTypeEnum.getTypeNum();
                }
            }
            return "";
        }
    }


    public enum NewOnlineStatus {
        /**
         * 即将开拍
         */
        UPCOMING("UPCOMING", "即将开拍"),
        /**
         * 正在拍卖
         */
        SALE("SALE", "正在拍卖"),
        /**
         * 成功
         */
        SUCCESS("SUCCESS", "成交"),
        /**
         * 成功
         */
        FAILED("FAILED", "结束");


        private final String key;
        private final String value;

        NewOnlineStatus(String key, String value) {
            this.key = key;
            this.value = value;
        }

        public String getKey() {
            return key;
        }

        public String getValue() {
            return value;
        }

        @Override
        public String toString() {
            return key.toString();
        }

        /**
         * 通过值得到key
         */
        public static String getKeyByValue(String value) {
            for (ShopEnum.NewOnlineStatus model : ShopEnum.NewOnlineStatus.values()) {
                if (model.getKey().equals(value)) {
                    return model.getValue();
                }
            }
            return null;
        }

        public static String getKeyByDesc(String value) {
            for (ShopEnum.NewOnlineStatus model : ShopEnum.NewOnlineStatus.values()) {
                if (model.getValue().equals(value)) {
                    return model.getKey();
                }
            }
            return null;
        }
    }


    public enum  PriceDetail{

        ONE_MILLION("ONE_MILLION", "一百万以下", 1000000),
        TEN_MILLION("TEN_MILLION", "一百万-九百万", 10000000),
        ONE_HUNDRED_MILLION("ONE_HUNDRED_MILLION", "一千万-一亿", 100000000),
        MORE_ONE_HUNDRED_MILLION("MORE_ONE_HUNDRED_MILLION", "一亿以上", 100000000);

        private String priceModel;

        private String priceText;

        private Integer priceNum;

        PriceDetail(String priceModel, String priceText, Integer priceNum) {
            this.priceModel = priceModel;
            this.priceText = priceText;
            this.priceNum = priceNum;
        }

        public String getPriceModel() {
            return priceModel;
        }

        public void setPriceModel(String priceModel) {
            this.priceModel = priceModel;
        }

        public String getPriceText() {
            return priceText;
        }

        public void setPriceText(String priceText) {
            this.priceText = priceText;
        }

        public Integer getPriceNum() {
            return priceNum;
        }

        public void setPriceNum(Integer priceNum) {
            this.priceNum = priceNum;
        }

        public static Integer getRealPrice(String priceModel) {
            PriceDetail[] values = PriceDetail.values();
            for (PriceDetail priceDetail: values) {
                if(priceDetail.getPriceModel().equals(priceModel)) {
                    return priceDetail.getPriceNum();
                }
            }
            return 0;
        }

        public static List<PriceModel> getPriceList() {
            PriceDetail[] values = PriceDetail.values();
            List<PriceModel> priceModels = new ArrayList<>();
            for (PriceDetail priceDetail: values) {
                priceModels.add(new ShopEnum().new PriceModel(priceDetail.getPriceModel(),
                        priceDetail.getPriceText(), priceDetail.getPriceNum()));
            }
            return priceModels;
        }
    }


    @Setter
    @Getter
    private class PriceModel implements Serializable{
        private String priceModel;

        private String priceText;

        private Integer priceNum;

        public PriceModel(String priceModel, String priceText, Integer priceNum) {
            this.priceModel = priceModel;
            this.priceText = priceText;
            this.priceNum = priceNum;
        }
    }




    @Setter
    @Getter
    private class searchInfo implements Serializable{
        private String key;

        private String value;



    }


    public enum PartyCategoryEnum {

        /**
         * # 正常人
         */
        NORMAL_USER("4", "NORMAL_USER"),
        /**
         * 银行
         */
        BANK_COMPANY("1","BANK_COMPANY"),
        /**
         * AMC
         */
        AMC_COMPANY("2", "AMC_COMPANY"),
        /**
         * 民营资管
         */
        FOLK_ASSET_COMPANY("3","FOLK_ASSET_COMPANY"),
        /**
         * 拍卖公司
         */
        AUCTION_COMPANY("5", "AUCTION_COMPANY"),

        /**
         * 普通公司
         */
        NORMAL_COMPANY("6", "NORMAL_COMPANY");



        private String key;

        private String value;

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        PartyCategoryEnum(String key, String value) {
            this.key = key;
            this.value = value;
        }

        /**
         * 通过值得到key
         */
        public static String getKeyByValue(String key) {
            for (ShopEnum.PartyCategoryEnum model : ShopEnum.PartyCategoryEnum.values()) {
                if (model.getKey().equals(key)) {
                    return model.getValue();
                }
            }
            return null;
        }
    }

}
