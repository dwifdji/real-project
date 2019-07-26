package com.liuhaolei.dreamer.common.exception;

/**
 *  通用业务层异常
 *  create by liuhaolei on 2018-09-15
 *
 */
public class BusinessException extends RuntimeException{

	public BusinessException() {
		super();
	}
 

	public BusinessException(String message) {
		super(message);
	}

}
