package com._360pai.core.facade.disposal.resp;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

/**
 * @author xiaolei
 * @create 2018-10-25 10:33
 */
@Getter
@Setter
@ToString
public class CityPartnerResp implements Serializable {
    private static final long serialVersionUID = 8863414992983257239L;
    /* 城市、律所名称、律所id、联系人姓名、联系人手机号、违约次数 */

    private String companyName;
    private String provinceName;
    private String cityName;
    private Integer providerId;
    private String contactName;
    private String contactMobile;
    private Integer surveyRefuseNum;
    private Integer levelId;
    private String levelFlag;
    private Integer cityId;
    private String contractDate;
    private String contractNo;
}
