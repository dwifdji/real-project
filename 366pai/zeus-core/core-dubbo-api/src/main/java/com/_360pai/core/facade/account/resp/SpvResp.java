package com._360pai.core.facade.account.resp;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @author RuQ
 * @Title: SpvResp
 * @ProjectName zeus-parent
 * @Description:
 * @date 2018/9/21 20:25
 */
@Setter
@Getter
public class SpvResp implements Serializable {

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
    /**
     *
     */
    private java.util.Date createTime;
    /**
     *
     */
    private java.util.Date updateTime;



}
