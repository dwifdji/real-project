package com.winback.core.facade.account.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author xdrodger
 * @Title: AppletAccountInfo
 * @ProjectName winback
 * @Description:
 * @date 2019/2/12 14:45
 */
@Data
public class AppletAccountInfo implements Serializable {
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
