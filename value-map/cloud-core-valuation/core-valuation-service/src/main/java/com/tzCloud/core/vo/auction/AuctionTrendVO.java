package com.tzCloud.core.vo.auction;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Setter
@Getter
public class AuctionTrendVO implements Serializable {

    private String month;
    private String averagePrice;
    private Integer num;



}
