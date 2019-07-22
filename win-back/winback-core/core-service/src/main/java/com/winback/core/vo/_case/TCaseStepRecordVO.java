package com.winback.core.vo._case;

import java.io.Serializable;

public class TCaseStepRecordVO implements Serializable {

    private String id;

    private String createTime;

    private String stepName;

    private String lawyer;

    private String pushMsg;

    private String branchFlag;

    public String getBranchFlag() {
        return branchFlag;
    }

    public void setBranchFlag(String branchFlag) {
        this.branchFlag = branchFlag;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getStepName() {
        return stepName;
    }

    public void setStepName(String stepName) {
        this.stepName = stepName;
    }

    public String getLawyer() {
        return lawyer;
    }

    public void setLawyer(String lawyer) {
        this.lawyer = lawyer;
    }

    public String getPushMsg() {
        return pushMsg;
    }

    public void setPushMsg(String pushMsg) {
        this.pushMsg = pushMsg;
    }
}
