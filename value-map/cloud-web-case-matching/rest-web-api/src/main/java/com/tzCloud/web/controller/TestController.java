package com.tzCloud.web.controller;

import com.tzCloud.arch.core.redis.RedisCachemanager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @Autowired
    private RedisCachemanager redisCachemanager;

    @RequestMapping("/")
    public String index() {
        return "OK";
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

}
