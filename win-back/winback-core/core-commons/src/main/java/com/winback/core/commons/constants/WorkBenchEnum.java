package com.winback.core.commons.constants;

/**
 * create by liuahaolei on 2018/02/13
 */
public class WorkBenchEnum {

    public enum WorkBenchType{

        FINANCE_EXPEND("1", "放款待审核", "放款待审核单数，请及时处理"),
        FINANCE_INVOICE("2", "发票待开具", "发票待开具单数，请及时处理"),
        FINANCE_RECEIVABLE("3", "回款待审核", "回款待审核单数，请及时处理"),
        PENDING_CHECKING("4", "资料待预检", "资料待预检单数，请及时处理"),
        PENDING_CONTRACT("5", "合同待签约", "合同待签约数单数，请及时处理"),
        WIND_CONTROL_REVIEW("6", "风控待审核", "风控待审核单数，请及时处理"),
        LITIGATION_REVIEW_REJECTION("7", "进入诉讼阶段", "进入诉讼阶段诉讼单数，请及时跟进维系"),
        EXECUTE_REVIEW_REJECTION("8", "进入执行阶段", "进入执行阶段单数，请及时跟进维系"),
        TODAY_USER("9", "今日新增用户", "今日新增用户，请及时联系"),
        TODAY_LAWYER("10", "今日新增律师", "今日新增律师，请及时联系"),
        TODAY_LAW_FIRM("11", "今日新增律所", "今日新增律所，请及时联系"),
        TODAY_CASE("12", "今日新增案件", "今日新增案件，请及时处理");
        private String type;

        private String typeDesc;

        private String hint;



        public String getHint() {
            return hint;
        }

        public void setHint(String hint) {
            this.hint = hint;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getTypeDesc() {
            return typeDesc;
        }

        public void setTypeDesc(String typeDesc) {
            this.typeDesc = typeDesc;
        }

        WorkBenchType(String type, String typeDesc, String hint) {
            this.type = type;
            this.typeDesc = typeDesc;
            this.hint = hint;
        }

        public static String getTypeDescByType(String type) {
            WorkBenchType[] values = WorkBenchType.values();

            for (WorkBenchType workBenchType: values) {
                if(type.equals(workBenchType.getType())) {
                    return workBenchType.getTypeDesc();
                }
            }
            return null;
        }


        public static String getHintByType(String type) {
            WorkBenchType[] values = WorkBenchType.values();

            for (WorkBenchType workBenchType: values) {
                if(type.equals(workBenchType.getType())) {
                    return workBenchType.getHint();
                }
            }
            return null;
        }
    }
}
