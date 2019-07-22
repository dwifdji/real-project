package com._360pai.core.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @author RuQ
 * @Title: CommissionDto
 * @ProjectName zeus-parent
 * @Description:
 * @date 2019/5/5 10:52
 */
@Getter
@Setter
public class CommissionDto implements Serializable {
    private String userName;
    private String roleType;
    private String commissionAmount;
}
