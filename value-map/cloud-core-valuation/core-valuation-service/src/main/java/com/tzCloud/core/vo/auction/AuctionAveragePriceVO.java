package com.tzCloud.core.vo.auction;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Setter
@Getter
public class AuctionAveragePriceVO implements Serializable {

    private String averagePrice;

    private String sixMonAveragePrice;

    private String latelyAveragePrice;

    private String latelyId;

    private String lat;//纬度值

    private String lng;//经度值






}
