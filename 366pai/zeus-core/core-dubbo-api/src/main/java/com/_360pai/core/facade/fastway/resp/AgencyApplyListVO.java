package com._360pai.core.facade.fastway.resp;

import com._360pai.core.facade.disposal.req.City;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author xiaolei
 * @create 2018-11-29 16:20
 */
@Data
public class AgencyApplyListVO implements Serializable {
    private static final long serialVersionUID = 7715147239098945868L;

    private String  name;
    private String  mobile;
    private City    workCity;
    private Date    createTime;
    private String  applyStatus;
    private String  applyStatusDesc;
    private String  operatorName;
    private Date    operatorTime;
    private Integer applyId;
    private String  refuseReason;
}
