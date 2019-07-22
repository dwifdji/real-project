package com.winback.core.vo.operate;

import java.io.Serializable;

public class CaseStepVO implements Serializable {

    private String name;

    private String stepId;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStepId() {
        return stepId;
    }

    public void setStepId(String stepId) {
        this.stepId = stepId;
    }
}
