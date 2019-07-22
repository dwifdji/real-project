package com.tzCloud.core.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author xdrodger
 * @Title: EsCaseSearchCondition
 * @ProjectName tzCloud-parent
 * @Description:
 * @date 2019/5/8 19:17
 */
@Data
public class EsCaseSearchCondition implements Serializable {
    /**
     * 类型（详情见CaseSearchType）
     */
    private String filed;
    /**
     * 值
     */
    private String value;
    /**
     * match match_phrase term query_string
     */
    private String queryType = "term";
}
