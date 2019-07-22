package com._360pai.core.facade.comprador.req;

import com._360pai.arch.common.RequestModel;
import lombok.Data;

/**
 * 描述 资产大买办 我要推介
 *
 * @author : whisky_vip
 * @date : 2018/9/3 12:45
 */
@Data
public class RecommendAddReq extends RequestModel {

    /**
     * 联系电话
     */
    private String contactPhone;
    /**
     * 联系地址
     */
    private String contactAddress;
    /**
     * 联系人
     */
    private String contactName;
    /**
     * 资产说明
     */
    private String recommendDescription;


    private Integer accountId;
    private Integer partyId;


}
