package com._360pai.core.facade.comprador.resp;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 描述
 *
 * @author : whisky_vip
 * @date : 2018/9/4 12:47
 */
@Data
public class RecommendDetailForAdminResp implements Serializable {
    private Integer id;
    private Date    createdTime;
    private String  recommendNo;
    private String  contactPhone;
    private String  contactName;
    private String  contactAddress;
    private String  recommendDescription;
    private String  recommendStatus;
    private String  remark;

    private String sourceMobile;
    private String sourceName;
}
