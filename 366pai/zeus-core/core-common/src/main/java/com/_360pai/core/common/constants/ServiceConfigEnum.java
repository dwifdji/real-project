package com._360pai.core.common.constants;

/**
 * 描述 serviceConfig enum
 * value为默认提供值
 *
 * @author : whisky_vip
 * @date : 2018/9/6 18:45
 */
public enum ServiceConfigEnum {

    /**
     * 接受邮件的平台人员
     */
    PLATFORM_EMAIL_RECEIVER("platform_email_receiver", "likaishen@360pai.com"),

    /**
     * 总条数
     */
    WITHFUDIG_TOTAL_NUM("withfudig_total_num", "0"),
    /**
     * 总金额
     */
    WITHFUDIG_TOTAL_AMOUNT("withfudig_total_amount", "0"),
    /**
     * 资产大买办需求单价格
     */
    COMPRADOR_REQUIREMENT_PRICE("comprador_requirement_price", "100"),
    /**
     * 配资乐需求单价格
     */
    WITHFUDIG_REQUIREMENT_PRICE("withfudig_requirement_price", "100"),

    /**
     * 服务处置
     */
    DISPOSAL_REQUIREMENT_PRICE("disposal_requirement_price", "100"),


    /**
     * 尽调
     */
    DIPOSAL_ADJUSTED("diposal_adjusted", "100"),
    /**
     * 司法处置
     */
    DIPOSAL_JUDICIAL("diposal_judicial", "100"),
    /**
     * 催收
     */
    DIPOSAL_COLLECTION("diposal_collection", "100"),
    /**
     * 查找财产线索
     */
    DIPOSAL_FIND_PROPERTY_LEADS("diposal_find_property_leads", "100"),
    /**
     * 清房
     */
    DIPOSAL_CLEAR_HOUSE("diposal_clear_house", "100"),
    /**
     * 执行处置
     */
    DIPOSAL_EXECUTION("diposal_execution", "100"),
    /**
     * 评估
     */
    DIPOSAL_EVALUATION("diposal_evaluation", "100"),


    /**
     * 抵押物
     */
    ENROLLING_PAWN_PRICE("enrolling_pawn_price", "100"),

    /**
     * 物权招商
     */
    ENROLLING_REAL_PRICE("enrolling_real_price", "100"),

    /**
     * 债权招商
     */
    ENROLLING_CREDITOR_PRICE("enrolling_creditor_price", "100"),

    /**
     * 基础尽调
     */
    BASIS_REPORT_PRICE("basis_report_price", "100"),

    /**
     * 完整尽调
     *
     */
    COMPLETE_REPORT_PRICE("complete_report_price", "1500"),

    /**
     * 我要处置
     */
    DISPOSE_WANT_SHOW("dispose_want_show", "100"),


    /**
     * 开店
     */
    OPEN_SHOP_PRICE("open_shop_price", "2000"),
    /**
     * 数字跳动计算器-尽调成本
     */
    CALCULATOR_DUE_DILIGENCE_AMOUNT("calculator_due_diligence_amount", "50000"),
    /**
     * 数字跳动计算器-杂费
     */
    CALCULATOR_MISC_AMOUNT("calculator_misc_amount", "100000"),
    /**
     * 数字跳动计算器-播报费用
     */
    CALCULATOR_BROADCAST_AMOUNT("calculator_broadcast_amount", "10"),
    /**
     * 数字跳动计算器-罚息比例
     */
    CALCULATOR_PENALTY_RATE("calculator_penalty_rate", "50")
    ;


    private final String key;
    /**
     * 默认value值
     */
    private final String value;

    public String getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }

    ServiceConfigEnum(String key, String value) {
        this.key = key;
        this.value = value;
    }


}
