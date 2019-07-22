package com.winback.core.facade.account.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author xdrodger
 * @Title: Contacts
 * @ProjectName winback
 * @Description:
 * @date 2019/4/3 15:43
 */
@Data
public class Contacts implements Serializable {
    /**
     * 姓名
     */
    private String name;
    /**
     * 电话
     */
    private String telephone;
}
