package com._360pai.core.facade.comprador.req;

import java.io.Serializable;

/**
 * 描述
 *
 * @author : whisky_vip
 * @date : 2018/9/4 10:38
 */
public class RequiementRecommenderRemarkReq implements Serializable {
    /**
     * 审核数据内容
     */
    private String  remark;
    /**
     * 审核对应的 数据id
     */
    private Integer requirementRecommenderId;


    private Integer operatorId;

    public Integer getOperatorId() {
        return operatorId;
    }

    public void setOperatorId(Integer operatorId) {
        this.operatorId = operatorId;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getRequirementRecommenderId() {
        return requirementRecommenderId;
    }

    public void setRequirementRecommenderId(Integer requirementRecommenderId) {
        this.requirementRecommenderId = requirementRecommenderId;
    }
}
