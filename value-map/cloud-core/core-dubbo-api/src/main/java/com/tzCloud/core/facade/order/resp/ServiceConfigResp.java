package com.tzCloud.core.facade.order.resp;

import lombok.Data;

import java.io.Serializable;

/**
 * @author xiaolei
 * @create 2019-06-19 18:10
 */
@Data
public class ServiceConfigResp implements Serializable {

    private static final long serialVersionUID = 1272036544013730780L;
    private String configType;
    private String configValue;
}
