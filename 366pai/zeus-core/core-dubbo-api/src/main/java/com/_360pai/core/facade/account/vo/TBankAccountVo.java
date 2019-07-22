package com._360pai.core.facade.account.vo;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @author xdrodger
 * @Title: BankAccountVo
 * @ProjectName zeus
 * @Description:
 * @date 25/09/2018 09:53
 */
@Getter
@Setter
public class TBankAccountVo implements Serializable {
    /**
     * id
     */
    private Integer id;
    /**
     * 开户名称
     */
    private String bankAccountName;
    /**
     * 银行账号
     */
    private String bankAccountNo;
    /**
     * 银行代码
     */
    private String bankCode;
    /**
     * 银行名称
     */
    private String bankName;
    /**
     * 银行图片
     */
    private String imgUrl;
    /**
     * 支行名称
     */
    private String subBankName;
    /**
     * 是否启用（0 禁用 1 启用）
     */
    private String status;
    /**
     * 创建时间
     */
    private java.util.Date createTime;
    /**
     * 是否可以解绑
     */
    private Boolean isCanUnbind;

}
