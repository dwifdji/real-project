package com._360pai.core.facade.account.resp;

import com._360pai.arch.common.BaseResp;
import com._360pai.core.facade.account.vo.RoleVo;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @author xdrodger
 * @Title: RoleResp
 * @ProjectName zeus
 * @Description:
 * @date 2018/10/7 22:20
 */
public class RoleResp implements Serializable {
    @Getter
    @Setter
    public static class DetailResp extends BaseResp {
        private RoleVo role;
    }
}
