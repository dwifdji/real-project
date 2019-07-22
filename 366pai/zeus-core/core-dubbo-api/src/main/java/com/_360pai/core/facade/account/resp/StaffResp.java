package com._360pai.core.facade.account.resp;

import com._360pai.arch.common.BaseResp;
import com._360pai.core.facade.account.vo.ModuleVo;
import com._360pai.core.facade.account.vo.StaffVo;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Set;

/**
 * @author xdrodger
 * @Title: StaffResp
 * @ProjectName zeus
 * @Description:
 * @date 17/09/2018 09:30
 */
public class StaffResp extends BaseResp {

    @Getter
    @Setter
    public static class BasicResp extends BaseResp {
        /**
         *
         */
        private java.lang.Integer id;
        /**
         * 名称
         */
        private java.lang.String name;
        /**
         * 是否管理员
         */
        private java.lang.Boolean isAdmin;
        /**
         * 手机号
         */
        private java.lang.String mobile;
        /**
         *
         */
        private java.lang.String passwordHash;
        /**
         * QQ号
         */
        private java.lang.String qq;
        /**
         * 部门
         */
        private java.lang.String dept;
        /**
         * 职位
         */
        private java.lang.String job;
        /**
         * 工号
         */
        private java.lang.String empNo;
        /**
         * 企业邮箱
         */
        private java.lang.String companyEmail;
        /**
         * 状态（0：禁用，1：启用）
         */
        private java.lang.String status;
        /**
         * 客户画像管理员
         */
        private java.lang.Boolean personaAdmin;
        /**
         *
         */
        private java.util.Date createTime;
        /**
         *
         */
        private java.util.Date updateTime;
    }

    @Getter
    @Setter
    public static class DetailResp extends BaseResp {
        private StaffVo staff;
    }

    @Getter
    @Setter
    public static class PermissionResp extends BaseResp {
        private Integer id;
        private List<ModuleVo> modules;
        private Set<String> permissions;
    }
}
