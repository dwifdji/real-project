package com.winback.admin.vo;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;
import org.omg.PortableInterceptor.INACTIVE;

import java.io.Serializable;

/**
 * @author xdrodger
 * @Title: AccountInfo
 * @ProjectName winback
 * @Description:
 * @date 2019/1/25 15:30
 */
@Data
public class LoginInfo implements Serializable {
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
     * 是否管理员
     */
    @JSONField(serialize = false)
    private Boolean adminFlag;
    /**
     * 创建时间
     */
    private java.util.Date createTime;
}
