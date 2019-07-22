package com.winback.core.vo.operate;

import java.io.Serializable;
import java.util.List;

public class HomeIconGroupTypeVO implements Serializable {
    private String type;

    private String typeDesc;

    private List<OperateIconListVO> operateIconListVOS;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTypeDesc() {
        return typeDesc;
    }

    public void setTypeDesc(String typeDesc) {
        this.typeDesc = typeDesc;
    }

    public List<OperateIconListVO> getOperateIconListVOS() {
        return operateIconListVOS;
    }

    public void setOperateIconListVOS(List<OperateIconListVO> operateIconListVOS) {
        this.operateIconListVOS = operateIconListVOS;
    }

    public HomeIconGroupTypeVO(String type, String typeDesc) {
        this.type = type;
        this.typeDesc = typeDesc;
    }
}
