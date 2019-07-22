package com.winback.core.service.assistant.impl;

import com.winback.arch.common.utils.DateUtil;
import com.winback.arch.core.file.FilePathUtils;
import com.winback.arch.core.file.QiNiuUtil;
import com.winback.arch.core.sysconfig.properties.GatewayProperties;
import com.winback.core.condition.assistant.TFileCondition;
import com.winback.core.dao.assistant.TFileDao;
import com.winback.core.model.assistant.TFile;
import com.winback.core.service.assistant.TFileService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.Date;

/**
 * 描述
 *
 * @author : whisky_vip
 * @date : 2018/11/21 10:54
 */
@Service
@Slf4j
public class TFileServiceImpl implements TFileService {

    @Autowired
    private TFileDao tFileDao;
    @Autowired
    private QiNiuUtil qiNiuUtil;
    @Autowired
    private GatewayProperties gatewayProperties;

    @Override
    public String watermark(String url) {
        String   watermarkResult = "";
        String[] urlSplit        = url.split("\\?");
        String   baseUrl         = urlSplit[0];
        log.info("开始加水印: {}", url);
        boolean acceptable = FilePathUtils.acceptFile(url);
        if (acceptable) {
            TFileCondition condition = new TFileCondition();
            condition.setFileUrl(baseUrl);
            TFile tFile = tFileDao.selectFirst(condition);
            if (tFile != null) {
                watermarkResult = tFile.getFileUrlWatermark();
            } else {
                File baseFile = FilePathUtils.downLoadFromUrl(baseUrl, FilePathUtils.getFileName(url));
                log.info("开始加载水印文件: {}", url);
                File watermarkFile = FilePathUtils.downLoadWatermarkFileFromUrl(baseUrl, FilePathUtils.getFileName(url));
                log.info("加载水印文件完成: {}", url);
                if (watermarkFile.exists()) {
//                String baseFileQeTag      = qiNiuUtil.getQETag(baseFile);
                    String watermarkFileQeTag = qiNiuUtil.getQETag(watermarkFile);

                    TFileCondition condition2 = new TFileCondition();
                    condition2.setEtagWatermark(watermarkFileQeTag);
                    TFile tFile2 = tFileDao.selectFirst(condition2);
                    if (tFile2 != null) {
                        watermarkResult = tFile2.getFileUrlWatermark();
                    } else {
                        String returnUrl = "";
                        try {
                            returnUrl = qiNiuUtil.uploadToPublic(watermarkFile, "watermark/" + DateUtil.format(new Date(), DateUtil.FORMAT_LONG_NO_SPLIT2) + "/" + FilePathUtils.getFileName(url));
                            log.info("returnUrl:" + returnUrl);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        watermarkResult = StringUtils.isNotBlank(returnUrl) ? "https://" + gatewayProperties.getDomain() + "/" + returnUrl : null;
                        TFile model = new TFile();
                        model.setFileUrl(baseUrl);
                        model.setFileUrlWatermark(watermarkResult);
//                    model.setEtag(baseFileQeTag);
                        model.setEtagWatermark(watermarkFileQeTag);
                        tFileDao.insert(model);
                    }
                }
            }
        }
        if (StringUtils.isNotBlank(watermarkResult)) {
            if (urlSplit.length >= 2) {
                return watermarkResult + "?" + urlSplit[1];
            } else {
                return watermarkResult;
            }
        }
        return url;
    }

    @Override
    public String watermark(File file) {
        String   watermarkResult = "";
        File watermarkFile = file;
        if (watermarkFile.exists()) {
            String watermarkFileQeTag = qiNiuUtil.getQETag(watermarkFile);
            TFileCondition condition2 = new TFileCondition();
            condition2.setEtagWatermark(watermarkFileQeTag);
            TFile tFile2 = tFileDao.selectFirst(condition2);
            if (tFile2 != null) {
                watermarkResult = tFile2.getFileUrlWatermark();
            } else {
                watermarkFile = FilePathUtils.watermarkFile(file);

                String returnUrl = "";
                try {
                    returnUrl = qiNiuUtil.uploadToPublic(watermarkFile, "watermark/" + DateUtil.format(new Date(), DateUtil.FORMAT_LONG_NO_SPLIT2) + "/" + RandomStringUtils.randomNumeric(6) + "/" + file.getName().split("\\.")[0]);
                    log.info("returnUrl:" + returnUrl);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                watermarkResult = StringUtils.isNotBlank(returnUrl) ? "https://" + gatewayProperties.getDomain() + "/" + returnUrl : null;
                TFile model = new TFile();
                model.setFileUrl(watermarkResult);
                model.setFileUrlWatermark(watermarkResult);
                model.setEtag(watermarkFileQeTag);
                model.setEtagWatermark(watermarkFileQeTag);
                tFileDao.insert(model);
            }
        }
        if (StringUtils.isNotBlank(watermarkResult)) {
            return watermarkResult;
        }
        return "";
    }
}
