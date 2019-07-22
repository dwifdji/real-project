package com.winback.core.facade._case.req;

import com.winback.arch.common.RequestModel;
import lombok.Getter;
import lombok.Setter;

/**
 * @author RuQ
 * @Title: CaseLawyerOrderReq
 * @ProjectName winback
 * @Description:
 * @date 2019/2/26 18:14
 */
@Getter
@Setter
public class CaseLawyerOrderReq extends RequestModel {
    /**
     * 主键
     */
    private String id;
    /**
     * 案件id
     */
    private String caseId;

    /**
     * 状态
     */
    private String orderStatus;
    /**
     * 删除标志,1:删除
     */
    private Integer deleteFlag = 0;
    /**
     * 开始时间
     */
    private String beginTime;

    /**
     * 结束时间
     */
    private String endTime;

    /**
     * 原告
     */
    private java.lang.String plaintiff;
    /**
     * 被告
     */
    private java.lang.String defendant;
}
