package com.tzCloud.arch.common;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 日志实体
 *
 * @author : whisky_vip
 * @date : 2018/8/23 14:52
 */
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@Data
public class OperatorLogModel implements Serializable {
    private static final long serialVersionUID = -7847270097936488649L;
    /**
     * 日志编号
     */
    private String  id;
    /**
     * 用户id
     */
    private Integer accountId;
    /**
     * 用户类型
     */
    private String  accountType;
    /**
     * platform：currentPartyId，partner：agencyId
     */
    private Integer partyId;

    /**
     * 用户TOKEN
     */
    private String token;
    /**
     * 开始时间
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date   startTime;
    /**
     * 结束时间
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date   endTime;
    /**
     * 消耗时间
     */
    private Long   spendTime;

    /**
     * 请求参数
     */
    private String parameter;
    /**
     * 返回参数
     */
    private String result;

    /**
     * 请求方式
     */
    private String httpMethod;

    /**
     * 报错信息
     */
    private String exception;

    private String remoteAddr;
    private String classMethod;
    private String requestUri;
    private String uri;
    private String sessionId;
    private String browser;
    private String type;

    private String area;
    private String province;
    private String city;
    private String isp;

    /**
     * 系统名字 默认admin
     */
    private String applicationName;

}
