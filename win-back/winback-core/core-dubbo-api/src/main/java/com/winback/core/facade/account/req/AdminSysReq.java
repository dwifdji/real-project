package com.winback.core.facade.account.req;

import com.winback.arch.common.AdminReq;
import lombok.Data;

import java.util.Collections;
import java.util.List;

/**
 * @author xdrodger
 * @Title: AdminSysReq
 * @ProjectName winback
 * @Description:
 * @date 2019/1/30 14:10
 */
public class AdminSysReq {

    @Data
    public static class QueryReq extends AdminReq {
        private String q;
        private String mobile;
        private String dept;
        private String status;
        private Integer id;
    }

    @Data
    public static class StaffAddReq extends AdminReq {
        /**
         * 手机号
         */
        private String mobile;
        /**
         * 密码
         */
        private String password;
        /**
         * 名称
         */
        private String name;
        /**
         * 邮箱
         */
        private String email;
        /**
         * QQ号
         */
        private String qq;
        /**
         * 部门
         */
        private String dept;
        /**
         * 备注
         */
        private String memo;
        /**
         * 状态（0：禁用，1：启用）
         */
        private Boolean status;

        private List<Integer> roleIds = Collections.EMPTY_LIST;

        private List<Integer> specialPermissionIds = Collections.EMPTY_LIST;
    }

    @Data
    public static class StaffUpdateReq extends AdminReq {
        private Integer id;
        /**
         * 手机号
         */
        private String mobile;
        /**
         * 名称
         */
        private String name;
        /**
         * 邮箱
         */
        private String email;
        /**
         * QQ号
         */
        private String qq;
        /**
         * 部门
         */
        private String dept;
        /**
         * 备注
         */
        private String memo;
        /**
         * 状态（0：禁用，1：启用）
         */
        private Boolean status;

        private List<Integer> roleIds = Collections.EMPTY_LIST;

        private List<Integer> specialPermissionIds = Collections.EMPTY_LIST;
    }

    @Data
    public static class StaffResetPasswordReq extends AdminReq {
        private Integer id;
        /**
         * 密码
         */
        private String password;
    }

    @Data
    public static class RoleAddReq extends AdminReq {
        /**
         * 角色名称
         */
        private String name;
        /**
         * 状态（0：禁用，1：启用）
         */
        private Boolean status;
        /**
         * 角色描述
         */
        private String description;
        private List<Integer> menuIds = Collections.EMPTY_LIST;
    }

    @Data
    public static class RoleUpdateReq extends AdminReq {
        private Integer id;
        /**
         * 角色名称
         */
        private String name;
        /**
         * 状态（0：禁用，1：启用）
         */
        private Boolean status;
        /**
         * 角色描述
         */
        private String description;
        private List<Integer> menuIds = Collections.EMPTY_LIST;
    }

    @Data
    public static class RoleDeleteReq extends AdminReq {
        private Integer id;
    }
}
