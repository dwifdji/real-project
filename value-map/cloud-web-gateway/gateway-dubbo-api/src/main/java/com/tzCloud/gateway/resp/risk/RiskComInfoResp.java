package com.tzCloud.gateway.resp.risk;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 描述：风控预检公司信息
 * <p>
 * 作者： wuchuanqi
 * 版本： 1.0.0
 * 时间： 2019/1/17 10:44
 */
@Data
public class RiskComInfoResp implements Serializable {

    private String code;

    private String desc;

    private String comName;//公司名称

    private String comStatus;//公司状态


    /**
     * 1 公司 2 个人
     */
    private String type;
    /**
     * 1 正常 2 异常
     */
    private Integer exceptionFlag;
    /**
     * 注册资本
     */
    private String registerCapital;
    /**
     * 公司状态
     */
    private String status;
    /**
     * 注册地
     */
    private String registerArea;
    /**
     * 公司类型
     */
    private String comType;
    /**
     * 原告笔数
     */
    private String accuserNum;
    /**
     * 被告笔数
     */
    private String defendantNum;
    /**
     * 被执行笔数
     */
    private String executeNum;
    /**
     * 失信笔数
     */
    private String loseCreditNum;
    /**
     * 股东信息
     */
    private String shareholdersInfo;
    /**
     * 财产线索
     */
    private String propertyClue;





    public static RiskComInfoResp failure(String code , String desc){

        RiskComInfoResp resp = new RiskComInfoResp();

        resp.setCode(code);
        resp.setDesc(desc);
        return resp;
    }
}
