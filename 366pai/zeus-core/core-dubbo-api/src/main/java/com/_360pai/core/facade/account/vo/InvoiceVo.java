package com._360pai.core.facade.account.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author xdrodger
 * @Title: InviceVo
 * @ProjectName zeus
 * @Description:
 * @date 2018/12/6 11:04
 */
@Data
public class InvoiceVo implements Serializable {
    /**
     *
     */
    private Integer id;
    /**
     * 纸质/电子
     */
    private String type;
    private String typeDesc;
    /**
     * 物流公司
     */
    private String logisticsCompany;
    /**
     * 电子版url
     */
    private String imgurl;
    /**
     * 物流单号
     */
    private String logisticsNo;
    /**
     * 发票代码
     */
    private String code;
    /**
     * 发票号码
     */
    private String num;
    /**
     *
     */
    private java.util.Date createTime;
    /**
     *
     */
    private java.util.Date updateTime;
}
