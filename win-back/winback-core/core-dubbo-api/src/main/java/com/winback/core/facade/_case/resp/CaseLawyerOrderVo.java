package com.winback.core.facade._case.resp;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @author RuQ
 * @Title: CaseLawyerOrderVo
 * @ProjectName winback
 * @Description:
 * @date 2019/2/27 9:26
 */
@Getter
@Setter
public class CaseLawyerOrderVo implements Serializable {

    private String id;
    private String caseId;
    private String createTime;
    /**
     * 原告
     */
    private java.lang.String plaintiff;
    /**
     * 被告
     */
    private java.lang.String defendant;

    /**
     * 状态
     */
    private String orderStatus;

    /**
     * 涉案金额
     */
    private String caseAmount;

    /**
     * 合作模式、案件类型
     */
    private String cooperateWay;

    private String lawyerName;

}
