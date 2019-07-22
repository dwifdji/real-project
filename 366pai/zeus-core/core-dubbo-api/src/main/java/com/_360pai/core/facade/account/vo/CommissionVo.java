package com._360pai.core.facade.account.vo;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author xdrodger
 * @Title: InviteCommissionVo
 * @ProjectName zeus
 * @Description:
 * @date 2018/12/3 16:18
 */
@Data
public class CommissionVo implements Serializable {
    private String id;
    /**
     * 名称
     */
    private String name;
    /**
     * 类型
     */
    private String type;
    /**
     * 金额
     */
    private BigDecimal amount;
    /**
     * 好友来源
     */
    private String mobile;
    /**
     * 创建时间
     */
    @JSONField(format= "yyyy/MM/dd")
    private Date createTime;
    /**
     * 发放时间
     */
    @JSONField(format= "yyyy/MM/dd")
    private Date releaseTime;
}
