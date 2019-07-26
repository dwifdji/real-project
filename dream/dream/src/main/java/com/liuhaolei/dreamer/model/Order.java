package com.liuhaolei.dreamer.model;

import com.baomidou.mybatisplus.enums.IdType;
import java.math.BigDecimal;
import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
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
 * @since 2019-01-07
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("t_order")
public class Order extends Model<Order> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 订单名称
     */
    @TableField("order_name")
    private String orderName;
    /**
     * 订单类型
     */
    @TableField("order_type")
    private String orderType;
    /**
     * 具体位置
     */
    private String address;
    /**
     * 工期
     */
    private String duration;
    /**
     * 总共额
     */
    private BigDecimal price;
    /**
     * 总面积
     */
    @TableField("total_area")
    private String totalArea;
    /**
     * 图片
     */
    private String images;
    /**
     * 详细描述
     */
    @TableField("order_desc")
    private String orderDesc;
    /**
     * 创建时间
     */
    @TableField("create_at")
    private Date createAt;
    /**
     * 修改时间
     */
    @TableField("update_at")
    private Date updateAt;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
