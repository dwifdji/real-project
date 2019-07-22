package com.winback.core.facade.contract.vo;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author xdrodger
 * @Title: ContractOrderItem
 * @ProjectName winback
 * @Description:
 * @date 2019/2/11 15:05
 */
@Data
public class ContractOrderItem implements Serializable {
    /**
     * 条目id
     */
    private Integer id;
    /**
     * 合同名称
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
    /**
     * 删除标志（0 否 1 是）
     */
    private Boolean deleteFlag;
}
