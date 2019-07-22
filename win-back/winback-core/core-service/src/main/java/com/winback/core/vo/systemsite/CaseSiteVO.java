package com.winback.core.vo.systemsite;

import com.winback.core.commons.constants.SystemSiteEnum;

import java.io.Serializable;

public class CaseSiteVO implements Serializable {

    private String id;

    private String name;

    private String type;

    private String typeDesc;

    private String code;

    private Integer status;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
        this.typeDesc = SystemSiteEnum.CaseSiteType.getDescByType(type);
    }

    public String getTypeDesc() {
        return typeDesc;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
