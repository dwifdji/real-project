package com.winback.core.facade.contract.vo;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * @author xdrodger
 * @Title: ContractShoppingCart
 * @ProjectName winback
 * @Description:
 * @date 2019/1/31 14:45
 */
@Data
public class ContractShoppingCart implements Serializable {

    private Integer total;

    private BigDecimal totalAmt;

    private List<ContractShoppingCartItem> itemList;
}
