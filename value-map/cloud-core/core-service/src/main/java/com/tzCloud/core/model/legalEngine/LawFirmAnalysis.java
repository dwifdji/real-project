package com.tzCloud.core.model.legalEngine;

import lombok.Data;

import java.io.Serializable;

/**
 * @author xiaolei
 * @create 2019-05-05 14:48
 */
@Data
public class LawFirmAnalysis implements Serializable {
    private static final long serialVersionUID = -7200006234494169049L;
    private Integer winCount;
    private Integer loseCount;
    private Integer drawCount;
    private String basisCount;
    private String middleCount;
    private String highCount;
}
