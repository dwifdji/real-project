package com.tzCloud.gateway.controller.req.push;

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

    private String text;//内容

    private String logo;

    private String logoUrl;

    private String url;


    private String busType; //业务类型


}
