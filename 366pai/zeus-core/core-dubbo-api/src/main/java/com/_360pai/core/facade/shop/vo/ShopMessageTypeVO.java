package com._360pai.core.facade.shop.vo;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Setter
@Getter
public class ShopMessageTypeVO implements Serializable {

    private String messageType;

    private String messageTotal;

    private String publicAt;
}
