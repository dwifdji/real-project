package com._360pai.arch.common.exception;

import com._360pai.arch.common.enums.ApiCallResult;

public class BaseBusinessException extends BaseException {
    private static final long serialVersionUID = -8643291827871393589L;

    protected String errorCode;

    protected String message;

    public BaseBusinessException() {
        super();
    }

    public BaseBusinessException(String errorCode) {
        super();
        this.errorCode = errorCode;
    }

    public BaseBusinessException(Throwable arg0) {
        super(arg0);
    }

    public BaseBusinessException(ApiCallResult result) {
        super();
        this.errorCode = result.getCode();
        this.message = result.getDesc();
    }

    public BaseBusinessException(ApiCallResult result, String detail) {
        super();
        this.errorCode = result.getCode();
        this.message = result.getDesc() + ":" + detail;
    }

    public BaseBusinessException(String errorCode, Throwable cause) {
        super(cause);
        this.errorCode = errorCode;
    }

    public BaseBusinessException(String errorCode, String message) {
        super();
        this.errorCode = errorCode;
        this.message = message;
    }

    public BaseBusinessException(String errorCode, String message, Throwable cause) {
        super(cause);
        this.errorCode = errorCode;
        this.message = message;
    }

    public BaseBusinessException(ExceptionEnum ee){
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
