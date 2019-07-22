package com.winback.core.facade.contract.resp;

import com.winback.arch.common.RespModel;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author xdrodger
 * @Title: AppContractResp
 * @ProjectName winback
 * @Description:
 * @date 2019/2/13 20:09
 */
public class AppContractResp {
    @Data
    public static class PreInvoiceResp extends RespModel {
        private BigDecimal amount;
    }
}
