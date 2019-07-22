package com.tzCloud.core.facade.legalEngine.req;

import lombok.Data;

import java.io.Serializable;

/**
 * @author xiaolei
 * @create 2019-04-23 13:06
 */
@Data
public class LawyerSearchCondition implements Serializable {
    private static final long serialVersionUID = 4980893676391787233L;
    private String searchWord;
    private String brief;
    private String lawFirmProvince;
    private Integer years;// 1:3年以下 2：3~5年 3：5~10年 4：10年以上
    private Integer years_begin;
    private Integer years_end;
}
