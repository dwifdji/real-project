package com.winback.core.facade._case.vo;

import com.winback.core.commons.constants.CaseEnum;
import lombok.Data;

import java.io.Serializable;

/**
 * @author xdrodger
 * @Title: CaseStatus
 * @ProjectName winback
 * @Description:
 * @date 2019-05-06 10:46
 */


@Data
public class CaseStatus implements Serializable {

    public CaseStatus(String status, String name) {
        this.status = status;
        this.name= name;
    }

    public CaseStatus(CaseEnum.MainStatus s) {
        this.status = s.getKey();
        this.name= s.getValue();
    }

    private String status;
    private String name;
}
