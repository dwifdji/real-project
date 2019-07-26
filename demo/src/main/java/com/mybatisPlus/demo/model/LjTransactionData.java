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
 * @since 2019-06-12
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("lj_transaction_data")
public class LjTransactionData extends Model<LjTransactionData> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 名称
     */
    private String title;
    /**
     * 成交时间
     */
    @TableField("end_time")
    private String endTime;
    /**
     * 成交价格
     */
    @TableField("current_price")
    private String currentPrice;
    /**
     * 单位价格
     */
    @TableField("unit_price")
    private String unitPrice;
    /**
     * 挂牌价格
     */
    @TableField("listing_price")
    private String listingPrice;
    /**
     * 成交周期
     */
    @TableField("transaction_cycle")
    private String transactionCycle;
    /**
     * 调价次数
     */
    @TableField("price_adjustment")
    private String priceAdjustment;
    /**
     * 带看次数
     */
    @TableField("look_times")
    private Integer lookTimes;
    /**
     * 关注人数
     */
    @TableField("attention_num")
    private Integer attentionNum;
    /**
     * 浏览人数
     */
    @TableField("view_num")
    private String viewNum;
    /**
     * 房屋类型
     */
    @TableField("house_type")
    private String houseType;
    /**
     * 具体层数
     */
    @TableField("house_floor")
    private String houseFloor;
    /**
     * 建筑面积
     */
    @TableField("construction_area")
    private String constructionArea;
    /**
     * 户型结构
     */
    @TableField("house_structure")
    private String houseStructure;
    /**
     * 使用面积
     */
    @TableField("Inner_area")
    private String innerArea;
    /**
     * 建筑类型
     */
    @TableField("building_type")
    private String buildingType;
    /**
     * 房子方位
     */
    @TableField("house_orientation")
    private String houseOrientation;
    /**
     * 建成年代
     */
    @TableField("built_era")
    private String builtEra;
    /**
     * 装修情况
     */
    @TableField("renovation_condition")
    private String renovationCondition;
    /**
     * 建筑结构
     */
    @TableField("building_structure")
    private String buildingStructure;
    /**
     * 供暖方式
     */
    @TableField("heating_method")
    private String heatingMethod;
    /**
     * 梯户比例
     */
    @TableField("ladder_ratio")
    private String ladderRatio;
    /**
     * 产权年限
     */
    @TableField("year_limit")
    private String yearLimit;
    /**
     * 是否有电梯
     */
    @TableField("elevator_flag")
    private String elevatorFlag;
    /**
     * 编码
     */
    private String code;
    /**
     * 交易属性
     */
    @TableField("trading_authority")
    private String tradingAuthority;
    /**
     * 挂牌时间
     */
    @TableField("listing_time")
    private String listingTime;
    /**
     * 房屋用途
     */
    @TableField("use_type")
    private String useType;
    /**
     * 房屋年限
     */
    @TableField("house_limit")
    private String houseLimit;
    /**
     * 区域id
     */
    @TableField("area_id")
    private Integer areaId;
    /**
     * 详细地址
     */
    private String address;
    /**
     * 经度
     */
    private String lng;
    /**
     * 纬度
     */
    private String lat;
    /**
     * 房产归属
     */
    @TableField("house_belong")
    private String houseBelong;
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
