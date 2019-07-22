package com._360pai.core.facade.applet.vo;

import com._360pai.core.facade.account.vo.CompanyVo;
import com._360pai.core.facade.account.vo.UserVo;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author xdrodger
 * @Title: ShopUpdateApplyRecordVo
 * @ProjectName zeus
 * @Description:
 * @date 2018/11/30 09:37
 */
@Data
public class ShopUpdateApplyRecordVo implements Serializable {
    /**
     *
     */
    private Integer id;
    /**
     * 店铺id
     */
    private Integer shopId;
    /**
     * 店铺名称
     */
    private String name;
    /**
     * 店铺头像
     */
    private String logoUrl;
    /**
     * 申请状态(PENDING = 1,APPROVED = 2,REJECT = 3)
     */
    private String status;

    private String statusDesc;
    /**
     * 审核人
     */
    private String operator;
    /**
     * 原因
     */
    private String reason;
    /**
     * 申请时间
     */
    private Date createTime;
    /**
     * 店铺推荐码
     */
    private String inviteCode;
    private String type;
    private UserVo user;
    private CompanyVo company;

    /**
     * 原店铺名称
     */
    private String oldName;
    /**
     * 原店铺头像
     */
    private String oldLogoUrl;
}
