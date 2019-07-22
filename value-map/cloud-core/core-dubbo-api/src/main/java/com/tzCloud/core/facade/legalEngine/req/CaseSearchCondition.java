package com.tzCloud.core.facade.legalEngine.req;

import lombok.Data;

import java.io.Serializable;

/**
 * @author xdrodger
 * @Title: CaseSearchCondition
 * @ProjectName tzCloud-parent
 * @Description:
 * @date 2019/4/24 15:46
 */
@Data
public class CaseSearchCondition implements Serializable {
    /**
     * 类型（详情见CaseSearchType）
     */
    private String type;
    /**
     * 值
     */
    private String value;
    /**
     * 层级（案由： 1：一级、、、5：五级）
     */
    private String level;
}
