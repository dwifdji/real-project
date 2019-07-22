package com.winback.core.facade.contract.vo;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author xdrodger
 * @Title: ContractShoppingCartItem
 * @ProjectName winback
 * @Description:
 * @date 2019/2/11 10:42
 */
@Data
public class ContractShoppingCartItem implements Serializable {
    /**
     *
     */
    private Integer id;
    /**
     * 名称
     */
    private String name;
    /**
     * 金额
     */
    private BigDecimal amount;
    /**
     * 合同id
     */
    private Integer contractId;
}
