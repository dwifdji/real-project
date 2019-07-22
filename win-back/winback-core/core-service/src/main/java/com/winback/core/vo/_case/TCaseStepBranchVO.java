package com.winback.core.vo._case;

import java.io.Serializable;

public class TCaseStepBranchVO implements Serializable {

    private String branchId;

    private String branchName;

    private String branchType;

    private String branchOrder;

    private String branchNameDesc;

    private Integer branchFlag;

    private String branchNextId;

    private String branchNextName;

    private String stepId;


    public String getBranchNextId() {
        return branchNextId;
    }

    public void setBranchNextId(String branchNextId) {
        this.branchNextId = branchNextId;
    }

    public String getBranchNextName() {
        return branchNextName;
    }

    public void setBranchNextName(String branchNextName) {
        this.branchNextName = branchNextName;
    }

    public String getBranchId() {
        return branchId;
    }

    public void setBranchId(String branchId) {
        this.branchId = branchId;
    }

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    public String getBranchType() {
        return branchType;
    }

    public void setBranchType(String branchType) {
        this.branchType = branchType;
    }

    public String getBranchOrder() {
        return branchOrder;
    }

    public void setBranchOrder(String branchOrder) {
        this.branchOrder = branchOrder;
    }

    public String getBranchNameDesc() {
        return branchNameDesc;
    }

    public void setBranchNameDesc(String branchNameDesc) {
        this.branchNameDesc = branchNameDesc;
    }

    public Integer getBranchFlag() {
        return branchFlag;
    }

    public void setBranchFlag(Integer branchFlag) {
        this.branchFlag = branchFlag;
    }

    public String getStepId() {
        return stepId;
    }

    public void setStepId(String stepId) {
        this.stepId = stepId;
    }
}
