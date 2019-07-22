package com._360pai.core.facade.asset.resp;

import com._360pai.arch.common.BaseResp;
import com._360pai.core.facade.activity.vo.FileInfo;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @author xdrodger
 * @Title: AssetAuthorizationResp
 * @ProjectName zeus
 * @Description:
 * @date 2018/11/7 10:44
 */
public class AssetAuthorizationResp implements Serializable {

    @Getter
    @Setter
    public static class PreSignTuneAuthProtocolResp extends BaseResp {
        private String jdReportSaleViewUrl;
        private String jdReportCommissionViewUrl;
        private String thirdProtocolViewUrl;
    }
}
