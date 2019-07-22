package com._360pai.core.model.assistant;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

/**
 * 描述
 *
 * @author : whisky_vip
 * @date : 2019/4/25 16:49
 */
@Getter
@Setter
@NoArgsConstructor
public class DetailViewRecode implements java.io.Serializable {

    private Integer id;

    private Integer activityId;
    private Integer accountId;
    private Integer partyId;

    /**
     * 1 拍卖,2,预招商
     */
    private Short recodeType;

    /**
     * '0 web,1,applet',
     */
    private Short          pathType;
    private java.util.Date createTime = new Date();

    public DetailViewRecode(Short recodeType, Short pathType) {
        this.recodeType = recodeType;
        this.pathType = pathType;
        this.createTime = new Date();
    }

    public DetailViewRecode build(Integer activityId, Integer accountId, Integer partyId) {
        this.setPartyId(partyId);
        this.setAccountId(accountId);
        this.setActivityId(activityId);
        return this;
    }
}
