package com.winback.gateway.controller.req.push;

import lombok.Data;

import java.io.Serializable;

/**
 * 描述：单体推送请求参数
 * <p>
 * 作者： wuchuanqi
 * 版本： 1.0.0
 * 时间： 2018/8/17 18:34
 */
@Data
public class SinglePushReq implements Serializable {

    private String clientId;//发送手机的手机码

    private String title;//推送题目

    private String text;//消息

    private String logo;

    private String logoUrl;

    private String url;

    private String busType; //业务类型

    private String majorKey; //跳转的关键id

    private Integer msgType;//消息类型


    private String channel; //渠道



}
