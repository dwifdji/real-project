package com._360pai.core.facade.account.resp;

import com._360pai.arch.common.BaseResp;
import com._360pai.core.facade.account.vo.FundProviderVo;

/**
 * @author xdrodger
 * @Title: FundProviderResp
 * @ProjectName zeus
 * @Description:
 * @date 18/09/2018 18:52
 */
public class FundProviderResp extends BaseResp {

    public static class DetailResp extends BaseResp {
        private FundProviderVo provider;

        public FundProviderVo getProvider() {
            return provider;
        }

        public void setProvider(FundProviderVo provider) {
            this.provider = provider;
        }
    }
}
