package com._360pai.core.facade.lease.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;


@Data
public class LeaseStaffVO implements Serializable {


    private Integer id;
    private String name;
    private String greatWallFlag;
    private String mobile;
    private String roleDesc;
    private String statusDesc;
    private String createTime;

}
