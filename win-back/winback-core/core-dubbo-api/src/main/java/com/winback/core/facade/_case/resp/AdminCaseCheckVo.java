package com.winback.core.facade._case.resp;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @author RuQ
 * @Title: AdminCaseCheckVo
 * @ProjectName winback
 * @Description:
 * @date 2019/2/19 9:57
 */
@Getter
@Setter
public class AdminCaseCheckVo implements Serializable {
    private Integer id;
    private String caseId;
    private String checkResult;
    private String checkRemark;
    private String createTime;
    private String operatorName;
}
