package com.winback.core.facade._case.req;

import com.winback.arch.common.RequestModel;
import lombok.Getter;
import lombok.Setter;

/**
 * @author RuQ
 * @Title: CaseAssetReq
 * @ProjectName winback
 * @Description:
 * @date 2019/2/28 17:56
 */
@Getter
@Setter
public class CaseAssetReq extends RequestModel {
    private String assetAmount;
    /**
     *
     */
    private String assetDesc;

    private Integer accountId;

    /**
     *
     */
    private String beginTime;
    private String endTime;

    private String mobile;
}
