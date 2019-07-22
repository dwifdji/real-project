package com.winback.admin.vo;

import com.winback.core.facade.account.vo.Permission;
import com.winback.core.facade.account.vo.Role;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author xdrodger
 * @Title: Profile
 * @ProjectName winback
 * @Description:
 * @date 2019/1/25 15:27
 */
@Data
public class Profile implements Serializable {
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
     * 创建时间
     */
    private java.util.Date createTime;

}
