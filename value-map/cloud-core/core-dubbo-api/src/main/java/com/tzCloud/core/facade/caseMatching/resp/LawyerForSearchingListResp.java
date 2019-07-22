package com.tzCloud.core.facade.caseMatching.resp;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 描述
 *
 * @author : whisky_vip
 * @date : 2019/2/26 10:29
 */
@Data
public class LawyerForSearchingListResp implements Serializable {
    //    private Integer    id;
    private String     lawyerName;
    private String     location;
    private String     lawyerFirm;
    private String     experienceYears;
    private Integer    totalCaseNum;
    private Integer    thisCaseNum;
    private BigDecimal thisCaseWinRates;
    private String     lawyerImageUrl;
    private String     brief;
}
