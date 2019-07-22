package com._360pai.applet.controller.numberJump;

import com._360pai.arch.common.ResponseModel;
import com._360pai.core.facade.applet.CalculatorFacade;
import com.alibaba.dubbo.config.annotation.Reference;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 描述
 *
 * @author : whisky_vip
 * @date : 2019/6/3 13:39
 */
@RestController
@Slf4j
public class CalculatorDataFixController {
    @Reference(version = "1.0.0")
    CalculatorFacade numberJumpFacade;

    @GetMapping(value = "/open/numberJump/dataFix")
    public ResponseModel dataFix() {
        return numberJumpFacade.dataFix();
    }

}
