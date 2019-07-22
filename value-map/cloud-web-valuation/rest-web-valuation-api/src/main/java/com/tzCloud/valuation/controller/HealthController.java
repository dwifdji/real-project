package com.tzCloud.valuation.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zxiao
 * @Title: healthController
 * @ProjectName zeus-parent
 * @date 2018/8/9 14:41
 */
@RestController
@RequestMapping(value = "/health")
public class HealthController {

    @RequestMapping
    public String checkHealth() {
        return "ok";
    }
}
