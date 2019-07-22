package com._360pai.core.facade.account.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

/**
 * @author xdrodger
 * @Title: DisposeProvider
 * @ProjectName dev2-zeus
 * @Description:
 * @date 2019-04-08 10:38
 */
@Data
public class DisposeProvider implements Serializable {
    private Integer id;
    /**
     * 处置类型(10：律师事务所，20：评估机构，30：律师)
     */
    private String disposeType;
    /**
     * 名称
     */
    private String name;
    /**
     * 所属律所
     */
    private String lawOffice;
    /**
     * 联系人姓名
     */
    private String contactName;
    /**
     * 联系方式
     */
    private String contactMobile;
    /**
     * 工作年限
     */
    private String workYear;
    /**
     * 自我介绍
     */
    private String introduction;
    /**
     * 可提供服务
     */
    private List<String> provideServices = Collections.EMPTY_LIST;
    /**
     * 业务区域
     */
    private String businessRegion;
    /**
     * 业务区域详细
     */
    private String businessRegionDetail;
    /**
     * 客户经理
     */
    private String customerManager;
    /**
     * 客户经理电话
     */
    private String customerManagerMobile;
}
