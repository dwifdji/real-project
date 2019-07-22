package com.winback.core.facade._case.resp;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @author RuQ
 * @Title: LawyerVo
 * @ProjectName winback
 * @Description:
 * @date 2019/1/25 15:59
 */
@Setter
@Getter
public class LawyerVo implements Serializable {
    private Integer lawyerId;
    private Integer lawyerAccountId;
    private String lawyerMobile;
    private String lawyerName;
    private String lawyerFirmName;
    private String areaName;
    private String createTime;
    private String workYear;
    private String lawyerLicenseNum;
    private String orderStatus;
    private boolean acceptedFlag;

}
