package com.tzCloud.core.facade.caseMatching.req;

import com.tzCloud.arch.common.RequestModel;
import lombok.Data;

/**
 * @author zxiao
 * @Title: CourtReq
 * @ProjectName tzCloud-parent
 * @Description:
 * @date 2019/4/23 14:20
 */
@Data
public class CourtReq extends RequestModel {
    /**
     * 主键
     */
    private Integer id;
    /**
     * 法院名称
     */
    private String name;
    /**
     * 法院类型（基层法院，中层法院，高级法院，最高法院）
     */
    private String type;
    /**
     * 法院地址
     */
    private String address;
    /**
     * 电话
     */
    private String phone;
    /**
     * 其他联系方式
     */
    private String otherPhone;
    /**
     * 工作时间
     */
    private String workTime;
    /**
     * 描述
     */
    private String description;
    /**
     * 图片链接
     */
    private String imgUrl;
    /**
     * 省
     */
    private String province;
    /**
     * 市
     */
    private String city;
    /**
     * 删除标志
     */
    private Boolean deleteFlag;
    /**
     * 创建时间
     */
    private java.util.Date createTime;
    /**
     * 更新时间
     */
    private java.util.Date updateTime;
}
