package com._360pai.core.facade.comprador.req;

import com._360pai.arch.common.RequestModel;
import lombok.Getter;
import lombok.Setter;

/**
 * 描述
 *
 * @author : whisky_vip
 * @date : 2018/9/3 14:53
 */
@Getter
@Setter
public class CompradorRequirementQueryReq extends RequestModel {
    /**
     * 关联的 t_comprador_requirement 的id
     */
    private Integer requirementId;

    private Integer operatorId;

    private Integer partyId;

}
