package com.winback.arch.common;

import com.alibaba.fastjson.JSONObject;
import com.winback.arch.common.enums.EmailEnum;
import lombok.Data;

import java.io.File;
import java.io.Serializable;
import java.util.List;

/**
 * 发送邮件请求
 */
@Data
public class ComEmailSendReq implements Serializable {


    /**
     * 业务类型 必传
     *
     */
    private EmailEnum.BUS_TYPE busType;


    /**
     * 业务模板 非必传
     *
     */
    private EmailEnum.BUS_TEMPLATE busTemplate;


    /**
     * 业务模板参数 非必传
     *
     */
    private JSONObject templateParam;



    /**
     * 重要等级 非必传  报警邮件时传
     *
     */
    private EmailEnum.IMPORTANT_LEVEL importantLevel;

    /**
     * 系统模块 非必传
     *
     */
    private EmailEnum.MODULE_TYPE moduleType;


    /**
     * 平台模块
     *
     */
    private EmailEnum.WEB web;


    /**
     * 异常请求参数
     *
     */
    private String exceptionParam;

    /**
     * 发送人 非必传  不传去配置邮箱发送
     *
     */
    private List<String> emailList;


    /**
     * 邮件标题 非必传  不传取默认配置
     *
     */
    private String title;

    /**
     * 邮件内容 必传
     *
     */
    private String content;


    /**
     * 邮件附件 非必传
     *
     */
    private List<File> files;



}
