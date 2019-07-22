package com.winback.core.vo.finance;

import com.winback.core.commons.constants.WorkBenchEnum;

import java.io.Serializable;

public class WorkBenchVO implements Serializable {

    private String type;

    private String typeDesc;

    private String total;

    private String hint;

    private String params;

    public String getParams() {
        return params;
    }

    public void setParams(String params) {
        this.params = params;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
        this.typeDesc = WorkBenchEnum.WorkBenchType.getTypeDescByType(type);
        this.hint = WorkBenchEnum.WorkBenchType.getHintByType(type);
    }

    public String getTypeDesc() {
        return typeDesc;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getHint() {
        return hint;
    }
}
