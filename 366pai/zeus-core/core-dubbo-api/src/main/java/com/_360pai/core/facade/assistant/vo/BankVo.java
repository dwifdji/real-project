package com._360pai.core.facade.assistant.vo;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author xdrodger
 * @Title: BankVo
 * @ProjectName zeus
 * @Description:
 * @date 25/09/2018 09:20
 */
@Getter
@Setter
public class BankVo implements Serializable {
    /**
     * 银行id
     */
    private Integer id;
    /**
     * 银行编号
     */
    private String code;

    /**
     * 银行名称
     */
    private String name;
    /**
     * 银行图片
     */
    private String imgUrl;
    /**
     * 银行id
     */
    private String value;
    /**
     * 限额
     */
    private java.math.BigDecimal quota;
}
