package com.tzCloud.core.facade.legalEngine.resp;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author xiaolei
 * @create 2019-04-28 19:26
 */
@Data
public class LawFirmInfoVO implements Serializable {
    private static final long serialVersionUID = -1180800541486389467L;
    private String lawFirm;
    private String lawFirmShort;
    private String foundTime;
    private String lawFirmProvince;
    private Integer lawyerCount;
    private Integer caseCount;
    private String briefConcat;
    private String contactAddress;
    private String contactNumber;
    private String licenseNumber;
    private String firmId;
    private String introduction;
    private List<String> briefArray;
}
