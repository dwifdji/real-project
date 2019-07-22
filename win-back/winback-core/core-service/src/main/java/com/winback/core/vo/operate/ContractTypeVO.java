package com.winback.core.vo.operate;

import java.io.Serializable;

public class ContractTypeVO implements Serializable {
    private String typeId;

    private String name;

    public String getTypeId() {
        return typeId;
    }

    public void setTypeId(String typeId) {
        this.typeId = typeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
