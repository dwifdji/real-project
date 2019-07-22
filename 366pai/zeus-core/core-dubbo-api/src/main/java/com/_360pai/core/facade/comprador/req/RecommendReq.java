package com._360pai.core.facade.comprador.req;

import java.io.Serializable;

/**
 * 描述
 *
 * @author : whisky_vip
 * @date : 2018/9/4 11:27
 */
public class RecommendReq implements Serializable {
    private Integer recommendId;

    private Integer operatorId;

    public Integer getOperatorId() {
        return operatorId;
    }

    public void setOperatorId(Integer operatorId) {
        this.operatorId = operatorId;
    }

    public Integer getRecommendId() {
        return recommendId;
    }

    public void setRecommendId(Integer recommendId) {
        this.recommendId = recommendId;
    }
}
