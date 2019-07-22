package com.tzCloud.core.facade.caseMatching.resp;

import lombok.Data;

import java.io.Serializable;

/**
 * 描述
 *
 * @author : whisky_vip
 * @date : 2019/2/26 10:27
 */
@Data
public class LawyerFirmResp implements Serializable {
//    private Integer id;
    private String  lawyerFirmName;
    private Integer caseNum;
}
