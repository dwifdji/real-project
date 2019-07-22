package com.tzCloud.core.facade.caseMatching.resp;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 描述
 *
 * @author : whisky_vip
 * @date : 2019/2/26 10:45
 */
@Data
public class LawyerForRankingListResp implements Serializable {
//    private Integer    id;
    private String     lawyerName;
    private Integer    thisCaseNum;
    private BigDecimal thisCaseWinRates;
}
