package com.winback.core.dto._case;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CaseStatusStepDto {

    private String caseId;

    private String litigant;

    private String beginTime;

    private String endTime;

    private String caseTypeId;

    private String stepId;

    private String type; //0诉讼1执行
}
