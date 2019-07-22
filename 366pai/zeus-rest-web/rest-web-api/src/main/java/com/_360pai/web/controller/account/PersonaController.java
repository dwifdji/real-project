package com._360pai.web.controller.account;

import com._360pai.arch.common.ResponseModel;
import com._360pai.arch.common.enums.ApiCallResult;
import com._360pai.core.facade.account.PersonaFacade;
import com._360pai.core.facade.account.req.PersonaReq;
import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

/**
 * 客户画像相关
 */
@RestController
@RequestMapping(value = "/open/persona", produces = "application/json;charset=UTF-8")
public class PersonaController {

    private final Logger logger = LoggerFactory.getLogger(PersonaController.class);

    @Reference(version = "1.0.0")
    PersonaFacade personaFacade;

    @PostMapping(value = "/create")
    public ResponseModel create(@RequestBody PersonaReq req) {
        logger.info("客户画像创建，request body={}", JSON.toJSONString(req));
        try {
            return personaFacade.create(req);
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("客户画像创建异常，request body={}，msg=", JSON.toJSONString(req), e.getMessage());
            return ResponseModel.fail(ApiCallResult.EXCEPTION);
        }
    }

    @PostMapping(value = "/update")
    public ResponseModel update(@RequestBody PersonaReq req) {
        logger.info("客户画像修改，request body={}", JSON.toJSONString(req));
        try {
            return personaFacade.update(req);
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("客户画像修改异常，request body={}，msg=", JSON.toJSONString(req), e.getMessage());
            return ResponseModel.fail(ApiCallResult.EXCEPTION);
        }
    }

    @GetMapping(value = "/detail")
    public ResponseModel detail(PersonaReq req) {
        logger.info("客户画像详情，request body={}", JSON.toJSONString(req));
        try {
            return personaFacade.detail(req);
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("客户画像详情异常，request body={}，msg=", JSON.toJSONString(req), e.getMessage());
            return ResponseModel.fail(ApiCallResult.EXCEPTION);
        }
    }

    @GetMapping(value = "/list")
    public ResponseModel list(PersonaReq req) {
        logger.info("客户画像列表，request body={}", JSON.toJSONString(req));
        try {
            return personaFacade.list(req);
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("客户画像列表异常，request body={}，msg=", JSON.toJSONString(req), e.getMessage());
            return ResponseModel.fail(ApiCallResult.EXCEPTION);
        }
    }

    @PostMapping(value = "/login")
    public ResponseModel login(@RequestBody PersonaReq req) {
//        logger.info("客户画像登录，request body={}", JSON.toJSONString(req));
        try {
            return personaFacade.login(req);
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("客户画像登录异常，request body={}，msg=", JSON.toJSONString(req), e.getMessage());
            return ResponseModel.fail(ApiCallResult.EXCEPTION);
        }
    }
}
