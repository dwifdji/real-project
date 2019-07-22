package com.winback.core.facade._case.resp;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @author RuQ
 * @Title: CaseAssetVo
 * @ProjectName winback
 * @Description:
 * @date 2019/2/28 20:47
 */
@Setter
@Getter
public class CaseAssetVo implements Serializable {
    private Integer id;
    private Integer accountId;
    /**
     *
     */
    private String assetAmount;
    /**
     *
     */
    private String assetDesc;
    /**
     *
     */
    private String createTime;

    private String mobile;

}
