package com._360pai.core.vo.lease;

import lombok.Data;

import java.io.Serializable;

/**
 * 描述：参拍审核信息
 * <p>
 * 作者： wuchuanqi
 * 版本： 1.0.0
 */
@Data
public class LeaseCompeteApplyVo implements Serializable {

    private String userName;//

    private String mobile;//

    private String userType;

    private String status;

    private String statusDesc;

    private String proveUrl;

    private String activityId;

    private String id;



}
