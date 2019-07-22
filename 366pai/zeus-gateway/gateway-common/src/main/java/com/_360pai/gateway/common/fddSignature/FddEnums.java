package com._360pai.gateway.common.fddSignature;

/**
 * 描述：法大大枚举类
 * <p>
 * 作者： wuchuanqi
 * 版本： 1.0.0
 * 时间： 2018/9/1 10:25
 */
public class FddEnums {


    /**
     *
     * 签章类型枚举类
     */
    public  enum SING_TYPE
    {
        DELEGATION("1", "委托协议"),
        ADDITIONAL("2", "委托补充协议"),
        DEAL("3", "成交确认协议"),
        ENROLLING_DELEGATION("4", "预招商委托协议"),
        BANK_DELEGATION("5", "银行类委托模板"),
        OFFLINE_DELEGATION("6", "线下委托协议"),
        ZHAIQUAN_DELEGATION("7", "债权预招商委托协议"),
        WUQUAN_DELEGATION("8", "物权预招商委托协议"),

        JINDIAO_DELEGATION("9", "尽调报告销售授权委托书"),
        JINZI_DELEGATION("10", "尽职调查授权委托书"),
        SANFANG_DELEGATION("11", "三方协议"),
        SERVICE_ADVISORY("12", "咨询服务合同"),

        BANK_OFFLINE_DELEGATION("13", "线下银行类委托协议"),

        LEASE_DELEGATION("14", "租赁权委托合同"),

        LEASE_DEAL("15", "租赁权成交确认书"),


        LEASE_WITHOUT_LICENSE("16", "租赁协议无证版"),

        LEASE_HAS_LICENSE("17", "租赁协议有证版"),

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
        private SING_TYPE(String type, String typeName)
        {
            this.type = type;
            this.typeName = typeName;
        }

        //根据code 获取描述
        public static String getDesc(String type) {
            for (SING_TYPE c : SING_TYPE.values()) {
                if (c.getType().equals(type)) {
                    return c.getTypeName();
                }
            }
            return null;
        }
    }


    /**
     *
     * 签章类型枚举类
     */
    public  enum SING_TYPE_DB
    {
        DELEGATION("1", "DELEGATION"),
        ADDITIONAL("2", "ADDITIONAL"),
        DEAL("3", "DEAL"),
        ENROLLING_DELEGATION("4", "ENROLLING_DELEGATION"),
        BANK_DELEGATION("5", "BANK_DELEGATION"),
        OFFLINE_DELEGATION("6", "OFFLINE_DELEGATION"),
        ZHAIQUAN_DELEGATION("7", "ZHAIQUAN_DELEGATION"),
        WUQUAN_DELEGATION("8", "WUQUAN_DELEGATION"),
        JINDIAO_DELEGATION("9", "JINDIAO_DELEGATION"),
        JINZI_DELEGATION("10", "JINZI_DELEGATION"),
        SANFANG_DELEGATION("11", "SANFANG_DELEGATION"),
        SERVICE_ADVISORY("12", "SERVICE_ADVISORY"),
        BANK_OFFLINE_DELEGATION("13", "BANK_OFFLINE_DELEGATION"),
        LEASE_DELEGATION("14", "LEASE_DELEGATION"),
        LEASE_DEAL("15", "LEASE_DEAL"),
        LEASE_WITHOUT_LICENSE("16", "LEASE_WITHOUT_LICENSE"),
        LEASE_HAS_LICENSE("17", "LEASE_HAS_LICENSE"),



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
        private SING_TYPE_DB(String type, String typeName)
        {
            this.type = type;
            this.typeName = typeName;
        }

        //根据code 获取描述
        public static String getDesc(String type) {
            for (SING_TYPE_DB c : SING_TYPE_DB.values()) {
                if (c.getType().equals(type)) {
                    return c.getTypeName();
                }
            }
            return null;
        }
    }



    /**
     *
     * 签章角色类型
     */
    public  enum SING_ROLE_TYPE
    {
        SELLER("1", "委托人角色"),
        BUYER("2", "竞买人角色"),
        AGENCY("3", "送拍机构"),
        JOINT_AGENCY("4", "连拍机构"),
        WEB("5", "平台");
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
        private SING_ROLE_TYPE(String type, String typeName)
        {
            this.type = type;
            this.typeName = typeName;
        }

        //根据code 获取描述
        public static String getDesc(String type) {
            for (SING_ROLE_TYPE c : SING_ROLE_TYPE.values()) {
                if (c.getType().equals(type)) {
                    return c.getTypeName();
                }
            }
            return null;
        }
    }



    /**
     *
     * 是否自动签章枚举
     */
    public  enum SING_AUTO
    {
        AUTO("1", "自动签章"),
        NOT_AUTO("2", "手动签章");

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
        private SING_AUTO(String type, String typeName)
        {
            this.type = type;
            this.typeName = typeName;
        }

        //根据code 获取描述
        public static String getDesc(String type) {
            for (SING_AUTO c : SING_AUTO.values()) {
                if (c.getType().equals(type)) {
                    return c.getTypeName();
                }
            }
            return null;
        }
    }



    /**
     *
     * 签章结果枚举
     */
    public  enum SING_RESULT
    {
        SUCCESS("3000", "签章成功"),
        FAIL("3001", "签章失败");

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
        private SING_RESULT(String type, String typeName)
        {
            this.type = type;
            this.typeName = typeName;
        }

        //根据code 获取描述
        public static String getDesc(String type) {
            for (SING_RESULT c : SING_RESULT.values()) {
                if (c.getType().equals(type)) {
                    return c.getTypeName();
                }
            }
            return null;
        }
    }



    /**
     *
     * 签章号前缀生成规则
     */
    public  enum TRANSACTION_TYPE
    {
        SELLER("seller", "委托人签章交易号前缀"),
        BUYER("buyer", "竞买人签章前缀"),
        WEB("web", "平台签章前缀"),
        AGENCY("agency", "机构签章前缀"),
        JOINT("joint", "连拍机构签章前缀"),
        CONTRACT("num", "生成合同号前缀");


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
        private TRANSACTION_TYPE(String type, String typeName)
        {
            this.type = type;
            this.typeName = typeName;
        }

        //根据code 获取描述
        public static String getDesc(String type) {
            for (TRANSACTION_TYPE c : TRANSACTION_TYPE.values()) {
                if (c.getType().equals(type)) {
                    return c.getTypeName();
                }
            }
            return null;
        }
    }

}
