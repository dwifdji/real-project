package com._360pai.core.common.constants;

/**
 * @author xdrodger
 * @Title: PersonaEnum
 * @ProjectName zeus
 * @Description:
 * @date 2018/10/13 14:39
 */
public class PersonaEnum {
    public enum AssetPackageSource {
        /**
         * 资产包来源（10 AMC 20 其他资管公司 30 当地政府 40 国有企业 50 银行）
         */
        AMC("10", "AMC"),
        OTHER_AMC("20", "其他资管公司"),
        LOCAL_GOVERNMENT("30", "当地政府"),
        STATE_OWNED_ENTERPRISE("40", "国有企业"),
        BANK("50", "银行");


        private String code;
        private String desc;

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        private AssetPackageSource(String code, String desc) {
            this.code = code;
            this.desc = desc;
        }

        public static boolean contains(String code) {
            for (AssetPackageSource item : AssetPackageSource.values()) {
                if (item.getCode().equals(code)) {
                    return true;
                }
            }
            return false;
        }
    }

    public enum AssetPropertyType {
        /**
         * 资产属性类型( 00 住宅 10 商业 20 厂房 30 设备物资 40 商铺 50 土地 60 保证信用类 70 租赁权 80 股权 90 车辆)
         */
        RESIDENCE("00", "住宅"),
        BUSINESS("10", "商业"),
        FACTORY("20", "厂房"),
        EQUIPMENT_MATERIAL("30", "设备物资"),
        SHOP("40", "商铺"),
        LAND("50", "土地"),
        CREDIT_GUARANTEE_CATEGORY("60", "保证信用类"),
        LEASEHOLD("70", "租赁权"),
        STOCK_RIGHT("80", "股权"),
        VEHICLE("90", "车辆");

        private String code;
        private String desc;

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        private AssetPropertyType(String code, String desc) {
            this.code = code;
            this.desc = desc;
        }

        public static boolean contains(String code) {
            for (AssetPropertyType item : AssetPropertyType.values()) {
                if (item.getCode().equals(code)) {
                    return true;
                }
            }
            return false;
        }
    }

    public enum AssetType {
        /**
         * 资产类型（10 债权 20 物权 30 债权+物权）
         */
        CREDITOR_RIGHT("10", "债权"),
        REAL_RIGHT("20", "物权"),
        CREDITOR_PLUS_REAL_RIGHT("30", "债权+物权");

        private String code;
        private String desc;

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        private AssetType(String code, String desc) {
            this.code = code;
            this.desc = desc;
        }

        public static boolean contains(String code) {
            for (AssetType item : AssetType.values()) {
                if (item.getCode().equals(code)) {
                    return true;
                }
            }
            return false;
        }
    }

    public enum CompanyType {
        /**
         * 企业类型 10 民营 20 合资 30 国有企业 40 银行 50 个人
         */
        PRIVATE_ENTERPRISE("10", "民营"),
        JOINT_VENTURE("20", "合资"),
        STATE_OWNED_ENTERPRISE("30", "国有企业"),
        BANK("40", "银行"),
        INDIVIDUAL("50", "个人");

        private String code;
        private String desc;

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        private CompanyType(String code, String desc) {
            this.code = code;
            this.desc = desc;
        }

        public static boolean contains(String code) {
            for (CompanyType item : CompanyType.values()) {
                if (item.getCode().equals(code)) {
                    return true;
                }
            }
            return false;
        }

        public static String getDesc(String code) {
            for (CompanyType item : CompanyType.values()) {
                if (item.getCode().equals(code)) {
                    return item.getDesc();
                }
            }
            return "";
        }
    }

    public enum CustomerType {
        /**
         * 客户类型（10 竞买人 20 资产方 30 处置方 40 中介方 50 联拍机构 60 配资机构）
         */
        BIDDER("10", "竞买人"),
        ASSET_PARTY("20", "资产方"),
        DISPOSAL_PARTY("30", "处置方"),
        INTERMEDIARY_ORGAN("40", "中介方"),
        UNION_AUCTION_AGENCy("50", "联拍机构"),
        FUNDING_AGENCY("60", "配资机构");

        private String code;
        private String desc;

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        private CustomerType(String code, String desc) {
            this.code = code;
            this.desc = desc;
        }

        public static boolean contains(String code) {
            for (CustomerType item : CustomerType.values()) {
                if (item.getCode().equals(code)) {
                    return true;
                }
            }
            return false;
        }
    }

    public enum DispoalInvestigation {
        /**
         * 处置调查（10 自行 20 第三方）
         */
        ONESELF("10", "自行"),
        THIRD_PARTY("20", "第三方");

        private String code;
        private String desc;

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        private DispoalInvestigation(String code, String desc) {
            this.code = code;
            this.desc = desc;
        }

        public static boolean contains(String code) {
            for (DispoalInvestigation item : DispoalInvestigation.values()) {
                if (item.getCode().equals(code)) {
                    return true;
                }
            }
            return false;
        }
    }

    public enum DisposalBusinessType {
        /**
         * 擅长业务类型（10 评估 20 尽职调查 30 司法处置 40 执行处置 50 清房 60 催收 70 查找资产线索）
         */
        EVALUATE("10", "评估"),
        RESPONSIBLE_INVESTIGATION("20", "尽职调查"),
        JUDICIAL_DISPOSAL("30", "司法处置"),
        EXECUTE_DISPOSAL("40", "执行处置"),
        ROOM_CLEARING("50", "清房"),
        COLLECTION("60", "催收"),
        FIND_ASSET_CLUES("70", "查找资产线索");

        private String code;
        private String desc;

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        private DisposalBusinessType(String code, String desc) {
            this.code = code;
            this.desc = desc;
        }

        public static boolean contains(String code) {
            for (DisposalBusinessType item : DisposalBusinessType.values()) {
                if (item.getCode().equals(code)) {
                    return true;
                }
            }
            return false;
        }
    }

    public enum FundingLevel {
        /**
         * 配资需求（10 优先 20 劣后 30 夹层）
         */
        PRIOR("10", "优先"),
        INFERIOR("20", "劣后"),
        INTERBED("30", "夹层");

        private String code;
        private String desc;

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        private FundingLevel(String code, String desc) {
            this.code = code;
            this.desc = desc;
        }

        public static boolean contains(String code) {
            for (FundingLevel item : FundingLevel.values()) {
                if (item.getCode().equals(code)) {
                    return true;
                }
            }
            return false;
        }
    }

    public enum FundSource {
        /**
         * 资金来源（10 自有资金 20 第三方融资）
         */
        OWN_FUND("10", "自有资金"),
        THIRD_PARTY_FINANCING("20", "第三方融资");

        private String code;
        private String desc;

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        private FundSource(String code, String desc) {
            this.code = code;
            this.desc = desc;
        }

        public static boolean contains(String code) {
            for (FundSource item : FundSource.values()) {
                if (item.getCode().equals(code)) {
                    return true;
                }
            }
            return false;
        }
    }

    public enum IntermediaryOrganBusinessType {
        /**
         * 擅长业务类型（10 找买家 20 找卖家）
         */
        FIND_BUYER("10", "找买家"),
        FIND_SELLER("20", "找卖家");

        private String code;
        private String desc;

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        private IntermediaryOrganBusinessType(String code, String desc) {
            this.code = code;
            this.desc = desc;
        }

        public static boolean contains(String code) {
            for (IntermediaryOrganBusinessType item : IntermediaryOrganBusinessType.values()) {
                if (item.getCode().equals(code)) {
                    return true;
                }
            }
            return false;
        }
    }

    public enum ResponsibleInvestigation {

        /**
         * 尽职调查（10 自行 20 第三方）
         */
        ONESELF("10", "自行"),
        THIRD_PARTY("20", "第三方");

        private String code;
        private String desc;

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        private ResponsibleInvestigation(String code, String desc) {
            this.code = code;
            this.desc = desc;
        }

        public static boolean contains(String code) {
            for (ResponsibleInvestigation item : ResponsibleInvestigation.values()) {
                if (item.getCode().equals(code)) {
                    return true;
                }
            }
            return false;
        }
    }

    public enum UserStatus {
        /**
         * 用户状态 10 电话空号、20 电话无人接听、30 不愿意合作、40 已建立沟通、50 已展开合作、60 接听电话被拒绝
         */
        ABSENTEE("10", "电话空号"),
        NO_ANSWER("20", "电话无人接听"),
        UNWILLING_TO_COOPERATE("30", "不愿意合作"),
        PHONE_IS_REJECTED("40", "已建立沟通"),
        ESTABLISHED_COMMUNICATIONS("50", "已展开合作"),
        BUSINESS_COOPERATION("60", "接听电话被拒绝"),
        PHONE_ERROR("70", "电话号码错误");

        private String code;
        private String desc;

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        private UserStatus(String code, String desc) {
            this.code = code;
            this.desc = desc;
        }

        public static boolean contains(String code) {
            for (UserStatus item : UserStatus.values()) {
                if (item.getCode().equals(code)) {
                    return true;
                }
            }
            return false;
        }

        public static String getDesc(String code) {
            for (UserStatus item : UserStatus.values()) {
                if (item.getCode().equals(code)) {
                    return item.getDesc();
                }
            }
            return "";
        }
    }

    public enum UserType {
        /**
         * 用户所属类型（10 个人 20 公司）
         */
        INDIVIDUAL("10", "个人"),
        COMPANY("20", "公司");

        private String code;
        private String desc;

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        private UserType(String code, String desc) {
            this.code = code;
            this.desc = desc;
        }

        public static boolean contains(String code) {
            for (UserType item : UserType.values()) {
                if (item.getCode().equals(code)) {
                    return true;
                }
            }
            return false;
        }

        public static String getDesc(String code) {
            for (UserType item : UserType.values()) {
                if (item.getCode().equals(code)) {
                    return item.getDesc();
                }
            }
            return "";
        }
    }
}
