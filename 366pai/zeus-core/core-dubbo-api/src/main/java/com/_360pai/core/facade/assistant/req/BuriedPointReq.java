package com._360pai.core.facade.assistant.req;

import com._360pai.arch.common.RequestModel;
import lombok.Getter;
import lombok.Setter;

/**
 * 描述
 *
 * @author : whisky_vip
 * @date : 2018/10/8 12:37
 */
@Getter
@Setter
public class BuriedPointReq extends RequestModel {
    private String pointKey;
    private String pointDesc;
    private String pointType;
    private String buzId;
    private String buzParams;

    /**
     * 下面的参数不需要前端传
     */
    private String deviceMark;
    private String deviceType;
    private String userId;
    private String userType;
    private String createTime;
    private String updateTime;
    private String city;
    private String province;
    private String ip;
}
