package com._360pai.core.facade.fastway.resp;

import com._360pai.arch.common.utils.DateUtil;
import com._360pai.core.facade.disposal.req.City;
import com._360pai.core.facade.fastway.vo.AuctionVO;
import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.util.Date;

/**
 * @author xiaolei
 * @create 2018-11-29 16:45
 */
@Data
public class AgencyAuctionDetailVO extends AuctionVO {

    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 审核状态
     */
    private String applyStatus;
    /**
     * 审核状态描述
     */
    private String applyStatusDesc;
    /**
     * 工作城市
     */
    private City workCity;
    /**
     * 许可证有效开始日期
     */
    @JSONField(format= DateUtil.NORM_DATE_PATTERN)
    private Date qualifiedBegin;
    /**
     * 许可证有效结束日期
     */
    @JSONField(format= DateUtil.NORM_DATE_PATTERN)
    private Date qualifiedEnd;
    /**
     * 成拍分成
     */
    private Integer serveBuyerPercent;
    /**
     * 送拍分成
     */
    private Integer serveSellerPercent;
    /**
     * 许可证签署时间
     */
    @JSONField(format= DateUtil.NORM_DATE_PATTERN)
    private Date qualifiedSignDate;
    /**
     * 营业开始时间
     */
    @JSONField(format= DateUtil.NORM_DATE_PATTERN)
    private Date businessBegin;
    /**
     * 营业结束时间
     */
    @JSONField(format= DateUtil.NORM_DATE_PATTERN)
    private Date businessEnd;
    /**
     * 资质批准证书号
     */
    private String auctionApproveNo;
    /**
     * 注册手机号
      */
    private String mobile;
    /**
     * 审核备注
     */
    private String remark;
    /**
     * 机构简称
     */
    private String shortName;
    /**
     * 机构代码
     */
    private String code;
    /**
     * 开户人员
     */
    private java.lang.Integer openAccountOperatorId;
    /**
     * 业务对接人
     */
    private java.lang.Integer businessOperatorId;
    /**
     * 开户人员
     */
    private java.lang.String openAccountOperator;
    /**
     * 业务对接人
     */
    private java.lang.String businessOperator;
    /**
     * 受托人
     */
    private java.lang.String trustee;
}
