package com.winback.core.facade._case.resp;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @author RuQ
 * @Title: AdminCaseSignContractVo
 * @ProjectName winback
 * @Description:
 * @date 2019/2/19 10:02
 */
@Setter
@Getter
public class AdminCaseSignContractVo implements Serializable {
    private Integer lawyerId;
    private String lawyerName;
    private String lawFirmName;
    private String areaName;
    private String createTime;
    private String operatorName;
    private String lawyerMobile;

}
