package com.tzCloud.core.vo.house;

import org.apache.commons.lang.StringUtils;

import java.io.Serializable;
import java.math.BigDecimal;

public class HouseCoordinateStatisticsVO implements Serializable {

    private String total;

    private String lng;

    private String lat;

    private String avgPrice;

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
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
}
