package com._360pai.core.facade.shop.vo;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class AppletQueryInfoVO implements Serializable {


    private String key;

    private String value;


    public AppletQueryInfoVO(String key, String value) {
        this.key = key;
        this.value = value;
    }

}
