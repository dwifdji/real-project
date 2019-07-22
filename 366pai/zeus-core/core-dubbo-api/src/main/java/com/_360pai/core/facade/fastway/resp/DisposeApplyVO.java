package com._360pai.core.facade.fastway.resp;

import com._360pai.core.facade.disposal.req.City;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author xiaolei
 * @create 2018-11-26 14:13
 */
@Data
public class DisposeApplyVO implements Serializable {
    private static final long serialVersionUID = 1300355131849511651L;

    private Integer applyId;
    private String applyName;
    private String applyType;
    private String applyTypeDesc;
    private String mobile;
    private City city;
    private Date createTime;
    private String applyStatus;
    private String applyStatusDesc;
    private String operatorName;
    private Date operatorTime;
    private String authorInfo;
}
