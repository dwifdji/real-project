package com._360pai.core.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * @author RuQ
 * @Title: SellerPayInfo
 * @ProjectName zeus-parent
 * @Description:
 * @date 2018/9/28 16:42
 */
@Getter
@Setter
public class SellerPayInfo {

    private String dfftId;
    private String name;
    private String fddId;
    private boolean isBank;
    private String bankName;
    private String bankAccountNo;  //卡号
    private String bankAccountName;//开户名
    private String mobile;

}
