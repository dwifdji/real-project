package com._360pai.core.facade.account.resp;

import com._360pai.arch.common.BaseResp;
import com._360pai.core.facade.account.vo.AccountAuthVo;
import com._360pai.core.facade.account.vo.AccountVo;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
public class AccountResp implements Serializable {
    /**
     * 主键
     */
    private java.lang.Integer id;
    /**
     * 手机号
     */
    private java.lang.String mobile;
    /**
     * 密码
     */
    private java.lang.String password;
    /**
     * 注册来源
     */
    private java.lang.String registerSource;
    /**
     * 渠道来源
     */
    private java.lang.String source;
    /**
     * 状态(0:无效,1:有效)
     */
    private java.lang.Integer status;
    /**
     * 默认送拍机构
     */
    private java.lang.Integer defaultAgencyId;
    /**
     * 从属于某个机构的管理员
     */
    private java.lang.Integer agencyId;
    /**
     * 是否是机构的申请者
     */
    private java.lang.Integer isAgencyAdmin;
    /**
     * 是否可以查看保留价
     */
    private java.lang.Boolean canCheckReservePrice;
    /**
     *
     */
    private java.lang.Integer currentPartyId;
    /**
     * 店铺id
     */
    private java.lang.Integer shopId;
    /**
     * 创建时间
     */
    private java.util.Date createTime;
    /**
     * 更新时间
     */
    private java.util.Date updateTime;

    @Getter
    @Setter
    public static class DetailResp extends BaseResp {
        private AccountVo account;
    }

    @Getter
    @Setter
    public static class OpenElectronicSignatureResp extends BaseResp {
        private String customId;
    }

    @Getter
    @Setter
    public static class LoginResp extends BaseResp {
        private Integer loginId;
        private String openId;
    }

    @Getter
    @Setter
    public static class BindAccountResp extends BaseResp {
        private Boolean isAccountAuth;
        private List<AccountAuthVo> accountAuthList;
    }

    @Getter
    @Setter
    public static class AuthInfoResp extends BaseResp {
        /**
         * 认证申请状态（0 未申请 1 申请中 2 申请成功 3 申请失败）
         */
        private String applyStatus;
        /**
         * 认证申请状态描述
         */
        private String applyStatusDesc;
        private List<AccountAuthVo> accountAuthList;
    }
}
