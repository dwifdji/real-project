package com.tzCloud.arch.common.enums;

public class HouseEnum {

    public enum UseType{
        NORMAL_HOUSING("1", "普通住宅"),
        COMMERCIAL_OFFICE("2", "商用办公类"),
        OTHER("4", "旧式里弄"),
        LAND("5", "四合院"),
        INDUSTRIAL_PLANTS("3", "工业厂房");

        private String key;
        private String value;

        UseType(String key, String value) {
                this.key = key;
                this.value = value;
            }

        public static String getValueByKey(String key) {
            UseType[] values = UseType.values();
                for (UseType status : values) {
                    if (status.key.equals(key)) {
                        return status.value;
                    }
                }
                return null;
        }

        public String getKey() {
            return key;
        }

        public String getValue() {
            return value;
        }
    }


    public enum MonthLimit{
        HALF_YEAR("HALF_YEAR", -6),
        SIX_MONTH("SIX_MONTH", -5),
        ONE_YEAR("ONE_YEAR", -12);

        private String key;
        private Integer value;

        MonthLimit(String key, Integer value) {
            this.key = key;
            this.value = value;
        }

        public static Integer getValueByKey(String key) {

            for (MonthLimit status : MonthLimit.values()) {
                if (status.key.equals(key)) {
                    return status.value;
                }
            }
            return null;
        }

        public String getKey() {
            return key;
        }

        public Integer getValue() {
            return value;
        }
    }
}
