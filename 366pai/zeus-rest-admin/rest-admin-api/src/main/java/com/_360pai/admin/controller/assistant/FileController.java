package com._360pai.admin.controller.assistant;

import com._360pai.admin.controller.AbstractController;
import com._360pai.arch.common.ResponseModel;
import com._360pai.core.facade.assistant.FileFacade;
import com._360pai.core.facade.assistant.req.FileReq;
import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.concurrent.*;

/**
 * 描述
 *
 * @author : whisky_vip
 * @date : 2018/11/20 15:51
 */
@Controller
@Slf4j
public class FileController extends AbstractController {

    @Reference(version = "1.0.0")
    FileFacade fileFacade;

    @RequestMapping(value = "/open/file/waterMarker")
    @ResponseBody
    public ResponseModel fileWaterMarker(@RequestBody FileReq.Watermark req) {
        JSONObject jsonObject   = new JSONObject();
        String     watermarkUrl = req.getUrl();

        //异步10秒钟还没返回，则就原url直接返回
        Callable<String>   watermark = () -> fileFacade.watermark(req.getUrl());
        FutureTask<String> task      = new FutureTask<>(watermark);
        new Thread(task).start();
        try {
            watermarkUrl = task.get(10000L, TimeUnit.MILLISECONDS);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("打水印报错:{}",e);
        }
        jsonObject.put("watermarkUrl", watermarkUrl);
        return ResponseModel.succ(jsonObject);
    }

}
