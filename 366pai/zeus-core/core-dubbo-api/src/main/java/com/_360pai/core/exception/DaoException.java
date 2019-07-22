package com._360pai.core.exception;

import com._360pai.arch.common.enums.ApiCallResult;
import com._360pai.arch.common.exception.BaseDaoException;
import com._360pai.arch.common.exception.ExceptionEnum;

/**
 * @author xdrodger
 * @Title: DaoException
 * @ProjectName zeus
 * @Description:
 * @date 22/09/2018 14:41
 */
public class DaoException extends BaseDaoException {
    /**
     * 序列化版本
     */
    private static final long serialVersionUID = 7892004144187904668L;

    protected String errorCode;

    protected String message;

    public DaoException() {
        super();
    }

    public DaoException(String errorCode) {
        super();
        this.errorCode = errorCode;
    }

    public DaoException(Throwable arg0) {
        super(arg0);
    }

    public DaoException(ApiCallResult result) {
        super();
        this.errorCode = result.getCode();
        this.message = result.getDesc();
    }

    public DaoException(ApiCallResult result, String detail) {
        super();
        this.errorCode = result.getCode();
        this.message = result.getDesc() + "/" + detail;
    }

    public DaoException(String errorCode, Throwable cause) {
        super(cause);
        this.errorCode = errorCode;
    }

    public DaoException(String errorCode, String message) {
        super();
        this.errorCode = errorCode;
        this.message = message;
    }

    public DaoException(String errorCode, String message, Throwable cause) {
        super(cause);
        this.errorCode = errorCode;
        this.message = message;
    }

    public DaoException(ExceptionEnum ee){
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
