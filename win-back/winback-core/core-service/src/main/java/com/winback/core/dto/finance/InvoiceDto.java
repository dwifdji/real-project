package com.winback.core.dto.finance;

import lombok.Data;

/**
 * 描述：
 * <p>
 * 作者： wuchuanqi
 * 版本： 1.0.0
 * 时间： 2019/1/22 16:56
 */
@Data
public class InvoiceDto {

    private String caseNo;
    private String customer;
    private String expendBeginAt;
    private String expendEndAt;
    private String mode;
    private String status;

}
