package com.tzCloud.core.facade.caseMatching.resp;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * 描述
 *
 * @author : whisky_vip
 * @date : 2019/2/26 11:30
 */
@Data
public class LawyerDetailResp implements Serializable {
    //    private Integer    id;
    private String                    lawyerName;
    private String                    location;
    private String                    lawyerFirm;
    private String                    experienceYears;
    private String                    lawyerImgUrl;
    private Integer                   totalCaseNum;
    private List<Map<String, Object>> courtList;
    private List<Map>                 caseTypeList;
    private List<Map<String, Object>> locationList;
    private List<Map<String, Object>> caseCountByMonthList;
    private String                    attentionFlag;//“1”：已关注 “0”： 未关注
    private Integer                   lawyerId;
}
