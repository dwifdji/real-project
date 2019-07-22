package com.tzCloud.gateway.common.constants;

/**
 * 描述：支付枚举类
 * <p>
 * 作者： wuchuanqi
 * 版本： 1.0.0
 * 时间： 2018/9/1 10:25
 */
public class PayEnums {


    /**
     *
     * 支付类型枚举
     */
    public  enum PAY_TYPE
    {
        SCAN_PAY("SCAN_PAY", "微信扫码支付"),
        APPLET_PAY("APPLET_PAY", "微信小程序支付"),
        LOCK_DEPOSIT("LOCK_DEPOSIT", "保证金锁定"),
        RELEASE_DEPOSIT("RELEASE_DEPOSIT", "保证金释放"),
        TRANSFER_DEPOSIT("TRANSFER_DEPOSIT", "保证金支付"),
        DIRECT_PAY("DIRECT_PAY", "直接支付"),
        CHANNEL_PAY("CHANNEL_PAY", "通道支付"),
        BATCH_PAY("BATCH_PAY", "批量支付"),
        ;

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
        private PAY_TYPE(String type, String typeName)
        {
            this.type = type;
            this.typeName = typeName;
        }

        //根据code 获取描述
        public static String getDesc(String type) {
            for (PAY_TYPE c : PAY_TYPE.values()) {
                if (c.getType().equals(type)) {
                    return c.getTypeName();
                }
            }
            return null;
        }
    }


    /**
     *
     * 东方付通支付接口码枚举
     */
    public  enum DFFT_PAY_CODE
    {
        PAY_TYPE_LOCK_DEPOSIT("03031", "保证金锁定"),
        PAY_TYPE_RELEASE_DEPOSIT("03041", "保证金释放"),
        PAY_TYPE_TRANSFER_DEPOSIT("03051", "保证金锁定支付"),
        PAY_TYPE_PAY("01010", "直接支付"),
        PAY_TYPE_CHANNEL("01051", "通道支付");

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
        private DFFT_PAY_CODE(String type, String typeName)
        {
            this.type = type;
            this.typeName = typeName;
        }

        //根据code 获取描述
        public static String getDesc(String type) {
            for (DFFT_PAY_CODE c : DFFT_PAY_CODE.values()) {
                if (c.getType().equals(type)) {
                    return c.getTypeName();
                }
            }
            return null;
        }
    }



    /**
     *
     * 金额锁定标志
     */
    public  enum LOCK_TAG
    {
        DIRECT_PAY("0", "直接支付不锁定"),
        LOCK_PAY("1", "支付但是锁定状态");

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
        private LOCK_TAG(String type, String typeName)
        {
            this.type = type;
            this.typeName = typeName;
        }

        //根据code 获取描述
        public static String getDesc(String type) {
            for (LOCK_TAG c : LOCK_TAG.values()) {
                if (c.getType().equals(type)) {
                    return c.getTypeName();
                }
            }
            return null;
        }
    }



    /**
     *
     * 支付到平台账号还是用户账号
     */
    public  enum PAY_TO
    {
        PAY_TO_WEB("1", "支付到平台，不用传接收方账号"),
        PAY_TO_MEM("2", "支付到用户，需要填收款方信息");

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
        private PAY_TO(String type, String typeName)
        {
            this.type = type;
            this.typeName = typeName;
        }

        //根据code 获取描述
        public static String getDesc(String type) {
            for (PAY_TO c : PAY_TO.values()) {
                if (c.getType().equals(type)) {
                    return c.getTypeName();
                }
            }
            return null;
        }
    }



    /**
     *
     * 支付金额退回标志
     */
    public  enum BACK_TAG
    {
        NOT_BACK("0", "不退回"),
        BACK("1", "退回标志");

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
        private BACK_TAG(String type, String typeName)
        {
            this.type = type;
            this.typeName = typeName;
        }

        //根据code 获取描述
        public static String getDesc(String type) {
            for (BACK_TAG c : BACK_TAG.values()) {
                if (c.getType().equals(type)) {
                    return c.getTypeName();
                }
            }
            return null;
        }
    }





    /**
     *
     * 付款方是谁
     */
    public  enum WHO_PAY
    {
        WEB_PAY("1", "平台账户付款"),
        MEM_PAY("2", "普通用户付款");

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
        private WHO_PAY(String type, String typeName)
        {
            this.type = type;
            this.typeName = typeName;
        }

        //根据code 获取描述
        public static String getDesc(String type) {
            for (WHO_PAY c : WHO_PAY.values()) {
                if (c.getType().equals(type)) {
                    return c.getTypeName();
                }
            }
            return null;
        }
    }



    /**
     *
     * 支付业务类型枚举
     */
    public  enum PAY_BUS_CODE
    {

        LOCK_DEPOSIT("LOCK_DEPOSIT", "拍卖保证金锁定"),
        RELEASE_DEPOSIT("RELEASE_DEPOSIT", "拍卖保证金释放"),
        ENROLLING_LOCK_DEPOSIT("ENROLLING_LOCK_DEPOSIT", "预招商保证金锁定"),
        ENROLLING_RELEASE_DEPOSIT("ENROLLING_RELEASE_DEPOSIT", "预招商保证金释放"),
        BALANCE_PAY("BALANCE_PAY", "支付尾款"),
        CHANNEL_PAY("CHANNEL_PAY", "通道支付"),
        CHANNEL_LOCK_DEPOSIT("CHANNEL_LOCK_DEPOSIT", "通道支付保证金锁定"),
        CHANNEL_RELEASE_DEPOSIT("CHANNEL_RELEASE_DEPOSIT", "通道支付保证释放"),
        SCAN_PAY("SCAN_PAY", "微信扫码支付"),
        DEPOSIT_PAY("DEPOSIT_PAY", "保证金支付"),
        ENROLLING_COMMISSION_PAY("ENROLLING_COMMISSION_PAY", "抵押物预招商佣金支付"),
        ENROLLING_COMMISSION_SELLER_PAY("ENROLLING_COMMISSION_SELLER_PAY", "送拍机构分润"),
        ENROLLING_COMMISSION_BUYER_PAY("ENROLLING_COMMISSION_BUYER_PAY", "承拍机构分润"),


        /**
         * 服务需求支付 （资产大买办 配资乐 服务处置）
         */
        SERVICE_REQUIREMENT_PAY("SERVICE_REQUIREMENT_PAY", "服务需求支付"),
        BATCH_PAY("BATCH_PAY", "尾款保证金批量支付"),
        ENROLLING_SUBMIT_PAY("ENROLLING_SUBMIT_PAY", "预招商发布支付"),
        APPLET_OPEN_PAY("APPLET_OPEN_PAY", "小程序开店费"),

        APPLET_CALCULATOR_PAY("APPLET_CALCULATOR_PAY", "计算器费用"),


        MEMBERSHIP_FEE_PAY("MEMBERSHIP_FEE_PAY","会员费用支付"),

        ;



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
        private PAY_BUS_CODE(String type, String typeName)
        {
            this.type = type;
            this.typeName = typeName;
        }

        //根据code 获取描述
        public static String getDesc(String type) {
            for (PAY_BUS_CODE c : PAY_BUS_CODE.values()) {
                if (c.getType().equals(type)) {
                    return c.getTypeName();
                }
            }
            return null;
        }
    }




    /**
     *
     * 支付订单生成号前端标识
     */
    public  enum PAY_ORDER_MARK
    {
        WX("wx", "微信扫码支付订单号前缀"),
        APPLET("APPLET", "微信小程序支付"),
        WX_ORDER("wxOrder", "微信扫码支付order号前缀"),
        LOCK("lock", "锁定支付订单号前缀"),
        RELEASE("release", "保证金释放订单号前缀"),
        CHANNEL("channel", "通道支付订单号前缀"),
        DIRECT("direct", "直接支付订单号前缀"),
        TRANSFER("transfer", "锁定支付订单号前缀"),
        TRAN_RELEASE("tranRelease", "保证金支付解锁订单前缀"),
        ORDER("order", "内部订单号");

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
        private PAY_ORDER_MARK(String type, String typeName)
        {
            this.type = type;
            this.typeName = typeName;
        }

        //根据code 获取描述
        public static String getDesc(String type) {
            for (PAY_ORDER_MARK c : PAY_ORDER_MARK.values()) {
                if (c.getType().equals(type)) {
                    return c.getTypeName();
                }
            }
            return null;
        }
    }



    /**
     *
     * 微信支付状态返回
     */
    public  enum PAY_QUERY_STATUS
    {
        SUCCESS("SUCCESS", "000000"),
        REFUND("REFUND", "500000"),//转入退款
        NOTPAY("NOTPAY", "500001"),//未支付
        CLOSED("CLOSED", "500002"),//已关闭
        REVOKED("REVOKED", "500003"),//已撤销（刷卡支付)
        USERPAYING("USERPAYING", "500004"),//用户支付中
        PAYERROR("PAYERROR", "111111");//支付失败(其他原因，如银行返回失败)

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
        private PAY_QUERY_STATUS(String type, String typeName)
        {
            this.type = type;
            this.typeName = typeName;
        }

        //根据code 获取描述
        public static String getDesc(String type) {
            for (PAY_QUERY_STATUS c : PAY_QUERY_STATUS.values()) {
                if (c.getType().equals(type)) {
                    return c.getTypeName();
                }
            }
            return null;
        }
    }


    /**
     *
     * 是否是页面跳转支付
     */
    public  enum JUMP_PAY_TYPE
    {
        JUMP_PAY("1", "页面跳转支付"),
        AUTO_PAY("2", "直接支付不跳转页面");

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
        private JUMP_PAY_TYPE(String type, String typeName)
        {
            this.type = type;
            this.typeName = typeName;
        }

        //根据code 获取描述
        public static String getDesc(String type) {
            for (JUMP_PAY_TYPE c : JUMP_PAY_TYPE.values()) {
                if (c.getType().equals(type)) {
                    return c.getTypeName();
                }
            }
            return null;
        }
    }




}
