package com.tzCloud.core.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author xiaolei
 * @create 2019-07-03 16:45
 */
@Data
public class LawyerVO implements Serializable {
    private static final long serialVersionUID = -359395434920043080L;

    /**
     * 主键id
     */
    private Long id;
    /**
     * 律师名称
     */
    private String lawyerName;
    /**
     * 律师事务所
     */
    private String lawFirm;
    /**
     * 事务所名称
     */
    private String lawFirmShort;
    /**
     * 事务所所在城市
     */
    private String lawFirmCity;
    /**
     * 事务所所在省份
     */
    private String lawFirmProvince;
    /**
     * 工作年限
     */
    private Integer years;
}
