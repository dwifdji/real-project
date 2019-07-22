package com.tzCloud.core.facade.legalEngine.vo;

import com.alibaba.fastjson.annotation.JSONField;
import com.tzCloud.arch.common.utils.DateUtil;
import lombok.Data;

import java.io.Serializable;

/**
 * @author xdrodger
 * @Title: GuidedCase
 * @ProjectName tzCloud-parent
 * @Description:
 * @date 2019/6/21 10:03
 */
@Data
public class GuidedCase implements Serializable {
    /**
     * 文书ID
     */
    private String docId;
    /**
     * 案件名称，对应cpwsw_item表ajmc
     */
    private String title;
    /**
     * 裁判要旨段原文，对应cpwsw_item表cpyzdyw
     */
    private String courtOpinion;
    /**
     * 裁判日期，对应cpwsw_item表cprq
     */
    @JSONField(format= DateUtil.NORM_DATE_PATTERN)
    private java.util.Date judgementDate;
    /**
     * 律师
     */
    private String lawyer;
    /**
     * 律所所在律所
     */
    private String lawFirm;
    /**
     * 律师头像
     */
    private String lawyerImgUrl;

}
