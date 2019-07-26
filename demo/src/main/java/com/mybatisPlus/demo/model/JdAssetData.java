package com.mybatisPlus.demo.model;

import com.baomidou.mybatisplus.enums.IdType;
import java.math.BigDecimal;
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
@TableName("jd_asset_data")
public class JdAssetData extends Model<JdAssetData> {

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
     * 名称
     */
    private String name;
    /**
     * 省份
     */
    private String province;
    /**
     * 市区
     */
    private String city;
    /**
     * 区域
     */
    private String country;
    /**
     * 详细地址
     */
    private String address;
    /**
     * 建筑面积
     */
    @TableField("build_area")
    private BigDecimal buildArea;
    /**
     * 分类名称
     */
    @TableField("category_name")
    private String categoryName;
    /**
     * 经度
     */
    private String lng;
    /**
     * 维度
     */
    private String lat;
    /**
     * 创建时间
     */
    @TableField("create_time")
    private Date createTime;
    /**
     * 删除标识
     */
    @TableField("delete_flag")
    private Integer deleteFlag;
    /**
     * 成交价格
     */
    @TableField("current_price")
    private BigDecimal currentPrice;
    /**
     * 起拍价格
     */
    @TableField("start_price")
    private BigDecimal startPrice;
    /**
     * 评估价格
     */
    @TableField("assessment_price")
    private BigDecimal assessmentPrice;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
