package com.mybatisPlus.demo.model;

import com.baomidou.mybatisplus.enums.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

import com.baomidou.mybatisplus.annotations.Version;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author liuhaolei
 * @since 2019-06-11
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("jd_pm_transaction_data_legal")
public class JdPmTransactionDataLegal extends Model<JdPmTransactionDataLegal> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 编码
     */
    private String code;
    /**
     * 类别名称
     */
    @TableField("category_name")
    private String categoryName;
    /**
     * 标题
     */
    private String title;
    /**
     * 起拍价
     */
    @TableField("start_price")
    private String startPrice;
    /**
     * 最低加价
     */
    @TableField("priceLower_offset")
    private String pricelowerOffset;
    /**
     * 评估价
     */
    @TableField("assessment_price")
    private String assessmentPrice;
    /**
     * 保证金
     */
    @TableField("ensure_price")
    private String ensurePrice;
    /**
     * 处置律师
     */
    @TableField("consult_name")
    private String consultName;
    /**
     * 处置律师电话号码
     */
    @TableField("consult_tel")
    private String consultTel;
    /**
     * 省
     */
    private String province;
    /**
     * 市
     */
    private String city;
    /**
     * 区
     */
    private String county;
    /**
     * 详细地址
     */
    private String address;
    /**
     * 成交价
     */
    @TableField("current_price")
    private String currentPrice;
    /**
     * 延迟次数
     */
    @TableField("delayed_count")
    private String delayedCount;
    /**
     * 报名人数
     */
    @TableField("access_ensure_num")
    private String accessEnsureNum;
    /**
     * 围观人数
     */
    @TableField("access_num")
    private String accessNum;
    /**
     * 开始时间
     */
    @TableField("begin_time")
    private Date beginTime;
    /**
     * 结束时间
     */
    @TableField("end_time")
    private Date endTime;
    /**
     * 成交确认书地址
     */
    @TableField("confirmation_url")
    private String confirmationUrl;
    /**
     * 是否有优先竞买人
     */
    @TableField("priority_flag")
    private String priorityFlag;
    /**
     * 拍卖次数
     */
    @TableField("auction_times")
    private String auctionTimes;
    /**
     * 裁定法院
     */
    @TableField("shop_name")
    private String shopName;
    /**
     * 创建时间
     */
    @TableField("create_time")
    private Date createTime;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
