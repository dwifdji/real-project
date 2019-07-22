package com.winback.applet.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author xdrodger
 * @Title: Profile
 * @ProjectName winback
 * @Description:
 * @date 2019/1/25 15:27
 */
@Data
public class Profile implements Serializable {
    /**
     * 主键
     */
    private Integer id;
    /**
     * 手机号
     */
    private String mobile;
    /**
     * 昵称
     */
    private String nickName;
    /**
     * 头像
     */
    private String headImgUrl;
    /**
     * 是否绑定账户（0 否 1 是）
     */
    private boolean bindAccountFlag = false;
    /**
     * 未读消息数量
     */
    private int unreadMessageCount = 0;
    /**
     * 最新合同下载邮箱地址
     */
    private String latestDownloadEmail;
    /**
     * 收藏合同数量
     */
    private int favoriteContractCount = 0;

}
