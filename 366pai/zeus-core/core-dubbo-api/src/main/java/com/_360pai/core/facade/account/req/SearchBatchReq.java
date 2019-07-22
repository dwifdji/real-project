package com._360pai.core.facade.account.req;

import com._360pai.arch.common.RequestModel;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @author RuQ
 * @Title: SearchBatchReq
 * @ProjectName zeus-parent
 * @Description:
 * @date 2018/12/7 10:28
 */
@Setter
@Getter
public class SearchBatchReq extends RequestModel implements Serializable {
    private String beginTime;
    private String endTime;
    private String batchOrderNo;
    private String status;
    private Integer partyId;
    private String type;
}
