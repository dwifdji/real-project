package com.winback.web.vo;

import com.winback.core.facade.account.vo.Lawyer;
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
    private java.lang.Integer id;
    /**
     * 手机号
     */
    private java.lang.String mobile;
    /**
     * 昵称
     */
    private java.lang.String nickName;
    /**
     * 头像
     */
    private java.lang.String headImgUrl;
    /**
     * 律师标志
     */
    private boolean lawyerFlag;
    /**
     * 加盟商标志
     */
    private boolean franchiseeFlag;
    /**
     * 律师认证状态
     */
    private String lawyerApplyStatus;
    /**
     * 加盟商认证状态
     */
    private String franchiseeApplyStatus;
    /**
     * 未读消息数量
     */
    private int unreadMessageCount;
    /**
     * 律师信息
     */
    private Lawyer lawyer;

    /**
     * 全部的未读消息 消息中心 + 沟通
     */
    private int unreadMessageAll;
    /**
     * 项目经理标志
     */
    private java.lang.Boolean projectManagerFlag;

}