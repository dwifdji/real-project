package com.tzCloud.arch.web;

import com.tzCloud.arch.common.ResponseModel;
import com.tzCloud.arch.common.enums.ApiCallResult;
import com.tzCloud.arch.common.exception.BaseBusinessException;
import com.tzCloud.arch.common.exception.BaseException;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.UnauthorizedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author: whisky_vip
 * @date: 2018/8/16 10:22
 * @description: 异常统一处理
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    private final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ResponseBody
    @ExceptionHandler(BaseBusinessException.class)
    public ResponseModel processException(BaseBusinessException e) {
        String msg = "BaseBusinessException " + e.getMessage();
        e.printStackTrace();
        logger.error(msg);
        return ResponseModel.fail(e.getErrorCode(), e.getMessage());
    }

    @ResponseBody
    @ExceptionHandler(BaseException.class)
    public ResponseModel processException(BaseException e) {
        String msg = "BaseException " + e.getMessage();
        e.printStackTrace();
        logger.error(msg);
        return ResponseModel.fail(e.getMessage());
    }

    @ResponseBody
    @ExceptionHandler(UnauthorizedException.class)
    public ResponseModel processException(UnauthorizedException e) {
        String msg = "UnauthorizedException " + e.getMessage();
        logger.error(msg);
        return ResponseModel.fail(ApiCallResult.PERMISSION_DENIED);
    }

    /**
     * //[\u4E00-\u9FA5]是unicode2的中文区间
     */
    private static Pattern pattern = Pattern.compile("[^\u4E00-\u9FA5]");

    @ResponseBody
    @ExceptionHandler(Exception.class)
    public ResponseModel processException(Exception e) {
        String msg = "all exception " + e.getMessage();
        e.printStackTrace();
        logger.error(msg);
        if (StringUtils.isBlank(e.getMessage())) {
            return ResponseModel.fail();
        }
        Matcher matcher = pattern.matcher(e.getMessage());
        String  message = matcher.replaceAll("");
        if (StringUtils.isBlank(message)){
            message = "服务器开小差了";
        }
        return ResponseModel.fail(message);
    }

}
