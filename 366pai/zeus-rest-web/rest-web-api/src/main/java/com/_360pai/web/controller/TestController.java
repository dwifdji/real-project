package com._360pai.web.controller;

import com._360pai.arch.core.redis.RedisCachemanager;
import com._360pai.core.facade.account.AccountFacade;
import com._360pai.core.facade.asset.AssetFacade;
import com._360pai.core.facade.account.resp.AccountResp;
import com._360pai.core.facade.asset.resp.AssetResp;
import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @Autowired
    private RedisCachemanager redisCachemanager;

    @Reference(version = "1.0.0")
    AccountFacade accountFacade;

    @Reference(version = "1.0.0")
    AssetFacade assetFacade;


    @RequestMapping("/")
    public String index() {
        return "OK";
    }

    @RequestMapping("/open/testDubbo")
    public String getAccount() {

        AccountResp account = accountFacade.getAccount(2L);

        return JSON.toJSONString(account);
    }

    @ResponseBody
    @RequestMapping("/open/setRedis")
    public String setRedis(String value) {
        redisCachemanager.set("hehe", value);
        Boolean testHeset = redisCachemanager.hSet("gege", "gegeg", value);
        Object  ss        = redisCachemanager.hGet("gege", "gegeg");
        System.out.println(testHeset);
        System.out.println(ss);
        return "ok";
    }

    @ResponseBody
    @RequestMapping("/open/getRedis")
    public String getRedis(String redisKey) {
        String value = (String) redisCachemanager.get(redisKey);
        return value;
    }


    @RequestMapping(value = "/open/testPage", produces = "application/json;charset=UTF-8")
    public String getAllAssetByPage() {
        PageInfo<AssetResp> pageInfo = assetFacade.getAllAssetByPage(1, 10);
        return JSON.toJSONString(pageInfo);
    }
}
