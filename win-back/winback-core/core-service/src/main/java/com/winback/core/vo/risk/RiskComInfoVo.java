package com.winback.core.vo.risk;


import lombok.Data;

import java.io.Serializable;

@Data
public class RiskComInfoVo implements Serializable {

    private String id;

    private String type;

    private String comName;

    private String comStatus;

    private String hasException;

    private String registCapital;

    private String registArea;

    private String comType;

    private String accuserNum;

    private String defendantNum;

    private String executeNum;

    private String loseCreditNum;


    private String shareholdersInfo;

    private String propertyClue;

}
