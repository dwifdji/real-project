package com.winback.core.facade._case.resp;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @author RuQ
 * @Title: CaseRoleVo
 * @ProjectName winback
 * @Description:
 * @date 2019/5/22 11:16
 */
@Getter
@Setter
public class CaseRoleVo implements Serializable {
    private String caseRole;
    private String roleName;
    private String headImgUrl;
}
