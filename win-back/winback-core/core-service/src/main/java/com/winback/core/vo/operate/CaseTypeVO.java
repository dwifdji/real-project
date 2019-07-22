package com.winback.core.vo.operate;

import java.io.Serializable;

public class CaseTypeVO implements Serializable {

    private String name;

    private String caseTypeId;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCaseTypeId() {
        return caseTypeId;
    }

    public void setCaseTypeId(String caseTypeId) {
        this.caseTypeId = caseTypeId;
    }
}
