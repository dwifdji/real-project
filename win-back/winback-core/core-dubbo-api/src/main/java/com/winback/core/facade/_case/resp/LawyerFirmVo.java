package com.winback.core.facade._case.resp;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @author RuQ
 * @Title: LawyerFirmVo
 * @ProjectName winback
 * @Description:
 * @date 2019/1/25 15:39
 */
@Getter
@Setter
public class LawyerFirmVo implements Serializable {

    private String firmId;
    private String firmName;
    private String firmType;
    private String firmProvinceName;
    private String firmCityName;
    private String firmAreaName;

}
