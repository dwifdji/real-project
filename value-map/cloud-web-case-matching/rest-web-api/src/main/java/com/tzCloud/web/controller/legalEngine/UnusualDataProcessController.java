package com.tzCloud.web.controller.legalEngine;

import com.alibaba.dubbo.config.annotation.Reference;
import com.tzCloud.arch.common.ResponseModel;
import com.tzCloud.core.facade.legalEngine.UnusualDataProcessFacade;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author xiaolei
 * @create 2019-05-06 16:36
 */
@RestController
public class UnusualDataProcessController {

    @Reference(version = "1.0.0")
    private UnusualDataProcessFacade unusualDataProcessFacade;

    /**
     * 处理 t_lawyer_data 表中  一条记录  、 包含多个律师的情况
     * eg: 刘丰畅、李牧
     */
    @GetMapping("/open/unusual/lawyerDataProcess1")
    public ResponseModel unusualLawyerDataProcess1() {
        unusualDataProcessFacade.unusualLawyerDataProcess1();
        return ResponseModel.succ();
    }

    /**
     * 处理 t_lawyer_data 更新 lawyer_id
     *
     */
    @GetMapping("/open/unusual/lawyerDataProcess2")
    public ResponseModel unusualLawyerDataProcess2() {
        unusualDataProcessFacade.unusualLawyerDataProcess2();
        return ResponseModel.succ();
    }

    /**
     * 生成parse_lawyer_info 记录
     */
    @GetMapping("/open/unusual/lawyerDataProcess3")
    public ResponseModel unusualLawyerDataProcess3() {
        unusualDataProcessFacade.unusualLawyerDataProcess3();
        return ResponseModel.succ();
    }
}
