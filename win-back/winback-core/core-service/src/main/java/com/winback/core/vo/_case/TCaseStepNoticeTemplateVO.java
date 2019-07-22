package com.winback.core.vo._case;

import java.io.Serializable;

public class TCaseStepNoticeTemplateVO implements Serializable {

    /**
     *
     */
    private Integer id;
    /**
     * 案件大类步骤名称
     */
    private  String name;

    /**
     * 名称描述
     */
    private String nameDesc;
    /**
     * 步骤id
     */
    private Integer stepId;
    /**
     * 通知类型 1 app 2 短信 3 邮件 4 app、邮件 5 短信、邮件
     */
    private  Integer type;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNameDesc() {
        return nameDesc;
    }

    public void setNameDesc(String nameDesc) {
        this.nameDesc = nameDesc;
    }

    public Integer getStepId() {
        return stepId;
    }

    public void setStepId(Integer stepId) {
        this.stepId = stepId;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
}
