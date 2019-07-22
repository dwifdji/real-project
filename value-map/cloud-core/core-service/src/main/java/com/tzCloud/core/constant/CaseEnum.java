package com.tzCloud.core.constant;

import org.apache.commons.lang.StringUtils;

/**
 * @author xdrodger
 * @Title: CaseEnum
 * @ProjectName tzCloud-parent
 * @Description:
 * @date 2019-04-19 14:25
 */
public class CaseEnum {

    /**
     * 案件类型
     */
    public enum CaseType {
        /**
         * 刑事案件
         */
        CRIMINAL_CASE("1", "刑事"),
        /**
         * 民事案件
         */
        CIVIL_CASE("2", "民事"),
        /**
         * 行政案件
         */
        ADMINISTRATIVE_CASE("3", "行政"),
        /**
         * 赔偿案件
         */
        COMPENSATION_CASE("4", "赔偿"),
        /**
         * 执行案件
         */
        EXECUTION_CASE("5", "执行");

        private final String key;
        private final String value;

        public String getKey() {
            return key;
        }

        public String getValue() {
            return value;
        }

        CaseType(String key, String value) {

            this.key = key;
            this.value = value;
        }

        /**
         * 通过值得到key
         */
        public static String getValueByKey(String key) {
            for (CaseType model : CaseType.values()) {
                if (model.getKey().equals(key)) {
                    return model.value;
                }
            }
            return null;
        }
    }

    /**
     * 文书类型
     */
    public enum JudgementType {
        /**
         * 判决书
         */
        TYPE_1("1", "判决"),
        /**
         * 裁定书
         */
        TYPE_2("2", "裁定"),
        /**
         * 调解书
         */
        TYPE_3("3", "调解"),
        /**
         * 决定书
         */
        TYPE_4("4", "决定"),
        /**
         * 通知书
         */
        TYPE_5("5", "通知"),
        /**
         * 批复
         */
        TYPE_6("6", "批复"),
        /**
         * 答复
         */
        TYPE_7("7", "答复"),
        /**
         * 涵
         */
        TYPE_8("8", "涵"),
        /**
         * 令
         */
        TYPE_9("9", "令"),
        /**
         * 其他
         */
        TYPE_10("10", "其他");

        private final String key;
        private final String value;

        public String getKey() {
            return key;
        }

        public String getValue() {
            return value;
        }

        JudgementType(String key, String value) {

            this.key = key;
            this.value = value;
        }

        /**
         * 通过key得到值
         */
        public static String getValueByKey(String key) {
            for (JudgementType model : JudgementType.values()) {
                if (model.getKey().equals(key)) {
                    return model.value;
                }
            }
            return null;
        }

        /**
         * 通过值得到key（近似）
         */
        public static String getKeyBySimilarValue(String value) {
            if (StringUtils.isBlank(value)) {
                return JudgementType.TYPE_10.key;
            }
            if (value.contains(JudgementType.TYPE_1.value)) {
                return JudgementType.TYPE_1.key;
            } else if (value.contains(JudgementType.TYPE_2.value)) {
                return JudgementType.TYPE_2.key;
            } else if (value.contains(JudgementType.TYPE_3.value)) {
                return JudgementType.TYPE_3.key;
            } else if (value.contains(JudgementType.TYPE_4.value)) {
                return JudgementType.TYPE_4.key;
            } else if (value.contains(JudgementType.TYPE_5.value)) {
                return JudgementType.TYPE_5.key;
            } else if (value.contains(JudgementType.TYPE_6.value)) {
                return JudgementType.TYPE_6.key;
            } else if (value.contains(JudgementType.TYPE_7.value)) {
                return JudgementType.TYPE_7.key;
            }
            return JudgementType.TYPE_10.key;
        }
    }

    public enum LawyerSearchOrderBy
    {
        default_asc,default_desc,years_asc,years_desc,caseCount_asc,caseCount_desc;

        public static String convertKey(String orderBy)
        {
            if (containsKey(orderBy))
            {
                String prefix = orderBy.substring(0, orderBy.indexOf("_"));
                String suffix = orderBy.substring(orderBy.indexOf("_")+1);

                if (prefix.equals("default"))
                {
                    prefix = "id";
                }
                return prefix+" "+suffix;
            }
            return "id asc";
        }

        public static boolean containsKey(String orderBy)
        {
            LawyerSearchOrderBy[] values = values();
            for (LawyerSearchOrderBy lawyerSearchOrderBy : values)
            {
                if (lawyerSearchOrderBy.name().equals(orderBy))
                {
                    return true;
                }
            }
            return false;
        }
    }

    public enum LawyFirmSearchOrderBy
    {
        default_asc,default_desc,id_asc,id_desc,caseCount_asc,caseCount_desc;

        public static String convertKey(String orderBy)
        {
            if (containsKey(orderBy))
            {
                String prefix = orderBy.substring(0, orderBy.indexOf("_"));
                String suffix = orderBy.substring(orderBy.indexOf("_")+1);

                if (prefix.equals("default"))
                {
                    prefix = "foundTime";
                }
                return prefix+" "+suffix;
            }
            return "foundTime desc";
        }

        public static boolean containsKey(String orderBy)
        {
            LawyFirmSearchOrderBy[] values = values();
            for (LawyFirmSearchOrderBy lawyFirmSearchOrderBy : values)
            {
                if (lawyFirmSearchOrderBy.name().equals(orderBy))
                {
                    return true;
                }
            }
            return false;
        }
    }


    /**
     * 案件搜索类型
     */
    public enum CaseSearchType {
        keyword("keyword", "关键字"),
        searchWord("searchWord", "搜索词"),
        caseBrief("caseBrief", "案由"),
        trialRound("trialRound", "审批程序"),
        judgementType("judgementType", "文书性质"),
        judgementYear("judgementYear", "裁判年份"),
        courtName("court.name", "法院名称"),
        courtType("court.type", "法院层级"),
        courtProvince("court.province", "法院省份"),
        courtCity("court.city", "法院城市")
        ;


        private final String key;
        private final String value;

        public String getKey() {
            return key;
        }

        public String getValue() {
            return value;
        }

        CaseSearchType(String key, String value) {
            this.key = key;
            this.value = value;
        }

        public static boolean containsType(String name) {
            if (StringUtils.isBlank(name)) {
                return false;
            }
            CaseSearchType[] values = values();
            for (CaseSearchType item : values) {
                if (item.name().equals(name)) {
                    return true;
                }
            }
            return false;
        }

        public static CaseSearchType getByType(String name) {
            CaseSearchType[] values = values();
            for (CaseSearchType item : values) {
                if (item.name().equals(name)) {
                    return item;
                }
            }
            return null;
        }

        public static boolean containsKey(String key) {
            if (StringUtils.isBlank(key)) {
                return false;
            }
            CaseSearchType[] values = values();
            for (CaseSearchType item : values) {
                if (item.getKey().equals(key)) {
                    return true;
                }
            }
            return false;
        }

        public static CaseSearchType getByKey(String key) {
            CaseSearchType[] values = values();
            for (CaseSearchType item : values) {
                if (item.getKey().equals(key)) {
                    return item;
                }
            }
            return null;
        }
    }

    /**
     * 审判程序
     */
    public enum TrialRound {
        /**
         * 一审
         */
        TYPE_1("1", "一审"),
        /**
         * 二审
         */
        TYPE_2("2", "二审"),
        /**
         * 再审
         */
        TYPE_3("3", "再审"),
        /**
         * 非诉执行审查
         */
        TYPE_4("4", "非诉执行审查"),
        /**
         * 复核
         */
        TYPE_5("5", "复核"),
        /**
         * 刑罚变更
         */
        TYPE_6("6", "刑罚变更"),
        /**
         * 再审审查与审判监督
         */
        TYPE_7("7", "再审审查与审判监督"),
        /**
         * 其他
         */
        TYPE_8("8", "其他");

        private final String key;
        private final String value;

        public String getKey() {
            return key;
        }

        public String getValue() {
            return value;
        }

        TrialRound(String key, String value) {

            this.key = key;
            this.value = value;
        }

        /**
         * 通过key得到值
         */
        public static String getValueByKey(String key) {
            for (TrialRound model : TrialRound.values()) {
                if (model.getKey().equals(key)) {
                    return model.value;
                }
            }
            return null;
        }

        /**
         * 通过值得到key
         */
        public static String getKeyByValue(String value) {
            if (StringUtils.isBlank(value)) {
                return TrialRound.TYPE_8.key;
            }
            for (TrialRound model : TrialRound.values()) {
                if (model.getValue().equals(value)) {
                    return model.key;
                }
            }
            return JudgementType.TYPE_8.key;
        }
    }

    /**
     * 案由级别
     */
    public enum BriefLevel {
        /**
         * 一级案由
         */
        First("1", "一级案由"),
        /**
         * 二级案由
         */
        Second("2", "二级案由"),
        /**
         * 三级案由
         */
        Third("3", "三级案由"),
        /**
         * 四级案由
         */
        Fourth("4", "四级案由"),
        /**
         * 五级案由
         */
        Fifth("5", "五级案由");

        private final String key;
        private final String value;

        public String getKey() {
            return key;
        }

        public String getValue() {
            return value;
        }

        BriefLevel(String key, String value) {

            this.key = key;
            this.value = value;
        }

        /**
         * 通过key得到值
         */
        public static String getValueByKey(String key) {
            for (BriefLevel model : BriefLevel.values()) {
                if (model.getKey().equals(key)) {
                    return model.value;
                }
            }
            return null;
        }
    }

    public enum CollectTypeEum
    {
        CASE,LAWYER;

        public static boolean containsKey(String key)
        {
            CollectTypeEum[] values = values();
            for (CollectTypeEum collectTypeEum : values)
            {
                if (collectTypeEum.name().equals(key))
                {
                    return true;
                }
            }
            return false;
        }
    }

    /**
     * 聚合类别
     */
    public enum AggsType {
        /**
         * 文书性质
         */
        judgementType("judgementType", true, "judgementType"),
        /**
         * 裁判年份
         */
        judgementYear("judgementYear", true, "judgementYear"),
        /**
         * 审批程序
         */
        trialRound("trialRound", true, "trialRound"),
        /**
         * 案件类型
         */
        type("type", true, "type"),
        /**
         * 法院层级
         */
        courtType("courtType", true, "court.type"),
        /**
         * 法院省份
         */
        courtProvince("courtProvince", true, "court.province"),
        /**
         * 法院市
         */
        courtCity("courtCity", false, "court.city"),
        /**
         * 法院名称
         */
        courtName("courtName", false, "court.name"),

        /**
         * 一级案由
         */
        briefFirst("brief", true, "brief.firstId"),
        /**
         * 二级案由
         */
        briefSecond("child", false, "brief.secondId"),
        /**
         * 三级案由
         */
        briefThird("child", false, "brief.thirdId"),
        /**
         * 四级案由
         */
        briefFourth("child", false, "brief.fourthId"),
        /**
         * 五级案由
         */
        briefFifth("child", false, "brief.fifthId"),
        ;

        private final String key;
        private final boolean enable;
        private final String value;

        public String getKey() {
            return key;
        }

        public String getValue() {
            return value;
        }

        public boolean isEnable() {
            return enable;
        }

        AggsType(String key, boolean enable, String value) {

            this.key = key;
            this.enable = enable;
            this.value = value;
        }

        /**
         * 通过key得到值
         */
        public static String getValueByKey(String key) {
            for (AggsType model : AggsType.values()) {
                if (model.getKey().equals(key)) {
                    return model.value;
                }
            }
            return null;
        }
    }

    public enum lawyerSearchField {
        lawyerName, lawFirmProvince, years, brief
    }

    public enum lawFirmSearchField {
        lawFirm, lawFirmProvince, foundTime, lawyerNum
    }
}
