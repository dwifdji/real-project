package com._360pai.core.facade.account.resp;

import com._360pai.arch.common.BaseResp;
import com._360pai.core.facade.account.vo.AgencyApplyRecordVo;
import com._360pai.core.facade.account.vo.InviteCommissionVo;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author xdrodger
 * @Title: AcctResp
 * @ProjectName zeus
 * @Description:
 * @date 2018/12/3 17:17
 */
@Data
public class AcctResp implements Serializable {
    /**
     *
     */
    private Integer id;
    /**
     * user、compny、agency
     */
    private String type;
    /**
     *
     */
    private Integer partyId;
    /**
     * 总金额
     */
    private java.math.BigDecimal totalAmt = BigDecimal.ZERO;
    /**
     * 锁定金额
     */
    private java.math.BigDecimal lockAmt = BigDecimal.ZERO;
    /**
     * 可用余额
     */
    private java.math.BigDecimal availAmt = BigDecimal.ZERO;
    /**
     *
     */
    private java.util.Date createTime;
    /**
     *
     */
    private java.util.Date updateTime;
}
