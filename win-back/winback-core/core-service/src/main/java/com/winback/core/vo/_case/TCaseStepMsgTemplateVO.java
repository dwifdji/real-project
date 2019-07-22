package com.winback.core.vo._case;

import java.io.Serializable;

public class TCaseStepMsgTemplateVO implements Serializable {

    private String stepId;

    private String stepName;

    private String templateId;

    private String templateBody;

    private String branchFlag;


    public String getBranchFlag() {
        return branchFlag;
    }

    public void setBranchFlag(String branchFlag) {
        this.branchFlag = branchFlag;
    }

    public String getStepId() {
        return stepId;
    }

    public void setStepId(String stepId) {
        this.stepId = stepId;
    }

    public String getStepName() {
        return stepName;
    }

    public void setStepName(String stepName) {
        this.stepName = stepName;
    }

    public String getTemplateId() {
        return templateId;
    }

    public void setTemplateId(String templateId) {
        this.templateId = templateId;
    }

    public String getTemplateBody() {
        return templateBody;
    }

    public void setTemplateBody(String templateBody) {
        this.templateBody = templateBody;
    }
}
