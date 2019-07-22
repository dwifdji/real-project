
package com._360pai.core.model.asset;

import com._360pai.core.common.constants.AssetEnum;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * <p>封装实体bean</p>
 *
 * @author Generator
 * @date 2018年09月12日 09时20分20秒
 */
@Getter
@Setter
public class Asset implements java.io.Serializable {

    /**
     * 自增ID
     */
    private Integer id;
    /**
     * 状态
     */
    private AssetEnum.Status status;
    /**
     * 创建时间
     */
    private java.util.Date createdAt;
    /**
     * 标的编号
     */
    private String code;
    /**
     * 标的名称
     */
    private String name;
    /**
     * 标的所在省id
     */
    private java.lang.String provinceId;
    /**
     * 标的所在地
     */
    private String cityId;
    /**
     * 标的所在区id
     */
    private java.lang.String areaId;
    /**
     * 委托人ID
     */
    private Integer partyId;
    /**
     * 标的物可能由多个
     */
    private Integer quantity;
    /**
     * 剩余个数
     */
    private Integer remain;
    /**
     * 标的物类型
     */
    private AssetEnum.AssetType assetType;
    /**
     * 备注
     */
    private String remark;
    /**
     * 上传机构ID
     */
    private Integer agencyId;
    /**
     * 保留价
     */
    private java.math.BigDecimal reservePrice;
    /**
     * 描述文件
     */
    private String descriptionDoc;
    /**
     * 标的详情
     */
    private String detail;
    /**
     * 联系人
     */
    private String contactName;
    /**
     * 联系人手机号
     */
    private String contactPhone;
    /**
     * 联系人QQ
     */
    private String contactQq;
    /**
     * 包含图片的JSON数据
     */
    private com.alibaba.fastjson.JSONObject extra;
    /**
     * 期望拍卖模式
     */
    private AssetEnum.ExpectedMode expectedMode;
    /**
     * 参考价
     */
    private java.math.BigDecimal refPrice;
    /**
     * 起拍价
     */
    private java.math.BigDecimal startingPrice;
    /**
     * 期望拍卖开始时间
     */
    private java.util.Date beginAt;
    /**
     * 期望拍卖结束时间
     */
    private java.util.Date endAt;
    /**
     * 标的属性ID
     */
    private Integer propertyId;
    /**
     * 标的类型ID(模板ID)
     */
    private Integer categoryId;
    /**
     * 拍品类型
     */
    private Integer TCategoryId;
    /**
     * 拍品子类型
     */
    private Integer TCategoryOtionId;
    /**
     * 标的物选项
     */
    private String options;
    /**
     * 交接天数 仅作为 属性
     */
    private Integer handoverDays;
    /**
     * 支付天数 仅作为 属性
     */
    private Integer payDays;
    /**
     * 特别说明
     */
    private String specialDetail;
    /**
     * 银行账户名
     */
    private String bankAccountName;
    /**
     * 银行账户号码
     */
    private String bankAccountNumber;
    /**
     * 银行ID
     */
    private Integer bankId;
    /**
     * 用户手动输入的银行名称
     */
    private String bankName;
    /**
     * 尽调报告 1:基础尽调 2:完整尽调
     */
    private BigDecimal tuneReport;
    /**
     * 尽调报告url
     */
    private com.alibaba.fastjson.JSONObject tuneReportUrl;
    /**
     * 尽调报告是否授权  false未授权  true授权
     */
    private java.lang.Boolean tuneReportAuthorization;
    /**
     * 评估报告 1:基础评估 2:完整评估
     */
    private BigDecimal evaluationReport;
    /**
     * 评估报告url
     */
    private com.alibaba.fastjson.JSONObject evaluationReportUrl;
    /**
     * 债券转让协议url
     */
    private String claimsTransferUrl;
    /**
     * 转让公告url
     */
    private String transferAnnouncementUrl;
    /**
     * 更新时间
     */
    private java.util.Date updatedAt;
    /**
     * 线上 线下操作 0:线下操作 1:线上操作
     */
    private java.lang.Integer onlined;
    /**
     * 业务类型 0:拍卖 1:配置乐 2:处置服务商
     */
    private java.lang.Integer busType;
    /**
     * 债权本金
     */
    private java.math.BigDecimal debtPrincipal;
    /**
     * 债权利息
     */
    private java.math.BigDecimal debtInterest;
    /**
     * 上拍来源：0 平台 1 机构
     */
    private java.lang.String comeFrom;
    /**
     * 标识老数据 0老数据 1新数据
     */
    private java.lang.Boolean oldData;
    /**
     * 拍卖类型
     */
    private String categoryName;
    /**
     * 城市名称
     */
    private String cityName;
    /**
     * spvId
     */
    private Integer spvId;
    /**
     * 删除标志（0 否 1 是）
     */
    private java.lang.Boolean deleteFlag;
    /**
     * 连拍状态 0 连拍 1 不连拍
     */
    private Integer jointStatus;
    /**
     * 商品分类
     */
    private String busTypeName;
    /**
     * 银行类全线下标志
     */
    private java.lang.Boolean bankOfflineFlag;
    /**
     * 租赁权的小状态
     */
    private String subStatus;
    /**
     * 拒绝状态
     */
    private String rejectReason;

    /**
     * 镇id
     */
    private String townId;
}
