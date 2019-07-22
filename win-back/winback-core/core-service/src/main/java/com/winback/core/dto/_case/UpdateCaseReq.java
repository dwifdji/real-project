package com.winback.core.dto._case;

import lombok.Getter;
import lombok.Setter;

/**
 * @author RuQ
 * @Title: UpdateCaseReq
 * @ProjectName winback
 * @Description:
 * @date 2019/2/18 18:31
 */
@Getter
@Setter
public class UpdateCaseReq {
    private Integer operatorId;
    private String caseId;
    private String operate;

    //有且只有诉讼、执行必传
    private String subStatusId;
    private String subStatusDesc;
    private Integer id;
}
