package com._360pai.core.vo.lease;

import lombok.Data;

import java.io.Serializable;

/**
 * 描述：
 * <p>
 * 作者： wuchuanqi
 * 版本： 1.0.0
 * 时间： 2018/8/20 17:00
 */
@Data
public class LeaseAuditRecordVo implements Serializable {

    private String createTime;//

    private String userName;//

    private String status;

    private String statusDesc;

    private String remark;




}
