package com._360pai.core.common.constants;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author : whisky_vip
 * @date : 2018/8/16 16:58
 */
public class AssetEnum {
    public static void main(String[] args) {
        System.out.println(AssetEnum.AssetType.valueOf("CREDITOR_RIGHTS"));
    }

    public enum AssetType {
        /**
         * 债权
         */
        CREDITOR_RIGHTS("CREDITOR_RIGHTS", "债权"),
        /**
         * 二手车
         */
        CAR("CAR", "二手车"),
        /**
         * 租赁权
         */
        LEASEHOLD("LEASEHOLD", "租赁权"),
        /**
         * 艺术品
         */
        TREASURE("TREASURE", "艺术品");
        private final String key;
        private final String value;

        public String getKey() {
            return key;
        }

        public String getValue() {
            return value;
        }

        AssetType(String key, String value) {
            this.key = key;
            this.value = value;
        }

        @Override
        public String toString() {
            return key.toString();
        }
    }

    public enum ExpectedMode {
        /**
         * 增价 升价拍
         */
        ENGLISH("ENGLISH", "增价拍"),
        /**
         * 减价 降价拍模式
         */
        DUTCH("DUTCH", "减价拍"),
        /**
         * 暗标 一口价暗标
         */
        SEALED("SEALED", "一口价暗标"),

        /**
         * 明标 一口价明标
         */
        PUBLIC("PUBLIC", "一口价明标"),

        /**
         *
         * 自由报价 自由出价 择优成交
         */
        FREE("FREE", "自由报价"),

        /**
         * 秒杀 限时秒杀
         */
        FLASH("FLASH", "限时秒杀");

        private final String key;
        private final String value;

        ExpectedMode(String key, String value) {
            this.key = key;
            this.value = value;
        }

        public String getKey() {
            return key;
        }

        public String getValue() {
            return value;
        }

        @Override
        public String toString() {
            return key.toString();
        }

        /**
         * 通过值得到key
         */
        public static String getKeyByValue(String value) {
            for (ExpectedMode model : ExpectedMode.values()) {
                if (model.getKey().equals(value)) {
                    return model.value;
                }
            }
            return null;
        }

        /**
         * 通过值得到key
         */
        public static ExpectedMode getModeByKey(String key) {
            for (ExpectedMode model : ExpectedMode.values()) {
                if (model.getKey().equals(key)) {
                    return model;
                }
            }
            return null;
        }
    }

    public enum Status {

        /**
         * 等待完善信息
         */
        PENDING("PENDING", "未发布"),
        /**
         * 上拍
         */
        DELIVERING("DELIVERING", "等待机构审核"),
        /**
         * 正在拍卖
         */
        SELLING("SELLING", "正在拍卖"),

        /**
         * 成交
         */
        CLOSED("CLOSED", "成交"),

        /**
         * 流拍
         */
        FAILED("FAILED", "流拍"),

        /**
         * 机构拒绝
         */
        REJECT("REJECT", "机构审核拒绝"),

        /**
         * 机构审核通过
         */
        APPROVED("APPROVED", "机构审核通过"),

        /**
         * 撤回
         */
        WITHDRAW("WITHDRAW", "撤回");

        private final String key;
        private final String value;

        public String getValue() {
            return value;
        }

        public String getKey() {

            return key;
        }

        Status(String key, String value) {

            this.key = key;
            this.value = value;
        }

        /**
         * 通过key得到值
         */
        public static String getValueByKey(String key) {
            for (Status model : Status.values()) {
                if (model.getKey().equals(key)) {
                    return model.value;
                }
            }
            return null;
        }
    }

    /**
     * 债券业务类型
     */
    public enum CategoryBusinessType {
        /**
         * 拍卖
         */
        AUCTION("AUCTION", "拍卖"),
        /**
         * 预招商
         */
        ENROLLING("ENROLLING", "预招商");

        private final String key;
        private final String value;

        public String getValue() {
            return value;
        }

        public String getKey() {

            return key;
        }

        CategoryBusinessType(String key, String value) {

            this.key = key;
            this.value = value;
        }
    }

    /**
     * 拍卖模式
     */
    public enum CategoryDealMode {
        /**
         * 转让类
         */
        SELL("SELL", "转让类"),
        /**
         * 服务类
         */
        SERVICE("SERVICE", "服务类");

        private final String key;
        private final String value;

        public String getValue() {
            return value;
        }

        public String getKey() {

            return key;
        }

        CategoryDealMode(String key, String value) {
            this.key = key;
            this.value = value;
        }
    }

    /**
     * 业务类型 0:拍卖 1:配置乐 2:处置服务商
     */
    public enum BusType {
        /**
         * 拍卖
         */
        AUCTION("0", "拍卖"),
        /**
         * 配置乐
         */
        WITHFUDIG("1", "配置乐"),
        /**
         * 处置服务商
         */
        DISPOSAL("2", "处置服务商");

        private final String key;
        private final String value;

        public String getValue() {
            return value;
        }

        public String getKey() {

            return key;
        }

        BusType(String key, String value) {
            this.key = key;
            this.value = value;
        }
    }

    /**
     *
     * 功能描述:  草稿箱枚举
     *
     */
    public enum Drafts {
        AUCTION("auction", "拍卖活动"),
        ENROLLING("enrolling", "预招商"),
        ENROLLING_THIRD_PARTY("enrolling_third_party", "预招商第三方"),
        INFORMATION("information", "配资乐"),
        DISPOSAL("disposal", "处置服务");

        private final String key;
        private final String value;

        public String getValue() {
            return value;
        }

        public String getKey() {

            return key;
        }

        Drafts(String key, String value) {
            this.key = key;
            this.value = value;
        }
    }

    /**
     * 业务类型 0:拍卖 1:配置乐 2:处置服务商
     */
    public enum ComeFrom {
        /**
         * 拍卖
         */
        PLATFORM("0", "平台上拍"),
        /**
         * 配置乐
         */
        AGENCY("1", "机构上拍");

        private final String key;
        private final String value;

        public String getValue() {
            return value;
        }

        public String getKey() {

            return key;
        }

        ComeFrom(String key, String value) {
            this.key = key;
            this.value = value;
        }


        /**
         * 通过值得到key
         */
        public static String getKeyByValue(String value) {
            for (ComeFrom model : ComeFrom.values()) {
                if (model.getKey().equals(value)) {
                    return model.value;
                }
            }
            return null;
        }
    }



    /**
     * 关注拍品的业务来源
     */
    public enum FavorType {
        /**
         * 平台关注
         */
        WEB("0", "平台关注来源"),
        /**
         * 店铺关注
         */
        SHOP("1", "店铺关注来源");

        private final String key;
        private final String value;

        public String getValue() {
            return value;
        }

        public String getKey() {

            return key;
        }

        FavorType(String key, String value) {
            this.key = key;
            this.value = value;
        }


        /**
         * 通过值得到key
         */
        public static String getKeyByValue(String value) {
            for (FavorType model : FavorType.values()) {
                if (model.getKey().equals(value)) {
                    return model.value;
                }
            }
            return null;
        }
    }


    /**
     *  租赁权类型
     */
    public enum LeaseAssetType {

        /**
         * 商铺
         */
        SHOP("SHOP", "商铺"),
        /**
         *
         */
        MARKET("MARKET", "市场"),
        /**
         * 厂房
         */
        FACTORY_BUILDING("FACTORY_BUILDING", "厂房"),
        /**
         * 办公
         */
        OFFICE("OFFICE", "办公"),
        /**
         * 土地
         */
        LAND("LAND", "土地"),
        /**
         * 场地
         */
        SITE("SITE", "场地"),
        /**
         * 农用地
         */
        AGRICULTURAL_LAND("AGRICULTURAL_LAND", "农用地"),
        /**
         * 商业
         */
        BUSINESS("BUSINESS", "商业"),
        /**
         * 其他
         */
        OTHER("OTHER", "其他");


        private final String key;
        private final String value;

        public String getValue() {
            return value;
        }

        public String getKey() {

            return key;
        }

        LeaseAssetType(String key, String value) {
            this.key = key;
            this.value = value;
        }


        /**
         * 通过值得到key
         */
        public static String getValueByKey(String key) {
            for (LeaseAssetType model : LeaseAssetType.values()) {
                if (model.getKey().equals(key)) {
                    return model.value;
                }
            }
            return null;
        }

        // 获取所有areaType类型
        public static final List<Map<String, String>> list = new ArrayList<>();

        static  {
            for (LeaseAssetType model : LeaseAssetType.values()) {
                Map<String, String> newMap = new HashMap<>();
                newMap.put("id", model.key);
                newMap.put("name", model.value);

                list.add(newMap);
            }
        }

        /**
         * 通过值得到key
         */
        public static String getKeyByValue(String value) {
            for (LeaseAssetType model : LeaseAssetType.values()) {
                if (model.getValue().equals(value)) {
                    return model.key;
                }
            }
            return null;
        }
    }


    /**
     *  租赁权类型
     */
    public enum OperatingTypeType {

        /**
         * 办公
         */
        OFFICE("OFFICE", "办公"),
        /**
         * 工业
         */
        INDUSTRY("INDUSTRY", "工业"),
        /**
         * 居住
         */
        RESIDE("RESIDE", "居住"),
        /**
         * 商业
         */
        BUSINESS("BUSINESS", "商业"),
        /**
         * 商业
         */
        WAREHOUSING("WAREHOUSING", "仓储"),
        /**
         * 其他
         */
        OTHER("OTHER", "其他");


        private final String key;
        private final String value;

        public String getValue() {
            return value;
        }

        public String getKey() {

            return key;
        }

        OperatingTypeType(String key, String value) {
            this.key = key;
            this.value = value;
        }


        /**
         * 通过值得到key
         */
        public static String getValueByKey(String key) {
            for (OperatingTypeType model : OperatingTypeType.values()) {
                if (model.getKey().equals(key)) {
                    return model.value;
                }
            }
            return null;
        }

        // 获取所有areaType类型
        public static final List<Map<String, String>> list = new ArrayList<>();

        static  {
            for (OperatingTypeType model : OperatingTypeType.values()) {
                Map<String, String> newMap = new HashMap<>();
                newMap.put("id", model.key);
                newMap.put("name", model.value);

                list.add(newMap);
            }
        }
    }


    /**
     *  租赁权类型
     */
    public enum LeaseAreaType {
        /**
         * 区属国有资产
         */
        STATE_OWNED_ASSETS("STATE_OWNED_ASSETS", "区属国有资产"),
        /**
         * 区属集体资产
         */
        DISTRICT_COLLECTIVE_ASSETS("DISTRICT_COLLECTIVE_ASSETS", "区属集体资产"),
        /**
         * 镇属集体资产
         */
        TOWNSHIP_COLLECTIVE_ASSETS("TOWNSHIP_COLLECTIVE_ASSETS", "镇属集体资产"),
        /**
         * 镇属国有资产
         */
        TOWNSHIP_OWNED_ASSETS("TOWNSHIP_OWNED_ASSETS", "镇属国有资产");


        private final String key;
        private final String value;

        public String getValue() {
            return value;
        }

        public String getKey() {

            return key;
        }

        LeaseAreaType(String key, String value) {
            this.key = key;
            this.value = value;
        }


        /**
         * 通过值得到key
         */
        public static String getValueByKey(String key) {
            for (LeaseAreaType model : LeaseAreaType.values()) {
                if (model.getKey().equals(key)) {
                    return model.value;
                }
            }
            return null;
        }



        // 获取所有areaType类型
        public static final List<Map<String, String>> list = new ArrayList<>();

        static  {
            for (LeaseAreaType model : LeaseAreaType.values()) {
                Map<String, String> newMap = new HashMap<>();
                newMap.put("id", model.key);
                newMap.put("name", model.value);

                list.add(newMap);
            }
        }
    }


    /**
     *  竞租方资格
     */
    public enum BidderQualification {
        /**
         * 自然人
         */
        NATURAL_PERSON("NATURAL_PERSON", "自然人"),
        /**
         * 自然人
         */
        NATURAL_LEGAL_PERSON("NATURAL_LEGAL_PERSON", "自然人或法人"),
        /**
         * 法人
         */
        LEGAL_PERSON("LEGAL_PERSON", "法人");

        private final String key;
        private final String value;

        public String getValue() {
            return value;
        }

        public String getKey() {

            return key;
        }

        BidderQualification(String key, String value) {
            this.key = key;
            this.value = value;
        }


        /**
         * 通过值得到key
         */
        public static String getValueByKey(String key) {
            for (BidderQualification model : BidderQualification.values()) {
                if (model.getKey().equals(key)) {
                    return model.value;
                }
            }
            return null;
        }
    }


    /**
     *  资料预审方式
     */
    public enum PreAuditMethod {
        /**
         * 线上审核
         */
        ONLINE_REVIEW("ONLINE_REVIEW", "线上审核"),
        /**
         * 线下审核
         */
        OFFLINE_REVIEW ("OFFLINE_REVIEW", "线下审核");

        private final String key;
        private final String value;

        public String getValue() {
            return value;
        }

        public String getKey() {

            return key;
        }

        PreAuditMethod(String key, String value) {
            this.key = key;
            this.value = value;
        }


        /**
         * 通过值得到key
         */
        public static String getValueByKey(String key) {
            for (PreAuditMethod model : PreAuditMethod.values()) {
                if (model.getKey().equals(key)) {
                    return model.value;
                }
            }
            return null;
        }
    }

     /**
     *  资料预审方式
     */
    public enum AssetDraft {

        LEASE("lease", "租赁权");

        private final String key;
        private final String value;

        public String getValue() {
            return value;
        }

        public String getKey() {

            return key;
        }

         AssetDraft(String key, String value) {
            this.key = key;
            this.value = value;
        }


        /**
         * 通过值得到key
         */
        public static String getValueByKey(String key) {
            for (AssetDraft model : AssetDraft.values()) {
                if (model.getKey().equals(key)) {
                    return model.value;
                }
            }
            return null;
        }
    }

    /**
     *  租金支付约定
     */
    public enum PaymentCycle {
        /**
         * 季度
         */
        QUARTER("QUARTER", "季", "第一年第一季度的租金"),
        YEAR("YEAR", "年", "第一年整年的租金"),
        HALF_YEAR("HALF_YEAR","半年", "第一年半年的租金"),
        MONTH("MONTH", "月", "第一个月的租金");

        private final String key;
        private final String value;
        private final String showValue;

        public String getValue() {
            return value;
        }

        public String getKey() {

            return key;
        }

        public String getShowValue() {
            return showValue;
        }

        PaymentCycle(String key, String value, String showValue) {
            this.key = key;
            this.value = value;
            this.showValue = showValue;
        }


        /**
         * 通过值得到key
         */
        public static String getValueByKey(String key) {
            for (PaymentCycle model : PaymentCycle.values()) {
                if (model.getKey().equals(key)) {
                    return model.value;
                }
            }
            return null;
        }

        /**
         * 通过值得到key
         */
        public static String getShowValueByKey(String key) {
            for (PaymentCycle model : PaymentCycle.values()) {
                if (model.getKey().equals(key)) {
                    return model.showValue;
                }
            }
            return null;
        }
    }


    /**
     *  租金支付约定
     */
    public enum CostBearer {

        LESSOR("LESSOR", "出租方"),
        LESSEE("LESSEE", "承租方");

        private final String key;
        private final String value;

        public String getValue() {
            return value;
        }

        public String getKey() {

            return key;
        }

        CostBearer(String key, String value) {
            this.key = key;
            this.value = value;
        }


        /**
         * 通过值得到key
         */
        public static String getValueByKey(String key) {
            for (CostBearer model : CostBearer.values()) {
                if (model.getKey().equals(key)) {
                    return model.value;
                }
            }
            return null;
        }
    }


    /**
     *  租赁权拍卖状态
     */
    public enum LeaseStatus {

        WAITING_FIRST_REVIEW("WAITING_FIRST_REVIEW", "待初审", "等待内部审核","待初审","待初审"),
        WAITING_SECOND_REVIEW("WAITING_SECOND_REVIEW", "待终审", "等待内部审核","待终审","初审通过"),
        FIRST_REVIEW_REJECTION("FIRST_REVIEW_REJECTION", "初审退回", "内部审核拒绝","初审退回","初审退回"),
        SECOND_REVIEW_REJECTION("SECOND_REVIEW_REJECTION", "终审退回", "内部审核拒绝","待初审","终审退回"),
        WAITING_RELEASE("WAITING_RELEASE", "等待发布", "等待发布","终审通过","终审通过"),
        REGISTRATION_PERIOD("REGISTRATION_PERIOD", "报名中", "报名中","终审通过",""),
        LEASE_APPLY_END("LEASE_APPLY_END", "已过报名时间", "已过报名时间", "已过报名时间",""),
        QUALIFICATION_REVIEW("QUALIFICATION_REVIEW", "资质审核中", "资质审核中","终审通过","");


        private final String key;
        private final String value;
        private final String parentValue;
        private final String showValue;
        private final String recordValue;


        public String getRecordValue() {
            return recordValue;
        }

        public String getValue() {
            return value;
        }

        public String getParentValue() {
            return parentValue;
        }

        public String getShowValue() {
            return showValue;
        }

        public String getKey() {

            return key;
        }

        LeaseStatus(String key, String value, String parentValue,String showValue,String recordValue) {
            this.key = key;
            this.value = value;
            this.parentValue = parentValue;
            this.showValue = showValue;
            this.recordValue = recordValue;

        }


        /**
         * 通过值得到key
         */
        public static String getValueByKey(String key) {
            for (LeaseStatus model : LeaseStatus.values()) {
                if (model.getKey().equals(key)) {
                    return model.value;
                }
            }
            return null;
        }

        /**
         * 通过值得到key
         */
        public static String getParentValueByKey(String key) {
            for (LeaseStatus model : LeaseStatus.values()) {
                if (model.getKey().equals(key)) {
                    return model.parentValue;
                }
            }
            return null;
        }



        /**
         * 通过值得到key
         */
        public static String getShowValueByKey(String key) {
            for (LeaseStatus model : LeaseStatus.values()) {
                if (model.getKey().equals(key)) {
                    return model.showValue;
                }
            }
            return null;
        }



        /**
         * 通过值得到key
         */
        public static String getRecordValueByKey(String key) {
            for (LeaseStatus model : LeaseStatus.values()) {
                if (model.getKey().equals(key)) {
                    return model.recordValue;
                }
            }
            return null;
        }
    }



    /**
     *  租赁权拍卖状态
     */
    public enum FieldErrorMessage {

        LESS_SEVEN_DAYS(7, "审核结束时间与开始报名时间应大于等于七个自然日"),
        LESS_TWO_DAYS(2, "报名截止时间与审核截止时间间隔应大于等于两个工作日"),
        LESS_ONE_DAYS(1, "拍卖开始时间与拍卖截止时间间隔应大于等于一个工作日"),
        HOUSE_NUM_NULL(0, "出租房间数不能为空"),
        POWER_SITUATION_NULL(0, "产证权利人不能为空"),
        LEASE_AREA_NULL(0, "出租面积不能为空"),
        LESSOR_CONTACT_NULL(0, "出租方联系人不能为空"),
        LESSOR_CONTACT_NUMBER_NULL(0, "出租房联系方式不能为空"),
        INCREMENT_RATE_NULL(0, "租金年递增比例不能为空"),
        RESERVE_PRICE_NULL(0, "保留价不能为空"),
        START_PRICE_NULL(0, "起拍价不能为空"),
        INCREMENT_NULL(0, "加价幅度不能为空"),
        REDUCTION_NULL(0, "减价幅度不能为空"),
        REDUCTION_PERIOD_NULL(0, "减价周期不能为空"),
        DEPOSIT_NULL(0, "保证金数据不能为空"),
        PAY_DAYS_NULL(0, "支付时间不能为空"),
        DEPOSIT_MORE_START(0, "保证金金额不能大于起拍价"),
        FIND_NULL(0, "租赁权查询数据为空"),
        HANDOVER_DAYS_NULL(0, "拍卖起止时间不能为空");


        private final Integer key;
        private final String value;

        public String getValue() {
            return value;
        }

        public Integer getKey() {
            return key;
        }

        FieldErrorMessage(Integer key, String value) {
            this.key = key;
            this.value = value;
        }


        /**
         * 通过值得到key
         */
        public static String getValueByKey(String key) {
            for (FieldErrorMessage model : FieldErrorMessage.values()) {
                if (model.getKey().equals(key)) {
                    return model.value;
                }
            }
            return null;
        }
    }


    /**
     *  租金支付约定
     */
    public enum HouseStructure {

        BRICK_STRUCTURE("BRICK_STRUCTURE", "砖结构"),
        FRAME_STRUCTURE("FRAME_STRUCTURE", "框架结构"),
        MIXED_STRUCTURE("MIXED_STRUCTURE", "砖混结构");

        private final String key;
        private final String value;

        public String getValue() {
            return value;
        }

        public String getKey() {

            return key;
        }

        HouseStructure(String key, String value) {
            this.key = key;
            this.value = value;
        }


        /**
         * 通过值得到key
         */
        public static String getValueByKey(String key) {
            for (HouseStructure model : HouseStructure.values()) {
                if (model.getKey().equals(key)) {
                    return model.value;
                }
            }
            return null;
        }
    }


    /**
     *  租金支付约定
     */
    public enum WebFlag {

        WEB("WEB", "前端"),
        ADMIN("ADMIN", "***"),
        AGENCY("AGENCY", "***");

        private final String key;
        private final String value;

        public String getValue() {
            return value;
        }

        public String getKey() {

            return key;
        }

        WebFlag(String key, String value) {
            this.key = key;
            this.value = value;
        }


        /**
         * 通过值得到key
         */
        public static String getValueByKey(String key) {
            for (WebFlag model : WebFlag.values()) {
                if (model.getKey().equals(key)) {
                    return model.value;
                }
            }
            return null;
        }
    }

}
