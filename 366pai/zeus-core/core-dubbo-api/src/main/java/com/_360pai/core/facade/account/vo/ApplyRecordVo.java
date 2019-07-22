package com._360pai.core.facade.account.vo;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

/**
 * @author xdrodger
 * @Title: ApplyRecordVo
 * @ProjectName zeus
 * @Description:
 * @date 2018/10/31 14:40
 */
@Getter
@Setter
public class ApplyRecordVo implements Serializable {
    private Integer id;
    private String type;
    private String name;
    private Date createTime;
    private String status;
    private String statusDesc;
    private String remark;
}
