package com._360pai.core.facade.disposal.resp;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

/**
 * @author xiaolei
 * @create 2018-10-24 20:41
 */
@Setter
@Getter
@ToString
public class DisposeSurveyResp implements Serializable {

    private static final long serialVersionUID = -5202153626704146738L;
    private Integer surveyId;
    private String surveyNo;
    private String assetName;
    private String cityId;
    private Date assignTime;
    private String surveyStatus;
    private String surveyStatusDesc;
    private Integer assetId;
    private String basisSurvey;
    private String completeSurvey;
    private String region;
    private Integer activityId;
}
