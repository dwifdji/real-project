package com._360pai.core.facade.applet.req;

import com._360pai.arch.common.RequestModel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * @author xdrodger
 * @Title: NumberJumpReq
 * @ProjectName dev2-zeus
 * @Description:
 * @date 2019-04-28 19:16
 */
@Data
public class CalculatorReq extends RequestModel {

    @Data
    public static class LoginReq extends RequestModel {
        private String code;
        private String nickName;
        private String headImgUrl;
    }

    @Data
    public static class QueryReq extends RequestModel {
        /**
         * 外部绑定表id
         */
        private Integer extBindId;

        private Integer id;
    }

    @Data
    public static class QueryRelativeListReq extends RequestModel {
        /**
         * 外部绑定表id
         */
        private Integer extBindId;

        private String projectName;
        private String  type;
    }

    @Data
    public static class CalculatorQueryConditionReq extends RequestModel {
        private Integer extBindId;
        private String  type;
        private Boolean broadcastFlag = false;
    }

    @Data
    public static class QueryHistoryReq extends RequestModel {
        /**
         * 外部绑定表id
         */
        private Integer extBindId;

        private String type;
        private String province;
        private String city;
        private String area;
        private String projectManager;
    }

    @Data
    public static class QueryBroadcastReq extends RequestModel {
        /**
         * 外部绑定表id
         */
        private Integer extBindId;

        private String type;
        private String province;
        private String city;
        private String area;
        private String projectManager;
    }

    @Data
    public static class DebtCalculatorReq extends RequestModel {
        /**
         * 外部绑定表id
         */
        private Integer              extBindId;
        /**
         * 项目名称
         */
        private String               projectName;
        /**
         * 债权本金
         */
        private java.math.BigDecimal debtPrincipal;
        /**
         * 债权利息
         */
        private java.math.BigDecimal debtInterest;
        /**
         * 债权最高额
         */
        private java.math.BigDecimal maximumDebt;
        /**
         * 清收目标金额
         */
        private java.math.BigDecimal liquidationAmount;
        /**
         * 转让价格
         */
        private java.math.BigDecimal transferAmount;
        /**
         * 自有资金GP
         */
        private java.math.BigDecimal gpAmount;
        /**
         * 需要配资LP金额
         */
        private java.math.BigDecimal lpAmount;
        /**
         * 配资年化费率
         */
        private java.math.BigDecimal withFundingAnnualizedRate;
//        /**
//         * 配资周期开始
//         */
//        private java.util.Date withFundingPeriodStart;
//        /**
//         * 配资周期结束
//         */
//        private java.util.Date withFundingPeriodEnd;
        /**
         * 配资周期
         */
        private BigDecimal           withFundingPeriod;
        /**
         * 处置成本-年化费率
         */
        private java.math.BigDecimal disposalAnnualizedRate;
        /**
         * 处置周期
         */
        private java.math.BigDecimal disposalPeriod;
        /**
         * 尽调成本
         */
        private java.math.BigDecimal dueDiligenceAmount;
        /**
         * 杂费
         */
        private java.math.BigDecimal miscAmount;

        private String projectManager;
        private String province;
        private String city;
        private String area;
    }

    @Data
    public static class PrincipalInterestCalculatorReq extends RequestModel {
        /**
         * 外部绑定表id
         */
        private Integer              extBindId;
        /**
         * 项目名称
         */
        private String               projectName;
        /**
         * 贷款期限
         */
        private java.math.BigDecimal loanPeriod;
        /**
         * 剩余本金
         */
        private java.math.BigDecimal remainPrincipal;
        /**
         * 浮动利率
         */
        private java.math.BigDecimal fluctuationRate;
        /**
         * 欠息
         */
        private java.math.BigDecimal debtInterest;
        /**
         * 逾期时间开始
         */
        private java.util.Date       overdueStart;
        /**
         * 逾期时间结束
         */
        private java.util.Date       overdueEnd;
        /**
         * 罚息比例
         */
        private java.math.BigDecimal penaltyRate;
        /**
         * 基准利率来源
         */
        private java.lang.String     benchmarkInterestRateSource;
        /**
         * 延迟履行期开始
         */
        private java.util.Date       delayPerformanceStart;
        /**
         * 延迟履行期结束
         */
        private java.util.Date       delayPerformanceEnd;

        private String projectManager;
        private String province;
        private String city;
        private String area;

    }

    @Data
    public static class CalculatorBroadcastPayReq extends RequestModel {
        private Integer calculatorId;
        // 类型（0 本息计算器 1 债权计算器）
        private String  type;
    }

    @Data
    public static class CalculatorBroadcastPayCallBackReq extends RequestModel {
        private String orderId;
    }
}
