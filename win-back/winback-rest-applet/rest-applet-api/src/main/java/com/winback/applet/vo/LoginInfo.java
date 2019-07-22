package com.winback.applet.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author xdrodger
 * @Title: AccountInfo
 * @ProjectName winback
 * @Description:
 * @date 2019/1/25 15:30
 */
@Data
public class LoginInfo implements Serializable {
    /**
     * 主键
     */
    private Integer id;
    /**
     * 手机号
     */
    private String mobile;
    /**
     * 账户id
     */
    private Integer accountId;
    /**
     * 昵称
     */
    private String nickName;
    /**
     * 头像
     */
    private String headImgUrl;
    /**
     * 未读消息数量
     */
    private int unreadMessageCount;
    /**
     * 最新合同下载邮箱地址
     */
    private String latestDownloadEmail;
    /**
     * 收藏合同数量
     */
    private int favoriteContractCount = 0;
}
