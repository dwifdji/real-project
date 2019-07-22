package com._360pai.core.facade.disposal.resp;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @author xiaolei
 * @create 2018-09-18 18:42
 */
@Getter
@Setter
public class CompanyInfo implements Serializable {

    private static final long serialVersionUID = 3845086838953449373L;

    private String companyName;
    private String companyType;
    private String registerAddress;
    private String registerCapital;
    private String contactName;
    private String contactMobile;
    private String qualificationUrl;
    private String region;

}
