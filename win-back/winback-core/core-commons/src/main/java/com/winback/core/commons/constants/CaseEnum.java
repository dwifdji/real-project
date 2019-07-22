package com.winback.core.commons.constants;

import java.util.ArrayList;
import java.util.List;

/**
 * @author RuQ
 * @Title: CaseEnum
 * @ProjectName winback
 * @Description:
 * @date 2019/1/29 15:59
 */
public class CaseEnum {

    public enum MainStatus {

        INIT("INIT", "待资料预检"),
        PRE_CHECK_SUCCESS("PRE_CHECK_SUCCESS", "待风控审核"),
        PRE_CHECK_FAIL("PRE_CHECK_FAIL", "资料预检失败"),
        LAWSUIT_RISK_CHECK_SUCCESS("LAWSUIT_RISK_CHECK_SUCCESS", "诉讼风控审核通过"),
        LAWSUIT_RISK_CHECK_FAIL("LAWSUIT_RISK_CHECK_FAIL", "诉讼风控审核失败"),
        EXECUTE_RISK_CHECK_SUCCESS("EXECUTE_RISK_CHECK_SUCCESS", "执行风控审核通过"),
        EXECUTE_RISK_CHECK_FAIL("EXECUTE_RISK_CHECK_FAIL", "执行风控审核失败"),
        RISK_CHECK_SUCCESS("RISK_CHECK_SUCCESS", "合同待签约"),
        RISK_CHECK_FAIL("RISK_CHECK_FAIL", "风控审核拒绝"),
        HAS_SIGN_CONTRACT("HAS_SIGN_CONTRACT", "待放款审核"),
        HAS_LOAN("HAS_LOAN", "财务已放款"),
        BEING_LAWSUIT("BEING_LAWSUIT", "案件进入诉讼阶段"),
        BEING_EXECUTE("BEING_EXECUTE", "案件进入执行阶段"),
        WAIT_RECEIVED_PAY("WAIT_RECEIVED_PAY", "等待回款"),
        SUCCESS("SUCCESS", "已完成");

        private final String key;
        private final String value;

        MainStatus(String key, String value) {
            this.key = key;
            this.value = value;
        }

        public String getKey() {
            return key;
        }

        public String getValue() {
            return value;
        }

        public static String getValueByKey(String key) {
            MainStatus[] values = MainStatus.values();
            for (MainStatus status : values) {
                if (status.key.equals(key)) {
                    return status.value;
                }
            }
            return null;
        }

        private static List<MainStatus> signedContractList = new ArrayList<>();
        static {
            signedContractList.add(MainStatus.HAS_SIGN_CONTRACT);
            signedContractList.add(MainStatus.HAS_LOAN);
            signedContractList.add(MainStatus.BEING_LAWSUIT);
            signedContractList.add(MainStatus.BEING_EXECUTE);
            signedContractList.add(MainStatus.WAIT_RECEIVED_PAY);
            signedContractList.add(MainStatus.SUCCESS);
        }

        public static boolean hasSignContract(String key) {
            for (MainStatus status : signedContractList) {
                if (status.key.equals(key)) {
                    return true;
                }
            }
            return false;
        }
    }


    public enum CaseStep {

        INIT("INIT", "申请成功"),
        ADD_INFO("ADD_INFO", "补充资料"),
        PRE_CHECK_SUCCESS("PRE_CHECK_SUCCESS", "资料预检通过"),
        PRE_CHECK_FAIL("PRE_CHECK_FAIL", "资料预检失败"),
        LAWSUIT_RISK_CHECK_SUCCESS("LAWSUIT_RISK_CHECK_SUCCESS", "诉讼风控审核通过"),
        LAWSUIT_RISK_CHECK_FAIL("LAWSUIT_RISK_CHECK_FAIL", "诉讼风控审核失败"),
        EXECUTE_RISK_CHECK_SUCCESS("EXECUTE_RISK_CHECK_SUCCESS", "执行风控审核通过"),
        EXECUTE_RISK_CHECK_FAIL("EXECUTE_RISK_CHECK_FAIL", "执行风控审核失败"),
        RISK_ADD_INFO("RISK_ADD_INFO", "风控上传资料"),
        SCHEDULE_ACCEPTED_LAWYER("SCHEDULE_ACCEPTED_LAWYER", "安排承办律师"),
        HAS_SIGN_CONTRACT("HAS_SIGN_CONTRACT", "合同线下签约成功"),
        ADD_LOAN_APPLY("ADD_LOAN_APPLY", "添加放款申请"),
        HAS_LOAN("HAS_LOAN", "财务审核放款"),
        BEING_LAWSUIT("BEING_LAWSUIT", "案件进入诉讼阶段"),
        BEING_EXECUTE("BEING_EXECUTE", "案件进入执行阶段"),
        LAWYER_UPDATE_STATUS("LAWYER_UPDATE_STATUS", "律师更新案件进度"),
        ADD_RECEIVED_APPLY("ADD_RECEIVED_APPLY", "添加回款申请"),
        HAS_RECEIVED_PAY("HAS_RECEIVED_PAY", "财务审核回款"),
        ADD_INVOICE_APPLY("ADD_INVOICE_APPLY", "添加开票申请"),
        HAS_INVOICED("HAS_INVOICED", "财务审核开票"),
        END_LAWSUIT("END_LAWSUIT", "完成诉讼"),
        END_EXECUTE("END_EXECUTE", "完成执行"),
        LAWSUIT_EXECUTE("LAWSUIT_EXECUTE", "诉讼执行"),
        SUCCESS("SUCCESS", "已完成");

        private final String key;
        private final String value;

        CaseStep(String key, String value) {
            this.key = key;
            this.value = value;
        }

        public String getKey() {
            return key;
        }

        public String getValue() {
            return value;
        }

        public static String getValueByKey(String key) {
            CaseStep[] values = CaseStep.values();
            for (CaseStep status : values) {
                if (status.key.equals(key)) {
                    return status.value;
                }
            }
            return null;
        }
    }


    public enum MainSource {

        ONLINE("ONLINE", "线上"),
        OFFLINE("OFFLINE", "线下");

        private final String key;
        private final String value;

        MainSource(String key, String value) {
            this.key = key;
            this.value = value;
        }

        public String getKey() {
            return key;
        }

        public String getValue() {
            return value;
        }
    }

    public enum SubSource {

        WEB("WEB", "网站"),
        APPLET("APPLET", "小程序"),
        APP("APP", "APP"),
        CLIENT("CLIENT", "当事人"),
        LAWYER("LAWYER", "律师"),
        FRANCHISEE("FRANCHISEE", "加盟商"),
        ONLINE_OTHER("ONLINE_OTHER", "线上其他"),

        ADMIN("ADMIN", "后台录入"),
        INTERMEDIARY("INTERMEDIARY", "中介"),
        LAW_FIRM("LAW_FIRM", "律所"),
        BD("BD", "BD"),
        ;

        private final String key;
        private final String value;

        SubSource(String key, String value) {
            this.key = key;
            this.value = value;
        }

        public String getKey() {
            return key;
        }

        public String getValue() {
            return value;
        }

        public static String getValueByKey(String key) {
            SubSource[] values = SubSource.values();
            for (SubSource status : values) {
                if (status.key.equals(key)) {
                    return status.value;
                }
            }
            return "";
        }
    }

    public enum ApplyUserType {

        CLIENT("CLIENT", "当事人"),
        LAWYER("LAWYER", "律师"),
        FRANCHISEE("FRANCHISEE", "加盟商");

        private final String key;
        private final String value;

        ApplyUserType(String key, String value) {
            this.key = key;
            this.value = value;
        }

        public String getKey() {
            return key;
        }

        public String getValue() {
            return value;
        }
    }

    public enum CasePhase {

        NO_PUT_ON_RECORD("NO_PUT_ON_RECORD", "未立案"),
        PUT_ON_RECORD("PUT_ON_RECORD", "立案"),
        FIRST_VERIFY("FIRST_VERIFY", "一审"),
        SECOND_VERIFY("SECOND_VERIFY", "二审"),
        REPEAT_VERIFY("REPEAT_VERIFY", "重审"),
        AGAIN_VERIFY("AGAIN_VERIFY", "再审"),
        EXECUTE("EXECUTE", "执行"),
        FORCE_EXECUTE("FORCE_EXECUTE", "强制执行"),
        UNKNOWN("UNKNOWN", "不详");

        private final String key;
        private final String value;

        CasePhase(String key, String value) {
            this.key = key;
            this.value = value;
        }

        public String getKey() {
            return key;
        }

        public String getValue() {
            return value;
        }
    }

    public enum CaseType {

        RISK("RISK", "风险投"),
        UNITE_EXECUTE("UNITE_EXECUTE", "联合执行"),
        LAWSUIT_EXECUTE("LAWSUIT_EXECUTE", "诉讼执行"),
        ENTRUST_EXECUTE("ENTRUST_EXECUTE", "委托执行"),
        OBLIGATION_PURCHASE("OBLIGATION_PURCHASE", "债权收购");

        private final String key;
        private final String value;

        CaseType(String key, String value) {
            this.key = key;
            this.value = value;
        }

        public String getKey() {
            return key;
        }

        public String getValue() {
            return value;
        }
    }


    public enum CaseAttachmentMainStatus {

        PRE_CHECK("PRE_CHECK", "资料预检"), RISK_CHECK("RISK_CHECK", "风控审核"), SIGN_CONTRACT("SIGN_CONTRACT", "合同签约");

        private final String key;
        private final String value;

        CaseAttachmentMainStatus(String key, String value) {
            this.key = key;
            this.value = value;
        }

        public String getKey() {
            return key;
        }

        public String getValue() {
            return value;
        }
    }

    public enum CaseAttachmentSubStatus {

        LAWSUIT_CONTRACT("LAWSUIT_CONTRACT", "诉讼投资合同"),
        EMPOWER_DOC("EMPOWER_DOC", "授权文书");

        private final String key;
        private final String value;

        CaseAttachmentSubStatus(String key, String value) {
            this.key = key;
            this.value = value;
        }

        public String getKey() {
            return key;
        }

        public String getValue() {
            return value;
        }
    }

    public enum CaseRecordType {

        STEP("STEP", "状态流转"),
        OPERATE("OPERATE", "操作记录");

        private final String key;
        private final String value;

        CaseRecordType(String key, String value) {
            this.key = key;
            this.value = value;
        }

        public String getKey() {
            return key;
        }

        public String getValue() {
            return value;
        }
    }

    public enum CaseLawyerOrderStatus {

        INIT("INIT", "申请接单"),
        ACCEPTED("ACCEPTED", "已委托"),
        SUCCESS("SUCCESS", "已完成"),
        REFUSED("REFUSED", "已驳回");

        private final String key;
        private final String value;

        CaseLawyerOrderStatus(String key, String value) {
            this.key = key;
            this.value = value;
        }

        public static String getValueByKey(String key) {
            CaseLawyerOrderStatus[] values = CaseLawyerOrderStatus.values();
            for (CaseLawyerOrderStatus status : values) {
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

}
