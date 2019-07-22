package com.tzCloud.core.facade.legalEngine.resp;

import lombok.Data;

import java.io.Serializable;

@Data
public class LawyerInfoVO implements Serializable
{
    private static final long serialVersionUID = 4316776948589304023L;

    private Long id;
    /**
     * 律师名称
     */
    private String lawyerName;
    /**
     * 律所所在城市
     */
    private String lawFirmCity;
    /**
     * 律所名称
     */
    private String lawFirm;
    /**
     * 执业年限
     */
    private Integer years;
    /**
     * 可查案例数
     */
    private Integer caseCount;
    /**
     * 案由数组
     */
    private String[] briefArray;
    /**
     * 是否关注标识
     */
    private String attentionFlag;
    /**
     * 胜诉率
     */
    private String winRate;
    /**
     * 头像Url
     */
    private String avatarImgUrl;
}