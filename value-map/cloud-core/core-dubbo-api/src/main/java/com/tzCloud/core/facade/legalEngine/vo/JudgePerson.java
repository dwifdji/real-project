package com.tzCloud.core.facade.legalEngine.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author xdrodger
 * @Title: JudgePerson
 * @ProjectName tzCloud-parent
 * @Description:
 * @date 2019/5/16 15:16
 */
@Data
public class JudgePerson implements Serializable {
    /**
     * 审判长
     */
    private String presidingJudge;
}
