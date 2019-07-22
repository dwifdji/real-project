package com._360pai.core.condition.assistant;

import com._360pai.arch.core.abs.DaoCondition;
import lombok.Data;

/**
 * 描述
 *
 * @author : whisky_vip
 * @date : 2019/4/25 16:52
 */
@Data
public class DetailViewRecodeCondition implements DaoCondition {
    private Integer           id;
    /**
     * 1 拍卖,2,预招商
     */
    private Short             recodeType;
    private Integer           activityId;
    private java.lang.Integer accountId;
    private java.lang.Integer partyId;
    /**
     * '0 web,1,applet',
     */
    private Short             pathType;
    private java.util.Date    createTime;
}
