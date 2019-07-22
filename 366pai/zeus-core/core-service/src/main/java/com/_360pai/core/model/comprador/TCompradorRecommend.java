
package com._360pai.core.model.comprador;

import lombok.Data;

/**
 * <p>封装实体bean</p>
 *
 * @author Generator
 * @date 2018年09月03日 12时40分06秒
 */
@Data
public class TCompradorRecommend implements java.io.Serializable {

    /**
     *
     */
    private Integer        id;
    /**
     * 单号
     */
    private String         recommendNo;
    /**
     * 联系电话
     */
    private String         contactPhone;
    /**
     * 联系地址
     */
    private String         contactAddress;
    /**
     * 联系人
     */
    private String         contactName;
    /**
     * 资产说明
     */
    private String         recommendDescription;
    /**
     * 推荐人id
     */
    private Integer        accountId;
    private Integer        partyId;
    /**
     * 10 推介中 20已完成 30撮合成功
     */
    private String         recommendStatus;
    /**
     * 备注
     */
    private String         remark;
    /**
     * 是否删除（0 否 1 是）
     */
    private Boolean        isDelete;
    /**
     * 生成时间
     */
    private java.util.Date createdTime;
    /**
     * 更新时间
     */
    private java.util.Date updateTime;
    /**
     * 操作人id
     */
    private String         operatorId;

}
