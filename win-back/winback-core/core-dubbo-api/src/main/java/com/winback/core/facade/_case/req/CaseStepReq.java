package com.winback.core.facade._case.req;

import com.winback.arch.common.RequestModel;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

public class CaseStepReq {

    @Setter
    @Getter
    public static class CaseStepListReq extends RequestModel {
        private String id;
        private String type;

    }

    @Setter
    @Getter
    public static class SaveCaseStepReq extends RequestModel{
        private String type;

        private String haveBranchFlag;

        private String name;

        private String nameDesc;

        private String nextId;

        private String groupId;

        private String stepId;

        private List<CaseBranchReq> caseBranchReqList;
    }


    @Getter
    @Setter
    public static class UpdateOrDeleteReq extends RequestModel{

        private String id;

        private String type;

        private String name;

        private String nameDesc;

        private String nextId;

        private String groupId;

        private String stepId;

        private String orderFlag;

        /**
         * 1 是刪除 0 修改
         */
        private String updateFlag;

        private List<CaseBranchReq> caseBranchReqList;
    }

    @Setter
    @Getter
    public static class CaseBranchReq extends RequestModel{
        private String name;

        private String nameDesc;

        private String nextId;
    }

    @Setter
    @Getter
    public static class UpdateBranchReq extends RequestModel{
        private  String id;

        private String orderFlag;

        private String stepId;
    }

    @Setter
    @Getter
    public static class StepTemplateSaveReq extends RequestModel{
        private String name;

        private String nameDesc;

        private String stepId;

        private String branchFlag;

    }

    @Setter
    @Getter
    public static class StepTemplateUpdateReq extends RequestModel{
        private String id;

        private String updateFlag;

        private String name;

        private String nameDesc;

    }

    @Setter
    @Getter
    public static class CaseTemplateReq extends RequestModel{

        private String id;

        private String branchFlag;

    }


    @Setter
    @Getter
    public static class CaseStepRecordSaveReq extends RequestModel{

        private String type;

        private String caseId;

        private String stepId;

        private String pushMsg;

        private String accountId;

        private String branchFlag;

        private String updateSource;

    }


    @Setter
    @Getter
    public static class CaseStepRecordListReq extends RequestModel{
        private String caseId;

        private String type;
    }


    @Setter
    @Getter
    public static class StatusCaseReq extends RequestModel{

        private String caseId;

        private String litigant;

        private String beginTime;

        private String endTime;

        private String caseTypeId;

        private String stepId;

        private String type; //1诉讼2执行
    }

    @Setter
    @Getter
    public static class CaseStepRecordReq extends RequestModel{

        private String caseId;

        private String stepId;

        private String branchFlag;

        private String pushMsg;

        private String type; //1诉讼2执行
    }

}
