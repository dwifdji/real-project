package com._360pai.core.facade.disposal.resp;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @author xiaolei
 * @create 2018-10-25 19:07
 */
@Getter
@Setter
public class RecommendProviderResp implements Serializable {
    private static final long serialVersionUID = -8562653774232044574L;
    private Integer providerId;
    private String companyName;
    private String contactName;
    private String contactMobile;
    private String levelFlag;
}
