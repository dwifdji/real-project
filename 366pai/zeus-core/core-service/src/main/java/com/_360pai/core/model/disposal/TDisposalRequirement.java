
package com._360pai.core.model.disposal;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>封装实体bean</p>
 *
 * @author Generator
 * @date 2018年09月14日 10时33分09秒
 */
@Data
public class TDisposalRequirement implements java.io.Serializable {

    /**
     * 主键id
     */
    private Integer id;
    /**
     * 处置类型: 10：尽调 11：评估 12：司法处置
     * 13：执行处置 14：清房 15：催收  16：查找财产线索
     */
    private String  disposalType;
    /**
     * 发布处置需求用户id
     */
    private Integer accountId;
    private Integer partyId;
    /**
     * 处置单号
     */
    private String  disposalNo;
    /**
     * 平台单号
     */
    private String  platformNo;
    /**
     * 需求名称
     */
    private String  disposalName;
    /**
     * 案件描述
     */
    private String  caseDescription;
    /**
     * 需求描述
     */
    private String  requireDescription;
    /**
     * 处理周期
     */
    private Double  period;
    /**
     * 处置费用
     */
    private String  cost;
    /**
     * 00、未支付\r\n 10、等待平台审核\r\n 11、审核不通过\r\n 12、处置中\r\n20、已完成\r\n30、撮合成功\r\n
     */
    private String  disposalStatus;

    /**
     * 商品id
     */
    private Integer        assetId;
    /**
     * 发布时间
     */
    private Date           publishTime;
    /**
     * 平台标识  0：非平台 1：平台
     */
    private Integer        isPlatform;
    /**
     * 观看人数
     */
    private Integer        viewNum;
    /**
     * 关注人数
     */
    private Integer        followNum;
    /**
     * 创建时间
     */
    private java.util.Date createTime;
    /**
     * 更新时间
     */
    private java.util.Date updateTime;
    /**
     * 删除标识  0：未删除 1：删除
     */
    private Integer        isDel;
    /**
     * 付款成功单号
     */
    private String         payOrder;
    /**
     * 所在省id
     */
    private String         provinceId;
    /**
     * 所在城市id
     */
    private String         cityId;
    /**
     * 区县id
     */
    private java.lang.String areaId;
    /**
     * 图片json
     */
    private JSONObject     extra;
    /**
     * 投标数
     */
    private Integer        biddingNum;

    /**
     * 审核备注
     */
    private String  verifyContent;
    /**
     * 招标须知
     */
    private String  biddingNotice;
    /**
     * 招标须知上传操作员id
     */
    private Integer operatorNoticeId;
    /**
     * 手动完成操作员id
     */
    private Integer operatorFinishId;
    /**
     * 审核操作员id
     */
    private Integer operatorVerifyId;

    private Date deadline;

    private String providerAreas;

    /**
     * 上拍来源：0 平台 1 机构
     */
    private java.lang.String comeFrom;
    private BigDecimal debtInterest;
    private BigDecimal debtPrincipal;

    private String cityName;
    @JSONField(serialize = false)
    private String areaName;
    @JSONField(serialize = false)
    private String provinceName;

}
