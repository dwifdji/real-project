package com._360pai.core.facade.comprador.resp;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 描述
 *
 * @author : whisky_vip
 * @date : 2018/9/5 13:54
 */
@Data
public class MyRequirementDetailResp implements Serializable {
    private String     requirementName;
    private BigDecimal endArea;
    private BigDecimal startArea;
    private String     transactionMode;
    private String     requirementNo;
    private String     remark;
    private Integer    cityId;
    private String     proposedAcquisition;
    private Date       createdTime;
    private BigDecimal commissionPercent;
    private Integer    id;
    private String     requirementStatus;
    private String     requirementStatusDesc;
    private String     buildingType;
    private Integer    recommenderNum;
    private Integer    viewNum;
    private Integer    followNum;

    private String      payDeadlineTimestamp;
}
