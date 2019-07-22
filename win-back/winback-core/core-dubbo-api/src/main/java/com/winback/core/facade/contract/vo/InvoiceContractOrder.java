package com.winback.core.facade.contract.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author xdrodger
 * @Title: InvoiceContractOrder
 * @ProjectName winback
 * @Description:
 * @date 2019/2/22 13:18
 */
@Data
public class InvoiceContractOrder implements Serializable {
    private String month;
    private List<InvoiceContractOrderDetail> orderList;
}
