package com._360pai.core.facade.account.req;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @author RuQ
 * @Title: SpvReq
 * @ProjectName zeus-parent
 * @Description:
 * @date 2018/9/21 20:23
 */
@Setter
@Getter
public class SpvReq implements Serializable {

    /**
     *
     */
    private Integer id;
    /**
     * 企业id
     */
    private Integer companyId;
    /**
     *
     */
    private String name;
    /**
     *
     */
    private String license;
    /**
     *
     */
    private String mobile;
    /**
     *
     */
    private Integer payBind;
    /**
     *
     */
    private String dfftId;
    /**
     *
     */
    private String fddId;

    private String smsCode;

}
