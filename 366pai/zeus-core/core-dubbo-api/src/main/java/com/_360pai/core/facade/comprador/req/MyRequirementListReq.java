package com._360pai.core.facade.comprador.req;

import com._360pai.arch.common.RequestModel;
import lombok.Data;

/**
 * 描述
 *
 * @author : whisky_vip
 * @date : 2018/9/4 16:17
 */
@Data
public class MyRequirementListReq extends RequestModel {
    private Integer cityId;
    private String  buildingType;
    private String  requirementStatus;
    private String  requirementName;

    /**
     * 前端不需要传
     */
    private Integer accountId;
    private Integer partyId;

}
