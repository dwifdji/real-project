package com._360pai.crawler.model.caa123;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author xiaolei
 * @create 2018-11-14 11:03
 */
@Data
public class Caa123Pm {
    private Integer id;
    private String startTime;//开始时间
    private String endTime;//结束时间
    private String lotStatus;//拍品状态(类型暂不确定)
    private BigDecimal startPrice;//变卖价
    private BigDecimal assessPrice;//评估价
    private String name;//拍品名称
    private String standardType;//标准类型
    private String secondaryType;//次要类型
    private String nowTime;//当前时间
    private BigDecimal nowPrice;//当前价
    private String pic;
    private String canLoan;
    private String isRestricted;
    private Integer times;
    private Integer onLooker;//围观数
    private Integer enrollment;//报名数
}
