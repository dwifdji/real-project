package com._360pai.core.vo.lease;

import lombok.Data;

import java.io.Serializable;

/**
 * 描述：
 * <p>
 * 作者： wuchuanqi
 * 版本： 1.0.0
 * 时间： 2018/8/20 17:00
 */
@Data
public class LeadFlagVo implements Serializable {

    private Boolean focusFlag;//

    private Boolean competeFlag;//

    private Boolean remindFlag;

    private Boolean dealFlag;

    private Boolean applyFlag;


    private String agentAuditNum;//经办人待审核数量

    private String leadAuditNum;//领导待审核数量

    private String agentReleaseNum;//经办人待发布的数量

    private String agentSignNum;//送拍待签协议数量

    private String userSignNum;//参拍代签协议数量



}
