package com.tzCloud.core.facade.legalEngine.resp;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author xiaolei
 * @create 2019-05-16 14:26
 */
@Data
public class LawFirmVO implements Serializable {
    private static final long serialVersionUID = 4800750244835203495L;

    // 公司名称
    private String lawFirm;
    // 省份
    private String lawFirmProvince;
    // 联系地址
    private String contactAddress;
    // 营业执照
    private String licenseNumber;
    // 联系电话
    private String contactNumber;
    // 创建时间
    private String foundTime;
    private Integer lawyerNum;
    private Integer caseCount;
}
