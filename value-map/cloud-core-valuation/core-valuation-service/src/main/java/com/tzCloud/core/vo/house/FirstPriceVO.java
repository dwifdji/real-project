package com.tzCloud.core.vo.house;

import java.io.Serializable;

public class FirstPriceVO implements Serializable {

    private String firstPrice;

    private String id;

    public String getFirstPrice() {
        return firstPrice;
    }

    public void setFirstPrice(String firstPrice) {
        this.firstPrice = firstPrice;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
