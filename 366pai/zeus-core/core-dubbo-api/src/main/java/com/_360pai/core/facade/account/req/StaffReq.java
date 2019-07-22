package com._360pai.core.facade.account.req;

import com._360pai.arch.common.RequestModel;
import com._360pai.core.facade.account.vo.PermissionVo;
import com._360pai.core.facade.account.vo.RoleVo;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

/**
 * @author xdrodger
 * @Title: StaffReq
 * @ProjectName zeus
 * @Description:
 * @date 17/09/2018 09:28
 */
public class StaffReq extends RequestModel {
    @Getter
    @Setter
    public static class BaseReq extends RequestModel {
        private Integer staffId;
        private String mobile;
        private String password;
        private Integer operatorId;
    }

    @Getter
    @Setter
    public static class QueryReq extends RequestModel {
        private String q;
        private String status;
        private boolean basicInfo;
    }

    @Getter
    @Setter
    public static class CreateReq extends RequestModel {
        @NotBlank
        private String name;
        @NotBlank
        private String qq;
        @NotBlank
        private String mobile;
        @NotBlank
        private String password;
        private Integer operatorId;
        /**
         * 状态（0：禁用，1：启用）
         */
        @NotBlank
        private String status;
        /**
         * 角色列表
         */
        private List<RoleVo> roles;
        /**
         * 特殊权限列表
         */
        private List<PermissionVo> specialPermissions;
        /**
         * 部门
         */
        private String dept;
        /**
         * 职位
         */
        private String job;
        /**
         * 工号
         */
        private String empNo;
        /**
         * 公司邮箱
         */
        private String companyEmail;
    }

    @Getter
    @Setter
    public static class UpdateReq extends RequestModel {
        @NotNull
        private Integer staffId;
        private String name;
        private String qq;
        private Integer operatorId;
        /**
         * 状态（0：禁用，1：启用）
         */
        private String status;
        /**
         * 角色列表
         */
        private List<RoleVo> roles;
        /**
         * 特殊权限列表
         */
        private List<PermissionVo> specialPermissions;
        /**
         * 部门
         */
        private String dept;
        /**
         * 职位
         */
        private String job;
        /**
         * 工号
         */
        private String empNo;
        /**
         * 公司邮箱
         */
        private String companyEmail;
    }

    @Getter
    @Setter
    public static class ModifyPasswordReq extends RequestModel {
        private Integer staffId;
        @NotBlank
        private String oldPassword;
        @NotBlank
        private String newPassword;
        @NotBlank
        private String confirmPassword;
    }

    @Getter
    @Setter
    public static class ToggleStatusReq extends RequestModel {
        @NotNull
        private Integer staffId;
        private Integer operatorId;
    }
}
