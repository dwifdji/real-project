package com._360pai.core.facade.account.resp;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

/**
 * @author RuQ
 * @Title: BatchOrderInfoResp
 * @ProjectName zeus-parent
 * @Description:
 * @date 2018/12/3 17:15
 */
@Getter
@Setter
public class BatchOrderInfoResp implements Serializable {
    private List<WithdrawDetailVo>  detailList;
    private String batchOrderNo;
    private String batchOrderCreateTime;
    private String totalAmount;
    private String totalNum;
    private String status;
}
