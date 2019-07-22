package com._360pai.web.controller.assistant;

import com._360pai.arch.common.ResponseModel;
import com._360pai.arch.common.utils.DateUtil;
import com._360pai.arch.common.utils.RandomNumberGenerator;
import com._360pai.arch.core.file.FilePathUtils;
import com._360pai.arch.core.file.QiNiuUtil;
import com._360pai.arch.core.sysconfig.properties.GatewayProperties;
import com._360pai.core.facade.assistant.FileFacade;
import com._360pai.core.facade.assistant.req.FileReq;
import com._360pai.web.controller.AbstractController;
import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Path;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;

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
    @Autowired
    private QiNiuUtil         qiNiuUtil;
    @Autowired
    private GatewayProperties gatewayProperties;

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

    @PostMapping(value = "/open/file/imageUpload")
    @ResponseBody
    public ResponseModel imageUpload(@RequestParam("files") MultipartFile[] files) {
        List<String> list = Lists.newArrayList();
        //判断file数组不能为空并且长度大于0
        if (files != null && files.length > 0) {
            //循环获取file数组中得文件
            for (int i = 0; i < files.length; i++) {
                MultipartFile file = files[i];
                //保存文件
                Boolean flag = saveFile(file);
                if (flag) {
                    File uploadFile = new File(FilePathUtils.getInputPath() + file.getOriginalFilename());
                    if (uploadFile.exists()) {
                        String returnUrl = null;
                        try {
                            returnUrl = qiNiuUtil.uploadToPublic(uploadFile, DateUtil.format(new Date(), DateUtil.FORMAT_LONG_NO_SPLIT) + "/" + file.getOriginalFilename());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        if (StringUtils.isNotBlank(returnUrl)) {
                            String watermarkResult = "https://" + gatewayProperties.getDomain() + "/" + returnUrl;
                            list.add(watermarkResult);
                        }
                    }
                }
            }
        }
        // 重定向
        return ResponseModel.succ(list);
    }


    private boolean saveFile(MultipartFile file) {
        // 判断文件是否为空
        if (!file.isEmpty()) {
            try {
                // 文件保存路径
                String savePath = FilePathUtils.getInputPath() + file.getOriginalFilename();
                // 转存文件
                file.transferTo(new File(savePath));

                return true;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    /**
     * 生产二维码图片
     */
    @GetMapping(value = "/open/create/qr/code")
    public void createQrCode(FileReq.Watermark req, HttpServletResponse response) {
        String content = req.getUrl();
        int width = 300;
        int height = 300;
        String format = "png";
        //定义二维码的参数
        HashMap map = new HashMap();
        //设置编码
        map.put(EncodeHintType.CHARACTER_SET, "utf-8");
        //设置纠错等级
        map.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.M);
        map.put(EncodeHintType.MARGIN, 2);
        String path = FilePathUtils.getOutPutPath() + RandomNumberGenerator.wordGenerator(9) + ".png";
        try {
            //生成二维码
            BitMatrix bitMatrix = new MultiFormatWriter().encode(content, BarcodeFormat.QR_CODE, width, height);
            Path file = new File(path).toPath();
            response.setContentType("image/png");
            OutputStream os = response.getOutputStream();
            MatrixToImageWriter.writeToStream(bitMatrix, format, os);
        } catch (WriterException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
