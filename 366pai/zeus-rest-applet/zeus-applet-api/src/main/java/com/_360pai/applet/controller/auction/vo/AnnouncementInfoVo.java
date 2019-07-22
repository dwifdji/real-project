package com._360pai.applet.controller.auction.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * 描述：
 * <p>
 * 作者： wuchuanqi
 * 版本： 1.0.0
 * 时间： 2018/11/26 13:23
 */
@Data
public class AnnouncementInfoVo implements Serializable {

    private String announcement;//拍品的公告
    private String inform;//特别告知
    private String instructions;//竞买须知
}
