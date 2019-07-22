package com._360pai.core.common.constants;

/**
 * @author xdrodger
 * @Title: AccountEnum
 * @ProjectName zeus
 * @Description:
 * @date 11/09/2018 15:36
 */
public class AccountEnum {

    public enum RegisterSource {
        /**
         * 平台
         */
        WEB("WEB", "平台"),
        /**
         * 机构
         */
        AGENCY("AGENCY", "机构"),
        /**
         * 小程序
         */
        APPLET("APPLET", "小程序");

        private final String key;
        private final String value;

        public String getKey() {
            return key;
        }

        public String getValue() {
            return value;
        }

        RegisterSource(String key, String value) {

            this.key = key;
            this.value = value;
        }
    }

    public enum ApplyStatus {
        /**
         * 等待审核
         */
        PENDING("PENDING", "等待审核"),
        /**
         * 审核通过
         */
        APPROVED("APPROVED", "审核通过"),
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

        ApplyStatus(String key, String value) {
            this.key = key;
            this.value = value;
        }

        /**
         * 通过值得到key
         */
        public static String getValueByKey(String key) {
            for (ApplyStatus model : ApplyStatus.values()) {
                if (model.getKey().equals(key)) {
                    return model.value;
                }
            }
            return null;
        }
    }

    /**
     * 账户外部关系类型
     */
    public enum ExtType {
        /**
         * 小程序
         */
        APPLET("APPLET", "小程序"),
        /**
         * 数字跳动小程序
         */
        CALCULATOR("CALCULATOR", "数字跳动小程序"),
        /**
         * 360拍买服务公众号
         */
        MP_360PAI("MP_360PAI", "360拍买服务公众号");

        private final String key;
        private final String value;

        public String getKey() {
            return key;
        }

        public String getValue() {
            return value;
        }

        ExtType(String key, String value) {

            this.key = key;
            this.value = value;
        }
    }

    /**
     * 账户外部关系类型
     */
    public enum InviteType {
        /**
         * 店铺
         */
        DP("DP", "店铺"),
        /**
         * 机构
         */
        JG("JG", "机构");

        private final String key;
        private final String value;

        public String getKey() {
            return key;
        }

        public String getValue() {
            return value;
        }

        InviteType(String key, String value) {

            this.key = key;
            this.value = value;
        }
    }



    /**
     * 账户操作类型  类型 充值、其他、开店推荐费、成交佣金、提现
     */
    public enum AcctOperateType {
        /**
         * 充值
         */
        RECHARGE("RECHARGE", "充值"),
        /**
         *
         */
        OTHER("OTHER", "其他"),
        BUY_COMMISSION("BUY_COMMISSION", "成交佣金"),
        SHOP_INVITED_COMMISSION("SHOP_INVITED_COMMISSION", "开店推荐费"),
        WITHDRAW("WITHDRAW", "提现");

        private final String key;
        private final String value;

        public String getKey() {
            return key;
        }

        public String getValue() {
            return value;
        }

        AcctOperateType(String key, String value) {

            this.key = key;
            this.value = value;
        }

        /**
         * 通过key得到值
         */
        public static String getValueByKey(String key) {
            for (AcctOperateType model : AcctOperateType.values()) {
                if (model.getKey().equals(key)) {
                    return model.value;
                }
            }
            return null;
        }
    }



    /**
     * 账户详情状态
     * 待初审、初审成功/失败、已签合同、已提供发票、
     * 发票审核成功/失败、终审成功/失败 、
     * 已出款、已标记红冲、红冲
     */
    public enum AcctDetailStatus {

        INIT("INIT", "待初审"),
        FIRST_VERIFY_SUCCESS("FIRST_VERIFY_SUCCESS", "待签合同"),
        FIRST_VERIFY_FAIL("FIRST_VERIFY_FAIL", "已失败"),
        HAS_SIGN_CONTRACT("HAS_SIGN_CONTRACT", "待提交发票"),
        HAS_PROVIDE_INVOICE("HAS_PROVIDE_INVOICE", "待审核发票"),
        INVOICE_VERIFY_SUCCESS("INVOICE_VERIFY_SUCCESS", "待出款"),
        INVOICE_VERIFY_FAIL("INVOICE_VERIFY_FAIL", "待提交发票"),
        LAST_VERIFY_SUCCESS("LAST_VERIFY_SUCCESS", "终审成功"),
        LAST_VERIFY_FAIL("LAST_VERIFY_FAIL", "终审失败"),
        HAS_PAY("HAS_PAY", "已完成"),
        HAS_MARK_HC("HAS_MARK_HC", "已失败"),
        HC("HC", "红冲");

        private final String key;
        private final String value;

        public String getKey() {
            return key;
        }

        public String getValue() {
            return value;
        }

        AcctDetailStatus(String key, String value) {

            this.key = key;
            this.value = value;
        }

        /**
         * 通过key得到值
         */
        public static String getValueByKey(String key) {
            for (AcctDetailStatus model : AcctDetailStatus.values()) {
                if (model.getKey().equals(key)) {
                    return model.value;
                }
            }
            return null;
        }
    }

    /**
     * 前端用户账户详情状态
     * 待初审、初审成功/失败、已签合同、已提供发票、
     * 发票审核成功/失败、终审成功/失败 、
     * 已出款、已标记红冲、红冲
     */
    public enum FrontAcctDetailStatus {

        NEED_VERIFY("NEED_VERIFY", "待审核"),
        NEED_PAY("NEED_PAY", "待出款"),
        HAS_PAY("HAS_PAY", "已完成"),
        VERIFY_FAIL("VERIFY_FAIL", "已失败");

        private final String key;
        private final String value;

        public String getKey() {
            return key;
        }

        public String getValue() {
            return value;
        }

        FrontAcctDetailStatus(String key, String value) {

            this.key = key;
            this.value = value;
        }

        /**
         * 通过key得到值
         */
        public static String getValueByKey(String key) {
            for (FrontAcctDetailStatus model : FrontAcctDetailStatus.values()) {
                if (model.getKey().equals(key)) {
                    return model.value;
                }
            }
            return null;
        }
    }

    /**
     * 初审状态（下拉列表用）
     */
    public enum FirstVerifyWithdrawStatus {

        INIT("INIT", "待初审"),
        FIRST_VERIFY_FAIL("FIRST_VERIFY_FAIL", "初审拒绝"),
        NEED_VERIFY_INVOICE("NEED_VERIFY_INVOICE", "待审核发票");

        private final String key;
        private final String value;

        public String getKey() {
            return key;
        }

        public String getValue() {
            return value;
        }

        FirstVerifyWithdrawStatus(String key, String value) {
            this.key = key;
            this.value = value;
        }

        /**
         * 通过key得到值
         */
        public static String getValueByKey(String key) {
            for (FirstVerifyWithdrawStatus model : FirstVerifyWithdrawStatus.values()) {
                if (model.getKey().equals(key)) {
                    return model.value;
                }
            }
            return null;
        }
    }

    /**
     * 发票审核状态（下拉列表用）
     */
    public enum InvoiceVerifyWithdrawStatus {

        HAS_PROVIDE_INVOICE("HAS_PROVIDE_INVOICE", "待审核"),
        INVOICE_VERIFY_SUCCESS("INVOICE_VERIFY_SUCCESS", "已通过"),
        INVOICE_VERIFY_FAIL("INVOICE_VERIFY_FAIL", "已拒绝"),;

        private final String key;
        private final String value;

        public String getKey() {
            return key;
        }

        public String getValue() {
            return value;
        }

        InvoiceVerifyWithdrawStatus(String key, String value) {
            this.key = key;
            this.value = value;
        }

        /**
         * 通过key得到值
         */
        public static String getValueByKey(String key) {
            for (InvoiceVerifyWithdrawStatus model : InvoiceVerifyWithdrawStatus.values()) {
                if (model.getKey().equals(key)) {
                    return model.value;
                }
            }
            return null;
        }
    }

    /**
     * 发票类型
     * 纸质、电子
     */
    public enum InvoiceType {

        PAPER("PAPER", "纸质"),
        IMAGE("IMAGE", "电子");

        private final String key;
        private final String value;

        public String getKey() {
            return key;
        }

        public String getValue() {
            return value;
        }

        InvoiceType(String key, String value) {

            this.key = key;
            this.value = value;
        }

        /**
         * 通过key得到值
         */
        public static String getValueByKey(String key) {
            for (InvoiceType model : InvoiceType.values()) {
                if (model.getKey().equals(key)) {
                    return model.value;
                }
            }
            return null;
        }
    }

    /**
     * 账户类型
     */
    public enum AcctType {
        /**
         * 个人
         */
        USER("user", "个人"),
        /**
         * 公司
         */
        COMPANY("company", "企业"),
        /**
         * 机构
         */
        AGENCY("agency", "机构"),
        /**
         * 机构
         */
        SHOP("shop", "店铺");

        private final String key;
        private final String value;

        public String getKey() {
            return key;
        }

        public String getValue() {
            return value;
        }

        AcctType(String key, String value) {

            this.key = key;
            this.value = value;
        }

        /**
         * 通过key得到值
         */
        public static String getValueByKey(String key) {
            for (AcctType model : AcctType.values()) {
                if (model.getKey().equals(key)) {
                    return model.value;
                }
            }
            return null;
        }
    }

    /**
     * 用户浏览类型
     * 活动类型auction拍卖enrolling招商disposal处置
     */
    public enum ViewType {

        AUCTION("AUCTION", "拍卖"),
        ENROLLING("ENROLLING", "预招商"),
        DISPOSAL("DISPOSAL", "处置服务");

        private final String key;
        private final String value;

        public String getKey() {
            return key;
        }

        public String getValue() {
            return value;
        }

        ViewType(String key, String value) {

            this.key = key;
            this.value = value;
        }

        /**
         * 通过key得到值
         */
        public static String getValueByKey(String key) {
            for (AcctType model : AcctType.values()) {
                if (model.getKey().equals(key)) {
                    return model.value;
                }
            }
            return null;
        }
    }

}
