package com._360pai.core.facade.account.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author xdrodger
 * @Title: AcctVo
 * @ProjectName zeus
 * @Description:
 * @date 2018/12/6 15:13
 */
@Data
public class AcctVo implements Serializable {
    /**
     *
     */
    private Integer id;
    /**
     * user、compny、agency
     */
    private String type;
    /**
     * 类型描述
     */
    private String typeDesc;
    /**
     * 总金额
     */
    private java.math.BigDecimal totalAmt;
    /**
     * 锁定金额
     */
    private java.math.BigDecimal lockAmt;
    /**
     * 可用余额
     */
    private java.math.BigDecimal availAmt;
    /**
     *  创建时间
     */
    private java.util.Date createTime;

    /**
     * 手机号
     */
    private String mobile;
    /**
     * 名称
     */
    private String name;
}
