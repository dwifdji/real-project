package com._360pai.core.facade.account.vo;

import com._360pai.core.facade.assistant.vo.BankVo;
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
public class BankAccountVo implements Serializable {
    /**
     * id
     */
    private Integer id;
    /**
     * 开户名称
     */
    private String name;
    /**
     * 银行账号
     */
    private String number;
    /**
     * 银行
     */
    private BankVo bank;
}
