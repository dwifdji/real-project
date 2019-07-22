package com.winback.arch.common.enums;

/**
 * 描述：消息模板枚举列
 * <p>
 * 作者： wuchuanqi
 * 版本： 1.0.0
 * 时间： 2018/8/21 23:51
 */
public class MessageTemplateEnum {


    /**
     *
     * 消息大组类型
     */
    public  enum GROUP_TYPE
    {
        CONTRACT_DOWNLOAD("1", "合同范本下载"),
        FRANCHISEE_APPLICATION("2", "加盟商申请"),
        QUICK_PRESS("3", "快速发布"),
        PUBLISHED_CASE ("4", "发布案件"),
        CASE_LIBRARY("5", "案件库（律师承接案件）"),
        USER_CENTER("6", "用户中心（律师资质申请）"),
        CASE_PROGRESS("7", "案件进展"),
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
        GROUP_TYPE(String type, String typeName)
        {
            this.type = type;
            this.typeName = typeName;
        }

        //根据code 获取描述
        public static String getDesc(String type) {
            for (GROUP_TYPE c : GROUP_TYPE.values()) {
                if (c.getType().equals(type)) {
                    return c.getTypeName();
                }
            }
            return null;
        }

        //根据值获取type
        public static String getType(String typeName) {
            for (GROUP_TYPE c : GROUP_TYPE.values()) {
                if (c.getTypeName().equals(typeName)) {
                    return c.getType();
                }
            }
            return null;
        }
    }




    /**
     *
     * 消息大组类型
     */
    public  enum SEND_TYPE
    {
        PUSH_APP("1", "推送"),
        EMAIL("2", "邮件"),
        SMS("3", "短信")

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
        SEND_TYPE(String type, String typeName)
        {
            this.type = type;
            this.typeName = typeName;
        }

        //根据code 获取描述
        public static String getDesc(String type) {
            for (SEND_TYPE c : SEND_TYPE.values()) {
                if (c.getType().equals(type)) {
                    return c.getTypeName();
                }
            }
            return null;
        }

        //根据值获取type
        public static String getType(String typeName) {
            for (SEND_TYPE c : SEND_TYPE.values()) {
                if (c.getTypeName().equals(typeName)) {
                    return c.getType();
                }
            }
            return null;
        }
    }


    /**
     *
     * 推送的类型
     */
    public  enum TYPE
    {
        TYPE_1("1", "合同上新","{classifyName :合同分类的名称}"),
        TYPE_2("2", "支付成功",null),
        TYPE_3("3", null,null),
        TYPE_4("4", "申请成功",null),//申请电子发票发布时，推送消息提醒
        TYPE_5("5", "申请成功",null),//发起加盟商申请，推送消息提醒
        TYPE_6("6", "发起申加盟商请，邮件运营人员，推送消息提醒",null),
        TYPE_7("7", "审核通过",null),//加盟商申请审核通过，推送消息提示
        TYPE_8("8", null,"{userName:下线客户的名称 }"),//加盟商的客户成功发布按键，推送消息提示
        TYPE_9("9", null,"{userName:下线客户的名称 }"),//加盟商的客户发布的案件胜诉，分润完成同时
        TYPE_10("10", "发布成功",null),//发布风险投
        TYPE_11("11", "发布风险投，邮件业务人员",null),
        TYPE_12("12", "审核通过",null),//风险投案件审核通过
        TYPE_13("13", "审核拒绝","{createTime :发布时间}"),
        TYPE_14("14", "发布成功",null),//发布联合执行
        TYPE_15("15", "发布联合执行，邮件业务人员",null),
        TYPE_16("16", "审核通过","{createTime :发布时间}"),//联合执行案件审核通过
        TYPE_17("17", "审核拒绝","{createTime :发布时间}"),//联合执行案件审核不通过
        TYPE_18("18", "发布成功",null),//发布委托执行
        TYPE_19("19", "发布委托执行，邮件业务人员",null),
        TYPE_20("20", "审核通过","{createTime :发布时间}"),//委托执行案件审核通过
        TYPE_21("21", "审核拒绝","{createTime :发布时间}"),//委托执行案件审核不通过
        TYPE_22("22", "发布成功",null),//发布诉讼执行
        TYPE_23("23", "发布诉讼执行，邮件业务人员",null),//
        TYPE_24("24", "审核通过","{createTime :发布时间}"),//诉讼执行案件审核通过
        TYPE_25("25", "审核拒绝","{createTime :发布时间}"),//诉讼执行案件审核不通过
        TYPE_26("26", "发布成功",null),//发布债权收购
        TYPE_27("27", "发布债权收购，邮件债权部人员",null),
        TYPE_28("28", "发布成功",null),//发布案件
        TYPE_29("29", "发布案件，邮件业务人员",null),
        TYPE_30("30", "审核通过","{createTime :发布时间}"),//案件审核通过
        TYPE_31("31", "审核拒绝","{createTime :发布时间}"),//案件审核不通过
        TYPE_32("32", "申请成功",null),//承接案件
        TYPE_33("33", "承接案件，邮件业务人员","{caseId :案件编号}"),
        TYPE_34("34", null,"{caseId :案件编号}"),//承接案件审核通过
        TYPE_35("35", "审核拒绝","{caseId :案件编号}"),//承接案件审核不通过
        TYPE_36("36", "申请成功","null"),//申请资质
        TYPE_37("37", "申请资质，邮件业务人员",null),
        TYPE_38("38", "审核通过",null),//资质审核通过
        TYPE_39("39", "审核拒绝",null),//资质审核不通过
        TYPE_40("40", "步骤更新",null),//步骤更新
        TYPE_41("41", "案件上新",null),//案件上新

        TYPE_42("42", "已委任律师",null),//已委任律师

        TYPE_43("43","审核通过",null),//线下律师签约
        TYPE_44("44","用户提交律师资质审核-后台人员",null),//用户提交律师资质审核-后台人员
        TYPE_45("45","用户发布案件-项目经理",null),//用户发布案件-项目经理
        TYPE_46("46","客服管理-赢回来客服","{partyName : 咨询人}"),//用户发布案件-项目经理
        ;
        private String type;
        private String typeName;
        private String params;



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

        public String getParams() {
            return params;
        }

        public void setParams(String params) {
            this.params = params;
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
        TYPE(String type, String typeName,String params)
        {
            this.type = type;
            this.typeName = typeName;
            this.params = params;

        }

        //根据code 获取描述
        public static String getDesc(String type) {
            for (TYPE c : TYPE.values()) {
                if (c.getType().equals(type)) {
                    return c.getTypeName();
                }
            }
            return null;
        }

        //根据值获取type
        public static String getType(String typeName) {
            for (TYPE c : TYPE.values()) {
                if (c.getTypeName().equals(typeName)) {
                    return c.getType();
                }
            }
            return null;
        }
    }




    /**
     * 小程序站内消息
     */
    public enum AppletType {
        /**
         * 合同上新
         */
        TYPE_1("1", "合同上新", "%s有新的合同范本可供购买使用"),
        /**
         * 订单将失效
         */
        TYPE_2("2", "系统消息", "您的购物订单{{orderNo}}尚未支付，5分钟后订单将失效。"),
        /**
         * 订单失效
         */
        TYPE_3("3", "系统消息", "您的购物订单已失效，可再次购买"),
        /**
         * 支付成功
         */
        TYPE_4("4", "系统消息", "您已完成支付，可在{{myOrders}}下载已购买合同"),
        /**
         * 合同下载成功
         */
        TYPE_5("5", "系统消息", "您的合同将在30分钟内以邮件形式发至您的箱；若超时未接收成功，请致电{{servicePhone}}"),
        /**
         * 开票申请提交成功
         */
        TYPE_6("6", "系统消息", "您的开票申请已收到，平台将在3个工作日内开具电子发票。"),

        ;

        private final String type;
        private final String title;
        private final String content;

        public String getType() {
            return type;
        }

        public String getTitle() {
            return title;
        }

        public String getContent() {
            return content;
        }

        AppletType(String type, String title, String content) {
            this.type = type;
            this.title = title;
            this.content = content;
        }
    }


}
