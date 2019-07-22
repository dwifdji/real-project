package com._360pai.core.exception;

import com._360pai.arch.common.enums.ApiCallResult;
import com._360pai.arch.common.exception.BaseBusinessException;
import com._360pai.arch.common.exception.ExceptionEnum;

/**
 * @author xdrodger
 * @Title: CoreBusinessException
 * @ProjectName zeus
 * @Description:
 * @date 22/09/2018 11:40
 */
public class BusinessException extends BaseBusinessException {
    public BusinessException() {
        super();
    }

    public BusinessException(String message) {
        super();
        this.errorCode = ApiCallResult.FAILURE.getCode();
        this.message = message;
    }

    public BusinessException(Throwable arg0) {
        super(arg0);
    }

    public BusinessException(ApiCallResult result) {
        super();
        this.errorCode = result.getCode();
        this.message = result.getDesc();
    }

    public BusinessException(ApiCallResult result, String detail) {
        super();
        this.errorCode = result.getCode();
        this.message = detail;
    }

    public BusinessException(String errorCode, Throwable cause) {
        super(cause);
        this.errorCode = errorCode;
    }

    public BusinessException(String errorCode, String message) {
        super();
        this.errorCode = errorCode;
        this.message = message;
    }

    public BusinessException(String errorCode, String message, Throwable cause) {
        super(cause);
        this.errorCode = errorCode;
        this.message = message;
    }

    public BusinessException(ExceptionEnum ee){
        super();
        this.errorCode = ee.getCode();
        this.message = ee.getMessage();
    }

    /**
     * Getter method for property <tt>errorCode</tt>.
     *
     * @return property value of errorCode
     */
    public String getErrorCode() {
        return errorCode;
    }

    /**
     * Setter method for property <tt>errorCode</tt>.
     *
     * @param errorCode value to be assigned to property errorCode
     */
    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    /**
     * Getter method for property <tt>message</tt>.
     *
     * @return property value of message
     */
    @Override
    public String getMessage() {
        return message;
    }

    /**
     * Setter method for property <tt>message</tt>.
     *
     * @param message value to be assigned to property message
     */
    public void setMessage(String message) {
        this.message = message;
    }
}
