package com._360pai.gateway.controller.req.fdd;

import java.io.Serializable;

/**
 * 描述：法大大生成合同公共请求
 * <p>
 * 作者： wuchuanqi
 * 版本： 1.0.0
 * 时间： 2018/9/1 9:29
 */
public class GenerateContractComReq implements Serializable {

    private String type; //生成合同类型 1 委托协议 2 委托补充协议 3 成交协议  4 预招商委托协议 5 银行类委托模板

    private String activityId;//签章活动id

    private String partyId;//签章的用户id

    private String fddId;//法大大的用户id

    private Object param;//签章参数

    public Object getParam() {
        return param;
    }

    public void setParam(Object param) {
        this.param = param;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getActivityId() {
        return activityId;
    }

    public void setActivityId(String activityId) {
        this.activityId = activityId;
    }

    public String getPartyId() {
        return partyId;
    }

    public void setPartyId(String partyId) {
        this.partyId = partyId;
    }

    public String getFddId() {
        return fddId;
    }

    public void setFddId(String fddId) {
        this.fddId = fddId;
    }
}
