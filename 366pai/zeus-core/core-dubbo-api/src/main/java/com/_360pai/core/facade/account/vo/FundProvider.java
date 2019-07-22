package com._360pai.core.facade.account.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author xdrodger
 * @Title: FundProvider
 * @ProjectName dev2-zeus
 * @Description:
 * @date 2019-05-21 14:02
 */
@Data
public class FundProvider implements Serializable {
    private Integer id;
    private String name;
    private String phone;
}
