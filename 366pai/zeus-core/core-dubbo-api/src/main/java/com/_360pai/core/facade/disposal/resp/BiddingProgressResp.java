package com._360pai.core.facade.disposal.resp;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

/**
 * @author xiaolei
 * @create 2018-09-20 18:00
 */
@Getter
@Setter
@ToString
public class BiddingProgressResp implements Serializable {
    private static final long serialVersionUID = 7610371852489999549L;

    private Integer biddingId;
    private String  disposalNo;
    private String  disposalName;
    private String  disposalType;
    private Integer isPlatform;
    private String  bidNo;
    private Date    createTime;
    private String  bidStatus;
    private String  bidCost;
    private String  requireDescription;
    private String  specialDescription;
}
