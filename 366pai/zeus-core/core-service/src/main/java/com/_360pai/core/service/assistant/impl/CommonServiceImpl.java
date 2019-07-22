package com._360pai.core.service.assistant.impl;

import com._360pai.arch.common.ResponseModel;
import com._360pai.arch.common.enums.ApiCallResult;
import com._360pai.arch.common.utils.DateUtil;
import com._360pai.arch.common.utils.RandomNumberGenerator;
import com._360pai.arch.core.file.FilePathUtils;
import com._360pai.arch.core.file.QiNiuUtil;
import com._360pai.arch.core.sysconfig.properties.GatewayProperties;
import com._360pai.core.exception.BusinessException;
import com._360pai.core.service.assistant.CommonService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.Date;

/**
 * @author xdrodger
 * @Title: CommonServiceImpl
 * @ProjectName zeus
 * @Description:
 * @date 2018/11/30 16:50
 */
@Service
public class CommonServiceImpl implements CommonService {
    @Autowired
    private QiNiuUtil qiNiuUtil;
    @Autowired
    private GatewayProperties gatewayProperties;

    @Override
    public String saveExternalImgUrl(String imgUrl) {
        if (StringUtils.isEmpty(imgUrl)) {
            throw new BusinessException(ApiCallResult.EMPTY);
        }
        File uploadFile = null;
        String returnUrl = "";
        try {
            if (StringUtils.isEmpty(imgUrl)) {
                return "";
            }
            String fileName = RandomNumberGenerator.getUUID();
            uploadFile = FilePathUtils.downLoadFromUrl(imgUrl, fileName);
            if (uploadFile != null) {
                returnUrl = qiNiuUtil.uploadToPublic(uploadFile, "shop/logUrl/"+ DateUtil.format(new Date(), DateUtil.FORMAT_LONG_NO_SPLIT) + "/" + fileName);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (uploadFile != null) {
                uploadFile.delete();
            }
        }
        if (StringUtils.isEmpty(returnUrl)) {
            throw new BusinessException(ApiCallResult.FAILURE);
        }
        return  "https://" + gatewayProperties.getDomain() + "/" + returnUrl;
    }
}
