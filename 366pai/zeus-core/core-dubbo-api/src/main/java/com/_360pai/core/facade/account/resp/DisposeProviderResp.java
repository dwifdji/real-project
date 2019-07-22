package com._360pai.core.facade.account.resp;

import com._360pai.arch.common.BaseResp;
import com._360pai.core.facade.account.vo.DisposeProviderApplyVo;
import com._360pai.core.facade.account.vo.DisposeProviderVo;

/**
 * @author xdrodger
 * @Title: DisposeProviderApplyResp
 * @ProjectName zeus
 * @Description:
 * @date 18/09/2018 18:42
 */
public class DisposeProviderResp extends BaseResp {

    public static class DetailResp extends BaseResp {
        private DisposeProviderVo provider;

        public DisposeProviderVo getProvider() {
            return provider;
        }

        public void setProvider(DisposeProviderVo provider) {
            this.provider = provider;
        }
    }
}
