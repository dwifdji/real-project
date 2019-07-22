package com.tzCloud.core.facade.legalEngine.req;

import lombok.Data;

import java.io.Serializable;

/**
 * @author xiaolei
 * @create 2019-05-13 18:40
 */
@Data
public class LawFirmSearchCondition implements Serializable {
    private static final long serialVersionUID = 1839445429284536645L;
    private String province;
    private String foundTime;
    private String lawyerNum;
    private String searchWord;
    private String foundTime_begin;
    private String foundTime_end;
    private Integer lawyerNum_begin;
    private Integer lawyerNum_end;
}
