package com.winback.core.facade._case.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * App热门案件
 */
@Data
public class HomePageCaseVO implements Serializable {

    private String id;

    private String defendant;

    private String caseBrief;

    private String casePrice;

    private String province;

    private String city;

    private String caseCourt;

    private String caseId;

    private String mainStatus;

    private String statusDesc;

}
