package com._360pai.core.facade.fastway.resp;

import com._360pai.core.facade.disposal.req.City;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author xiaolei
 * @create 2018-12-07 11:23
 */
@Data
public class FundApplyListVO implements Serializable {
    private static final long serialVersionUID = 571836778786661810L;

    private String  name;
    private String  applyType;
    private City    workCity;
    private Date    createTime;
    private String  applyStatus;
    private String  applyStatusDesc;
    private String  operatorName;
    private Date    operatorTime;
    private Integer applyId;
    private String  refuseReason;
    private String  mobile;

    public static FundApplyListVO empty() {
        return new FundApplyListVO();
    }
}
