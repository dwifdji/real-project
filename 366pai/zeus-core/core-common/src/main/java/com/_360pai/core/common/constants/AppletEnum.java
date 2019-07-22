package com._360pai.core.common.constants;

/**
 * @author xdrodger
 * @Title: AppletEnum
 * @ProjectName zeus
 * @Description:
 * @date 2018/11/27 16:41
 */
public class AppletEnum {

    public enum FrontApplyStatus {
        /**
         * 等待审核
         */
        NO_APPLY("NO_APPLY", "未申请"),
        /**
         * 等待审核
         */
        PENDING("PENDING", "申请中"),
        /**
         * 审核通过
         */
        APPROVED("APPROVED", "已认证"),
        /**
         * 审核拒绝
         */
        REJECT("REJECT", "审核拒绝");

        private final String key;
        private final String value;

        public String getKey() {
            return key;
        }

        public String getValue() {
            return value;
        }

        FrontApplyStatus(String key, String value) {
            this.key = key;
            this.value = value;
        }

        /**
         * 通过值得到key
         */
        public static String getValueByKey(String key) {
            for (FrontApplyStatus model : FrontApplyStatus.values()) {
                if (model.getKey().equals(key)) {
                    return model.value;
                }
            }
            return null;
        }
    }

    public enum OpenShopStatus {
        /**
         * 已开通
         */
        OPENED("OPENED", "已开通"),
        /**
         * 已开通
         */
        NOT_OPENED("NOT_OPENED", "未开通");

        private final String key;
        private final String value;

        public String getKey() {
            return key;
        }

        public String getValue() {
            return value;
        }

        OpenShopStatus(String key, String value) {
            this.key = key;
            this.value = value;
        }

        /**
         * 通过值得到key
         */
        public static String getValueByKey(String key) {
            for (OpenShopStatus model : OpenShopStatus.values()) {
                if (model.getKey().equals(key)) {
                    return model.value;
                }
            }
            return null;
        }
    }

    /**
     * 站内消息
     */
    public enum MessageType {
        /**
         * 下级用户开店通知
         */
        SUBORDINATE_OPEN_SHOP("1", "好友开店通知"),

        /**
         * 提现通知
         */
        WITHDRAW("2", "提现通知"),
        /**
         * 开店通知
         */
        OPEN_SHOP("3", "开店通知"),
        /**
         * 下线注册完成 （第一次）
         */
        SUBORDINATE_ACCOUNT_REGISTER("4", "好友注册完成通知"),
        /**
         * 下线注册完成 （第二次，3天内并未完成认证）
         */
        SUBORDINATE_ACCOUNT_REGISTER_SECOND("5", "好友注册通知"),
        /**
         * 下线认证完成（第一次）
         */
        SUBORDINATE_AUTH_FINISH("6", "好友账户认证成功通知"),
        /**
         * 下线认证完成（第二次）
         */
        SUBORDINATE_AUTH_FINISH_SECOND("7", "好友认证成功通知"),
        /**
         * 账户注册通知
         */
        ACCOUNT_REGISTER("8", "注册成功通知"),
        /**
         * 到账提醒
         */
        ARRIVAL_REMINDER("9", "到账提醒"),
        /**
         * 分佣提醒
         */
        COMMISSION_REMINDER("10", "分佣提醒"),
        /**
         * 好友报名招商提醒
         */
        SUBORDINATE_ENROLLING_ACTIVITY_APPLY_REMINDER("11", "下级好友报名成功"),
        /**
         * 店铺信息变更成功通知
         */
        SHOP_UPDATE_APPLY_APPROVE_REMINDER("12", "店铺信息变更成功通知");

        private final String key;
        private final String value;

        public String getKey() {
            return key;
        }

        public String getValue() {
            return value;
        }

        MessageType(String key, String value) {
            this.key = key;
            this.value = value;
        }
    }



    /**
     * 开店支付的状态
     */
    public enum PAY_STATUS {
        /**
         * 初始状态
         */
        INIT("0", "支付初始状态"),

        /**
         * 提现通知
         */
        SUCCESS("1", "支付成功"),
        /**
         * 支付失败
         */
        FAILURE("2", "支付失败");

        private final String key;
        private final String value;

        public String getKey() {
            return key;
        }

        public String getValue() {
            return value;
        }

        PAY_STATUS(String key, String value) {
            this.key = key;
            this.value = value;
        }
    }




    /**
     *
     *查询枚举类转换
     */
    public  enum QUERY_STATUS
    {
        BANK_COMPANY("1", PartyEnum.Category.BANK_COMPANY.getKey()),
        AMC_COMPANY("2", PartyEnum.Category.AMC_COMPANY.getKey()),
        FOLK_ASSET_COMPANY("3", PartyEnum.Category.FOLK_ASSET_COMPANY.getKey()),
        NORMAL_USER("4", PartyEnum.Category.NORMAL_USER.getKey()),
        AUCTION_COMPANY("5", PartyEnum.Category.AUCTION_COMPANY.getKey());

        private String type;
        private String typeName;

        public String getType()
        {
            return type;
        }

        public void setType(String type)
        {
            this.type = type;
        }

        public String getTypeName()
        {
            return typeName;
        }

        public void setTypeName(String typeName)
        {
            this.typeName = typeName;
        }
        public boolean test(String code) {
            if (this.getType().equals(code)) {
                return true;
            }
            return false;
        }
        QUERY_STATUS(String type, String typeName)
        {
            this.type = type;
            this.typeName = typeName;
        }

        //根据code 获取描述
        public static String getDesc(String type) {
            for (QUERY_STATUS c : QUERY_STATUS.values()) {
                if (c.getType().equals(type)) {
                    return c.getTypeName();
                }
            }
            return null;
        }

        //根据值获取type
        public static String getType(String typeName) {
            for (QUERY_STATUS c : QUERY_STATUS.values()) {
                if (c.getTypeName().equals(typeName)) {
                    return c.getType();
                }
            }
            return null;
        }
    }





    /**
     *
     *查询枚举类转换
     */
    public  enum QUERY_PRICE
    {
        BANK_COMPANY("1", "","1000000"),
        AMC_COMPANY("2", "1000000","9000000"),
        FOLK_ASSET_COMPANY("3","10000000","90000000"),
        NORMAL_USER("4", "100000000","");

        private String type;
        private String beginPrice;
        private String endPrice;


        public String getType()
        {
            return type;
        }

        public void setType(String type)
        {
            this.type = type;
        }


        public String getBeginPrice() {
            return beginPrice;
        }

        public void setBeginPrice(String beginPrice) {
            this.beginPrice = beginPrice;
        }

        public String getEndPrice() {
            return endPrice;
        }

        public void setEndPrice(String endPrice) {
            this.endPrice = endPrice;
        }

        QUERY_PRICE(String type, String beginPrice, String endPrice)
        {
            this.type = type;
            this.beginPrice = beginPrice;
            this.endPrice = endPrice;

        }

        //根据code 获取beginPrice
        public static String getBeginPrice(String type) {
            for (QUERY_PRICE c : QUERY_PRICE.values()) {
                if (c.getType().equals(type)) {
                    return c.getBeginPrice();
                }
            }
            return null;
        }

        //根据值获取type
        public static String getEndPriceByType(String type) {
            for (QUERY_PRICE c : QUERY_PRICE.values()) {
                if (c.getType().equals(type)) {
                    return c.getEndPrice();
                }
            }
            return null;
        }
    }

    public enum AppletAgreementType {
        /**
         * 360PAI银行账户绑定须知
         */
        BIND_BANK_ACCOUNT("1", "360PAI银行账户绑定须知"),
        /**
         * 已开通
         */
        CONSULTING_SERVICE_CONTRACT("2", "咨询服务合同"),
        /**
         * 开店协议
         */
        OPEN_SHOP("3", "开店协议"),
        /**
         * 用户协议
         */
        USER("4", "360PAI用户协议"),
        /**
         * 用户隐私权政策
         */
        USER_PRIVACY("5", "用户隐私权政策");

        private final String key;
        private final String value;

        public String getKey() {
            return key;
        }

        public String getValue() {
            return value;
        }

        AppletAgreementType(String key, String value) {
            this.key = key;
            this.value = value;
        }

        /**
         * 通过值得到key
         */
        public static String getValueByKey(String key) {
            for (AppletAgreementType model : AppletAgreementType.values()) {
                if (model.getKey().equals(key)) {
                    return model.value;
                }
            }
            return null;
        }
    }
}
