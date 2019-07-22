package com._360pai.core.facade.account.vo;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @author RuQ
 * @Title: SpvVo
 * @ProjectName zeus-parent
 * @Description:
 * @date 2018/10/9 9:32
 */
@Getter
@Setter
public class SpvVo implements Serializable {
    /**
     *
     */
    private Integer id;
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
    private boolean payBind;

    private boolean fddBind;
    /**
     *
     */
    private String dfftDescrip;
    /**
     *
     */
    private String fddDescrip;
    /**
     *
     */
    private String createTime;
    /**
     *
     */
    private String updateTime;

    private String statusDescrip;

    private String[] operate;
}
