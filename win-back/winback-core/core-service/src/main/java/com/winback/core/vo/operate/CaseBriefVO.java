package com.winback.core.vo.operate;

import java.io.Serializable;

public class CaseBriefVO implements Serializable {

    private String  briefId;

    private String name;

    public String getBriefId() {
        return briefId;
    }

    public void setBriefId(String briefId) {
        this.briefId = briefId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
