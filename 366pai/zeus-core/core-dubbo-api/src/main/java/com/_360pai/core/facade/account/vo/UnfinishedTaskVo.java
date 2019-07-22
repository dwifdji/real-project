package com._360pai.core.facade.account.vo;

import com.alibaba.fastjson.JSONObject;
import lombok.Data;

import java.io.Serializable;

/**
 * @author xdrodger
 * @Title: UnfinishedTaskVo
 * @ProjectName zeus
 * @Description:
 * @date 2019/1/15 13:53
 */
@Data
public class UnfinishedTaskVo implements Serializable {
    private String message;
    /**
     * 1 已登录未认证用户提示
     * 2 已登录已认证未开通法大大/电子签章用户提示
     * 3 关注拍品/招商的用户，结束前五分钟提示
     * 4 关注拍品/招商的用户，开始前五分钟提示
     * 5 提醒拍品/招商的用户，结束前五分钟提示
     * 6 提醒拍品/招商的用户，开始前五分钟提示
     * 7 参与拍卖已交保证金用户，结束前五分钟提示
     * 8 参与拍卖已交保证金用户，开始前五分钟提示
     * 9 线上拍品，拍卖成功未交尾款的用户
     */
    private String type;
    private JSONObject parameters;
}
