package com.winback.core.facade.account.vo;

import com.winback.core.facade._case.resp.CaseVo;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author xdrodger
 * @Title: Account
 * @ProjectName winback
 * @Description:
 * @date 2019/1/28 15:51
 */
@Data
public class ProjectManager implements Serializable {
    /**
     * 主键
     */
    private Integer id;
    /**
     * 手机号
     */
    private String mobile;
    /**
     * 昵称
     */
    private String nickName;
    /**
     * 头像
     */
    private String headImgUrl;
    /**
     * 注册来源
     */
    private String registerSource;
    /**
     * 注册来源描述
     */
    private String registerSourceDesc;
    /**
     * 渠道来源
     */
    private String source;
    /**
     * 邀请码
     */
    private String inviteCode;
    /**
     * 创建时间
     */
    private java.util.Date createTime;

    /**
     * 分配的案件数量
     */
    private int allocatedCaseNum;
    /**
     * 完成的案件数量
     */
    private int successfulCaseNum;

    private List<CaseVo> caseList;
}
