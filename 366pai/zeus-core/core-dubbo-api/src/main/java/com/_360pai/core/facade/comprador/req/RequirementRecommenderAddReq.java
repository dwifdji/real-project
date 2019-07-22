package com._360pai.core.facade.comprador.req;

import com._360pai.arch.common.RequestModel;
import lombok.Data;

/**
 * 描述
 *
 * @author : whisky_vip
 * @date : 2018/9/3 15:58
 */
@Data
public class RequirementRecommenderAddReq extends RequestModel {

    /**
     * requirementId
     */
    private Integer requirementId;

    private Integer accountId;
    private Integer partyId;

}
