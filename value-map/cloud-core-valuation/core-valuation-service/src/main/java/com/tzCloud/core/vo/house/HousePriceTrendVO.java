package com.tzCloud.core.vo.house;

import org.apache.commons.lang.StringUtils;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;


public class HousePriceTrendVO implements Serializable {

    private String month;

    private String avgPrice;

    private String num;

    private String risePercent;

    public HousePriceTrendVO() {
    }

    public HousePriceTrendVO(String month, String avgPrice, String num, String risePercent) {
        this.month = month;
        this.avgPrice = avgPrice;
        this.num = num;
        this.risePercent = risePercent;
    }

    public String getRisePercent() {
        return risePercent;
    }

    public void setRisePercent(String risePercent) {
        this.risePercent = risePercent;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month.replace(".", "-");
    }

    public String getAvgPrice() {
        return avgPrice;
    }

    public void setAvgPrice(String avgPrice) {

        if(StringUtils.isNotBlank(avgPrice)) {
            avgPrice = new BigDecimal(avgPrice).setScale(0,BigDecimal.ROUND_HALF_UP).toString();
        }
        this.avgPrice = avgPrice;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

}
