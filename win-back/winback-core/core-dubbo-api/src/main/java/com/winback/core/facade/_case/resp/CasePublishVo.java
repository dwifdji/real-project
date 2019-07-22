package com.winback.core.facade._case.resp;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @author RuQ
 * @Title: CasePublishVo
 * @ProjectName winback
 * @Description:
 * @date 2019/2/27 15:05
 */
@Setter
@Getter
public class CasePublishVo implements Serializable {
    private String createTime;
    private String userName;
    private String caseReason;
    private String caseTypeDesc;
}
