package com.winback.arch.common.enums;

/**
 * 描述：发送邮件枚举类
 * <p>
 * 作者： wuchuanqi
 * 版本： 1.0.0
 * 时间： 2018/11/06 9:51
 */
public class EmailEnum {



    /**
     * 业务模板
     */
    public enum BUS_TEMPLATE {

        DEMO("1", "模板名称","模板的参数"),

         ;
        private final String key;
        private final String value;
        private final String param;


        public String getKey() {
            return key;
        }

        public String getValue() {
            return value;
        }

        public String getParam() {
            return param;
        }

        BUS_TEMPLATE(String key, String value,String param) {

            this.key = key;
            this.value = value;
            this.param = param;

        }

        /**
         * 通过值得到key
         */
        public static String getKeyByValue(String value) {
            for (BUS_TEMPLATE model : BUS_TEMPLATE.values()) {
                if (model.getKey().equals(value)) {
                    return model.value;
                }
            }
            return null;
        }
    }


    /**
     * 业务类型
     */
    public enum BUS_TYPE {

        BUSINESS("1", "业务邮件"),

        EXCEPTION("2", "系统异常邮件"),;


        private final String key;
        private final String value;

        public String getKey() {
            return key;
        }

        public String getValue() {
            return value;
        }

        BUS_TYPE(String key, String value) {

            this.key = key;
            this.value = value;
        }

        /**
         * 通过值得到key
         */
        public static String getKeyByValue(String value) {
            for (BUS_TYPE model : BUS_TYPE.values()) {
                if (model.getKey().equals(value)) {
                    return model.value;
                }
            }
            return null;
        }
    }


    /**
     * 系统模块类型
     */
    public enum MODULE_TYPE {

        CASE("CASE", "【案件模块】","1"),

        USER("USER", "【用户模块】","2"),

        PAY("PAY", "【支付模块】","3"),

        CHECK("CHECK", "【企查查】","4"),

        SMS("SMS", "【短信模块】","5"),

        CONTRACT("CONTRACT", "【合同模块】","6"),

        OTHER("OTHER", "【其他模块】","7"),

        APPLET("APPLET", "【小程序模块】","8"),

        TRY_CATCH("TRY_CATCH", "【try/catch模块】","9"),

        PUSH("PUSH", "【推送模块】","10"),

        EMAIL("EMAIL", "【邮件模块】","11")
        ;

        private final String key;
        private final String value;
        private final String type;

        public String getType() {
            return type;
        }

        public String getKey() {
            return key;
        }

        public String getValue() {
            return value;
        }

        MODULE_TYPE(String key, String value,String type) {

            this.key = key;
            this.value = value;
            this.type = type;
        }

        /**
         * 通过值得到key
         */
        public static String getValueByKey(String key) {
            for (MODULE_TYPE model : MODULE_TYPE.values()) {
                if (model.getKey().equals(key)) {
                    return model.value;
                }
            }
            return null;
        }
    }

    /**
     * 重要等级
     */
    public enum IMPORTANT_LEVEL {


        SERIOUS("SERIOUS", "【严重】"),

        MINOR("MINOR", "【次要】"),

        OTHER("OTHER", "【其他】"),

        IGNORE("IGNORE", "【提示】"),;


        private final String key;
        private final String value;

        public String getKey() {
            return key;
        }

        public String getValue() {
            return value;
        }

        IMPORTANT_LEVEL(String key, String value) {

            this.key = key;
            this.value = value;
        }

        /**
         * 通过值得到key
         */
        public static String getKeyByValue(String value) {
            for (IMPORTANT_LEVEL model : IMPORTANT_LEVEL.values()) {
                if (model.getKey().equals(value)) {
                    return model.value;
                }
            }
            return null;
        }
    }




    /**
     * 发送的环境
     */
    public enum ENVIRONMENT {

        ONLINE("ONLINE", "【生产环境】"),

        TEST("TEST", "【测试环境】"),;


        private final String key;
        private final String value;

        public String getKey() {
            return key;
        }

        public String getValue() {
            return value;
        }

        ENVIRONMENT(String key, String value) {

            this.key = key;
            this.value = value;
        }

        /**
         * 通过值得到key
         */
        public static String getKeyByValue(String value) {
            for (ENVIRONMENT model : ENVIRONMENT.values()) {
                if (model.getKey().equals(value)) {
                    return model.value;
                }
            }
            return null;
        }
    }



    /**
     * 平台
     */
    public enum WEB {

        APP("APP", "【APP】"),

        ADMIN("ADMIN", "【管理后台】"),

        APPLET("APPLET", "【小程序】"),;


        private final String key;
        private final String value;

        public String getKey() {
            return key;
        }

        public String getValue() {
            return value;
        }

        WEB(String key, String value) {

            this.key = key;
            this.value = value;
        }

        /**
         * 通过值得到key
         */
        public static String getKeyByValue(String value) {
            for (WEB model : WEB.values()) {
                if (model.getKey().equals(value)) {
                    return model.value;
                }
            }
            return null;
        }
    }


    /**
     * 发送的环境
     */
    public enum CommonEmailTheme {

        SYSTEM_WAITERS("赢回来客服咨询", "<html lang='zh-CN'><head ><meta charset='utf-8'>\n" +
                "</head><body>向您咨询了一条信息,请您及时回复\n" +
                "<a href='DEFAULTSYSTEMWAITERURL'>【前去回复】</a></body></html>");

        private final String title;
        private final String content;

        public String getKey() {
            return title;
        }

        public String getValue() {
            return content;
        }

        CommonEmailTheme(String title, String content) {

            this.title = title;
            this.content = content;
        }

        /**
         * 通过值得到key
         */
        public static String getValueByKey(String key) {
            for (CommonEmailTheme model : CommonEmailTheme.values()) {
                if (model.getKey().equals(key)) {
                    return model.content;
                }
            }
            return null;
        }
    }

}