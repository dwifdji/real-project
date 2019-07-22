package com._360pai.core.facade.disposal.resp;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

/**
 * @author xiaolei
 * @create 2018-09-20 18:11
 */
@Getter
@Setter
@ToString
public class RequirementProgressForAdminResp implements Serializable {
    private static final long serialVersionUID = -3946938085198912044L;

    private Integer disposalId;
    private String  disposalName;
    private String  disposalNo;
    private String  disposalType;
    private Integer isPlatform;
    private Integer biddingNum;
    private Date    publishTime;
    private String  disposalStatus;
    private Double  period;
    private String  verifyContent;
    private Integer assetId;

    /**
     * 发布人电话
     */
    private String sourceMobile;
    /**
     * 发布人名称
     */
    private String sourceName;
}
