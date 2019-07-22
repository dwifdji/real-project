package com.winback.arch.common.exception;

import com.winback.arch.common.enums.ApiCallResult;

/**
/**
 * Dao层异常
 */
public class BaseDaoException extends BaseException {

    /**
     * 序列化版本
     */
    private static final long serialVersionUID = 7892004144187904668L;

    protected String errorCode;

    protected String message;

    public BaseDaoException() {
        super();
    }

    public BaseDaoException(String errorCode) {
        super();
        this.errorCode = errorCode;
    }

    public BaseDaoException(Throwable arg0) {
        super(arg0);
    }

    public BaseDaoException(ApiCallResult result) {
        super();
        this.errorCode = result.getCode();
        this.message = result.getDesc();
    }

    public BaseDaoException(ApiCallResult result, String detail) {
        super();
        this.errorCode = result.getCode();
        this.message = result.getDesc() + ":" + detail;
    }

    public BaseDaoException(String errorCode, Throwable cause) {
        super(cause);
        this.errorCode = errorCode;
    }

    public BaseDaoException(String errorCode, String message) {
        super();
        this.errorCode = errorCode;
        this.message = message;
    }

    public BaseDaoException(String errorCode, String message, Throwable cause) {
        super(cause);
        this.errorCode = errorCode;
        this.message = message;
    }

    public BaseDaoException(ExceptionEnum ee){
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
