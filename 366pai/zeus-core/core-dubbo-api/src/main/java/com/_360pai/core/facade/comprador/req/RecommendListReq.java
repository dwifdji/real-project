package com._360pai.core.facade.comprador.req;

import com._360pai.arch.common.RequestModel;
import lombok.Getter;
import lombok.Setter;

/**
 * 描述
 *
 * @author : whisky_vip
 * @date : 2018/9/4 12:42
 */
@Setter
@Getter
public class RecommendListReq extends RequestModel {

    /**
     * 是否有备注 0 否 1 是
     */
    private String remarked;

    private String createdTime;
    /**
     * 联系人姓名或手机号
     */
    private String contact;

    private String recommendStatus;
}
