
package com._360pai.core.condition.fdd;

import com._360pai.arch.core.abs.DaoCondition;

/**
 * <p>用于封装查询条件</p>
 * <p>默认条件下仅生成数据表字段的查询条件，更多条件，请自行添加</p>
 * @author Generator
 * @date 2018年08月31日 17时14分42秒
 */
public class GatewayFddTemplateCondition implements DaoCondition{

    /**
     *
     */
    private Integer id;
    /**
     *
     */
    private String type;
    /**
     *
     */
    private java.util.Date createdAt;
    /**
     *
     */
    private String docTitle;
    /**
     *
     */
    private String templateId;
    /**
     *
     */
    private String isValid;
    /**
     *
     */
    private String docUrl;
    /**
     *
     */
    private Integer version;

    /**
     *
     */
    public Integer getId(){
        return id;
    }

    /**
     *
     */
    public void setId(Integer id){
        this.id = id;
    }

    /**
     *
     */
    public String getType(){
        return type;
    }

    /**
     *
     */
    public void setType(String type){
        this.type = type;
    }

    /**
     *
     */
    public java.util.Date getCreatedAt(){
        return createdAt;
    }

    /**
     *
     */
    public void setCreatedAt(java.util.Date createdAt){
        this.createdAt = createdAt;
    }

    /**
     *
     */
    public String getDocTitle(){
        return docTitle;
    }

    /**
     *
     */
    public void setDocTitle(String docTitle){
        this.docTitle = docTitle;
    }

    /**
     *
     */
    public String getTemplateId(){
        return templateId;
    }

    /**
     *
     */
    public void setTemplateId(String templateId){
        this.templateId = templateId;
    }

    /**
     *
     */
    public String getIsValid(){
        return isValid;
    }

    /**
     *
     */
    public void setIsValid(String isValid){
        this.isValid = isValid;
    }

    /**
     *
     */
    public String getDocUrl(){
        return docUrl;
    }

    /**
     *
     */
    public void setDocUrl(String docUrl){
        this.docUrl = docUrl;
    }

    /**
     *
     */
    public Integer getVersion(){
        return version;
    }

    /**
     *
     */
    public void setVersion(Integer version){
        this.version = version;
    }



}