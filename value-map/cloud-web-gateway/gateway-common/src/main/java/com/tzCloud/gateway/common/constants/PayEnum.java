package com.tzCloud.gateway.common.constants;

/**
 * 描述：支付枚举
 * <p>
 * 作者： wuchuanqi
 * 版本： 1.0.0
 * 时间： 2019/1/17 10:51
 */
public class PayEnum {


    /**
     *
     *查询枚举类转换
     */
    public  enum PAY_TYPE
    {
        ALI_PAY("1", "支付宝"),
        WX_PAY("2", "微信支付"),
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
        PAY_TYPE(String type, String typeName)
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

        //根据值获取type
        public static String getType(String typeName) {
            for (PAY_TYPE c : PAY_TYPE.values()) {
                if (c.getTypeName().equals(typeName)) {
                    return c.getType();
                }
            }
            return null;
        }
    }





    /**
     *
     *支付业务
     */
    public  enum BUSINESS_TYPE
    {
        APP_PAY("1", "APP支付"),
        APPLET_PAY("2", "小程序支付"),
        SCAN_PAY("3", "扫码支付");
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
        BUSINESS_TYPE(String type, String typeName)
        {
            this.type = type;
            this.typeName = typeName;
        }

        //根据code 获取描述
        public static String getDesc(String type) {
            for (BUSINESS_TYPE c : BUSINESS_TYPE.values()) {
                if (c.getType().equals(type)) {
                    return c.getTypeName();
                }
            }
            return null;
        }

        //根据值获取type
        public static String getType(String typeName) {
            for (BUSINESS_TYPE c : BUSINESS_TYPE.values()) {
                if (c.getTypeName().equals(typeName)) {
                    return c.getType();
                }
            }
            return null;
        }
    }




    /**
     *
     *订单号前缀
     */
    public  enum ORDER_MARK
    {
        AlI_APP_PAY("aliApp", "阿里APP支付"),
        WX_APP_PAY("wxApp", "微信app"),
        WX_APPLET_PAY("wxApplet", "小程序支付");


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
        ORDER_MARK(String type, String typeName)
        {
            this.type = type;
            this.typeName = typeName;
        }

        //根据code 获取描述
        public static String getDesc(String type) {
            for (ORDER_MARK c : ORDER_MARK.values()) {
                if (c.getType().equals(type)) {
                    return c.getTypeName();
                }
            }
            return null;
        }

        //根据值获取type
        public static String getType(String typeName) {
            for (ORDER_MARK c : ORDER_MARK.values()) {
                if (c.getTypeName().equals(typeName)) {
                    return c.getType();
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


}
