package com.winback.core.facade._case.resp;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @author RuQ
 * @Title: CaseRecordVo
 * @ProjectName winback
 * @Description:
 * @date 2019/1/24 9:22
 */
@Getter
@Setter
public class CaseRecordVo implements Serializable {
    private String createTime;//创建时间
    private String caseStatus;//案件状态
    private String caseStatusDesc;//案件状态描述
    private String operatorName;//操作人
}
