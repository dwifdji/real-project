package com._360pai.core.common.constants;

/**
 * @author xdrodger
 * @Title: NumberJumpEnum
 * @ProjectName dev2-zeus
 * @Description:
 * @date 2019-04-28 19:39
 */
public class CalculatorEnum {

    public enum Type {
        /**
         * 本息计算器
         */
        PrincipalInterest("0", "本息计算器"),
        /**
         * 债权计算器
         */
        Debt("1", "债权计算器");

        private final String key;
        private final String value;

        public String getKey() {
            return key;
        }

        public String getValue() {
            return value;
        }

        Type(String key, String value) {
            this.key = key;
            this.value = value;
        }

        /**
         * 通过值得到key
         */
        public static String getValueByKey(String key) {
            for (Type model : Type.values()) {
                if (model.getKey().equals(key)) {
                    return model.value;
                }
            }
            return null;
        }
    }

    public enum BenchmarkInterestRateSource {
        /**
         * 本息计算器
         */
        Court("0", "法院"),
        /**
         * 债权计算器
         */
        Bank("1", "银行");

        private final String key;
        private final String value;

        public String getKey() {
            return key;
        }

        public String getValue() {
            return value;
        }

        BenchmarkInterestRateSource(String key, String value) {
            this.key = key;
            this.value = value;
        }

        /**
         * 通过值得到key
         */
        public static String getValueByKey(String key) {
            for (BenchmarkInterestRateSource model : BenchmarkInterestRateSource.values()) {
                if (model.getKey().equals(key)) {
                    return model.value;
                }
            }
            return null;
        }
    }
}
