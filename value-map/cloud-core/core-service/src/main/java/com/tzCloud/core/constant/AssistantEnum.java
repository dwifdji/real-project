package com.tzCloud.core.constant;

/**
 * @author xdrodger
 * @Title: AssistantEnum
 * @ProjectName tzCloud-parent
 * @Description:
 * @date 2019/6/19 15:56
 */
public class AssistantEnum {

    public enum ConfigType {
        /**
         * 热词
         */
        HotWord("hot_word", "热词"),
        /**
         * 热词
         */
        GuidedCase("guided_case", "指导性案列"),
        ;

        private final String key;
        private final String value;

        public String getKey() {
            return key;
        }

        public String getValue() {
            return value;
        }

        ConfigType(String key, String value) {

            this.key = key;
            this.value = value;
        }

        /**
         * 通过值得到key
         */
        public static String getValueByKey(String key) {
            for (ConfigType model : ConfigType.values()) {
                if (model.getKey().equals(key)) {
                    return model.value;
                }
            }
            return null;
        }
    }

    public enum SearchType {
        /**
         * 案件
         */
        Case("1", "案件"),
        /**
         * 律师
         */
        Lawyer("2", "律师"),
        /**
         * 律所
         */
        LawFirm("3", "律所"),
        /**
         * 法院
         */
        Court("4", "法院"),
        ;

        private final String key;
        private final String value;

        public String getKey() {
            return key;
        }

        public String getValue() {
            return value;
        }

        SearchType(String key, String value) {

            this.key = key;
            this.value = value;
        }

        /**
         * 通过值得到key
         */
        public static String getValueByKey(String key) {
            for (SearchType model : SearchType.values()) {
                if (model.getKey().equals(key)) {
                    return model.value;
                }
            }
            return null;
        }
    }
}
