package com._360pai.core.facade.fastway.resp;

import com._360pai.core.facade.disposal.req.City;
import lombok.Data;

import java.io.Serializable;

/**
 * @author xiaolei
 * @create 2019-01-22 13:13
 */
@Data
public class DisposeCompanyVO implements Serializable {
    private String lawOffice;
    private Integer accountId;
    private String name;
    private City workCity;
    private String workAddress;
    private Integer partyId;
}
