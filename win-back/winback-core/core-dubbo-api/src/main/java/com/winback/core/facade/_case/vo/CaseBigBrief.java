package com.winback.core.facade._case.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

/**
 * @author xdrodger
 * @Title: CaseBigBrief
 * @ProjectName winback
 * @Description:
 * @date 2019-04-22 18:49
 */
@Data
public class CaseBigBrief implements Serializable {
    private String id;
    private String name;
    /**
     * 是否展示（0 否 1 是）
     */
    private Boolean display;
    private List<CaseBrief> caseBriefs = Collections.EMPTY_LIST;
}